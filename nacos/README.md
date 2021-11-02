# 官方文档
  https://nacos.io/zh-cn/docs/what-is-nacos.html


# 单机模式支持mysql
  在0.7版本之前，在单机模式时nacos使用嵌入式数据库实现数据的存储，不方便观察数据存储的基本情况。0.7版本增加了支持mysql数据源能力，具体的操作步骤：
  
  1.安装数据库，版本要求：5.6.5+
  
  2.初始化mysql数据库，数据库初始化文件：nacos-mysql.sql
  
  3.修改conf/application.properties文件，增加支持mysql数据源配置（目前只支持mysql），添加mysql数据源的url、用户名和密码。
  ```yaml
  spring.datasource.platform=mysql
  
  db.num=1
  db.url.0=jdbc:mysql://11.162.196.16:3306/nacos_devtest?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
  db.user=nacos_devtest
  db.password=youdontknow
  再以单机模式启动nacos，nacos所有写嵌入式数据库的数据都写到了mysql
  ```
# Nacos启动
  Linux/Unix/Mac：
  
  sh startup.sh -m standalone.
  
  Standalone means it is non-cluster Mode. 
  
  Windows：
  
  cmd startup.cmd -m standalone.
  
  Standalone means it is non-cluster Mode. 
  