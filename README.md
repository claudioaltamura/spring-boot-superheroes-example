[![Build Status](https://travis-ci.com/claudioaltamura/spring-boot-superheroes-example.svg?branch=master)](https://travis-ci.com/claudioaltamura/spring-boot-superheroes-example)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# spring-boot-superheroes-example
Spring Boot Superheroes example

## examples

find all
   
     curl -i http://localhost:8080/api/v1/superheroes

get one
    
    curl -i http://localhost:8080/api/v1/superheroes/1

## generate OpenAPI

    ./gradlew generateOpenApiDocs

## links

h2-console

    http://localhost:8080/h2-console

Healthcheck

    http://localhost:8080/actuator/health

Swagger-UI
        
    http://localhost:8080/swagger-ui.html

OpenAPI
    
    http://localhost:8080/api-docs
