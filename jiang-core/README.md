# jiang-core/README.md

# jiang-core

## 模块定位

`jiang-core` 是 JiangCore 的运行时核心模块，包名为：

```text
com.jj.core
```

该模块负责框架初始化、全局配置、上下文管理、Activity 栈管理、生命周期监听和基础运行环境。

它是 JiangCore 在 Android 环境中的核心入口。

## 核心职责

* 提供 `JiangCore.init()` 初始化入口
* 管理全局配置
* 保存 Application Context
* 管理 Activity 栈
* 监听 App 前后台状态
* 提供基础生命周期能力
* 提供框架运行时基础支撑

## 不负责什么

* 不负责网络请求
* 不负责页面 UI
* 不负责业务逻辑
* 不负责本地缓存
* 不直接依赖 `jiang-network`
* 不直接依赖 `jiang-ui`
* 不直接依赖 `jiang-arch`

## 主要包结构

```text
com.jj.core
├── config        框架配置
├── context       全局上下文
├── app           App / Activity 管理
├── lifecycle     生命周期监听
├── crash         崩溃处理
└── initializer   初始化器
```

## 对外能力

* `JiangCore`：框架初始化入口
* `JiangConfig`：框架全局配置
* `JiangContext`：全局 Context 持有
* `ActivityStackManager`：Activity 栈管理
* `AppForegroundObserver`：App 前后台状态监听
* `JiangInitializer`：模块初始化抽象

## 使用示例

```kotlin
JiangCore.init(
    context = application,
    config = JiangConfig(
        debug = BuildConfig.DEBUG,
        appName = "Demo"
    )
)
```

## 依赖关系

```kotlin
implementation(project(":jiang-common"))
```

## 设计原则

`jiang-core` 是所有 Android 能力模块的运行基础，但不能变成大杂烩。

该模块只负责框架运行环境，不承载网络、UI、业务页面、业务数据等具体功能。
