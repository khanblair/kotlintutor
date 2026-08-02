# Kotlin Tutor — Developer Guide

Android app that teaches Kotlin, structured around the roadmap.sh Kotlin
curriculum (`docs/kotlin.pdf`). Single user, fully offline, no backend.

## Architecture

- Kotlin + Jetpack Compose (Material 3), single Gradle app module (no
  multi-module split for v1 — the app is small enough that it's premature).
- MVVM: Compose screens -> ViewModels (`StateFlow`) -> Repositories -> local
  sources.
- Room DB for the only genuinely dynamic state: per-topic completion and quiz
  scores.
- Roadmap/lesson/quiz content is plain Kotlin data (sealed classes / data
  objects) checked into the repo — compiler-checked, no JSON parsing, easy to
  diff and review in git.
- Navigation-Compose for screen navigation.
- Manual lightweight DI (a small `AppContainer`), not Hilt — keeps boilerplate
  minimal for an app this size.

Content lives behind repository interfaces so it can move to a multi-module
split or a remote/backend-driven source later without touching UI or
ViewModel code.

## Data model

- `RoadmapNode(id, title, parentId, category, hasContent: Boolean)` — mirrors
  the full tree from the roadmap PDF.
- `Lesson(topicId, title, sections: List<String>)`
- `QuizQuestion(id, topicId, question, options, correctIndex, explanation)`
- `UserProgress` (Room entity): `topicId, isCompleted, lastQuizScore,
  lastAttemptedAt`

## Screens

- **RoadmapScreen** — the entire roadmap tree as an expandable checklist,
  with completion state and overall progress %.
- **LessonScreen** — reading content for a topic, plus "Take Quiz" / "Mark
  Complete" actions.
- **QuizScreen** — multiple-choice flow, score summary, retry.

Backed by `RoadmapRepository`, `ContentRepository`, `ProgressRepository`.

## Content scope for v1

Authoring lessons + quizzes for every node in the roadmap (~90 leaf topics)
isn't realistic for a first version:

- **Full content** ships for core language-fundamentals topics: val/var, data
  types, control flow (if/when/for/while), functions, collections basics,
  classes & objects basics, null safety (~8-10 topics).
- The roadmap screen still shows **every** node from the PDF as a checklist.
  Topics without authored content are tappable but show "Content coming soon
  — mark done manually," so the tracker stays useful for the whole roadmap
  even before every lesson is written.
- Adding more topics later means adding more Kotlin data — no architecture
  change required.

## Error handling

No network involved, so there are few failure modes. Room errors are wrapped
in `Result` and surfaced via a retry snackbar. Missing lesson/quiz content is
a normal UI state ("coming soon"), not an error.

## Testing

- Unit: repositories, quiz scoring logic, ViewModel state transitions (JUnit
  + Turbine for `StateFlow`).
- UI: Compose UI tests for roadmap navigation and the quiz answer flow.

## Explicitly out of scope for v1

- In-app code playground / running Kotlin snippets
- User accounts, cloud sync, multi-device progress
- Remote/server-driven content updates
- iOS/desktop targets (Compose Multiplatform)
