---
title: 读《iOS与OS X多线程和内存管理》——前言
date: 2017-04-13 11:10:12
tags: [iOS]
---

# 前言 #

  都说不会写部落格的程序猿不是好作家，本作家本来想着当一个小小的初级工程师，每天码码界面，终其一生就算了。
谁知天有不测风云，人有祸福旦夕，那天在工程中多看了一眼别人的代码，顿时觉得自己的知识储备实在是太匮乏了。

那么究竟是什么样的代码，居然有如此神奇的功效，闲话少叙，上代码：

<!-- more -->

代码一：

```mm
@interface MyView ()

@property (nonatomic, weak) UILabel *label;

@end

@implementation MyView
- (void)setupSubview {
	UILabel *label = [[UILabel alloc] init];
	label.font = [UIFont systemFontOfSize:14];
	label.textColor = [UIColor whiteColor];
	label.textAlignment = NSTextAlignmentCenter;
	label.text = @"你好";
	[self addSubview:label];
	_label = label;
}
@end
```

就是这么一段代码，如果要让楼主写 则是下面这样的

代码二：

```objectivec
@interface MyView ()

@property (nonatomic, strong) UILabel *label;

@end

@implementation MyView
- (void)setupSubview {
	self.label = [[UILabel alloc] init];
	self.label.font = [UIFont systemFontOfSize:14];
	self.label.textColor = [UIColor whiteColor];
	self.label.textAlignment = NSTextAlignmentCenter;
	self.label.text = @"你好";
	[self addSubview:self.label];
}
@end
```


当然你也可以直接用`_label`赋值，这里问题的重点不是set方法和成员变量的区别，而是我的属性
用的是`strong`修饰而他的是使用`weak`修饰。

本着不耻下问，虚心求教的态度我问了一下我同事，得到了这样的解答：

代码一的属性使用的`weak`修饰，因为在`addSubview：`的时候`self`会强引用`label`对象`weak`修饰的成员变量`_label`弱引用着`label`对象

代码二的属性使用`strong`修饰， 在`addSubview：`的时候`self`会强引用`label`对象 ```strong``` 修饰的成员变量`_label`也强引用着`label`对象

第一种代码的好处是在`self`移除`label`的时候`label`对象就释放了。因为`label`只有一个`self`强引用着

第二种代码在`self`移除`label`的时候`label`对象不会释放，因为还有个`_label`的变量强引用这`label`对象，
只有在`self`释放的时候`label`对象才会释放。


本人读书少，仔细一想确实是这么回事，用`weak`修饰，`label`对象只被`self`强引用，从view上移除后就可以释放，
不用等到`self`释放的时候才释放，创建和释放都很合理(哪里创建哪里释放)。

但是细思极恐，难道我多年来的开发经验是错误的么，用了n年的`strong`原来是个低效的使用方法。这时候我突然想到了一个面试题，

那就是你真的了解属性修饰符的用法么？

好吧我承认！我不了解，这就是我看这本书的原因。


注：
1. 上面的解释不一定是正确的。
2. 小弟初来乍到要是有啥说的不对的地方希望各位大大不吝赐教。
3. 要是你们有什么意见和建议，也可以随便吐槽。
