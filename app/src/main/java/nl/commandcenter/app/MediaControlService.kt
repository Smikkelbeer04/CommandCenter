package nl.commandcenter.app

import android.content.Context
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.content.ComponentName

class MediaControlService : NotificationListenerService() {
    companion object {
        fun sendCommand(context: Context, action: String) {
            val i = Intent(context, MediaControlService::class.java).setAction(action)
            context.startService(i)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action ?: return START_NOT_STICKY
        try {
            val manager = getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager
val sessions = manager.getActiveSessions(
    ComponentName(this, MediaControlService::class.java)
)
            val spotify = sessions.firstOrNull { it.packageName == "com.spotify.music" }
                ?: sessions.firstOrNull()
            when (action) {
                "playpause" -> spotify?.let {
                    val state = it.playbackState?.state
                    if (state == PlaybackState.STATE_PLAYING) it.transportControls.pause()
                    else it.transportControls.play()
                }
                "next" -> spotify?.transportControls?.skipToNext()
                "previous" -> spotify?.transportControls?.skipToPrevious()
            }
        } catch (_: Exception) { }
        stopSelf(startId)
        return START_NOT_STICKY
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) { }
}
