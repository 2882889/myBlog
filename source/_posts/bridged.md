
---
title: iOS的CF和OC中间的类型转换
date: 2017-01-11 11:10:12
tags: iOS
---


# iOS的CF和OC中间的类型转换 #

## Toll-Free bridged ##

`Toll-Free bridged`  是Core Foundation和Foundation之间交换使用数据类型的技术.即同一数据类型既可作为Core Foundation函数的参数,也可以作为接收者向其发送Objective-C消息.

说的简单点,就是OC中的有些类型,通过`Toll-Free bridged` 既可以当做Core Foundation的类型使用(CFxxxx),又可以当做Foundation的类型使用(NSxxxx).



<!----- more ----->


## Toll-Free bridged原理 ##

每一个能够bridge的ObjC类，都是一个类簇（class cluster）。类簇是一个公开的抽象类，但其核心功能的是在不同的私有子类中实现的，公开类只暴露一致的接口和实现一些辅助的创建方法。而与该ObjC类相对应的Core Foundation类的内存结构，正好与类簇的其中一个私有子类相同。

举个例子，NSString是一个类簇，一个公开的抽象类，但每次创建一个NSString的实例时，实际上我们会获得其中一个私有子类的实例。而NSString的其中一个私有子类实现既为NSCFString，其内存的结构与CFString是相同的，CFString的isa指针就指向NSCFString类，即CFString对象就是一个NSCFString类的实例。
所以，当NSString的实现刚好是NSCFString的时候，他们两者之间的转换是相当容易而直接的，他们就是同一个类的实例。

当NSString的实现不是NSCFString的时候（比如我们自己subclass了NSString），我们调用CF函数，就需要先检查对象的具体实现。如果发现其不是NSCFString，我们不会调用CF函数的实现来获得结果，而是通过给对象发送与函数功能相对应的ObjC消息（调用相对应的NSString的接口）来获得其结果。

例如CFStringGetLength函数，当收到一个作为参数传递进来的对象时，会先确认该对象到底是不是NSCFString实现。如果是的话，就会直接调用CFStringGetLength函数的实现来获得字符串的长度；如果不是的话，会给对象发送length消息（调用NSString的length接口），来得到字符串的长度。

## Toll-Free bridged的内存管理 ##

在ARC中编译器不知道如何处理这个同时拥有ObjC和CFTypeRef指向的对象,所以你必须为编译器提供额外的信息：将类型显示转换为互换类型；同时可能需明确对象的声明周期。 因此我们需要使用 `__bridge`, `__bridge_retained`, `__bridge_transfer` 修饰符告诉编译器该如何去做。

### __bridge ###

```objectivec
//objc to cf
 NSString *str = @"hello";
 CFStringRef cfStr = (__bridge CFStringRef)str;
 CFRelease(cfStr);//这里不需要release
```
编译器会继续负责nsStr的内存管理的事情，不会在bridge的时候retain对象，所以也不需要开发者在CF 一端释放。需要注意的是，当nsStr被释放的时候（比如出了作用域），意味着cfStr指向的对象被释放了，这时如果继续使用cfStr将会引起程序崩溃。

```objectivec
//cf to objc
 CFStringRef cfStr = CFStringCreateWithCString(kCFAllpacatorDefaule,"hello",kCFStringEncodingUTF8);
 NSString *str = (__bridge NSString *)(cfStr);
 CFRelease(cfStr);//这里需要release
```

 bridge的时候编译器不会做任何内存管理的事情，bridge之后，编译器会负责ObjC一端的内存管理的事情。同时，开发者需要负责管理CF 一端的内存管理的事情，需要在bridge之后，负责release对象。

### __bridge_retained ###
接着上面的例子，Foundation类型对应的Core Foundation类型时，为了防止str被释放，引起我们使用cfStr的时候程序崩溃，可以使用__bridge_retained修饰符。这意味着，在bridge的时候，编译器会retain对象，而由开发者在CF一端负责release。这样，就算str在objc一端被释放，只要开发者不手动去释放cfStr，其指向的对象就不会被真的销毁。但同时，开发者也必须保证和负责对象的释放。

```objectivec
//objc to cf
 NSString *str = @"hello";
 CFStringRef cfStr = (__bridge_retained CFStringRef)str;
 CFRelease(cfStr);//这里不需要release
```

### __bridge_transfer ###
当Core Foundation类型转化为Foundation类型时，如果使用__bridge修饰符在cf转objc的时候非常的麻烦，我们既需要一个CFTypeRef的变量，还需要在bridge之后负责释放。这时我们可以使用__bridge_transfer，意味着在bridge的时候，编译器转移了对象的所有权，开发者不再需要负责对象的释放。

```objectivec
//cf to objc
 CFStringRef cfStr = CFStringCreateWithCString(kCFAllpacatorDefaule,"hello",kCFStringEncodingUTF8);
 NSString *str = (__bridge NSString *)(cfStr);
 CFRelease(cfStr);//这里需要release
```

## 总结 ##

  * `__bridge` :编译器在bridge的时候不要做任何事情
  * `__bridge_retained` :（ObjC转CF的时候使用）编译器在bridge的时候retain对象，开发者需要在CF一端负责释放对象
  * `__bridge_transfer` :（CF转ObjC的时候使用）编译器转移CF对象的所有权，开发者不再需要在CF一端负责释放对象
