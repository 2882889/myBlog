---
title: iOS中的isEqual：和 “==”
date: 2017-06-13 23:32:00
tags: [iOS]
---


## 前言 ##

在iOS开发中常常要比较两个值是否一样，通常的做法有两种，就是用`isEqual：`或者使用`==`

<!-- more -->

### "==" 比较

如果用`==`比较的是两基本类型，则比较的是两个两个基本类型的值，如果比较的是两个对象类型，则比较的是对象的地址


这时如果有两个`string`：

```
    NSString *str1 = @"hello";
    NSString *str2 = [NSString stringWithFormat:@"hello"];
```

这两个string的内容明显是一样的，但是地址却不一样，如果使用`==`比较就会返回`NO`

这样就用到我们的`isEqual：`

注：isEqualToString：会在下面说

### isEqual：比较


使用`isEqual：`是想要比较对象的值，而不是地址，注意这里是想要

使用`isEqual：`比较上面两个字符串，会返回`YES`

但是这不代表所有的对象使用都会去自动比较对象的值，比如比较两个自己定义的对象的时候，默认情况下他其实还是比较的两个对象的地址

之所以比较字符串会比较两个值，是因为`NSString`的`isEqual：` 已经被重写了,注意这里是重写因为`isEqual：`是`NSObject`的方法

同时还增加了一个`isEqualToString：`方法，这里是增加，因为这是`NSString`自己的方法

同样的还有还多，比如`NSDate`,`NSArray`,`NSDictionary`,`NSSet`,甚至是`UIColor`

所以当我们想要比较自己定义的对象的值的时候，我们也要重写自己的`isEqual：`

重写的时候其实你可以说任何一个比较的对象都是一样的，换句话说只要你觉得符合你的业务逻辑，你就可以说他是一样

### 重写isEqual：


```
- (BOOL)isEqual:(id)other {  
 	if (other == self)   
 		return YES;  
 	if (!other || ![other isKindOfClass:[self class]])  
 		return NO;  
 		return [self isEqualToWidget:other];  
}  


- (BOOL)isEqualToWidget:(MyWidget *)aWidget {  
 	if (self == aWidget)  
 		return YES;  
 	if (![(id)[self name] isEqual:[aWidget name]])  
 		return NO;  
 	if (![[self data] isEqualToData:[aWidget data]])  
 		return NO;  
 		return YES;  
}
```

其实上面的方法可以随意返回，只要你觉得需要
