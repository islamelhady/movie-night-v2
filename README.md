<h1 align="center">Movie Night</h1>
This movie android app has been developed with features that allow users to browse movies and series, actors and their works, search for movies, series or actors, add reviews and comments on movies and series, create custom lists such as watch later or favourites, and manage their account through a login and signup.

## Architecture
**Movie Night** is based on the recommended architecture and the Repository pattern, from Google with dependency inversion which follows [Google's official architecture guidance](https://developer.android.com/topic/architecture).


## Features
- <b>Login and signup:</b> Users can create an account and log in to the app to access their lists and preferences across multiple devices.
- <b>Browse movies and series:</b> Users can browse the app to discover new movies and series that are categorized as new, latest, popular, top-rated, or by genre.
- <b>Browse actors and their works:</b> Users can browse actors' profiles and their past work, such as movies and series.
- <b>Search for movies, series or actors:</b> Users can search for specific movies, series, or actors using a search bar with predictive search suggestions.
- <b>Review and comment:</b> Users can add reviews and comments to movies and series, sharing their opinions and thoughts about the content.
- <b>Create custom lists:</b> Users can create custom lists to keep track of the movies and series they want to watch, such as a watch later list or a list of favourites.
- <b>Watch trailers:</b> Users can watch trailers of movies and series to get a glimpse of the content before watching it.

## Tech stack
- [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) to separate the UI from the business logic, which can improve code quality and maintainability.
- [Data Store](https://developer.android.com/jetpack/androidx/releases/datastore) to store data locally, which can improve performance and offline support.
- [Room](https://developer.android.com/jetpack/androidx/releases/room) to persist data to a database, which can improve performance and data security.
- [Hilt dependency injection](https://developer.android.com/training/dependency-injection/hilt-android) to manage dependencies, which can help to improve code quality and maintainability.
- [Paging library](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) to load data in pages, which can improve performance and user experience.
- [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation) to navigate between screens, which can improve user experience.
- [Coroutines](https://developer.android.com/kotlin/coroutines) to write asynchronous code, which can improve performance and responsiveness.
- [Data Binding](https://developer.android.com/codelabs/android-databinding#0) to bind data to views, which can improve code quality and maintainability.
- [Live Data](https://developer.android.com/topic/libraries/architecture/livedata) to observe data changes, which can improve code quality and responsiveness.



