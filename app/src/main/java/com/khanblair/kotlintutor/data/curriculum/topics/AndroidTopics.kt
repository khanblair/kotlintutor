package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val androidTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "android-sdk",
        title = "Android SDK",
        category = "Android",
        recap = Recap(
            previousTopicTitle = "Compose Multiplatform",
            recapText = "Compose Multiplatform lets a single Compose UI codebase target Android, iOS, desktop, and web — one of several ways Kotlin now reaches beyond the JVM.",
            quickCheckQuestion = "What does Compose Multiplatform let you share across platforms?",
            quickCheckAnswer = "A single Compose UI codebase, targeted at Android, iOS, desktop, and web.",
        ),
        explain = "The Android SDK (Software Development Kit) is the collection of tools, libraries, and platform APIs Google publishes for building Android apps. It isn't one artifact — it's a set of packages you install piece by piece: platform API levels (the actual framework classes an app can call, one set per Android version), build tools (compilers and packagers like aapt2 and d8), emulator system images (virtual devices for testing), and command-line tools like adb (Android Debug Bridge, for talking to a connected device or emulator). Every app declares which slice of that SDK it targets using three version numbers in its build config: compileSdk is the API level the code is compiled against — it determines which platform APIs and lint checks are available at build time. minSdk is the oldest API level the app is willing to install on — the OS refuses installation on anything older. targetSdk tells the OS which API level's behavior the app was designed and tested for; the platform uses it to decide which legacy compatibility behaviors to keep versus which modern behaviors to enforce, even though the actual code still runs against whatever OS version the device has. This app declares compileSdk 36, minSdk 26, and targetSdk 36 — meaning it's built against Android 16's SDK, opts in to that version's runtime behavior, but still installs on devices as old as Android 8.0 (API 26). None of this happens by hand: the Android Gradle Plugin (AGP) is the build-system plugin that reads those version numbers, resolves the right SDK platform and build tools, and drives the actual compile-package-sign pipeline that turns source code into an installable APK or AAB.",
        example = """
            |// From this app's own app/build.gradle.kts
            |android {
            |    compileSdk = 36
            |
            |    defaultConfig {
            |        minSdk = 26
            |        targetSdk = 36
            |    }
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "The Android SDK bundles platform APIs (per API level), build tools, emulator system images, and command-line tools like adb.",
            "compileSdk controls which APIs are available at build time; minSdk is the oldest OS version the app installs on; targetSdk signals which version's runtime behavior the app was built for.",
            "This app targets compileSdk 36 / targetSdk 36 while keeping minSdk 26, so it uses modern APIs but still installs on Android 8.0 devices.",
            "The Android Gradle Plugin (AGP) is what actually reads those version numbers and drives the SDK-aware build.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "android-sdk-q1",
                topicId = "android-sdk",
                question = "What does minSdk control?",
                options = listOf(
                    "The oldest Android version the app is allowed to install on",
                    "Which API level the code is compiled against",
                    "Which build tools version Gradle downloads",
                    "The target emulator image used for testing",
                ),
                correctIndex = 0,
                explanation = "minSdk sets the floor — the OS blocks installation on any device running an older API level than this.",
            ),
            QuizQuestion(
                id = "android-sdk-q2",
                topicId = "android-sdk",
                question = "This app declares compileSdk 36, minSdk 26, and targetSdk 36. What does that combination mean?",
                options = listOf(
                    "It only runs on Android devices running exactly API 36",
                    "It ignores minSdk entirely since targetSdk is set",
                    "It's built against API 36's SDK and opts into API 36 runtime behavior, but still installs on devices as old as API 26",
                    "compileSdk and targetSdk must always be different values",
                ),
                correctIndex = 2,
                explanation = "compileSdk/targetSdk determine build-time APIs and platform behavior; minSdk independently sets the lowest OS version it will install on.",
            ),
            QuizQuestion(
                id = "android-sdk-q3",
                topicId = "android-sdk",
                question = "What role does the Android Gradle Plugin (AGP) play with respect to the SDK?",
                options = listOf(
                    "It is the emulator used to run virtual devices",
                    "It is a replacement for the Kotlin compiler",
                    "It only manages dependency version numbers, not builds",
                    "It reads the SDK version settings and drives the actual compile/package/sign build pipeline",
                ),
                correctIndex = 3,
                explanation = "AGP is the build-system plugin that ties compileSdk/minSdk/targetSdk to the concrete SDK platform, build tools, and packaging steps.",
            ),
        ),
        tutorFocus = "Ground this in the app's own build.gradle.kts values (compileSdk 36 / minSdk 26 / targetSdk 36) since the learner has already seen this file. Make sure the three version concepts don't blur together — each answers a different question (build-time APIs, install floor, runtime behavior).",
    ),
    CurriculumTopic(
        id = "android-studio",
        title = "Android Studio",
        category = "Android",
        recap = Recap(
            previousTopicTitle = "Android SDK",
            recapText = "The Android SDK is a set of installable pieces — platform APIs per level, build tools, emulator images, adb — and compileSdk/minSdk/targetSdk pick which slice of it an app builds and runs against.",
            quickCheckQuestion = "This app declares compileSdk 36, minSdk 26, and targetSdk 36. What does that combination mean?",
            quickCheckAnswer = "It's built against API 36's SDK and opts into API 36 runtime behavior, but still installs on devices as old as API 26.",
        ),
        explain = "Android Studio is JetBrains' IntelliJ platform, distributed by Google with Android-specific tooling layered on top — and its most important job, distinct from being 'a nice code editor,' is managing the SDK/build toolchain end to end. The SDK Manager is where you install and update the pieces of the Android SDK described in the previous topic: specific platform API levels, build tools versions, and emulator system images, all without touching a command line. The AVD Manager (Android Virtual Device Manager) is where you configure emulated devices to test against — picking a system image, screen size, and hardware profile so you can run the app on, say, a simulated API 26 phone without owning one. When you open or edit a Gradle-based project, Android Studio performs a Gradle sync: it re-reads the build.gradle.kts files, resolves dependencies and the configured SDK versions, and rebuilds its internal model of the project so the IDE's code completion, error highlighting, and run configurations stay accurate. Finally, build variants — the combination of a build type (debug, with debugging enabled and no code shrinking, versus release, typically minified and signed for distribution) and any product flavors (variants of the same app, like free/paid or region-specific builds) — are configured and switched from within Android Studio's Build Variants panel, letting the same project produce multiple distinct app builds from one codebase.",
        example = "",
        keyPoints = listOf(
            "SDK Manager installs/updates platform API levels, build tools, and emulator system images.",
            "AVD Manager configures virtual devices (system image, screen size, hardware profile) for the emulator.",
            "Gradle sync re-reads the build files and refreshes the IDE's model of dependencies and SDK versions — needed whenever build.gradle.kts changes.",
            "Build variants combine a build type (debug/release) with optional product flavors, letting one project produce multiple distinct builds.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "android-studio-q1",
                topicId = "android-studio",
                question = "Which Android Studio tool do you use to install a new platform API level or emulator system image?",
                options = listOf("AVD Manager", "SDK Manager", "Build Variants panel", "Logcat"),
                correctIndex = 1,
                explanation = "The SDK Manager is specifically for installing and updating pieces of the Android SDK, including platform API levels and system images.",
            ),
            QuizQuestion(
                id = "android-studio-q2",
                topicId = "android-studio",
                question = "What happens during a Gradle sync?",
                options = listOf(
                    "The app is compiled and installed on a connected device",
                    "The emulator's system image is downloaded",
                    "Android Studio re-reads the build files and refreshes its internal model of dependencies and SDK versions",
                    "All build variants are built simultaneously",
                ),
                correctIndex = 2,
                explanation = "Gradle sync updates the IDE's understanding of the project after build-file changes, without necessarily producing a runnable build.",
            ),
            QuizQuestion(
                id = "android-studio-q3",
                topicId = "android-studio",
                question = "What is a build variant?",
                options = listOf(
                    "A separate installation of Android Studio for each SDK version",
                    "Another name for a Gradle sync",
                    "A virtual device configuration in the AVD Manager",
                    "The combination of a build type (like debug or release) with any configured product flavors",
                ),
                correctIndex = 3,
                explanation = "A build variant is a build type paired with a product flavor (if any), letting one project generate multiple distinct app builds.",
            ),
        ),
        tutorFocus = "This is a different angle from the general IDE-features 'Android Studio' topic elsewhere in the roadmap — stay focused on its role in the SDK/build toolchain specifically: SDK Manager, AVD Manager, Gradle sync, build variants. Avoid rehashing general code-editing features.",
    ),
    CurriculumTopic(
        id = "android-jetpack",
        title = "Android Jetpack",
        category = "Android",
        recap = Recap(
            previousTopicTitle = "Android Studio",
            recapText = "Android Studio's SDK Manager, AVD Manager, Gradle sync, and build variants make up the core of the SDK/build toolchain, on top of the raw Android SDK.",
            quickCheckQuestion = "What is a build variant?",
            quickCheckAnswer = "The combination of a build type (like debug or release) with any configured product flavors.",
        ),
        explain = "Android Jetpack is Google's suite of libraries, tools, and architectural guidance built on top of the raw platform APIs, aimed at reducing boilerplate and steering apps toward a consistent, modern architecture rather than leaving every team to reinvent patterns like navigation or database access from scratch. Jetpack isn't one library — it's dozens of independently versioned androidx.* artifacts, and this app itself is built on several of its core pieces. Jetpack Compose (androidx.compose.*) is the declarative UI toolkit used throughout this app's screens, replacing the older View/XML system with composable functions. Room (androidx.room, this app's data/progress package) is Jetpack's persistence library — an abstraction over SQLite that maps entities and DAOs to typed Kotlin classes and generates the SQL at compile time via KSP. Navigation-Compose (androidx.navigation) is what drives this app's screen-to-screen navigation, letting a NavHost declare composable destinations and back-stack behavior declaratively instead of managing FragmentTransactions by hand. ViewModel (androidx.lifecycle.ViewModel) is the piece of Jetpack that survives configuration changes like screen rotation and holds UI state outside the composable lifecycle; this app pairs each ViewModel with a StateFlow exposing its UI state — note that StateFlow itself comes from kotlinx.coroutines, not Jetpack, but Jetpack supplies the glue, such as viewModelScope, that lets a ViewModel launch coroutines tied to its own lifecycle. Screens in this app then read that state with collectAsState() and recompose whenever it changes. Together these form the app's MVVM-style architecture: Jetpack's stated goal is exactly this — take the parts of building an app that nearly every app needs (navigation, persistence, lifecycle-aware state, UI) and provide well-tested, officially maintained libraries for them instead of ad hoc solutions.",
        example = """
            |// Sketch of this app's ViewModel + StateFlow pattern (Jetpack ViewModel,
            |// StateFlow from kotlinx.coroutines, wired together via viewModelScope)
            |class ExampleViewModel : ViewModel() {
            |    private val _uiState = MutableStateFlow(ExampleUiState())
            |    val uiState: StateFlow<ExampleUiState> = _uiState.asStateFlow()
            |
            |    fun load() {
            |        viewModelScope.launch {
            |            _uiState.value = _uiState.value.copy(isLoading = true)
            |        }
            |    }
            |}
            |
            |// In a composable screen:
            |// val uiState by viewModel.uiState.collectAsState()
        """.trimMargin(),
        keyPoints = listOf(
            "Jetpack is a collection of independently versioned androidx.* libraries, not a single monolithic dependency.",
            "This app uses Jetpack Compose for UI, Room for local persistence, and Navigation-Compose for screen navigation.",
            "ViewModel is Jetpack's piece for surviving configuration changes; StateFlow (from kotlinx.coroutines, not Jetpack) is what it exposes, connected via Jetpack's viewModelScope.",
            "Jetpack's overall goal is reducing boilerplate for tasks nearly every app needs, encouraging a consistent, modern architecture.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "android-jetpack-q1",
                topicId = "android-jetpack",
                question = "Which of these libraries used by this app is part of Android Jetpack?",
                options = listOf("Ktor", "OkHttp", "Room", "kotlinx.serialization"),
                correctIndex = 2,
                explanation = "Room (androidx.room) is Jetpack's persistence library, used by this app's data/progress package. Ktor, kotlinx.serialization, and OkHttp are not Jetpack libraries.",
            ),
            QuizQuestion(
                id = "android-jetpack-q2",
                topicId = "android-jetpack",
                question = "Is StateFlow itself part of Android Jetpack?",
                options = listOf(
                    "Yes, StateFlow ships inside androidx.lifecycle",
                    "Yes, but only when used inside a Composable",
                    "No, StateFlow is exclusive to Kotlin Multiplatform projects",
                    "No — StateFlow comes from kotlinx.coroutines; Jetpack's ViewModel supplies the glue (like viewModelScope) to use it safely",
                ),
                correctIndex = 3,
                explanation = "StateFlow is part of the kotlinx.coroutines library, not Jetpack. Jetpack's ViewModel provides viewModelScope, which is how a ViewModel launches coroutines tied to its own lifecycle.",
            ),
            QuizQuestion(
                id = "android-jetpack-q3",
                topicId = "android-jetpack",
                question = "What is Jetpack's overall goal, as described by Google?",
                options = listOf(
                    "Replace the Android SDK entirely with a new platform",
                    "Reduce boilerplate for common app tasks and encourage a consistent, modern architecture",
                    "Provide a single library that every app must fully adopt",
                    "Replace Gradle as the Android build system",
                ),
                correctIndex = 1,
                explanation = "Jetpack supplies well-tested, officially maintained libraries for tasks nearly every app needs — navigation, persistence, lifecycle-aware state, UI — instead of leaving teams to build ad hoc solutions.",
            ),
        ),
        tutorFocus = "Tie this directly back to the app's own architecture: Compose for UI, Room in data/progress, Navigation-Compose in KotlinTutorNavHost, and ViewModel+StateFlow in each screen's ViewModel. Be precise that StateFlow is a coroutines-library type, not a Jetpack artifact, even though Jetpack's ViewModel is what makes it easy to use safely.",
    ),
)
