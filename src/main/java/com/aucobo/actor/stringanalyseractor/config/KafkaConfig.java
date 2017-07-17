package com.aucobo.actor.stringanalyseractor.config;

import com.aucobo.actor.stringanalyseractor.StringAnalyserActorApplication;
import org.apache.log4j.Logger;


import java.io.IOException;
import java.util.Properties;


/**
 * Created by Fabian on 14.07.2017.
 */
public class KafkaConfig {

    public static Properties props;
    public static String applicationName;
    public static String bootstrapServersConfig;
    public static String zookeeperServersConfig;
    public static String sourceTopic;
    public static String targetTopic;
    public static String groupId;
    public static String clientId;
    public static String keyDeserializer;
    public static String valueDeserializer;
    public static String autoOffsetReset;
    public static String searchFor;
    private static Logger logger = Logger.getLogger(KafkaConfig.class);
    private static KafkaConfig instance;

    private KafkaConfig() {

        //load properties from application properties
        props = new Properties();
        try {
            props.load(StringAnalyserActorApplication.class.getClassLoader()
                    .getResourceAsStream("application.properties"));

            applicationName = props.getProperty("aucobo.kafka.application_name");
            System.out.println(applicationName);
            bootstrapServersConfig = props.getProperty("aucobo.kafka.bootstrap_servers_config");
            System.out.println(bootstrapServersConfig);
            zookeeperServersConfig = props.getProperty("aucobo.kafka.zookeeper_servers_config");
            System.out.println(zookeeperServersConfig);
            sourceTopic = props.getProperty("aucobo.kafka.source_topic");
            System.out.println(sourceTopic);
            targetTopic = props.getProperty("aucobo.kafka.target_topic");
            System.out.println(targetTopic);
            groupId = props.getProperty("aucobo.kafka.group_id");
            clientId = props.getProperty("aucobo.kafka.client_id");
            keyDeserializer = props.getProperty("aucobo.kafka.key_deserializer");
            valueDeserializer = props.getProperty("aucobo.kafka.value_deserializer");
            autoOffsetReset = props.getProperty("aucobo.kafka.auto_offset_reset");

            searchFor = props.getProperty("aucobo.kafka.search_for");

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    public static KafkaConfig getInstance() {
        if (null == KafkaConfig.instance) {
            KafkaConfig.instance = new KafkaConfig();
        }
        return KafkaConfig.instance;
    }


}
