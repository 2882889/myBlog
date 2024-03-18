---
title: 微信聊天记录备份计划
date: 2023-09-21 12:13:26
---

## 前言

微信




### 9月21日

下载Mac版微信,然后使用`class-dump`获取头文件,

```
./class-dump -H xxx -o xxx
```

报错:

```
Error: Cannot find offset for address 0x1000000001045943 in stringAtAddress:
```

解决方法:

使用修改版的`class-dump`

[方案来源](https://iosre.com/t/solved-classdump-error-cannot-find-offset-for-address-xxxxx-in-stringataddress/10626/3)

[下载地址](https://github.com/AloneMonkey/MonkeyDev/blob/master/bin/class-dump)
