# jiang-common

## Module Role

`jiang-common` provides small framework-wide primitives that can be used by other JiangCore modules.

Package:

```text
com.jj.common
```

## Current Capabilities

- `JiangResult`: success, error, loading, and empty result model.
- `JiangException`: framework exception model with code, message, and source throwable.
- `JiangErrorCode`: common error codes.
- `JiangLog`: lightweight log wrapper.

## Boundaries

`jiang-common` must stay lightweight.

It should not depend on:

- Android UI.
- Retrofit or OkHttp.
- MMKV or DataStore.
- Business DTOs or business APIs.

## Dependencies

No JiangCore module dependency is required.
