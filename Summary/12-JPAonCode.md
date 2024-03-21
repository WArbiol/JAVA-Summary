## First:

add on pom.xml:

```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

## Connecting to a Database

We'll use the embedded H2 database adding other dependency

```
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
  <scope>runtime</scope>
</dependency>
```

And tell Spring both where the database is stored, as well as what library it should use to “translate” our Java objects into SQL queries that H2 can understand:

On **src/main/resources/application.properties** adding:

```
spring.datasource.url=jdbc:h2:~/plants.db
spring.datasource.driverClassName=org.h2.Driver

      ⬇⬇⬇⬇⬇⬇⬇

server.port: 4001
spring.jpa.defer-datasource-initialization= true
spring.jpa.hibernate.ddl-auto=create
spring.datasource.initialization-mode=always

spring.datasource.url=jdbc:h2:~/plants.db
spring.datasource.driverClassName=org.h2.Driver
```

## Create a Model

a **P**lain **O**ld **J**ava **O**bject (a POJO) that represents a person, storing their attributes as fields of our POJO.

```java
public class Person {
  private Integer id;
  private String name;
  private Integer age;
  private String eyeColor;
}
```

With annotations from JPA that help define how our Java object will map to a database relation.

```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name="PEOPLE")
public class Person {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="NAME")
    private String name;

    @Column(name="AGE")
    private Integer age;

    @Column(name="EYE_COLOR")
    private String eyeColor;
}
```

These annotations come from the JPA library, which comes along with the `spring-boot-starter-data-jpa` dependency.

- `@Entity`: tells the ORM that this model will be used to represent a table or relation in our database
- `@Table`: tells the ORM what table name in the underlying database that this model corresponds to. Here, it is used to say that the Person entity represents a single entry in the "PEOPLE" table of the underlying database.
- `@Id`: tells the ORM that this field (id) will be used to uniquely identify a single entry in our "PEOPLE" relation
- `@GeneratedValue`: tells the ORM that the developer will not be supplying the value for this field themselves. Instead, it should be “auto-generated” by the database. Typically, an `@Id` field for an entity will be auto-generated in this way, so that we can leverage the database to guarantee that the ID will always be unique.
- `@Column`: tells the ORM what column in the underlying relation that the annotated field corresponds to. For example, the eyeColor field of our entity corresponds to the "EYE_COLOR" column in the "PEOPLE" relation.

[Others annotations](https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html)

**The ORM (object-relational mapping) will not be able to interact with your model without getters and setters!!!**

## Create a Repository

Determine how to use the model to interact with the database.
methods to call on instances of your data model like `.save()`, or `.findAll()`, or `.findById()`.

It should be able to **C**reate, **R**ead, **U**pdate, and **D**elete instances of the model. (CRUD)

Spring Data JPA comes with a special kind of repository interface that gives you full CRUD functionality for your model. ❤️❤️

Here’s an example of how the CrudRepository interface would work with the Person class:

```java
import org.springframework.data.repository.CrudRepository;

import com.codecademy.people.entities.Person;

// creating an extension of the CrudRepository that can manage our Person model
public interface PersonRepository extends CrudRepository<Person, Integer> {
  // no method declarations are required! }
```

We create a **new interface** that **extends the CrudRepository**, and **parameterize** it with our **Person model** and **the type of its ID field**, Integer.

        The angle brackets (< >) are a special kind of syntax used in Java to provide more specific type information to a class, using type parameters. You may have seen these used when we have a List of things in Java, like List<Integer>

Some methods that the CrudRepository:

- `.findById(Integer id)`: allows you to query the database to find an instance of your model by its ID field
- `.findAll()`: allows you to retrieve ALL the entries in the database for a given model
- `.save(Person p)`: allows you to create AND modify instances of your model in the database
- `.delete(Person p)`: allows you to delete instances of your model from the database

## Connect Your Repository to a Controller

Since the extension of the `CrudRepository` will be used in the “Spring context,” it is implicitly a bean.

For the `PersonRepository` example, we could have a `PersonController` that looks like:

```java
import org.springframework.web.bind.annotation.RestController;
import com.codecademy.people.repositories.PersonRepository;

@RestController
public class PersonController {
  private final PersonRepository personRepository;

  public PersonController(final PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  // ... all REST endpoint mappings
}
```

All that had to be done was:

- Import the `PersonRepository` from the correct package
- Add the `PersonRepository` as a field in the `PersonController` class
- Add the `PersonRepository` to the constructor and assign it to the field appropriately

## Get and Post

As simple as it is:

```java
  @GetMapping("/people")
  public Iterable<Person> getAllPeople() {
    return this.personRepository.findAll();
  }

  @GetMapping("/people/{id}")
  public Optional<Person> getPersonById(@PathVariable("id") Integer id) {
    return this.personRepository.findById(id);
  }

  @PostMapping("/people")
  public Person createNewPerson(@RequestBody Person person) {
    Person newPerson = this.personRepository.save(person);
    return newPerson;
  }
```

## Make an update

```java
  @PutMapping("/people/{id}")
  public Person updatePerson(@PathVariable("id") Integer id, @RequestBody Person p) {
    Optional<Person> personToUpdateOptional = this.personRepository.findById(id);
    if (!personToUpdateOptional.isPresent()) {
      return null;
    }

    // Since isPresent() was true, we can .get() the Person object out of the Optional <<<<------
    Person personToUpdate = personToUpdateOptional.get();

    if (p.getName() != null) {
      personToUpdate.setName(p.getName());
    }
    if (p.getAge() != null) {
      personToUpdate.setAge(p.getAge());
    }
    if (p.getEyeColor() != null) {
      personToUpdate.setEyeColor(p.getEyeColor());
    }

    Person updatedPerson = this.personRepository.save(personToUpdate);
    return updatedPerson;
  }
```

**VERY IMPORTANT:**`Person personToUpdate = personToUpdateOptional.get();`

## Delete

```java
  @DeleteMapping("/people/{id}")
  public Person deletePerson(@PathVariable("id") Integer id) {
    Optional<Person> personToDeleteOptional = this.personRepository.findById(id);
    if (!personToDeleteOptional.isPresent()) {
      return null;
    }
    Person personToDelete = personToDeleteOptional.get();
    this.personRepository.delete(personToDelete);
    return personToDelete;
  }
```

## **CUSTOM QUERIES**

For example, say we have a Person entity with a String eyeColor field, and we wish to query people that have a certain eyeColor:

```java
public interface PersonRepository extends CrudRepository<Person, Integer> {
  // this declaration is all we need!
  List<Person> findByEyeColor(String eyeColor);
}
```

If it is a boolean, donnot need parameters:

```java
public interface PlantRepository extends CrudRepository<Plant, Integer> {
  List<Plant> findByHasFruitTrue();
}

      ⬇⬇⬇⬇⬇⬇⬇

@GetMapping("/plants/search")
public List<Plant> searchPlants(@RequestParam(name="hasFruit", required=false) Boolean hasFruit) {
  if (hasFruit != null){
    if(hasFruit == true){
      return this.plantRepository.findByHasFruitTrue();
    }
  }
  return new ArrayList<>();
}
```

Spring Data JPA will automatically generate the “implementation” of the method when your application is compiled.

```java
@GetMapping("/people/search")
public List<Person> searchPeople(
  @RequestParam(name = "eyeColor", required = false) String eyeColor
) {
  if (eyeColor != null) {
    return this.personRepository.findByEyeColor(eyeColor) //HERE
  } else {
    return new ArrayList<>();
  }
}
```

## Advanced Custom Queries

We can filter by age:

```java
List<Person> findByAgeLessThan(Integer age);
```

And update:

```java
@GetMapping("/people/search")
public List<Person> searchPeople(
  @RequestParam(name = "eyeColor", required = false) String eyeColor,
  @RequestParam(name = "maxAge", required = false) Integer maxAge
) {
  if (eyeColor != null) {
    return this.personRepository.findByEyeColor(eyeColor)
  } else if (maxAge != null) {
    return this.personRepository.findByAgeLessThan(maxAge);
  } else {
    return new ArrayList<>();
  }
}
```

### Using `And` queries

```java
List<Person> findByEyeColorAndAgeLessThan(String eyeColor, Integer age);
```

```java
@GetMapping("/people/search")
public List<Person> searchPeople(
  @RequestParam(name = "eyeColor", required = false) String eyeColor,
  @RequestParam(name = "maxAge", required = false) Integer maxAge
) {
  if (eyeColor != null && maxAge != null) {
    return this.personRepository.findByEyeColorAndAgeLessThan(eyeColor, maxAge);
  } else if (eyeColor != null) {
    return this.personRepository.findByEyeColor(eyeColor);
  } else if (maxAge != null) {
    return this.personRepository.findByAgeLessThan(maxAge);
  } else {
    return new ArrayList<>();
  }
}
```

```java

```
