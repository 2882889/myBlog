---
title: hexo部署报错解决
date: 2017-12-06 23:21:15
tags: [日常]
---

## 前言 ##

由于换了一个电脑搭建`hexo`,在部署的时候报错了，这里记录一下解决办法：

删除`.deploy_git`

<!-- more -->

### 报错操作 ###

从`git`拉下代码，在新搭建的`hexo`环境中`hexo d`部署的时候报一下错误

```
~/Desktop/myBlog (master ✘)✖✹✭ ᐅ hexo d
INFO  Deploying: git
INFO  Clearing .deploy_git folder...
INFO  Copying files from public folder...
fatal: in unpopulated submodule '.deploy_git'
FATAL Something's wrong. Maybe you can find the solution here: http://hexo.io/docs/troubleshooting.html
Error: fatal: in unpopulated submodule '.deploy_git'

    at ChildProcess.<anonymous> (/Users/xxx/Desktop/myBlog/node_modules/hexo-util/lib/spawn.js:37:17)
    at ChildProcess.emit (events.js:182:13)
    at maybeClose (internal/child_process.js:978:16)
    at Socket.stream.socket.on (internal/child_process.js:395:11)
    at Socket.emit (events.js:182:13)
    at Pipe._handle.close (net.js:616:12)
```

### 解决办法 ###

解决办法很简单，把博客目录下的`.deploy_git`删除重新构建部署就好，命令如下

```
rm -rf .deploy_git
hexo g
hexo d
```

看来以后要把这个文件夹添加到`.gitignore`文件中试试
