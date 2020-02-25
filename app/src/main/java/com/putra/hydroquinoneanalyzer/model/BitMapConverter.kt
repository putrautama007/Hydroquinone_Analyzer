package com.putra.hydroquinoneanalyzer.model

import android.graphics.Bitmap
import android.graphics.Color

class BitMapConverter(private val bitmap: Bitmap) {
    fun getAverageColorRGB(): IntArray {
        val width = bitmap.width / 2
        val height = bitmap.height / 2
        var size = width * height
        var pixelColor: Int
        var r: Int
        var g: Int
        var b = 0
        g = b
        r = g
        for (x in 0 until width) {
            for (y in 0 until height) {
                pixelColor = bitmap.getPixel(x, y)
                if (pixelColor == 0) {
                    size--
                    continue
                }
                r += Color.red(pixelColor)
                g += Color.green(pixelColor)
                b += Color.blue(pixelColor)
            }
        }
        r /= size
        g /= size
        b /= size
        return intArrayOf(
            r, g, b
        )
    }
}