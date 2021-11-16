FROM gradle:7.3.0-jdk11-alpine

LABEL author = "Sergey Andruhovich sergeyandruhovich1998@solbeg.com"

ENV JAVA_OPTS=""

WORKDIR /app

COPY /build/libs/library-0.0.1-SNAPSHOT.jar .

CMD java ${JAVA_OPTS} -jar library-0.0.1-SNAPSHOT.jar