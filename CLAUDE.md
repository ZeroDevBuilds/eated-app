# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew installDebug           # Build and install on connected device/emulator
./gradlew test                   # Run unit tests
./gradlew connectedAndroidTest   # Run instrumentation tests (requires device/emulator)
```

## Architecture

**Eated** is a single-module Android app for tracking restaurant and dish ratings. Built with Kotlin, Jetpack Compose, and Material3.

**Stack:** Compose UI, Room database, Navigation Compose, Kotlin Coroutines/Flow, MVVM pattern.

**Key layers:**
- `data/local/` — Room database (`EatedDatabase`), DAOs, and entities. `RestaurantEntity` and `DishEntity` with cascade delete on the foreign key.
- `data/repository/RestaurantRepository` — Single repository abstracting all database operations.
- `data/backup/` — JSON export/import via `BackupManager`.
- `ui/screen/` — Each screen has its own package with a Composable screen and ViewModel. ViewModels use `StateFlow`.
- `ui/components/` — Shared composables: `RatingBadge`, `RatingSelector`, `SentimentIcon`.
- `ui/navigation/EatedNavigation.kt` — All nav routes and graph definition.

**DI:** Manual dependency injection through `EatedApplication`, which instantiates the database and repository. ViewModels receive the repository via `ViewModelProvider.Factory`.

**Rating system:** 1-10 nullable scale (`Int?`) with color-coded tiers — green (≥8), orange (≥5), red (<5), gray (unrated). Both restaurants and dishes can be saved without a rating (defaults to `null`). `RatingSelector` has a "—" button to clear the selection. Unrated items show a shrug emoji and "Not rated" badge.

**Restaurant flair:** Optional short note on each restaurant, stored as `flair` column (default empty string). Displayed in italics with curly quotes under the restaurant name in the list.

**Timestamps:** Restaurants have `createdAt` and `modifiedAt` (epoch millis). Set automatically on insert/update. Included in backup JSON with fallback to current time for older backups.

**Sorting:** Main list supports sort by Recently Modified (default), Date Added, Rating, and A–Z via `FilterChip` row. Sort state lives in `RestaurantListViewModel.sortOption`.

**Room DB version:** 4. Migration 1→2 adds flair column. Migration 2→3 recreates both tables to make rating columns nullable. Migration 3→4 adds createdAt/modifiedAt columns.

## Build Configuration

- Kotlin DSL build scripts with version catalog (`gradle/libs.versions.toml`)
- Compile/Target SDK 36, Min SDK 24
- KSP for Room annotation processing
- Java 11 source/target compatibility
