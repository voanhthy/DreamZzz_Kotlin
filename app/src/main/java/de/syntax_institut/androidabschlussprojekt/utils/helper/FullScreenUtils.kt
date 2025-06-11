package de.syntax_institut.androidabschlussprojekt.utils.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

// FullScreen nur für NightSkyScreen
@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("WrongConstant")
fun Activity.enableFullscreen(hideNavigationBar: Boolean) {
    WindowCompat.setDecorFitsSystemWindows(window, false)

    WindowInsetsControllerCompat(window, window.decorView).apply {
        hide(WindowInsets.Type.statusBars())    // Statusbar immer versteckt

        if (hideNavigationBar) {
            hide(WindowInsets.Type.navigationBars())
        } else {
            show(WindowInsets.Type.navigationBars())
        }

        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}