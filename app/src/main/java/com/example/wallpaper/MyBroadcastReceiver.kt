package com.example.wallpaper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MyBroadcastReceiver : BroadcastReceiver() {

    var speedAdd = false

    companion object {
        var NIGHT_TIME_ALARM =101
        var DAY_TIME_ALARM =100
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1 != null){
            when (p1.action) {
                Intent.ACTION_POWER_CONNECTED -> {
                    runBlocking {
                        launch(Dispatchers.IO) {
                            var speed = Wallpaper(0f, 360f, listOf(R.drawable.speed), p0!!)
                            MyWallpaperService.mainWallpaperMap.forEach {
                                it.value.horizontalSpeed *= 4
                            }
                            MyWallpaperService.mainWallpaperMap.put("speed",speed)
                            speedAdd = true
                        }
                    }
                }
                Intent.ACTION_POWER_DISCONNECTED -> {
                    if (speedAdd) {
                        MyWallpaperService.mainWallpaperMap.forEach {
                            it.value.horizontalSpeed /= 4
                        }
                        MyWallpaperService.mainWallpaperMap.remove("speed")
                    }
                    speedAdd = false
                }
            }
        }
    }

}