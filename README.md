# Uygulama Kurulumu ve Çalıştırılması

## Gereksinimler
* Bilgisayarınızda uygun JDK'nin (Java Development Kit) yüklü olması gerekmektedir. JDK'nin uygun sürümünü [Oracle JDK indirme sayfası](https://www.oracle.com/java/technologies/downloads/#jdk21-windows) üzerinden edinebilirsiniz.

## Kurulum
1. JDK'yi bilgisayarınıza indirin ve yükleyin.
2. Uygulamayı GitHub'dan indirerek ana klasörünü bir dizine çıkartın.

## Çalıştırma
Uygulamanın ana klasöründeki **Report Application** aracılığıyla çalıştırabilir

Ya da

Uygulamayı masaüstünde çalıştırmak için aşağıdaki adımları gerçekleştirebirsiniz:

1. Masaüstünde uygulamanın kısayolunu oluşturmak için, ana klasöründeki **Report Application** uygulamasına sağ tıklayın.
2. Açılan menüden "**Kısayol Oluştur**" seçeneğini seçin.
3. Oluşturulan kısayolu masaüstüne sürükleyip bırakarak masaüstüne taşıyabilirsiniz.
4. Artık masaüstündeki kısayol ile uygulamayı çalıştırabilirsiniz.

**Not:** Uygulamada Cloud Database kullanımından kaynaklı internet bağlantısıyla ilgili gecikmeler ve veritabanı iletişim sorunları olabilir. Bu tür sorunlarla karşılaşıldığında, web sayfasını yenilemek genellikle sorunu çözebilir.

# Kullanıcı Yetkileri ve Sınırlandırmalar

#### Standart Kullanıcılar:
* Kayıt oluşturabilir ve güncelleyebilir.
* Silme işlemini gerçekleştiremez.

#### Yönetici:
* Tüm kayıtları silme ve güncelleme yetkisine sahiptir.

## Sınırlandırmalar
* **Standart kullanıcılar**, sadece kendi oluşturdukları kayıtları düzenleyebilir ve belirli kayıtlara erişebilir.
* **Yöneticiler**, tüm kayıtlara erişebilir ve tüm eylemleri gerçekleştirebilir. 

## Yönetici Giriş Bilgileri
* Kullanıcı Adı: admin
* Şifre: admin 

 Bu bilgilerle yönetici, uygulamaya giriş yapabilir ve tüm eylemleri gerçekleştirebilir.
<br>

# Kullanılan Teknolojiler 

## Spring Boot

Uygulama geliştirmeyi hızlandırmak ve kolaylaştırmak için Spring Boot kullanıldı. Spring Boot, otomatik yapılandırma ve sıfır yapılandırma ile geliştirme sürecini kolaylaştırır.

#### **JPA Repository**
Veritabanı işlemleri için JPA Repository kullanıldı. JPA Repository, veritabanı işlemlerini basitleştirmek ve kod tekrarını azaltmak için kullanılır.

#### **Lombok Annotations**
Lombok, Java sınıflarında tekrarlanan kodu azaltmak için kullanılan bir kütüphanedir. Getter, setter ve constructor gibi metodları otomatik olarak oluşturmak için Lombok annotations kullanıldı.

#### **ModelMapper**
ModelMapper, farklı sınıflar arasında alanları eşleştirmek için kullanılan bir kütüphanedir. Bu sayede veri transferi işlemleri daha kolay hale gelir.

#### **Swagger UI**
Swagger UI, API dokümantasyonunu oluşturmak ve API'yi test etmek için kullanılan bir araçtır. Swagger UI kullanılarak API'nin kolayca anlaşılabilir dokümantasyonu sağlandı.

#### **Unit Testler ve Mockito**
Unit testler yazmak için Mockito kütüphanesi kullanıldı. Mockito, Java'da mock nesneler oluşturmak ve testleri kolayca yazmak için kullanılan bir kütüphanedir. Bu sayede uygulamanın farklı parçaları izole edilerek test edilebilir hale gelir.

#### **JaCoCo**
JaCoCo, Java kodunun test kapsamını ölçmek için kullanılan bir araçtır. JaCoCo kullanılarak unit testlerin kapsamı kontrol edildi ve test edilmemiş kod blokları belirlendi.

## React
Kullanıcı arayüzü için React kullanıldı. React, bileşen tabanlı bir yapıya sahip olduğundan, yeniden kullanılabilir ve modüler bileşenler oluşturmayı kolaylaştırır. Ayrıca, sanal DOM kullanarak performansı artırır.

#### **Semantic UI**
Dizayn için Semantic UI kütüphanesi tercih edildi. Semantic UI, modern ve şık görünümlü kullanıcı arayüzleri oluşturmak için tasarlanmıştır.

#### **Redux**
Componentler arası iletişim için Redux kullanıldı. Redux, React uygulamalarında bileşenler arasında durum yönetimini kolaylaştırır ve uygulama genelinde tutarlı bir durum sağlar.

#### **Axios**
API iletişimi için Axios kütüphanesi kullanıldı. Axios, HTTP isteklerini yapmak ve almak için kullanılan popüler bir JavaScript kütüphanesidir.

## PostgreSQL
Veritabanı olarak PostgreSQL tercih edildi. PostgreSQL, açık kaynaklı olması ve geniş özellik seti sunması nedeniyle tercih edildi. Ayrıca geniş veri tipleri desteği ile güvenilir bir seçimdir.

#### **Cloud Veritabanı**
Cloud veritabanı olarak **Neon Tech**'ten yararlanıldı. Neon Tech, bulut tabanlı veritabanı hizmeti sunan bir sağlayıcıdır. Bu sayede uygulama, yüksek erişilebilirlik ve ölçeklenebilirlik avantajlarından faydalanabilir.

#### **Soft Delete**
Veritabanında soft delete işlemi uygulanmaktadır. Silinen veriler fiziksel olarak silinmez, ancak bir "isActive" alanının değeri değiştirilerek pasif hale getirilir. Bu sayede, silinen veriler daha sonra geri yüklenebilir veya kullanılabilir hale getirilebilir.
#### **Pageable**
Büyük veri kümeleri için performansı artırmak için Pageable tercih edilmiştir. Pageable, veritabanı sorgularında sayfa numaralandırması ve sıralama işlemlerini yönetmek için kullanılır ve büyük veri kümeleriyle daha etkili çalışır.

## Maven  
Maven, bağımlılıkları yönetmek ve proje yapılandırmasını kolaylaştırmak için kullanıldı. Maven, proje bağımlılıklarını ve yapılandırmasını XML tabanlı bir dosyada tanımlayarak proje yönetimini kolaylaştırır.

#### **frontend-maven-plugin**
frontend-maven-plugin kullanılarak proje içindeki frontend kaynakları (React uygulaması) yönetildi. Bu eklenti, frontend kaynaklarını derlemek, paketlemek ve yayınlamak için gerekli işlemleri otomatikleştirir. Bu sayede frontend geliştirme süreci Maven projesi içinde entegre edilmiş oldu.

<br>
<br>

# Application Setup and Operation
## Requirements
You must have the appropriate JDK (Java Development Kit) installed on your computer. You can obtain the appropriate version of the JDK from the [Oracle JDK download page](https://www.oracle.com/java/technologies/downloads/#jdk21-windows).

## Installation

1. Download and install the JDK on your computer.
2. Download the application from GitHub and extract its main folder to a directory.
## Run 
Can run through **Report Application** in the application's home folder

Or

You can perform the following steps to run the application on the desktop:

1. To create a shortcut to the application on the desktop, right-click the Report Application in its home folder.
2. Select "**Create Shortcut**" from the drop-down menu.
3. You can move the created shortcut to the desktop by dragging and dropping it to the desktop.
4. Now you can run the application with the shortcut on the desktop. 

**Note:** There may be delays related to internet connection and database communication problems due to the use of Cloud Database in the application. When such problems are encountered, refreshing the web page can usually solve the problem.

# User Authorizations
#### Standard Users:
* Can create and update records.
* Cannot perform deletion.
#### Administrator:
* Authorized to delete and update all records.
## Limitations
* **Standard users** can only edit records they have created and have access to specific records.
* **Administrators** can access all records and perform all actions.
## Administrator Login Information
* Username: admin
* Password: admin 

With this information, the administrator can log in to the application and perform all actions.

# Technologies
## Spring Boot
Spring Boot was used to speed up and simplify application development. Spring Boot simplifies the development process with automatic configuration and zero configuration.

#### **JPA Repository**
JPA Repository was used for database operations. JPA Repository is used to simplify database operations and reduce code repetition.

#### **Lombok Annotations**
Lombok is a library used to reduce repetitive code in Java classes. Lombok annotations were used to automatically generate methods such as getters, setters and constructors.

#### **ModelMapper**
ModelMapper is a library for mapping fields between different classes. This makes data transfer operations easier.

#### **Swagger UI**
Swagger UI is a tool for creating API documentation and testing the API. Easily understandable documentation of the API was provided using Swagger UI.

#### **Unit Tests and Mockito**
We used the Mockito library to write unit tests. Mockito is a library used in Java to create mock objects and write tests easily. In this way, different parts of the application can be isolated and tested.

#### **JaCoCo**
JaCoCo is a tool used to measure the test coverage of Java code. Using JaCoCo, the coverage of unit tests was checked and untested code blocks were identified.

## React
React was used for the user interface. React has a component-based structure, making it easy to create reusable and modular components. It also improves performance by using virtual DOM.

#### **Semantic UI**
Semantic UI library was preferred for the design. Semantic UI is designed to create modern and elegant looking user interfaces.

#### **Redux**
Redux was used for communication between components. Redux simplifies state management between components in React applications and ensures consistent state across the application.

#### **Axios**
Axios library was used for API communication. Axios is a popular JavaScript library for making and receiving HTTP requests.

## PostgreSQL
PostgreSQL was preferred as the database. PostgreSQL was preferred because it is open source and offers a wide feature set. It is also a reliable choice with support for a wide range of data types.

#### **Cloud Database**
Neon Tech was used as a cloud database. Neon Tech is a provider of cloud-based database services. This allows the application to benefit from high availability and scalability.

#### **Soft Delete**
A soft delete operation is applied in the database. Deleted data is not physically deleted, but made inactive by changing the value of an "isActive" field. This way, deleted data can be restored or made available later.

## Maven
Maven was used to manage dependencies and simplify project configuration. Maven simplifies project management by defining project dependencies and configuration in an XML-based file.

#### **frontend-maven-plugin**
Managed frontend resources (React application) within the project using frontend-maven-plugin. This plugin automates the process of compiling, packaging and publishing frontend resources. In this way, the frontend development process was integrated within the Maven project.
