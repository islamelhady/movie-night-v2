<h1 align="center">Movie Night | Modern Android Application</h1>

<p align="center">
A production-inspired Android application for discovering movies and TV shows, built with
<b>Kotlin</b>, <b>Clean Architecture</b>, <b>MVVM + UDF</b>,
and <b>Multi-Module Architecture</b>.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green.svg?style=for-the-badge&logo=android" />
  <img src="https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Architecture-Clean%20Architecture-orange?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Pattern-MVVM%20%2B%20UDF-blue?style=for-the-badge" />
  <img src="https://img.shields.io/badge/DI-Hilt-yellow?style=for-the-badge&logo=dagger" />
</p>

---

##  Overview

**Movie Night** is an Android application for discovering, searching, and exploring movies and TV shows using **The Movie Database (TMDB) API**.

The project is built with a focus on scalable and maintainable Android development using:

* Clean Architecture
* MVVM + Unidirectional Data Flow
* Multi-Module Architecture
* Repository Pattern
* Dependency Injection
* Reactive state management
* Local data caching

The project is organized mainly by feature, with shared core modules that provide common infrastructure and functionality.

---

##  Features

###  Movies & TV Shows

* Browse Trending, Popular, Top Rated, and Upcoming content
* Discover movies by categories and genres
* Explore TV shows
* Watch movie and TV show trailers

###  Search

* Search for Movies, TV Shows, and Actors
* Search suggestions
* Search history

###  Movie & TV Details

* Ratings
* Cast and Crew
* Reviews
* Recommendations
* Trailers

###  User Features

* Authentication
* Profile management
* Watchlist
* Favorites
* Watch history

###  UI & UX

* Material Design 3
* Light and Dark themes
* Responsive layouts
* Smooth animations
* Loading and error states

---

##  Architecture

Movie Night follows:

**MVVM + UDF + Clean Architecture + Multi-Module Architecture**

### Data Flow

```text
UI
 ↓
ViewModel
 ↓
UseCase
 ↓
Repository
 ↓
Data Sources
 ├── Remote
 └── Local
```

The UI observes state exposed by the `ViewModel` and sends user actions back to it.

The application follows a unidirectional flow:

```text
User Action
    ↓
ViewModel
    ↓
UseCase
    ↓
Repository
    ↓
Data Sources
    ↓
Repository
    ↓
ViewModel
    ↓
UI State
    ↓
UI
```

### Architecture Principles

* Separation of concerns
* Dependency inversion
* Unidirectional data flow
* Immutable UI state
* Feature isolation
* Composition over unnecessary inheritance
* Clear dependency direction
* Testable business logic

---

## Multi-Module Architecture

The project is organized mainly by feature with shared core modules.

```text
movie-night
│
├── app
│
├── core
│   ├── common
│   ├── data
│   ├── database
│   ├── datastore
│   ├── domain
│   ├── network
│   └── ui
│
├── feature
│   ├── auth
│   ├── home
│   ├── search
│   ├── details
│   ├── explore
│   ├── watchlist
│   ├── profile
│   ├── tvshow
│   └── showmore
│
└── build-logic
```

### Core Modules

| Module           | Responsibility                                     |
| ---------------- | -------------------------------------------------- |
| `core:common`    | Shared utilities and extensions                    |
| `core:data`      | Repository implementations and data coordination   |
| `core:database`  | Room database and local persistence                |
| `core:datastore` | DataStore preferences                              |
| `core:domain`    | Business logic, UseCases, and repository contracts |
| `core:network`   | Retrofit, OkHttp, and API services                 |
| `core:ui`        | Shared UI components and resources                 |

### Feature Modules

Each feature is isolated into its own module:

* `feature:auth`
* `feature:home`
* `feature:search`
* `feature:details`
* `feature:explore`
* `feature:watchlist`
* `feature:profile`
* `feature:tvshow`
* `feature:showmore`

This structure helps reduce coupling and makes the project easier to maintain and scale.

---

##  UI State Management

The presentation layer follows **MVVM + UDF**.

Persistent screen state is represented using immutable `UiState` models and exposed through `StateFlow`.

One-time events such as navigation, messages, and transient UI effects are handled separately when needed.

Example:

```kotlin
data class ExploreUiState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val isGridLayout: Boolean = false
)
```

The UI does not directly control business logic.

Instead:

```text
UI
 ↓ user action
ViewModel
 ↓
UseCase
 ↓
Repository
```

This keeps the UI layer simple and predictable.

---

##  Tech Stack

### Language

* Kotlin

### Android

* Android SDK
* XML
* Material 3
* Navigation Component
* ViewModel

### Architecture

* MVVM
* Unidirectional Data Flow
* Clean Architecture
* Repository Pattern
* Multi-Module Architecture

### Asynchronous Programming

* Kotlin Coroutines
* Flow
* StateFlow
* SharedFlow

### Networking

* Retrofit
* OkHttp

### Local Storage

* Room
* DataStore

### Dependency Injection

* Hilt

### Other Libraries

* Paging 3
* Glide

### API

* TMDB API

---

##  Screenshots

###  Light Theme

|                                                         |                                                       |                                                         |
| :-----------------------------------------------------: | :---------------------------------------------------: | :-----------------------------------------------------: |
| <img src="https://i.imgur.com/I300etz.jpg" width="250"> | <img src="https://imgur.com/eFNvTOL.jpg" width="250"> |  <img src="https://imgur.com/wd9wTBy.jpg" width="250">  |
| <img src="https://i.imgur.com/MrjJ3iF.jpg" width="250"> | <img src="https://imgur.com/OB3voHY.jpg" width="250"> |  <img src="https://imgur.com/cKgotDS.jpg" width="250">  |
|  <img src="https://imgur.com/LLEgoPr.jpg" width="250">  | <img src="https://imgur.com/XJQTOyx.jpg" width="250"> |  <img src="https://imgur.com/aOgwh56.jpg" width="250">  |
|  <img src="https://imgur.com/DeNpsd2.jpg" width="250">  | <img src="https://imgur.com/QmtAm5V.jpg" width="250"> | <img src="https://i.imgur.com/zgz9Epj.jpg" width="250"> |

###  Dark Theme

|                                                       |                                                       |                                                       |
| :---------------------------------------------------: | :---------------------------------------------------: | :---------------------------------------------------: |
| <img src="https://imgur.com/2vv8ded.jpg" width="250"> | <img src="https://imgur.com/8H6AAPD.jpg" width="250"> | <img src="https://imgur.com/qvKkqxR.jpg" width="250"> |
| <img src="https://imgur.com/WTbgV6a.jpg" width="250"> | <img src="https://imgur.com/WVngEtv.jpg" width="250"> | <img src="https://imgur.com/BpIrKDd.jpg" width="250"> |
| <img src="https://imgur.com/hhVs90b.jpg" width="250"> | <img src="https://imgur.com/mVhEejx.jpg" width="250"> | <img src="https://imgur.com/Kbm2rDX.jpg" width="250"> |
| <img src="https://imgur.com/3RO4LOQ.jpg" width="250"> | <img src="https://imgur.com/FY923cv.jpg" width="250"> | <img src="https://imgur.com/q9HpB50.jpg" width="250"> |

---

##  Getting Started

### Prerequisites

* Android Studio Koala or newer
* JDK 17+
* TMDB API key

## Setup

### 1. Clone the repository

```bash
git clone https://github.com/islamelhady/movie-night-v2.git
```

### 2. Configure TMDB

Create a `local.properties` file in the project root:

```properties
API_KEY=YOUR_API_KEY
BASE_URL=https://api.themoviedb.org/3/
IMAGE_BASE_PATH=https://image.tmdb.org/t/p/
TMDB_SIGNUP_URL=https://www.themoviedb.org/signup
```

Get your API key from the TMDB developer portal.

### 3. Build and Run

Open the project in Android Studio, sync Gradle, and run the application.

---

##  Author

**Islam Elhady**

Android Developer specializing in Kotlin and modern Android Development.

* LinkedIn: [islam elhady](https://www.linkedin.com/in/islamelhady)

---

##  Support

If you find this project useful or interesting, consider giving it a ⭐ on GitHub.

---

## License

This project is developed for educational and portfolio purposes.

