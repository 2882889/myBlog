---
title: swift_node_1
date: 2021-06-09 19:17:54
tags:[Swift]
---

### swift常量和变量

#### 常量

* 使用`let`声明
* 只能赋值一次（但不一定要在声明的时候赋值）
* 不需要在编译时期确定，但在使用前必须赋值
* 不在声明的时候赋值，需要指明类型

#### 变量

* 使用`var`声明
* 可以多次赋值
* 不需要在编译时期确定，但在使用前必须赋值
* 不在声明的时候赋值，需要指明类型

### swift常见数据类型

|  值类型  | 枚举（enum）     | Optional                                                     |
| :------: | ---------------- | ------------------------------------------------------------ |
|          | 结构体（struct） | Bool、Int、Float、Double、Character、String、Array、Dictionary、Set |
| 引用类型 | 类（class）      |                                                              |

* `Int`在32位系统上是`Int32`，在64位系统上是`Int64`
* `Int`等结构体类型是具有属性的，eg：`Int8.min`

### swift流程控制

#### if-else

* `if`后面的条件可以不写小括号
* 条件执行需要用大括号
* `if`后面的条件只能是`Bool`类型的

#### while

* 条件后面不写小括号
* 条件执行语句要有大括号
* 条件只能是`Bool`类型的

#### repeat-while

* 相当于`do-while`
* 同`while` 

#### for

##### 区间运算符

* 闭区间运算符 (a <= 取值 <= b)

  ```swift
  // 表示 a, a + 1, a + 2, ... b
  a...b
  ```
```
  
* 半开区间运算符 (a <= 取值 < b)

  ```swift
  // 表示 a, a + 1, a + 2, ... b - 1
  a..<b
```

* 单侧区间 （a <= 取值）

  ```swift
  // 表示从 a到尽可能远
  a...
  // 从尽可能远到a
  ...a
  // 从尽可能远到a - 1
  ..<a
  ```

* 单侧区间不能直接用在`for`循环的范围中，但可以使用在数组的取值，因为数组有尽可能远

  ```swift
  let age = [1,2,3,4,5]
  let a = age[2...] // [3, 4, 5]
  let b = age[...2] // [1, 2, 3]
  let c = age[..<2] // [1, 2]
  ```

* 区间不只可以是`Int`类型的，也可以是字符和字符串类型，但是不能用在`for`循环中

* 带间隔的区间

  ```swift
  stride(from: 5, through: 11, by: 3) // [5, 8, 11]
  ```

##### for循环模板

```swift
for i in 2...5 {
	print(i)
}
```

* `for`循环中可以使用`where`添加条件

  ```swift
  for i in 2...5 where i == 3 {
  	print(i) //只有3输出
  }
  ```

  

#### switch

* `case`、`default`后面不写大括号

* 默认可以不写`break`,且不写不会继续走下面的`case`

* 如果需要继续走下面的`case`需要添加`fallthrough`语句

* `switch`必须要能处理所有情况

* `case`和`default`后面必须要有一条语句，如果不想做任何操作，可添加`break`

* 如果能处理所有情况，则可以不用写`default`

* `switch`可以支持字符和字符串，元组

* `case`后面可以跟多个情况，用`,`分割

* `switch`元组的时候可以使用`值匹配`,且可以使用`where`判断

  ```swift
  let point = (2, 0)
  switch point {
  	case (let x, 0):
  		break // 此处可以使用 x
  	case let (x, y) where x == y:
  		break //在x 等于 y 是命中
  }
  ```

  