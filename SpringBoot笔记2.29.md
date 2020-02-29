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



# 四、Web开发

### 1.使用SpringBoot：

1. **创建SpringBoot应用，选中我们需要的模块**
2. **SpringBoot已经默认将这些模块配置好**
3. **自己编写业务代码**



### 2. SpringBoot对静态资源的映射规则

```java
@ConfigurationProperties(
    prefix = "spring.resources",
    ignoreUnknownFields = false
)
public class ResourceProperties {
    //可以设置和静态资源有关的参数
```

> ```java
> public void addResourceHandlers(ResourceHandlerRegistry registry) {
>     if (!this.resourceProperties.isAddMappings()) {
>         logger.debug("Default resource handling disabled");
>     } else {
>         Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
>         CacheControl cacheControl = this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
>         if (!registry.hasMappingForPattern("/webjars/**")) {
>             this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"}).setCachePeriod(this.getSeconds(cachePeriod)).setCacheControl(cacheControl));
>         }
> 
>         String staticPathPattern = this.mvcProperties.getStaticPathPattern();
>         if (!registry.hasMappingForPattern(staticPathPattern)) {
>             this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{staticPathPattern}).addResourceLocations(WebMvcAutoConfiguration.getResourceLocations(this.resourceProperties.getStaticLocations())).setCachePeriod(this.getSeconds(cachePeriod)).setCacheControl(cacheControl));
>         }
> 
>     }
> }
> ```

1.**所有/webjars/**的访问**，都去classpath:/META-INF/resources/webjars/中找资源

​	webjars：以jar包的方式引入静态资源 参考：https://www.webjars.org/



2.**/**访问当前项目的任何资源**

> ```java
> "classpath:/META-INF/resources/", 
> "classpath:/resources/", 
> "classpath:/static/", 
> "classpath:/public/"，
> "/":当前资源的根路径
> ```



3. **欢迎页**：静态资源文件夹下的所有index.html， 被"/**"映射

   > ```java
   > @Bean
   > public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext applicationContext, FormattingConversionService mvcConversionService, ResourceUrlProvider mvcResourceUrlProvider) {
   >     WelcomePageHandlerMapping welcomePageHandlerMapping = new WelcomePageHandlerMapping(new TemplateAvailabilityProviders(applicationContext), applicationContext, this.getWelcomePage(), this.mvcProperties.getStaticPathPattern());
   >     welcomePageHandlerMapping.setInterceptors(this.getInterceptors(mvcConversionService, mvcResourceUrlProvider));
   >     return welcomePageHandlerMapping;
   > }
   > ```

   localhost:8080 --> 找index.html首页

### 3.模板引擎

JSP、Velocity、Freemarker、Thymeleaf

![image-20200217155837769](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20200217155837769.png)



**SpringBoot推荐的Thymeleaf**（语法简单，功能强大）

##### 1. 引入Thymeleaf：

> ```xml
> <dependency>
>     <groupId>org.springframework.boot</groupId>
>     <artifactId>spring-boot-starter-thymeleaf</artifactId>
> </dependency>
> ```



##### 2.Thymeleaf使用&语法

> ```java
> ConfigurationProperties(
>     prefix = "spring.thymeleaf"
> )
> public class ThymeleafProperties {
>     private static final Charset DEFAULT_ENCODING;
>     public static final String DEFAULT_PREFIX = "classpath:/templates/";
>     public static final String DEFAULT_SUFFIX = ".html";
>     private boolean checkTemplate = true;
>     private boolean checkTemplateLocation = true;
>     private String prefix = "classpath:/templates/";
>     private String suffix = ".html";
>     private String mode = "HTML";
>     private Charset encoding;
>     private boolean cache;
>     private Integer templateResolverOrder;
>     private String[] viewNames;
>     private String[] excludedViewNames;
>     private boolean enableSpringElCompiler;
>     private boolean renderHiddenMarkersBeforeCheckboxes;
>     private boolean enabled;
>     private final ThymeleafProperties.Servlet servlet;
>     private final ThymeleafProperties.Reactive reactive;
> ```

只要我们把html页面放在classpath:/templates/下， thymeleaf就可以帮我们渲染

> ```java
> @RequestMapping("/success")
> public String success(){
>     return "success";
> }
> ```



**使用步骤：**

​	1.导入thymeleaf的名称空间

> ```html
> <html lang="en" xmlns:th="http://www.thymeleaf.org">
> ```

	2. 使用thymeleaf语法

> ```html
> <!DOCTYPE html>
> <html lang="en" xmlns:th="http://www.thymeleaf.org">
> <head>
>     <meta charset="UTF-8">
>     <title>Title</title>
> </head>
> <body>
>     <h1>成功</h1>
>     <!--th:text 将div里面的文本内容设置为**-->
>     <div th:text="${hello}">这是显示欢迎信息</div>
> </body>
> </html>
> ```



##### 3. 语法规则

​	1.**th:text** : 改变当前元素里面的文本内容

​		th:可以将任意html属性原生属性的替换掉

![2018-02-04_123955](F:\springboot\源码、资料、课件\源码、资料、课件\文档\Spring Boot 笔记\images\2018-02-04_123955.png)



 2. **表达式**：

    ```properties
    Simple expressions:（表达式语法）
        Variable Expressions: ${...}：获取变量值；OGNL；
        		1）、获取对象的属性、调用方法
        		2）、使用内置的基本对象：
        			#ctx : the context object.
        			#vars: the context variables.
                    #locale : the context locale.
                    #request : (only in Web Contexts) the HttpServletRequest object.
                    #response : (only in Web Contexts) the HttpServletResponse object.
                    #session : (only in Web Contexts) the HttpSession object.
                    #servletContext : (only in Web Contexts) the ServletContext object.
                    
                    ${session.foo}
                3）、内置的一些工具对象：
    #execInfo : information about the template being processed.
    #messages : methods for obtaining externalized messages inside variables expressions, in the same way as they would be obtained using #{…} syntax.
    #uris : methods for escaping parts of URLs/URIs
    #conversions : methods for executing the configured conversion service (if any).
    #dates : methods for java.util.Date objects: formatting, component extraction, etc.
    #calendars : analogous to #dates , but for java.util.Calendar objects.
    #numbers : methods for formatting numeric objects.
    #strings : methods for String objects: contains, startsWith, prepending/appending, etc.
    #objects : methods for objects in general.
    #bools : methods for boolean evaluation.
    #arrays : methods for arrays.
    #lists : methods for lists.
    #sets : methods for sets.
    #maps : methods for maps.
    #aggregates : methods for creating aggregates on arrays or collections.
    #ids : methods for dealing with id attributes that might be repeated (for example, as a result of an iteration).
    
        Selection Variable Expressions: *{...}：选择表达式：和${}在功能上是一样；
        	补充：配合 th:object="${session.user}：
        <div th:object="${session.user}">
        <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
        <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
        <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
        </div>
        
        Message Expressions: #{...}：获取国际化内容
        Link URL Expressions: @{...}：定义URL；
        		@{/order/process(execId=${execId},execType='FAST')}
        Fragment Expressions: ~{...}：片段引用表达式
        		<div th:insert="~{commons :: main}">...</div>
        		
    Literals（字面量）
          Text literals: 'one text' , 'Another one!' ,…
          Number literals: 0 , 34 , 3.0 , 12.3 ,…
          Boolean literals: true , false
          Null literal: null
          Literal tokens: one , sometext , main ,…
    Text operations:（文本操作）
        String concatenation: +
        Literal substitutions: |The name is ${name}|
    Arithmetic operations:（数学运算）
        Binary operators: + , - , * , / , %
        Minus sign (unary operator): -
    Boolean operations:（布尔运算）
        Binary operators: and , or
        Boolean negation (unary operator): ! , not
    Comparisons and equality:（比较运算）
        Comparators: > , < , >= , <= ( gt , lt , ge , le )
        Equality operators: == , != ( eq , ne )
    Conditional operators:条件运算（三元运算符）
        If-then: (if) ? (then)
        If-then-else: (if) ? (then) : (else)
        Default: (value) ?: (defaultvalue)
    Special tokens:
        No-Operation: _ 
    ```

    

    ### 4. SpringMVC自动配置
    
    #### 1. Spring MVC auto-configuration
    
    https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications
    
    ##### Spring MVC Auto-configuration（SpringBoot自动配置好了SpringMVC）
    
    Spring Boot provides auto-configuration for Spring MVC that works well with most applications.
    
    The auto-configuration adds the following features on top of Spring’s defaults:
    
    以下是SpringBoot对SpringMVC的默认配置：
    
    - Inclusion of `ContentNegotiatingViewResolver` and `BeanNameViewResolver` beans.（自动配置了ViewResolver[视图解析器，根据方法的返回值得到视图对象View]，视图对象决定如何渲染（转发or重定向））
    
      `ContentNegotiatingViewResolver`：组合所有的视图解析器
    
      如何定制视图解析器：自己给容器中添加一个视图解析器
    
    - Support for serving static resources, including support for WebJars (covered [later in this document](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-static-content))). 静态资源文件夹路径webjars
    
    - Automatic registration of `Converter`, `GenericConverter`, and `Formatter` beans.
    
      `Converter`：转换器：类型转换使用
    
      `Formatter`：格式化器：2017-12-17===Date
    
    - Support for `HttpMessageConverters` (covered [later in this document](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-message-converters)).
    
      `HttpMessageConverters`:SpringMVC用来转换http请求和响应：User---json
    
      `HttpMessageConverters`是从容器中确定
    
    - Automatic registration of `MessageCodesResolver` (covered [later in this document](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-spring-message-codes)).
    
    - Static `index.html` support.静态首页访问
    
    - Custom `Favicon` support (covered [later in this document](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-favicon)).
    
    - Automatic use of a `ConfigurableWebBindingInitializer` bean (covered [later in this document](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-web-binding-initializer)).
    
    
    
    #### 2.扩展SpringMVC
    
    If you want to keep those Spring Boot MVC customizations and make more [MVC customizations](https://docs.spring.io/spring/docs/5.2.3.RELEASE/spring-framework-reference/web.html#mvc) (interceptors, formatters, view controllers, and other features), you can add your own `@Configuration` class of type `WebMvcConfigurer` but **without** `@EnableWebMvc`.
    
    > ```xml
    > <mvc:view-controller path="/hello" view-name="success"></mvc:view-controller>
    > <mvc:interceptors>
    >     <mvc:interceptor>
    >         <mvc:mapping path="/hello"/>
    >         <bean></bean>
    >     </mvc:interceptor>
    > </mvc:interceptors>
    > ```
    
    **编写一个配置类（@Configuration），是WebMvcConfigurer，既保留了Springboot自动配置，也能用扩展的配置**
    
    > ```java
    > //使用WebMvcConfigurer可以扩展
    > @Configuration
    > public class MyMvcConfig implements WebMvcConfigurer {
    >     @Override
    >     public void addViewControllers(ViewControllerRegistry registry) {
    >         //浏览器发送/my， 请求来到success页面
    >         registry.addViewController("/my").setViewName("success");
    > 
    >     }
    > }
    > ```
    
    原理：
    
    	1. WebMvcAutoConfiguration是SpringMVC自动配置类
     	2. 在做其他自动配置时会导入：@Import(EnableWebMvcConfiguration.class)
     	3. 容器中的所有WebMvcConfigurer都会一起起作用
     	4. 我们的配置类也会被调用
    
    
    
    #### 3. 全面接管SpringMVC
    
    Springboot对SpringMVC的自动配置不需要了，所有都是我们自己配
    
    在配置类中先加`@EnableWebMvc`
    
    
    
    If you want to provide custom instances of `RequestMappingHandlerMapping`, `RequestMappingHandlerAdapter`, or `ExceptionHandlerExceptionResolver`, and still keep the Spring Boot MVC customizations, you can declare a bean of type `WebMvcRegistrations` and use it to provide custom instances of those components.
    
    If you want to take complete control of Spring MVC, you can add your own `@Configuration` annotated with `@EnableWebMvc`, or alternatively add your own `@Configuration`-annotated `DelegatingWebMvcConfiguration` as described in the Javadoc of `@EnableWebMvc`.



### 5. 如何修改SpringBoot的默认配置

模式：

1.SpringBoot在自动配置很多组件的时候，先看容器中有没有用户自己配置的（@Bean, @Component） 如果有就用用户配置的；如果没有，才自动配置；如果有些组件可以有两个（ViewResolver）将用户配置和自己默认的组合起来

2.在Springboot中会有非常多的xxConfigurer帮我们进行扩展



### 6. RestfulCRUD

#### 1.默认访问首页，如果要修改，如下：

> ```java
> //所有的WebMvcConfigurer都会一起起作用
> @Bean
> public WebMvcConfigurer webMvcConfigurer(){
>     WebMvcConfigurer configurer = new WebMvcConfigurer(){
>         @Override
>         public void addViewControllers(ViewControllerRegistry registry) {
>             registry.addViewController("/").setViewName("login");
>             registry.addViewController("/index.html").setViewName("login");
>         }
>     };
>     return configurer;
> }
> ```



#### 2.国际化

1.**编写国际化配置文件**

2.使用ResourceBundleMessageSource管理国际化资源文件

3.在页面使用fmt:message取出国际化内容



步骤：

1.编写国际化配置文件，抽取页面需要显示的国际化消息

![image-20200219111201936](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20200219111201936.png)



2.SpringBoot自动配置好了管理国际化资源文件的组件

> ```java
> @Configuration(
>     proxyBeanMethods = false
> )
> @ConditionalOnMissingBean(
>     name = {"messageSource"},
>     search = SearchStrategy.CURRENT
> )
> @AutoConfigureOrder(-2147483648)
> @Conditional({MessageSourceAutoConfiguration.ResourceBundleCondition.class})
> @EnableConfigurationProperties
> public class MessageSourceAutoConfiguration {
>     private static final Resource[] NO_RESOURCES = new Resource[0];
> 
>     public MessageSourceAutoConfiguration() {
>     }
> 
>     @Bean
>     @ConfigurationProperties(
>         prefix = "spring.messages"
>     )
>     
>     
>     @Bean
>     public MessageSource messageSource(MessageSourceProperties properties) {
>         ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
>         if (StringUtils.hasText(properties.getBasename())) 
>         //设置国际化资源文件的基础名（去掉语言国家）默认为message，可以放在message.properties下
>         {
>             messageSource.setBasenames(StringUtils.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(properties.getBasename())));
>         }
> 
>         if (properties.getEncoding() != null) {
>             messageSource.setDefaultEncoding(properties.getEncoding().name());
>         }
> 
>         messageSource.setFallbackToSystemLocale(properties.isFallbackToSystemLocale());
>         Duration cacheDuration = properties.getCacheDuration();
>         if (cacheDuration != null) {
>             messageSource.setCacheMillis(cacheDuration.toMillis());
>         }
> 
>         messageSource.setAlwaysUseMessageFormat(properties.isAlwaysUseMessageFormat());
>         messageSource.setUseCodeAsDefaultMessage(properties.isUseCodeAsDefaultMessage());
>         return messageSource;
>     }
> 
> ```



3.去页面获取国际化的值（Thymeleaf）

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
      <meta name="description" content="">
      <meta name="author" content="">
      <title>Signin Template for Bootstrap</title>
      <!-- Bootstrap core CSS -->
      <link href="asserts/css/bootstrap.min.css" rel="stylesheet">
      <!-- Custom styles for this template -->
      <link href="asserts/css/signin.css" rel="stylesheet">
   </head>

   <body class="text-center">
      <form class="form-signin" action="dashboard.html">
         <img class="mb-4" src="asserts/img/bootstrap-solid.svg" alt="" width="72" height="72">
         <h1 class="h3 mb-3 font-weight-normal" th:text="#{login.tip}">Please sign in</h1>
         <label class="sr-only" th:text="#{login.username}">Username</label>
         <input type="text" class="form-control" placeholder="Username" th:placeholder="#{login.username}" required="" autofocus="">
         <label class="sr-only" th:text="#{login.password}">Password</label>
         <input type="password" class="form-control" placeholder="Password" th:placeholder="#{login.password}" required="">
         <div class="checkbox mb-3">
            <label>
          <input type="checkbox" value="remember-me"> [[#{login.remember}]]
        </label>
         </div>
         <button class="btn btn-lg btn-primary btn-block" type="submit" th:text="#{login.btn}">Sign in</button>
         <p class="mt-5 mb-3 text-muted">© 2017-2018</p>
         <a class="btn btn-sm">中文</a>
         <a class="btn btn-sm">English</a>
      </form>

   </body>

</html>
```

效果：根据浏览器语言设置的信息切换了国际化

原理：

​	国际化Locale（区域信息对象）； LocaleResolver（获取区域信息对象）

> ```java
> @Bean
> @ConditionalOnMissingBean
> @ConditionalOnProperty(
>     prefix = "spring.mvc",
>     name = {"locale"}
> )
> public LocaleResolver localeResolver() {
>     if (this.mvcProperties.getLocaleResolver() == org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.LocaleResolver.FIXED) {
>         return new FixedLocaleResolver(this.mvcProperties.getLocale());
>     } else {
>         AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
>         localeResolver.setDefaultLocale(this.mvcProperties.getLocale());
>         return localeResolver;
>     }
> }
> ```
>
> 默认的区域解析器就是根据http请求头带来的区域信息获取Locale进行国际化



4. 点击链接切换国际化

   > ```java
   > public class MyLocaleResolver implements LocaleResolver {
   >     @Override
   >     public Locale resolveLocale(HttpServletRequest httpServletRequest) {
   >         String l = httpServletRequest.getParameter("l");
   >         Locale locale = Locale.getDefault();
   >         if(!StringUtils.isEmpty(l)){
   >             String[] split = l.split("_");
   >             locale = new Locale(split[0],split[1]);
   > 
   >         }
   >         return locale;
   >     }
   > 
   >     @Override
   >     public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {
   > 
   >     }
   > }
   > 
   > MyMvcConfig.java下：
   >     @Bean
   >     public LocaleResolver localeResolver(){
   >         return new MyLocaleResolver();
   > 
   >     }
   > ```

#### 3. 登录

开发期间模板引擎页面修改后，要实时生效：

​	禁用模板引擎的缓存，修改后ctrl+F9，重新编译

**LoginController**

> ```java
> @Controller
> public class LoginController {
> 
>     @PostMapping(value = "/user/login")
>     //@RequestMapping(value = "/user/login", method = RequestMethod.POST)
>     public String login(@RequestParam("username") String username,
>                         @RequestParam("password") String password,
>                         Map<String, Object> map){
>         if(!StringUtils.isEmpty(username) && "123456".equals(password)){
>             return "dashboard"; //登录成功
>         }else{
>             //登录失败
>             map.put("msg", "用户名密码错误");
>             return "login";
>         }
> 
>     }
> }
> ```



**错误提示：**

> ```html
> <p style="color: red" th:text="${msg}" th:if="${not #strings.isEmpty(msg)}"></p>
> ```



#### **4.拦截器机制做登录检查：**

> ```java
> @Override
> public void addInterceptors(InterceptorRegistry registry) {
>     //静态资源：*css, *js不需要管，SpringBoot已经做好静态资源映射
>     registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
>             .excludePathPatterns("/index.html","/","/user/login","/assert/css/**","/webjars/**");
> }
> ```



#### 5. CRUD-员工列表

##### 1.RestfulCRUD:CRUD满足Rest风格

​	URI:/资源名称/资源标识		HTTP请求方式区分对资源的CRUD操作

|      | 普通CRUD(URI区分操作)   | RestfulCRUD       |
| ---- | ----------------------- | ----------------- |
| 查询 | getEmp                  | emp---GET         |
| 添加 | addEmp?xxx              | emp---POST        |
| 修改 | updateEmp?id=xxx&xxx=xx | emp/{id}---PUT    |
| 删除 | deleteEmp?id=1          | emp/{id}---DELETE |

##### 2.实验的请求架构：

|                            | 请求的URI | 请求方式 |
| -------------------------- | --------- | -------- |
| 查询所有员工               | emps      | GET      |
| 查询某个员工               | emp/{id}  | GET      |
| 转跳到添加页面             | emp       | GET      |
| 添加员工                   | emp       | POST     |
| 转跳到修改页面（先查后写） | emp/{id}  | GET      |
| 修改员工                   | emp       | PUT      |
| 删除员工                   | emp/{id}  | DELETE   |

**3.员工列表**

> ```java
> @Controller
> public class EmployeeController {
> 
>     @Autowired
>     EmployeeDao employeeDao;
> 
>     //查询所有员工返回列表页面
>     @GetMapping("/emps")
>     public String list(Model model){
>         Collection<Employee> employees = employeeDao.getAll();
> 
>         //放在请求域中
>         model.addAttribute("emps", employees);
> 
>         return "emp/list";
>     }
> 
> }
> ```



Thymeleaf公共页面元素抽取：

```html
1.抽取公共片段
<div th:fragment="copy"> 
    &copy; 2011 The Good Thymes Virtual Grocery 
</div>

2.引入公共片段
<div th:insert="~{footer :: copy}"></div>
~{templatename::selector}--模板名::选择器
~{templatename::fragmentname}--模板名::片段名

3.默认效果：
insert的公共片段在div标签中
```

三种引入公共片段的th属性：

**th:insert**：将公共片段整体插入到声明引入的元素中

**th:replace**：将声明引入的元素替换为公共片段

**th:include**：将被引入的片段内容包含进标签中

```html
<footer th:fragment="copy"> 
    &copy; 2011 The Good Thymes Virtual Grocery 
</div>

<div th:insert="footer :: copy"></div> 
<div th:replace="footer :: copy"></div> 
<div th:include="footer :: copy"></div>

效果：
<div> 
    <footer> 
        &copy; 2011 The Good Thymes Virtual Grocery 
    </footer> 
</div>

<footer> 
    &copy; 2011 The Good Thymes Virtual Grocery 
</footer>

<div> 
    &copy; 2011 The Good Thymes Virtual Grocery 
</div>
```



**引入片段的时候传入参数：**

```html
<div th:replace="commons/bar::#sidebar(activeUri='emps')"></div>
```





员工信息添加的小问题：

​	生日日期格式：2000-1-1； 2000/1/1;  2000.1.1

​	默认日期是/ /形式

​	

#### 6. CRUD-员工添加

添加页面

```html
<form>
    <div class="form-group">
        <label>LastName</label>
        <input type="text" class="form-control" placeholder="zhangsan">
    </div>
    <div class="form-group">
        <label>Email</label>
        <input type="email" class="form-control" placeholder="zhangsan@atguigu.com">
    </div>
    <div class="form-group">
        <label>Gender</label><br/>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="gender"  value="1">
            <label class="form-check-label">男</label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="gender"  value="0">
            <label class="form-check-label">女</label>
        </div>
    </div>
    <div class="form-group">
        <label>department</label>
        <select class="form-control">
            <option>1</option>
            <option>2</option>
            <option>3</option>
            <option>4</option>
            <option>5</option>
        </select>
    </div>
    <div class="form-group">
        <label>Birth</label>
        <input type="text" class="form-control" placeholder="zhangsan">
    </div>
    <button type="submit" class="btn btn-primary">添加</button>
</form>
```

提交的数据格式不对：生日：日期；

2017-12-12；2017/12/12；2017.12.12；

日期的格式化；SpringMVC将页面提交的值需要转换为指定的类型;

2017-12-12---Date； 类型转换，格式化;

默认日期是按照/的方式；

#### 7. CRUD-员工修改

修改添加二合一表单

```html
<!--需要区分是员工修改还是添加；-->
<form th:action="@{/emp}" method="post">
    <!--发送put请求修改员工数据-->
    <!--
1、SpringMVC中配置HiddenHttpMethodFilter;（SpringBoot自动配置好的）
2、页面创建一个post表单
3、创建一个input项，name="_method";值就是我们指定的请求方式
-->
    <input type="hidden" name="_method" value="put" th:if="${emp!=null}"/>
    <input type="hidden" name="id" th:if="${emp!=null}" th:value="${emp.id}">
    <div class="form-group">
        <label>LastName</label>
        <input name="lastName" type="text" class="form-control" placeholder="zhangsan" th:value="${emp!=null}?${emp.lastName}">
    </div>
    <div class="form-group">
        <label>Email</label>
        <input name="email" type="email" class="form-control" placeholder="zhangsan@atguigu.com" th:value="${emp!=null}?${emp.email}">
    </div>
    <div class="form-group">
        <label>Gender</label><br/>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="gender" value="1" th:checked="${emp!=null}?${emp.gender==1}">
            <label class="form-check-label">男</label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="gender" value="0" th:checked="${emp!=null}?${emp.gender==0}">
            <label class="form-check-label">女</label>
        </div>
    </div>
    <div class="form-group">
        <label>department</label>
        <!--提交的是部门的id-->
        <select class="form-control" name="department.id">
            <option th:selected="${emp!=null}?${dept.id == emp.department.id}" th:value="${dept.id}" th:each="dept:${depts}" th:text="${dept.departmentName}">1</option>
        </select>
    </div>
    <div class="form-group">
        <label>Birth</label>
        <input name="birth" type="text" class="form-control" placeholder="zhangsan" th:value="${emp!=null}?${#dates.format(emp.birth, 'yyyy-MM-dd HH:mm')}">
    </div>
    <button type="submit" class="btn btn-primary" th:text="${emp!=null}?'修改':'添加'">添加</button>
</form>
```

#### 8. CRUD-员工删除

```html
<tr th:each="emp:${emps}">
    <td th:text="${emp.id}"></td>
    <td>[[${emp.lastName}]]</td>
    <td th:text="${emp.email}"></td>
    <td th:text="${emp.gender}==0?'女':'男'"></td>
    <td th:text="${emp.department.departmentName}"></td>
    <td th:text="${#dates.format(emp.birth, 'yyyy-MM-dd HH:mm')}"></td>
    <td>
        <a class="btn btn-sm btn-primary" th:href="@{/emp/}+${emp.id}">编辑</a>
        <button th:attr="del_uri=@{/emp/}+${emp.id}" class="btn btn-sm btn-danger deleteBtn">删除</button>
    </td>
</tr>


<script>
    $(".deleteBtn").click(function(){
        //删除当前员工的
        $("#deleteEmpForm").attr("action",$(this).attr("del_uri")).submit();
        return false;
    });
</script>
```



### 7.错误处理机制

#### 1.SpringBoot默认的错误处理机制

默认效果：

​	1）返回一个默认的错误页面

![image-20200227111853821](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20200227111853821.png)

​	2）如果是其他客户端，默认响应一个json数据



**原理**：（参照SpringBoot自动配置ErrorMvcAutoConfiguration）

​	给容器添加了以下组件：

​	1.DefaultErrorAttributes（在页面共享信息）	

```java
public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = new LinkedHashMap();
        errorAttributes.put("timestamp", new Date());
        errorAttributes.put("path", request.path());
        Throwable error = this.getError(request);
        MergedAnnotation<ResponseStatus> responseStatusAnnotation = MergedAnnotations.from(error.getClass(), SearchStrategy.TYPE_HIERARCHY).get(ResponseStatus.class);
        HttpStatus errorStatus = this.determineHttpStatus(error, responseStatusAnnotation);
        errorAttributes.put("status", errorStatus.value());
        errorAttributes.put("error", errorStatus.getReasonPhrase());
        errorAttributes.put("message", this.determineMessage(error, responseStatusAnnotation));
        errorAttributes.put("requestId", request.exchange().getRequest().getId());
        this.handleException(errorAttributes, this.determineException(error), includeStackTrace);
        return errorAttributes;
    }
```



2. BasicErrorController

   ```java
   @Controller //处理默认/error请求
   @RequestMapping({"${server.error.path:${error.path:/error}}"})
   public class BasicErrorController extends AbstractErrorController {
       
       @RequestMapping(
           produces = {"text/html"}//产生html类型的处理；浏览器发送的请求来到这个方法处理
       )
       public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
           HttpStatus status = this.getStatus(request);
           Map<String, Object> model = Collections.unmodifiableMap(this.getErrorAttributes(request, this.isIncludeStackTrace(request, MediaType.TEXT_HTML)));
           response.setStatus(status.value());
           
           //决定去哪个页面作为错误页面，包含页面地址和页面内容
           ModelAndView modelAndView = this.resolveErrorView(request, response, status, model);
           return modelAndView != null ? modelAndView : new ModelAndView("error", model);
       }
   
       @RequestMapping //产生json数据，其他客户端来到这个方法处理
       public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
           HttpStatus status = this.getStatus(request);
           if (status == HttpStatus.NO_CONTENT) {
               return new ResponseEntity(status);
           } else {
               Map<String, Object> body = this.getErrorAttributes(request, this.isIncludeStackTrace(request, MediaType.ALL));
               return new ResponseEntity(body, status);
           }
       }
   ```

   

  3. ErrorPageCustomizer

     ```java
     @Value("${error.path:/error}")
     private String path = "/error";系统出现错误以后来到error请求进行处理
     ```

  4. DefaultErrorViewResolverConfiguration

     ```java
         public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
             ModelAndView modelAndView = this.resolve(String.valueOf(status.value()), model);
             if (modelAndView == null && SERIES_VIEWS.containsKey(status.series())) {
                 modelAndView = this.resolve((String)SERIES_VIEWS.get(status.series()), model);
             }
     
             return modelAndView;
         }
     
         private ModelAndView resolve(String viewName, Map<String, Object> model) {
             //默认springboot可以去找到某个页面-->error/{状态码}
             String errorViewName = "error/" + viewName;
             //用模板引擎解析
             TemplateAvailabilityProvider provider = this.templateAvailabilityProviders.getProvider(errorViewName, this.applicationContext);
             
             //模板引擎不可用，就在静态资源文件夹下找errorViewName对应的页面 error/404.html
             return provider != null ? new ModelAndView(errorViewName, model) : this.resolveResource(errorViewName, model);
         }
     
     ```

     

**步骤：**

​	一旦系统出现4xx或者5xx之类的错误，**ErrorPageCustomizer**就会生效（定制错误的响应规则），就会来到/error请求，接着会被**BasicErrorController**处理

​	1）响应页面（去哪个页面由DefaultErrorViewResolver解析）

```java
    protected ModelAndView resolveErrorView(HttpServletRequest request, HttpServletResponse response, HttpStatus status, Map<String, Object> model) {
        Iterator var5 = this.errorViewResolvers.iterator();

        ModelAndView modelAndView;
        do {
            if (!var5.hasNext()) {
                return null;
            }

            ErrorViewResolver resolver = (ErrorViewResolver)var5.next();
            modelAndView = resolver.resolveErrorView(request, status, model);
        } while(modelAndView == null);

        return modelAndView;
    }
```



#### 2.如何定制错误响应

##### 	1.如果定制错误的页面

​		1）**有模板引擎情况下**：templates/error/{状态码} ,产生此状态码的错误就会转跳到对应的页面

​		页面能获取的信息：

​	timestamp（时间戳），path（路径），status（状态码），error（错误提示），message（错误消息），requestId（请求ID）



​		2）**没有模板引擎情况下**（模板引擎找不到错误页面）：静态资源文件夹下找

​		3）以上都没有错误页面，默认来到springboot默认的错误页面



##### 	2.如何定制错误的json数据

​		1）自定义异常处理&返回定制json数据

```java
@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UserNotExitException.class)
    public Map<String, Object> handleException(Exception e){
        Map<String, Object> map = new HashMap<>();
        map.put("code", "user.notexist");
        map.put("message", e.getMessage());
        return map;
    }
}
//没有自适应效果
```

​	2）转发到/error进行自适应响应

```java

    @ResponseBody
    @ExceptionHandler(UserNotExitException.class)
    public String handleException(Exception e){
        Map<String, Object> map = new HashMap<>();
        map.put("code", "user.notexist");
        map.put("message", e.getMessage());
        //转发到error
        return "forward:/error";
    }
```

```java
//@ResponseBody 转发或者重定向不可以有这个注释 不然会显示字符串
 @ExceptionHandler(UserNotExitException.class)
    public String handleException(Exception e, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        //传入我们自己的错误状态码
        request.setAttribute("javax.servlet.error.status_code", 500);
        //要设置状态码，不然不会进入定制页面的解析
        map.put("code", "user.notexist");
        map.put("message", e.getMessage());
        //转发到error
        return "forward:/error";
    }
```

3）**将我们的定制数据携带出去**

出现错误以后，会来到/error请求，这个请求会被BasicErrorController处理，响应出去可以获取的数据是由getErrorAttributes得到（是AbstractErrorController（ErrorController）规定的方法）

​	1.可以完全来编写一个ErrorController的实现类（或者编写AbstractErrorController的子类）

​	2.页面上能用的数据，或者是json返回能用的数据都是通过ErrorAttributes.getErrorAttributes得到。容器中DefaultErrorAttributes默认进行数据处理的



自定义ErrorAttributes

```java
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
        map.put("school", "SCU");
        return map;
    }
```

**最终效果**：响应是自适应的，可以通过定制ErrorAttributes改变需要返回的内容