# 🎬 Movie Listing App

This is a movie listing application that displays the best movies of 2024 using **TheMovieDB API**. The app follows the **MVVM architecture** and supports **offline favorites**.

---

## 📌 Features
- Fetches a list of the **best movies of 2024** from TheMovieDB API.
- Displays **movie posters, names, ratings, and release dates** in the movie list.
- Allows users to **add/remove movies from favorites**, with data stored offline.
- Clicking on a movie opens a **detailed page** with additional information:
  - Overview
  - Vote Average
  - Original Language
  - Favorite status (syncs with the main list)

---

## 🏗️ Tech Stack
- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Networking**: Retrofit + Gson
- **Offline Storage**: Room Database
- **UI**: XML (Jetpack components)
- **Dependency Injection**: Hilt
  
---

## 🔧 Installation & Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/YOUR_GITHUB_USERNAME/MovieListingApp.git
