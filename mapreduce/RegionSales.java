
// Region-wise Total Sales Analysis
// Hadoop MapReduce Implementation

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class RegionSales {


    public static class SalesMapper 
        extends Mapper<Object, Text, Text, DoubleWritable> {


        private Text region = new Text();
        private DoubleWritable sales = new DoubleWritable();


        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {


            String[] fields = value.toString().split(",");


            try {

                region.set(fields[4]);
                sales.set(Double.parseDouble(fields[6]));

                context.write(region, sales);

            } catch(Exception e) {

                // Ignore header and invalid rows

            }
        }
    }



    public static class SalesReducer 
        extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {


        private DoubleWritable result = new DoubleWritable();


        public void reduce(Text key, Iterable<DoubleWritable> values,
                Context context)
                throws IOException, InterruptedException {


            double totalSales = 0;


            for(DoubleWritable val : values) {

                totalSales += val.get();

            }


            result.set(totalSales);

            context.write(key, result);

        }
    }




    public static void main(String[] args) throws Exception {


        Configuration conf = new Configuration();


        Job job = Job.getInstance(conf,
                "Region Wise Total Sales Analysis");



        job.setJarByClass(RegionSales.class);



        job.setMapperClass(SalesMapper.class);

        job.setReducerClass(SalesReducer.class);



        job.setOutputKeyClass(Text.class);

        job.setOutputValueClass(DoubleWritable.class);



        FileInputFormat.addInputPath(job,
                new Path(args[0]));


        FileOutputFormat.setOutputPath(job,
                new Path(args[1]));



        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}
