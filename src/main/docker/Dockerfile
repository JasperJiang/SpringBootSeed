FROM openjdk:8-jre-alpine
ENV APP_HOME /var/app
ENV LOG ${APP_HOME}/logs
ENV DATA_PATH_FILES ${APP_HOME}/dataFiles

RUN mkdir -p "$APP_HOME"
RUN mkdir -p "${LOG}"
RUN mkdir -p "${DATA_PATH_FILES}"

VOLUME ["/tmp","${APP_HOME}","${LOG}","${DATA_PATH_FILES}"]

ADD *.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Dlogging.file=${LOG}/cip.log -Dapp.serverPathForDataIngestion=${DATA_PATH_FILES} -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]