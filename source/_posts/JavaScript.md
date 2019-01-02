---
title: Learn JavaScript
date: 2017-11-11 21:10:12
tags: JavaScript
---

## 前言 ##
闲来无事看了看`JavaScript`,这里记录部分内容。

<!-- more -->

### Equality (相等比较符) ###

```Javascript
var foo = 42;
var bar = 42;
var baz = "42";
var qux = "life";
```
定义了四个变量在Javascript中`==`是用来比较两个值但是不会判断类型故`foo == baz`返回的值是`true`

使用`==`比较的时候编译器会先把类型转换成相同的类型,再去比较

因此在JavaScript 要是比较不同类型的两个值可以使用`===`这样会先判断类型,如果类型不同就会返回`false`

所以推荐使用`===`比较

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


### Types ###

#### numbers ####

JavaScript中只有一种数字类型 – 64-bit float point,类似于Java中的double类型,所以在JavaScript中1和1.0是相同的值
number可以直接做四则运算，规则和数学一致

#### 字符串 ####

字符串是以'单引号'或"双引号"括起来的文本,如`'abc'` `"xyz"`

注意`' '`和`" "`并不是字符串的一部分

如果字符串中包含`'` 就使用`""`把文本包起来,如果字符串中包含`"` 就需要使用转义字符了`\`

在多行字符串中换行使用的是`\n` 但是如果行数太多`\n`需要写多个,不方便,在JavaScript中又增加了一种表示方法(ES6),就是将多行文本用\`... \`括起来,就是键盘上tab键上边的那个按键.

##### 模板字符串 #####
多个字符串可以使用`+`连接起来.字符串也可以是变量如:

```JavaScript
var name = '小王吧';
var age = '100';
var message = '你好,' + name + ',你现在' + age + '岁';
```
同样,如果要连接的变量有很多,使用`+`就会很不方便,在JavaScript中增加了一种模板字符串的表示(ES6):

```JavaScript
var name = '小王吧';
var age = '101';
var message = `${name},你今年${age}岁,是不是?`
```
注意:新的表示方法使用的是\` ,就是新的多行文本使用的符号,就是键盘上`tab`按键上边的那个按键,不能使用`""或''`,那样无法转换变量

##### 字符串操作 #####
获取字符的长度

```JavaScript
var name = 'xiao wang ba.';
alert(name.length); //13
```

获取指定位置字符,可以把字符串看成是特殊的数组,对字符串进行下标操作,从0索引开始

```JavaScript
var name = 'xiao wang ba.';
name[0]; //'x'
name[1]; //'i'
name[4]; //' '
name[12];//'.'
name[13];//undefined 超出范围的索引一律返回undefined
```
注意:给字符串的索引赋值不会有报错,也不会有任何效果,字符串也不会改变

改变字符串为大写

```JavaScript
var name = 'xiao wang ba.';
name.toUpperCase(); //返回'XIAO WANG BA.'
```

改变字符串为小写

```JavaScript
var name = 'Xiao Wang Ba.';
name.toLowerCase(); //返回'xiao wang ba.'
```
注意:如果字符串是中文,则没效果

返回字符串出现的位置

```JavaScript
var name = 'Xiao Wang Ba.';
name.indexOf('Wang');//返回5
name.indexOf('wang');//如果没有找到指定的子串,返回-1
```

获取字符串中的子串

```JavaScript
var name = 'xiao wang ba.';
name.substring(0,4);//从索引0开始到4(不包括4),返回'xiao'
name.substring(10);//从索引10开始到结束,返回'ba.'
```

#### 布尔值 ####
JavaScript中的布尔值有`true`和`false`两种值,可以直接用`true`或`false`表示,也可以通过布尔运算计算出来

#### null和undefined ####
`null`表示一个"空"的值,同Java中的`null`,Swift中的nil,Python中的None用法类似.
`undefined`表示"未定义".
JavaScript设计的时候希望用`null`表示一个空的值,用`undefined`表示值未定义,但是大多数情况下我们都应该用`null`.`undefined`仅仅在判断函数参数是否传递的情况下有用

#### 数组 ####
数组是一组按顺序排列的集合,集合的每个值称为元素.JavaScript的数组可以包括任意数据类型.
创建方法:

```JavaScript
[1,2,3.14,"hello",null,true]; //推荐使用
```
另一种创建方法:

```JavaScript
new Array(1,2,3);
```

获取数组的长度

```JavaScript
var arr = [1,2,3.14,"hello",null,true];
arr.length;//6
//可以给数组的长度赋值,改变数组的大小
arr.length = 7;//数组改变为[1,2,3.14,"hello",null,true,undefined]
arr.length = 4;//数组改变为[1,2,3.14,"hello"]
```


数组可以通过索引来访问每个元素的值,也可以改变索引对应元素的值:

```JavaScript
var arr = [1,2,3.14,"hello",null,true];
arr[0]; //数组第一个元素,值为1
arr[1] = 10;//修改数组第二个元素的值为10
```

注意:如果通过索引给数组赋值的时候,索引超过了数组长度,数组会被变大

```JavaScript
var arr = ['A', 'B', 'C'];
arr[5] = 'F';//arr会变为['A', 'B', 'C', undefined, undefined, 'F'];
```

获取对应元素的索引

```JavaScript
var arr = [10, 20 ,'30', 'xyz'];
arr.indexOf(10);//元素10的索引是0
arr.indexOf(20);//元素20的索引是1
arr.indexOf(30);//元素30的索引是 -1 因为没有找到元素
arr.indexOf('30');//元素'30'的索引是2
```
注意：数字`30`和`'30'`是不同的元素

获取数组中的部分元素

```JavaScript
var arr =['A','B','C','D','E','F','G'];
arr.slice(0,3);//从索引0开始,到索引3结束,但不包括索引3:['A','B','C']
arr.slice(3);//从索引3开始到结束:['D','E','F','G']

```

注意:`slice()`的起止参数包括开始索引,不包括结束索引
如果不给`slice()`传递任何参数,它会从头到尾截取所有元素,这个方法可以复制一个`array`

```JavaScript
var arr = ['A','B','C','D','E','F','G'];
var aCopy = arr.slice();
aCopy; //['A','B','C','D','E','F','G'];
aCopy === arr; //false
if(aCopy.toString() === arr.toString()) {
    alert('一样');//数组相同
}else {
    alert('不一样');//数组不同
}
```
注意:`JavaScript`比较两个数组是否相同不能直接使用`===` 要比较两个数组是否相同,需要先把数组转换成字符串,然后再比较

在数组末尾添加或删除若干元素,`push()`和`pop()`

```JavaScript
var arr = [1 , 2];
arr.push('A','B'); //返回新的长度为4的数组
arr; //[1,2,'A','B']
arr.pop();//pop()返回'B'
arr; // [1,2,'A']
arr.pop();arr.pop();arr.pop(); //连续pop 3次
arr; //[]
arr.pop(); //空数组继续pop不会报错,而是返回undefined
arr;//[]
```

在数组头部添加或删除若干元素,`unshift()`和`shift()`

```JavaScript
var arr =[1,2];
arr.unshift('A','B');//返回新的长度为4的数组
arr;//['A','B',1,2]
arr.shift();//'A'
arr;//['B',1,2]
arr.shift();arr.shift();arr.shift();//连续shift 3次
arr;//[]
arr.shift();//空数组继续shift不会报错,而是返回undefined
arr;//[]
```

数组排序
`sort()`可以对当前数组进行排序,它会直接修改当前的数组元素的位置,直接调用时,会按照默认顺序排序

```JavaScript
var arr = ['B','C','A'];
arr.sort();
arr;//['A','B','C']
```
注意 在函数中可以自定义排序条件

数组反转

`reverse()`可以把数组的元素反转

```JavaScript
var arr = ['A','B','C'];
arr.reverse();
arr;//['three','two','one']
```

数组修改的"万能方法",可以从指定索引开始删除若干元素,然后再从该位置添加若干元素

```JavaScript
var arr =['Microsoft','Apple','Yahoo','AOL','Excite','Oracle']
arr.splice(2,3,'Google','Facebook');//从索引2开始删除3个元素,然后再添加两个元素,返回删除的元素['Yahoo','AOL','Excite']
arr.splice(2,2);//只删除不添加,返回['Google','Facebook']
arr;//['Microsoft','Apple','Oracle']
arr.splice(2.0,'Google','Facebook');//只添加,不删除,返回[]
arr;//['Microsoft','Apple','Google','Facebook','Oracle']
```
把两个数组连接起来,返回一个新的数组

```JavaScript
var arr = ['A','B','C'];
var added = arr.concat([1,2,3]);
added; //['A','B','C',1,2,3]
ARR;//['A','B','C']
```
注意 `concat()`方法并没有修改当前数组,而是返回了一个新的数组

而且`concat()`可以接收任意个元素和数组,并把所有数组自动拆开,添加到新的数组中

```JavaScript
var arr = ['A','B','C'];
arr.concat(1,2,[3,4]);//['A','B','C',1,2,3,4]
```

把数组的每个元素都用指定的字符串连接起来,然后返回字符串

```JavaScript
var arr = ['A','B','C',1,2,3];
arr.join('-'); //'A-B-C-1-2-3'
```
注意如果数组的元素不是字符串,会自动转换成字符串再连接

多维数组,指的是数组中的某个元素又是一个数组

```JavaScript
var arr = [[1,2,3],[400,500,600],'-'];
```

上述`Array`包含3个元素,其中头两个元素本身也是`Array`


#### 对象 ####
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

JavaScript用一个`{...}` 表示一个对象,键值对以`xxx: xxx`形式申明,用`,`隔开,最后一个键值对不需要加

JavaScript对象的键都是字符串类型,值可以是任意数据类型,每个键都是对象的一个属性,获取属性的方法:

```JavaScript
person.name; // 'XiaoWang'
person.zipcode; // null
```
注:`var person` 是JavaScript的一个变量,`person`是变量名,变量名是大小写英文,数字,`$`和`_`的组合,且不能用数字开头,也不能使用JavaScript中的关键字


##### strict模式 #####
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

##### Advanced Operaters (高级运算符) #####
取余运算 `x = y % 2`
累加运算 `a = 5`
		`c = a++`/Users/YaoWanXin/Desktop/app.html resulets : c = 5 and a = 6
		`c = ++a` resulets : c = 6 and a = 6
累减运算 `a = 5`
		`c = a--` resulets : c = 5 and a = 4
		`c = --a` resulets : c = 4 and a = 4
