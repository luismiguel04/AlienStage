package com.example.alienstage

import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        val logo: ImageView = findViewById(R.id.ivLogo)
        val centerPoint: View = findViewById(R.id.centerPoint)

        // Mostrar el logo
        logo.animate().alpha(1f).setDuration(1500).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: android.animation.Animator) {
                startCirclesAnimation(centerPoint)
            }
        })
    }

    private fun startCirclesAnimation(centerPoint: View) {
        val duration = 2000L

        // Crear y animar círculos
        for (i in 0..3) {
            val circle = View(this).apply {
                background = ShapeDrawable(OvalShape()).apply {
                    paint.color = resources.getColor(android.R.color.white, null)
                }
                layoutParams = centerPoint.layoutParams
                alpha = 0.5f
                scaleX = 0.1f
                scaleY = 0.1f
            }

            (centerPoint.parent as ViewGroup).addView(circle)

            // Animación de escalado
            val scaleX = ObjectAnimator.ofFloat(circle, "scaleX", 0.1f, 3f)
            val scaleY = ObjectAnimator.ofFloat(circle, "scaleY", 0.1f, 3f)

            // Animación de movimiento
            val translationX = ObjectAnimator.ofFloat(circle, "translationX", when (i) {
                0 -> -300f
                1 -> 300f
                2 -> 300f
                3 -> -300f
                else -> 0f
            })

            val translationY = ObjectAnimator.ofFloat(circle, "translationY", when (i) {
                0 -> -300f
                1 -> -300f
                2 -> 300f
                3 -> 300f
                else -> 0f
            })

            // Configuración de duración de animaciones
            scaleX.duration = duration
            scaleY.duration = duration
            translationX.duration = duration
            translationY.duration = duration

            // Ejecutar animaciones
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(scaleX, scaleY, translationX, translationY)
            animatorSet.start()

            animatorSet.doOnEnd {
                (circle.parent as ViewGroup).removeView(circle)
            }
        }

        // Mover a la siguiente actividad
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, duration)
    }

    private fun enableEdgeToEdge() {
        // Configura el edge-to-edge según sea necesario
    }
}