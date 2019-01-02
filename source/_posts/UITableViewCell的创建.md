---
title: UITableViewCell的创建
date: 2018-10-20 19:58:55
tags: [iOS]
---

## 前言 ##
`cell`的创建通常有两种方法，平时使用的时候没有注意，这里记录一下

<!-- more -->

### UITableViewCell的创建 ###

我们都知道iOS中`tableview`的`cell`在创建的时候会用到重用机制，简单的说，就是把每个`cell`绑定一个标识，然后当`cell`在屏幕中显示的时候，系统首先会去缓冲池中取对应标志的`cell`，如果没有那就创建一个`cell`，如果有就直接拿出来使用，当`cell`滚动出屏幕的时候，系统会把`cell`放回缓冲池中，这样，缓冲池中最多也就一两个对象，而系统也不需要每次显示都创建对象。


```
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"identiffier"];
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"identiffier"];
    }
    
    return cell;
}
```

这个是一个默认的`tablecell`的创建，可以看到

```
UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"identiffier"];

```
先是去缓冲池去取`cell`，如果没有再去创建

所以这个写法必须要加上下面的if判断，如果不加，而且缓冲池中没有cell，那么程序就会因为返回一个nil崩溃


```
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"identiffier" forIndexPath:indexPath];
    
    return cell;
}
```

在iOS6之后，又有了这种创建`cell`的方法，这种方法不需要写`if`，也就是说，如果缓冲池中没有，系统会自动的去创建一个`cell`，但是如果只是这么写还是不够的，系统还是会崩溃，因为你你要告诉tableview用什么标识创建一个什么`cell`

简单的说，你还要在tableview创建的时候写上下面的方法

```
[tableView registerClass:[UITableViewCell class] forCellReuseIdentifier:@"identiffier"];
```

这样tableview就知道，在缓冲池中没有cell的时候，需要用什么类创建一个什么标识的cell

### 总结 ###

`tableviewcell`的重用有两种方法

```
UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"identiffier"];
```

这种方法不需要注册cell，但如果缓冲池没有需要手动创建

```
UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"identiffier" forIndexPath:indexPath];

```
这种方法需要注册`cell`，但是不需要手动创建了
