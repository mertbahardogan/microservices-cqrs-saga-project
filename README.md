# Getting Started with Microservices Arch.

<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#">Technologies</a>
    </li>
  </ol>
</details>

## Built With

### Technologies
1. Java 
2. Spring Boot
3. Spring Cloud (Eureka & API Gateway)
4. CQRS Pattern
5. Saga Pattern
6. Axon Server
7. Google Guava
8. Admin Server

### Architectures
1. Microservices Architecture
2. Layered Architecture

### What is Saga Pattern?

Mikroservis mimarisinde her mikroservis için ayrı bir database kullanılıyorsa özellikle long running işlemlerde transaction yönetimini sağlamamız 
için Saga Pattern uygulanabilir. Transaction yönetimi, bütünlüğü, veri tutarlılığını (consistency) amaçlar.

Burada " " Saga Pattern uygulanmıştır. 

### What is CQRS(Command Query Responsibility Segregation)?

CQRS kullanmak ölçeklenebilirliği, performansı ve güvenliği artırmaya yardımcı olabilir. 

Command: Express the intent to change the application's state.
Query: Express the desire for information. 
Event: Represent a notification that something relevant has happened.

CQRS ise bu iki işlem tipini birbirinden "ayrıştırarak" farklı data modelleriyle gerçekleştirme prensibine denir.

Handlers: Uygulama üzerinde yapılacak her command ve query isteklerini işleyecek yapılara denir.
Aggregate: Her zaman tutarlı bir durumda tutulan varlık/varlıklar grubudur. Değişimin başladığı yerdir, business entitysi olarak düşünülebilir.

#### CQRS Naming Conventions
* Command -> (PerformedAction)(Noun)Command (CreateProductCommand)
* Event -> (Noun)(PerformedAction)Event (ProductCreatedEvent)
* Query -> (PerformedAction)(Noun)Query (FindProductQuery)

#### First Part: CRS Flow
CommandGateway(Send) → Aggregate → CommandHandler(Command) → EventSourcingHandler(Event) → EventHandler → Db

#### Second Part: QRS Flow
QueryGateway(Query) → QueryHandler → Db

### What is Event Sourcing? 
Sistemde gerçekleşmiş olayların (eventlerin) biriktirilmesi fikri üzerine çıkmıştır. CQRS’in bir parçası olarak kullanılabilir. Bir objenin son durumunu 
saklamak yerine, objenin durum değişikliğine neden olan tüm olaylarının kayıt altına alınmasıdır.

### What is Domain Driven Design (DDD)?
Wikipedia daki tanımı ‘Yazılım kodunun yapısının ve dilinin iş alanıyla eşleşmesi gerektiği kavramıdır. DDD, uygulamayı gelişen bir modele bağlar.’

### What is Axon Framework - Server?

"Axon Framework" DDD tekniklerini kullanarak CQRS, Saga gibi çözümleri uygulamamıza yarayan Java bazlı açık kaynak kodlu ve event driven mikroservis frameworküdür.
"Axon Server" ise Axon Framework'e entegre veya ayrı olarak kullabilen Event, Command mesajlarının birbiri arasında yönlendirilmesi, monitoring ve security gibi 
işlemleri daha hızlı yapabilmemizi sağlar.

### Google Guava

Collection nesnelerini birleştirmek, parçalamak için methodlara sahip sınıflar içeren bir collection kütüphanesidir.

### H2 Database

Java tabanlı bellekte çalışan bir SQL veritabanıdır. Sistemde gömülü veya sunucu-istemci çalışabilen ve kolayca
kurulabilen veritabanıdır.

### Axon Framework Informations

* EventSourcingHandler: Gönderilen eventin işleyicisini belirtmek için annotasyon? source tamamen bunla mı oluyor?

* CommandHandler: Gönderilen komutun işleyicisini belirtmek için annotasyon

* Projector Sınıfları: Axon Framework'te her bir event geldiği zaman DB işlemlerini yapacak sınıfları
tanımlamak için oluşturulan....

* EventHandler: Yaratılmış event geldikten sonra projector sınıfı içerisinde eventleri dinlemek için oluşturular metodların tepesine konulan annotasyon.

* QueryHandler: Gelen eventleri sorgulamada kullanılan annotasyon.

#### About Validation
* Bean Validation 

* @CommandHandler Validation

* Message Dispach Interceptor

* set based consistency validation
how to check if record already exists in a database table?
how to check if Product already exist?
how to check if User already exist?
  
Lookup DB: 

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
* [Product Service-Gateway](http://localhost:8088/swagger-ui/)
* [Product Database](http://localhost:RANDOM_PORT/api/v1/h2-console)


### Todo List  
* All project DEBUG
* Product Gateway URL does not work. IMPORTANT
localhost:8088/products-service/products /GET ile veriyi alabiliyor.
  
* Why we used Serializable and serialVersionUID in entity class?
* Why I did not see validation errors in aggregate class?
* What is the public void on class?
* AxonConfig (5): All events come again every starts.
* db-gateway-debug

### Resources

1. https://www.baeldung.com/spring-boot-admin
2. https://www.baeldung.com/axon-cqrs-event-sourcing
3. https://alperkiraz.medium.com/event-sourcing-nedir-4726c2a5f37c
4. https://metinalniacik.medium.com/lombok-k%C3%BCt%C3%BCphanesinde-builder-anotasyonu-639325cb8de7
5. https://medium.com/@bilgehan.yildiz/axon-framework-kullanarak-spring-boot-i%CC%87le-cqrs-pattern-i%CC%87mplementasyonu-part-1-85fc5e15acd8
6. https://alperkiraz.medium.com/event-sourcing-nedir-4726c2a5f37c
7. https://martinfowler.com/bliki/CQRS.html
8. https://blog.burakkutbay.com/spring-boot-h2-gomulu-veritabani-kullanmak.html/
9. http://localhost:8088/actuator/gateway/routes

### Other Examples

1. https://github.com/akiraz/springboot-eventsourcing

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