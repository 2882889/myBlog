---
title: MAC使用密钥登录VPS
date: 2019-02-12 21:30:12
tags: [Linux]
---

## 前言 ##

今天登录`VPS`,突然提示如下信息

```
Last failed login: Tue Feb 12 07:55:56 EST 2019 from 218.92.1.143 on ssh:notty
There were 49 failed login attempts since the last successful login.
```

貌似被别人跑猴子了，所以我就关了密码登录

<!-- more -->

### 使用密钥登录 ###

`MAC`默认是有一个密钥的，没有的话先生成一对

接下运行如下命令：

```
ssh-copy-id root@X.X.X.X
```

`root`是你VPS的用户名，后面是`IP`

运行完会提示输入`VPS`的密码

如下

```
/usr/bin/ssh-copy-id: INFO: Source of key(s) to be installed: "/Users/xxx/.ssh/id_rsa.pub"
/usr/bin/ssh-copy-id: INFO: attempting to log in with the new key(s), to filter out any that are already installed
/usr/bin/ssh-copy-id: INFO: 1 key(s) remain to be installed -- if you are prompted now it is to install the new keys
root@X.X.X.X's password:

Number of key(s) added:        1

Now try logging into the machine, with:   "ssh 'root@X.X.X.X'"
and check to make sure that only the key(s) you wanted were added.
```

接下来尝试登录：

```
ssh 'root@X.X.X.X'
```

没有问题会直接登录成功

### 关闭密码登录 ###

打开`VPS`的`ssh`配置文件

```
vi /etc/ssh/sshd_config
```

找到`PasswordAuthentication`改`yes`为`no`,如果没有就手动添加上

然后保存重启`ssh`服务

```
systemctl restart sshd.service
```
