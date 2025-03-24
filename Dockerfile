FROM gradle:8.8.0-jdk-21-and-22 AS build
WORKDIR /home/gradle/

COPY build.gradle settings.gradle gradlew /home/gradle/
COPY src/main/resources/application*.yaml /home/gradle/
COPY src /home/gradle/src
COPY run.sh /home/gradle/

RUN mkdir /usr/local/jvm
RUN wget https://download.java.net/java/GA/jdk21.0.2/f2283984656d49d69e91c558476027ac/13/GPL/openjdk-21.0.2_linux-x64_bin.tar.gz
RUN tar zxf openjdk-21.0.2_linux-x64_bin.tar.gz --directory /usr/local/jvm/



RUN gradle --refresh-dependencies --no-daemon
RUN gradle clean build -x test

FROM debian:stable-slim
ARG BUILD_VERSION
ENV VERSION=$BUILD_VERSION
ENV TZ=America/Bogota

RUN groupadd -r demo && useradd -r -g demo -m -d /etc/demo  demo
RUN set -ex; \
    mkdir -p /etc/demo/config; \
    mkdir /etc/demo/logs; \
    touch /etc/demo/logs/demo.log; \
    chown -R demo:demo /etc/demo; \
    chmod 754 -R /etc/demo; \
    mkdir -p /usr/local/jvm/java;

COPY --from=build /home/gradle/*.yaml /etc/demo/config/
COPY --from=build /home/gradle/build/libs/demo1-${VERSION}.jar /etc/demo/demo1.jar
COPY --from=build /home/gradle/run.sh /usr/local/bin
COPY --from=build  /usr/local/jvm/*  /usr/local/jvm/java




RUN set -ex; \
    chown -R demo:demo /etc/demo; \
    chmod a+x /usr/local/bin/run.sh



ENV SPRING_CONFIG_LOCATION=/etc/demo/config/
ENV CLASSPATH=/etc/demo
ENV SPRING_CONFIG_NAME=application
ENV JAVA_HOME=/usr/local/jvm/java
ENV PATH=$PATH:${JAVA_HOME}/bin



USER demo:demo
WORKDIR /etc/demo

ENTRYPOINT ["run.sh"]
EXPOSE 8080
