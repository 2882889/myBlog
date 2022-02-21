### iOS破解开发流程

### 越狱

> 破解app的前提是需要有越狱系统，不然许多工作无法完成

#### 越狱方法

​	iPhone5s ~ iPhoneX 设备的 iOS12.0以后的系统可以使用[checkra1n](https://checkra.in/),工具进行不完美越狱（重启后需要重新越狱）

#### 越狱插件

##### apple File Conduit "2"

这个插件是查看所有系统文件的，安装后可以查看iPhone的所有目录

##### AppSync Unified

这个插件是关闭苹果签名验证的，安装后可以随意安装ipa

##### OpenSSH

这个插件是开启iPhone的SSH连接的，账号`root`, 密码`alpine`

##### Cycript

这个插件是提供运行中调试的，安装后可以调试运行中的app

### 运行中的应用调试

越狱的手机，可以通过[Cycript](http://www.cycript.org/),调试正在运行的应用

#### Cycript

> Cycript allows developers to explore and modify running applications on either iOS or Mac OS X using a hybrid of Objective-C++ and JavaScript syntax through an interactive console that features syntax highlighting and tab completion.
> (It also runs standalone on Android and Linux and provides access to Java, but without injection.)

##### Cycript安装

在`Cydia`中安装`Cycript`插件

##### Cycript使用

1. 终端使用ssh登录到手机
2. 手机上启动要调试的应用
3. 使用`ps -A`查看要调试的应用进程ID或进程名称
4. 使用`cycript -p 进程id`或`cycript -p 名称`启动`cycript`

##### Cycript封装









