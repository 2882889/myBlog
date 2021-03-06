---
title: OC使用静态库中的类别
date: 2017-06-05 20:02:30
tags: iOS
---

## OC中使用静态库中的类别的问题 ##

OC的源文件经过 `clang` 编译首先生成中间代码,接着通过优化生成目标代码(目标代码中包括符号表,标示了此代码中的全局符号和静态符号,还标示了导入符号等),连接器会根据符号表分析各个目标代码之间的调用关系,然后将使用到的代码进行连接和重定位,最后生成可执行文件.

在编译Objective-C源文件到目标文件时,编译器并不知道方法的对应实现,只能在运行时才知道,所以编译器只会为类生成连接符号,不会对类中的方法生成连接符号,由于Category方法并不对应一个新类,所以不会生成连接符号,连接器也不会将Category方法合并到原始的类中,最终导致连接器忽略了Category方法,不会将其连接到可执行文件中.


> 在OC使用静态库中的类别,需要设置下面5种方法中的一种


## Other Linker Flags 设置 ##

* 1.通过在Other Linder Flags 添加 `-all\_load` 它会告诉编译器对”所有文档中的所有对象文件,不管里面的符号有没有被用到,全部都载入”,这样会产生比较大的二进制文件

* 2.通过在Other Linder Flags 添加 `-force\_load 和指定路径` e.g: `-force\_load $(SRCROOT)/RHAddressBook/libRHAddressBook.a` 这种方法会只载入指定的归档

* 3.通过在Other Linder Flags 添加 `-ObjC` ,这个标识告诉编译器”如果在文档中发现了OC代码,就把文档载入” 

* 4.在Xcode里build setting中的 Perform Single-Object PreLink，如果启用这个选项，所有的对象文件都会被合并成一个单文件（这不是真正的链接，所以叫做预链接），这个对象文件（有时被称做主对象文件(master object file)）被添加到文档中。现在如果主对象文件中的任何符号被认为是“在使用”，整个主对象文件都会被认为在使用，这样它里面的OC部分就会被载入了。因为里面的类都被正常符号化了，所以能使从这样的静态库中使用所有的Category

* 5.最后一种解决方法是在只有Category的源文件里添加Fake symbol。如果你想在运行时使用Category，一定要确保你以某种方法在编译时引用了fake symbol，这会使得对象文件以及它里面的OC代码被载入。例如，它可以是一个有空函数体的函数，也可以是一个被访问的全局变量（例如一个全局的int变量，只要它被读或者写了一次就足够了）。和上面其他的解决方法不一样，这种解决方法可以控制哪些category可以在运行时被编译后的代码使用（可以通过使用这个符号，使它们被链接并变得可用；也可以不使用这个符号，这样链接器就会忽略它）
