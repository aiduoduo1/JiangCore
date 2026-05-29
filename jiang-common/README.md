# jiang-common/README.md

# jiang-common

## 模块定位

`jiang-common` 是 JiangCore 的最底层公共模块，包名为：

```text
com.jj.common
```

该模块负责提供框架中最基础、最通用的能力，例如统一结果模型、异常模型、日志抽象、通用扩展函数和基础工具类。

`jiang-common` 应尽量保持纯净，不应该依赖具体业务，也不应该绑定 Android 页面、网络请求或 UI 组件。

## 核心职责

* 提供统一结果模型
* 提供统一异常模型
* 提供基础日志抽象
* 提供通用 Kotlin 扩展函数
* 提供基础工具类
* 为其他 JiangCore 模块提供公共能力支撑

## 不负责什么

* 不处理业务逻辑
* 不处理 Android 页面
* 不处理网络请求
* 不处理 Loading、Toast、Dialog 等 UI 能力
* 不直接依赖其他上层模块

## 主要包结构

```text
com.jj.common
├── result        统一结果模型
├── exception     统一异常模型
├── logger        日志抽象
├── ext           Kotlin 扩展函数
└── util          通用工具类
```

## 对外能力

* `JiangResult`：统一成功 / 失败结果封装
* `JiangException`：统一异常基类
* `JiangLogger`：日志能力抽象
* `StringExt`：字符串扩展
* `CollectionExt`：集合扩展
* `TimeUtils`：时间工具类
* `JsonUtils`：JSON 工具类

## 依赖关系

`jiang-common` 是最底层模块，不依赖 JiangCore 中的其他模块。

其他模块可以依赖它：

```kotlin
implementation(project(":jiang-common"))
```

## 设计原则

`jiang-common` 必须保持轻量、通用、稳定。

该模块只放所有项目都可能用到的基础能力，不能放任何具体业务概念，也不能因为某一个项目的特殊需求破坏公共层的纯净性。

---

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

---

# jiang-arch/README.md

# jiang-arch

## 模块定位

`jiang-arch` 是 JiangCore 的架构层模块，包名为：

```text
com.jj.arch
```

该模块负责封装 Android 项目中常用的 MVVM 基础能力，包括 Activity 基类、Fragment 基类、ViewModel 基类、Repository 基类、页面状态、一次性事件和协程请求封装。

## 核心职责

* 提供统一 Activity 基类
* 提供统一 Fragment 基类
* 提供统一 ViewModel 基类
* 提供统一 Repository 基类
* 提供页面状态模型
* 提供一次性事件模型
* 提供协程请求封装
* 提供 Flow / Lifecycle 扩展能力

## 不负责什么

* 不定义业务页面
* 不定义业务接口
* 不处理具体后端返回结构
* 不直接持有业务数据
* 不直接处理 Retrofit 创建
* 不直接处理本地存储细节

## 主要包结构

```text
com.jj.arch
├── base          MVVM 基类
├── state         页面状态
├── effect        一次性事件
├── event         UI 事件
├── launcher      请求启动器
└── ext           生命周期 / Flow 扩展
```

## 对外能力

* `JiangActivity`：Activity 基类
* `JiangFragment`：Fragment 基类
* `JiangViewModel`：ViewModel 基类
* `JiangRepository`：Repository 基类
* `JiangUiState`：统一页面状态
* `JiangEffect`：一次性事件
* `launchRequest()`：统一协程请求封装
* `collectWithLifecycle()`：生命周期安全的数据收集扩展

## 使用示例

```kotlin
class MainViewModel : JiangViewModel() {

    fun loadData() {
        launchRequest {
            // 执行业务请求
        }
    }
}
```

```kotlin
class MainActivity : JiangActivity<ActivityMainBinding>() {

    override fun initView() {
        // 初始化页面
    }

    override fun initData() {
        // 初始化数据
    }
}
```

## 依赖关系

```kotlin
implementation(project(":jiang-common"))
implementation(project(":jiang-core"))
implementation(project(":jiang-ui"))
```

## 设计原则

`jiang-arch` 只解决页面开发中的通用架构问题。

它应该让业务页面更轻、更干净，但不能把业务逻辑、接口逻辑、存储逻辑全部塞进基类里。

`JiangActivity` 和 `JiangViewModel` 不能变成上帝类。
