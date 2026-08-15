# NewsFeed

NewsFeed is a native Android application for browsing, searching, reading, and saving current news articles. It retrieves headlines from [NewsData.io](https://newsdata.io/), opens articles in an in-app WebView, and stores saved articles locally for later access.

The project uses a single-activity architecture with MVVM, use cases, repository abstractions, Hilt dependency injection, and separate remote and local data sources.

## Features

- Browse current US news headlines
- Search for articles by topic
- Read articles without leaving the app
- Save articles locally with Room
- Browse a locally persisted saved-article list
- Swipe to delete saved articles
- Undo accidental deletions
- Edge-to-edge layout support
- Lifecycle-aware UI and network state handling

## Demo

https://github.com/user-attachments/assets/ca9ee03b-8354-4e79-baed-fab3dde3e925

## Tech stack

| Area | Technology |
| --- | --- |
| Language | Kotlin |
| UI | Android Views, Material Components, View Binding |
| Architecture | MVVM, use cases, repository pattern |
| Dependency injection | Dagger Hilt |
| Networking | Retrofit, OkHttp, Gson |
| Image loading | Glide |
| Local storage | Room |
| Asynchronous work | Kotlin Coroutines and Flow |
| State | ViewModel and LiveData |
| Navigation | AndroidX Navigation and Safe Args |
| Build tooling | Gradle 9, AGP 9, KSP |

## Architecture

| Layer | Responsibility |
| --- | --- |
| `presentation` | Activities, fragments, adapters, navigation models, ViewModels, and dependency-injection modules |
| `domain` | Repository contracts and application use cases |
| `data` | API models, Room database access, data sources, and repository implementations |

Data flows from the presentation layer through domain use cases and repository contracts. Repository implementations select the NewsData.io API or Room database as the appropriate source.

## Requirements

- Android Studio with JDK 17 support
- Android SDK 37
- A [NewsData.io API key](https://newsdata.io/register)
- Android 8.0 (API 26) or newer for running the app

## Getting started

1. Clone the repository:

   ```bash
   git clone https://github.com/GetRighhttt/NewsFeed.git
   cd NewsFeed
   ```

2. Add your NewsData.io key to the untracked `local.properties` file:

   ```properties
   NEWS_DATA_API_KEY=your_newsdata_api_key
   ```

   Android Studio normally creates this file with your local SDK path. Keep that existing value and add the API-key entry on a new line. As an alternative, provide `NEWS_DATA_API_KEY` as an environment variable.

3. Open the project in Android Studio and sync Gradle.

4. Run the `app` configuration on an emulator or physical device.

> [!IMPORTANT]
> Never commit `local.properties` or a real API key. If a key is exposed, revoke it and generate a replacement through NewsData.io.

## Build and verification

Run the following commands from the repository root:

```bash
# Compile and package the debug APK
./gradlew assembleDebug

# Run local unit tests
./gradlew test

# Run Android lint
./gradlew lintDebug

# Build the minified, unsigned release APK
./gradlew assembleRelease
```

Generated APKs are written under `app/build/outputs/apk/`.

## Project structure

```text
app/src/main/java/com/example/newsfeed/
├── data/           # API, database, models, and repository implementations
├── domain/         # Repository contracts and use cases
├── presentation/   # Application, DI, UI, navigation models, and ViewModel
└── util/           # Shared platform utilities
```

## Contributing

Contributions are welcome. Before opening a pull request:

1. Create a focused branch for the change.
2. Add or update tests where appropriate.
3. Run `./gradlew test lintDebug assembleDebug`.
4. Describe the behavior change and verification performed in the pull request.

## Contact

Questions and feedback can be sent to [stefanbusiness95@gmail.com](mailto:stefanbusiness95@gmail.com).
