<h1 align="center">FitBuddy</h1>

<div align="center">

[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=fitbuddy-app&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=fitbuddy-app)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=fitbuddy-app&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=fitbuddy-app)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=fitbuddy-app&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=fitbuddy-app)

[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=fitbuddy-app&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=fitbuddy-app)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=fitbuddy-app&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=fitbuddy-app)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fitbuddy-app&metric=coverage)](https://sonarcloud.io/summary/new_code?id=fitbuddy-app)

![Build](https://github.com/mepox/fitbuddy/actions/workflows/build.yml/badge.svg)

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

## Screenshots

<div align="center">

<img src="https://mepox.github.io/projects/fitbuddy/fitbuddy_login.jpg" width=200>
<img src="https://mepox.github.io/projects/fitbuddy/fitbuddy_register.jpg" width=200>
<img src="https://mepox.github.io/projects/fitbuddy/fitbuddy_history.jpg" width=200>
<img src="https://mepox.github.io/projects/fitbuddy/fitbuddy_exercises.jpg" width=200>

</div>
