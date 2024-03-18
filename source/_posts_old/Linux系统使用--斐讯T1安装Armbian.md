---
title: Linux系统使用--斐讯T1安装Armbian
date: 2018-6-14 11:10:12
tags: [Linux]
categories: [Linux, Armbian]
---

## 前言 ##

之前买过一个斐讯T1，用来当电视盒子配置还是够的，但是我没有看电视的需求，后来发现可以安装`Linux`，于是拿来尝试了一下，增加它的可玩性

<!-- more -->

### 准备工作 ###

* U盘一个
* 斐讯T1
* 电脑

注意：我在安装的时候使用的使用的一个U盘怎么也无法启动到`Linux`，看网上说是对U盘有要求，解决办法就是换一个U盘，所以在多次安装完成后遇到，无法启动`Linux`时，建议更换U盘，我后来换成了TF卡就没有出现无法启动的问题

还有安装的时候斐讯的系统版本不能是最新版的，最好刷一个降级包

### 下载系统 ###

斐讯T1的`cpu`是Amlogic 晶晨S912，对应的系统被国外的大神制作好上传到`Yandex Disk`,所以下载系统可能需要FQ

[系统下载地址](https://yadi.sk/d/pHxaRAs-tZiei)

进去会有各个版本的文件夹，我使用的是`5.91`,所以进入`5.91`文件中，下载需要的系统。

一个版本会有多个种类的系统文件，区别是`Debian`和`Ubuntu`,还有对应的桌面版还是服务版，下载自己需要的一个就好。

### 写入U盘 ###

下载完系统，就可以把写入U盘了

我使用的软件是`balenaEtcher`, [下载地址](https://www.balena.io/etcher/)，选择对应系统的版本就好（是你电脑的系统）

`balenaEtcher`的使用也很简单，第一项选择上面下载好的`linux`系统，第二项选择你的U盘（写入操作会格式化U盘，注意U盘数据备份），接着点击第三项`Flash`,等待写入成功

### 修改dtb ###

写入完成的U盘会有个叫`BOOT`的盘符，这时我们需要修改里面的`dtb`,斐讯T1使用的`dtb`叫做`meson-gxm-q201.dtb`,我们可以在`Boot/dtb/`文件夹中找到。

使用文本编辑器打开`BOOT`盘符中的`boot.ini`文件，修改里面的`setenv dtb_name`为`"/dtb/meson-gxm-q201.dtb"`,修改完该行如下：

```
setenv dtb_name "/dtb/meson-gxm-q201.dtb"
```

使用文本编辑器打开`BOOT`盘符中的`uEnv.ini`文件，修改里面的`dtb_name`为`/dtb/meson-gxm-q201.dtb`,修改完该行如下：

```
dtb_name=/dtb/meson-gxm-q201.dtb
```

使用文本编辑器打开`BOOT`盘符中`extlinux`文件夹下的`extlinux.conf`文件，修改里面的`FDT`为`/dtb/meson-gxm-q201.dtb`,修改完该行如下：

```
FDT /dtb/meson-gxm-q201.dtb
```

### 启动Linux ###

将修改好的U盘插入斐讯T1的usb接口，然后给斐讯通电，斐讯启动会自动进入`Linux`系统，如果没有进入，网上的教程说在安卓的终端输入`reboot update`,斐讯T1会重启，就进入`Linux`系统了，但是我用这个不起作用，所以，我建议，没有启动`Linux`,就更换U盘，或者更换系统试试

### 进入系统 ###

如果启动了`Linux`,等待加载完成，如果加载过程中失败了，则可能是系统问题，建议更换别的版本的`Linux`系统。

如果加载完成了，输入`root`登录超级用户，密码`1234`,登录成功会提示设置新的`root`密码，先输入默认密码`1234`,在输入两次新密码，设定成功。

接下来按照提示，设置新用户名，和密码，已经用户信息。设置完成，就可以用新用户登录了。

如果是有图形界面版本的系统，没有进入桌面，按`ctrl+alt+F7`进入桌面系统

### 设置中文字体 ###

开启中文字体
```
sudo vim /etc/locale.gen # 将里面的zh_CN.UTF-8前面的#去掉
```

更新程序列表
```
sudo apt update
```

更新程序
```
sudo apt upgrade
```

安装字体包（两种字体选一种就好）
```
sudo apt install fonts-noto-cjk

sudo apt install ttf-wqy-zenhei 
```

更新字体列表
```
sudo fc-cache -v
```

生成`locale`文件
```
sudo locale-gen
```

重启系统


### 远程桌面 ###

安装`x11vnc`

```
sudo apt install x11vnc
```

生成vnc密码

```
sudo x11vnc -storepasswd 
```

启动vnc

```
x11vnc -auth guess -forever -loop -noxdamage -repeat -rfbauth /home/USERNAME/.vnc/passwd -rfbport 5900 -shared
```

设置开机自启动

```
#新建service
sudo vim /etc/systemd/system/x11vnc.service

#添加如下内容
[Unit]
Description=Start x11vnc at startup.
After=multi-user.target
 
[Service]
Type=simple
ExecStart=/usr/bin/x11vnc -auth guess -forever -loop -noxdamage -repeat -rfbauth /home/USERNAME/.vnc/passwd -rfbport 5900 -shared
 
[Install]
WantedBy=multi-user.target
```

依次执行下面的命令
```
sudo chmod u+x /etc/systemd/system/x11vnc.service
sudo systemctl daemon-reload
sudo systemctl enable x11vnc.service
```