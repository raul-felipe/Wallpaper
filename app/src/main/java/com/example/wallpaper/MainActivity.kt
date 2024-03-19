package com.example.wallpaper

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.Glide
import com.example.wallpaper.MyWallpaperService.Companion.needUpdate
import com.example.wallpaper.MyWallpaperService.Companion.removePreviewWallpaper
import com.example.wallpaper.MyWallpaperService.Companion.updateMainWallpaperList
import com.example.wallpaper.ui.theme.WallPaperTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Your best wallpaper app",
                )
                Button(onClick = {
                    MyWallpaperService.type = 1
                    setWallpaper()
                }) {
                    Text("Start Tokyo")
                }
//                Button(onClick = {
//                    MyWallpaperService.type = 0
//                    setWallpaper()
//                }) {
//                    Text("Start Nature")
//                }
            }
        }
    }

    fun setWallpaper() {
        val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
        intent.putExtra(
            WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
            ComponentName(this, MyWallpaperService::class.java)
        )
        startActivityForResult(intent,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== RESULT_OK)
            updateMainWallpaperList()
        else
            removePreviewWallpaper()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WallPaperTheme {
        Greeting("Android")
    }
}