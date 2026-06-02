# VERSION

Current documented status: `v0.3.0`

The original released baseline is `v0.1.0`. The current workspace already includes additional `v0.2.x` and `v0.3.x` capabilities that have not yet been formally released.

## Product Direction

JiangCore is intended to become a self-owned Android foundation framework for future new projects.

It is not intended to migrate old projects directly. Old projects only provide experience references for common patterns:

- Login and token handling.
- Business API calls.
- Scan-code workflows.
- Multi-request page orchestration.
- Loading, toast, and dialog lifecycle.
- File download and APK update guidance.
- Future extension points such as upload, printing, SSE, WebSocket, or multi-base-url.

The framework should grow from real needs, with small focused iterations instead of copying large third-party framework designs.

## v0.1.0

Baseline framework version.

Completed:

- Android multi-module library structure.
- `com.jj.xxx` package namespace.
- ViewBinding-based Activity and Fragment base classes.
- ViewModel base class and coroutine helpers.
- `JiangUiState` page state model.
- Toolbar and StateLayout support.
- Retrofit, OkHttp, and Gson network base.
- Network safe API call and exception mapping.
- MMKV key-value storage.
- Showcase sample app.

## v0.2.0-dev

Page development and common UI enhancement stage.

Implemented in current workspace:

- `JToast`.
- `JLoadingDialog`.
- `JConfirmDialog`.
- `JBaseDialog`.
- `JInputDialog`.
- `JScanInputDialog`.
- `JSingleChoiceDialog`.
- `JMultiChoiceDialog`.
- `JListDialog`.
- `clickNoRepeat`.
- UI demo page.
- UI demo coverage for input, scan input, single-choice, multi-choice, and list dialogs.
- Network dynamic headers through `JiangNetworkConfig.dynamicHeadersProvider`.
- Login API usage demo in app.
- App-owned business response conversion example through `MesBaseResult<T>.toJiangResult()`.

Follow-up work moved into `v0.2.1-dev`.

## v0.2.1-dev

Page development ergonomics cleanup stage.

Implemented in current workspace:

- `JiangLoadingMode`: none, page, dialog.
- `JiangErrorMode`: page, toast, none.
- Base Activity loading/error dispatch cleanup.
- `JiangEvent` and `JiangUiEvent` for one-time UI events.
- Common `observeText()` helper in `JiangToolbarStateVmActivity`.
- `NetworkDemoActivity` simplified to validate the new page API.
- Lifecycle-aware dialog show/dismiss behavior.
- Toast repeated-message suppression.
- Click debounce based on elapsed realtime.
- Scan-code business page demo with sequential requests, focus jump, select-all-on-error, and dialog loading.

Still recommended:

- Add small result helper extensions if repeated `JiangResult` handling keeps growing.

## v0.3.0

Network and system capability enhancement stage.

Implemented in current workspace:

- `jiang-network.download.JiangDownloadConfig`.
- `jiang-network.download.JiangDownloadApi`.
- `jiang-network.download.JiangFileDownloader`.
- `jiang-network.download.JiangDownloadResult`.
- `jiang-network.download.JiangDownloadProgress`.
- `jiang-network.download.JiangApkDownloader`.
- `jiang-network.exception.JiangApiException`.
- `jiang-core.apk.JiangApkInstaller`.
- `jiang-core.apk.JiangApkInstallResult`.
- `jiang-core.apk.JiangApkInstallExt`.
- `jiang-core.permission.JiangPermission`.
- `jiang-core.permission.JiangPermissionChecker`.
- `jiang-core.permission.JiangPermissionResult`.
- `jiang-core.permission.JiangPermissionExt`.
- `jiang-core.permission.JiangPermissionScene`.
- `jiang-core.permission.JiangPermissionFlowResult`.
- `jiang-core.system.JiangVibrator`.
- App manifest `REQUEST_INSTALL_PACKAGES`.
- App manifest baseline permissions for network, camera, location, bluetooth, media/photos, file storage, and vibration.
- App FileProvider config with `${applicationId}.jiang.fileprovider`.
- `res/xml/jiang_file_paths.xml`.
- APK update demo page.
- Permission demo page.
- Permission business-flow demo page.

Notes:

- Version checking, forced update rules, update dialogs, and business download URL composition remain app/business responsibilities.
- `jiang-network` only downloads files. It does not install APKs.
- `jiang-core` only guides APK install through the system installer. It does not perform silent, root, or MDM install.
- Permission copy, business timing, and forced permission flow remain app/business responsibilities.
- Permission flow demo shows how app/business pages should request permission only when a user action needs it, block the business action on denial, and guide to app settings after permanent denial.

## Network Roadmap

Current network layer is foundation-ready for new project API development:

- Retrofit service creation.
- OkHttp client creation.
- Gson converter.
- Static and dynamic headers.
- Safe API call wrapper.
- Network exception mapping.
- File and APK download.
- Download token reading from the merged static and dynamic header source.
- Configurable request logging levels through `JiangNetworkLogLevel`.
- Sensitive request log header redaction.
- Coroutine cancellation passthrough in API calls and downloads.
- HTTP `401` unauthorized hook, with navigation still owned by app/business code.
- Per-request `No-Token` internal header support.
- `NETWORK_UNAVAILABLE` error code for host/network unavailable mapping.

Completed network polish:

- Keep business response adaptation in app/business modules, with simple examples such as `MesBaseResult<T>.toJiangResult()`.
- Make download token handling consistent with dynamic request headers.
- Add configurable request logging levels for API integration debugging.

Recommended next network work:

- Add app-level repository helpers if repeated business response conversion grows.

Later, add upload, multi-base-url, request de-duplication, cache, SSE, or WebSocket only when a real new project needs them.
