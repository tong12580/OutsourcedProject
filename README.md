## OutsourcedProject

#### 简介

`OutsourcedProject是什么` `joker` `java`

----

1. OutsourcedProject是什么?

* > OutsourcedProject项目是一个基于springBoot 的快速开发项目 他专注于服务的后台接口
* > 基于 springData 来处理Dao层开发
* > 使用 springSecurity 来管理权限校验
* > 使用 redis来处理缓存 token

2. OutsourcedProject能做什么?

* > outsourceProject项目可以作为一个电商网站的载体

### 打包方式
> 本项目可以用MVN进行打包构建 具体的mvn脚本如下所示
```
 mvn jar:jar spring-boot:repackage
```

| 时间         | 编辑人      | 版本号    |
|:----------- |:----------:| --------:|
| 2016/12/09  | joker      | 20161209 |
| 2016/12/12  | joker      | 20161212 |
| 2017/01/07  | joker      | 20170106 |
| 2017/01/17  | joker      | 20170116 |
| 2017/02/08  | joker      | 20170208 |
| 2018/02/22  | joker      | 20180222 |

----

### v.20180222 版本 新增内容处理说明
 
1. 新增security权限校验
2. 新增webSocket支持
3. 去除无效的过滤器
4. 新增JDBC连接池升级
5. 添加日志切面
6. 新增返回结果集调用方式


#### OutsourcedProject 目录结构树

``` xml
├─logs
├─sql
└─src
   ├─main
   │  ├─java
   │  │  └─com
   │  │      └─business
   │  │          ├─common
   │  │          │  ├─context
   │  │          │  ├─date
   │  │          │  ├─http
   │  │          │  │  └─token
   │  │          │  ├─json
   │  │          │  ├─message
   │  │          │  ├─other
   │  │          │  │  ├─excel
   │  │          │  │  ├─Files
   │  │          │  │  ├─idCard
   │  │          │  │  └─img
   │  │          │  ├─redis
   │  │          │  ├─response
   │  │          │  ├─sort
   │  │          │  └─uuid
   │  │          ├─config
   │  │          ├─controller
   │  │          │  ├─auth
   │  │          │  └─user
   │  │          ├─dao
   │  │          │  ├─auth
   │  │          │  └─users
   │  │          ├─listener
   │  │          ├─pojo
   │  │          │  ├─dto
   │  │          │  │  └─user
   │  │          │  └─enums
   │  │          ├─service
   │  │          │  └─interfaces
   │  │          │      └─auth
   │  │          ├─thread
   │  │          │  ├─schedule
   │  │          │  └─task
   │  │          └─webSocket
   │  └─resources
   │      ├─static
   │      └─templates
   └─test
       └─java
           └─com
               └─business


````