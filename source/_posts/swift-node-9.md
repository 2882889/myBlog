---
title: swift_node_9
date: 2021-10-15 14:44:25
tags:
---

### 错误处理

#### 错误类型

* 语法错误 （编译错误）
* 逻辑错误
* 运行时错误 （可能会导致闪退，一般也叫异常）

#### 自定义错误

* Swifth中可以通过Error协议自定义运行时的错误信息

* 函数内部通过throw抛出自定义Error，可能会抛出Error的函数必须加上throws声明

* 需要使用try调用可能会抛出Error的函数

* 处理Error的2种方式
  1. 通过do-catch捕捉Error
  2. 不捕捉Error，在当前函数增加throws声明，Error将自动抛给上层函数
     * 如果最顶层函数（main函数）依然没有捕捉Error，那么程序将终止
  
* 可以通过try?、try!调用可能抛出Error的函数，这样就不用去处理Error

* `rethrows`表明：函数本身不会抛出错误，但调用闭包参数抛出错误，那么它会将错误向上抛

* `defer`语句：用来定义任何方式（抛出错误、return等）离开代码块前必须执行的代码

* `defer`语句将延迟至当前作用域结束之前执行，所以写入位置可以在前面

* `defer`语句的执行顺序与定义顺序相反

  ```swift
  func fn1() { print("fn1") }
  func fn2() { print("fn2") }
  func test() {
  		defer { fn1() }
  		defer { fn2() }
  }
  test()
  //fn2
  //fn1
  ```

  

### 泛型

* 泛型可以将类型参数化，提高代码复用率，减少代码量

### 关联类型

* 关联类型的作用： 给协议中用到的类型定义一个占位名称
* 协议中可以拥有多个关联类型

```
protocol Stackable {
			associatedtype Element
}

class StringStack : Stackable {
		typealias Element = String
}
```

