---
title: Linux执行定时脚本
date: 2018-4-11 11:10:12
tags: [Linux]
---

## 前言 ##

新租的的服务器内存有点小，经常在使用一段时间后，就回因为内存不够，导致服务停止，所以写了一个定时启动服务的脚本


<!-- more -->

### 定时服务脚本 ###

这个脚本的作用就是找到当前任务中有没有叫`shadowsocks.json`的任务，如果有，就证明服务没挂，那就重启一下，如果没有，就证明服务挂了，那就启动一下

脚本内容如下:

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

使用`vi`编辑完成后保存为`xxx.sh`


### 给脚本添加执行权限 ###

```
chmod 777 xxx.sh
```

### 添加定时任务 ### 

通过`crontab`命令，在固定时间间隔执行指定的系统指令或`shell`脚本

`crontab`其实就是一个定时任务，如果开启的时候，他会按照配置文件中的时间，定时执行指定脚本

`crontab`服务的开启方法：

```
sudo service crond start     #启动服务
sudo service crond stop      #关闭服务
sudo service crond restart   #重启服务
sudo service crond reload    #重新载入配置
sudo service crond status    #查看服务状态
```

`crontab`配置文件的编辑方法：

```
crontab -l #查看配置文件内容

crontab -e #编辑配置文件内容
```

`crontab`配置文件格式：

```
# .---------------- minute (0 - 59) 
# |  .------------- hour (0 - 23)
# |  |  .---------- day of month (1 - 31)
# |  |  |  .------- month (1 - 12) OR jan,feb,mar,apr ... 
# |  |  |  |  .---- day of week (0 - 6) (Sunday=0 or 7)  OR sun,mon,tue,wed,thu,fri,sat 
# |  |  |  |  |
# *  *  *  *  *  command to be executed

#eg:
* */4 * * * ~/xxx.sh #表示每四分之一小时执行一次xxx.sh脚本
```

### MacOS使用相关

* 脚本的`PATH`可能和在终端运行时候有些区别，导致部分命令无法使用，解决办法是在脚本第一行添加`PATH`

  ```shell
  PATH=/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/bin
  ```

* 脚本的运行路径可能和在终端运行有区别，所以脚本中的路径都要使用绝对路径，如果想使用相对路径，需要手动`cd`到相应的路径，或在配置文件中先`cd`到相应路径

  ```shell
  15 7 * * * cd /home/xxxx/Documents/Scripts && ./email_ip_script.sh
  ```

* MacOS下的`crontab`对脚本路径有要求，比如：`Documents`目录下的脚本都没法执行，所以需要给`crontab`添加所有文件权限，添加方法，`系统偏好设置`-`安全性与隐私`-`隐私` - `完全磁盘访问权限` ，把`/usr/sbin/cron` 拖入其中

* ```shell
    # 启动
    sudo /usr/sbin/cron start
    # 重启
    sudo /usr/sbin/cron restart
    # 停止
    sudo /usr/sbin/cron stop
  ```

  

