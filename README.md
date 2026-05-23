# djust-native-android

Native Android (Jetpack Compose) client for
[djust LiveView](https://github.com/djust-org/djust).

**Status**: Scaffold only — implementation tracked in
[djust-org/djust#1580 (LVN-IV)](https://github.com/djust-org/djust/issues/1580).
This repo currently contains `LICENSE` + this README; the Gradle
`build.gradle.kts`, Kotlin source, tests, and CI all land via that
issue's PR sequence.

## What this is

djust ships a server-side reactive framework (Django + Rust VDOM). The
default client is HTML in a browser — `client.js` consumes a stream of
VDOM patches over WebSocket and applies them to the DOM. This repo will
ship a parallel client: subscribe to the same WebSocket, consume the
same patch stream, render true Jetpack Compose widgets instead of HTML.

The pattern is borrowed from
[Phoenix LiveView Native](https://github.com/liveview-native/live_view_native).
djust's specific design lives in
[ADR-019: LiveView Native](https://github.com/djust-org/djust/blob/main/docs/adr/019-liveview-native.md).

## Eventual public API

```kotlin
import org.djust.native.DjustLiveView

@Composable
fun ContentScreen() {
    DjustLiveView(url = "ws://127.0.0.1:8111/ws/live/")
}
```

`DjustLiveView` is a `@Composable` that opens the WebSocket, decodes
msgpack patches, and maintains a `MutableState`-backed widget tree
indexed by the same base62 `djust_id` the browser client uses.

## Companion repos

| Repo | Purpose |
| - | - |
| [`djust-org/djust`](https://github.com/djust-org/djust) | The framework. Server-side reactive Python + Rust VDOM. |
| [`djust-org/djust-native-ios`](https://github.com/djust-org/djust-native-ios) | iOS equivalent (SwiftUI). Same protocol, different platform. |
| [`djust-org/djust-mobile-toga`](https://github.com/djust-org/djust-mobile-toga) | WebView mode — reuse web templates verbatim inside a Toga `WebView`. The "easy mode" alongside this repo's "polish mode." |

## License

[MIT](LICENSE) — matches djust.
