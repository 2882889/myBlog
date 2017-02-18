---
title: Learn JavaScript
date: 2017-01-11 11:10:12
tags: JavaScript
---

## Equality (相等比较符) ##

```Javascript
var foo = 42;
var bar = 42;
var baz = "42";
var qux = "life";
```
定义了四个变量在Javascript中 `==`是用来比较两个值但是不会判断类型故 `foo == baz`返回的值是`true`

使用`==`比较的时候编译器会先把类型转换成相同的类型,再去比较

因此在JavaScript 要是比较不同类型的两个值可以使用`===`这样会先判断类型,如果类型不同就会返回`false`

所以推荐使用`===`比较

<!----- more ----->

注意: 1.`NaN`是个特殊的Number,他与所有其他值都不相等,包括它自己
```JavaScript
NaN === NaN ; // false
```
唯一能判断NaN的方法是通过isNaN()函数:
```JavaScript
isNaN(NaN); // true
```

2.浮点数的比较:
```JavaScript
1/3 === (1 - 2 / 3); //false
```
这个结果是正确的,出现这个结果这是因为浮点数在运算过程中会产生误差,计算机无法精确的表示无限小数,所以要比较两个浮点数是否相等,只能计算他们之差的绝对值,看是否小于某个阈值:

```JavaScript
Math.abs(1/3 - (1 - 2 / 3)) < 0.0000001; //true
```


## Types ##

### numbers

JavaScript中只有一种数字类型 – 64-bit float point,类似于Java中的double类型,所以在JavaScript中1和1.0是相同的值

### 字符串

字符串是以'单引号'或"双引号"括起来的文本,如`'abc'` `"xyz"`

注意`' '`和`" "`并不是字符串的一部分

如果字符串中包含`'` 就使用`""`把文本包起来,如果字符串中包含`"` 就需要使用转义字符了`\` 

在多行字符串中换行使用的是`\n` 但是如果行数太多`\n`需要写多个,不方便,在JavaScript中又增加了一种表示方法,

### 布尔值
JavaScript中的布尔值有`true`和`false`两种值,可以直接用`true`或`false`表示,也可以通过布尔运算计算出来

### null和undefined
`null`表示一个"空"的值,同Java中的`null`,Swift中的nil,Python中的None用法类似.
`undefined`表示"未定义".
JavaScript设计的时候希望用`null`表示一个空的值,用`undefined`表示值未定义,但是大多数情况下我们都应该用`null`.`undefined`仅仅在判断函数参数是否传递的情况下有用

### 数组
数组是一组按顺序排列的集合,集合的每个值称为元素.JavaScript的数组可以包括任意数据类型.
创建方法:

```JavaScript
[1,2,3.14,"hello",null,true]; //推荐使用
```
另一种创建方法:

```JavaScript
new Array(1,2,3);
```

### 对象
对象是一组由键-值组成的无序集合:

```JavaScript
var person = {
	name: 'XiaoWang',
	tags: ['js', 'web', 'mobile'],
	age: 12,
	city: 'shanghai',
	hasCar: false,
	zipcode: null
};
```
JavaScript对象的键都是字符串类型,值可以是任意数据类型,每个键都是对象的一个属性,获取属性的方法:

```JavaScript
person.name; // 'XiaoWang'
person.zipcode; // null
```
注:`var person` 是JavaScript的一个变量,`person`是变量名,变量名是大小写英文,数字,`$`和`_`的组合,且不能用数字开头,也不能使用JavaScript中的关键字


#### strict模式
蛋疼的布兰登在设计JavaScript的时候为了赶工期,并没有强制使用`var`声明变量,如果一个变量没有通过`var`声明就被使用,这个变量就会被自动声明为全局变量

```JavaScript
i = 10; //i就是一个全局变量
```

如果在不同的JavaScript文件中,你都使用了同样名字的变量,而且都没有用`var`声明,那么你就麻烦了.

因此,为了给布兰登填坑,ECMA在后续的规范中推出了strict模式,在strict模式下运行JavaScript代码不允许使用没有`var`声明的变量,如果有那么就给你报错

启动strict模式的方法很简单,就是在JavaScript代码的第一行写上:

```JavaScript
'use strict';
```
注:如果你用的是老爷机,你的浏览器太老了,他会自以为是的把上面那就话当做一个字符串语句执行,如果这样的话还是换个最新版的浏览器吧

#### Advanced Operaters (高级运算符)
取余运算 `x = y % 2`
累加运算 `a = 5`
		`c = a++`/Users/YaoWanXin/Desktop/app.html resulets : c = 5 and a = 6
		`c = ++a` resulets : c = 6 and a = 6
累减运算 `a = 5`
		`c = a--` resulets : c = 5 and a = 4
		`c = --a` resulets : c = 4 and a = 4

