# Spring Data Options

## Understand the database technologies available with Spring

## Spring Data JPA and JDBC

Adding a database is easy to do with the help of a library called Spring Data JPA

### **JPA** stands for **Java Persistence API**

It translate objects from Java into queries that can be sent directly to the underlying database.

Spring Data JPA “wraps” around an implementation of the JPA to make it a **seamless process to integrate a database into your Spring Boot Application.**

JPA does this by wrapping around another standard, JDBC (**Java Database Connectivity**). This is the layer that sends raw database queries to the underlying database.

JPA is a standard that can be implemented by an ORM (object-relational mapping).

For example, instead of defining a database table in SQL…

```sql
CREATE TABLE plants (
  name varchar,
  type varchar,
);
```

…we can define a plain-old Java class:

```java
public class Plant {
  private string name;
  private string type;
}
```

And instead of writing SQL queries…

```sql
SELECT name
FROM plants
WHERE name='ficus';
```

```java
Plant ficusName = plantRepository.findByName('ficus').name;
```

## **Hibernate**, an ORM

It's the most commonly ORM used with Spring Data JPA, and it comes packaged along with Spring Data JPA as part of the dependency, spring-boot-starter-data-jpa.

## Database Types

Spring Data JPA supports many different kinds of databases right out of the box.

For example, we could start with a database like MySQL, but if we eventually change the database behind your application to be PostgreSQL instead, we won’t have to change anything about your application logic.

This convenience is all thanks to the JPA standard. As long as appropriate JDBC drivers exist for a given database.

## H2, a Database Type

H2 is a relational database written entirely in Java, and we can run entirely “in-memory”. This makes it easy to use to test your Spring Boot application on your machine, without having to obtain a database server from elsewhere for your application to use. S2

H2 can run alongside your application, running in the background when your application starts up. This is all made possible without the developer having to do any additional work to set it up.

When our Spring Boot application starts, it will check the properties file, located at **src/main/resources/application.properties**. This example adds a plants.db database, which might be used by a botanist to track their inventory.

```
spring.datasource.url=jdbc:h2:~/plants.db
spring.datasource.driverClassName=org.h2.Driver
```

## **Review**

- Spring Data JPA is available in the spring-boot-starter-data-jpa dependency
- Spring Data JPA is an implementation of the Java Persistence API, or JPA
- The object-relational mapping tool, or ORM, provided by Spring Data JPA is called Hibernate. It provides Java methods that can be translated into SQL queries:

```java
Plant ficusName = plantRepository.findByName('ficus').name;
```

- The standard that defines the relationship between Java code and SQL queries is called Java Database Connectivity, or JDBC.
- There are many different databases that Spring Data JPA can work with. H2 is one of those. It differs from the other databases in that it can run in-memory, meaning that developers don’t have to set up and connect to a separate database to get their app working.
- The database type is specified in application.properties
