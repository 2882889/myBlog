---
title: 阿里云盘挂载到Mac
date: 2021-12-20 12:32:36
tags:
---

### 获取阿里云token

登录到阿里云盘网页版，F12打开调试框，在 `Application`-`Local Storage`-`token`里面找到`refresh_token`的值

### 创建Docker

```
docker create \
    --name=webdav-aliyundriver \
    --restart=always \
    -p 8080:8080 \
    -v /etc/localtime:/etc/localtime \
    -e TZ=Asia/Shanghai \
    -e ALIYUNDRIVE_REFRESH_TOKEN="填写你刚刚获得的token" \
    -e ALIYUNDRIVE_AUTH_PASSWORD="admin" \
    -e ALIYUNDRIVE_AUTH_USER-NAME="admin" \
    -e JAVA_OPTS="-Xmx1g" \
    zx5253/webdav-aliyundriver
```

或使用 yml

在需要创建docker容器的文件夹中创建一个`docker-compose.yml`文件，输入下面的内容：

```
version: '3'
services:
  # https://github.com/zxbu/webdav-aliyundriver
  webdav-aliyundriver:
    image: zx5253/webdav-aliyundriver
    container_name: webdav-aliyundriver
    restart: always
    volumes:
      - /etc/localtime:/etc/localtime
      - ./docker/etc/aliyun-driver/:/etc/aliyun-driver/
    ports:
      - "8080:8080"
    tty: true
    environment:
      - TZ=Asia/Shanghai
      - ALIYUNDRIVE_REFRESH_TOKEN=<change me>
      - ALIYUNDRIVE_AUTH_PASSWORD=<change me>
      - ALIYUNDRIVE_AUTH_USER-NAME=<change me>
```

修改对应token，账号和密码

`cd`到`docker-compose.yml`目录输入：

```sh
docker-compose up -d
```

创建并启动

### Mac下挂载服务

```
地址: http://127.0.0.1:8080
账号: admin
密码: admin
```



1. 在 Mac 上的“访达”<img src="https://help.apple.com/assets/605932B4A1B7A93F492858E8/605932C0A1B7A93F492858FF/zh_CN/058e4af8e726290f491044219d2eee73.png" alt="img" style="zoom:33%;" />中，选取“前往”>“连接服务器”，在“服务器地址”栏中输入服务器的地址，然后点按“连接”。
2. 在“服务器地址”栏中输入服务器的地址。WebDAV 服务器地址应该与以下的格式类似：http://servername.com/path/
3. 点按“连接”。

