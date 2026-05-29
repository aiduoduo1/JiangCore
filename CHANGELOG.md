# CHANGELOG

## v0.1.0 - 2026-05-29

### 阶段定位

JiangCore 第一阶段基础框架版本。

该版本完成 Android 多模块 Library 框架的基础骨架、页面开发范式、网络层、存储层和 app-sample 示例验证。

### 已完成

#### 工程结构

- 完成多模块工程结构
- 统一包名为 `com.jj.xxx`
- 完成根目录 `CODEX.md`
- 完成各模块 README
- 完成依赖边界约束

#### jiang-core

- 提供 `JiangCore` 初始化入口
- 提供 `JiangConfig` 配置类

#### jiang-common

- 提供 `JiangResult`
- 提供 `JiangException`
- 提供 `JiangErrorCode`
- 提供 `JiangLog`

#### jiang-arch

- 提供 `JiangActivity`
- 提供 `JiangVmActivity`
- 提供 `JiangFragment`
- 提供 `JiangVmFragment`
- 提供 `JiangStateVmActivity`
- 提供 `JiangViewModel`
- 提供 `JiangUiState`
- 支持 ViewBinding
- 支持 ViewModel 自动注入
- 支持 ViewModel 协程封装
- 支持 UI 状态自动分发

#### jiang-ui

- 提供 `JiangToolbar`
- 提供 `JiangToolbarActivity`
- 提供 `JiangToolbarVmActivity`
- 提供 `JiangToolbarStateVmActivity`
- 提供 `JiangStateLayout`
- 支持 Toolbar 标题、返回按钮、菜单
- 支持 Loading / Empty / Error / Content 页面状态切换

#### jiang-network

- 提供 `JiangNetwork`
- 提供 `JiangNetworkConfig`
- 提供 Retrofit 创建能力
- 提供 OkHttp 创建能力
- 提供 Gson Converter
- 提供 HeaderInterceptor
- 提供 `JiangApiCaller.safeApiCall`
- 提供网络异常统一转换

#### jiang-storage

- 提供 `JiangStorage`
- 提供 `JiangStorageConfig`
- 提供 `JiangKeyValueStore`
- 提供 MMKV Key-Value 实现

#### app-sample

- 提供 Showcase 首页
- 提供 Toolbar 示例
- 提供 StateLayout 示例
- 提供 Network 示例
- 提供 Storage 示例
- 完成框架主要能力验证

### 约束

- 不使用旧包名 `com.jiang.xxx`
- 不使用 DataBinding
- 不引入业务概念
- `jiang-core` 不依赖 network / storage / ui / arch
- `jiang-network` 不依赖 ui / storage / arch
- `jiang-storage` 不依赖 network / ui / arch
- `jiang-arch` 不依赖 network / storage / ui
- `Retrofit / OkHttp / Gson` 只在 `jiang-network`
- `MMKV / DataStore` 只在 `jiang-storage`