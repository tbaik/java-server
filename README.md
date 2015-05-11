# Java-Server

## To compile the code:

In the project directory:

```
mvn clean install
mvn clean package
```

## To run all tests:

In the project directory:

```
mvn clean test
```

## To start the Java server:

In the project directory:

```
java -jar target/java-server-1.0-SNAPSHOT.jar
```

server.jar takes in two optional arguments:

1. `-p` which specifies the port to listen on. Default is 5000.
2. `-d` which specifies the directory to serve files from. Default is /public

## To run the Java-server against [cob_spec](https://github.com/8thlight/cob_spec):

Setup cob_spec using the instructions on cob_spec's github repository [README](https://github.com/8thlight/cob_spec).

set SERVER_START_COMMAND to point to target/java-server-1.0-SNAPSHOT.jar

