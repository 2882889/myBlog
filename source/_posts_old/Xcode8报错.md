---
title: Xcode8 报错resource fork, Finder information, or similar detritus not allowed
date: 2017-03-31 11:02:30
tags: [iOS]
---

Xcode8 报错resource fork, Finder information, or similar detritus not allowed

## 原因 ##

这是因为Xcode8引进的新的安全机制, Code signing不允许app的bundle里面的文件有未知的文件系统扩展属性

<!-- more -->

## 解决方法 ##
在终端中输入`xattr -lr <path_to_app_bundle>`可以查看存在未知扩展属性的文件

在终端中输入`xattr -cr <path_to_app_bundle>`可以移除所有未知的扩展属性

然后清理Xcode 重新build 工程
