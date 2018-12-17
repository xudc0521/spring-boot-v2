# spring-boot-v2-cache
#### 步骤

1、引入spring-boot-starter-cache模块; <br>
2、使用@EnableCaching开启缓存; <br>
3、使用缓存注解; <br>
4、切换为其他缓存; <br>


将方法的运行结果进行缓存,以后再要相同的数据,直接从缓存中获取,不用调用方法;<br>
CacheManager管理多个Cache组件的,对缓存的真正CRUD操作在Cache组件中,每一个缓存组件有自己唯一一个名字;

#### 原理:
1、自动配置类：CacheAutoConfiguration; <br>
2、缓存的配置类: <br>
```java
org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration
org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration【默认】
org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
```  
3、哪个配置类默认生效:SimpleCacheConfiguration; <br>
4、给容器中注册了一个CacheManager：ConcurrentMapCacheManager; <br>
5、可以获取和创建ConcurrentMapCache类型的缓存组件,他的作用将数据保存在ConcurrentMap中; <br>