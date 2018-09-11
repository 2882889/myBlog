---
title: centOS Linux系统命令--VPS那些事
date: 2016-12-11 11:02:30
tags: Linux
---

## 升级系统 ##

```
yum update -y
```

## 安装wget ##
wget是一个从网络上自动下载文件的自由工具，支持通过HTTP、HTTPS、FTP三个最常见的TCP/IP协议下载，并可以使用HTTP代理。wget名称的由来是“World Wide Web”与“get”的结合

```
yum -y install wget
```

## 查看当前内核版本 ##
```
uname -r
```

<!----- more ----->

## 修改内核 ##
```
rpm -ivh http://gongju.wn789.com/neihei/kernel-firmware-2.6.32-504.3.3.el6.noarch.rpm
```

```
rpm -ivh http://gongju.wn789.com/neihei/kernel-2.6.32-504.3.3.el6.x86_64.rpm --force
```

## 查看内核 ##
```
rpm -qa | grep kernel
```

## 安装锐速破解版 ##
```
wget -N --no-check-certificate https://github.com/91yun/serverspeeder/raw/master/serverspeeder.sh && bash serverspeeder.sh
```

## 锐速卸载 ##
```
chattr -i /serverspeeder/etc/apx* && /serverspeeder/bin/serverSpeeder.sh uninstall -f
```

## 查看锐速状态 ##
```
service serverSpeeder status
```

## 锐速相关 ##
```
 service serverSpeeder start | stop | restart
```

## 搭建shadowsocks ##

1. 安装

安装pip
```
yum install python-setuptools && easy_install pip
```

安装shadowsocks
```
pip install shadowsocks
```

2. 配置

创建一个文件在```/etc/shadowsocks.json```
```
cd /etc

vi shadowsocks.json
```

在json文件中写入如下信息

```
{
        "server":"x.x.x.x",            #服务器IP地址
        "server_port":8388,                 #服务监听端口
        "local_port":1080,                  #本地连接端口
        "password":"barfoo",               #加密传输使用到的密码
        "timeout":600,                      #连接超时时间
        "method":"aes-256-cfb"            #加密算法
}
```

3. 启动、停止

```
ssserver -c /etc/shadowsocks.json -d start | stop
```

4. 配置定时任务

在vps的内存比较小的时候，shadowsocks容易被系统给kill掉，这时，我们就要有个定时任务去查看shadowsocks的服务状态

首先，创建一个查看服务脚本

```
#! /bin/sh

proc_name="shadowsocks.json"

number=`ps -ef | grep $proc_name | grep -v grep | wc -l`

if [ $number -eq 0 ];then
        ssserver -c /etc/shadowsocks.json -d start
else
        ssserver -c /etc/shadowsocks.json -d restart
fi
```

给脚本添加运行权限

```
chmod 777 xxxx.sh
```

将脚本加入定时任务中

```
crontab -e  //打开定时任务配置
```

加入上面的脚本

```
*/1 * * * * ./xxx.sh  //注意脚本路径

```

启动定时任务

```
service crond start
```



## 安装使用Apache ##

在Linux中Apache的名字叫 ```httpd```

查看服务器版本
```
apachectl -v    
```

安装Apache
```
yum install httpd
```

打开Apache
```
/etc/init.d/httpd start
或者
httpd -k start | stop | restart
```

Apache的配置文件路径
```
/etc/httpd/conf/httpd.conf
```

## 安装Mysql ##

```
yum list installed | grep mysql#查看是否已经安装
yum install mysql-server#安装服务端
yum install mysql-devel#这个应该是组件，不知道有什么用
yum install mysql#安装客户端

service mysqld start#启动
service mysqld stop#停止
service mysqld restart#重启
mysqladmin -u root password 123456#设置密码
mysql -u root -p #登录
```
