---
title: appium测试时候的那些事
date: 2017-01-11 11:10:12
tags: appium
---

# 遇到的问题 #

## 1.org.openqa.selenium.WebDriverException: Method has not yet been implemented ##

<!----- more ----->

在使用appium跑java脚本的时候,如果出现异常,提示如上面显示的,说明你的appium安装出现了问题,具体原因是我的appium当时卸载的时候没有完全卸载干净,导致appium无法连接到WebDriverException

解决方法: 卸载appium ,最好再使用清理工具如:cleanMyMac 清理一下残留文件,然后从新安装新的appium ,问题就可以解决,如果还不行,可以新建一个电脑账户,在新的账户中重新搭建appium也可以