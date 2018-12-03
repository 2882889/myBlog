# Blocks #

## 什么是Blocks ##

```blocks```是带有自动变量（局部变量）的匿名函数

匿名函数，通过```Blocks``` 源码中能够使用不带名称的函数





## Block 截获自动变量的值 ##

```blocks```被称为“带有自动变量（局部变量）的匿名函数”


那么```blocks```如何带自动变量呢？

```
void subMain() {
    
    int val = 10;
    char *fmt = "the val is %d";
    
    void (^blk)(void) = ^{printf(fmt,val);};
    blk();//the val is 10
    val = 2;
    blk();//the val is 10
}
```

上面这个函数中两次调用block变量 blk，输出的都是“the val is 10”，因为block截获自动变量的值，
所谓block截获自动变量的值，指的是在block表达式中会保存该自动变量的瞬间值


如果要在block中改变局部变量呢？

```
void subMain() {
    
    int val = 10;
    char *fmt = "the val is %d";
    
    void (^blk)(void) = ^{ val = 2};
    blk();
    
}
```

这个函数想在block中改变局部变量的值，但是会报编译错误，显然，直接在block中改变自动变量的值也是不可以的

如果想要在block中改变局部变量的值需要在局部变量前面加上一个```__blcok```修饰

```
void subMain() {
    
    __block int val = 10;
    char *fmt = "the val is %d";
    
    void (^blk)(void) = ^{ val = 2};
    blk();
    
}
```

这样就不会报错，而且可以改变局部变量的值


clang -rewrite-objc




block 会在以下情况从栈拷贝到堆上

1. 调用copy的实例方法
2. block作为函数返回值返回时
3. 将block赋值给附有__strong修饰符id类型的类或block类型成员变量时
4. 在方法名中含有usingBlock的cocoa框架方法或GCD的API中传递Block时



栈区(stack)
概述：栈是向低地址扩展的数据结构，是一块连续的内存区域。由栈顶的地址和栈的最大容量是系统预先规定好的。栈的空间很小，大概1-2M。超出overflow。

使用：栈区，就是函数(方法)运行时向系统请求的内存。栈中的局部变量，参数由编译器分配和释放，函数运行就分配，函数结束就释放，由系统完成。是系统数据结构，对应进程唯一。

特别注意：系统栈的剩余空间 大于 所申请的空间，系统为程序提供内存，不然，报错异常，提示栈的溢出。

堆区(heap)
概述：堆是向高地址扩展的数据结构，不连续的内存区域。系统用链表储存空闲地址的。链表遍历由低向高。堆大小直接受设备有效虚拟内存影响。

1.首先应该知道操作系统有一个记录空闲内存地址的链表。
2.当系统收到程序的申请时，会遍历该链表，寻找第一个空间大于所申请空间的堆结点，然后将该结点从空闲结点链表中删除，并将该结点的空间分配给程序。
3.由于找到的堆结点的大小不一定正好等于申请的大小，系统会自动的将多余的那部分重新放入空闲链表中
1
2
3
使用: 存放实体对象的。由程序员分配和释放(arc自动插入分配和释放代码)，例如alloc 申请的会放入堆中。

全局\静态区(static)
静态变量和全局变量是存储在一起的。初始化的全局变量和静态变量存放在一块区域，未初始化的全局变量和静态变量在相邻的另一块区域，程序结束后有系统释放。

int a;//未初始化的静态区
int b = 10;//初始化的静态区
1
2
文字常量区
存放常量字符串，程序结束系统释放

程序代码区
存放函数的二进制代码

```
main{
   int b; 栈区
   char s[] = "abc" 栈
   char *p1; 栈 
   char *p2 = "123456";  //123456在文字常量区，p2在栈上。
   static int c =0； 全局（静态）初始化区 

   w1 = (char *)malloc(10); 
   w2 = (char *)malloc(20); 
   分配得来得10和20字节的区域就在堆区。 
 }

```


# GCD

同步添加
dispatch_sync(Queue,Block) 

等待 queue 中的所有任务都完成了之后，把block任务添加进入

异步添加
dispatch_async(Queue,Block) 

不做任何等待，直接把block中的任务添加进入queue


注意：

1. 如果是dispatch_sync（同步添加），但是queue 就是当前dispatch_sync运行的队列，就会造成死锁


打比方：

block ：每个学生

queue ： 学生所站的队 （有两类）

dispatch_sync 和 dispatch_async

线程 ： 在哪个跑道上跑步



把一个任务使用GCD让其在线程中执行，比喻下来就是安排一个学生到跑道上跑步


queue：这里有两种队列，一直叫串行队列，一种叫并行队列，

串行队列：只能有一个学生在排队，如果有多个学生想要去排队，只能等前一个学生跑玩步，跑道空出来才能去排队




eg：

异步并行队列：

```
void GCD_async_Test() {
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0); //获得一个并行队列
    NSLog(@"任务1加入队列1前");
    dispatch_async(queue, ^{
        NSLog(@"开始长时间任务1");
        [NSThread sleepForTimeInterval:3];
        NSLog(@"长时间任务1完成");
    });//把任务放入并行队列中
    NSLog(@"任务1加入队列后");
}
```

输出：

任务1加入队列1前
任务1加入队列后
开始长时间任务1
长时间任务1完成














