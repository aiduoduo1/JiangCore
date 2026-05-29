# jiang-network/README.md

# jiang-network

## 模块定位

`jiang-network` 是 JiangCore 的网络请求模块，包名为：

```text
com.jj.network
```

该模块负责封装 Retrofit、OkHttp、统一请求头、Token、网络异常、接口响应适配、请求日志和安全请求调用能力。

## 核心职责

* 初始化网络请求能力
* 创建 Retrofit Service
* 管理统一 BaseUrl
* 管理统一请求头
* 管理 Token 注入
* 统一处理网络异常
* 统一处理接口响应结构
* 提供安全请求封装
* 提供网络请求日志能力

## 不负责什么

* 不处理页面 Loading
* 不处理 Activity / Fragment
* 不处理具体业务页面
* 不强绑定某一种后端返回结构
* 不直接处理页面跳转
* 不直接处理业务登录逻辑

## 主要包结构

```text
com.jj.network
├── config        网络配置
├── contract      扩展接口
├── model         网络模型
├── exception     网络异常
├── interceptor   OkHttp 拦截器
├── retrofit      Retrofit 创建
└── ext           网络扩展函数
```

## 对外能力

* `JiangHttp`：网络模块初始化入口
* `HttpConfig`：网络配置
* `RetrofitFactory`：Retrofit 创建工厂
* `ServiceCreator`：接口 Service 创建器
* `TokenProvider`：Token 提供器
* `HeaderProvider`：全局请求头提供器
* `LoginExpiredHandler`：登录过期处理器
* `ResponseAdapter`：接口响应适配器
* `NetworkExceptionHandler`：网络异常处理器
* `HeaderInterceptor`：请求头拦截器
* `TokenInterceptor`：Token 拦截器
* `LoggingInterceptor`：日志拦截器

## 使用示例

```kotlin
JiangHttp.init(
    config = HttpConfig(
        baseUrl = "https://api.example.com/",
        tokenProvider = object : TokenProvider {
            override fun getToken(): String? {
                return "token"
            }
        },
        headerProvider = object : HeaderProvider {
            override fun getHeaders(): Map<String, String> {
                return mapOf(
                    "platform" to "android"
                )
            }
        }
    )
)
```

创建接口：

```kotlin
interface UserApi {

    @GET("user/info")
    suspend fun getUserInfo(): ApiResponse<UserInfo>
}
```

创建 Service：

```kotlin
val userApi = ServiceCreator.create(UserApi::class.java)
```

## 依赖关系

```kotlin
implementation(project(":jiang-common"))
implementation(project(":jiang-core"))
```

## 设计原则

`jiang-network` 必须保持可扩展。

不同项目的后端返回结构可能不同，所以网络模块不能把某一种 `BaseResult` 写死在框架里。

框架应该通过 `ResponseAdapter`、`TokenProvider`、`HeaderProvider`、`LoginExpiredHandler` 等接口给业务项目留扩展点。

---

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

```kotlin
JiangToast.show("保存成功")
```

```kotlin
JiangLoading.show(context)
JiangLoading.dismiss()
```

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

---

# jiang-storage/README.md

# jiang-storage

## 模块定位

`jiang-storage` 是 JiangCore 的本地存储模块，包名为：

```text
com.jj.storage
```

该模块负责封装 Key-Value 存储、缓存管理、本地数据读写和存储实现隔离。

业务层不应该直接依赖 MMKV、SharedPreferences 或 DataStore，而应该通过 JiangCore 提供的统一存储接口进行访问。

## 核心职责

* 提供统一 Key-Value 存储接口
* 封装 MMKV / DataStore / SharedPreferences 等存储实现
* 提供缓存管理能力
* 提供本地数据清理能力
* 提供 Token、用户配置等轻量数据存储能力
* 隔离业务层和具体存储框架

## 不负责什么

* 不处理复杂数据库关系
* 不处理业务数据模型
* 不处理网络缓存策略
* 不处理账号体系逻辑
* 不直接绑定某个具体业务场景

## 主要包结构

```text
com.jj.storage
├── kv            Key-Value 存储
├── cache         缓存管理
└── ext           存储扩展函数
```

## 对外能力

* `JiangStorage`：存储模块入口
* `KvStorage`：Key-Value 存储接口
* `MMKVStorage`：MMKV 实现
* `DataStoreStorage`：DataStore 实现
* `SpStorage`：SharedPreferences 实现
* `CacheManager`：缓存管理器

## 使用示例

保存数据：

```kotlin
JiangStorage.kv.putString("token", token)
```

读取数据：

```kotlin
val token = JiangStorage.kv.getString("token")
```

删除数据：

```kotlin
JiangStorage.kv.remove("token")
```

清空数据：

```kotlin
JiangStorage.kv.clear()
```

## 依赖关系

```kotlin
implementation(project(":jiang-common"))
implementation(project(":jiang-core"))
```

## 设计原则

`jiang-storage` 的核心价值是隔离具体存储实现。

业务项目不应该直接写：

```kotlin
MMKV.defaultMMKV().encode("token", token)
```

而应该通过统一入口访问：

```kotlin
JiangStorage.kv.putString("token", token)
```

这样后续即使从 MMKV 切换到 DataStore，也不会影响业务层代码。
