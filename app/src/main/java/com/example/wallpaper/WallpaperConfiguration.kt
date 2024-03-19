package com.example.wallpaper

import android.content.Context

class WallpaperConfiguration {

    private var tokyoConfiguration = HashMap<String, MutableList<Wallpaper>>()

    fun getLayerFromConfiguration(environment: Environment,context: Context): LinkedHashMap<String, Wallpaper> {
        var wallpaperLayersMap = LinkedHashMap<String, Wallpaper>()
        when (environment) {
            Environment.DAY -> {
                var sky = Wallpaper(0f, 1f, listOf(R.drawable.sky), context)
                var train = Wallpaper(0f, 0f, listOf(R.drawable.t1,R.drawable.t3,R.drawable.t5,R.drawable.t7),context)
                var cloud1 = Wallpaper(0f,6f,listOf(R.drawable.ss_cloud1),context)
                var back_fuji = Wallpaper(0f,3f,listOf(R.drawable.back_fuji),context)
                var back_building1 = Wallpaper(0f,10f,listOf(R.drawable.ss_back_building1),context)
                var back_building2 = Wallpaper(0f,8f,listOf(R.drawable.ss_back_building2),context)
                var back_building3 = Wallpaper(0f,6f,listOf(R.drawable.ss_back_building3),context)
                var tower_skytree = Wallpaper(0f,14f,listOf(R.drawable.ss_tower_skytree),context)
                var cloud2 = Wallpaper(0f,20f,listOf(R.drawable.ss_cloud2),context)
                var back_nature = Wallpaper(0f,30f,listOf(R.drawable.ss_back_nature),context)
                var bridge = Wallpaper(0f,45f,listOf(R.drawable.ss_bridge),context)
                wallpaperLayersMap.put("sky",sky)
                wallpaperLayersMap.put("cloud1",cloud1)
                wallpaperLayersMap.put("back_fuji",back_fuji)
                wallpaperLayersMap.put("back_building3",back_building3)
                wallpaperLayersMap.put("back_building2",back_building2)
                wallpaperLayersMap.put("back_building1",back_building1)
                wallpaperLayersMap.put("tower_skytree",tower_skytree)
                wallpaperLayersMap.put("cloud2",cloud2)
                wallpaperLayersMap.put("back_nature",back_nature)
                wallpaperLayersMap.put("train",train)
                wallpaperLayersMap.put("bridge",bridge)
                CURRENT_ENVIRONMENT = Environment.DAY
            }
            Environment.AFTERNOON -> {}
            Environment.NIGHT -> {
                var sky = Wallpaper(0f, 1f, listOf(R.drawable.ns_night_sky), context)
                var train = Wallpaper(0f, 0f, listOf(R.drawable.t1,R.drawable.t3,R.drawable.t5,R.drawable.t7),context)
                var cloud1 = Wallpaper(0f,6f,listOf(R.drawable.ns_cloud1),context)
                var back_fuji = Wallpaper(0f,3f,listOf(R.drawable.ns_back_fuji),context)
                var back_building1 = Wallpaper(0f,10f,listOf(R.drawable.ns_back_building1),context)
                var back_building2 = Wallpaper(0f,8f,listOf(R.drawable.ns_back_building2),context)
                var back_building3 = Wallpaper(0f,6f,listOf(R.drawable.ns_back_building3),context)
                var tower_skytree = Wallpaper(0f,14f,listOf(R.drawable.ns_tower_skytree),context)
                var cloud2 = Wallpaper(0f,20f,listOf(R.drawable.ns_cloud2),context)
                var back_nature = Wallpaper(0f,30f,listOf(R.drawable.ns_back_nature),context)
                var bridge = Wallpaper(0f,45f,listOf(R.drawable.ns_bridge),context)
                wallpaperLayersMap.put("sky",sky)
                wallpaperLayersMap.put("cloud1",cloud1)
                wallpaperLayersMap.put("back_fuji",back_fuji)
                wallpaperLayersMap.put("back_building3",back_building3)
                wallpaperLayersMap.put("back_building2",back_building2)
                wallpaperLayersMap.put("back_building1",back_building1)
                wallpaperLayersMap.put("tower_skytree",tower_skytree)
                wallpaperLayersMap.put("cloud2",cloud2)
                wallpaperLayersMap.put("back_nature",back_nature)
                wallpaperLayersMap.put("train",train)
                wallpaperLayersMap.put("bridge",bridge)
                CURRENT_ENVIRONMENT = Environment.NIGHT
            }
        }
        return wallpaperLayersMap
    }

    companion object {
        enum class Environment {
            DAY,
            AFTERNOON,
            NIGHT
        }
        var CURRENT_ENVIRONMENT : Environment = Environment.DAY
    }
}