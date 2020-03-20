FROM openjdk:8-jdk
RUN mkdir /source && wget https://github.com/apache/incubator-dubbo-admin/archive/0.2.0.zip && unzip 0.2.0.zip -d /source
RUN wget https://mirror.bit.edu.cn/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz && tar -zxvf apache-maven-3.6.3-bin.tar.gz -C /source
WORKDIR /source/dubbo-admin-0.2.0
RUN pwd && ls /source/dubbo-admin-0.2.0
RUN sed -i 's/zookeeper:\/\/127.0.0.1:2181/zookeeper:\/\/host.docker.internal:2181\/dubbo/g' dubbo-admin-server/src/main/resources/application.properties
RUN /source/apache-maven-3.6.3/bin/mvn clean package -Dmaven.test.skip=true

FROM openjdk:8-jre
LABEL maintainer="dev@dubbo.apache.org"
COPY --from=0 /source/dubbo-admin-0.2.0/dubbo-admin-distribution/target/dubbo-admin-0.2.0.jar /app.jar
ENV ZK_ADDR=zookeeper://host.docker.internal:2181/dubbo
RUN echo "===>${ZK_ADDR}<===="
ENTRYPOINT ["java","-Ddubbo.registry.address=${ZK_ADDR} ", "-XX:+UnlockExperimentalVMOptions","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8082