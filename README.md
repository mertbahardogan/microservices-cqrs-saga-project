# Getting Started with Microservices Arch.

## Built With

### Technologies
1. Java 
2. Spring Boot
3. Spring Cloud (Eureka & Api Gateway)
4. CQRS Pattern
5. Saga Pattern
6. Axon Server
7. Google Guava
8. Admin Server

### Architectures
1. Microservices Architecture
2. Layered Architecture

### What is CQRS(Command Query Responsibility Segregation)?

Command: Veriyi oluşturan (insert) ya da veriyi manipüle eden(update,delete) işlemlerine "command" denir. Commandlar gelecekte reddebilecek veya kabul edilebilecek bir isteği temsil eder.

Query: Veriyi okumak (select) için yapılan işlemlere "query" denir.

CQRS ise bu iki işlem tipini birbirinden "ayrıştırarak" farklı data modelleriyle gerçekleştirme prensibine denir.


Handlers: Uygulama üzerinde yapılacak her command ve query isteklerini işleyecek yapılara denir.

Aggregate: Her zaman tutarlı bir durumda tutulan varlık/varlıklar grubudur.

CQRS tabanlı işlemlerde aggregateler değişimin başladığı yerdir. Kendi başlarına DDD'de nesneler bir araya gelerek iş akışını oluşturmamıza olanak sağlar.
Aggregate bir JPA entitysi değil, bir business entity veya entity kümesi olarak düşünebiliriz.

Events: Zaten olmuş ve geri alınamayacak (immutable) bir geçmişi temsil eder.

Bir eventin birden fazla tüketicisi olabilir. Ancak commandlar sadece bir tanesine yöneliktir.
CQRS çok fazla paydaşın olduğu verilerde geliştirme aşamalarını farklı takımlara bölmemize olanak sağlayarak her paydaşın kendi logici üzerinde çalışmasına olanak sağlayan bir yaklaşımdır.

### What is Domain Driven Design (DDD)?
Wikipedia daki tanımı ‘Yazılım kodunun yapısının ve dilinin iş alanıyla eşleşmesi gerektiği kavramıdır. DDD, uygulamayı gelişen bir modele bağlar.’

### What is Saga Pattern?

Microservis mimarinizde her microservis için ayrı bir database kullanıyorsanız bunun sonucu olarak özellikle long running işlemlerde transaction yöntemi olarak saga pattern implementasyonu yapmanız gerekecektir.

Saga pattern dağıtık mimarilerde veriler arası tutarlılık (consistency) sağlamak amacıyla bir hata yönetim patterni olarak karşımıza çıkmaktadır.

Saga Pattern implementasyonunda her local transaction bir event veya message başlatarak bir sonraki transactionın başlatılmasından sorumludur.

Hata durumu oluştuğunda saga çözümü sayesinde yazılmış olan compensation servisleri ile yapılmış transactionların rollback olması sağlanır.

Ancak unutulmaması gereken önemli bir nokta saga çözümleri dirty transaction oluşmasına açık bir konudur.

İki tip Saga Pattern yöntemi vardır.

Choregrapy Based Saga:Her bir transaction kendin sonraki transaction ve compensatiton senaryolarını tetiklemekten sorumludur.

Orchestaration Based Saga:Saga pattern için gerekli transaciton akışını yöneten bir koordinatör bulunmaktadır

Burada orchestration Based Saga patternini sağlamak amacıyla axon framework kullanıyor olacağız.

### What is Axon Framework - Server?

"Axon Framework" DDD tekniklerini kullanarak CQRS, Saga gibi çözümleri uygulamamıza yarayan Java bazlı mikroservis frameworküdür.
"Axon Server" ise Axon Framework'e entegre veya ayrı olarak kullabilen Event, Command mesajlarının birbiri arasında yönlendirilmesi,
monitoring ve security gibi işlemleri daha hızlı yapabilmemizi sağlar.

#### Terms
AggreagateLifeCycle: Bir aggreagate üzerinden event yayınlamak için kullanılır. Bir command execute edilirken uygulamanın geri kalanına yeni bir event oluştuğunu bildirir.
CommandGateway: Axonun komut işleme bileşenlerine yönelin bir arayüzdür. Command Gateway ile gönderilen komutların sonuçları beklenebilir.
QueryGateway: 

Axon Framwork event yapılarını takip edebilmek için db üzerinde tablolara ihtiyaç duyar. Bu tablolar bulunmazsa hata alınır.

Axon'un oluşturduğu tablolar:

SAGA_ENTRY :serialize edilmiş saga kayıtlarının tutulduğu tablo
ASSOCATION_VALUE_ENTRY:
TOKEN_ENTRY:
TrackingEventProcessor TrackingTokenı işlenen eventlerin durumunu izleme için kullanılır
TrackingToken eventstream üzerindeki eventin pozisyonunu temsil eder.
Tracking Tokenlar tokenstoreda saklanır.
TokenStore implementasyonu için jpa,jdbc vs gibi farklı implementasyonlarla saklanabilir
TokenEntry tablosu:Axon Framework her processing group için bir token tutar.

AggregateIdentifier: string,UUID ve primitive olmayan numericler kullanılabilir, örnek int seklinde identifier sequence hatasına yol açmaktadır.
EventSourcingHandler: Gönderilen eventin işleyicisini belirtmek için axon anotasyonu
· CommandHandler:Gönderilen komutun işleyicisini belirtmek için axon anotasyonu
· Projector sınıfları:Axon frameworkünde her bir event geldiği zaman db işlemlerini yapacak sınıfları tanımlamak için oluşturulan keywordün(mantıksal bir terminoloji)
· EventHandler:Yaratılmış event geldikten sonra projector sınıfı içerisinde eventleri dinlemek için oluşturulan methodların tepesine konulan axon anotasyonu

Kaynak: 
https://medium.com/@bilgehan.yildiz/axon-framework-kullanarak-spring-boot-i%CC%87le-cqrs-pattern-i%CC%87mplementasyonu-part-1-85fc5e15acd8#:~:text=Axon%20framework%20domain%20driven%20design,uyumlu%20%C3%A7al%C4%B1%C5%9Fabilen%20bir%20framework%20olmas%C4%B1d%C4%B1r.&text=Axon%20Framework%20ek%20olarak%20AxonServer%20birle%C5%9Fenide%20bulunmaktad%C4%B1r.
https://www.baeldung.com/axon-cqrs-event-sourcing

#### How to install Axon Server
-Run the following command:
docker run -d --name axonserver -p 8024:8024 -p 8124:8124 -v "W:\documents\axon-docker\data":/data -v "W:\documents\axon-docker\eventdata":/eventdata -v "W:\documents\axon-docker\config":/config axoniq/axonserver

-Add the following rows in local /config folder as file name "axonserver.properties":
server.port=8024
axoniq.axonserver.name=My Axon Server
axoniq.axonserver.hostname=localhost
axoniq.axonserver.devmode.enabled=true

-Address: 
http://localhost:8024/

### Resources
1. https://www.baeldung.com/spring-boot-admin

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
