## MICROSERVICES PROJECT WITH SPRING BOOT
This project involves microservices and demonstrates the relations between them. Technologies used are: Rabbitmq, Elasticsearch, FeignClient, Spring Security, Docker. Postgresql database is used to save data.

## Mikroservisler Arası Data Gönderimi
- RabbitMq ile AuthMikroServis'ten UserMikroServis'e
data gönderilmektedir.
- FeignClient kullanarak UserMikroServis'ten ElasticsearchMikroServis'e
data gönderilmektedir.
---------------------------------------------------
## RabbitMQ Docker
Enables communication (data sending and receiving) between microservices with an enhanced queue structure.
Run this command on Windows Power Shell(here, username: user, password: root):
docker run -d --hostname my-rabbit --name some-rabbit -p 15672:15672 -p 5672:5672 -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=root rabbitmq:3-management

* localhost: 15672 --> write this on browser. This is RabbitMQ ui. Username: user, Password: root. On the 'Queues' tab you can see if any process is waiting on queue (regarding the data transfer between microservices).

## Docker Image Oluşturmak
docker build --build-arg JAR_FILE=Curriculum Pattern 2 (m.a.k.)/Intellij/a022_Microservice_mak/ConfigServerGit/build/libs/ConfigServerGit-v.0.1.jar -t javaboost2/java6configservergit:v.0.1 .  --> burda boşluktan sonra nokta var. Burdaki javaboost2 bizim hub.docker.com'da oturum açınca görebileceğimiz repo'muzun ismi (namespace'imiz). 'java6configservergit' -> projemizin docker hub'ımızdaki ismi. Sonra versiyonu yazıyoruz. ||| Çalışmazsa path'i çift tırnak ("") içine al.

docker build --build-arg JAR_FILE=Curriculum Pattern 2 (m.a.k.)/Intellij/a022_Microservice_mak/AuthMicroService/build/libs/AuthMicroService-v.0.1.jar -t javaboost2/java6auth:v.0.1 .

docker build --build-arg JAR_FILE=Curriculum Pattern 2 (m.a.k.)/Intellij/a022_Microservice_mak/ApiGatewayService/build/libs/ApiGatewayService-v.0.1.jar -t javaboost2/java6gateway:v.0.1 .

HER BİR MİKROSERVİS İÇİN YUKARIDAKİ BUILD KOMUTUNU ÇALIŞTIRMAMIZ GEREK.
(yukarıdaki komut terminal'de çalıştırılır; ve projemiz Docker Desktop'a gelir).
NOT: Yukardakilerden, örneğin ilk satırdaki komutta '='in sağ tarafındaki konumu şöyle elde ettik:
Öncelikle ConfigServerGit Gradle menüsünden build edilmiş olmalı.
Sonra, sol taraftaki dosya görüntüleyicisinden ConfigServerGit -> 
build -> libs -> ConfigServerGit-v.0.1.jar  dosyasına sağ tıkayıp 
'Copy Path/Reference' -> 'Path from repository root' tıklıyoruz.
Kopyaladığımız bu konumu '='in sağına yapıştırıyoruz.

NOT: M1 Chipset (Mac bilgisayar'ın) kullanıyorsanız imajlar sorun çıkartır;
bu nedenle platformun özellikle belirtilmesi gereklidir (--platform linux/amd64  şeklinde,aşağıdaki gibi):
docker build --platform linux/amd64 --build-arg JAR_FILE=Curriculum Pattern 2 (m.a.k.)/Intellij/a022_Microservice_mak/ConfigServerGit/build/libs/ConfigServerGit-v.0.1.jar -t javaboost2/java6configservergit:v.0.1 .
