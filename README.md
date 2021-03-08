<h1>codetc</h1>

![Image text](https://img.shields.io/badge/springboot-2.3.1-green.svg)
![Image text](https://img.shields.io/badge/MyBatis%20Plus-3.4.1-green.svg)

一个基于 Spring Boot + mybaits plus + Redis 的前后端分离快速开发脚手架

## 项目说明
- 用于构建中小型API、RESTful API 项目，稳定、简单、快速，避免重复劳动。
- 架构清析，代码精简，最干净的脚手架。
- Spring Security 结合 Redis 实现快速认证。
- 全局接管异常，统一处理参数验证，RESTful 风格返回数据。
- Swagger 自动生成同步在线文档，实时在线测试API。
- 引入lombok 大量简化了代码。
- 引入hutool 工具包，规范工具类。
- 实现了基于分布式锁防止重复提交请求方案。
- 简单、方便、灵活的权限管理方案，可自由配置各种不同的角色权限。

## 模块说明
- codetc-common 公共模块
- codetc-mbg Mybatis Plus自动生成代码
- codetc-third 接入第三方服务
- codetc-web Web项目模块

## 快速启动

简化依赖服务，只需安装最常用的 MySql 和 Redis 服务，数据库导入`codetc_db.sql`脚本，修改 `codetc-web` 模块中相关配置信息即可启动。

>mvn clean package 打包发布

## 环境
- [JDK 1.8+](https://www.oracle.com/technetwork/java/javase/overview/index.html)
- [MySQL 5.7+](https://dev.mysql.com/downloads/mysql/)
- [Maven](https://maven.apache.org/download.cgi)
- [Redis 3.2+](https://redis.io/download)