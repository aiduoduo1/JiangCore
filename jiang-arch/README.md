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
