# Spring Cloud整合Seata分布式事务框架

> [Seata](https://github.com/seata/seata)：阿里巴巴开源的一款分布式解决方案，其前身是Fescar。官网：https://seata.io/zh-cn/index.html。

## 1. 添加依赖

```xml
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-seata</artifactId>
            <version>2.1.1.RELEASE</version>
        </dependency>
```
## 2. 添加seata配置和注册文件
> 以下配置文件在要下载的seata-server/conf文件夹里有，直接从文件里复制作相应修改即可。

- 注册文件 `registry.conf`:
> 该配置用于指定 TC 的注册中心和配置文件，默认使用file文件注册，则该文件无需修改。如果是注册到`eureka`，则要修改`registry.type="eureka"`，并修改`registry.eureka.serviceUrl`为你当前`eureka`注册中心的地址。其它注册方式类推。
```properties
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  type = "file"

  nacos {
    serverAddr = "localhost"
    namespace = ""
    cluster = "default"
  }
  eureka {
    serviceUrl = "http://localhost:8761/eureka"
    application = "default"
    weight = "1"
  }
  redis {
    serverAddr = "localhost:6379"
    db = "0"
  }
  zk {
    cluster = "default"
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  consul {
    cluster = "default"
    serverAddr = "127.0.0.1:8500"
  }
  etcd3 {
    cluster = "default"
    serverAddr = "http://localhost:2379"
  }
  sofa {
    serverAddr = "127.0.0.1:9603"
    application = "default"
    region = "DEFAULT_ZONE"
    datacenter = "DefaultDataCenter"
    cluster = "default"
    group = "SEATA_GROUP"
    addressWaitTime = "3000"
  }
  file {
    name = "file.conf"
  }
}

config {
  # file、nacos 、apollo、zk、consul、etcd3
  type = "file"

  nacos {
    serverAddr = "localhost"
    namespace = ""
  }
  consul {
    serverAddr = "127.0.0.1:8500"
  }
  apollo {
    app.id = "seata-server"
    apollo.meta = "http://192.168.1.204:8801"
  }
  zk {
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  etcd3 {
    serverAddr = "http://localhost:2379"
  }
  file {
    name = "file.conf"
  }
}
```
- `file.conf`:
> 该配置用于指定TC的相关属性；如果使用注册中心也可以将配置添加到配置中心。\
> 这里要注意的是`transport.service.vgroup_mapping`配置的值，在 Spring Cloud 中默认值是`${spring.application.name}-fescar-service-group`，可以通过指定`application.properties`的 `spring.cloud.alibaba.seata.tx-service-group`这个属性覆盖，但是必须要和`file.conf`中`transport.service.vgroup_mapping`的值一致，否则会提示 no available server to connect
```properties
transport {
  # tcp udt unix-domain-socket
  type = "TCP"
  #NIO NATIVE
  server = "NIO"
  #enable heartbeat
  heartbeat = true
  #thread factory for netty
  thread-factory {
    boss-thread-prefix = "NettyBoss"
    worker-thread-prefix = "NettyServerNIOWorker"
    server-executor-thread-prefix = "NettyServerBizHandler"
    share-boss-worker = false
    client-selector-thread-prefix = "NettyClientSelector"
    client-selector-thread-size = 1
    client-worker-thread-prefix = "NettyClientWorkerThread"
    # netty boss thread size,will not be used for UDT
    boss-thread-size = 1
    #auto default pin or 8
    worker-thread-size = 8
  }
  shutdown {
    # when destroy server, wait seconds
    wait = 3
  }
  serialization = "seata"
  compressor = "none"
}
service {
  #vgroup->rgroup
  vgroup_mapping.my_test_tx_group = "default"
  #only support single node
  default.grouplist = "127.0.0.1:8091"
  #degrade current not support
  enableDegrade = false
  #disable
  disable = false
  #unit ms,s,m,h,d represents milliseconds, seconds, minutes, hours, days, default permanent
  max.commit.retry.timeout = "-1"
  max.rollback.retry.timeout = "-1"
}

client {
  async.commit.buffer.limit = 10000
  lock {
    retry.internal = 10
    retry.times = 30
  }
  report.retry.count = 5
  tm.commit.retry.count = 1
  tm.rollback.retry.count = 1
}

## transaction log store
store {
  ## store mode: file、db
  mode = "file"

  ## file store
  file {
    dir = "sessionStore"

    # branch session size , if exceeded first try compress lockkey, still exceeded throws exceptions
    max-branch-session-size = 16384
    # globe session size , if exceeded throws exceptions
    max-global-session-size = 512
    # file buffer size , if exceeded allocate new buffer
    file-write-buffer-cache-size = 16384
    # when recover batch read size
    session.reload.read_size = 100
    # async, sync
    flush-disk-mode = async
  }

  ## database store
  db {
    ## the implement of javax.sql.DataSource, such as DruidDataSource(druid)/BasicDataSource(dbcp) etc.
    datasource = "dbcp"
    ## mysql/oracle/h2/oceanbase etc.
    db-type = "mysql"
    driver-class-name = "com.mysql.jdbc.Driver"
    url = "jdbc:mysql://127.0.0.1:3306/seata"
    user = "mysql"
    password = "mysql"
    min-conn = 1
    max-conn = 3
    global.table = "global_table"
    branch.table = "branch_table"
    lock-table = "lock_table"
    query-limit = 100
  }
}
lock {
  ## the lock store mode: local、remote
  mode = "remote"

  local {
    ## store locks in user's database
  }

  remote {
    ## store locks in the seata's server
  }
}
recovery {
  #schedule committing retry period in milliseconds
  committing-retry-period = 1000
  #schedule asyn committing retry period in milliseconds
  asyn-committing-retry-period = 1000
  #schedule rollbacking retry period in milliseconds
  rollbacking-retry-period = 1000
  #schedule timeout retry period in milliseconds
  timeout-retry-period = 1000
}

transaction {
  undo.data.validation = true
  undo.log.serialization = "jackson"
  undo.log.save.days = 7
  #schedule delete expired undo_log in milliseconds
  undo.log.delete.period = 86400000
  undo.log.table = "undo_log"
}

## metrics settings
metrics {
  enabled = false
  registry-type = "compact"
  # multi exporters use comma divided
  exporter-list = "prometheus"
  exporter-prometheus-port = 9898
}

support {
  ## spring
  spring {
    # auto proxy the DataSource bean
    datasource.autoproxy = false
  }
}
```
## 3. 配置数据源
Seata 通过代理数据源的方式实现分支事务；MyBatis 和 JPA 都需要注入 io.seata.rm.datasource.DataSourceProxy, 此外，MyBatis 还需要额外注入 org.apache.ibatis.session.SqlSessionFactory。
- MyBatis
```java
@Configuration
public class DataSourceProxyConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean
    public DataSourceProxy dataSourceProxy(DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSourceProxy dataSourceProxy) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceProxy);
        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        return sqlSessionFactoryBean.getObject();
    }
}
```

> 特别提示：对于3的2个配置文件和4的数据源配置，每个参与分布式事务的服务都需要引入，要在一个事务组里。

## 4. 添加Seata需要用到的`undo_log`表
该sql在seata-server/conf里有提供。该表用于在分布式事务发生异常时执行回滚的依据。**每个参与分布式事务的数据库都需要加该表**。
```mysql
-- the table to store seata xid data
-- 0.7.0+ add context
-- you must to init this sql for you business databese. the seata server not need it.
-- 此脚本必须初始化在你当前的业务数据库中，用于AT 模式XID记录。与server端无关（注：业务数据库）
-- 注意此处0.3.0+ 增加唯一索引 ux_undo_log
drop table `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```

## 5. 下载运行seata-server服务
下载地址：<https://seata.io/zh-cn/blog/download.html>，根据需要下载相应版本。解压后，`seata/conf`目录有以上需要的配置等。修改相应的配置，启动运行。双击`seata/bin`目录下的`seata-server.bat`或者执行以下命令运行：
```shell script
sh ./bin/seata-server.sh
```

## 6. 添加注解`@GlobalTransactional`开启分布式事务
```java
    @Override
    @GlobalTransactional
    public void create(String userId, String commodityCode, int orderCount) {
        int orderMoney = calculate(commodityCode, orderCount);
        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(orderCount);
        order.setMoney(orderMoney);
        orderMapper.insert(order);

        // Feign远程调用，扣钱
        accountFeignClient.debit(userId, orderMoney);
        // 减库存
        storageFeignClient.deduct(commodityCode, orderCount);

        if (orderCount == 3) {
            throw new RuntimeException("异常回滚");
        }
    }
```

## 7. 测试

- 正常测试

<http://localhost:8081/order/create?userId=1&commodityCode=book&orderCount=1>

- 异常测试

<http://localhost:8081/order/create?userId=1&commodityCode=book&orderCount=3>

> 这里我代码里写了orderCount==3时候会抛异常，用于测试。另外，我们也可以去掉`@GlobalTransactional`测试。

## 8. 详细代码
- [Github](https://github.com/xudc0521/spring-boot-v2/tree/master/spring-boot-v2-seata)

- [码云](https://gitee.com/xudc/spring-boot-v2/tree/master/spring-boot-v2-seata) 
