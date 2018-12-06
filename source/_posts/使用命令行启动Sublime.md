---
title: 使用命令行启动Sublime
date: 2017-10-06 22:10:38
tags: [日常]
---


### 使用终端启动Sublime ###

工作中需要经常使用终端，有时候查看文本文件又需要使用`Sublime`,所以可以用终端调用`Sublime`是一件很方便的事情


#### 准备工作 ####

系统：mac ox

软件：sublime

<!-- more -->

#### 第一步 查看终端是否能打开Sublime ####

打开终端，`cd`到任意一个文件夹，输入如下命令：

```
/Applications/Sublime\ Text.app/Contents/SharedSupport/bin/subl .
```

这时候如果`Sublime`成功的打开了当前命令所在的文件夹，就说明终端启动成功。

如果无法启动，请查看sublime的路径和名称是否和你安装的相同

`.`表示打开当前文件夹，如果输入具体文件名称，也可以打开具体文件


#### 第二步 查看终端环境变量 ####

在终端输入：

```
vim ~/.bash_profile
```

在里面找到当前系统的环境变量

```
export PATH= 
```

注意：如果你安装过`zsh`,那么你的环境变量应该到`~/.zshrc`中寻找

如果你的环境变量文件编辑了，那么你需要`source 环境变量文件`一下你的环境变量文件，使他生效

#### 第三步 创建命令 ####

终端执行：

```
ln -s /Applications/Sublime\ Text.app/Contents/SharedSupport/bin/subl /usr/local/bin/subl
```

然后就搞定了。

前面的路径是sublime的安装路径，后面的路径是设置好的环境变量

在终端中使用下面命令测试一下

```
subl .
```

如果成功打开当前路径文件夹就说明成了，以后使用`subl 文件名`就可以快速的在终端用`Sublime`打开文件啦


#### 参考文件：

1. [Open Sublime Text from Terminal in macOS](https://stackoverflow.com/questions/16199581/open-sublime-text-from-terminal-in-macos/41288133)

2. [mac中的环境变量](https://blog.csdn.net/hlllmr1314/article/details/52228672)

3. [sublime 官网描述](http://www.sublimetext.com/docs/3/osx_command_line.html)




