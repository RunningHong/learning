package com.hong.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;

/**
 * @author hongzh.zhang on 2020/08/01
 *
 * 作用：对文件中的单词进行计数
 *
 * 在hadoop上运行
 * 1、把项目通过maven打成jar(需要指定main class为com.hong.hadoop.WordCountMR)
 * 2、把jar包(hadoop-learning-1.0-SNAPSHOT.jar)上传到服务器
 * 3、运行：hadoop jar hadoop-learning-1.0-SNAPSHOT.jar /textfile/file_hello.txt /out/count_out
 * 4、通过hdfs dfs -cat /out/count_out/* 来查看最终的结果信息
 */
public class WordCountMR {
    public static void main(String[] args) throws Exception {
        // 获取配置信息
        Configuration configuration = new Configuration();

        // 输入和输出路径
        String inputPath=args[0];
        String outputPath=args[1];

        System.out.println("args[0]  "+inputPath);
        System.out.println("args[1]  "+outputPath);

        // :首先删除输出路径的已有生成文件
        FileSystem fs = FileSystem.get(new URI(inputPath), configuration);
        Path outPath = new Path(outputPath);
        if (fs.exists(outPath)) {
            fs.delete(outPath, true);
        }

        // 设置job实例
        Job job = Job.getInstance(configuration, "WordCountMR");

        // 3、设置加载jar的位置路径,直接传入当前Class对象
        job.setJarByClass(WordCountMR.class);

        // 4.设置Map类
        job.setMapperClass(WordCountMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 设置reduce类
        job.setReducerClass(WordCountReducer.class);

        // 设置最终的输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);


        System.out.println("args[0]  "+args[0]);
        System.out.println("args[1]  "+args[1]);

        // 设置输入和输出路径
        FileInputFormat.setInputPaths(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        long beginMillis = System.currentTimeMillis();

        // 提交job&并且等待job计算完成
        job.waitForCompletion(true);

        long endMillis = System.currentTimeMillis();
        System.out.println("耗时："+(endMillis-beginMillis)+"millis");
    }

    // 前两个参数类型（LongWritable，Text）代表Map的输入类型
    //     longWritable: map的key-行偏移量
    //     Text: map的value-行数据
    // 后两个参数类型（Text，IntWritable）代表Map输出
    //     Text: 输出类型的key
    //     IntWritable: 输出类型的value
    public static class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line=value.toString();
            String[] words=line.split(" ");
            for (String word : words) {
                if (!"".equals(word.trim())) {
                    context.write(new Text(word), new IntWritable(1));
                }
            }
        }
    }


    // 前两个参数类型Text, IntWritable代表Map阶段输出的参数类型（即map最后context.write的参数类型）需要和main方法里的第5阶段类型(设置Map的输出类型)对应
    // 后两个参数类型Text, IntWritable代表Reduce阶段输出的参数类型（即reduce最后context.write的参数类型）需要和main方法里的第7阶段类型(设置最终的输出类型)对应
    public static class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum=0;
            Iterator<IntWritable> iter = values.iterator();
            while (iter.hasNext()) {
                sum+=iter.next().get();
            }
            context.write(key, new IntWritable(sum));
        }
    }

}
