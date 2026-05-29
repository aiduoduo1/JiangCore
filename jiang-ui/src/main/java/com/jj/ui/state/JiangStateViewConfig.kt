package com.jj.ui.state

data class JiangStateViewConfig(
    val loadingText: CharSequence = "加载中...",
    val emptyText: CharSequence = "暂无数据",
    val errorText: CharSequence = "加载失败",
    val retryText: CharSequence = "重试",
)
