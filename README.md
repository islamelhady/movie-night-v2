<h1 align="center">🎬 Movie Night | Modern Android Application</h1>
<p align="center">
A production-inspired Android movie discovery application built with Kotlin using
<b>Multi-Module Clean Architecture</b>, <b>MVI</b>, and modern Android development practices.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green.svg?style=for-the-badge&logo=android" />
  <img src="https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Architecture-Clean%20Architecture-orange?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Pattern-MVI-red?style=for-the-badge" />
  <img src="https://img.shields.io/badge/DI-Hilt-yellow?style=for-the-badge&logo=dagger" />
</p>

---

# Overview

**Movie Night** is a modern Android application that allows users to discover, search, and explore movies and TV shows using **The Movie Database (TMDB)** API.

The application follows **Google's recommended Android architecture** using **Clean Architecture**, **MVI**, **Repository Pattern**, and **Multi-Module Architecture** to achieve scalability, maintainability, and testability.

# Architecture

- **Multi-Module Architecture** — separates features and shared components into independent Gradle modules.
- **Clean Architecture** — organizes the project into Presentation, Domain, and Data layers.
- **MVI (Model-View-Intent)** — provides predictable UI state management with unidirectional data flow.
- **Repository Pattern** — abstracts data sources behind a single access point.
- **Dependency Injection (Hilt)** — manages dependencies across modules.
- **Offline-first caching** — uses Room to cache data and improve the offline experience.

---

#  Features

###  Movies & TV Shows
- Browse Trending, Popular, Top Rated, and Upcoming content.
- Discover Movies by Category and Genres.
- Integrated YouTube player for trailers.

###  Search
- Predictive search for Movies, TV Shows, and Actors.
- Search suggestions and history.

###  Movie Details
- Comprehensive information: Ratings, Cast, Crew, and Reviews.
- Similar movies and personalized recommendations.

###  User Features
- Secure Authentication and Profile management.
- Personalized Watchlist and Favorites.
- Detailed Watch History.

###  UI & UX
- **Material Design 3** implementation.
- Full support for **Light and Dark themes**.
- Smooth animations and responsive layouts.

---

#  Project Structure

```text
movie-night
│
├── app                 # Application entry point
│
├── core
│   ├── common          # Shared utilities and extensions
│   ├── data            # Repository implementations
│   ├── database        # Room database
│   ├── datastore       # DataStore preferences
│   ├── domain          # UseCases & repository interfaces
│   ├── network         # Retrofit & API services
│   └── ui              # Shared UI components
│
├── feature
│   ├── auth            # Authentication
│   ├── home            # Home screen
│   ├── search          # Search
│   ├── details         # Movie details
│   ├── explore         # Discover content
│   ├── watchlist       # Watchlist
│   ├── player          # Trailer player
│   ├── profile         # User profile
│   ├── tvshow          # TV Shows
│   └── showmore        # Show more content
│
└── build-logic         # Convention plugins & Gradle configuration

```

## MVI (Model-View-Intent)

MVI ensures a unidirectional data flow, making the UI state predictable and easier to debug.

```mermaid
flowchart TD

User([User])

Intent[Intent]

VM[ViewModel]

Reducer[Reducer]

State[UI State]

UI[UI]

User --> Intent
Intent --> VM
VM --> Reducer
Reducer --> State
State --> UI
UI --> User
```

---

# Tech Stack

- Kotlin
- XML
- Clean Architecture
- Multi-Module
- MVI
- Coroutines
- Flow
- StateFlow
- Paging 3
- Room
- Retrofit
- OkHttp
- Hilt
- Navigation Component
- DataStore
- Coil

---

# Light and Dark Theme

The app supports both **Light** and **Dark** themes, which can be toggled based on system preferences.

## Light Theme
|                                                        |                                                       |                                                     |
|:------------------------------------------------------:|:-----------------------------------------------------:|:---------------------------------------------------:|
| <img src="https://i.imgur.com/I300etz.jpg" width="250">  | <img src="https://imgur.com/eFNvTOL.jpg" width="250"> | <img src="https://imgur.com/wd9wTBy.jpg" width="250">   
| <img src="https://i.imgur.com/MrjJ3iF.jpg" width="250">  | <img src="https://imgur.com/OB3voHY.jpg" width="250"> | <img src="https://imgur.com/cKgotDS.jpg" width="250"> 
| <img src="https://imgur.com/LLEgoPr.jpg" width="250">  | <img src="https://imgur.com/XJQTOyx.jpg" width="250"> | <img src="https://imgur.com/aOgwh56.jpg" width="250"> 
| <img src="https://imgur.com/DeNpsd2.jpg" width="250">  | <img src="https://imgur.com/QmtAm5V.jpg" width="250"> | <img src="https://i.imgur.com/zgz9Epj.jpg" width="250">

## Dark Theme

|                                                        |                                                       |                                                     |
|:------------------------------------------------------:|:-----------------------------------------------------:|:---------------------------------------------------:|
| <img src="https://imgur.com/2vv8ded.jpg" width="250">  | <img src="https://imgur.com/8H6AAPD.jpg" width="250">  | <img src="https://imgur.com/qvKkqxR.jpg" width="250">
| <img src="https://imgur.com/WTbgV6a.jpg" width="250">  | <img src="https://imgur.com/WVngEtv.jpg" width="250"> | <img src="https://imgur.com/BpIrKDd.jpg" width="250"> 
| <img src="https://imgur.com/hhVs90b.jpg" width="250">  | <img src="https://imgur.com/mVhEejx.jpg" width="250"> | <img src="https://imgur.com/Kbm2rDX.jpg" width="250">
| <img src="https://imgur.com/3RO4LOQ.jpg" width="250">  | <img src="https://imgur.com/FY923cv.jpg" width="250"> | <img src="https://imgur.com/q9HpB50.jpg" width="250"> 
---

#  Getting Started

## Prerequisites
- Android Studio Koala+
- JDK 17+

## Setup
1. **Clone the repository**:
   ```bash
   git clone https://github.com/islamelhady/movie-night-v2.git
   ```

2. **Configure TMDB API**:
   Create a `local.properties` file in the root directory and add:
   ```properties
   API_KEY=YOUR_API_KEY
   BASE_URL=https://api.themoviedb.org/3/
   IMAGE_BASE_PATH=https://image.tmdb.org/t/p/
   TMDB_SIGNUP_URL=https://www.themoviedb.org/signup
   ```
   *Obtain your key at [developer.themoviedb.org](https://developer.themoviedb.org/)*

3. **Build & Run**:
   Open in Android Studio, sync Gradle, and press **Run**.

---

#  Author

**Islam Elhady**
Android Developer

- **GitHub**: [islamelhady](https://github.com/islamelhady)
- **LinkedIn**: [islam-elhady](https://www.linkedin.com/in/islam-elhady/)

---

#  Support
If you found this project helpful, consider giving it a ⭐ on GitHub!

