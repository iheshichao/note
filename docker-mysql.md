```
1. 首先创建centos容器，用于安装mysql，建议使用以下方式创建否则会在启动mysql时遇到：Failed to get D-Bus connection: Operation not permitted 的问题
   docker run -d -e "container=docker" --privileged=true -v /sys/fs/cgroup:/sys/fs/cgroup --name centos7 centos /usr/sbin/init 
   
2. 安装mysql源
   下载mysql源
   wget http://dev.mysql.com/get/mysql57-community-release-el7-8.noarch.rpm
   安装mysql源
   yum localinstall mysql57-community-release-el7-8.noarch.rpm
   检查是否安装成功：
   yum repolist enabled | grep "mysql.*-community.*"
   
3. 安装mysql
   yum install mysql-community-server
 
4. 启动mysql服务
   systemctl start mysqld
   systemctl status mysqld

5. 开机启动mysql
   systemctl enable mysqld
   systemctl daemon-reload
   
6. 修改root本地登陆密码
   grep 'password' /var/log/mysqld.log
   mysql -uroot -p 'password'
   set password for 'root'@'localhost'=password('my password')
   查看mysql密码策略
   show variables like '%password%';
   
7. 授权远程登录用户
   GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'password!' WITH GRANT OPTION;
   FLUSH PRIVILEGES;
   
8. 默认配置文件路径
   
   配置文件：/etc/my.cnf 
   日志文件：/var/log//var/log/mysqld.log 
   服务启动脚本：/usr/lib/systemd/system/mysqld.service 
   socket文件：/var/run/mysqld/mysqld.pid
```
