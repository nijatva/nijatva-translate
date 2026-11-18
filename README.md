<div align="center">

<img src="icons/icon.svg" alt="Nijatva Translate" width="120" />

# Nijatva Translate

**A calm translator for India's languages — bring your own engine.**

Kotlin · Jetpack Compose · Material 3 · `com.nijatva.translate`

Part of the [Nijatva suite](https://github.com/nijatva/nijatva-suite).

</div>

---

## What it is

Nijatva Translate is an Android client for translation engines you control. The UI
covers all 22 scheduled languages of India plus a set of world languages, and it
talks to any LibreTranslate-compatible endpoint — a local Docker container, a
self-hosted IndicTrans2 service, or your own Bhashini gateway.

Nothing is hard-wired to a vendor: the endpoint and API key live in **Engine
settings**, so the same APK works against a laptop on your LAN or a server you run
in production.

## Features

- **22 scheduled Indian languages** with native-script names, in a searchable picker.
- **Source auto-detection**, one-tap language swap (it swaps the text too).
- **Engine-agnostic**: any LibreTranslate-compatible `POST /translate` endpoint.
- **Speak the result** with the platform text-to-speech engine, in the target language.
- **Translate selected text** — `ACTION_PROCESS_TEXT` puts "Nijatva Translate" in every
  app's text-selection menu, and `ACTION_SEND` puts it in the share sheet.
- **The suite's Appearance and Developer screens**, from `nijatva-core`; the accent
  picked there tints this app's chrome.
- No Google Play Services, no Firebase, no analytics, no bundled keys.

## Suite rules this app follows

| Rule | Where it shows |
| --- | --- |
| `minSdk 26`, adaptive icon only, no PNG densities | `app/build.gradle.kts`, `res/mipmap-anydpi-v26` |
| One icon geometry: 96 units, 24-unit radius, 5-unit white stroke | `icons/icon.svg`, mirrored from [nijatva-design](https://github.com/nijatva/nijatva-design/tree/main/icons/translate) |
| Every app carries Appearance + Developer | `com.nijatva:signature`, Settings → Nijatva |
| No GMS, no Firebase, no sibling app dependency | `in.nijatva.no-gms-check` in `check` and CI |
| Apps talk over intents, never artifacts | `ACTION_SEND`, `ACTION_PROCESS_TEXT`; no Nijatva dependency |
| Hindi complete for every key | `res/values-hi/strings.xml` |

## Quick start

The build needs two checkouts side by side, because the suite guard is an
included build until it is published:

```bash
git clone https://github.com/nijatva/no-gms-check.git ../no-gms-check
git clone https://github.com/nijatva/nijatva-translate.git
cd nijatva-translate
```

Then open the folder in Android Studio (Ladybug or newer), or:

```bash
gradle wrapper --gradle-version 8.9   # once, if gradlew is missing
./gradlew assembleDebug
```

Requirements: JDK 17, Android SDK 35, `minSdk` 26. The Gradle wrapper JAR is not
committed, so run `gradle wrapper` once before using `./gradlew`.

**The signature kit** comes from GitHub Packages, so the build needs
`GITHUB_ACTOR` and `GITHUB_TOKEN` (with `read:packages`) in the environment. For a
local loop without a token:

```bash
git clone https://github.com/nijatva/nijatva-core.git ../nijatva-core
cd ../nijatva-core && ./gradlew :signature:publishToMavenLocal
```

`mavenLocal()` is already in `settings.gradle.kts` as the fallback.

## Point it at an engine

The app starts with the public LibreTranslate endpoint. For anything serious, run
your own:

```bash
docker run -ti --rm -p 5000:5000 libretranslate/libretranslate
```

Then open **Engine settings**:

| Field | Value |
| --- | --- |
| Base URL | `http://10.0.2.2:5000` from the emulator, `http://<your-lan-ip>:5000` from a device |
| API key | leave blank unless your server requires one |

Plain HTTP is refused on Android 9+ unless the host is allowlisted. For a LAN
server, add a `network_security_config.xml` that permits that one host — see
[`docs/ENGINES.md`](docs/ENGINES.md) for the wider list of backends
(IndicTrans2, Bhashini, bharatvoiceai) and how to wrap them in this contract.

## Project layout

```
app/src/main/java/com/nijatva/translate/
├── MainActivity.kt                 entry point, edge-to-edge, share and selection intake
├── data/
│   ├── Language.kt                 the 22 scheduled languages + world set, search
│   ├── LibreTranslateEngine.kt     the engine interface + the reference implementation
│   ├── SettingsStore.kt            DataStore-backed engine URL and key
│   └── TranslationRepository.kt    single entry point for translation
└── ui/
    ├── NijatvaTranslateApp.kt      screen switch (translate ↔ settings)
    ├── TranslateScreen.kt          language bar, input, result card, picker sheet
    ├── SettingsScreen.kt           engine configuration + the Nijatva rows
    ├── TranslateViewModel.kt       state, swap logic, request lifecycle
    └── theme/                      colours, typography, accent from the kit
```

Tests live in `app/src/test/` and cover the language catalogue and search.

## Icon

A white outline speech bubble on the suite's indigo tile: 96-unit canvas, 24-unit
corner radius, 5-unit stroke, round caps and joins. The source of truth is
`icons/translate/` in [nijatva-design](https://github.com/nijatva/nijatva-design);
this repository carries the four generated resources plus `icon.svg` as a mirror.
There are no PNG densities — adaptive icons arrive with API 26, which is the
minimum.

## Status

Version 1.0.0 is a working text translator: language selection, translation,
copy/share/listen, engine configuration, and both suite screens. Speech input,
camera OCR, offline models and conversation mode are the natural next steps.

## Licence

Apache-2.0. See [LICENSE](LICENSE).
