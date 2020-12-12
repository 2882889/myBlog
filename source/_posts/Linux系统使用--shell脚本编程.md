---
title: Linux系统使用--shell脚本编程
date: 2018-4-11 11:10:12
tags: [Linux]
---

## 前言 ##

linux的shell脚本编程使用相关

<!-- more -->

### Linux系统使用--shell脚本编程 ###

`shell`脚本编程是利用系统上的命令及编程组件进行编程的过程式编程语言，简单的说`shell`脚本就是命令的堆积，但是需要程序逻辑来判断运行条件是否满足，以避免其运行中发生错误

### shell脚本格式 ###

1. 第一行，顶格给出`shebang(解释器路径)`，用于指明解释执行当前脚本的解释器程序文件
    常见的解释器路径：
    ```
    #!/bin/bash
    #!/usr/bin/python
    #!/usr/bin/perl
    ```
    
### shell脚本运行 ###

1. 赋予执行权限，直接运行

    ```
    chmod +x /PATH/TO/SCRIPT_FILE
    /PATH/TO/SCRIPT_FILE
    ```

2. 直接运行解释器，将脚本以命令行参数传递给解释器程序

    ```
    bash /PATH/TO/SCRIPT_FILE
    ```