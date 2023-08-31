# Getting Started with CQRS

### Technologies
1. Java 
2. Spring Boot
3. Spring Cloud (Eureka & API Gateway)
4. CQRS Pattern
5. Saga Pattern
6. Axon Server
7. Google Guava

### Architectures
1. Microservices Architecture
2. Command Query Responsibility Segregation
3. Layered Architecture

#### How to install Axon Server  
Run the following command:
  ```sh
   docker run -d --name axonserver -p 8024:8024 -p 8124:8124 -v "W:\documents\axon-docker\data":/data -v "W:\documents\axon-docker\eventdata":/eventdata -v "W:\documents\axon-docker\config":/config axoniq/axonserver
   ```

Add the following rows in local /config folder as file name "axonserver.properties":
 ```sh
server.port=8024
axoniq.axonserver.name=My Axon Server
axoniq.axonserver.hostname=localhost
axoniq.axonserver.devmode.enabled=true
   ```

### URL List

* [Axon Server](http://localhost:8024/)
* [Product Service-Swagger](http://localhost:RANDOM_PORT/api/v1/swagger-ui/)
* [Gateway-Swagger-Global](http://localhost:9000/swagger-ui.html)
* [Product Database](http://localhost:RANDOM_PORT/h2-console)

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


## Contact
### Would you like to any notify or suggest something to me?
mbahardogan4@gmail.com
