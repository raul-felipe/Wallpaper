package com.example.wallpaper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget

class Wallpaper(var verticalSpeed: Float, var horizontalSpeed: Float, var wallpaperBitmapList:List<Int>, var context: Context) {

    var verticalLocation: Float = 0f
    var horizontalLocation: Float = 0f

    var framesToNextBitmap = 3
    var bitmapIndex = 0

    var wallpaperList: MutableList<Bitmap> = ArrayList()
    var targetList: MutableList<FutureTarget<Bitmap>> = ArrayList()



    init {
        wallpaperBitmapList.forEach { it ->
            var target = Glide.with(context).asBitmap().load(it).submit()
            targetList.add(target)
            var bitMap = target.get()
            wallpaperList.add(bitMap)
        }
    }

    fun recycleBitmaps(){
        wallpaperList.forEach {
            it.recycle()
        }
    }

    fun clearBitmaps(){
        targetList.forEach {
            Glide.with(context).clear(it);
        }
    }


    fun update(canvas: Canvas){
        var currentBitMap = wallpaperList[bitmapIndex]
        framesToNextBitmap--
        if (framesToNextBitmap==0){
            bitmapIndex++
            if (bitmapIndex==wallpaperList.size)
                bitmapIndex = 0

            framesToNextBitmap = 3
        }

        var wallpaperWidth = (canvas.height.toFloat() / currentBitMap.height * currentBitMap.width).toInt()

//        if (horizontalLocation<=-currentBitMap.width.toFloat())
//            horizontalLocation+=currentBitMap.width.toFloat()

        if (horizontalLocation<=-wallpaperWidth)
            horizontalLocation+=wallpaperWidth



        canvas.drawBitmap(currentBitMap,
            null,
            Rect(
                horizontalLocation.toInt(),
                0,
                wallpaperWidth+horizontalLocation.toInt(),
                canvas.height),
            null)

        canvas.drawBitmap(currentBitMap,null, Rect(
            wallpaperWidth+horizontalLocation.toInt(),
            0,
            wallpaperWidth*2+horizontalLocation.toInt(),
            canvas.height),null)

//        canvas.drawBitmap(currentBitMap, horizontalLocation, 0f, null)
//        canvas.drawBitmap(currentBitMap, horizontalLocation+currentBitMap.width, 0f, null)

        horizontalLocation -= horizontalSpeed

    }

}