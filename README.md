# Spring Data JPA and Spring web starter

## 1. Pre-requisites
   - Java 7+
   - Maven 3.8.6 
   - MariaDB 10.3.4

## 2. Configuration
- Create resources/env.properties file. Required properties:
  - `DB_DATABASE=nameofmariadbdatabase`
  - `DB_USER=mariadbuser`
  - `DB_PASSWORD=mariadbuserpassword`

- Check application.properties file for other properties.

## 3. Entities :
  - __Create entities__ in `com.example.demo.entities` package using __JPA syntax__
    - They are auto discovered and there is no persistence.xml thanks to spring boot
  - __Create repositories__ in `com.example.demo.repository` package

## 4. Controllers :
- __Create controllers__ in `com.example.demo.controller` package
- They are auto discovered and there is no web.xml thanks to spring boot

## 5. Exceptions :
- If any exceptions are thrown, they are handled by class `com.example.demo.exception.GlobalExceptionHandler`
- Special care was needed to handle exception thrown by Spring Security :
  - implemented interface `AuthenticationEntryPoint` to delegate authentication exception handling to GlobalExceptionHandler
  - implemented `AccessDeniedHandler` to delegate authorization exception handling to GlobalExceptionHandler

## 6. Documentation
- __SpringDoc__ is used to document the API
- Check route in `application.properties` file 
  - `springdoc.api-docs.path=/docs`
  - `springdoc.swagger-ui.path=/docs/swagger.html`
- Use Operation annotation on controllers to add a custom description or document them as secured :
  - `@Operation(summary="Get user by id",security = @SecurityRequirement(name = "basicAuth"))`
    - `security="basicAuth"` is used to document the security scheme, defined in application class as follow :
      - `@SecurityScheme(
    name = "basicAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer"
    )`
  
#### More thoughts :

![Registration sequence diagram](Registration_Sequence.png?raw=true "Regestration sequence diagram")
