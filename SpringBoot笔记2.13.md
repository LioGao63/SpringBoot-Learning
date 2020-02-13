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



## 3.自动配置的原理