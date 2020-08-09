package com.hong.hive.udtf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *  对输入的字符串进行扩展，对逗号进行分隔，第一列为分隔出来的值，第二列为col_2_第一列值
 * 输入AA,BB
 * 返回：
 * AA col_2_AA
 * BB col_2_BB
 */
public class GenericUDTFExplodeV2 extends GenericUDTF {

    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        // 生成表的字段名数组，多个列对应多个值
        ArrayList<String> fieldNames = new ArrayList<>();

        // 生成表的字段对象监控器（object inspector）数组，即生成表的行对象每个字段的类型
        ArrayList<ObjectInspector> fieldOIs = new ArrayList<>();

        fieldNames.add("col_1"); // 第一个字段名：col_1
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector); // 第一个字段类型是PRIMITIVE

        fieldNames.add("col_2"); // 第二个字段名：col_2
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector); // 第二个字段类型是PRIMITIVE

        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs); //返回对象监控器
    }

    @Override
    public void process(Object[] args) throws HiveException {
        String str=args[0].toString(); // 得到当前行的数据,真实类型为Text

        String[] values=str.split(","); // 进行分割

        List<String[]> result=null; // 最终输出的结果

        for (int i = 0; i < values.length; i++) {
            String col_1_value=values[i];
            String col_2_value="col_2_"+values[i];

            result.add(new String[]{col_1_value, col_2_value});

            // 转发结果。注意：每转发一次生成一行
            forward(result);
        }
    }

    @Override
    public void close() throws HiveException {
        System.out.println("关闭啦");
    }
}
