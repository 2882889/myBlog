---
title: synthesize和dynamic
date: 2017-05-24 23:07:32
tags: [iOS]
---


## @property ##

@property  会生成一个`ivar`和他的`getter/setter`方法

但是在`protocol`和`category`中只会生成`getter/setter`方法的声明


<!-- more -->

## @synthesize ##

@synthesize 表示如果属性没有手动实现`setter/getter`方法，编译器会自动加上`getter/setter`两个方法,给后面的实例变量

eg：

```
@syntheszie var = _var;
```
如果一个属性这么写了，表示你告诉编译器，生成一个以`var`命名的`setter/getter`方法，和一个实例变量`_var`。如果同时你又自己声明了一个名叫`_var`的实例变量，那么`@synthesize`就不会生成实例变量，而是用你声明的那个。也就是两个实例变量是一个。

如果`_var`你写的是另一个属性的实例变量，那么会编译错误，但是你可以交换两个属性的实例变量。（可是没什么意义）

eg：

```
@syntheszie var
```

如果这么写，那么生成的实例变量就是`var`,这时你自己声明一个`_var`和`var`是不冲突的，也就是两个，属性用的是`var`。


## @dynamic ##
@dynamic 表示属性的`setter/getter`方法由用户自己实现，不自动生成。若没有手动生成，则运行时会报错，因为方法没有实现

eg：

```
@dynamic var;
```
