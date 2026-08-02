# Kotlin Tutor

An Android app for learning Kotlin, structured around the [roadmap.sh Kotlin roadmap](docs/kotlin.pdf).

## What it does

- Browse the full Kotlin roadmap as an interactive checklist
- Read lessons and take quizzes for core language topics
- Track completion progress per topic, stored locally on-device

## Tech stack

- Kotlin + Jetpack Compose (Material 3)
- MVVM (Compose screens -> ViewModels -> Repositories)
- Room for local progress/quiz-score persistence
- Navigation-Compose
- No backend, no login — fully offline, single-user

## Content scope (v1)

Full lessons + quizzes ship for the core language-fundamentals topics (val/var,
data types, control flow, functions, collections basics, classes & objects
basics, null safety). The roadmap screen shows every node from the source
roadmap as a checklist regardless — topics without authored content yet can
still be marked done manually.

See [docs/GUIDE.md](docs/GUIDE.md) for the full architecture, data model, and
build/testing details.

## Status

Early development — project scaffolding not yet created. See the
implementation plan/checklist for current progress.

## Setup

Once scaffolded: open in Android Studio, JDK 17+, standard Gradle sync and
run. (Details will be filled in once the project is scaffolded.)
