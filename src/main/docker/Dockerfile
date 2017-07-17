FROM frolvlad/alpine-oraclejdk8:slim

LABEL com.aucobo.name="aucobo-stringanalyseractor" \
            com.aucobo..description="analyses strings" \
            com.aucobo.url="https://www.aucobo.de" \
            com.aucobo.vendor="aucobo GmbH" \
            com.aucobo.maintainer="fabian.peltzer@aucobo.de"

ENV LOGLEVEL "WARN"

# KAFKA
ENV APPLICATION_NAME "stringanalyseractor"
ENV BOOTSTRAP_SERVERS_CONFIG "ec2-52-57-210-172.eu-central-1.compute.amazonaws.com:29092"
ENV SOURCE_TOPIC "quickstart-jdbc-temperature"
ENV TARGET_TOPIC "sensor1.temperature.contains"
ENV GROUP_ID "stringanalyseractor"
ENV CLIENT_ID "instance1"
ENV AUTO_OFFSET_RESET "earliest"

ENV SEARCHSTRING "email"

ENV JAVA_OPTS ""

WORKDIR /data

COPY stringanalyseractor.jar /data/app.jar

RUN sh -c 'touch /data/app.jar'

ENTRYPOINT ["sh", "-c", "java -Dlog4j.rootLogger=$LOGLEVEL \
-Daucobo.kafka.application_name=$APPLICATION_NAME \
-Daucobo.kafka.bootstrap_servers_config=$BOOTSRAP_SERVERS_CONFIG \
-Daucobo.kafka.source_topic=$SOURCE_TOPIC \
-Daucobo.kafka.target_topic=$TARGET_TOPIC \
-Daucobo.kafka.group_id=$GROUP_ID \
-Daucobo.kafka.client_id=$CLIENT_ID \
-Daucobo.kafka.auto_offset_reset=$AUTO_OFFSET_RESET \
-Daucobo.kafka.search_for=$SEARCHSTRING \
-Djava.security.egd=file:/dev/./urandom -Xms256m -Xmx512m -server $JAVA_OPTS -jar ./app.jar"]