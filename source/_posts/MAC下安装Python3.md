---
title: MAC下安装Python3
date: 2018-03-24 22:32:17
tags: Linux
---

## 前言 ##
`MAC os`本身是自带`Python`的,但是不是`Python3`，这里记录一下安装`Python3`的步骤

<!-- more -->

### 1、安装Xcode ###

步骤：

1）、打开App Store 搜索`Xcode`,点击安装，安装完成后打开

如果是第一次打开`Xcode`需要同意`License Agreement`


2）、然后安装`Xcode command line tool`

在终端输入

```
xcode-select --install
```

在弹出的窗口中点击`安装`等待完成

### 2、安装HOMEBREW ###

HomeBrew的介绍和安装方法可以在其官网找到 [HomeBrew官网](https://brew.sh/index_zh-cn)

1）、在终端输入：

```
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```

等待`HomeBrew`安装完成

2）、验证安装结果，在终端输入：

```
brew doctor
```

如果显示`Your system is ready to brew.` 代表`Homebrew`安装成功


### 3、安装Python ###

如前言所说其实`MAC os`是自带一个Python的,我们可以使用下面命令查看Python版本

```
python --version
```

首先安装Python 终端输入：

```
brew install python
```

完成后Python3就会被安装到`/usr/local/Cellar`目录下

输入`which python3`查看python3的命令路径


### 4、修改系统路径 ###

首先查看系统路径，输入：

```
echo $PATH
```

显示出来的路径就是系统搜索命令的路径

如果里面没有Python3的路径，就需要我们手动添加一下

输入：

```
sudo emacs /etc/paths
```

把Python3的路径添加进去

然后按：ctrl+x+c 回复y保存退出

### 5、注意 ###

TensorFlow在Python3.7下目前不支持，而brew的新版本又不支持指定版本Python安装，所以最简单的解决办法是从[Python官网](https://www.python.org/downloads/mac-osx/)下载3.6版本的pkg安装，pkg安装的路径是`/Library/Frameworks/Python.framework/`


移除brew安装的Python

```
#移除所有版本Python
brew uninstall --force python 
```