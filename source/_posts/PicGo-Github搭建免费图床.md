---
title: PicGo+Github搭建免费图床
categories: [Hexo]
tags: [日常,Hexo]
date: 2024-02-20 15:30:40
---

## 前言 ##

平时在写博客的时候,经常需要插入一些图片,但是好用的图床通常都是要钱的,这不符合咱的气质,找了半天,发现了用`Github`当图床的方法,毕竟不怕跑路,还免费

<!-- more -->

## 准备工作 ##

1. Github账号
1.1 创建一个仓库用来存放图片
1.2 生成一个token,用来访问仓库
2. PicGo软件

### 生成token

1. 进入"设置"
![](https://raw.githubusercontent.com/2882889/HexoPic/master/hexo20240220161143.png)

2. 点击左边最底部Developer settings

![](https://raw.githubusercontent.com/2882889/HexoPic/master/hexo20240220161336.png)

3. 生成token

![](https://raw.githubusercontent.com/2882889/HexoPic/master/hexo20240220161614.png)

注意: 生成的时候需要勾选repo

### 下载PicGo

访问PicGo的地址[PicGo](https://github.com/Molunerfinn/PicGo),下载对应的软件

安装完成后,按如下设置,将你的仓库信息填写完成

![](https://raw.githubusercontent.com/2882889/HexoPic/master/hexo20240220162533.png)

如果提示软件已经损坏,则需要打开"任何来源",打开方法:
终端输入:

```
sudo spctl --master-disable
```



