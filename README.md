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

## Run the application

### Run it with a Spring boot

 ```
 mvn spring-boot:run -f pom.xml -Dspring-boot.run.arguments="C:\gdva\names.txt C:\gdva\my-file-sorted.txt"
 ```

### Run it with a Spring boot

 ```
 java -jar target/sorter-0.0.1-SNAPSHOT.jar C:\gdva\names.txt,C:\gdva\my-file-sorted.txt
 ```
