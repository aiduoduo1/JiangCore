# app

## Module Type

Android application sample module.

## Namespace

```text
com.jj.sample
```

## Role

`app` is only used for JiangCore demos and verification. It should not contain reusable framework capabilities.

The sample module demonstrates how a future new project should use JiangCore. It is not a migration target for old projects.

## Current Demos

- Main entry page.
- Showcase page.
- Toolbar demo.
- StateLayout demo.
- Network demo.
- Login API usage demo.
- Scan-code business page demo for sequential requests, focus jump, select-all-on-error, and dialog loading.
- Storage demo.
- UI demo for Toast, LoadingDialog, ConfirmDialog, and click debounce.
- UI demo also covers input, scan input, single-choice, multi-choice, and list dialogs.
- APK update demo for download and install guidance usage.
- Permission demo for camera, location, bluetooth, photos/videos, file storage, app settings, and vibration.
- Permission flow demo for action-bound permission requests, denial blocking, permanent-denial settings guidance, and business fallback behavior.

## Business API Example

Business Retrofit APIs should be declared in app or business modules, not in `jiang-network`.

Current sample:

```text
com.jj.sample.login.LoginApi
com.jj.sample.login.LoginRepository
com.jj.sample.login.LoginRequest
com.jj.sample.login.MesBaseResult
com.jj.sample.login.MesBaseResultExt
```

The sample shows how normal business Retrofit APIs can be used through:

```kotlin
JiangNetwork.createApi(LoginApi::class.java)
```

Business response conversion is kept in app code through `MesBaseResult<T>.toJiangResult()`, so `jiang-network` does not depend on one backend response shape.

## Notes

- The sample base URL is `https://example.com/`.
- Replace the sample base URL and DTOs when creating a real app.
- Version checking, update dialogs, and forced update rules stay in app/business code.
- Permission request timing, copy, and denial handling stay in app/business code.
- Old project APIs can be used as references for naming and scenarios, but old framework structure should not be copied into this module.
