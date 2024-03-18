---
title: 读《iOS与OS X多线程和内存管理》—— 属性
date: 2017-05-10 19:26:12
tags: [iOS]
---

## 前言 ##

在`ARC`中是使用所有权修饰符来实现自动引用计数的，但是编程中我们几乎很少显式的写所有权修饰符，而是写属性关键字。

<!-- more -->


### 属性与所有权修饰符的对应关系 ###

| 属性        | 所有权修饰符    |
| ----       | -----         |
| assign     | __unsafe_unretained |
| copy       | __strong (赋值的是被复制对象)     |
| retain     | __strong      |
| strong     | __strong      |
| unsafe_unretained     | __unsafe_unretained      |
| weak     | __weak      |


### __strong 实现 ###

```mm
{
id __strong obj = [NSObject alloc] init];
}
```
在编译时会转换成

```
id obj = objc_msgSend(NSObject, @selector(alloc));
objc_msgSend(obj, @selector(init));
objc_release(obj);//变量作用域结束编译器自动插入了release
```
使用alloc/new/copy/mutableCopy以外的方法

```
{
id __strong obj = [NSMutableArray array];
}
```

在编译时会转换成

```
id obj = objc_msgSend(NSMutableArray,@selector(array));
/*
array方法在编译时转换成了
{
id obj = objc_msgSend(NSMutableArray, @selector(alloc));
objc_msgSend(obj, @selector(init));
return objc_autoreleaseReturnValue(obj);
}
*/
objc_retainAutoreleasedReturnValue(obj);
objc_release(obj);//变量作用域结束编译器自动插入了release
```


objc_retainAutoreleasedReturnValue() 和 objc_autoreleaseReturnValue是成对出现的，用在alloc/new/copy/mutableCopy以外的类方法时

objc_autoreleaseReturnValue函数和objc_autorelease函数不同，objc_autorelease函数会注册对象到autoreleasepool中
objc_autoreleaseReturnValue函数会检查使用该函数的方法或函数调用方的执行命令列表，
如果在后面紧接着调用objc_retainAutoreleaseReturnValue()函数，则将返回的对象直接传递到方法或函数的调用方，而不注册到autoreleasepool

objc_retainAutoreleasedReturnValue函数和objc_retain函数不同，它即便不注册到autoreleasepool中而返回对象，也能够正确地获取对象
通过objc_autoreleaseReturnValue函数和objc_retainAutoreleasedReturnValue函数，可以不将对象注册到autoreleasepool中而直接传递


### __weak 实现 ###

```
{
id __weak obj1 = obj;//此处假设变量obj附加__strong修饰符且对象被赋值
}
```
在编译时会转换成

```
id obj1;
objc_initWeak(&obj1, obj);
/*
objc_initWeak函数在这里转换成
obj1 = 0；
objc_storeWeak(&obj1, obj);
*/
objc_destroyWeak(&obj1);
/*
objc_destroyWeak函数在这里相当于
objc_storeWeak(&obj1, 0);
*/
```

objc_stroeWeak函数把第二个参数的赋值对象的地址作为键值，将第一个参数的附有`__weak`修饰符的变量的地址注册到weak表中，
如果第二个参数为0，则把变量的地址从weak表中删除

weak表与引用计数表相同，都是作为散列表实现的。使用weak表，将废弃的对象的地址作为键值进行检索，就能高速地获取对应的附有`__weak`修饰符的变量的地址
由于一个对象可同时赋值给多个附有`__weak`修饰符的变量中，所以对于一个键值，可注册多个变量的地址

对象释放时，程序的将进行下面几步：

1. objc_release
2. 执行dealloc
3. _objc_rootDealloc
4. object_dispose
5. objc_destructInstance
6. objc_clear_deallocating

objc_clear_deallocating函数做了下面几件事：

1. 从weak表中获取废弃对象的地址为键值的记录（获取所有的__weak修饰的变量的地址）
2. 将所有的__weak修饰的变量的地址，赋值为nil
3. 从weak表中删除该记录
4. 从引用计数表中删除废弃对象的地址为键值的记录

经过以上步骤，`__weak`修饰符的变量所引用的对象被废弃，且将nil赋值给该变量。
由此可知，如果大量使用附有`__weak`修饰符的变量，则会销毁相应的CPU资源，因此只是在需要避免循环引用时使用`__weak`修饰符

将自己生成并持有的对象赋值给`__weak`修饰符修饰的变量时，会引起编译器警告

```
{
id __weak obj = [[NSObject alloc] init];
}
```

在编译时转换成：

```
id obj;
id tmp = objc_msgSend(NSObject, @selector(alloc));
objc_msgSend(tmp, @selector(init));
objc_initWeak(&obj, tmp);
objc_release(tmp);
objc_destroyWeak(&object);
```

自己生成并持有的对象通过objc_initWeak函数被赋值给附有`__weak`修饰符的变量中，但变量无法持有，
故编译器判断其没有持有者，则该对象立即通过objc_release函数被释放和废弃，同时变量也被置nil

使用附有`__weak`修饰符的变量，即是使用注册到autoreleasepool中的对象

```
{
id __weak obj1 = obj;
NSLog(@"%@",obj1);
}
```

可以转化成如下形式

```
id obj1;
objc_initWeak(&obj1, obj);
id tmp = objc_loadWeakRetained(&obj1);
objc_autorelease(tmp);
NSLog(@"%@" ,tmp);
objc_destroyWeak(&obj1);
```

和被赋值相比，此处增加了objc_loadWeakRetained函数和objc_autorelease函数

1. objc_loadWeakRetained函数取出附有__weak 修饰符变量所引用的对象并retain。
2. objc_autorelease函数将对象注册到autoreleasepool中。

由此可知，`__weak`修饰符变量所引用的对象像这样被注册到autoreleasepool中，所以在@autoreleasepool 块结束之前都可以放心使用。
但是，如果大量地使用附有`__weak` 修饰符的变量，注册到autorpool的对象也会大量地增加，
因此，在使用附有`__weak`修饰符的变量时，最好先暂时赋值给附有`__strong`修饰符的变量后再使用

eg：

```
{
id __weak o = obj;
NSLog(@"1 %@", o);
NSLog(@"2 %@", o);
NSLog(@"3 %@", o);
NSLog(@"4 %@", o);
NSLog(@"5 %@", o);
}
```
这样o所赋值的对象注册到autoreleasepool中5次

但使用

```
{
id __weak o = obj;
id tmp = o;
NSLog(@"1 %@", tmp);
NSLog(@"2 %@", tmp);
NSLog(@"3 %@", tmp);
NSLog(@"4 %@", tmp);
NSLog(@"5 %@", tmp);
}
```
只会在"tmp = o"时，对象会登录到autoreleasepool中1次

在一些特定环境中是不能使用`__weak` 修饰符的，而且也存在着不支持`__weak`修饰符的类，这些类重写了retain/release
并实现该类独自的引用计数机制。因此独自实现引用计数机制的类大多数不支持__weak修饰符。
不支持__weak修饰符的类，其类声明中附加了`"__attribute__((objc_arc_weak_reference_unavailable))"`这一属性，
同时定义了`"NS_AUTOMATED_REFCOUNT_WEAK_UNAVAILABLE"`。如果将不支持`__weak`声明类的对象赋值给附有`__weak`修饰符的变量，
那么一旦编译器检验出来就会报告编译错误。而且Cocoa框架类中，不支持`__weak`修饰符的类极为罕见

allowsWeakReference/retainWeakReference方法
当allowsWeakReference/retainWeakReference实例方法返回NO时，同样不能使用`__weak`修饰符

在赋值给`__weak`修饰符的变量时，如果赋值对象的allowsWeakReference 方法返回NO,程序将异常终止

在赋值给`__weak`修饰符的变量时，如果赋值对象的retainWeakReference 方法返回NO,该变量将使用nil

eg：

```
{
id __strong obj = [[NSObject alloc] init];
id __weak o = obj;
NSLog(@"1 %@",o);
NSLog(@"2 %@",o);
NSLog(@"3 %@",o);
NSLog(@"4 %@",o);
NSLog(@"5 %@",o);
}
```

运行结果所有的NSLog都可以打印出地址

如果自定义一个MyObject继承自NSObject的类，并实现retainWeakReference方法

```
- (BOOL)retainWeakReference {
  if (++count > 3)
    return NO;
  return [super retainWeakReference];
}
```

使用MyObject类生成并持有对象

```
{
id __strong obj = [[MyObject alloc] init];
id __weak o = obj;
NSLog(@"1 %@",o);
NSLog(@"2 %@",o);
NSLog(@"3 %@",o);
NSLog(@"4 %@",o);
NSLog(@"5 %@",o);
}
```

运行结果4和5的NSLog打印的为nil


### __autoreleasing 实现

```
@autoreleasepool {
    id __autoreleaseing obj = [[NSObject alloc] init];
}
```

在编译的时候转换为

```
id pool = objc_autoreleasePoolPush();
id obj = objc_msgSend(NSObject, @selector(alloc));
objc_msgSend(obj, @selector(init));
objc_autorelease(obj);
objc_autoreleasePoolPop(pool);
```

在alloc/new/copy/mutableCopy方法群之外的方法使用

```
@autoreleasepool {
  id __autoreleasing obj = [NSMutableArray array];
}
```

在编译时转换为

```
id pool = objc_autoreleasePoolPush();
id obj = objc_msgSend(NSMutableArray, @selector(array));
objc_retainAutoreleasedReturnValue(obj);
objc_autorelease(obj);
objc_autoreleasePoolPop(pool);
```