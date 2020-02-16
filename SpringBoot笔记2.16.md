# 一. Spring Boot入门

## 1. Spring boot Helloworld 

一个功能

浏览器发送hello请求服务器接受请求并处理，响应Hello World字符串



#### 1. 创建一个maven工程：（jar）

#### 2.导入依赖spring boot相关的依赖

> ```xml
> <?xml version="1.0" encoding="UTF-8"?>
> <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
>     <modelVersion>4.0.0</modelVersion>
>     <parent>
>         <groupId>org.springframework.boot</groupId>
>         <artifactId>spring-boot-starter-parent</artifactId>
>         <version>2.2.4.RELEASE</version>
>         <relativePath/> <!-- lookup parent from repository -->
>     </parent>
>     <groupId>com.my</groupId>
>     <artifactId>spring-boot-01-helloworld</artifactId>
>     <version>0.0.1-SNAPSHOT</version>
>     <name>spring-boot-01-helloworld</name>
>     <description>Demo project for Spring Boot</description>
> 
>     <properties>
>         <java.version>9.0</java.version>
>     </properties>
> 
>     <dependencies>
>         <dependency>
>             <groupId>org.springframework.boot</groupId>
>             <artifactId>spring-boot-starter</artifactId>
>         </dependency>
> 
>         <dependency>
>             <groupId>org.springframework.boot</groupId>
>             <artifactId>spring-boot-starter-test</artifactId>
>             <scope>test</scope>
>             <exclusions>
>                 <exclusion>
>                     <groupId>org.junit.vintage</groupId>
>                     <artifactId>junit-vintage-engine</artifactId>
>                 </exclusion>
>             </exclusions>
>         </dependency>
>     </dependencies>
> 
>     <build>
>         <plugins>
>             <plugin>
>                 <groupId>org.springframework.boot</groupId>
>                 <artifactId>spring-boot-maven-plugin</artifactId>
>             </plugin>
>         </plugins>
>     </build>
> 
> </project>
> ```

#### 3. 编写主程序：启动Spring boot应用

> ```java
> package com.my;
> 
> import org.springframework.boot.SpringApplication;
> import org.springframework.boot.autoconfigure.SpringBootApplication;
> 
> /**
>  * @SpringBootApplication 来标注一个主程序类，说明这是一个Spring Boot应用
>  */
> @SpringBootApplication
> public class HelloWorldMainApplication {
>     public static void main(String[] args) {
> 
>         SpringApplication.run(HelloWorldMainApplication.class, args);
> 
>     }
> }
> ```

#### 4. 编写相关的Controller、Service

> ```java
> @Controller
> public class HelloController {
> 
>     @ResponseBody
>     @RequestMapping("/hello")
>     public String hello(){
>         return "Hello World!";
>     }
> }
> ```

#### 5. 运行主程序测试（启动Main函数）

#### 6.简化部署

在右边maven菜单中 --> Lifecycle --> package 即可打包成jar包



## 2. Hello World 探究

#### 1.POM文件

##### 1. 父项目

> ```xml
> <parent>
>     <groupId>org.springframework.boot</groupId>
>     <artifactId>spring-boot-starter-parent</artifactId>
>     <version>2.0.5.RELEASE</version>
>     <relativePath/> <!-- lookup parent from repository -->
> </parent>
> 
> 它的父项目是：spring-boot-dependencies 
> 这是真正来管理Spring Boot应用里面的所有依赖版本
> ```
>
> Spring Boot的版本仲裁中心，
>
> 以后导入依赖默认是不需要写版本（没有在dependencies里面管理的依赖就需要声明版本号）



##### 2. 导入的依赖（启动器）

> ```xml
> <dependency>
>     <groupId>org.springframework.boot</groupId>
>     <artifactId>spring-boot-starter-web</artifactId>
> </dependency>
> ```

**spring-boot-starter-*web*：**

​	spring-boot-starter：spring-boot场景启动器：帮我们导入了web模块正常运行所依赖的组件

Spring Boot将所有功能场景都抽取出来，做成一个个的starters（启动器），要用什么功能就用对应的场景启动器。



#### 2.主程序类，主入口类

> ```java
> /**
>  * @SpringBootApplication 来标注一个主程序类，说明这是一个Spring Boot应用
>  */
> 
> @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
> public class HelloWorldMainApplication {
>     public static void main(String[] args) {
> 
>         //参数为主函数类和args
>         SpringApplication.run(HelloWorldMainApplication.class, args);
> 
>     }
> }
> ```

@SpringBootApplication： Spring Boot应用标注在某个类上说明这个类是SpringBoot的主配置类，就应该运行这个类的main方法启动SpringBoot应用



> ```java
> @Target({ElementType.TYPE})
> @Retention(RetentionPolicy.RUNTIME)
> @Documented
> @Inherited
> @SpringBootConfiguration
> @EnableAutoConfiguration
> @ComponentScan(
>     excludeFilters = {@Filter(
>     type = FilterType.CUSTOM,
>     classes = {TypeExcludeFilter.class}
> ), @Filter(
>     type = FilterType.CUSTOM,
>     classes = {AutoConfigurationExcludeFilter.class}
> )}
> )
> ```

@**SpringBootConfiguration**: Spring boot 的配置类：

​		标注在某个类上，表示这是一个SpringBoot的配置类

​		@Configuration：配置类上来标注这个注解

​				配置 ······  配置文件：配置类也是容器中的一个组件



**@EnableAutoConfiguration**：开启自动配置功能

​		以前需要配置的东西，SpringBoot帮我们自动配置

> ```java
> @AutoConfigurationPackage
> @Import({AutoConfigurationImportSelector.class})
> public @interface EnableAutoConfiguration {
> ```

​		@**AutoConfigurationPackage**：自动配置包

​			@**Import**({Registrar.class})

​			Spring的底层注释@import，给容器中导入一个组件，导入的组件由Registrar.class

​			<u>将主配置类（@SpringBootApplication标注的类）所在的包下面所有子包里面的所有组件扫描到Spring容器中。</u>

​								@**Import**({AutoConfigurationImportSelector.class})：

​		给容器中导入组件：导入哪些组件的选择器

​		将所有需要导入的组件以全类名的方式返回：这些组件就会被添加到容器中。会给容器中导入非常多的自动配置类（xxxAutoConfiguration）：就是给容器中导入这个场景需要的所有组件，并且配置好这些组件。



J2EE的整体整合解决方案和自动配置都在spring-boot-autoconfigure-1.5.9.REALEASE.jar中



## 3. 使用Spring Initializer快速创建Spring Boot项目

使用Spring项目创建向导快速创建Spring Boot项目

选择我们需要的模块：向导会联网创建Spring Boot项目

默认生成的SpringBoot项目：

- 主程序已经生成好
- resources文件夹中目录结构：
  - static：保存所有的静态资源：js css images
  - templates:保存所有模板页面（Spring Boot默认jar包使用嵌入式的Tomcat，默认不支持JSP页面） 但可以使用模板引擎（freemarker, thymeleaf）
  - application.properties： Springboot应用的配置文件，可以修改一些默认配置



# 二. 配置文件

## 1. 配置文件

SpringBoot 使用一个全局的配置文件，配置文件名是固定的

- application.properties
- application.yml

配置文件的作用：修改SpringBoot自动配置的默认值；SpringBoot在底层都给我们自动配置好。



YAML（YAML ain‘t Markup Language）

​	它是一个标记语言又不是一个标记语言

标记语言：

​	以前的配置文件：大多都是.xml文件

​	YAML是以数据为中心，比json xml更适合配置文件

> ```yml
> server:
>   port: 8088
>   
> ```

## 2.YAML 语法：

### 1.基本语法

k: （空格）v    ： 表示一个键值对

以空格缩进控制层级关系：左对齐的一列数据为同一层级

> ```yml
> server:
>   port: 8088
>   path: /hello
> ```

属性和值对大小写敏感



### 2.值的写法：

**字面量：普通的值（数字、字符串、布尔）**

​	k: v -->字面量直接写

​				字符串默认不用加上单引号或者双引号

​				“ ”： 双引号： 不转义字符串里面的特殊字符

​				’ ‘： 单引号：  会转义特殊字符

**对象、Map(属性和值) （键值对）**

​		k: v :在下一行写对象的属性和值的关系（注意缩进）

​			对象还是键值对方式：

> ```yml
> friends:
>   lastName: zhangsan
>   age: 20
> ```

​		行内写法：

> ```yml
> friends: {lastName: zhangsan,age: 20}
> ```

**数组（List、Set）**

​	用-值表示数组中的一个元素

> ```yml
> pets:
>   - cat
>   - dog
>   - pig
> ```

​	行内写法：

> ```yml
> pets: [cat,dog,pig]
> ```



### 3.配置文件值注入：

配置文件（略）

##### 1. JavaBean：

> ```java
> /**
>  * 将配置文件中配置的每一个属性的值，映射到这个组件中
>  * @ConfigurationProperties: 告诉springboot将本类中的所有属性和配置文件中相关的配置进行绑定
>  *      prefix = "person"：在配置文件中哪属性下的所有属性进行一一映射
>  *
>  *
>  *      只有这个组件是容器中的组件，才能使用容器提供的功能
>  */
> @Component
> @ConfigurationProperties(prefix = "person")
> public class Person {
> 
>     private String lastName;
>     private Integer age;
>     private Boolean boss;
>     private Date birth;
> 
>     private Map<String, Object> maps;
>     private List<Object> lists;
>     private Dog dog;
> ```

我们可以导入配置文件处理器，以后编写配置就有提示了

> ```xml
> <dependency>
>     <groupId>org.springframework.boot</groupId>
>     <artifactId>spring-boot-configuration-processor</artifactId>
>     <optional>true</optional>
> </dependency>
> ```

##### 2. @Value获取值和@ConfigurationProperties获取值比较

代替@ConfigurationProperties(prefix = "person")的语句：

@Value（“”）

|                        | @ConfigurationProperties | @Value       |
| ---------------------- | ------------------------ | ------------ |
| 功能                   | 批量注入配置文件中的属性 | 一个一个指定 |
| 松散绑定（松散语法）   | 大写用-，小写用_         | 不支持       |
| 支持SpEL               | 不支持                   | 支持         |
| JSR303数据校验         | 支持                     | 不支持       |
| 复杂类型封装（如Maps） | 支持                     | 不支持       |

##### 3.数据校验：（注解必须是@ConfigurationProperties）

> ```java
> @Component
> @ConfigurationProperties(prefix = "person")
> @Validated //数据校验
> public class Person {
> 
>     @Email //要求必须是邮箱格式
>     private String lastName;
> ```



##### 4. @PropertySource和 @ImportResource

@PropertySource:加载指定的配置文件。

**@ImportResource:**导入Spring配置文件，让配置文件里面的内容生效。

SpringBoot里面没有Spring的配置文件，我们自己编写的配置文件也不能自动识别

想让Spring的配置文件生效加载进来，把**@ImportResource**标注在主配置类上

> ```java
> @ImportResource(locations = {"classpath:beans.xml"})
> ```
>
> 导入Spring配置文件让其生效

> ```xml
> <?xml version="1.0" encoding="UTF-8"?>
> <beans xmlns="http://www.springframework.org/schema/beans"
>        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
> 
> 
>     <bean id="helloService" class="com.my.springboot.service.HelloService"></bean>
> </beans>
> ```

SpringBoot推荐给容器中添加组件的方式：推荐使用全注解方式

1. 配置类==Spring配置文件
2. 使用@Bean给容器中添加组件

> ```java
> /**
>  * @Configuration指明当前类是一个配置类：来替代之前的Spring配置文件
>  * 在配置文件中使用<bean><bean/>标签添加组件
>  */
> 
> @Configuration
> public class MyAppConfig {
> 
>     //将方法返回值添加到容器中，容器中这个组件默认的id就是方法名
>     @Bean
>     public HelloService helloService(){
>         System.out.println("@Bean给容器加入组件了");
>         return new HelloService();
>     }
> 
> }
> ```



### 4. 配置文件占位符

##### 1.随机数

> ${random.value}, ${random.int}, ${random.long}, 
>
> ${random.int(10)}, ${random.int[1024, 65536]}



##### 2.占位符获取之前配置的值，如果没有则可以使用：指定默认值

> ```yml
> person:
>   lastName: 张三${random.uuid}
>   age: 18
>   boss: false
>   birth: 2020/2/9
>   maps: {k1: v1,k2: v2}
>   lists:
>     - lisi
>     - wanger
>   dog:
>     name: ${person.lastName}_hape
>     age: ${person.hello:1}2
> ```



### 5. Profile

Profile是Spring对不同环境提供不同配置功能的支持，可以通过激活、指定参数等方式快速切换环境

##### 1.多Profile文件

在主配置文件编写的时候，文件名可以是application-{profile}.properties/yml

默认使用application.properties/yml



##### 2.yml支持多文档块方式

> ```yml
> server:
>   port: 8088
>   path: /hello
> spring:
>   profiles:
>     active: dev
> ---
> server:
>   port: 8082
> spring:
>   profiles: dev
> ---
> server:
>   port: 8083
> spring:
>   profiles: prod
> 
> ---
> ```
>
> 两个---之间是一个document，对应一个代码块

##### 3.激活指定profile

​	1.在主配置文件中指定要激活哪个环境配置文件：

> ```yml
> spring:
>   profiles:
>     active: dev
> ```

 2. 命令行方式：--spring.profiles.active=dev

 3. 虚拟机参数VM options：

    -Dspring.profiles.active = dev



### 6. 配置文件加载位置

Spring boot启动会扫描一下位置的application.properties或者application.yml文件作为Springboot的默认配置文件

优先级从高到低：

**-file:./config/**

**-file:./**

**-classpath:/config/**

**-classpath:/**

**所有位置**的文件都会被加载（互补配置），**高优先级配置内容会覆盖低优先级配置内容。**

我们也可以通过**spring.config.location**来改变默认配置

项目打包好以后，可以使用命令行参数的形式，启动项目的时候来指定配置文件的新位置，指定配置文件和默认加载的这些配置文件共同起作用形成互补配置。





### 7.外部配置加载顺序（参见springboot官方文档）



### 8.自动配置的原理

配置文件到底能写什么？怎么写？自动配置原理



配置文件能配置的属性参照springboot官方文档：https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/html/appendix-application-properties.html#common-application-properties



##### **1.自动配置原理**：

1.SpringBoot启动的时候，加载主配置类，开启了自动配置功能@EnableAutoConfiguration

2.@EnableAutoConfiguration作用：

- 利用@AutoConfigurationImportSelector给容器中导入一些组件

- 可以查看**selectImports（）**方法的内容：

- List<String> configurations = this.getCandidateConfigurations(annotationMetadata, attributes);  获取候选的配置

  - ```java
    SpringFactoriesLoader.loadFactoryNames()
    扫描所有jar包类路径下的："META-INF/spring.factories"
    把扫描到的这些文件的内容包装成Properties对象
    从properties中获取到的EnableAutoConfiguration.class类名对应的值，把它们添加到容器中
    
    ```

    **将类路径下META-INF/spring.factories里面配置的所有EnableAutoConfiguration的值加入到容器中**

    > ```properties
    > # Auto Configure
    > org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
    > org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
    > org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
    > org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
    > org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
    > org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
    > org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
    > org.springframework.boot.autoconfigure.cloud.CloudServiceConnectorsAutoConfiguration,\
    > org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
    > org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
    > org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
    > org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRepositoriesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveRestClientAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration,\
    > org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration,\
    > org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration,\
    > org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration,\
    > org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
    > org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,\
    > org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
    > org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration,\
    > org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration,\
    > org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration,\
    > org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration,\
    > org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration,\
    > org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration,\
    > org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration,\
    > org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration,\
    > org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration,\
    > org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
    > org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
    > org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
    > org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration,\
    > org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration,\
    > org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
    > org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration,\
    > org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration,\
    > org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration,\
    > org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,\
    > org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration,\
    > org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration,\
    > org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration,\
    > org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration,\
    > org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration,\
    > org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration,\
    > org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration,\
    > org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration,\
    > org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
    > org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration,\
    > org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration,\
    > org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration,\
    > org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,\
    > org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration,\
    > org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration,\
    > org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,\
    > org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration,\
    > org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration,\
    > org.springframework.boot.autoconfigure.rsocket.RSocketRequesterAutoConfiguration,\
    > org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration,\
    > org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,\
    > org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,\
    > org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration,\
    > org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration,\
    > org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration,\
    > org.springframework.boot.autoconfigure.security.rsocket.RSocketSecurityAutoConfiguration,\
    > org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration,\
    > org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration,\
    > org.springframework.boot.autoconfigure.session.SessionAutoConfiguration,\
    > org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration,\
    > org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration,\
    > org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration,\
    > org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration,\
    > org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration,\
    > org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration,\
    > org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration,\
    > org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration,\
    > org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration,\
    > org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration,\
    > org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,\
    > org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration,\
    > org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration,\
    > org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration,\
    > org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration,\
    > org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration,\
    > org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration,\
    > org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration,\
    > org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration,\
    > org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration,\
    > org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration,\
    > org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration,\
    > org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration,\
    > org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration,\
    > org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration,\
    > org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration,\
    > org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration,\
    > org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration,\
    > org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration,\
    > org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration
    > ```

    每一个这样的xxxAutoConfiguration类都是容器中的一个组件，都加入到容器中，用它们来做自动配置。

3.每一个自动配置类进行自动配置功能（以**HttpEncodingAutoConfiguration**为例解释自动配置原理）

> ```java
> @Configuration(
>     proxyBeanMethods = false
> ) //表示是一个配置类，以前编写的配置文件一样，也可以给容器中添加组件
> @EnableConfigurationProperties({HttpProperties.class}) 
> //启用指定类的ConfigurationProperties功能：将配置文件中的值与HttpProperties绑定起来，并把HttpProperties加入到ioc容器中
> @ConditionalOnWebApplication(
>     type = Type.SERVLET
> )//Spring底层@Conditional注解，根据不同的条件进行判断。如果满足指定条件，整个配置类里面的配置就会生效；判断当前应用是否是web应用：如果是，当前配置类生效；反之无效
> @ConditionalOnClass({CharacterEncodingFilter.class})//判断当前项目有无这个类
> @ConditionalOnProperty(
>     prefix = "spring.http.encoding",
>     value = {"enabled"},
>     matchIfMissing = true
> )//判断配置文件中是否存在某个配置spring.http.encoding.enabled；如果不存在，判断也是成立
> public class HttpEncodingAutoConfiguration {
> ```

根据当前不同的条件判断，决定这个配置类是否生效，如果生效：

> ```java
> public class HttpEncodingAutoConfiguration {
>    
>     //已经和SpringBoot的配置文件映射
>     private final Encoding properties;
> 	
>     //只有一个有参构造器的情况下，参数就会从容器中拿
>     public HttpEncodingAutoConfiguration(HttpProperties properties) {
>         this.properties = properties.getEncoding();
>     }
> 
>     @Bean //给容器添加一个组件，这个组件的某些值需要从properties中获取
>     @ConditionalOnMissingBean
>     public CharacterEncodingFilter characterEncodingFilter() {
>         CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
>         filter.setEncoding(this.properties.getCharset().name());
>         filter.setForceRequestEncoding(this.properties.shouldForce(org.springframework.boot.autoconfigure.http.HttpProperties.Encoding.Type.REQUEST));
>         filter.setForceResponseEncoding(this.properties.shouldForce(org.springframework.boot.autoconfigure.http.HttpProperties.Encoding.Type.RESPONSE));
>         return filter;
>     }
> ```

​	一旦这个配置类生效，这个配置类就会给容器中添加各种组件；这些组件的属性是从对应的properties类中获取的，这些类里面的每一个属性又是和配置文件绑定的



4. 所有在配置文件中能配置的属性都是在xxxProperties类中封装着，配置文件能配置什么就可以参照某个功能对应的这个属性类

> ```java
> @ConfigurationProperties(
>     prefix = "spring.http"
> ) //从配置文件中获取指定的值和bean的属性进行绑定
> public class HttpProperties {
> ```



**SpringBoot精髓：**

​	**1.SpringBoot启动会加载大量的自动配置类**

​	**2.我们看我们需要的功能有没有SpringBoot默认写好的自动配置类**

​	**3.我们再来看这个自动配置类中到底配置了哪些组件（只要我们要用的组件有，我们就不在需要配置了）** 

​	**4.给容器中自动配置类添加组件的时候，会从properties类中获取某些属性。我们就可以在配置文件中指定这些属性的值。**



xxxAutoConfiguration：自动配置类：给容器中添加组件

xxxProperties：封装配置文件中相关属性。



##### 2.细节

**1.@Conditional派生注解**（Spring注解版原生的@Conditional作用）

作用：必须是@Conditional指定的条件成立，才给容器中添加组件，配置里面的所有内容才生效。

**@ConditionalOnBean**：仅仅在当前上下文中存在某个对象时，才会实例化一个Bean。
**@ConditionalOnClass**：某个class位于类路径上，才会实例化一个Bean。
**@ConditionalOnExpression**：当表达式为true的时候，才会实例化一个Bean。
**@ConditionalOnMissingBean**：仅仅在当前上下文中不存在某个对象时，才会实例化一个Bean。
**@ConditionalOnMissingClass**：某个class类路径上不存在的时候，才会实例化一个Bean。
**@ConditionalOnNotWebApplication**：不是web应用，才会实例化一个Bean。
**@ConditionalOnBean**：当容器中有指定Bean的条件下进行实例化。
**@ConditionalOnMissingBean**：当容器里没有指定Bean的条件下进行实例化。
**@ConditionalOnClass**：当classpath类路径下有指定类的条件下进行实例化。
**@ConditionalOnMissingClass**：当类路径下没有指定类的条件下进行实例化。
**@ConditionalOnWebApplication**：当项目是一个Web项目时进行实例化。
**@ConditionalOnNotWebApplication**：当项目不是一个Web项目时进行实例化。
**@ConditionalOnProperty**：当指定的属性有指定的值时进行实例化。
**@ConditionalOnExpression**：基于SpEL表达式的条件判断。
**@ConditionalOnJava**：当JVM版本为指定的版本范围时触发实例化。
**@ConditionalOnResource**：当类路径下有指定的资源时触发实例化。
**@ConditionalOnJndi**：在JNDI存在的条件下触发实例化。
**@ConditionalOnSingleCandidate**：当指定的Bean在容器中只有一个，或者有多个但是指定了首选的Bean时触发实例化。



**自动配置类必须在一定条件下生效**

我们怎么知道哪些自动配置类生效？

我可以通过

> ```properties
> debug=true
> ```

让控制台打印自动配置报告



# 三.Springboot日志

### 1.日志框架

开发一个大型系统：

1.System.out.println(""): 将关键数据打印在控制台。系统开发完要去掉。但是这个又很有用。

2.用框架来记录系统运行时的一些信息，由此产生一个日志框架。

3.高端的几个功能：异步模式记录；自动归档……

4.将2中的框架卸下，换上3中新的框架，要重新修改之前相关的API

5.可参照JDBC和数据库驱动的关系：

- ​	写一个统一的接口层——日志门面（日志的一个抽象层）:logging-abstract.jar
- ​	向项目中导入具体的日志实现

​	

市面上的日志框架：JUL, JCL, jboss-logging， logback， log4j， log4j2, slf4j

| 日志门面                              | 日志实现               |
| ------------------------------------- | ---------------------- |
| ~~JCL(jakarta Commons Logging)~~      | Log4j                  |
| SLF4j(Simple Logging Facade for java) | JUL(java.util.logging) |
| ~~jboss-logging~~                     | Log4j2                 |
|                                       | Logback                |

在表格第一列选择一个日志门面，第二列选择一个日志实现

**日志门面：SLF4j**

**日志实现：Logback**



SpringBoot：底层是Spring框架，Spring框架默认使用JCL

​						**SpringBoot选用SLF4j和logback**

### 2. SLF4j使用

#### 1如何在系统中使用SLF4j

开发时候，日志方法的调用，不应该来直接调用日志的实现类，而是调用日志抽象层里面的方法

应该给系统中导入SLF4j的jar包和 logback的实现jar包

> ```java
> import org.slf4j.Logger;
> import org.slf4j.LoggerFactory;
> 
> public class HelloWorld {
>   public static void main(String[] args) {
>     Logger logger = LoggerFactory.getLogger(HelloWorld.class);
>     logger.info("Hello World");
>   }
> }
> 
> ```



**图示**：

![img](http://www.slf4j.org/images/concrete-bindings.png)

每一个日志的实现框架都有自己的配置文件，使用slf4j以后，**配置文件还是做成日志实现框架的配置文件**



### 2. 遗留问题

a系统（slf4j+logback）：Spring（commons-logging）, Hibernate（jboss-logging）, Mybatis

各个框架的日志框架不同，因此要统一日志记录。即使是别的框架也要统一使用slf4j进行输出。

解决方法如图：

![img](http://www.slf4j.org/images/legacy.png)

如何让系统中所有的日志统一到slf4j：

**1.将系统中其他日志框架先排除出去**

**2.用中间包来替换原有的日志框架**

**3.导入slf4j其他的实现**



### 3. SpringBoot日志关系

> ```xml
> <dependency>
>   <groupId>org.springframework.boot</groupId>
>   <artifactId>spring-boot-starter</artifactId>
>   <version>2.2.4.RELEASE</version>
>   <scope>compile</scope>
> </dependency>
> ```

SpringBoot使用它来做日志功能：

> ```xml
> <dependency>
>   <groupId>org.springframework.boot</groupId>
>   <artifactId>spring-boot-starter-logging</artifactId>
>   <version>2.2.4.RELEASE</version>
>   <scope>compile</scope>
> </dependency>
> ```



​		底层依赖关系：

![image-20200216160636542](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20200216160636542.png)

​	总结：

​			1.SpringBoot底层也是使用slf4j+logback的方式进行日志记录

​			2.SpringBoot把其他的日志都替换成了slf4j

​			3.中间替换包

​			4.如果我们要引入其他框架，一定要把这个框架的默认日志依赖移除掉

​				

**SpringBoot能自动适配所有日志，而且底层使用slf4j + logback的方式记录日志，引入其他框架的时候，只需要把这个框架依赖的日志框架排除掉**

### 

### 4. 日志使用

#### 1.默认配置

SpringBoot默认帮我们配置好了日志，可以直接使用

> ```java
> //记录器
> Logger logger = LoggerFactory.getLogger(getClass());
> @Test
> void contextLoads() {
> 
>     //日志的级别：由低到高
>     //可以调整输出的日志级别，日志就只会在这个级别以后的高级别生效
>     logger.trace("这是trace日志");
>     logger.debug("这是debug日志");
> 
>     //SpringBoot默认给我们使用的是info级别的
>     logger.info("这是info日志");
>     logger.warn("这是warn日志");
>     logger.error("这是error日志");
> 
> }
> ```



> ```properties
> # 调整日志输出级别
> logging.level.com.my = trace
> 
> # 当前项目下生成spring.log日志文件
> # 不指定路径时在当前项目下生成
> # 指定路径时，在指定路径下生成
> logging.file.name  = springboot.log
> 
> # 在当前磁盘的根路径下创建spring文件夹和log文件夹，使用spring.log作为默认文件
> logging.file.path= /spring/log
> 
> # 在控制台输出日志的格式
> logging.pattern.console=
> # 指定文件中日志输出的格式
> logging.pattern.file=
> ```



#### 2. 指定配置

给类路径下放每个日志框架自己的配置文件即可。SpringBoot就不使用他默认配置了

| Logging System | Customization                                                |
| -------------- | ------------------------------------------------------------ |
| Logback        | logback-spring.xml, logback-spring.groovy, logback.xml, logback.groovy |
| Log4j2         | log4j2-spring.xml  log4j2.xml                                |
| JDK            | logging.properties                                           |

logback.xml: 直接就被日志框架识别了

logback-spring.xml:日志框架就不直接加载日志的配置项，由SpringBoot解析日志配置，可以使用springboot高级profile功能



#### 3.切换日志框架

可以按照slf4j的日志适配图，进行相关的切换，但不推荐