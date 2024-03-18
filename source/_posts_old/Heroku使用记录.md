---
title: Heroku使用记录
date: 2019-3-17 11:02:30
tags: Linux
---


## 前言 ##

`Heroku`是一个免费的云服务，据说还可以跑`VPN`,但是这不是我这次研究的问题，这次想把`listen1-api`,构建出来的`js`代码部署到上面，当做接口使用

<!-- more -->

### 一 、注册 ###

官网地址 ： [https://www.heroku.com/](https://www.heroku.com/)

注册没有难度，使用国外邮箱，能翻墙就OK


### 二、安装工具 ###

这个工具就是一个能在终端部署的工具`heroku-cli`

工具地址： [https://devcenter.heroku.com/articles/heroku-cli](https://devcenter.heroku.com/articles/heroku-cli)

这个工具和`hexo`是一个意思，但是有一个坑的地方就是，你的终端也要可以FQ,不然登录不上去

终端FQ方法以前记录过，这里在贴一下，方便使用

```
export http_proxy=socks5://127.0.0.1:1080 # 配置http 代理访问
export https_proxy=socks5://127.0.0.1:1080 # 配置https 代理访问
export all_proxy=socks5://127.0.0.1:1080 # 配置http和https访问
unset http_proxy  # 取消http 代理访问
unset https_proxy # 取消https 代理访问
```

上面命令中的端口号和地址，需要和`shadowsock`中`socks5`监听的地址和端口号一致,而且这个只是临时使用，要想长期使用，需要加入到`shell`中

卸载命令：

```
rm -rf /usr/local/heroku /usr/local/lib/heroku /usr/local/bin/heroku ~/.local/share/heroku ~/Library/Caches/heroku
```

### 三 、使用 ###

#### 查看工具版本 ####

```
heroku -v
```

#### 登录 ####

```
heroku login
```

#### 创建程序 ####

```
heroku create app-name
```

创建完成会有两个地址，一个是访问的网址，一个是代码仓库的地址

我的测试的地址是如下

```
https://nodetestdemo.herokuapp.com/ | https://git.heroku.com/nodetestdemo.git
```

#### 推送代码 ####

使用`git`把代码推送到上面生成的仓库中

```
git init
git add .
git commit -m "commit"
git remote add origin https://git.heroku.com/app-name.git
git remote -v
git push -u origin master
```

推送完成后，就可以访问上面的地址，查看代码是否部署成功

