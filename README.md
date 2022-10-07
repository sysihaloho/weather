# weather-app

[![Build Status](https://travis-ci.org/codecentric/springboot-sample-app.svg?branch=master)](https://travis-ci.org/codecentric/springboot-sample-app)
[![Coverage Status](https://coveralls.io/repos/github/codecentric/springboot-sample-app/badge.svg?branch=master)](https://coveralls.io/github/codecentric/springboot-sample-app?branch=master)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Spring Boot based application for utilize 3rd party Current Weather Conditon

## Requirements

For building and running the application you need:

- [OpenJDK 19](https://openjdk.org/projects/jdk/19/)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `de.codecentric.springbootsample.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
## How the 3rd Party used

We use https://openweathermap.org/api
How to use it?
1. sign-up or register
2. login and fill information of company & reason to use API
3. You can go to https://home.openweathermap.org/api_keys to get your API_KEY
4. Copy API_KEY and place it in application.properties in field _project.api.key_
5. We use API in this page https://openweathermap.org/current
## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.
