---
title: 读《iOS与OS X多线程和内存管理》—— GCD
date: 2017-05-14 21:53:48
tags: [iOS]
---

## 前言 ##

《iOS与OS X多线程和内存管理》的第三章是对`GCD`的介绍，`GCD`就是`Grand Central Dispatch`,是将应用程序中记述的线程管理用的代码在系统级中实现。说简单点就是，用简单的方法实现多线程

<!-- more -->

### 队列 Dispatch Queue ###

队列（Dispatch Queue）是存放任务等待执行处理的地方。既然是队列，那么他要遵循`FIFO`先进先出原则。

队列有两种：

1. Serial Dispatch Queue  (串行队列)

2. Concurrent Dispatch Queue (并行队列)


`Serial Dispatch Queue`（串行队列）的特点是需要等待现在执行中的任务处理结束才可以处理下一个任务

`Concurrent Dispatch Queue` (并行队列)的特点是不等待现在执行中的任务处理结束，可以并行执行多个处理，但是并行处理的数量取决于当前系统的状态

### 线程 ###

一个CPU执行的CPU命令列是一条无分叉路径，这就是一个线程，所以从队列的种类来看，串行队列始终在使用一个线程处理任务，而并行队列使用的是多个线程处理任务，多个线程的数量取决于当前系统状态


### API ###

`GCD`使用过程中的各种`API`

#### 创建和获取队列 ####

创建一个串行队列:

```
dispatch_queue_t serialDispatchQueue = dispatch_queue_create("queueName"，DISPATCH_QUEUE_SERIAL);
```

创建一个并行队列：

```
dispatch_queue_t concurrentDispatchQueue = dispatch_queue_create("queueName", DISPATCH_QUEUE_CONCURRENT);
```

创建串行队列和并行队列的方法是一样的，唯一的区别是第二个参数，如果创建的是串行队列，传入`DISPATCH_QUEUE_SERIAL`就好，如果是创建并行队列，则指定为`DISPATCH_QUEUE_CONCURRENT`。

创建队列的第一个参数，是队列的名称，他可以在调试器中显示，苹果推荐的名称是，逆序全程域名，你也可以传`NULL`

获取系统提供的队列：

```
//获取主线程队列 （串行队列）
dispatch_queue_t mainDispatchQueue = dispatch_get_main_queue();

//获取全局队列--高优先级 （并行队列）
dispatch_queue_t highQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0);

//获取全局队列--默认优先级 （并行队列）
dispatch_queue_t defaultQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);

//获取全局队列--低优先级 （并行队列）
dispatch_queue_t lowQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_LOW, 0);

//获取全局队列--后台优先级 （并行队列）
dispatch_queue_t backgroundQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_BACKGROUND, 0);
```

#### 改变队列优先级 ####

在使用`dispatch_queue_create`函数生成的队列，不管是串行还是并行的都是默认优先级的。要改变优先级需要使用`dispatch_set_target_queue`函数。

```
dispatch_queue_t myQueue = dispatch_queue_create("queueName", DISPATCH_QUEUE_SERIAL);
dispatch_queue_t globalBackgroundQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_BACKGROUND,0);
dispatch_set_target_queue(myQueue, globalBackgroundQueue);
```
这样`myQueue`的优先级就是`DISPATCH_QUEUE_PRIORITY_BACKGROUND `的了。

注意，不能改变系统提供的队列的优先级，也就是系统提供的队列不能写在第一个参数中

`dispatch_set_target_queue`还可以改变队列执行优先级，如果有多个串行任务在多个串行队列中执行，这时如果要防止并行，就可以将多个串行队列，指定到同一个目标串行队列中，原本并行执行的多个串行队列就会变成真正的串行，同时执行一个处理。


#### 延迟执行 ####

使用`dispatch_after`可以实现延迟指定时间执行

```
dispatch_time_t time = dispatch_time(DISPATCH_TIME_NOW, 3ull*NSEC_PER_SEC);

dispatch_after(time, dispatch_get_main_queue(), ^{
	//延迟执行的任务内容
});

```

注意，这里的延迟是在指定时间追加任务到指定队列，所以有可能有延时，不算绝对精确。


#### Dispatch Group ####

`Dispatch Group`可以在多个队列都执行完成后，执行指定队列

```
dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
dispatch_group_t group = dispatch_group_create();

dispatch_group_async(group,queue, ^{NSLog(@"blk1");});
dispatch_group_async(group,queue, ^{NSLog(@"blk2");});
dispatch_group_async(group,queue, ^{NSLog(@"blk3");});

dispatch_group_notify(group, dispatch_get_main_queue(),^{NSLog(@"done");});
//dispatch_group_wait(group,DISPATCH_TIME_FOREVER); //指定超时时间
```

#### dispatch_barrier_async ####

`dispatch_barrier_asyne`函数会等待在他前面追加在并行队列上的任务全部执行完，然后执行他的任务，在他的任务执行完再恢复并行队列的任务

eg：

```
dispatch_queue_t queue = dispatch_queue_create("queueName",DISPATCH_QUEUE_CONCURRENT);
dispatch_async(queue,blk0_for_reading);
dispatch_async(queue,blk1_for_reading);
dispatch_async(queue,blk2_for_reading);
dispatch_async(queue,blk3_for_reading);
dispatch_barrier_async(queue, blk_for_writing);
dispatch_async(queue,blk4_for_reading);
dispatch_async(queue,blk5_for_reading);
dispatch_async(queue,blk6_for_reading);
```

#### dispatch_sync ####

`dispatch_sync`是同步将`block`追加到队列中，`dispatch_async`是非同步的将`block`追加到队列中，区别就是`dispatch_sync`会在追加完成后，等待任务执行完，而`dispatch_async`不做任何等待。

一旦调用`dispatch_sync`函数，在指定的处理执行结束之前，该函数不会返回，所以该函数容易引起死锁问题，比如在主线程同步添加任务。

#### dispatch_apply ####

`dispatch_apply`可以按照指定的次数将指定的`block`追加到指定的队列中，并等待全部处理执行结束

```
dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT,0);
dispatch_apply(10, queue, ^(size_t index) {
	NSLog(@"%zu",index);
});
```

因为他也会等待处理执行结束，所以也会有死锁问题。


#### dispatch_suspend/dispatch_resume ####

`dispatch_suspend`函数可以挂起指定的队列

`dispatch_resume`函数可以恢复指定的队列


#### dispatch_once ####

这是经常写单例使用的方法，`dispatch_once`函数可以保证在应用程序执行中只执行一次。

eg：

```
static dispatch_once_t pred;

dispatch_once(&pred, ^{
	//do something
});
```












