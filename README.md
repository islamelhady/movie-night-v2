<h1 align="center">Movie Night - Clean Architecture & MVI</h1>

**Movie Night** is an Android application developed using **Kotlin** and follows the **MVI (Model-View-Intent)** architectural pattern. This app offers a variety of features for movie enthusiasts, including movie search, ratings, reviews, trailer viewing, and user authentication, providing a seamless user experience. The app makes use of powerful libraries and tools such as **Room**, **Retrofit**, **Dagger Hilt**, **Kotlin Coroutines**, and **Flow**.

This repository contains the source code for the app and its various components, which follow modern Android development practices.

## Features

- **User Authentication**: Handles user sign-in and account-related functionalities.
- **Browse Movies and Series**: Users can explore movies and series categorized by genre, rating, release date, and more.
- **Actor Profiles**: Users can browse actors' profiles and view their past works.
- **Search for Movies, Series, or Actors**: Allows users to search for specific movies, series, or actors with predictive search suggestions.
- **Reviews and Comments**: Users can add and view reviews and comments on movies and series.
- **Create Custom Lists**: Users can create custom lists like "Watch Later" or "Favorites".
- **Watch Trailers**: Users can watch trailers of movies and series before deciding to watch them.
- **Light/Dark Theme**: The app supports both light and dark themes to match user preferences.

## Architecture

The app is based on the recommended architecture and the **Repository pattern**, with **dependency inversion** following **[Google's official architecture guidance](https://developer.android.com/topic/architecture)**. It follows a **Clean Architecture** approach, which separates the app into different layers:

### Architecture Diagram:

![architecture](figure/figure0.gif)

### UI (Presentation)

The **UI layer** handles the app's user interface and interactions. It includes activities, fragments, and XML layouts responsible for displaying information to the user and receiving user input.

### View Model (Presentation)

The **ViewModel layer** connects the UI with the domain layer. It holds the app's UI-related data using **StateFlow** or **SharedFlow** and exposes methods for the UI to interact with. This allows the UI to respond to state changes while maintaining a clean separation between the UI and business logic.

### Use Cases (Domain)

The **Domain layer** contains the business logic of the app. It defines the use cases and operations that the app can perform, such as searching for movies, getting movie details, managing the watchlist, and handling user reviews.

### Repository (Data)

The **Repository layer** acts as an intermediary between the domain layer and the data sources. It fetches data from remote and local sources, such as APIs or databases, and provides the data to the domain layer. This layer abstracts the data sources and ensures that the domain layer doesn't need to know about how the data is retrieved.

### Remote (Data)

The **Remote data source** handles data retrieval from remote servers or APIs. For example, the app fetches movie details, trailers, and reviews from the [TMDB API](https://developers.themoviedb.org/3/getting-started/introduction). It is responsible for making network requests and parsing responses from the server.

### Local (Data)

The **Local data source** manages data storage and retrieval from local databases, such as **Room**. It handles tasks like saving user ratings, caching movie details, storing watch history, and managing favorites. This ensures offline functionality and improves app performance by reducing the number of network requests.

## StateFlow and SharedFlow

In the **Presentation Layer**, **StateFlow** and **SharedFlow** are used to manage the app's state and event flows efficiently. 

- **StateFlow** is used to represent the current state of the UI. It allows us to observe changes in the UI state in a lifecycle-safe manner, ensuring that the UI reflects the most recent data.
- **SharedFlow** is used for one-off events or actions that don't represent the app’s state but are still important, such as navigation events or showing error messages.

### MVI Architecture Diagram

The following diagram illustrates the flow of data in the **MVI** architecture:

![MVI Architecture](https://user-images.githubusercontent.com/60369343/107117861-049d4000-6832-11eb-852d-a2f16f9ab31b.png)

## Tech Stack

- **[Kotlin](https://developer.android.com/kotlin)** The primary programming language used for Android development.
- **[Material 3](https://m3.material.io/foundations/accessible-design/overview)** Used for the latest UI components based on Google’s Material Design guidelines.
- **Jetpack Libraries**
  - **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)** Manages UI-related data lifecycle-consciously.
  - **[Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)** - "Lifecycle-aware components react to lifecycle changes in activities and fragments, helping create cleaner, more maintainable, and efficient code.
  - **[Room](https://developer.android.com/jetpack/androidx/releases/room?gclid=CjwKCAjww8mWBhABEiwAl6-2RXqgw6-tYMdlLGJiAhLnEl9PNim-Sz8lx9P6JCaOD9qfQQojs-4DoRoCPkAQAvD_BwE&gclsrc=aw.ds)** A local database for storing user ratings, movie details, watch history, etc.
  - **[Data Store](https://developer.android.com/jetpack/androidx/releases/datastore)** A key-value store for storing user preferences and settings.
  - **[DataBinding](https://developer.android.com/topic/libraries/data-binding)** -Binds UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
  - **[Navigation](https://developer.android.com/jetpack/androidx/releases/navigation)** Manages navigation between screens within the app.
  - **[Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)**: Efficiently loads and displays large datasets.
- **[Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)** Simplifies background operations and asynchronous tasks.
- **[Kotlin Flow](https://developer.android.com/kotlin/flow)** A reactive stream library to handle data and states in the app.
- **[Retrofit](https://square.github.io/retrofit)** Fetches data from the network and interacts with the [TMDB API](https://developers.themoviedb.org/3/getting-started/introduction).
- **[Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android)** A dependency injection library for managing dependencies in the app.
- **[Logging Interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md)** -  logs HTTP request and response data.
- **[Coil](https://coil-kt.github.io/coil/compose/)** - An image-loading library for Android backed by Kotlin Coroutines.


## Getting Started

Follow these steps to set up and run the **Movie Night V2** app locally in your Android development environment.

### Prerequisites

Before running the app, make sure you have the following installed:

- **Android Studio**: The official IDE for Android development.
- **Kotlin**: The app is built using Kotlin.
- **Gradle**: To manage dependencies.

### Installation

1. Clone the repository to your local machine:

    ```bash
    git clone https://github.com/islamelhady/movie-night-v2.git
    ```

2. Open the project in **Android Studio**.

3. Sync the project with Gradle by clicking **Sync Now** in Android Studio.

4. Configure your **TMDB API Key**:
   - Create an account on [TMDB](https://www.themoviedb.org/) and get an API key.
   - Add the key to your project’s `local.properties` file or as an environment variable.

5. Build and run the app on an emulator or physical device.

### Running the App

Once the project is set up, you can run it directly from Android Studio. Select your target device or emulator and click **Run**.

---

## Light and Dark Theme

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

