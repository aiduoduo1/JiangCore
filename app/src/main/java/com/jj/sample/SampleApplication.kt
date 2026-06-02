package com.jj.sample

import android.app.Application
import com.jj.core.JiangCore
import com.jj.core.config.JiangConfig
import com.jj.network.JiangNetwork
import com.jj.network.config.JiangNetworkConfig
import com.jj.storage.JiangStorage
import com.jj.storage.config.JiangStorageConfig
import com.jj.ui.toast.JToast

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        JiangCore.init(
            application = this,
            config = JiangConfig(
                debug = BuildConfig.DEBUG,
                appName = "JiangCore Sample",
                baseUrl = "https://example.com/",
            ),
        )
        JiangNetwork.init(
            config = JiangNetworkConfig(
                baseUrl = "https://example.com/",
                dynamicHeadersProvider = {
                    mapOf(
                        "MES-UP-TOKEN" to JiangStorage.kv.getString(KEY_MES_UP_TOKEN, "").orEmpty(),
                        "User-Agent" to "JiangCoreSample",
                    )
                },
                debug = BuildConfig.DEBUG,
            ),
        )
        JiangStorage.init(
            application = this,
            config = JiangStorageConfig(
                defaultKvId = "sample_default",
                debug = BuildConfig.DEBUG,
            ),
        )
        JToast.init(this)
    }

    private companion object {
        const val KEY_MES_UP_TOKEN = "mes_up_token"
    }
}
