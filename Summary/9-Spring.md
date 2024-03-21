# Back-end

It is the technology required to process the incoming request and generate and send the response to the client. This typically includes three major parts:

- **The server**: This is the computer that listens for incoming requests.
- **The app**: This is the application running on the server that listens for requests (HTTP on URI), retrieves information from the database, and sends a response.
- **The database**: Databases are used to organize and persist data.

# Spring Project

    mySpringProject/
    ├── mySpringProject.iml
    ├── pom.xml
    ├── HELP.md
    ├── mvnw.cmd
    ├── mvnw
    └── src/
        ├── test
        └── main/
            ├── resources/
            │   ├── application.properties
            │   ├── static
            │   └── templates
            └── java/
                └── com/
                    └── example/
                        └── mySpringProject/
                            └── MySpringProjectApplication.java

## pom.xml

- POM stands for **Project Object Model** – an XML file that specifies and loads important project data, such as configuration, build profiles, and dependencies. All Maven projects are built using its own POM.

## application.properties

- Specifies the properties of your Spring application during project build. Examples of application properties can include JSON properties, data properties, server properties, and more. A Spring application will read and load properties specified in application.properties during project build.

## [ProjectName]Application.java

- Under the folder src/main/java/com/example/mySpringProject
- Contains the project’s main method, the entry point that allows a JVM (Java Virtual Machine) to execute the Java code.

## To Run Project

- On terminal at the bottom and type `./mvnw spring-boot:run`

# Mapping Requests

## @RestController

- Annotation used to declare a class as a controller that can provide application-specific types of data as part of an HTTP response.
- It combines the functionality of two separate annotations, **@Controller** and **@ResponseBody**:
  - **@Controller** is used to make a class identifiable to Spring as a **component of the web application**.
  - **@ResponseBody** tells Spring to **convert** the return values of a controller’s methods **to JSON** and **bind** that JSON **to the HTTP response body**.

## @RequestMapping

- **path** argument. `@RequestMapping(path = "/book")` and that method would provide the response.
- **method**: allows the developer to specify which HTTP method should be used when accessing the controller method. The most common are `RequestMethod.GET`, ...`POST`, ...`PUT`, and ...`DELETE`. Since this is an optional argument, if we do not specify an HTTP method it will be defaulted to GET.
- **params**: filters requests based on given parameters.
- **consumes**: used to specify which media type can be consumed; some common media types are `"text/plain"`, `"application/JSON"`, etc.

**_WE NEED TO IMPORT THE RestController AND RequestMapping_**

```java
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class SuperHeroController {

  private final SuperHeroRepository superHeroRepository;
  private final SuperReportRepository superReportRepository;

  public SuperHeroController(SuperHeroRepository superHeroRepository, SuperReportRepository superReportRepository) {
    this.superHeroRepository = superHeroRepository;
    this.superReportRepository = superReportRepository;
  }

  @RequestMapping("/superHeroes") // It is Get by default
  public Iterable<SuperHero> getSuperHeros() {
    Iterable<SuperHero> superHeroes = superHeroRepository.findAll();
    return superHeroes;
  }

}

```

## Base Paths

We can use the @RequestMapping data annotation at the **class level**, the specified path argument will become the **base path**.

The getBookThumbnails method is associated with the endpoint “/books/thumbnails”:

```java
@RestController
@RequestMapping("/books")
public class VirtualLibrary
{
  @RequestMapping(value = "/thumbnails", method = RequestMethod.GET)
  public String[] getBookThumbnails() {
    //returns thumbnails for all available titles
  }
}
```

## HTTP Method Annotations

```java
@RequestMapping("/about", method = RequestMethod.GET)
```

…is equivalent to…

```java
@GetMapping("/about")
```

| HTTP Method Annotation | Usage                                                          |
| ---------------------- | -------------------------------------------------------------- |
| `@GetMapping`          | Used to specify GET requests to retrieve resources             |
| `@PostMapping`         | Used to specify POST requests to create new resources          |
| `@PutMapping`          | Used to specify PUT requests to update existing resources      |
| `@DeleteMapping`       | Used to specify DELETE requests to remove specific resources ` |

## Query Parameters

let’s say the user submits their request for a book with 2 authors and a publishing year of 1995.

`libraryapp.com/book?author_count=2&published_year=1995`

```java
import org.springframework.web.bind.annotation.RequestParam;
(...)
@GetMapping("/book")
public Book isBookAvailable(@RequestParam int author_count, @RequestParam int published_year) {
  return books.find(author_count, published_year);
}
```

## Template Path Variables

template path: `/books/{id}` to use the URI `localhost:4001/books/28937`

### Defining the **path variable**:

```java
@GetMapping("/{id}")
public Book isBookAvailable(@PathVariable int id)  {
  return book.findByID(id);
}
```

## Deserializing into **Objects**

We may need to receive an **entire form object**.

We can use the `@RequestBody` annotation on the parameters of a method.

```java
class Book {
  public int authorCount;
  public int publishedYear;
}

      ⬇⬇⬇⬇⬇⬇⬇

@GetMapping("/book")
public Book isBookAvailable(@RequestBody Book book) {
  return book.find(book.authorCount, book.publishedYear);
}
```

The request would need to have authorCount and publishedYear in its body (rather than the previous URL query parameters `?author_count=2&published_year=1995`):

```
curl -X POST --data
  '{\"authorCount\": \"2\", \"publishedYear\": \"1995\"}' "https://localhost:8080/.../book"
```

### **Saving a object**:

```java
  @PostMapping(path="/addNew")
  public String createNewSuperHero(@RequestBody SuperHero newSuperHero) {
    superHeroRepository.save(newSuperHero);
    return "New Super Hero successfully added!";
  }
```

## Fine-tune the HTTP response

### **@ResponseStatus** annotation

Adjust the HTTP response status to a method:

```java
  @PostMapping(path = "/book")
  @ResponseStatus (code = HttpStatus.CREATED)
  public String addNewBook(@Request Param string title) {
    return "Book added successfully!";
  }
```

## Exception Handling

We use this notation:

`throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hero on this postal code");`:

```java
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@GetMapping("/{id}")
public Book isBookAvailable(@PathVariable string id)
{
  if (id.isNumeric()) {
    int id_int = Integer.parseInt(id)
    return book.findByID(id_int)
  }
  else {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The ID contained a non-numerical value.");
  }
}
```

## **Review**

- Map HTTP requests to controllers and methods (`@RestController` and `@RequestMapping`)
- Specify a path attribute to become a base path (`@RequestMapping` at the class level)
- Declare request types using HTTP method annotations (`@GetMapping`, `@PostMapping`, `@PutMapping`, and `@DeleteMapping`)
- Access request parameters in a method (`@RequestParam`)
- Bind data using template variables (`@PathVariable`)
- Fine-tune the status code returned by a method (`@ResponseStatus`)

### _Good imports:_

```java
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
```
