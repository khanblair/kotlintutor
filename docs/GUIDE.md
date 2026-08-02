# Kotlin Tutor — Developer Guide

Android app that teaches Kotlin, structured around the roadmap.sh Kotlin
curriculum (`docs/kotlin.pdf`) and a fully-authored companion curriculum
(`docs/kotlin-tutor-content.md`). Single user, no accounts. Core learning
content works fully offline; the AI Tutor feature calls the DeepSeek API.

## Architecture

- Kotlin + Jetpack Compose (Material 3), single Gradle app module (no
  multi-module split for v1 — the app is small enough that it's premature).
- MVVM: Compose screens -> ViewModels (`StateFlow`) -> Repositories -> local
  sources (Room) or remote sources (DeepSeek API via Ktor).
- Room DB for genuinely dynamic local state: per-topic completion and quiz
  scores.
- Roadmap/curriculum content is plain Kotlin data (sealed classes / data
  objects) checked into the repo — compiler-checked, no JSON parsing, easy to
  diff and review in git.
- Ktor Client + kotlinx.serialization for the DeepSeek chat completions API
  (the only network traffic in the app).
- Navigation-Compose for screen navigation.
- Manual lightweight DI (a small `AppContainer`), not Hilt — keeps boilerplate
  minimal for an app this size.

Content lives behind repository interfaces so it can move to a multi-module
split or a remote/backend-driven source later without touching UI or
ViewModel code.

## Data model

- `RoadmapNode(id, title, parentId, category, hasContent: Boolean)` — mirrors
  the full tree from the roadmap PDF (~130 nodes, the visual checklist).
- `CurriculumTopic(id, title, category, recap: Recap?, explain, keyPoints,
  quiz: List<QuizQuestion>, tutorFocus)` — one entry per authored topic from
  `kotlin-tutor-content.md` (42 topics). `recap` is null only for the very
  first topic. `tutorFocus` is never shown verbatim in the UI — it's folded
  into the AI Tutor's system prompt.
- `Recap(previousTopicTitle, recapText, quickCheckQuestion, quickCheckAnswer)`
- `QuizQuestion(id, topicId, question, options, correctIndex, explanation)`
- `UserProgress` (Room entity): `topicId, isCompleted, lastQuizScore,
  lastAttemptedAt`

`CurriculumTopic` replaces the earlier `Lesson` model — it supersedes the
original 8 hand-written topics with the full 42-topic curriculum, transcribed
from `kotlin-tutor-content.md` into `Curriculum.kt`. Each topic maps onto a
`RoadmapNode` id; where one curriculum topic covers several roadmap leaf nodes
(e.g. "Basic Types" covers `data-types`, `integers`, `floats`, …), the primary
node is flagged `hasContent = true` and the rest stay on the manual-check
roadmap checklist.

## Screens

- **RoadmapScreen** — the entire roadmap tree as an expandable checklist,
  with completion state and overall progress %.
- **LessonScreen** — recap of the previous topic, explanation + key points
  for the current one, plus "Take Quiz", "Ask the Tutor", and "Mark Complete".
- **QuizScreen** — multiple-choice flow, score summary, retry.
- **TutorScreen** — chat UI scoped to the current topic, with a mode selector
  (Explain / Quiz me / Review my code / Give an exercise) and a message list.
- **SettingsScreen** — enter/clear the DeepSeek API key.

Backed by `RoadmapRepository`, `CurriculumRepository`, `ProgressRepository`,
`TutorRepository`, `ApiKeyStore`.

## AI Tutor

A chat-based tutor per topic, backed by **DeepSeek** (model `deepseek-v4-pro`,
OpenAI-compatible Chat Completions API).

- **Endpoint:** `POST https://api.deepseek.com/chat/completions`, header
  `Authorization: Bearer <key>`, JSON body `{model, messages, stream}`.
- **Client:** Ktor Client (OkHttp engine) + kotlinx.serialization for request
  and response DTOs. This is the app's only network dependency.
- **API key:** the user supplies their own DeepSeek key in `SettingsScreen`.
  It's stored on-device with `androidx.security` `EncryptedSharedPreferences`
  (Android Keystore-backed) — never hardcoded, never committed. The app ships
  with no key and no backend proxy; without a key, Tutor screens show a
  "add your DeepSeek API key in Settings" prompt instead of erroring.
- **`TutorMode`** — one of `Explain`, `QuizMe`, `ReviewMyCode`, `GiveExercise`.
  Each mode builds its system prompt from the current `CurriculumTopic`, per
  the source doc's mapping:
  - *Explain* -> topic's `explain` + `keyPoints`
  - *Quiz me* -> topic's `quiz` questions, asked one at a time
  - *Review my code* -> `keyPoints` used as a review rubric against
    learner-pasted code
  - *Give an exercise* -> `tutorFocus`'s suggested exercise
- **Chat UI:** message list (user/assistant bubbles) + mode chips + text
  input. **v1 is non-streaming** (single request/response) — streaming
  (SSE) is a reasonable fast-follow but adds real complexity, so it's
  deliberately deferred.
- **History:** kept in the `TutorViewModel` only (cleared on leaving the
  screen) for v1 — not persisted to Room. Straightforward to add later
  (a `ChatMessageEntity` + DAO) if per-topic history turns out to matter.
- **Errors:** missing/invalid key -> inline prompt to open Settings; network
  or non-2xx response -> inline retry, no crash.
- **Cost:** DeepSeek is a metered API and the user's own key is billed
  directly — the app does no usage capping or cost display in v1.

## Error handling

Room errors are wrapped in `Result` and surfaced via a retry snackbar.
Missing curriculum content is a normal UI state ("coming soon"), not an
error. DeepSeek call failures (see above) are surfaced inline, not fatal.

## Testing

- Unit: repositories, quiz scoring logic, progress %, ViewModel state
  transitions (JUnit + `kotlinx-coroutines-test`), `TutorMode` system-prompt
  building (pure function, no network), DeepSeek request/response DTO
  serialization round-trips.
- UI: Compose UI tests for roadmap navigation and the quiz answer flow.
- The DeepSeek network call itself isn't exercised by automated tests (no
  live key in CI); it's verified manually against a real key.

## Explicitly out of scope for v1

- In-app code playground / running Kotlin snippets (the AI Tutor's "Review
  my code" mode reviews pasted code via the LLM — it doesn't execute it)
- User accounts, cloud sync, multi-device progress
- Streaming AI responses, persisted chat history
- iOS/desktop targets (Compose Multiplatform)
