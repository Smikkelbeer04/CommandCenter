package nl.commandcenter.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.provider.Settings
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager

class MainActivity : Activity() {
    private lateinit var web: WebView
    private lateinit var audio: AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        audio = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        window.decorView.systemUiVisibility = (android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                or android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        web = WebView(this)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.webViewClient = WebViewClient()
        web.webChromeClient = WebChromeClient()
        web.addJavascriptInterface(Bridge(), "Android")
        setContentView(web)
        web.loadUrl("file:///android_asset/index.html")
    }

    inner class Bridge {
        @JavascriptInterface fun volume(): Int {
            val max = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC).coerceAtLeast(1)
            return (audio.getStreamVolume(AudioManager.STREAM_MUSIC) * 100) / max
        }
        @JavascriptInterface fun setVolume(percent: Int) {
            val max = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            val value = (percent.coerceIn(0, 100) * max) / 100
            audio.setStreamVolume(AudioManager.STREAM_MUSIC, value, 0)
        }
        @JavascriptInterface fun launchSpotify() {
            val launch = packageManager.getLaunchIntentForPackage("com.spotify.music")
            if (launch != null) startActivity(launch)
            else startActivity(Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://open.spotify.com")))
        }
        @JavascriptInterface fun openBluetoothSettings() {
            startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
        }
        @JavascriptInterface fun openMediaAccessSettings() {
            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
        }
        @JavascriptInterface fun media(action: String) {
            MediaControlService.sendCommand(this@MainActivity, action)
        }
        @JavascriptInterface fun lightsSetup() {
            startActivity(Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://www.lsc-smartconnect.com/")))
        }
    }
}
