#SpringBoot Custom Endpoint demo

## step1 update mysql config
find file CustomEndpoint\demo\src\main\resources\application.properties
update mysql config
spring.datasource.url=jdbc:mysql://localhost:3306/user-center?useUnicode=true&characterEncoding=utf8&useSSL=false
spring.datasource.username=root
spring.datasource.password=123

## step2 start server
cd /CustomEndpoint
mvn install
1. cd \CustomEndpoint\demo\target
java -jar   xxxx.jar
2. cd \CustomEndpoint\demo
mvn spring-boot:run

## open browser
test custom endpoint:  
    http://localhost:8080/myentitys 
    {"user":{"name":"java.lang.String","id":"java.lang.Long"}}
                       
use swagger:
    http://localhost:8080/swagger-ui.html  