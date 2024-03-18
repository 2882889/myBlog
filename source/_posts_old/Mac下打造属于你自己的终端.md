---
title: Mac下打造属于你自己的终端
date: 2018-03-16 22:46:17
tags: Linux
---

## item2的自定义样式 ##

让你的终端用上主题

<!-- more -->

### 准备工具 ###

1. item2

	item2是Mac下超好用的一款终端产品,而且是免费的,你可以[点击这里](http://www.iterm2.com/) 下载到.

2. oh-my-zsh
	
	oh-my-zsh是一个管理自己zsh的工具,可以方便得管理自己的item2样式主题
	
3. Powerline (可选)

	Powerline是一个字体补丁,不安装有可能出现乱码,但不是所有的主题都需要
	
	
### 安装过程 ###

#### 安装oh-my-zsh ####

首先下载item2,从上面的官网点击`download`,然后解压下载下来的解压包,拷贝到应用程序中,item2就安装完成了

接着安装oh-my-zsh,直接在终端输入下面的命令

```
curl -L https://github.com/robbyrussell/oh-my-zsh/raw/master/tools/install.sh | sh
```

如果想要卸载oh-my-zsh需要在终端输入下面的命令

```
uninstall_oh_my_zsh 
```

oh-my-zsh安装完成会有如下提示:

```
Please look over the ~/.zshrc file to select plugins, themes, and options.
```

此时你的oh-my-zsh就安装完成了

关掉你的item2然后再重新启动一下看看,样子是不是已经发生变化了

#### 配置oh-my-zsh ####

oh-my-zsh安装完成会有一个默认主题的,上面重启之后看到的主题就是默认的主题,下面我们配置一下自定义主题


安装完成后在你的用户目录下面(就是通常的```~```下面)会多出来一些和zsh相关的文件夹和文件

`.oh-my-zsh`这个文件夹里面存放着oh-my-zsh的主题文件夹`themes`,配置模板`templates`,字体文件夹`plugins`,日志`log`,库`lib`,工具`tools`等

`.zshrc`这个文件是`oh-my-zsh`的配置文件

好知道了这些就可以更换主题了:

1. 创建一个zsh配置文件到`~`目录

	使用`cp`命令拷贝`~/.oh-my-zsh/templates/zshrc.zsh-template`这个文件到`~/.zshrc`
	
	输入:
	
	```
	cp ~/.oh-my-zsh/templates/zshrc.zsh-template ~/.zshrc
	```
	
	注意: 如果你的`~/.zshrc`存在,最好使用下面命令备份一下
	
	```
	cp ~/.zshrc ~/.zshrc.orig
	```
	
2. 设置zsh为默认的shell

	输入:
	
	```
	chsh -s /bin/zsh
	```
	
3. 查看主题

	到主题文件夹目录中选一个主题
	
	输入:
	
	```
	cd ~/.oh-my-zsh/themes
	```
	
	查看并选择一个想要设置的主题
	
4. 修改配置文件
	
	用vim打开oh-my-zsh的配置文件,修改主题名字
	
	输入:
	
	```
	vim ~/.zshrc
	```
	
	修改`ZSH_THEME="xxx"`一行引号中的内容为想要设置的主题名字,保存退出,重启item2.
	
	
	附录:
	
	想知道每个主题的样式可以点击这里,[官方对照表](https://github.com/robbyrussell/oh-my-zsh/wiki/themes)
	
	如果有自己下载的主题也可以放到`~/.oh-my-zsh/themes`中然后按照上面的方法设置
	
	如果有的主题显示乱码,就需要上面的字体补丁,安装方法
	
	输入:
	
	```
	pip install powerline-status
	```
	
	如果提示:
	
	```
	zsh: command not found: pip
	```
	
	则先安装pip,输入:
	
	```
	sudo easy_install pip
	```











	


  