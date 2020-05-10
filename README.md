# apollo-env-enhancer



apollo是一个流行的配置中心，可以通过不同env来隔离各个开发，测试，产线等环境。但是它的env是不可以编辑，可就是说只能使用以下几种环境名称。

* LOCAL
* DEV
* FWS
* FAT
* UAT
* LPT
* PRO
* TOOLS

而apollo-env-enhancer就是提供**可编辑**的env，这样使用起来更加方便一些。



## 使用

**Step1: GitHub下载源代码并编译**

```shell
git clone https://github.com/zhuangjinjin/apollo-env-enhancer.git
```

**Step2: 构建项目**

```shell
cd apollo-env-enhancer
mvn clean install -Dmaven.test.skip=true
```

**Step3: 引入**

```xml
<dependency>
    <groupId>io.github.ukuz</groupId>
    <artifactId>apollo-env-enhancer</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.ctrip.framework.apollo</groupId>
    <artifactId>apollo-client</artifactId>
    <version>1.1.1</version>
</dependency>
```

**Step4: 设置meta-server的地址**

apollo的用法就不赘述了，[可见]([https://github.com/ctripcorp/apollo/wiki/Java%E5%AE%A2%E6%88%B7%E7%AB%AF%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97](https://github.com/ctripcorp/apollo/wiki/Java客户端使用指南))

打开apollo-env.properties

```properties
rd.meta=http://rd.xxx.com:8080
```

启动参数

```shell
-Denv=rd
```

