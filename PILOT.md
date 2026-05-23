# MAX Companion pilot integration (LVN-IV PR-7)

Pilot screen for `djust-native-android` v0.1: re-implement MAX
Companion's `HomeView` (`djust-mobile-poc/medicare/views.py`) as a
native variant. Mirror of `djust-org/djust-native-ios/PILOT.md`.

## Server-side setup

Add a `home.compose.html` variant alongside the existing `home.html`
in `djust-mobile-poc/medicare/templates/medicare/`:

```html
{# medicare/home.compose.html #}
<Stack spacing="12" padding="16">
    <Text font="title">Hello, {{ first_name }}</Text>

    {% if show_alert %}
    <Stack padding="12" foregroundColor="#14457E">
        <Text font="headline">Your screening is due</Text>
        <Text>{{ screening }}</Text>
        <Button dj-tap="dismiss_alert">Dismiss</Button>
    </Stack>
    {% endif %}

    <List>
        {% for tile in tiles %}
        <Stack>
            <Text font="headline">{{ tile.label }}</Text>
            <Text>{{ tile.stat }}</Text>
        </Stack>
        {% endfor %}
    </List>
</Stack>
```

The `.compose.html` and `.swiftui.html` variants are intentionally
allowed to share content (same widget tags) — divergence happens only
when platform UX differs in a way the constrained styling vocabulary
can't express.

## Client-side wiring

Replace the existing `WebView` in the Android app with `DjustLiveView`:

```kotlin
class MaxCompanionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DjustLiveView(url = "ws://127.0.0.1:8111/ws/live/")
        }
    }
}
```

## Acceptance criteria

Pinned by the LVN-IV tracking issue (djust#1580):

- [ ] MAX home screen renders visually equivalent on Android emulator
      vs WebView
- [ ] `dismiss_alert` event round-trips through the unchanged Python
      `HomeView.dismiss_alert` handler
- [ ] `BeneficiaryPreferences` write-back survives a relaunch
- [ ] Bundle-size delta documented (native-only vs WebView+Toga
      build of `app-debug.apk`)

## Known gaps blocking full end-to-end

This PR ships the recipe + integration shape. End-to-end requires:

1. **msgpack decoder** (PR-2's `WebSocketClient.frames()` throws
   `DjustWSException` today). Either `org.msgpack:msgpack-core` or
   `jackson-dataformat-msgpack`.
2. **PatchApplicator per-op mutations** (PR-3 dispatches; per-op
   mutations need the Compose `MutableState` binding extensions).
3. **Native template variants** in max-companion (`home.compose.html`
   above is the recipe).
4. **Bundle-size measurement** documented in the LVN-IV tracking issue
   when measured.

Each gap is its own focused follow-up. The structural seams, type
contracts, and integration shape are all in place.
