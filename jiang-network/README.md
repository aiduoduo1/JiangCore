# jiang-network

## Module Role

`jiang-network` provides Retrofit, OkHttp, Gson, header handling, safe API calls, network exception mapping, and file download capabilities.

The goal is to support new-project API development with a small, maintainable network foundation. It should not copy old third-party network framework structure.

Package:

```text
com.jj.network
```

## Current Capabilities

- `JiangNetwork`: initialization and API creation entry.
- `JiangNetworkConfig`: base URL, timeout, static headers, dynamic headers, debug config, logging level, sensitive headers, and unauthorized hook.
- `OkHttpFactory`.
- `HeaderInterceptor`.
- `RetrofitFactory`.
- `JiangApiCaller.safeApiCall`.
- `NetworkExceptionMapper`.
- `JiangApiException`.
- File download package `com.jj.network.download`:
  - `JiangDownloadConfig`.
  - `JiangDownloadApi`.
  - `JiangFileDownloader`.
  - `JiangDownloadResult`.
  - `JiangDownloadProgress`.
  - `JiangApkDownloader`.

## Dynamic Headers

Use `dynamicHeadersProvider` when headers must be read fresh for every request, such as login token headers:

```kotlin
JiangNetwork.init(
    JiangNetworkConfig(
        baseUrl = "https://example.com/",
        dynamicHeadersProvider = {
            mapOf(
                "MES-UP-TOKEN" to tokenProvider().orEmpty(),
                "User-Agent" to "Android"
            )
        }
    )
)
```

Static `headers` and `dynamicHeadersProvider()` are merged in `HeaderInterceptor`. Dynamic headers win when the same name appears in both maps.

File downloads also read `MES-UP-TOKEN` from the same merged header source, so token refresh behavior stays consistent between normal requests and download requests.

If one API should not carry token, add the internal request header:

```kotlin
@Headers("No-Token: true")
@POST("/ca/auth/login")
suspend fun login(@Body request: LoginRequest): MesBaseResult<String>
```

`HeaderInterceptor` removes `No-Token` before the request is sent.

## Logging

Use `JiangNetworkConfig.logLevel` to control OkHttp request logging:

```kotlin
JiangNetworkConfig(
    baseUrl = "https://example.com/",
    logLevel = JiangNetworkLogLevel.BASIC
)
```

Available levels are `NONE`, `BASIC`, `HEADERS`, and `BODY`.

Sensitive headers are redacted by default:

```text
MES-UP-TOKEN
Authorization
password
pwd
```

Override `sensitiveHeaders` in `JiangNetworkConfig` if a project needs a different list.

## Unauthorized Hook

Use `unauthorizedHandler` to observe HTTP `401` centrally:

```kotlin
JiangNetworkConfig(
    baseUrl = "https://example.com/",
    unauthorizedHandler = { exception ->
        // Clear token or notify app navigation layer.
    }
)
```

The framework only reports the event. Clearing login state and jumping pages stay in app/business code.

## Business APIs

Business Retrofit interfaces belong in app/business modules:

```kotlin
interface LoginApi {
    @POST("/ca/auth/login")
    suspend fun login(@Body request: LoginRequest): MesBaseResult<String>
}
```

Create them through:

```kotlin
val api = JiangNetwork.createApi(LoginApi::class.java)
```

`jiang-network` must not hardcode one backend `BaseResult` structure.

Business response conversion should stay in app/business modules. The app sample provides an extension example:

```kotlin
fun <T> MesBaseResult<T>.toJiangResult(): JiangResult<T>
```

This keeps the framework independent from any single backend response format.

## File Download

`JiangFileDownloader` supports:

- Downloading normal files.
- Saving to a specified `File`.
- Progress callback.
- Parent directory creation.
- Writing to `.tmp` first.
- Renaming to the final file after success.
- Deleting `.tmp` after failure.
- Mapping failures to `JiangApiException`.
- Rethrowing coroutine cancellation instead of converting it to a network error.

`JiangApkDownloader` validates that the target file uses an `.apk` suffix.

Not implemented:

- Breakpoint resume.
- Multi-task download queue.
- Background notification download.
- APK installation.

## Boundaries

`jiang-network` does not handle:

- Activity or Fragment.
- Page loading or dialogs.
- Business login flow.
- Version checking rules.
- APK installation.

## Roadmap

Recommended next:

- Add app-level repository helpers if repeated business response conversion grows.

Later, only when real new projects need them:

- Multi-base-url support.
- File upload.
- Request de-duplication or keyed cancellation.
- Cache strategy.
- SSE or WebSocket helpers.

## Dependencies

```kotlin
implementation(project(":jiang-common"))
implementation(project(":jiang-core"))
api(libs.retrofit.core)
implementation(libs.retrofit.converter.gson)
implementation(libs.okhttp.core)
implementation(libs.okhttp.logging)
implementation(libs.gson)
```
