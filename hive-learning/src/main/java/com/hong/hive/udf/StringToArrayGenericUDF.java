package com.hong.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;

/**
 * 将逗号分隔的字符串(传入一个参数)转换为List并返回
 */
public class StringToArrayGenericUDF extends GenericUDF {

    private ArrayList aryayList = new ArrayList();
    
    /**
     * 这个方法只调用一次，并且在evaluate()方法之前调用。
     * 该方法接受的参数是一个ObjectInspectors数组。
     * 该方法可以检查接受正确的参数类型和参数个数。
     */
    @Override
    public ObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {
        // 初始化文件系统，可以在这里初始化读取文件等
        System.out.println("################这个方法只调用一次， hello################");

        //定义函数的返回类型为java的List
        ObjectInspector returnOI = PrimitiveObjectInspectorFactory
                .getPrimitiveJavaObjectInspector(PrimitiveObjectInspector.PrimitiveCategory.STRING);
        return ObjectInspectorFactory.getStandardListObjectInspector(returnOI);
    }

    @Override
    public Object evaluate(DeferredObject[] args) throws HiveException {
        aryayList.clear();
        if (args.length<1) {
            return aryayList;
        }

        // 获取第一个参数
        String content=args[0].get().toString();
        String[] words=content.split(",");
        for (String word : words) {
            aryayList.add(word);
        }
        return aryayList;
    }

    /**
     * 这个方法用于当实现的GenericUDF出错的时候，打印出提示信息。
     * 没有太多作用
     */
    @Override
    public String getDisplayString(String[] strings) {
        return "提示信息：一个参数(String)，以逗号分隔,最终返回List";
    }

    /**
     * 关闭操作，关闭流等
     * 注意：This is only called in runtime of MapRedTask.
     */
    @Override
    public void close() {
        System.out.println("################关闭了################");
    }
}
