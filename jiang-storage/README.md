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
