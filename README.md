# Command Center — Android project

A dark, landscape-first smart-home dashboard prototype for Samsung Galaxy Tab A SM-T510.

## Included
- Native Android volume slider for the tablet's media stream.
- Shortcut to Spotify.
- Optional Android media-session controls (previous/play-pause/next), which require enabling the app under Android Notification Access settings.
- Shortcut to Android Bluetooth settings.
- Premium dark dashboard UI.

## Important integration status
- LSC Smart Connect controls are placeholders, not live controls. To control the lights, an actual supported integration is required (for example a compatible Home Assistant setup or supported Tuya/LSC API access). Do not enter credentials into an untrusted service.
- Spotify transport controls may require Spotify to be open/playing and the Android notification/media access permission to be enabled. This project does not include a Spotify developer OAuth integration or display current track metadata.
- Tablet media volume works through Android. Separate speaker volume depends on the Bluetooth speaker's capabilities.

## Build
Open this folder in Android Studio (current stable) and let Gradle sync. Build > Build Bundle(s) / APK(s) > Build APK(s). Minimum Android version is 8.0 (API 26). The tablet's Android version should be checked in Settings > About tablet > Software information.

This project was generated as source code; it is not a precompiled APK. A compatible Android SDK/Gradle build environment is needed to produce the APK.
