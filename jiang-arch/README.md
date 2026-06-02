# jiang-arch

## Module Role

`jiang-arch` provides MVVM architecture helpers for Android pages.

Package:

```text
com.jj.arch
```

## Current Capabilities

- `JiangActivity`.
- `JiangFragment`.
- `JiangVmActivity`.
- `JiangVmFragment`.
- `JiangStateVmActivity`.
- `JiangViewModel`.
- `JiangUiState`.
- ViewBinding base support.
- ViewModel creation support.
- Coroutine launch helpers in `JiangViewModel`.
- `JiangResult` handling in `JiangViewModel`.
- `JiangLoadingMode`: none, page, dialog.
- `JiangEvent`: one-time event wrapper.
- `JiangUiEvent`: common one-time UI event model.

## Current Gap

The module can support normal pages, multiple requests, dialog loading, and scan-code workflows.

Recommended next:

- Result helper extensions.
- A scan-code demo that shows focus next field and select-all-on-error.

## Boundaries

`jiang-arch` should not define:

- Business APIs.
- Business DTOs.
- Backend response formats.
- Retrofit creation logic.
- Storage implementation details.

## Dependencies

```kotlin
api(project(":jiang-common"))
implementation(project(":jiang-core"))
```
