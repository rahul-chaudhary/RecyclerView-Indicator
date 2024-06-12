package com.example.recyclerviewindicator

object DummyData {
    fun fetchImages(): List<Int> {
        val imagesList: List<Int> = listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4
        )
        return imagesList
    }
}