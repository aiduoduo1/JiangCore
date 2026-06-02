# jiang-core

## Module Role

`jiang-core` is the runtime core module for framework initialization, global config, application context access, and Android system-level helpers.

Package:

```text
com.jj.core
```

## Current Capabilities

- `JiangCore`: framework initialization entry.
- `JiangConfig`: global framework config.
- APK install guidance under `com.jj.core.apk`:
  - `JiangApkInstaller`.
  - `JiangApkInstallResult`.
  - `JiangApkInstallExt`.
- Runtime permission helpers under `com.jj.core.permission`:
  - `JiangPermission`.
  - `JiangPermissionChecker`.
  - `JiangPermissionResult`.
  - `JiangPermissionExt`.
  - `JiangPermissionScene`.
  - `JiangPermissionFlowResult`.
- System helpers under `com.jj.core.system`:
  - `JiangVibrator`.

## APK Install Guidance

`JiangApkInstaller` supports:

- Checking whether an APK file exists.
- Checking whether a file suffix is `.apk`.
- Checking whether the current app can request package installs.
- Opening the current app's unknown app install settings page.
- Creating a FileProvider content URI.
- Launching the system APK installer UI.

It does not support:

- Silent install.
- Root install.
- MDM enterprise device management install.

## Boundaries

`jiang-core` should not depend on:

- `jiang-network`.
- `jiang-storage`.
- `jiang-ui`.
- `jiang-arch`.
- Business modules.

APK version checking, forced update rules, and update dialogs are app/business responsibilities.

Permission request timing, permission dialog copy, and page flow after denial are app/business responsibilities.

## Permission Helpers

`JiangPermission` provides Android-version-aware permission groups for camera, location, bluetooth, media/photos, file storage, network, and vibration.

`JiangPermissionChecker` and `JiangPermissionExt` support:

- Checking whether one or more permissions are granted.
- Listing denied permissions.
- Listing permissions that should show rationale.
- Converting ActivityResult permission maps to `JiangPermissionResult`.
- Opening the current app settings page.

`jiang-core` does not show permission dialogs by itself. Activities or business pages should request permissions with ActivityResult APIs.

`JiangPermissionScene` maps common business actions to permission groups. `JiangPermissionFlowResult` folds request results into granted, denied, and permanently denied states so app pages can decide whether to continue, block, or guide users to settings.

## System Helpers

`JiangVibrator` provides a small API-level-safe vibration helper. It expects the app manifest to declare `android.permission.VIBRATE`.

## Dependencies

```kotlin
implementation(project(":jiang-common"))
```
