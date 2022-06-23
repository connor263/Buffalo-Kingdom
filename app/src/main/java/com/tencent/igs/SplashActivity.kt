package com.tencent.igs

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.animation.doOnEnd

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val parrtstuDataDaomnt = findViewById<FrameLayout>(R.id.fl_parent)
        val artstuDataDaomame = findViewById<TextView>(R.id.tv_name)

        val trtstuDataDaompha = ObjectAnimator.ofFloat(
            artstuDataDaomame, View.ALPHA, 0F, 1F
        ).apply {
            duration = 2000L
        }

        val scrtstuDataDaomleX = ObjectAnimator.ofFloat(
            parrtstuDataDaomnt, View.SCALE_X, 0.98F, 1F
        ).apply {
            duration = 1000L
        }
        val scalertstuDataDaomY = ObjectAnimator.ofFloat(
            parrtstuDataDaomnt, View.SCALE_Y, 0.98F, 1F
        ).apply {
            duration = 1000L
        }
        val rotrtstuDataDaomheel = ObjectAnimator.ofFloat(
            parrtstuDataDaomnt, View.ROTATION, 0F, 2160F
        ).apply {
            duration = 1000L
        }

        AnimatorSet().apply {
            play(scrtstuDataDaomleX).with(scalertstuDataDaomY).with(rotrtstuDataDaomheel).with(trtstuDataDaompha)
        }.apply {
            doOnEnd {
                navigartstuDataDaominActivity()
            }
            start()
        }
    }

    private fun navigartstuDataDaominActivity() {
        Intent(this, MainActivity::class.java).run {
            startActivity(this)
            finish()
        }
    }
}