---
title: anaconda的安装和使用
date: 2021-04-12 18:43:57
tags:
---

## 前言 ##

最近在搭建一个`python`工程的环境的时候，发现需要使用一些指定版本的软件环境，所以就想到了使用`Anaconda`

<!-- more -->

### 简介 ###

> `Anaconda`指的是一个开源的[Python](https://baike.baidu.com/item/Python)发行版本，其包含了[conda](https://baike.baidu.com/item/conda/4500060)、Python等180多个科学包及其依赖项。 [1] 因为包含了大量的科学包，Anaconda 的下载文件比较大（约 531 MB），如果只需要某些包，或者需要节省带宽或[存储空间](https://baike.baidu.com/item/存储空间/10657950)，也可以使用**Miniconda**这个较小的发行版（仅包含conda和 Python）。

### 安装 ###

`Anaconda`有两种安装方式，都可以从[官网下载](https://www.anaconda.com/products/individual#Downloads)

#### 图形界面 ####

双击下载好的`pkg` ,然后无脑下一步就好

#### 命令行 ####

下载好命令行安装包，其实就是一个`sh`脚本,然后运行安装

```
bash ~/Downloads/xxx.sh
```

### 使用 ###

安装完没有任何反应，在终端敲`conda --version` 也显示找不到`conda`命令

这时候需要在终端启动一下

```shell
//如果安装的时候，选的是为所有人安装，则运行此命令
source /opt/anaconda3/bin/activate

//如果安装的时候，选的是自己的账号，则运行此命令，可以用 echo $USER 查看username
source /Users/my_username/opt/anaconda3/bin/activate

//初始化一下命令行 zsh 使用：
conda init zsh
//bash shell 使用
conda init

//查看是否设置成功
conda list

```

#### 环境管理 ####

##### 创建新环境 #####

```sh
conda create --name <env_name> <package_names>

//eg:
conda create -n python python=3.8

```

##### 切换环境 #####

```sh
source activate <env_name>

//eg:
source activate python

```

##### 查看环境 #####

```sh
conda info --envs
//or
conda info -e
//or
conda info --envs
```

##### 退出环境 #####

```
source deactivate
```

##### 删除环境 #####

```
conda remove --name <env_name> --all
```





