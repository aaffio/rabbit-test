# rabbit-test
Spring boot web with rabbitmq example

### Pre requisites
Install
- java jdk 8
- apache-maven-3.2.2
- rabbitmq
- mysql

### Requirements
- run your rabbitmq locally
- configure mysql in application.properties

### Building
```bash
mvn clean package
```

### Running 
```bash
mvn spring-boot:run 
```

### Testing
```bash
mvn test
```

## How to
* Install rabbitmq locally  
<pre>https://www.rabbitmq.com/download.html</pre>
* Access swagger ui  
<pre>http://localhost:8083/swagger-ui.html</pre>

### Disclaimer
- this is a simple example of common usage rabbitmq with springboot 

### Example
* you will see initial send and receive message as simple message with rabbit mq:
<pre>Sending message...
Received Hello from RabbitMQ!</pre>
* save your book in PUT /book (it's idempoten, just put null as "id")
* get your book with id and async GET /book/async/{id}