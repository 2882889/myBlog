---
title: Hexo的安装和使用
date: 2016-08-02 19:13:00
categories: [Hexo]
tags: [日常,Hexo]
---

### 一、hexo的安装

#### 1、安装Node.js

Hexo是基于`Node.js`的静态博客框架，所以要安装`Hexo`先要安装`Node.js`。


Node.js的安装方法非常简单，从[Node.js官网](https://nodejs.org/en/)下载安装包安装就好

#### 2、安装git

mac下安装了Xcode就会有git

<!-- more -->


#### 3、安装Hexo

在终端执行如下命令

```
sudo npm install -g hexo
```

等待安装成功

安装完成后使用查看版本命令，看看是否安装成功

```
hexo -v
```

如果正常显示版本说明安装成功

#### 注意

如果报如下错误：

```
Error: The module '/Users/***/Desktop/***/node_modules/dtrace-provider/build/Release/DTraceProviderBindings.node'
was compiled against a different Node.js version using
NODE_MODULE_VERSION 48. This version of Node.js requires
NODE_MODULE_VERSION 67. Please try re-compiling or re-installing
the module (for instance, using `npm rebuild` or `npm install`).
    at Object.Module._extensions..node (internal/modules/cjs/loader.js:751:18)
    at Module.load (internal/modules/cjs/loader.js:620:32)
    at tryModuleLoad (internal/modules/cjs/loader.js:560:12)
    at Function.Module._load (internal/modules/cjs/loader.js:552:3)
    at Module.require (internal/modules/cjs/loader.js:658:17)
    
    ...
```

执行下面操作：

```
1. cd hexo 根目录
2. rm -rvf node_modules
3. npm install

```

这是因为node_modules的缓存存在版本问题


### 二、hexo的使用

#### 创建一个blog库

```
hexo init blog
```

blog是文件夹的名称，可以随便起

#### 创建一个blog

```
hexo new blog_title
```

这个样就可以创建一个title是blog_title的博客了

这个只是一个默认参数的简写命令

#### 生成静态页面

```
hexo generate		
或者：
hexo g
```

#### 部署到本地

```
hexo server			
或者：
hexo s
```

部署完可以在浏览器中打开预设的地址查看blog


#### 部署到远端

```
hexo deploy			
或者：
hexo d
```

要部署到远端需要配置博客更目录下的`_config.yml`文件中的内容

```
deploy:
  type: git
  repo: 自己的git地址
  branch: master
```

#### 2021更新问题

今天在一台新电脑上部署的时候发现进入的是空白页，在本地测试的时候有如下错误提示

```sh
(node:9876) Warning: Accessing non-existent property 'lineno' of module exports inside circular dependency
(Use `node --trace-warnings ...` to show where the warning was created)
(node:9876) Warning: Accessing non-existent property 'column' of module exports inside circular dependency
(node:9876) Warning: Accessing non-existent property 'filename' of module exports inside circular dependency
(node:9876) Warning: Accessing non-existent property 'lineno' of module exports inside circular dependency
(node:9876) Warning: Accessing non-existent property 'column' of module exports inside circular dependency
(node:9876) Warning: Accessing non-existent property 'filename' of module exports inside circular dependency
```

在`github`上看，部署的`index`文件没有内容,

问题原因: `node.js`版本太高了

解决办法一: 降级`node.js`

步骤：

```sh
//安装node.js版本管理软件 n
sudo npm install -g n

//查看n 是否安装成功
n -V

//安装指定版本 node
sudo n 13.14

```

解决办法二: 

修改package.json中的`hexo-renderer-stylus`的版本号为`^2.0.1`, 然后重新npm安装





