---
title: 吃黑苹果--Clover美化
date: 2019-7-11 23:10:12
tags: [黑苹果]
---

## 前言 ##

`Clover`是用来引导黑苹果的，但是默认设置有时候会满足不了我们的需求，所以需要美化

<!-- more -->

### 修改Clover分辨率 ###

1. 关闭`CSM`， `CSM`是`BIOS`的一个设置项，它是一个兼容支持模块，可以让新的`UEFI BIOS`兼容老的`legacy+MBR`启动模式。关闭方法在:`BIOS`中设置
    
2. 使用`Clover configurator`，打开`‎⁨EFI⁩ ▸ ⁨EFI⁩ ▸ ⁨CLOVER ▸ config.plist`,在左边点击`GUI`栏，然后在右边`Screen Resolution`设置需要的分辨率

### 修改默认启动系统 ###

1. 使用`Clover configurator`，打开`‎⁨EFI⁩ ▸ ⁨EFI⁩ ▸ ⁨CLOVER ▸ config.plist`，在左边点击`boot`栏，在右边找到`Default Boot Volume`，选择`LastBootedVolume`，

2. 在右边找到`Timeout`，并设置需要延迟的时间，如果勾选`-1`则不延迟，如果选择`Fast`则不显示`Clover`,快速启动系统

### 查找所有启动项 ###

1. 使用`Clover configurator`，打开`‎⁨EFI⁩ ▸ ⁨EFI⁩ ▸ ⁨CLOVER ▸ config.plist`，点击左边的`Boot.log`,再点击右下角的`Generate log`

2. 从里面搜索`ScanLoader`，`ScanLoader`下面的`[xx]:启动项`就是启动项名


### 隐藏无用启动项 ###

1. 使用`Clover configurator`，打开`‎⁨EFI⁩ ▸ ⁨EFI⁩ ▸ ⁨CLOVER ▸ config.plist`，点击左边的`Gui`栏，在右边的`Hide Volume`里填入上面查找到的想要隐藏的启动项名称

### 修改Clover主题 ###

1. 下载喜欢的`clover`主题，并将文件夹放到`‎⁨EFI⁩ ▸ ⁨EFI⁩ ▸ ⁨CLOVER ▸ themes`文件夹中

2. 修改`‎⁨EFI⁩ ▸ ⁨EFI⁩ ▸ ⁨CLOVER ▸ config.plist`，点击左边的`Gui`栏，在右边`Theme`填入对应主题的文件夹名称

3. 如果找不到喜欢的主题，可以试试`Clover Theme Manager`,这个软件
