package com.mackosoft.core.image

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.mackosoft.core.BuildConfig
import com.mackosoft.core.base.datasource.api.RetrofitClient
import com.mackosoft.network.okHttpClient
import java.io.InputStream

@GlideModule
class TestFDJGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.apply {
            setLogLevel(
                if (BuildConfig.DEBUG)
                    Log.DEBUG
                else
                    Log.INFO
            )
        }
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val client = okHttpClient(RetrofitClient.globalOkHttpClient)
        registry.replace(
            GlideUrl::class.java, InputStream::class.java,
            OkHttpUrlLoader.Factory(client)
        )
    }
}