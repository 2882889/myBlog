---
title: Python相关--Numpy使用
date: 2019-3-1 20:10:12
tags: [Python]
---

## 前言 ##

NumPy(Numerical Python) 是 Python 语言的一个扩展程序库，支持大量的维度数组与矩阵运算，此外也针对数组运算提供大量的数学函数库。

<!-- more -->

### numpy 方法 ###

创建矩阵

```
np.array([1,2,3],[2,3,4])

np.array([2,3,4],dtype=np.int) //同时指定类型

np.zeros((3,4)) //生成3行4列全部为0的矩阵

np.ones((3,4),dtype=np.int) //生成3行4列全部为1的矩阵

np.empty((3,4)) //生成3行4列为空的矩阵，里面的值全部都是接近0的值

np.arange(10,20,2) //生成一个从10到20，步长为2的矩阵，不包括20

np.arange(12).reshape((3,4)) //生成一个从0到11的3行4列的矩阵

np.linspace(1,10,20) //生成一个从1到10，一共20段的矩阵，起始值是1结束值是10

np.random.random((2,4)) //生成一个随机的2行4列矩阵
```

查看数组维度

```
array.ndim
```

查看数组的形状

```
array.shape
```

查看数组的元素

```
array.size
```

### numpy的运算 ###

```
a=np.array([10,20,30,40]) //[10 20 30 40]
b=np.arange(4) //[0 1 2 3]

c=a+b //[10 21 32 43]
c=a-b //[10 19 28 37]
c=a*b //[0 20 60 120]
c=b**4 //表示b的4次方

c=10*np.sin(a) //对a的每个元素求sin再乘以10
c=b<3 //[True True True False] 每个元素是否小于3

/******/
a=np.array([[1,1],[0,1]])
b=np.arange(4).reshape((2,2))

c=a*b //元素逐个相乘 [[0 1] [0 3]]
c=np.dot(a,b) //矩阵的乘法 [[2 4] [2 3]]
c=a.dot(b) //矩阵乘法的另一种表示

/******/
a=np.random.random((2,4))
np.sum(a,axis=1) //不加axis=1表示所有元素求和，axis=1表示维度,1是以行求和，0是以列求和
np.min(a,axis=0) //求最小值 同上
np.max(a,axis=1) //求最大值 同上

/******/
a=np.arange(2,14).reshape((3,4))
b=np.argmin(a) //获取最小值的索引
c=np.argmax(a) //获取最大值的索引
d=np.mean(a) //获取平均值
e=np.average(a) //另一种平均值算法
f=np.median(a) //中位数
g=np.cumsum(a) //累加，第一个值是第一个元素的值。第二个值是前两个元素相加的值
h=np.diff(a) //相邻的差
i=np.sort(a) //从小到大排序，每行排每行的
j=np.transpose(a) //矩阵反向，行变成列，列变成行
j=a.T //也是矩阵的反向
k=np.clip(a,5,9) //小于5的数变成5，大于9的数变成9
```
