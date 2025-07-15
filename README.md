# HiResCarPlayer

**HiResCarPlayer** is a high-fidelity local music player for Android Auto, offering true 24-bit playback of FLAC and other lossless formats. Designed for audiophiles, it delivers untouched audio quality, seamless Android Auto integration, and a beautiful, dynamic interface derived from your own album art.

---

## 🚀 Features

### 🎵 Local File Playback
- **Supported formats:** FLAC, WAV, ALAC (24-bit, untouched audio)
- Playback follows exact file order from the folder (sorted by name or native order)
- Uses Android MediaStore or DocumentFile API for filesystem access

### 🏆 Maximum Audio Quality
- Built around ExoPlayer, configured for 24-bit playback via `DefaultAudioSink` with `PCM_FLOAT` output
- Displays audio quality and channel information in Android Auto

### 🚘 Android Auto Support
- Full Android Auto integration using `MediaBrowserServiceCompat`
- Seamless media controls via `MediaSessionCompat` and MediaStyle notification

### 🖼️ Advanced Android Auto UI
- Displays track title, artist, and album art (full-quality PNG/JPG/bitmap)
- Button colors and backgrounds dynamically styled from album cover using the Palette API
- Blurred album art as background using `RenderEffect.createBlurEffect`

### 🔄 Advanced Crossfade
- Configurable crossfade duration (e.g., 3–6 seconds) with beat-matching
- Implemented via ExoPlayer's `AudioProcessorChain` for smooth transitions

### 🔇 Skip Silences
- Real-time silence skipping using `SilenceSkippingAudioProcessor`
- Optionally supports offline analysis with FFmpeg or ReplayGain silence tags

### ⏭️ Proactive Preloading
- Uses `ConcatenatingMediaSource` to preload the next track for seamless playback

---

## 🏗️ Project Structure

```
HiResCarPlayer/
├── MainActivity.kt               # Folder selection UI
├── MusicService.kt               # Android Auto MediaBrowserService
├── MusicPlayer.kt                # ExoPlayer + playback logic
├── FileScanner.kt                # Scans local files
├── MetadataExtractor.kt          # Extracts FLAC metadata, album art, etc.
├── AudioProcessors.kt            # Crossfade, SkipSilence
├── models/
│   └── MusicTrack.kt             # Music track data class
├── utils/
│   └── ImageUtils.kt             # Blur + Palette color utilities
└── res/
    ├── layout/
    │   └── activity_main.xml     # Folder selection layout
    └── drawable/
        └── ic_launcher.png
```

---

## 📱 Mobile App

- Simple Material 3 expressive UI
- "Select music folder" button (using SAF / ACTION_OPEN_DOCUMENT_TREE)
- Selected folder path stored via SharedPreferences

---

## 🚘 Android Auto

- Service: `MusicService` (extends `MediaBrowserServiceCompat`)
- Creates a `MediaSessionCompat` with `MediaMetadataCompat`
- Uses MediaStyle for full Android Auto controls
- UI to choose folders to play, with art cover display

---

## 🧠 Advanced Audio Features

- **Crossfade & Gapless:**  
  ExoPlayer with custom `AudioProcessor` for smooth transitions.
- **Silence Skipping:**  
  Real-time skipping with `SilenceSkippingAudioProcessor`.
- **Preloading:**  
  Next track buffered via `ConcatenatingMediaSource`.

---

## 🎨 Dynamic UI Styling

- Button colors generated from album art (via Palette API)
- Blurred album art as UI background (via RenderEffect)

---

## 🛡️ Permissions

```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
<!-- For Android 13+ -->
<uses-permission android:name="android.permission.READ_MEDIA_AUDIO"/>
```
- Uses Storage Access Framework (SAF) for storage access

---

## 🧪 Recommended Tests

- Test multiple 24-bit FLAC albums (with/without embedded album art)
- Validate track order matches folder order
- Test on various Android Auto head-units (wired & wireless)
- Confirm silence skipping and smooth crossfades

---

## 📦 Build & Compatibility

- **Minimum SDK:** 26 (Android 8.0 Oreo)
- **Android Auto:** Compatible
- **Audio Engine:** ExoPlayer v2/v3 + AndroidX Media3
- **24-bit Playback:** Verified via `PCM_FLOAT_OUTPUT`

---

## 📄 License

[MIT](LICENSE) © 2025 HiResCarPlayer contributors

---

## 🙌 Contributions

Pull requests, bug reports, and feature suggestions are welcome!
