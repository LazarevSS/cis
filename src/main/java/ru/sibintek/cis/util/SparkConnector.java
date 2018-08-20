package ru.sibintek.cis.util;

import com.lucidworks.spark.rdd.SolrJavaRDD;
import org.apache.solr.common.SolrDocument;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkConnector implements Runnable {
    private static volatile SparkConnector instance;
    private SolrJavaRDD solrJavaRDD;
    private JavaRDD<SolrDocument> resultsRDD;

    private SparkConnector() {
        Thread t = new Thread(this);
        t.start();
    }

    public static SparkConnector getInstance() {
        if (instance == null) {
            synchronized (SparkConnector.class) {
                if (instance == null) {
                    instance = new SparkConnector();
                }
            }
        }
        return instance;
    }

    public JavaRDD<SolrDocument> getResultRDD() {
        if (resultsRDD == null) {
            SparkConf conf = new SparkConf();
            conf.setAppName("Spark MultipleContest Test");
            conf.set("spark.driver.allowMultipleContexts", "true");
            conf.setMaster("local");
            JavaSparkContext jsc = new JavaSparkContext(conf);
            solrJavaRDD = SolrJavaRDD.get("192.168.1.8:9983", "mycoll", jsc.sc());
            resultsRDD = solrJavaRDD.queryShards("*:*");
        }
        return resultsRDD;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(10000);
                resultsRDD = solrJavaRDD.queryShards("*:*");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
