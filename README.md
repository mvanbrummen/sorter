# Sorter

## About
The sorter project is a console application. 

This application receives a text file, sorts the content in alphabetical order and generates a result in a text file


### Requirements:
The console app:
* Takes as a parameter a string that represents a text file containing a list of names.
* Orders the names by last name followed by first name.
* Creates a new text file called < input file name >-sorted.txt with the list of sorted

### For example
* if the input file contains:
```
BAKER, THEODORE
SMITH, ANDREW
KENT, MADISON
SMITH, FREDRICK
```

* Then the output file would be:
```
BAKER, THEODORE
KENT, MADISON
SMITH, ANDREW
SMITH, FREDRICK
```
Example of console execution:
```
sort-names c:\names.txt
BAKER, THEODORE
KENT, MADISON
SMITH, ANDREW
```
## Getting Started

### Prerequisites

* JDK 17
* Maven
* For Testing purposes, Please copy names.txt file (src\test\resources) and paste it in C:/temp/names.txt
* A text file containing a list of names following this formart: surname, first name
 ```
 BAKER, THEODORE
 ```
## Run the application

### Run it with Spring boot

 ```
 mvn spring-boot:run -f pom.xml -Dspring-boot.run.arguments="C:\temp\names.txt"
 ```

### Run it with java

 ```
 java -jar target/sorter-0.0.1-SNAPSHOT.jar C:\temp\names.txt
 ```

## Testing
* For testing. used Junit5(Mockito) for Unit tests and SpringBootTest for Integration tests. For Load testing I would use Gatling.


## To Do

* Add more Unit tests and Integration tests for Happy and Sad cases.
* Add Profiles 
* Add javadocs
* Add Load tests using GATLING so that the results will help tune/improve the current solution.
* Add check-styles, jacoco, etc. for test coverage for example. 
* Dockerize if needed.
