---
title: Flutter的安装
date: 2018-11-10 19:26:12
tags: [Flutter]
---

## 前言 ##

记录一下安装`flutter`的过程，方便以后查询

<!-- more -->

### 安装 ###

下载`flutter`:

终端输入：

```
git clone -b beta https://github.com/flutter/flutter.git
```

添加临时环境变量：

```
export PATH=`pwd`/flutter/bin:$PATH
```

注意：
`pwd`表示当前路径，后面的`flutter`就是你git下来的文件夹，里面会有一个`bin`文件夹，这里如果git下来的文件夹不叫`flutter`，需要改上面的命令为对应的文件名称，而且要和`pwd`打印出来的路径能连接上

检查依赖：

```
flutter doctor
```

这一步可能需要翻墙，解决办法是[添加这两个镜像](https://flutter.io/community/china)

```
 export PUB_HOSTED_URL=https://pub.flutter-io.cn
 export FLUTTER_STORAGE_BASE_URL=https://storage.flutter-io.cn
```

如果`flutter doctor`跑出什么问题，按照提示解决就好

如果是如下错误，需要在`android studio`中添加插件

```
    ✗ Flutter plugin not installed; this adds Flutter specific functionality.
    ✗ Dart plugin not installed; this adds Dart specific functionality.
[!] Connected device
    ! No devices available
```

如果需要安装`dart`点同意就好，如果没有其他错误，就表示`flutter`安装成功

