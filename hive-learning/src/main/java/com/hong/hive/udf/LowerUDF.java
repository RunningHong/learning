package com.hong.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * UDF
 * 1、打成jar
 * 2、上传至hdfs: hdfs dfs -put hive_udf_project.jar /udf/
 * 3、hive中：add jar hdfs:/udf/hive_udf_project.jar;
 * 4、create temporary function lower_udf as 'com.hong.hive.udf.LowerUDF';
 * 5、在hive中使用： select lower_udf('AAbb')
 */
public class LowerUDF extends UDF {

    /**
     * 需要实现evaluate方法，该方法最终是通过反射调用的
     */
    public Text evaluate(Text text) {
        if (text == null) {
            return null;
        }
        return new Text(text.toString().toLowerCase());
    }

}
