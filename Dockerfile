FROM openjdk:11
ARG JAR_FILE=./${project.artifactId}-${project.version}.jar
COPY ${JAR_FILE} /opt/app.jar
WORKDIR /opt
ENTRYPOINT ["java","-jar","app.jar"]