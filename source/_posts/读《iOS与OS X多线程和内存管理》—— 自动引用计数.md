---
title: 读《iOS与OS X多线程和内存管理》—— 自动引用计数
date: 2017-01-11 11:10:12
tags: [iOS]
---

## 前言 ##

下面是自动引用计数部分书中内容的笔记


<!-- more -->


### 自动引用计数设置 ###

前面说到过，自动引用计数（Automatic Reference Counting）简称就是我们现在使用的`ARC`了，顾名思义自动引用计数就是编译器自动帮我们处理引用计数

在编译的时候，我们可以指定每个文件是否使用`ARC`，Xcode4.2以后编译器默认为`ARC`有效。

在创建完工程的时候，我们可以在工程的配置文件中设置工程模式是`MRC`还是`ARC`，设置的方法是：

在工程配置文件的，`Build Settings`中，找到`Objective-C Automatic Reference Counting` 选项，设置为`YES`,就是`ARC`工程模式，设置为`NO`就是`MRC`工程模式。

在`ARC`工程模式或`MRC`工程模式也可以设置部分文件编译模式与之相反，设置方法：

在`Build pyases`中的`compile source`找到对应的文件设置参数`-fno-objc-arc`为`MRC`模式，反之`-fobjc-arc`为`ARC`模式。


### 所有权修饰符 ###

在引用计数中，我们知道，对象的销毁，是通过引用计数的值来判断的，但是引用计数的值是需要我们手动去维护的，那么要想让编译器去维护引用计数，我们就要通过一些修饰符，去告诉编译器，变量什么时候需要引用计数加一，什么时候引用计数需要减一，那么这个修饰符就是所有权修饰符


在`ARC`模式下，所有权修饰符有四种：

- __strong
- __weak
- __unsafe_unretained
- __autoreleasing


#### __strong ####

`__strong`修饰符是id类型和对象类型的默认所有权修饰符

```
id obj = [[NSObject alloc] init];
```

```
id __strong obj = [[NSObject alloc] init];
```

这两种表示方法的结果是一样的

在`ARC`中，被`__strong`修饰符修饰的变量，表示对对象的强引用，持有强引用的变量在超出其作用域时被废弃，随着强引用的失效，引用的对象会随之释放

在变量赋值的时候，也会对对象强引用，如果赋值为`nil`,则强引用失效

`__strong`也可以修饰在方法参数上，虽然平常我们写的方法上都没有，那是因为默认是不用写的


#### __weak ####

`__weak`修饰符，提供弱引用，弱引用不能持有对象实例。`__weak`修饰符是为了解决`__strong`修饰符会产生循环引用出现的。 所谓循环引用，就是自己引用自己，或者多个对象相互引用，导致谁都无法释放的问题

`__weak`修饰符，的另一个作用是，在持有某对象的弱引用时，若该对象被废弃，则此弱引用将自动失效且处于`nil`被赋值的状态（空弱引用）。

所以，检查`__weak`修饰符的变量是否为空，可以判断被赋值的对象是否已废弃


#### __unsafe_unretained ####

`__unsafe_unretained`修饰符，是不安全的所有权修饰符，被他修饰的变量，是不属于编译器的内存管理对象。


`__unsafe_unretained`修饰符，和`__weak`修饰符的作用相似，他也无法持有对象实例，但是他在对象已经释放的时候，仍然指向对象，形成垂悬指针。

所以，在使用`__unsafe_unretained`修饰符的变量的时候，必须确保被赋值的对象确实存在，不然会导致程序崩溃


#### __autoreleasing ####

在`MRC`的时候，`NSAutoreleasePool`类中的要想让对象自动释放，有个`autorelease`方法，但是在`ARC`中，`NSAutoreleasePool`已经改用`@autoreleasepool`了，所以，需要`__autoreleasing`修饰符，来表示对象自动释放。


`__autoreleasing`和`__strong`修饰符都不需要显示的添加，因为在使用`alloc/new/copy/mutableCopy`以外的方法取来的对象，已经被注册到`autoreleasepool`中了。

在`__weak`修饰符修饰的变量在访问对象的时候，也会把对象注册到`autoreleasepool`中

id的指针或者对象的指针在没有显式指定是，也会被加上`__autoreleasing`修饰符
