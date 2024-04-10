FROM openjdk:11
#WORKDIR	/app
COPY ./target/tipoCambio-1.0.jar tipoCambio.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar","tipoCambio.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]