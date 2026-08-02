# Kotlin Tutor

An Android app for learning Kotlin, structured around the [roadmap.sh Kotlin roadmap](docs/kotlin.pdf)
and a fully-authored companion curriculum ([docs/kotlin-tutor-content.md](docs/kotlin-tutor-content.md)).

## What it does

- Browse the full Kotlin roadmap as an interactive checklist
- Read lessons (recap, explanation, key points) and take quizzes per topic
- Track completion progress per topic, stored locally on-device
- **AI Tutor**: chat with an AI tutor per topic in one of four modes —
  Explain, Quiz me, Review my code, Give an exercise — powered by DeepSeek
  (`deepseek-v4-pro`) using your own API key

## Tech stack

- Kotlin + Jetpack Compose (Material 3)
- MVVM (Compose screens -> ViewModels -> Repositories)
- Room for local progress/quiz-score persistence
- Ktor Client + kotlinx.serialization for the DeepSeek chat API (the app's
  only network dependency)
- Navigation-Compose
- No accounts, no backend server — the AI Tutor calls DeepSeek directly using
  a key you supply in Settings; everything else is fully offline

## Content scope

Full lessons + quizzes ship for 42 core-language topics (`docs/kotlin-tutor-content.md`),
covering language basics through coroutines and packaging. The roadmap screen
shows every node from the source roadmap as a checklist regardless — topics
without authored content can still be marked done manually.

See [docs/GUIDE.md](docs/GUIDE.md) for the full architecture, data model, AI
Tutor design, and build/testing details.

## Status

Built and verified on-device: roadmap, lessons (all 42 topics), quizzes,
progress tracking, and the AI Tutor (chat UI, all 4 modes, Settings/API-key
flow, and the missing-key error path). Real DeepSeek responses haven't been
smoke-tested against a live key yet — everything up to the network call is
verified; the call itself needs your key to confirm end-to-end.

## Setup

Open in Android Studio (or `./gradlew assembleDebug` / `./gradlew test` from
the CLI), JDK 17+. To use the AI Tutor, add your own DeepSeek API key in the
app's Settings screen — the app never ships with a key and usage is billed
to your DeepSeek account.
