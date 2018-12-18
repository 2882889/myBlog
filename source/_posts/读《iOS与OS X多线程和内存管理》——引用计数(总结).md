---
title: 读《iOS与OS X多线程和内存管理》——引用计数(总结)
date: 2017-01-11 11:10:12
tags: [iOS]
---

## 前言 ##

上一篇文章介绍了引用计数，用`GNUstep`的实现，推测了`cocoa`框架的实现，现在总结一下个人觉得重要的地方，和上一篇没说的地方


<!-- more -->

### 非自己生成的对象，自己也能持有 ###

这一条内存管理举的例子是

```
id obj = [NSArray array];
[obj retain];
```

开始不是很明白，这里的工厂方法，和普通的初始化有什么区别，个人当时认为的是这也应该算做是`自己生成的对象，自己所持有`

但是后面说了，`[NSArray array]`的时候，其实他面会把生成的临时对象`[tempObj autorelease]`,那么反回的时候，就需要`[obj retain]`,因为之前说了，这个地方的内容还没涉及到`ARC`，所以需要`retain`一下



### NSAutoreleasePool ###

这个东西，说的简单点，就是一个块，让在他里面的东西，都保持状态，不释放，但是出了他的作用域，就全部释放一遍

继续用开关灯那个例子的话，`NSAutoreleasePool`就相当于一个屋子里面多了一个管理员，对象在`NSAutoreleasePool`中，就好比管理员在屋子中，所以屋子会一直亮着，而且，管理员肯定是第一个进来，最后一个走，这就保证了，屋子的灯可以由管理员控制，也就是对象的释放时机，是出了`NSAutoreleasePool`的时候


在`ARC`中，`NSAutoreleasePool`是不只直接使用的，你可以使用下面这个：

```
@autoreleasepool{

// Code benefitting from a local autorelease pool.

}
```

在自动释放池中，一个对象如果被`autorelease`了多次，那么在池子释放的时候，对象会被执行多次`release`


在运行时系统中调用方法

```mm
/* 函数声明 */
extern void _objc_autoreleasePoolPrint();

/* 调试处调用 */
_objc_autoreleasePoolPrint();
```

这个方法在`ARC`中的调试中可以使用，可以打印出，当前缓冲池中的所有对象，还是比较有用的。


