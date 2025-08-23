package com.yuwjoo.myhome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.yuwjoo.myhome.ui.theme.MyHomeTheme
import com.yuwjoo.myhome.ui.webview.MyWebView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyHomeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyWebView(
                        mainActivity = this,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}