package com.example.wallpaper

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.SurfaceHolder

class FrameManager(var holder: SurfaceHolder, var resources: Resources, var mainWallpaperMap : LinkedHashMap<String, Wallpaper>) {

    private val mainHandler = Handler(Looper.getMainLooper())
    private lateinit var animationRunnable:Runnable
    var animationStarted = false

    fun updateWallpaperList (mainWallpaperMap : LinkedHashMap<String, Wallpaper>){
        stopAnimation()
        this.mainWallpaperMap = mainWallpaperMap
        startAnimation()
        restartAnimation()
    }

    fun startAnimation(){
        if (!animationStarted){
            var canvas: Canvas
            animationRunnable = Runnable {
                if (holder.surface.isValid) {
                    canvas = holder.lockCanvas()
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR)
                    if (canvas != null) {
                        mainWallpaperMap.forEach {
                            it.value.update(canvas)
                        }
                        holder.unlockCanvasAndPost(canvas)
                    }
                }
                mainHandler.postDelayed(animationRunnable, 1000/60)
            }
        }
    }

    fun stopAnimation(){
        if(animationStarted) {
            mainHandler.removeCallbacks(animationRunnable)
            animationStarted = false
        }
    }

    fun restartAnimation(){
        if (!animationStarted) {
            mainHandler.post(animationRunnable)
            animationStarted = true
        }
    }

}