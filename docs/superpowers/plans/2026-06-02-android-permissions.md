# Android Permissions Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add JiangCore baseline Android permission declarations, runtime permission helpers, and an app demo page.

**Architecture:** `jiang-core` owns reusable permission/system helpers. `app` owns Manifest declarations and demo UI. Business pages decide when to request permissions and what copy to show.

**Tech Stack:** Android Manifest, Kotlin, AndroidX ActivityResult APIs already available through app dependencies, ViewBinding, JUnit.

---

### Task 1: Core Permission Groups

**Files:**
- Create: `jiang-core/src/main/java/com/jj/core/permission/JiangPermission.kt`
- Test: `jiang-core/src/test/java/com/jj/core/permission/JiangPermissionTest.kt`

- [ ] Write failing tests for Android-version-aware camera, location, bluetooth, media, storage, and vibrate permission groups.
- [ ] Run `.\gradlew.bat :jiang-core:testDebugUnitTest --tests com.jj.core.permission.JiangPermissionTest` and verify red.
- [ ] Implement `JiangPermission` constants and group functions.
- [ ] Re-run the test and verify green.

### Task 2: Core Permission Runtime Helpers

**Files:**
- Create: `jiang-core/src/main/java/com/jj/core/permission/JiangPermissionResult.kt`
- Create: `jiang-core/src/main/java/com/jj/core/permission/JiangPermissionChecker.kt`
- Create: `jiang-core/src/main/java/com/jj/core/permission/JiangPermissionExt.kt`

- [ ] Implement permission check, denied list, should-show-rationale list, and app settings intent helpers.
- [ ] Keep request UI and business copy outside `jiang-core`.

### Task 3: Vibration Helper

**Files:**
- Create: `jiang-core/src/main/java/com/jj/core/system/JiangVibrator.kt`

- [ ] Add a small vibration helper with API-level-safe `VibrationEffect` usage.
- [ ] Do not request permissions here; `VIBRATE` is Manifest-only.

### Task 4: App Manifest And Demo

**Files:**
- Modify: `app/src/main/AndroidManifest.xml`
- Create: `app/src/main/java/com/jj/sample/showcase/PermissionDemoActivity.kt`
- Create: `app/src/main/res/layout/activity_permission_demo.xml`
- Modify: `app/src/main/java/com/jj/sample/showcase/ShowcaseActivity.kt`
- Modify: `app/src/main/res/layout/activity_showcase.xml`

- [ ] Declare baseline permissions for network, camera, location, bluetooth, media/photos, file storage, install packages, and vibration.
- [ ] Add a ViewBinding demo page using ActivityResult `RequestMultiplePermissions`.
- [ ] Demo camera, location, bluetooth, photo/video, storage, settings jump, and vibration.
- [ ] Add a Showcase entry.

### Task 5: Docs And Verification

**Files:**
- Modify: `CODEX.md`
- Modify: `VERSION.md`
- Modify: `CHANGELOG.md`
- Modify: `jiang-core/README.md`
- Modify: `app/README.md`

- [ ] Document permission boundaries and demo usage.
- [ ] Run `.\gradlew.bat :jiang-core:testDebugUnitTest`.
- [ ] Run `.\gradlew.bat assembleDebug`.
