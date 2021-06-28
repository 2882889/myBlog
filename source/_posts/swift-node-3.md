---
title: swift_node_3
date: 2021-06-15 11:39:24
tags:
---

### 枚举

#### 关联值

* 定义枚举的时候可以指定关联值

  ```swift
  enum Score {
  	case points(Int)
  	case grade(Character)
  }
  
  enum Date {
    case digit(year: Int, month: Int, day: Int)
    case string(String)
  }
  
  var date = Date.digit(year: 2021, month: 9, day: 10)
  date = .string("2021-9-10")
  switch date {
    case .digit(let year, let month, let day):
    	print(year, month, day)
    case let .string(value)
    	print(value)
  }
  ```

* 关联值是存在枚举的内存中的

* 关联值枚举中有1个字节存储成员变量（哪个枚举），N个字节存储关联值（N取占用内存最大的关联值），所有case的关联值共用这N个字节

* 只有一个case的时候，不用花费1个字节存储成员变量，也就是只有一个case，占用的内存是N个字节存储关联值

#### 原始值

* 枚举成员可以使用相同类型的默认值预先关联，这个默认值叫做：原始值

  ```swift
  enum PokerSuit : String {
  	case spade = "黑桃"
  	case heart = "红桃"
  	case diamond = "方块"
  	case club = "梅花"
  }
  
  var suit = PokerSuit.spade
  print(suit) // spade
  print(suit.rawValue) // 黑桃
  print(PokerSuit.club.rawValue) //梅花
  ```

* 枚举的原始值类型是`Int`、`String`，Swift会自动分配原始值

* 原始值不存储在枚举内存中，不占用枚举内存

#### 原始值和关联值的区别

* 关联值不同的枚举变量可以设置不同的值，原始值是不可以改变的
* 设置为原始值的枚举，不可以在设置关联值

#### 递归枚举

* 枚举的关联值是枚举本身，枚举前面需要加`indirect`关键字

  ```swift
  indirect enum ArithExpr {
  	case number(Int)
  	case sum(ArithExpr, ArithExpr)
  	case difference(ArithExpr, ArithExpr)
  }
  // 或
   enum ArithExpr {
  	case number(Int)
  	indirect case sum(ArithExpr, ArithExpr)
  	indirect case difference(ArithExpr, ArithExpr)
  }
  ```

  

### MemoryLayout

```swift
MemoryLayout<Int>.size //8字节 Int类型实际占用的内存
MemoryLayout<Int>.stride //8字节 Int类型分配的内存
MemoryLayout<Int>.alignment //8字节 Int类型对齐内存

var a = 10
MemoryLayout.size(ofValue: a) //8字节 a实际占用的内存
MemoryLayout.stride(ofValue: a) //8字节 a分配的内存
MemoryLayout.alignment(ofValue: a) //8字节 a对齐内存
```



### 可选项

* 可选项允许将值设置为`nil`
* 在类型名称后面加`?`来定义一个可选项
* 使用`!`可以对可选项进行强制解包
* 对`nil`强制解包会报错
* 可以使用可选项绑定来判断可选项是否包含值，包含值返回`true`,不包含返回`false`
* 在类型名称后面加`!`来定义一个隐式解包可选项，隐式解包，在使用的时候会默认解包

#### 空合并运算符

* `a ?? b`
* `a`是可选项，`b`是可选项或不是可选项
* `b`和`a`的存储类型必须相同
* 如果`a`为`nil`,则返回`b`,如果`a`不为`nil`,则返回`a`
* 如果`b`不是可选项是，返回`a`时会自动解包



### 查看变量内存

```swift
frame variable -R 或 fr v -R
variable 和 v 是变量名
```



### 汇编相关

* `mov` 是把内存地址所指存储空间的值，取出来赋值，`lea`是把内存地址直接赋值
* `jmp`跳转到地址所指的指令去执行，`call`跳转调地址所指的指令执行，但会和`ret`配合使用，`ret`会返回，继续执行`call`下面的指令  

#### 寄存器

`rip` 存储一个汇编指令的地址

### LLDB常用指令

* `register read rax` 读取寄存器`rax`的值
* `register write rax 0x1`  向寄存器`rax`写入值`0x1`
* `register read` 列出所有寄存器的值
* `n` 单步运行，函数当整体直接跳过
* `s`单步运行，进入函数中 
* `ni` 单步运行，调试汇编用，功能同`n`
* `si`单步运行，调试汇编用，功能同`s`
* `finish`直接执行完当前函数的所有代码，返回到上一个函数，遇到其他断点也会停止