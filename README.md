# Spring Data JPA and Spring web starter

## 1. Pre-requisites
   - Java 7+
   - Maven 3.8.6 
   - MariaDB 10.3.4

## 2. Configuration
- Create resources/env.properties file. Required properties:
  - `DB_URL=jdbc:mysql://ip:3306/dbName?&useSSL=false&serverTimezone=UTC`
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

[comment]: # (![Registration sequence diagram](Registration_Sequence.png?raw=true "Regestration sequence diagram")
- On login, Security process starts mannually : 
  - `AuthenticationManager` is the entry point of the authentication process.
  - `AuthenticationProvider` is the interface that is responsible for authenticating the user.
  - `DaoAuthenticationProvider` is the implementation of the `AuthenticationProvider` interface that is responsible for authenticating a user by using a `UserDetailsService` implementation to retrieve the user details.
  - Since a `PasswordEncoder` bean is registered, Spring Security will use this one.
  - Same principle for `UserDetailsService` bean.
- On `@PreAuthorized` requests :
  - JwtTokenVerifierFilter is called
  - It verifies the token
  - It creates an authentication object with the user email parsed in the token
  - It sets the authentication in the SecurityContext

P.S. : JwtTokenVerifierFilter is not called on login request because it is not annotated with `@PreAuthorized` annotation.

P.S. : UsernamePasswordAuthenticationFilter is never called but the JwtTokenVerifierFilter is registered before it in the filter chain because it's a good place.
