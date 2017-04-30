FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
VOLUME /deal/images
ADD gs-spring-boot-docker-0.1.0.jar app.jar
ADD image-example.jpg image-example.jpg
RUN chmod 777 image-example.jpg
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
