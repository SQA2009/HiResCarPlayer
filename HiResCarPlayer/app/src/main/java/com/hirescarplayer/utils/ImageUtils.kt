package com.example.hirescarplayer.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.palette.graphics.Palette

object ImageUtils {
    @ColorInt
    fun getVibrantColor(bitmap: Bitmap, @ColorInt defaultColor: Int): Int {
        val palette = Palette.from(bitmap).generate()
        return palette.getVibrantColor(defaultColor)
    }

    fun blurBitmap(context: Context, bitmap: Bitmap, radius: Float = 25f): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val renderEffect = RenderEffect.createBlurEffect(radius, radius, Shader.TileMode.CLAMP)
            val blurred = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
            val canvas = Canvas(blurred)
            canvas.setRenderEffect(renderEffect)
            canvas.drawBitmap(bitmap, 0f, 0f, null)
            blurred
        } else {
            bitmap
        }
    }

    fun setBlurredImage(imageView: ImageView, bitmap: Bitmap, radius: Float = 25f) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            imageView.setImageBitmap(bitmap)
            val effect = RenderEffect.createBlurEffect(radius, radius, Shader.TileMode.CLAMP)
            imageView.setRenderEffect(effect)
        } else {
            imageView.setImageBitmap(bitmap)
        }
    }
}