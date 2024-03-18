---
title: Hexo创建一个blog
date: 2016-08-03 20:38:44
categories: [Hexo]
tags: [日常,Hexo]
---


### post布局

当你的Hexo已经搭建完成的时候，你就可以写blog了，最简单的创建一个blog的方法就是调用

```
hexo new blog_title
```

执行完成之后，hexo会在根目录的`source`文件夹下创建一个`_post`文件夹，并在里面生成一个`blog_title.md`文件。

<!-- more -->

这个创建blog的完整命令是

```
hexo new post blog_title
```

这个就是post布局，当`hexo g 或者 s 或者 d`的时候，会把`_post`文件夹中的文件都生成静态页面

### draft布局

```
hexo new draft blog_title
```

当使用`draft`布局创建一个博客的时候，hexo也会在根目录的`source`文件夹下创建一个`_draft`文件夹，并在里面生成一个`blog_title.md`文件，

但是在`hexo g 或者 s 或者 d`的时候，是无法看到你创建的博客的

因为`draft`布局是草稿布局，在`_draft`文件夹中的博客都认为是草稿，不能发布

那当你想看看你的草稿写出来的效果怎么样的时候，你可以使用下面命令将草稿部署到本地

```
hexo g --draft
hexo s --draft
```

如果你的博客已经完成了，你可以使用下面的命令将他发布

```
hexo publish draft blog_title.md
```

### page布局

```
hexo new page "about"
```

当使用`page`布局创建一个博客的时候，hexo会在根目录的`source`文件夹下创建一个`about`文件夹，并在里面生成一个`index.md`文件

当你部署完，你需要使用子路径去访问他

eg：

```
http://localhost:4000/about
```

### 布局文件

在新建文章时，Hexo 会根据 scaffolds 文件夹内相对应的文件来建立文件，例如：

```
hexo new photo "My Gallery"
```


