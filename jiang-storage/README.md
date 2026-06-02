# jiang-storage

## Module Role

`jiang-storage` provides local storage abstractions.

Package:

```text
com.jj.storage
```

## Current Capabilities

- `JiangStorage`: storage module initialization entry.
- `JiangStorageConfig`: storage config.
- `JiangKeyValueStore`: key-value storage interface.
- `MmkvKeyValueStore`: MMKV implementation.

## Usage

Initialize:

```kotlin
JiangStorage.init(
    application = this,
    config = JiangStorageConfig(
        defaultKvId = "sample_default",
        debug = BuildConfig.DEBUG
    )
)
```

Read and write:

```kotlin
JiangStorage.kv.putString("token", token)
val token = JiangStorage.kv.getString("token")
JiangStorage.kv.remove("token")
JiangStorage.kv.clear()
```

## Boundaries

`jiang-storage` does not handle:

- Business account logic.
- Business DTO serialization policies.
- Network caching.
- Database relationships.

## Dependencies

```kotlin
implementation(project(":jiang-common"))
implementation(project(":jiang-core"))
implementation(libs.mmkv)
```
