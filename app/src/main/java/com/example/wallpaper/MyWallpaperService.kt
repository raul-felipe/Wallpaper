package com.example.wallpaper

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Canvas
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.example.wallpaper.WallpaperConfiguration.Companion.CURRENT_ENVIRONMENT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Calendar
import java.util.concurrent.Executors


class MyWallpaperService : WallpaperService() {

    lateinit var receiver: MyBroadcastReceiver

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        var type: Int = 1
        var needUpdate = true
        var mainWallpaperEngine : WallpaperEngine? = null
        var previewWallpaperEngine : WallpaperEngine? = null

//        var mainWallpaperList : MutableList<Wallpaper> = ArrayList()
//        var previewWallpaperList : MutableList<Wallpaper> = ArrayList()
        var mainWallpaperMap = LinkedHashMap<String, Wallpaper>()
        var previewWallpaperMap = LinkedHashMap<String, Wallpaper>()


        fun updateMainWallpaperList(){
            mainWallpaperMap.forEach {
//                it.clearBitmaps()
            }
            mainWallpaperMap = previewWallpaperMap.toMutableMap() as LinkedHashMap<String, Wallpaper>
            previewWallpaperMap.clear()
            mainWallpaperEngine!!.frameManager.updateWallpaperList(mainWallpaperMap)
            previewWallpaperEngine!!.frameManager.stopAnimation()
        }

        fun removePreviewWallpaper(){
            previewWallpaperMap.forEach {
//                it.clearBitmaps()
            }
            previewWallpaperMap.clear()
        }

        fun updateMainWallpaperList(newWallpaper:LinkedHashMap<String, Wallpaper>){
            mainWallpaperMap.forEach {
//                it.clearBitmaps()
            }
            mainWallpaperMap = newWallpaper
            mainWallpaperEngine!!.frameManager.updateWallpaperList(mainWallpaperMap)
        }
    }
    fun countrySide(canvas: Canvas, holder: SurfaceHolder): FrameManager {
        var sky =
            Wallpaper(0f, 1f, listOf(R.drawable.sky), this@MyWallpaperService)
        var cloudsBackground =
            Wallpaper(0f, 3f, listOf(R.drawable.m2), this@MyWallpaperService)
        var cloudsForeground =
            Wallpaper(0f, 20f, listOf(R.drawable.m3), this@MyWallpaperService)
        var mountains =
            Wallpaper(0f, 5f, listOf(R.drawable.m1), this@MyWallpaperService)
        var train = Wallpaper(
            0f, 0f, listOf(
                R.drawable.t1,
                R.drawable.t2,
                R.drawable.t3,
                R.drawable.t4,
                R.drawable.t5,
                R.drawable.t6,
                R.drawable.t7,
                R.drawable.t8
            ),
            this@MyWallpaperService
        )
        var grassBackground =
            Wallpaper(0f, 45f, listOf(R.drawable.g3), this@MyWallpaperService)
        var grassForeground =
            Wallpaper(0f, 45f, listOf(R.drawable.g4), this@MyWallpaperService)
        var treeBackground =
            Wallpaper(0f, 30f, listOf(R.drawable.g1), this@MyWallpaperService)
        var treeForeground =
            Wallpaper(0f, 35f, listOf(R.drawable.g2), this@MyWallpaperService)

//        previewWallpaperMap.add(sky)
//        previewWallpaperMap.add(cloudsBackground)
//        previewWallpaperMap.add(mountains)
//        previewWallpaperMap.add(cloudsForeground)
//        previewWallpaperMap.add(treeBackground)
//        previewWallpaperMap.add(treeForeground)
//        previewWallpaperMap.add(train)
//        previewWallpaperMap.add(grassBackground)
//        previewWallpaperMap.add(grassForeground)

        holder?.unlockCanvasAndPost(canvas)

        return FrameManager(
            holder,
            resources,
            previewWallpaperMap
        )

    }

    override fun onCreateEngine(): Engine = WallpaperEngine()

    inner class WallpaperEngine : WallpaperService.Engine() {

        lateinit var frameManager: FrameManager

        override fun onVisibilityChanged(visible: Boolean) {
            if (visible) {
                if (::frameManager.isInitialized) {
                    frameManager.restartAnimation()
                        //night time check
                    if (!isPreview) {
                        runBlocking {
                            launch(Dispatchers.IO) {
                                var currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                                if ((CURRENT_ENVIRONMENT != WallpaperConfiguration.Companion.Environment.NIGHT) && (currentHour >= 19 || currentHour <= 6)) {
                                    updateMainWallpaperList(WallpaperConfiguration().getLayerFromConfiguration(
                                        WallpaperConfiguration.Companion.Environment.NIGHT,
                                        this@MyWallpaperService
                                    ))
                                } else if (CURRENT_ENVIRONMENT != WallpaperConfiguration.Companion.Environment.DAY && currentHour > 6 && currentHour < 19) {
                                    updateMainWallpaperList(WallpaperConfiguration().getLayerFromConfiguration(
                                        WallpaperConfiguration.Companion.Environment.DAY,
                                        this@MyWallpaperService
                                    ))
                                }

                            }
                        }
                    }
                }
            } else {
                if (::frameManager.isInitialized) {
                    frameManager.stopAnimation()
                }
            }
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            frameManager.stopAnimation()
            if (!isPreview){
                if (::receiver.isInitialized)
                    unregisterReceiver(receiver)
            }
        }

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)

            if (isPreview) {
                previewWallpaperMap.clear()
                needUpdate = true
                previewWallpaperEngine = this
                Executors.newSingleThreadExecutor().execute {
                    val canvas = holder?.lockCanvas()
                    if (canvas != null && isPreview) {
                        if (type == 1) {
                            var currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                            var currentEnvironment = WallpaperConfiguration.Companion.Environment.DAY
                            if (currentHour >= 19 || currentHour <= 6)
                                currentEnvironment = WallpaperConfiguration.Companion.Environment.NIGHT
                            if (currentHour in 6..19)
                                currentEnvironment = WallpaperConfiguration.Companion.Environment.DAY
                            previewWallpaperMap = WallpaperConfiguration().getLayerFromConfiguration(
                                currentEnvironment,
                                this@MyWallpaperService
                            )
                            holder.unlockCanvasAndPost(canvas)
                            frameManager = FrameManager(
                                holder,
                                resources,
                                previewWallpaperMap
                            )
                        }else
                            frameManager = countrySide(canvas, holder)
                        frameManager.startAnimation()
                    }
                }
            }
            else {
                needUpdate = false
                mainWallpaperEngine = this
                frameManager = FrameManager(holder!!,resources, LinkedHashMap())
                frameManager.startAnimation()
                receiver = MyBroadcastReceiver()
                var filter = IntentFilter()
                filter.addAction(Intent.ACTION_POWER_CONNECTED)
                filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
                registerReceiver(receiver, filter)
            }


        }

    }
}