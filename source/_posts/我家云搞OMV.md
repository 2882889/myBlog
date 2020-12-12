
---
title: 我家云搞OMV
date: 2020-11-11 23:10:12
tags: [我家云,OMV,armbian]
---

## 前言 ## 

最近闲的蛋疼，收了两个矿渣我家云，想着玩玩NAS，这里记录一下折腾过程

网上有一些现成的教程，都是大佬折腾好的，直接刷入，就可以使用了，基本没什么坑，但是我在使用过程中，发现`transmission`老是爆内存，然后被系统杀死，一个不能挂PT的NAS还是一个好NAS么，所以我打算自己搞一个OMV看看能解决这个问题不


<!-- more -->

## 安装linux

我家云的CPU是RK3328，多少核心啥的，自己百度一下吧，但是重点是，这个U是开源的，也就是你可以定制自己的系统，国外这里有详细的介绍，包括刷机，[点击这里](https://roc-rk3328-cc.readthedocs.io/en/latest/resource.html)

刷linux系统你需要如下东西：

硬件：
1. 我家云，
2. 公对公USB线一条
3. Windows电脑一台

软件：
1. DriverAssistant （驱动）
2. AndroidTool （刷机软件）
3. boot.img (引导镜像)
4. linux.img (系统镜像)

刷机过程：

首先，运行`DriverInstall.exe`安装驱动，然后，我家云用usb线连接电脑（使用白色2.0接口），运行刷机软件，选则上面两个镜像，按住我家云reset键，接入电源，软件找到设备，点击执行，等待刷写完成

成功以后，我家云接入有线网，使用ssh登录

```
ssh root@我家云ip
密码默认 1234
```
这里就进入了我家云linux系统

执行
```
apt update 
apt upgrade
```

更新系统

## omv安装

、、、
<!--下载key-->
wget http://packages.openmediavault.org/public/archive.key
<!--添加key-->
apt-key add archive.key
<!--创建服务列表-->
vim /etc/apt/sources.list.d/openmediavault.list
<!--在列表中添加下面两个源-->
deb http://packages.openmediavault.org/public arrakis main
deb http://packages.openmediavault.org/public usul main
<!--更新源-->
apt update
<!--安装omv-->
apt install openmediavaul
、、、

漫长的等待之后，就安装好了OMV，用浏览器访问我家云ip，就能看到登录页面。
账号： admin
密码： openmediavault

## 安装OMV-Extras

OMV5默认是没有OMV-Extras的，需要我们自己安装
[官网](https://omv-extras.org/)给出的教程是这样的，在终端使用root用户运行下面命令

```
wget -O - https://github.com/OpenMediaVault-Plugin-Developers/packages/raw/master/install | bash
```

显示`done`就表示完成了

## OMV的使用

在终端对OMV做一些配置，可以使用如下命令

```
omv-firstaid
```

如果出现ip无法访问OMV，可以使用这个命令初始化端口和软件


## docker安装

安装完`OMV-Extras`在OMV的web页面，左边系统栏下面会多一个`OMV-Extras`的选项，点击进入，然后选择docker，选择安装，就会自动安装完成docker，同样的可以把portainer安装完成，portainer是docker的可视化管理工具，可以用来创建容器，安装镜像

## transmission安装

进入portainer管理页，选择local，在`container`容器中，新建一个容器，名字随便输入，image镜像输入[docker hub](https://hub.docker.com/)中搜索到的名字`linuxserver/transmission`，从dockerhub说明页中的配置信息可以看到，transmission,需要映射9019，51413，51413udp，三个端口，同时在volumes中将对的的路径映射到本地，添加env变量，接下来部署，等待完成就可以使用transmission了

## 其他

网上有个设置好的`omv`但是，使用过程中风扇一直狂转，声音有些大，所以我找到一个改风扇的文件，估计就是恩山的，但是恩山要币，我下载不下来，不知道是不是同一个，使用方法

在系统 - 计划任务中，停掉`/opt/fan`任务，启动放入文件的任务

