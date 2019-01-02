---
title: SSH Key
date: 2018-06-01 15:41:23
tags: [Linux]
---

## 前言 ##
记录SSH的使用

<!-- more -->

### SSH Key 的生成 ###

1.  一个`ssh key `是由两个文件组成,一个是私钥名字默认是`id_rsa`,另一个就是公钥默认名字是`id_rsa.pub`,两个是配套使用的

2.  Mac系统默认秘钥的存放路径是`~/.ssh`

3.  如果在存放路径中有公钥和私钥就不需要生成,可以直接使用,如果没有就需要使用下面命令生成一个
4. 
```
ssh-keygen -t rsa -C "your_email@example.com"
```

4. 敲完回车首先会让你填写一个文件名,就是生成的ssh文件的文件名,不填写默认会是`id_rsa`和`id_rsa.pub`或`id_dsa`和`id_dsa.pub`

    注意:这里的文件如果输入了文件名,生成的公钥和私钥文件存放的位置是当前命令的位置

5.  输入完文件名回车后会让你输出密码和确认密码,可以为空,但是如果输出了,每次使用ssh的时候都会让你输入密码

6. 输入完密码生成一个ssh key文件就大功告成了

7. 最后会给你一个`randomart image`这个东西是用来方便比对key是否一样的,以为人们对不图片比对不字符串的接受更容易

### SSH Key 的参数 ###

` -t xxx `xxx可以是`rsa` `rsa1` 或`dsa`,这个是几种不同的加密算法

`-C "your_email@example.com"`生成的时候添加一个注释

`-f ~/.ssh/id_rsa_soho` 指定保存文件名和位置

其他参数暂时用不到,就不介绍了


### 多个SSH Key 同时使用 ###

如果需要使用多ssh文件给不同的服务器时,就要使用配置文件了

1.  在`~/.ssh`目录创建一个名字为d`config`的文本文件

2. 里面写如下内容

```
# 配置github.com
Host github.com
# 这个是真实的域名地址                 
    HostName github.com
# 这是id_rsa的路径   
    IdentityFile ~/.ssh/id_rsa_github
# 配置登录时用什么权限认证--可设为publickey,password,publickey,keyboard-interactive等
    PreferredAuthentications publickey
# 配置使用用户名
    User username1

# 配置git.oschina.net 
Host git.oschina.net 
    HostName git.oschina.net
    IdentityFile ~/.ssh/id_rsa_oschina
    PreferredAuthentications publickey
    User username2
```

每个`ssh`文件单独配置一个`Host`，`Host`可以随便起，只要配置好`HostName`和`IdentityFile`两个属性即可

注意：如果 `Host mygithub`这样定义的话，命令如下，
git的命令应该写成:`git clone git@mygithub:123.git`

3. 测试配置是否成功,使用如下命令:

```
ssh -T git@github.com
```
 　　　　　　　　　　


附录:

Mac下开启/关闭 显示隐藏文件的方法

```
显示：defaults write com.apple.finder AppleShowAllFiles -bool true
隐藏：defaults write com.apple.finder AppleShowAllFiles -bool false 
```
