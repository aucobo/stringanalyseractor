package com.aucobo.actor.stringanalyseractor;

import com.aucobo.actor.stringanalyseractor.processors.StringFinderProcessor;
import com.aucobo.actor.stringanalyseractor.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.apache.log4j.Logger;

import java.util.Properties;


/**
 * Created by Fabian on 14.07.2017.
 */
public class StringAnalyserActorApplication {

    static Logger logger = Logger.getLogger(StringAnalyserActorApplication.class);

    public static void main(String[] args) {

        KafkaConfig kafkaConfig = KafkaConfig.getInstance();

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.applicationName);
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.bootstrapServersConfig);
        //config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.zookeeperServersConfig);
        config.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 100);


        StringDeserializer stringDeserializer = new StringDeserializer();
        StringSerializer stringSerializer = new StringSerializer();

        StreamsConfig sConfig = new StreamsConfig(config);

        TopologyBuilder builder = new TopologyBuilder();
        logger.info("TOPOLOGY");
        builder.addSource("SOURCE", stringDeserializer, stringDeserializer, kafkaConfig.sourceTopic);
        logger.info("SOURCE");
        builder.addProcessor("PROCESS", StringFinderProcessor::new, "SOURCE");
        logger.info("PROCESSOR");
        builder.addSink("TARGET", kafkaConfig.targetTopic, stringSerializer, stringSerializer, "PROCESS");
        logger.info("TARGET");

        KafkaStreams streams = new KafkaStreams(builder, sConfig);

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        streams.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable ex) {
                ex.printStackTrace();
            }
        });

        streams.cleanUp();
        streams.start();
        System.out.println("-------------------------------- >>> STREAM started <<< ------------------------------");

    }

}
