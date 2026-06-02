# jiang-ui

## Module Role

`jiang-ui` provides reusable UI helpers and page base classes that combine toolbar, state layout, dialogs, and toast.

Package:

```text
com.jj.ui
```

## Current Capabilities

- Toolbar:
  - `JiangToolbar`.
  - `JiangToolbarActivity`.
  - `JiangToolbarVmActivity`.
  - `JiangToolbarStateVmActivity`.
- Page state layout:
  - `JiangStateLayout`.
  - `JiangStateViewConfig`.
- Dialogs:
  - `JBaseDialog`.
  - `JLoadingDialog`.
  - `JConfirmDialog`.
  - `JInputDialog`.
  - `JScanInputDialog`.
  - `JSingleChoiceDialog`.
  - `JMultiChoiceDialog`.
  - `JListDialog`.
- Toast:
  - `JToast`.
- View helpers:
  - `clickNoRepeat`.
- Page ergonomics:
  - `JiangErrorMode`.
  - `observeText()`.

## Current Behavior

`JiangToolbarStateVmActivity` observes `JiangViewModel.uiState` and dispatches:

- `Idle` -> content page.
- `Loading(PAGE)` -> loading page.
- `Loading(DIALOG)` -> loading dialog.
- `Loading(NONE)` -> no loading UI.
- `Success` -> content page.
- `Error` -> page error, toast, or no UI according to `errorMode()`.
- `Empty` -> empty page.

It also dismisses the loading dialog in `onDestroy`.

Dialogs are lifecycle-aware. `JBaseDialog`, `JLoadingDialog`, and the business dialog helpers can bind to a `LifecycleOwner`, dismiss automatically on destroy, and avoid showing when the Activity is no longer alive.

`JToast` keeps an application context, cancels the previous toast before showing a new one, and supports `showOnce()` for repeated-message suppression.

## Current Gap

The current base Activity is now lighter for common pages. Remaining gaps:

- Add richer one-time UI event examples for scan-code pages.
- Add optional retry action helpers if repeated retry code grows.
- Add custom `JiangStateLayout` view replacement if project-specific empty/error pages become common.

## Boundaries

`jiang-ui` should not contain:

- Business UI pages.
- Business network calls.
- Business DTOs.
- Project-specific visual design rules.

## Dependencies

```kotlin
implementation(project(":jiang-common"))
implementation(project(":jiang-core"))
implementation(project(":jiang-arch"))
implementation(libs.androidx.appcompat)
implementation(libs.android.material)
```
