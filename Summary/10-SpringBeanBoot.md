# Spring Bean

- Reusability in software.
- An object managed by the Spring Inversion of Control (IoC) container.
- Spring IoC container: Bean instantiation and the management of dependencies.

## Bean-stantiation

(classes are templates from which objects are created.)  
Let’s take a look at how we would normally use nested objects and compare it to a similar process when using Spring beans **designing a racing game**:

```java
public class RaceTrack {
  private String location;
  private int miles;
  private String trackType;
}

public class Driver {
  private String name;
  private String team;
  private int yearsExperience;
}
```

Now let’s create a class that can be used for each round of the race.

```java
public class RaceRound {
  private String startTime;
  private RaceTrack currentRaceTrack = new RaceTrack();
  private Driver currentDriver = new Driver();
}
```

### With Spring beans:

- @Component annotation:

```java
@Component
public class RaceTrack {
  private String location;
  private int miles;
  private String trackType;
}

@Component
public class Driver {
  private String name;
  private String team;
  private int yearsExperience;
}
```

instead using the @Autowired annotation instead of new() keyword:

```java
public class RaceRound {
  private String startTime;
  @Autowired
  private RaceTrack currentRaceTrack;
  @Autowired
  private Driver currentDriver;
}
```

    Note: @Component and @Autowired are just two of many annotations for working with Spring beans and the IoC container.

## Fully-automatic

Using three annotations:

- **@Configuration**, which notifies the framework that beans may be created via the annotated class.
- **@ComponentScan**, which tells the framework to scan our code for components such as classes, controllers, services, etc.
- **@EnableAutoConfiguration**, which tells the container to auto-create beans from the found components.

When we apply the **@SpringBootApplication** annotation to the class containing our main method, our application runs with all of this built-in functionality.

We can find this annotation provided in most default Spring projects:

```java
@SpringBootApplication
public class RecipeApplication {

  public static void main(String[] args) {
    SpringApplication.run(RecipeApplication.class, args);
  }
}
```

## Looking at what happens:

The Spring framework represents beans as a **BeanDefinition** object. It has several properties, but two are particularly interesting: The property named **class** and there is a property named **properties**.

Our classes no longer have to instantiate their own dependencies. Therefore, we can say the control of dependencies has been inverted back to the container, and this is why we call it an Inversion of Control (IoC) container. (**Control of Object Creation is inverted**)

    Overall, Spring allows developers to encompass a significant amount of functionality with simple annotations and the IoC container’s management of beans is an invaluable resource.

# What is Spring Boot?

We've learned that Spring application are configured as Spring beans and injected as dependencies into other components by the Inversion of Control (IoC) container.

For example of a web component:

```java
import org.springframework.web.bind.annotation.*;

@RequestMapping("/restaurants")
@RestController
public class RestaurantController {
}
```

When our app is run, **this controller class will be registered as a bean** and used to define routes available in our API.

A lot of things are done:

- A Spring Data JPA repository is bootstrapped
- A Tomcat server is initialized
- A WebApplicationContext is initialized
- An H2Dialect is used
- We see an option to Explicitly configure spring.jpa.open-in-view
- The Tomcat server is started on port(s): 4000

### **Where the heck comes all theses tings??**

The answer is **Spring Boot**! Spring Boot is a collection of tools that extends the Spring framework and makes it easier to build applications quickly.

The four main tools:

1. “Starters”
2. Auto-configuration
3. Custom configurations with application.properties
4. Distribution of your application

## Starters

The pom.xml of our Spring Boot application (**coffeeorders**), you’ll see the **spring-boot-start-jpa** and **spring-boot-starter-web** dependencies.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

These are **Spring Boot “starters”**. The **spring-boot-starter-web** dependency instructs Spring Boot to configure Tomcat for running a server and Spring MVC (Model-View-Controller) for routing HTTP requests. The spring-boot-starter-data-jpa sets up JPA and Hibernate for database access.

There are other starters like spring-boot-starter-test which includes common testing libraries such as JUnit Jupiter, Hamcrest, and Mockito, and spring-boot-starter-parent, which provides the spring-boot:run command.

## Auto-Configuration

We understand where dependencies like Tomcat are defined, but how are they hooked into our application?

The **SpringApplication** class and **@SpringBootApplication** annotation from the org.springframework.boot package:

```java
package com.codecademy.coffeeorders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoffeeOrdersApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoffeeOrdersApplication.class, args);
    }
}
```

## SpringApplication

Where runs `SpringApplication.run(CoffeeOrdersApplication.class, args);`. It's responsible for starting up that container. We can see that in the line:

```java
........ Initializing Spring embedded WebApplicationContext
```

    The application context is a representation of the Spring IoC container.

## @SpringBootApplication

It's equivalent to **@Configuration**, **@ComponentScan**, and **@EnableAutoConfiguration**. The first two are from the Spring framework, but the third, @EnableAutoConfiguration, is from Spring Boot.

It enables Spring Boot to review our dependencies (such as spring-boot-starter-jpa and h2) and assume the intended purpose of the application. It will configure and run the application to best fit the assumed purpose.

This explains the Spring Data JPA, Tomcat, and H2Dialect lines in our original code snippet: Spring Boot knows to bootstrap JPA repositories, start a Tomcat server, and use an H2 dialect because spring-boot-starter-jpa, spring-boot-starter-web, and h2 **are listed as dependencies in the pom.xml**.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

Here’s another way to explain it, from the **Spring documentation**:

    @EnableAutoConfiguration…tells Spring Boot to “guess” how you want to configure Spring, based on the jar dependencies that you have added. Since spring-boot-starter-web added Tomcat and Spring MVC, the auto-configuration assumes that you are developing a web application and sets up Spring accordingly.

## Custom Configuration with application.properties

```
........ Explicitly configure spring.jpa.open-in-view to disable this warning
........ Tomcat started on port(s): 4000 (http) with context path ''
```

For example, you can override the default 8080 port using:

```
server.port=4000
```

You can disable JPA warnings:

```
spring.jpa.open-in-view=false
```

Or customize the level of logging in your application:

```
logging.level.org.springframework=DEBUG
```

Spring Boot makes custom configuration easier with the application.properties file

## Distribution

Spring Boot provides an easy way to bundle your code and dependencies into a jar via the spring-boot-maven-plugin dependency.

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
    </plugin>
  </plugins>
</build>
```

## Conclusion

Now we should be able to:

- Describe the significance of dependencies like spring-boot-starter-web in your pom.xml
- Explain what SpringApplication does (responsible for starting up that container).
- Explain what @SpringBootApplication, and more specifically the underlying @EnableAutoConfiguration annotation, enables your application to do
- Identify when application.properties is used
- Explain the BUILD SUCCESS output occasionally shown in the Codecademy environment
