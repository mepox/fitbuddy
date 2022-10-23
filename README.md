# FitBuddy

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

## Overview

## Installation

## Running the application

## Live Preview

## Data Storage

It uses an in-memory (H2) database to store the data.

To view and query the database you can browse to /console, eg.: http://localhost:8080/console

Login details:

```
spring.datasource.url=jdbc:h2:mem:db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=admin
spring.datasource.password=admin
```

## Endpoints
