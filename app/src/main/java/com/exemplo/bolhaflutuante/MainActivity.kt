package com.exemplo.bolhaflutuante

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
            setBackgroundColor(Color.WHITE)
        }
        val title = TextView(this).apply {
            text = "Bolha flutuante"
            textSize = 24f
            gravity = Gravity.CENTER
            setTextColor(Color.BLACK)
        }
        val detail = TextView(this).apply {
            text = "Ative a permissão de sobreposição e depois inicie a bolha. Ela continua visível ao sair do aplicativo."
            textSize = 16f
            gravity = Gravity.CENTER
            setTextColor(Color.DKGRAY)
            setPadding(0, 24, 0, 24)
        }
        val start = Button(this).apply {
            text = "Ativar bolha"
            setOnClickListener { startBubble() }
        }
        val stop = Button(this).apply {
            text = "Desativar bolha"
            setOnClickListener { stopService(Intent(this@MainActivity, OverlayService::class.java)) }
        }
        root.addView(title)
        root.addView(detail)
        root.addView(start)
        root.addView(stop)
        setContentView(root)
    }

    private fun startBubble() {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
            return
        }
        startForegroundService(Intent(this, OverlayService::class.java))
    }
}
