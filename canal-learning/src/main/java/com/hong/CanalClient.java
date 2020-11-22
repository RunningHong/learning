package com.hong;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.Message;
import com.hong.canal.CanalClientPrintMes;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author hongzh.zhang on 2020/11/15
 */
public class CanalClient {
    public static void main(String args[]) {
        System.out.println("开始获取变更的binlog信息");

        CanalClientPrintMes canalClientPrintMes = new CanalClientPrintMes();

        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress("node01", 11111),
                "example", "canal", "canal");
        int batchSize = 1000;

        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();

            int line_num=0;
            while (true) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) { // 没有消息就睡眠一秒
                    try {
                        Thread.sleep(1000);
                        if (line_num % 80 == 0) { // 定时换行打印
                            System.out.print("\n等待消息中.");
                        } else {
                            System.out.print(".");
                        }
                        line_num++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    connector.ack(batchId); // 提交确认
                } else { // 有消息这接打印
                    long millis = System.currentTimeMillis();
                    System.out.println("\n###################### "+millis+" ######################");

                    // print demo1
                    // printEntry(message.getEntries());

                    // print demo2
                    printEntry2(message.getEntries());

                    // 官方print案例更改
                    // canalClientPrintMes.printEntry(message.getEntries());

                    System.out.println("\n");
                    line_num=0;
                }
            }

        } finally {
            connector.disconnect();
        }
    }

    private static void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            // 跳过事务的开始以及结束
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChange = null;
            try {
                rowChange = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChange.getEventType();
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));


            // 如果是DDL语句--打印变更的sql
            if (rowChange.getIsDdl()) {
                System.out.println(rowChange.getSql());
            }

            // 打印变更的字段信息
            printRowDataMes(rowChange, eventType);
        }
    }


    private static void printEntry2(List<Entry> entrys) {
        for (Entry entry : entrys) {

            // 只关注ROWDATA类型的Entry
            if (entry.getEntryType() != EntryType.ROWDATA) {
                continue;
            }

            // 获取header
            CanalEntry.Header header = entry.getHeader();
            // 忽略QUERY，如果binlog设置为row也不会出现QUERY的eventType
            if (header.getEventType() == EventType.QUERY) {
                continue;
            }

            // 从header获取信息
            EventType eventType = header.getEventType();
            long eventLength = header.getEventLength();
            long executeTime = header.getExecuteTime();
            String logFileName = header.getLogfileName();
            long logfileOffset = header.getLogfileOffset();
            String schemaName = header.getSchemaName();
            String tableName = header.getTableName();

            //获取rowChange
            RowChange rowChange;
            try {
                rowChange = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("parse event occurs an error , data:" + entry.toString(), e);
            }

            int rowDataCount = rowChange.getRowDatasCount();

            String mes = String.format("=========== logFileName[%s], logfileOffset[%s], " +
                            "\n\t\t\tschemaName[%s], tableName[%s], " +
                            "\n\t\t\teventType[%s], eventLength[%s], " +
                            "\n\t\t\texecuteTime[%s], rowDatasCount[%s]",
                    logFileName, logfileOffset,
                    schemaName, tableName,
                    eventType, eventLength,
                    executeTime, rowDataCount);
            System.out.println(mes);

            // 打印变更的字段信息
            printRowDataMes(rowChange, eventType);
        }

    }

    /**
     * 打印变更的字段信息
     */
    private static void printRowDataMes(RowChange rowChange, EventType eventType) {
        // 根据类型[DELETE||INSERT||other]打印变更的字段
        for (RowData rowData : rowChange.getRowDatasList()) {
            if (eventType == EventType.DELETE) {
                printColumn(rowData.getBeforeColumnsList());
            } else if (eventType == EventType.INSERT) {
                printColumn(rowData.getAfterColumnsList());
            } else {
                System.out.println("\n-------更改前字段信息如下：");
                printColumn(rowData.getBeforeColumnsList());
                System.out.println("-------更改后字段信息如下：");
                printColumn(rowData.getAfterColumnsList());
                System.out.println("\n");
            }
        }

    }


    /**
     * 打印字段信息
     */
    private static void printColumn(List<Column> columns) {
        for (Column column : columns) {
            String colName = column.getName(); // 字段名称
            String colType = column.getMysqlType(); // 字段类型
            String colValue = column.getValue(); // 字段值
            boolean isUpdate = column.getUpdated(); // 这次该字段是否更新

            System.out.println(String.format(
                    "\t\t\tcolName[%s]\tcolType[%s]\tcolValue[%s]\tisUpdate[%s]",
                    colName, colType, colValue, isUpdate));
        }
    }

}
