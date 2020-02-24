# Spring Boot 2.X 整合Apache Dubbo
## 引入依赖
```xml
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-dependencies-zookeeper</artifactId>
            <version>${dubbo.version}</version>
            <type>pom</type>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```
> 排除`slf4j-log4j12`冲突。\
> 另外，`Apache`的`dubbo starter`不包含`dubbo`依赖，不像`alibaba`的`starter`，所以要再额外引入`dubbo`的依赖，这点要额外注意。