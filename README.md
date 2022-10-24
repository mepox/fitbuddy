<h1 align="center">FitBuddy</h1>

<div align="center">

[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=mepox_fitbuddy&metric=sqale_rating)](https://github.com/mepox/fitbuddy)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=mepox_fitbuddy&metric=reliability_rating)](https://github.com/mepox/fitbuddy)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mepox_fitbuddy&metric=coverage)](https://github.com/mepox/fitbuddy)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=mepox_fitbuddy&metric=ncloc)](https://github.com/mepox/fitbuddy)
[![mepox](https://circleci.com/gh/mepox/fitbuddy.svg?style=svg)](https://github.com/mepox/fitbuddy)

</div>

FitBuddy is a workout tracker app made with Spring Boot

## What's inside

The project uses the following technologies:

Backend:
- Java 11
- Spring Boot with Spring Security and Spring Data JPA
- RESTful API
- Lombok
- SQL
- Maven

Frontend:
- Bootstrap 5 with Bootstrap Icons
- HTML, CSS and JavaScript

## Features

- Users can register and login to the application.
- Users can create their custom exercises.
- Users can log their workout by adding their own custom exercises to a specific date.

## Installation

The project is created with Maven, so you just need to import it to your IDE and build the project to resolve the dependencies.

Maven Wrapper included in the project, you can start the app with: `mvnw spring-boot:run` without installing Maven.

Alternatively, you can start the project with Maven: `mvn spring-boot:run`

## Running the application

A default user with some preloaded data is added at the start of the application.

```
username: user
password: user
```

## Live demo

https://fitbuddy-demo.herokuapp.com/
