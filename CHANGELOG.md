# CHANGELOG

## v0.3.0 - 2026-06-02

### Added

- Added file download support in `jiang-network.download`.
- Added APK download helper in `JiangApkDownloader`.
- Added temporary `.tmp` download flow with success rename and failure cleanup.
- Added download progress callback model.
- Added `JiangApiException` for download failure normalization.
- Added APK install guidance in `jiang-core.apk`.
- Added APK file validation, unknown app install permission check, settings jump, FileProvider URI creation, and system installer launch.
- Added app FileProvider manifest config and `jiang_file_paths.xml`.
- Added APK update demo page in app.
- Added `JiangNetworkLogLevel` and configurable `JiangNetworkConfig.logLevel`.
- Added app-owned `MesBaseResult<T>.toJiangResult()` business response conversion example.
- Added `JiangNetworkConfig.unauthorizedHandler` for HTTP `401` notification.
- Added internal `No-Token` header support for per-request token opt-out.
- Added default sensitive header redaction for network logging.
- Added `NETWORK_UNAVAILABLE` error code.
- Added runtime permission helpers in `jiang-core.permission`.
- Added permission scene and flow result models in `jiang-core.permission`.
- Added vibration helper in `jiang-core.system.JiangVibrator`.
- Added app baseline permissions for network, camera, location, bluetooth, media/photos, file storage, and vibration.
- Added permission demo page in app.
- Added permission business-flow demo page in app.

### Changed

- Download requests now read `MES-UP-TOKEN` from merged static and dynamic network headers.
- API calls and downloads now rethrow coroutine cancellation instead of converting it to a network error.
- `UnknownHostException` now maps to `NETWORK_UNAVAILABLE`.

### Notes

- No breakpoint resume, download queue, or notification download is implemented.
- `jiang-network` does not install APKs.
- `jiang-core` does not do silent install, root install, or MDM install.
- Version checking and update dialogs remain app/business logic.

## Unreleased - v0.2.0-dev

### Added

- Added `JToast`.
- Added `JLoadingDialog`.
- Added `JConfirmDialog`.
- Added lifecycle-aware `JBaseDialog`.
- Added `JInputDialog`.
- Added `JScanInputDialog`.
- Added `JSingleChoiceDialog`.
- Added `JMultiChoiceDialog`.
- Added `JListDialog`.
- Added click debounce extension.
- Added UI demo page.
- Added dynamic network headers through `JiangNetworkConfig.dynamicHeadersProvider`.
- Added login API usage demo in app with `LoginApi`, `LoginRepository`, `LoginRequest`, and `MesBaseResult`.

### Changed

- Exposed Retrofit core from `jiang-network` with `api(libs.retrofit.core)` so app/business modules can declare Retrofit interfaces while keeping dependency version ownership in `jiang-network`.
- Dialogs now bind to lifecycle-aware show/dismiss behavior.
- Fixed default UI dialog/state text.
- `JToast` now cancels the previous toast and supports repeated-message suppression.
- `clickNoRepeat` now uses elapsed realtime instead of wall-clock time.

### Notes

- Page development still has boilerplate in observers and UI state handling.
- Loading and error presentation need mode-based cleanup in the next step.

## Unreleased - v0.2.1-dev

### Added

- Added `JiangLoadingMode` with `NONE`, `PAGE`, and `DIALOG`.
- Added `JiangErrorMode` with `PAGE`, `TOAST`, and `NONE`.
- Added `JiangEvent` for one-time event delivery.
- Added `JiangUiEvent` for common one-time UI events.
- Added `observeText()` helper in `JiangToolbarStateVmActivity`.
- Added dialog loading support through `JiangViewModel.launch()` and `launchResult()`.
- Added scan-code business page demo for sequential requests, focus jump, select-all-on-error, and dialog loading.

### Changed

- `JiangUiState.Loading` now carries loading mode and optional message.
- `JiangToolbarStateVmActivity` now dispatches page loading, dialog loading, and toast errors through overridable modes.
- Simplified `NetworkDemoActivity` by removing repeated observer, success, error, and retry overrides.

### Notes

- Existing default behavior remains page loading and page error.
- Scan-code specific focus/select-all events are still intended to stay in app/business page event models.

## v0.1.0 - 2026-05-29

### Added

- Created Android multi-module framework structure.
- Added modules: `app`, `jiang-common`, `jiang-core`, `jiang-arch`, `jiang-network`, `jiang-storage`, and `jiang-ui`.
- Added `JiangCore` and `JiangConfig`.
- Added common result, exception, error code, and log classes.
- Added Activity, Fragment, ViewModel, and state base classes.
- Added toolbar and state layout support.
- Added Retrofit, OkHttp, Gson, request header interceptor, API caller, and network exception mapping.
- Added storage initialization and key-value abstraction.
- Added showcase sample pages for toolbar, state layout, network, and storage.

### Constraints

- Package namespace is `com.jj.xxx`.
- ViewBinding is used.
- DataBinding is not used.
- Gradle, AGP, Kotlin, SDK, and dependency versions are not upgraded by framework feature work.
