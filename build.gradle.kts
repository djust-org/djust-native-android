// djust-native-android — Native Android (Jetpack Compose) client for djust LiveView.
//
// LVN-IV PR-1 (djust-org/djust#1580): Gradle scaffold. The actual
// transport / msgpack decoder / patch applicator / widget composables
// land in subsequent PRs against this repo. See README for the full
// PR sequence and ADR-019 in djust-org/djust for architectural context.

plugins {
    id("com.android.library") version "8.5.0" apply false
    kotlin("android") version "2.0.0" apply false
}

// Subprojects / module config will land in PR-2 once the WS transport
// + msgpack decoder dependencies are settled. Likely:
//   - OkHttp WebSocket (com.squareup.okhttp3:okhttp)
//   - org.msgpack:msgpack-core (or jackson-dataformat-msgpack)
//   - androidx.compose.runtime / androidx.compose.foundation
