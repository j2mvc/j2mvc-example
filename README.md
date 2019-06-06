# j2mvc-example项目简介
基于j2mvc开发的示例项目，帮助你快速学会JAVA开发


# 创建java application项目
<pre>
 请参考示例j2mvc-example-app
</pre>
1、使用Eclipse创建Maven项目
<pre>
	File => new => project =>  Maven => Maven Project
	在Select an Archetype选择maven-archetype-quickstart
</pre>

<pre>
2、添加Maven依赖

3、数据库｜表

4、应用配置

5、实体模型

6、应用逻辑

7、应用入口

8、发布
</pre>
# 创建java web项目
<pre>
   请参考示例j2mvc-example-web
</pre>
1、使用Eclipse创建Maven项目

<pre>
	File => new => project =>  Maven => Maven Project
	在Select an Archetype选择maven-archetype-webapp
</pre>
	在pom.xml中添加依赖
	
	<dependencies>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.11</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>javax.servlet.jsp.jstl</groupId>
			<artifactId>jstl-api</artifactId>
			<version>1.2</version>
			<exclusions>
				<exclusion>
					<groupId>javax.servlet</groupId>
					<artifactId>servlet-api</artifactId>
				</exclusion>
				<exclusion>
					<groupId>javax.servlet.jsp</groupId>
					<artifactId>jsp-api</artifactId>
				</exclusion>
			</exclusions>
		</dependency>

		<dependency>
			<groupId>org.glassfish.web</groupId>
			<artifactId>jstl-impl</artifactId>
			<version>1.2</version>
			<exclusions>
				<exclusion>
					<groupId>javax.servlet</groupId>
					<artifactId>servlet-api</artifactId>
				</exclusion>
				<exclusion>
					<groupId>javax.servlet.jsp</groupId>
					<artifactId>jsp-api</artifactId>
				</exclusion>
				<exclusion>
					<groupId>javax.servlet.jsp.jstl</groupId>
					<artifactId>jstl-api</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		<!-- https://mvnrepository.com/artifact/log4j/log4j -->
		<dependency>
			<groupId>log4j</groupId>
			<artifactId>log4j</artifactId>
			<version>1.2.17</version>
		</dependency>


		<dependency>
			<groupId>com.j2mvc</groupId>
			<artifactId>j2mvc-util</artifactId>
			<version>2.0.1</version>
		</dependency>
		<dependency>
			<groupId>com.j2mvc</groupId>
			<artifactId>j2mvc-framework-web</artifactId>
			<version>2.0.1</version>
		</dependency>
	</dependencies>

	<build>
		<finalName>j2mvc-example-web</finalName>
		<pluginManagement>
			<plugins>
				<plugin>
					<artifactId>maven-compiler-plugin</artifactId>
					<version>3.5.1</version>
					<configuration>
						<source>1.7</source>
						<target>1.7</target>
					</configuration>
				</plugin>
				<plugin>
					<groupId>org.apache.maven.plugins</groupId>
					<artifactId>maven-release-plugin</artifactId>
					<version>2.5.3</version>
					<configuration>
						<tagNameFormat>v@2.5.3</tagNameFormat>
						<autoVersionSubmodules>true</autoVersionSubmodules>
					</configuration>
				</plugin>
			</plugins>
		</pluginManagement>
		<resources>
			<resource>
				<directory>src/main/java</directory>
				<includes>
					<include>**/*.properties</include>
					<include>**/*.xml</include>
					<include>**/*.tld</include>
				</includes>
				<filtering>true</filtering>
			</resource>
		</resources>
	</build>
	
2、数据库｜表
<pre>
create database j2mvc_example;

grant all privileges on j2mvc_example.* to exmaple@127.0.0.1 identified by 'exmaplepassword' WITH GRANT OPTION;
flush privileges;

use j2mvc_example;
 
-- 用户
DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users(
	id					varchar(32)	NOT NULL,
	username			varchar(255)	NOT NULL COMMENT '用户名',			
	password			varchar(255)	NOT NULL COMMENT '密码',				
  	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 购物车
DROP TABLE IF EXISTS products;
CREATE TABLE IF NOT EXISTS products(
	id					varchar(32)		NOT NULL,
	title				varchar(255)		NOT NULL COMMENT '商品标题',			
	detail				varchar(255)	NOT NULL COMMENT '商品详情',				
	price				double(11,2)	NOT NULL COMMENT '价格',		
	stock				int(11)			NOT NULL COMMENT '库存',
 	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 购物车
DROP TABLE IF EXISTS carts;
CREATE TABLE IF NOT EXISTS carts(
	id					varchar(32)	NOT NULL,
	user_id				varchar(255)	NOT NULL COMMENT '用户ID',			
	product_id			varchar(255)	NOT NULL COMMENT '商品ID',				
	num					int(11)	NOT NULL COMMENT '数量',				
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

</pre>

3、网站配置
/WEB-INF/web.xml

	<listener>
		<listener-class>org.fixwork.framework.FixworkListener</listener-class>
	</listener>
	<context-param>
		<description>配置文件</description>
		<param-name>works</param-name>
		<param-value>/WEB-INF/works.xml</param-value>
	</context-param>	  
	<context-param>
		<description>输入SQL日志</description>
		<param-name>sqlLog</param-name>
		<param-value>false</param-value>
	</context-param>
	<context-param>
		<description>输入uriLog日志</description>
		<param-name>uriLog</param-name>
		<param-value>false</param-value>
	</context-param>
	
	<filter>
		<display-name>DispatcherFilter</display-name>
		<filter-name>DispatcherFilter</filter-name>
		<filter-class>org.fixwork.framework.dispatcher.DispatcherFilter</filter-class>
		<init-param>
			<description>拦截的URI后缀</description>
			<param-name>subfixes</param-name>
			<param-value>.jsp,.do</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>DispatcherFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping> 
	
	<error-page>
		<error-code>404</error-code>
		<location>/404.jsp</location>
	</error-page>
	<error-page>
		<error-code>500</error-code>
		<location>/500.jsp</location>
	</error-page>
	
/WEB-INF/works.xml

<works>
	<DataSource 
		name="jdbc/j2mvc_example" 
		driverClassName="com.mysql.jdbc.Driver" 
		username="example"
		password="exmaplepass" 
		maxIdle="2"
		maxWait="100000" 
		maxActive="100"
		initialSize="1"
		url="jdbc:mysql://127.0.0.1:3306/j2mvc_example?autoReconnect=true&amp;failOverReadOnly=false&amp;useUnicode=true&amp;characterEncoding=UTF-8" />	
	 
	<!-- 拦截器 -->
	 <interceptors>
		<interceptor ref="com.j2mvc.example.web.interceptor.UserInterceptor"/>
	</interceptors> 
	
	
	<!-- actions控制器包名，多个包名用英文逗号分隔，WEB服务器启动时，注入actions -->
	<actions-packages>
		com.j2mvc.example.web.action
	</actions-packages>
	
	<i18n-default>zh-CN</i18n-default>
</works>

4、实体模型
<pre>
import com.j2mvc.util.mapping.DataSourceName;
import com.j2mvc.util.mapping.NotJSONField;
import com.j2mvc.util.mapping.PrimaryKey;
import com.j2mvc.util.mapping.Table;
import com.j2mvc.util.mapping.Column;
/**
 * 用户实体类
 * @author yangshuo
 * 如果只有一个数据源，可以不设置DataSourceName
 */
@DataSourceName("jdbc/j2mvcexample")
@Table("users")
@PrimaryKey(autoIncrement=false)
public class User {
	/** id */
	@Column(name = "id",length = 32)
	private String id;
	...
}
</pre>

5、服务逻辑
<pre>
import java.util.List;
import com.j2mvc.example.web.entity.Cart;
import com.j2mvc.framework.dao.DaoSupport;
public class CartService {
	DaoSupport dao = new DaoSupport(Cart.class);
	/**
	 * 插入
	 * @param cart
	 * @return Cart
	 */
	public Cart insert(Cart cart) {		
		return (Cart) dao.insert(cart);
	}
}
</pre>

6、访问逻辑
<pre>
import com.j2mvc.util.mapping.ActionPath;
import com.j2mvc.util.mapping.ActionUri;
@ActionPath(path="/",dir="/WEB-INF/jsp/")
public class BaseAction extends Action{

	@ActionUri(uri="items([/])?")
	public String index(Integer page){
	...
		return "index.jsp";
	}
}
</pre>

7、jsp
<pre>
在/WEB-INF/jsp/或自己定义的其它目录下编写jsp文件。
</pre>

8、运行
<pre>
直接运行到tomcat。
</pre>

9、发布
<pre>
mvn clean install
打包目录/target。将生成war文件或发布目录，上传至服务器。
</pre>

10、注意
若打包后的action类，无法获取到参数，先clean工程，再将target目录下classes目录上传服务器，覆盖服务器classes目录。