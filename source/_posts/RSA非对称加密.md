---
title: RAS 加密算法原理
date: 2018-05-02 21:45:12
tags: [Linux]
---

## 前言 ##

网上看了一下李永乐老师讲的非对称加密的原理，挺有意思的，记录一下

<!-- more -->


### 非对称加密 ###

#### 传递信息的非对称加密过程：####

1. A想要把信息m发送给B

2. B生成两个有相关性的数字，e（公钥）和d（私钥）

3. B把公钥e以公开的形式传给A

4. A通过公钥的某一种算法得到密文C

5. A把密文C传给B

6. B通过私钥d解密密文C，得到信息m


### RSA加密算法 ###

RSA就是一种非对称加密的算法


#### RSA加密过程：####

1. 首页B找出两个质数p，q
2. n = p*q
3. 带入欧拉函数 Φ（n）= （p-1）*（q-1）
4. 找出公钥e ，e需要满足两个条件（1）1<e<Φ（n）(2) e 和 Φ（n）互质
5. 找出私钥d ，d需要满足e*d/Φ（n）余数是1
6. 加密算法：m的e次幂除以n求余数C
7. 解密算法：C的d次方除以n求余数m


### 安全性 ###

A在传播信息的时候传播了n，e，c

解密需要n，d，c

如果想知道私钥d，由上面可知，需要知道Φ（n）

Φ（n）需要知道p，q

p，q需要知道n，因为n=p*q

求p，q的过程叫分解质因数

但是大数的质因数特别难分解，所以RSA加密算法安全，当然如果可以做到大数质因数分解那么就。。。


eg：量子计算机

