# VERSION

当前版本：`v0.1.0`

## v0.1.0

JiangCore 第一阶段基础框架版本。

### 版本状态

稳定性：基础可用  
用途：内部框架验证 / 后续扩展基础  
是否建议用于正式业务项目：暂不建议直接用于生产，建议先在 sample 和内部项目中继续验证

### 当前能力

- 多模块 Library 工程
- 框架初始化
- 公共结果 / 异常 / 日志
- Activity / Fragment 基类
- ViewBinding 支持
- ViewModel 自动注入
- ViewModel 协程封装
- UI 状态自动分发
- Toolbar 封装
- StateLayout 页面状态
- Retrofit / OkHttp / Gson 网络层
- MMKV Key-Value 存储
- app-sample Showcase 示例

### 下一版本规划

`v0.2.0` 计划增强页面开发体验：

- Toast 封装
- LoadingDialog 封装
- ConfirmDialog 封装
- 防重复点击
- Network 动态 Header
- TokenProvider 预留
- Storage 对象存储