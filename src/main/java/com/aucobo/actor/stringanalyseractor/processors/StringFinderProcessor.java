package com.aucobo.actor.stringanalyseractor.processors;

import com.aucobo.actor.stringanalyseractor.config.KafkaConfig;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.log4j.Logger;

/**
 * Created by Fabian on 14.07.2017.
 */
public class StringFinderProcessor implements Processor<String, String> {

    private static Logger logger = Logger.getLogger(StringFinderProcessor.class);
    private ProcessorContext context;
    private KafkaConfig kafkaConfig;


    @Override
    public void init(ProcessorContext processorContext) {
        this.context = processorContext;
        this.context.schedule(100000);
        kafkaConfig = KafkaConfig.getInstance();
        logger.error("-- STARTED --");
    }

    @Override
    public void process(String key, String value) {
        logger.info("PROCESS ++++++++++");
        if (value.toLowerCase().contains(kafkaConfig.searchFor.toLowerCase())) {
            logger.error("KEY: " + key);
            System.out.println(key + " | " + value);
            context.forward(key, value);
            context.commit();
        }
    }

    @Override
    public void punctuate(long l) {

    }

    @Override
    public void close() {

    }
}
