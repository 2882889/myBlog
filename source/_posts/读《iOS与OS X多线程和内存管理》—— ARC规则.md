---
title: 读《iOS与OS X多线程和内存管理》—— ARC规则
date: 2017-01-11 11:10:12
tags: [iOS]
---

## 前言 ##

在`ARC`有效的情况下编译源代码需要遵守的规则

<!-- more -->


### 不能使用 retain/release/retainCount/autorelease ###

这些方法都是在`MRC`中手动管理引用计数的方法，在`ARC`中，既然自动管理了引用计数了，自然不可以使用了，因为都管理，就会出现冲突


### 不能使用 NSAllocateObject/NSDeallocateObject ###

不能使用的原因同上

### 须遵守内存管理的方法命名规则 ###

`alloc/new/copy/mutableCopy/init`以这些名称开始的方法在返回对象是，必须返回给调用方所应当持有的对象。

`init`是`ARC`追加的命名规则，切`init`必须是实例方法，必须要返回对象


### 不要显式调用 dealloc ###

在对象被释放的时候，系统会自动调用`dealloc`方法，不需要我们手动调用，原因也很简单，如果手动调用了，就意味着要释放对象，但是这时候如果对象的引用计数不是0，那么引用计数为0就释放对象的规则就失效了。

在`MRC`时，在`dealloc`方法中需要显式的调用`[super dealloc]`,但是在`ARC`时，这是不需要的


### 使用@autoreleasepool 块替代 NSAutoreleasePool ###

在`ARC`中使用`NSAutoreleasePool`会引起编译器报错


### 不能使用区域（NSZone） ###

无论`MRC`还是`ARC`都无法使用。


### 对象型变量不能作为C语言结构体的成员 ###

原因是C语言没有方法来管理结构体成员的生存周期，`ARC`的内存管理是编译器完成的，所以编译器必须能知道并管理对象的生存周期。

要想把对象型变量加入结构体成员中，可以强制转换为`void *`或是附加`__unsafe_unretained`修饰符


### 显式转换 id 和 void * ###

在`MRC`时，将id变量强制转换void *变量并不会出问题,如下面代码：


```
id obj = [[NSObject alloc] init];
void *p = obj;
id o = p;
```

但是在`ARC`时，是会引起编译错误的，要想在id和void *之间相互赋值，需要`__bridge`转换。

```
id obj = [[NSObject alloc] init];
void *p = (__bridge void *)obj;
id o = (__bridge id)p;
```

`__bridge`还有两种转换：

`__bridge_retained` 可以使转换赋值的变量也持有所赋值的对象，相当于目标变量`retain`

`__bridge_transfer` 可以是被转换的变量所持有的对象在该变量被赋值给转换目标变量后随之释放，就是目标变量`retain`,自己`release`