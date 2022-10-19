# Spring Data JPA and Spring web starter

## 1. Pre-requisites
   - Java 7+
   - Maven 3.8.6 
   - MariaDB 10.3.4

## 2. How to continue
   - Create resources/env.properties file. Required properties:
     - DB_DATABASE=nameofmariadbdatabase
     - DB_USER=
     - DB_PASSWORD=
     - spring.datasource.username
   - Entities :
     - Create entities in com.example.demo.entity package using JPA syntax
     - They are auto discovered and there is no persistence.xml thanks to spring boot
     - Create repositories in com.example.demo.repository package
   - Controllers :
     - Create controllers in com.example.demo.controller package
     - They are auto discovered and there is no web.xml thanks to spring boot
   - Exceptions :
     - if any exceptions are thrown, they are handled by com.example.demo.exception.GlobalExceptionHandler
   - Swagger :
     - Swagger is configured in com.example.demo.config.SwaggerConfig

### 3. 