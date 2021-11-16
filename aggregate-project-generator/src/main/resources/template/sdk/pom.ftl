<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>org.github.commons</groupId>
        <artifactId>${projectName}</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>${projectName}-sdk</artifactId>
    <version>${r'${project.version}'}</version>
    <name>${r'${project.artifactId}'}</name>

    <dependencies>
        <dependency>
            <groupId>org.github.commons</groupId>
            <artifactId>${projectName}-bean</artifactId>
        </dependency>
    </dependencies>

</project>
