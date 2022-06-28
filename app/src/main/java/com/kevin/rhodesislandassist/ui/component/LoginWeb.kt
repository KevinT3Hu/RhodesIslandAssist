package com.kevin.rhodesislandassist.ui.component

import android.annotation.SuppressLint
import android.webkit.CookieManager
import android.webkit.WebView
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.kevin.rhodesislandassist.R

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginWeb(loginStatus: MutableState<Boolean>, token: MutableState<String>) {
    val webState = rememberWebViewState(url = "https://ak.hypergryph.com/user/login")
    val context = LocalContext.current
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Column {
                        Text(text = webState.pageTitle ?: "Loading")
                        if (webState.isLoading) {
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            )
        }
    ) {
        WebView(
            modifier = Modifier.padding(it),
            state = webState,
            onCreated = {
                it.settings.javaScriptEnabled = true
                it.settings.allowContentAccess = true
            },
            client = object : AccompanistWebViewClient() {
                override fun doUpdateVisitedHistory(
                    view: WebView?,
                    url: String?,
                    isReload: Boolean
                ) {
                    super.doUpdateVisitedHistory(view, url, isReload)
                    if (url == "https://ak.hypergryph.com/user/home") {
                        view?.loadUrl("https://as.hypergryph.com/user/info/v1/token_by_cookie")
                    } else if (url == "https://as.hypergryph.com/user/info/v1/token_by_cookie") {
                        val cookies = CookieManager.getInstance().getCookie(url)
                        val cookiesSeparated = cookies.split(";")
                        val tokenCookie = cookiesSeparated.find {
                            it.matches(Regex("token=.*"))
                        }?.split("=")?.get(1)
                        if (tokenCookie == null) {
                            Toast.makeText(context, R.string.toast_login_fail, Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            token.value = tokenCookie
                            loginStatus.value = true
                        }
                    }
                }
            }
        )
    }

}