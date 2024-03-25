## 1 Use Spring Initialzer to create the initial project

Go to start.spring.io and configure a Maven project targeting Java that includes the following dependencies:

- Spring Web
- Spring Data JPA
- H2 Database
- Lombok

Note the Java version that’s been pre-selected and be sure to install that corresponding JDK if you haven’t already.

Configure the Project Metadata as you wish. Accept the remaining defaults and click Generate. You will have a .zip file. Unzip the contents to a local folder.
See the article [Spring Project Layout and Running Locally](https://www.codecademy.com/paths/create-rest-apis-with-spring-and-java/tracks/spring-apis-web-and-spring-basics/modules/how-spring-works/articles/spring-project-layout-and-running-locally).

## 2 Setup your project structure

[docs](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.structuring-your-code)

- controller/
- model/
- repository/

## 3 Configure the H2 database to persist locally

Configure the H2 embedded database to persist to a local file by updating your project’s application properties. See the [“Embedded” examples found here](http://www.h2database.com/html/cheatSheet.html).

Within your project’s application.properties file, you’ll need to define spring.datasource.url. Its value will be based on an H2 file url.

Also visit the section on Connecting to a Database in the lesson [Add a Database with JPA](https://www.codecademy.com/paths/create-rest-apis-with-spring-and-java/tracks/spring-apis-data-with-jpa/modules/spring-data-and-jpa/lessons/add-a-database-with-jpa).

## 4 Configure Hibernate to always drop the database

Spring Data JPA offers a config setting, spring.jpa.hibernate.ddl-auto, that you can conveniently set. The possible values are borrowed from Hibernate. See [Hibernate’s documentation for all the possible values](https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#configurations-hbmddl).

## 5 Restaurant entity

Our restaurant dining review app scenario revolves around the following basic concepts:

- a restaurant
- a dining review
- a user

A restaurant will have a set of review scores based on those submitted by users. On a scale of 1 to 5, with 5 being the best, a restaurant will have individual scores for the following food allergies:

- peanut
- egg
- dairy

Each individual score will be the average across all the submitted scores for that given category. In the absence of any submitted score, an individual category score will have a null value.

A restaurant will also have an overall score, which will be the average across all the submitted scores across all the categories. A user can later search for restaurants based on these scores.

Define a plain ol’ Java object (POJO) class to represent a restaurant. Be sure to include the property that represents the unique database id, which you can define as type Long.

## 6 Use Lombok to help implement entities

In a different task, you had to define a POJO class to represent a restaurant. You normally would have to define both the constructors as well as any setters and getters for the individual properties. Lombok helps you save yourself from having to write all that code. Instead, you apply specific Lombok annotations that will auto-generate that code for you.

Apply the appropriate Lombok annotations that will auto-generate the constructors, getters and setters for your restaurant entity class.

Look at [Lombok’s documentation](https://projectlombok.org/features/all). Although there are a number of features, you mainly want to focus on auto-generating the constructors and the setters and getters.

## 7 Define the dining review entity

A dining review consists of the following info:

- who submitted, represented by their unique display name (String)
- the restaurant, represented by its Id (Long)
- an optional peanut score, on a scale of 1-5
- an optional egg score, on a scale of 1-5
- an optional dairy score, on a scale of 1-5
- an optional commentary

Define a plain ol’ Java object (POJO) class to represent a dining review. Be sure to include the property that represents the unique database `id`, which you can define as type `Long`. Apply the Lombok annotations to save you from writing more code than necessary.

## 8 Define the user entity

A user consists of the following info:

- their display name, one that’s unique to only that user
- city
- state
- zipcode
- whether they’re interested in peanut allergies
- whether they’re interested in egg allergies
- whether they’re interested in dairy allergies

Define a plain ol’ Java object (POJO) class to represent a user. Be sure to include the property that represents the unique database id, which you can define as type Long. Apply the Lombok annotations to save you from writing more code than necessary.

## 9 A data model for an admin review action

Once a user submits their review, an administrator – one who’s authorized to review and approve a dining review – will take action by either accepting or rejecting the dining review. Later in the project, you will define an API endpoint that will handle this administrator scenario.

Define a plain ol’ Java object (POJO) that represents an “admin review action”. A representation can simply consist of:

does the admin accept the dining review
Apply the Lombok annotations to save you from writing more code than necessary.

(You have no need to apply any Spring Data JPA annotations here because this will not be persisted to the database. This model is needed only as part of the API contract.)

## 10 Add “status” to your dining review entity

In a different task, we introduced the administrator scenario where the admin will need to approve or reject a user’s submission. You will need to represent that status as part of the dining review. That way, you will be able to determine which dining reviews are pending, and which ones have already been accepted or rejected.

Extend your existing dining review entity to include this notion of status.

Consider defining the status as an enumeration. For more info, see [Java enum types](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html).

## 11 Define the data repositories

User entity-related scenarios:

- As an unregistered user, I want to create my user profile using a display name that’s unique only to me.
- As a registered user, I want to update my user profile. I cannot modify my unique display name.
- As an application experience, I want to fetch the user profile belonging to a given display name.
- As part of the backend process that validates a user-submitted dining review, I want to verify that the user exists, based on the user display name associated with the dining review.

Dining review entity-related scenarios:

- As a registered user, I want to submit a dining review.
- As an admin, I want to get the list of all dining reviews that are pending approval.
- As an admin, I want to approve or reject a given dining review.
- As part of the backend process that updates a restaurant’s set of scores, I want to fetch the set of all approved dining reviews belonging to this restaurant.

Restaurant entity-related scenarios:

- As an application experience, I want to submit a new restaurant entry. Should a restaurant with the same name and with the same zip code already exist, I will see a failure.
- As an application experience, I want to fetch the details of a restaurant, given its unique Id.
- As an application experience, I want to fetch restaurants that match a given zip code and that also have at least one user-submitted score for a given allergy. I want to see them sorted in descending order.

See [Spring’s documentation for JPA query methods](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html#jpa.query-methods.query-creation).

Don’t feel compelled to implement all the repositories at once. You can also start with implementing and testing a specific API scenario, and then once done, going back and repeating for another API scenario.

## 12 Define the API endpoints

Let’s first adopt some API contract requirements. This will help you finalize the API endpoints and their expected behaviors.
**API contract requirements:**

Requests:

- A user will always be referenced by their unique display name, not by a database Id.
- A restaurant will always be referenced by its database Id.
- A dining review will always be referenced by its database Id.
- Any API scenarios that involve creating a new entity or updating an existing one will require the client to send the entity info as part of the request body.
- Restaurant search scenario: To support searching for a restaurant, use query params to represent the zip code and a specific allergy.
- Admin scenario: The APIs specific to the admin scenario should live under a specific “admin” path.

Validation:

- APIs should validate inputs. This may include validating the existence of other entities that may be related to the API operation. For instance, for the dining review scenario, validate that the given restaurant exists.

Responses:

- Any API scenarios that involve returning an entity will return them as a JSON response.
- Return an appropriate HTTP status code for any failures, including cases where the request fails validation.
- When returning an entity as a JSON response, if a value does not exist for a given property, do not send back a null value for that property. Instead, do not send back the property at all. For example, when returning a restaurant entity, if the restaurant does not have a peanut score, then do not include the peanut field as part of the JSON response.
- Restaurant scenario: When returning a restaurant entity with its scores, represent the individual scores to two decimal places, e.g. 4.75.
- Admin approval scenario: Upon approval of a dining review, re-compute the restaurant’s latest scores across all the approved dining reviews for that

### Hint

Consider defining different API endpoint paths based on your three entities.

Review the [various HTTP status codes](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes) and consider which ones you want to propose for your different error cases.

For formatting an Integer value, see Java’s [DecimalFormat](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/text/DecimalFormat.html).

Determine how you might validate some of the input values, for example, validating a U.S. zip code. One approach is to apply a regular expression.

To enforce the API response not including a field that has a null value, see the documentation for Jackson, which is the Java JSON library. Specifically, you can update the **application.properties** file to configure Jackson to include only non-null property values when serializing an object to JSON. Search for `default-property-inclusion` in the [Spring Boot repository](https://github.com/spring-projects/spring-boot/blob/65c00f373a5520bac27a7b9a2fc99535189f5756/spring-boot-project/spring-boot-docs/src/docs/asciidoc/howto/spring-mvc.adoc).

Don’t feel compelled to implement all the API endpoints at once. You can also start with implementing and testing a specific API scenario, and then once done, going back and repeating for another API scenario.

## 13 Build/run your Spring app, and test your APIs

At this point, you’ve implemented at least one API scenario. Build and run your app. Use cURL to test your API and its behavior.

Rinse and repeat!

For tips on building/running your Spring app and on using cURL, see the lessons [Spring Project Layout and Running Locally](https://www.codecademy.com/paths/create-rest-apis-with-spring-and-java/tracks/spring-apis-web-and-spring-basics/modules/how-spring-works/articles/spring-project-layout-and-running-locally) and [What is curl](https://www.codecademy.com/paths/create-rest-apis-with-spring-and-java/tracks/spring-apis-web-and-spring-basics/modules/how-spring-works/articles/what-is-curl-article)?.

If you run into issues building, double-check that the JDK version you have installed matches the version selected when you generated the project. Also, check that the JAVA_HOME environment variable points to that version’s installation folder.
