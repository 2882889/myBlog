---
title: 读《iOS与OS X多线程和内存管理》——引用计数
date: 2017-04-17 19:34:12
tags: [iOS]
---

## 前言

上一章说了看这本的目的，下面就算是读书笔记吧

<!-- more -->

### 1. 什么是自动引用计数(ARC) ###

ARC（Automatic Reference Counting）简而言之就是编译器自动进行内存管理，那为什么不叫"自动内存管理"，而叫"自动引用计数"，当然是因为内存管理是的方法是通过引用计数实现的

要说自动引用计数，首先要说什么是"引用计数"

#### 1.1引用计数 ####

什么是引用计数，书中举了一个生活中开关房间灯的例子。

简单的描述一下就是，第一个人来屋子的时候要开灯(创建对象)，最后一个人走的时候要关灯(释放对象)，中间来人走人都不需要开关灯。
那么办公室里的人数就是引用计数。也就是有多少个人用着灯

#### 1.2内存管理的思考方式 ####

内存管理的思考方式总结出来就是下面四点  

注：这里还没到ARC所以还是非ARC

1.自己生成的对象，自己所持有 (指通过`alloc/new/copy/mutableCopy`等创建的对象)
eg:

```mm
id obj = [NSObject new];
```

2.非自己生成的对象，自己也能持有 (指持有通过类方法创建的对象)
eg:

```mm
id obj = [NSArray array];
[obj retain];
```

3.不再需要自己持有的对象时释放  (释放对象)
eg:

```mm
id obj = [[NSObject alloc] init];
[obj release];  
```
4.非自己持有的对象无法释放  (多次释放，或未持有释放）
eg:

```mm
id obj = [[NSObject alloc] init];
[obj release];
[obj release];
```


对象的操作与Objective-C方法的对应
生成并持有对象------ alloc/new/copy/mutableCopy 等
持有对象----------- retain
释放对象----------- release
废弃对象----------- dealloc


### 2.alloc/retain/release/dealloc 实现 ###

这一节研究的是`alloc/retain/release/dealloc`的实现。

讲之前先说一个常识，那就是Objective-C的框架，底层的是一个叫Cocoa框架其中包含一个Foundation框架

首先Objective-C内存管理的方法是包含在Cocoa框架中的，这里所说的内存管理是的方法`alloc/retain/release/dealloc` 指的是`NSObject`类的`alloc`类方法,`retain`实例方法,`release`实例方法和`dealloc`实例方法，但是`NSObject`类的`Foundation`框架没有公开过源码，所以这里使用开源软件`GNUstep`的框架，一个能和Cocoa框架互换的框架，来理解苹果Cocoa框架的实现。

这里我把代码给大家写出来，供大家参考，但书的作者把有些地方的源码在不改变意思的范围进行了修改

#### 2.1 alloc 实现 ####

GNUstep的```alloc```实现：


```mm
+(id)alloc
{
    return [self allocWithZone:NSDefaultMallocZone()];
}

+ (id)allocWithZone:(NSZone *)z
{
    return NSAllocateObject (self, 0, z);
}

/* ------- NSAllocateObject ------- */
struct obj_layout {
    NSUInteger retained;
};

inline id NSAllocateObject (Class aClass, NSUInteger extraBytes, NSZone *zone)
{
    int size = 计算容纳对象所需内存大小；
    id new = NSZoneMalloc(zone,size);
    memset(new, 0, size);
    new = (id)&((struct obj_layout *) new)[1];
}

/* ----- 简化版alloc ----- */
+ alloc
{
    int size = sizeof(struct obj_layout) + 对象大小;
    struct obj_layout *p = (struct obj_layout *)calloc(1,size);
    return(id)(p+1);
}
```

简化版的是去掉了`NSZone`之后的代码，`NSZone`是为了防止内存碎片化引入的结构，对内存管理没有影响，所以可以忽略。从简化版的代码中可以看出`GNUstep`申请的对象的内存比需要的大了`sizeof(struct obj_layout)`，这个多出来的空间就是用来存放引用计数的

在非ARC即MRC中是可以通过`retainCount`查看对象的引用计数的。

retainCount的实现:

```mm
- (NSUInteger)retainCount
{
    return NSExtraRefCount(self) + 1;
}

inline NSUInteger NSExtraRefCount(id anObject)
{
    return((struct obj_layout *) anObject)[-1].retained;
}
```

#### 2.2 retain 实现 ####

GNUstep的`retain`实现：

```mm
- (id)retain
{
    NSIncrementExtraRefCount(self);
    return self;
}

inline void NSIncrementExtraRefCount(id anObject)
{
    if(((struct obj_layout *)anObject)[-1].retained == UINT_MAX -1)
        抛出异常
    ((struct obj_layout *)anObject)[-1].retained++;
}
```

#### 2.3 release 实现 ####
GNUstep的`release`实现：

```mm
- (void)release
{
    if(NSDecrementExtraRefCountWasZero(self))
        [self dealloc];
}

BOOL NSDecrementExtraRefCountWasZero(id anObject)
{
    if(((struct obj_layout *)anObject)[-1].retained == 0){
        return YES;
    }else {
        ((struct obj_layout *)anObject)[-1].retained--;
        return NO;
    }
}
```

#### 2.4 dealloc 实现 ####
GNUstep的`dealloc`实现：

```mm
- (void)dealloc
{
    NSDeallocateObject(self);
}

inline void NSDeallocateObject(id anObject)
{
    struct obj_layout *o = &((struct obj_layout *) anObject)[-1];
    free(o);
}
```

可以看到`GNUstep`实现引用计数的方法是在变量在内存块头部保存的当前对象的引用计数，默认是0，当调用`retainCount`时会自动+1，这就说明了自己生成的对象，自己所持有。


#### 2.5 苹果实现 ####
苹果由于官方未公开源代码，根据运行时调用的代码可以看出苹果实现引用计数和`GNUsetp`的主要区别就是`GNUsetp`将引用计数存放在内存块头，而苹果使用的是散列表（键为内存块地址的散列值）

存放在头部管理引用计数的好处：
- 代码量少
- 能够统一管理引用计数用内存块和对象用内存块

用散列表(引用计数表)管理引用计数的好处：
- 对象用内存块的分配无需考虑内存块头部
- 引用计数表各记录中存有内存块地址，可从各个记录追溯到各对象的内存块


### 3 autorelease ###
所谓`autorelease`就是自动释放，原理就是若某自动变量超出其作用域，该自动变量将被自动废弃

GNUstep的`autorelease`实现：


```mm
- (id)autorelease
{
    [NSAutoreleasePool addObject:self];
}

+ (void) addObject:(id)anObj
{
    NSAutoreleasePool *pool = 取得当前NSAutoreleasePool对象
    if(pool != nil) {
        [pool addObject:anObj];
    }else {
        NSLog(@"NSAutoreleasePool对象非存在状态下调用autorelease")；
    }
}
```

废弃`NSAutoreleasePool`方法`drain`实现


```mm
- (void)drain
{
    [self dealloc];
}

- (void)dealloc
{
    [self emptyPool];
    [array release];
}

- (void)emptyPool
{
    for(id obj in array) {
        [obj release];
    }
}
```

查看`AutoreleasePool`中的内容

```mm
[NSAutoreleasePool showPools];//此方法只能在iOS中使用
```
在运行时系统中调用方法

```mm
/* 函数声明 */
extern void _objc_autoreleasePoolPrint();

/* 调试处调用 */
_objc_autoreleasePoolPrint();
```

注：
(1).上面的内容全部是本人读《Objective-C高级编程 iOS与OS X多线程和内存管理》一书觉得重点的部分，如果大家感兴趣可以支持一下正版。
(2).如果各位看官觉得有什么是小弟说的不对的地方，欢迎指出。
(3).这篇基本上都是说引用计数的实现的，还没有涉及到前言提出的问题的解答。
(4).看到这里的都是真爱啊。
