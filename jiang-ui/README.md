# jiang-ui/README.md

# jiang-ui

## 模块定位

`jiang-ui` 是 JiangCore 的通用 UI 模块，包名为：

```text
com.jj.ui
```

该模块负责封装 Android 项目中常用的通用 UI 能力，例如 Loading、Toast、Dialog、页面状态布局、空页面、错误页面、View 扩展和通用 Adapter。

## 核心职责

* 提供统一 Loading 能力
* 提供统一 Toast 能力
* 提供统一 Dialog 能力
* 提供页面状态布局
* 提供空页面展示
* 提供错误页面展示
* 提供 View 扩展函数
* 提供防重复点击能力
* 提供 RecyclerView 通用能力

## 不负责什么

* 不编写业务页面
* 不处理网络请求
* 不处理业务状态
* 不直接依赖业务模块
* 不强绑定某个具体项目的 UI 风格

## 主要包结构

```text
com.jj.ui
├── loading       Loading 组件
├── toast         Toast 组件
├── dialog        Dialog 组件
├── state         页面状态布局
├── adapter       通用 Adapter
└── ext           View 扩展函数
```

## 对外能力

* `JiangLoading`：统一 Loading
* `LoadingDialog`：Loading 弹窗
* `JiangToast`：统一 Toast
* `ConfirmDialog`：确认弹窗
* `MessageDialog`：消息弹窗
* `StateLayout`：页面状态布局
* `EmptyView`：空页面
* `ErrorView`：错误页面
* `ViewExt`：View 扩展函数
* `JiangAdapter`：通用列表 Adapter

## 使用示例

Toast 提示：

```kotlin
JiangToast.show("保存成功")
```

Loading 展示：

```kotlin
JiangLoading.show(context)
JiangLoading.dismiss()
```

页面状态切换：

```kotlin
stateLayout.showLoading()
stateLayout.showContent()
stateLayout.showEmpty("暂无数据")
stateLayout.showError("加载失败")
```

防重复点击：

```kotlin
button.setSingleClickListener {
    // 执行点击事件
}
```

## 依赖关系

```kotlin
implementation(project(":jiang-common"))
implementation(project(":jiang-core"))
```

## 设计原则

`jiang-ui` 只提供通用 UI 能力，不写业务 UI。

所有 UI 能力都应该低侵入、可替换、可扩展。

业务项目可以直接使用默认组件，也可以替换 Loading、Toast、Dialog 的具体实现。
