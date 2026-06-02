# JiangCore Codex Notes

## Project Goal

JiangCore is a self-owned Android multi-module framework for future new projects. It is not a migration tool for old projects.

The old projects are useful only as experience references: they show real patterns that future projects will also need, such as login, token headers, scan-code workflows, multiple request orchestration, dialogs, file download, APK update guidance, printing, and long connections.

JiangCore should absorb those proven patterns into clean framework capabilities, without copying old third-party framework code or old business code.

## Project Positioning

JiangCore provides common runtime, architecture, UI, network, storage, and sample capabilities for new internal Android projects.

## Modules

- `app`: sample and verification module. Keep the module name as `app`.
- `jiang-common`: common result, exception, error code, and logging primitives.
- `jiang-core`: framework initialization, global config, Android system capabilities, and APK install guidance.
- `jiang-arch`: Activity, Fragment, ViewModel, coroutine, and UI state architecture helpers.
- `jiang-network`: Retrofit, OkHttp, Gson, request headers, exception mapping, API calling, file download, and APK download.
- `jiang-storage`: local key-value storage abstraction.
- `jiang-ui`: toolbar, state layout, toast, loading dialog, confirm dialog, and view helpers.

## Naming And Boundaries

- Package names stay under `com.jj.xxx`.
- Do not introduce `com.jiang.xxx`.
- Use ViewBinding. Do not use DataBinding.
- Retrofit, OkHttp, and Gson belong in `jiang-network`.
- MMKV and DataStore belong in `jiang-storage`.
- appcompat and material belong in `jiang-ui` or `app`.
- The `app` module is for demos and verification only.
- Business APIs, DTOs, update rules, dialogs, and page workflows belong in app code, not in framework modules.
- Old project code is reference material only. Do not migrate or copy old framework structure into JiangCore.

## Current Code Status

The codebase has moved beyond the original `v0.1.0` documentation. Current implementation is best described as `v0.3.0`:

- Base framework and module structure are available.
- UI helpers are available: `JToast`, `JLoadingDialog`, `JConfirmDialog`, `clickNoRepeat`.
- Dialog helpers now include lifecycle-aware base, input, scan input, single-choice, multi-choice, and list dialogs.
- Network dynamic headers are available through `JiangNetworkConfig.dynamicHeadersProvider`.
- Network request logging levels are configurable through `JiangNetworkConfig.logLevel`.
- Network logging redacts common token/password headers by default.
- Network calls support a lightweight unauthorized hook and per-request token opt-out.
- Page ergonomics are available through `JiangLoadingMode`, `JiangErrorMode`, `JiangEvent`, `JiangUiEvent`, and `observeText`.
- Login API usage and business response conversion are demonstrated in app with `LoginApi`, `LoginRepository`, `LoginRequest`, and `MesBaseResult<T>.toJiangResult()`.
- File download and APK download are available in `jiang-network.download`.
- APK install guidance is available in `jiang-core.apk`.
- Runtime permission helpers are available in `jiang-core.permission`.
- Permission business-flow scene/result models are available in `jiang-core.permission`.
- Vibration helper is available in `jiang-core.system.JiangVibrator`.
- Demo pages exist for toolbar, state layout, network, storage, UI, APK update, permissions, permission flow, and scan-code workflow.
- Scan-code business page demo exists for sequential requests, focus jump, select-all-on-error, and dialog loading lifecycle.

## Current Pain Point

The framework can now support common business pages, multiple network requests, loading states, dialog loading, one-time UI events, and scan-code style sequential workflows.

For the network layer, the next goal is not to build every advanced feature at once. The practical goal is to make new-project API development easy to write, easy to debug, and easy to extend.

## Recommended Next Step

Network follow-up priorities:

1. Keep adding small app-owned examples for common business response patterns.
2. Add advanced capabilities such as upload, multi-base-url, and request de-duplication only when a real new project needs them.
3. Keep login-expired navigation, update rules, and page-specific request orchestration in app/business code.

## Development Rules

- Do not upgrade Gradle, AGP, Kotlin, SDK, or dependency versions unless explicitly requested.
- Do not rename modules or packages.
- Keep changes scoped to the requested goal.
- Do not revert unrelated local changes.
- Run `assembleDebug` after framework or sample changes.
