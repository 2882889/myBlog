---
title: Linux系统使用--Bash基础
date: 2018-4-11 11:10:12
tags: [Linux]
---


## Linux系统使用--Bash基础 ##

### bash的特性 ###

命令行展开： ~ {}
命令别名： alias，unalias
命令历史： history
文件名通配：glob
快捷键：Ctrl+a,e,u,k,l
命名补全： $PATH
路径补全： Tab

####命令hash####
bash 会将使用过的命令缓存下来，再次输入命令的时候会先从缓存中去寻找命令，如果没有找到再去$PATH中去自左向右寻找

查看缓存历史命令:
```
hash
-d //删除缓存中的指定命令
-r //清空缓存
```

#### 变量 ####
bash的变量无需事先声明
bash把所有变量统统视作字符类型
bash不支持浮点数据，需借助外在工具
变量名只能包含数字，字母和下划线，且不能以数字开头


bash变量类型:
    一、本地变量: 作用域为当前shell进程
        变量使用: `${var_name}, 或$var_name, ""变量名会替换为其值, ''变量名不会替换为其值`
        查看变量: `set`
        撤销变量: `unset name`
    二、环境变量: 作用域为当前shell进程及其子进程
        变量赋值:
            1. export name=value
            2. name=value
               export name
            3. declare -x name=value
            4. name=value
               declare -x name  
        注意: bash内嵌了许多环境变量(通常为全大写字符)，用于定义bash的工作环境。eg: `PATH, HISTFILE, HISTSIZE, HISTFILESIZE, HISTCONTROL, SHELL, HOME, UID, PWD, OLDPWD`
        查看环境变量:
        ```
        export
        declare -x
        printenv
        env
        ```
        撤销变量: `unset name`
        只读变量:
        1. declare -r name
        2. readonly name
        只读变量无法重新赋值，且不支持撤销，存活时间为当前shell进程的生命周期，随shell进程终止而终止
    三、局部变量: 作用域为函数
    四、位置参数变量: 当执行脚本的shell进程传递的参数
    五、特殊变量: shell内置的有特殊功用的变量 eg:`$? 表示命令运行状态0表示成功，1-255表示失败`
    
    
#### 多命令执行 ####
```
~]# COMMAND1; COMMAND2; COMMAND3; ...
```

短路法则:
```
~]# COMMAND1 && COMMAND2 //如果命令1失败，则命令2不执行

~]# COMMAND1 || COMMAND2 //如果命令1成功，则命令2不执行
```

### bash的配置文件 ###

登录类型：
    交互式登录shell进程：
        直接通过某终端输入账号和密码后打开的`shell`进程
        使用`su - USERNAME`或者`su -l USERNAME`执行的登录切换
    非交互式登录shell进程：
        使用`su USERNAME`执行登录切换
        图形界面下打开的终端
        运行脚本
        
1. `profile`类：
    全局：对所有用户都生效
    
        ```
        /etc/profile
        /etc.profile.d/*.sh
        ```
    
     用户个人：仅对当前用户有效
     
        ```
        ~/.bash_profile
        ```
    
作用：
1. 用于定义环境变量
2. 运行命令或脚本


2. bashrc类
    全局：
    
    ```
    /etc/bashrc 
    ```
    
    用户个人：
    
    ```
    ~/.bashrc
    ```
    
作用：
    1. 定义本地变量
    2. 定义命令别名

    
加载顺序：

交互式

```
/etc/profile --> /etc/profile.d/* --> ~/bash_profile --> ~/.bashrc --> /etc/bashrc
```

非交互式

```
~/.bashrc --> /etc/bashrc --> /etc/profile.d/*
```

让`shell`进程重读配置文件

```
~]# source /PATH/FROM/CONF_FILE
~]# . /PATH/FROM/CONF_FILE
```