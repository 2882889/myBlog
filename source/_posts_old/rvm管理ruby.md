---
title: rvm管理ruby
date: 2018-08-13 20:13:12
tags: [Linux]
---

## 前言

最近工程更新开发环境，需要使用`ruby`的2.4.0以上的版本，而本地的开发环境是2.3.0，记录一下更换`ruby`方法

<!-- more -->


### 1、安装rvm ###

rvm就是Ruby Version Manager，是Ruby的版本管理器

查看rvm版本：

```
rvm -v
```

安装rvm

```
curl -L get.rvm.io | bash -s stable
```

更新修改配置文件使之生效

```
source ~/.bashrc  
source ~/.bash_profile
```

安装完成后可以再次查看rvm版本，判断是否安装成功

### 2、升级ruby ###

查看ruby当前版本,以及本地所安装的所有版本

```
rvm list
```

查看ruby所有版本

```
rvm list known
```

安装新版本ruby 2.4.4

```
rvm install 2.4.4
```

切换ruby到需要的版本

```
rvm --default use 2.4.4
```
