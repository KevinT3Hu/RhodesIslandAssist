package com.kevin.rhodesislandassist.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun launchUrl(context: Context,url:String)=context.startActivity(
    Intent().apply {
        action = "android.intent.action.VIEW"
        data = Uri.parse(url)
    }
)