package com.khanblair.kotlintutor.data.roadmap

import com.khanblair.kotlintutor.model.RoadmapNode

/**
 * Topic IDs with authored [com.khanblair.kotlintutor.model.CurriculumTopic] content —
 * the 42 topics transcribed from docs/kotlin-tutor-content.md into Curriculum.kt.
 */
val CONTENT_TOPIC_IDS: Set<String> = setOf(
    "why-use-kotlin",
    "history-of-kotlin",
    "java-interoperability-intro",
    "val-vs-var",
    "type-inference",
    "data-types",
    "string-templates",
    "type-checks-casts",
    "print-println",
    "lists-sets-maps",
    "ranges",
    "conditional-expressions",
    "loops",
    "throwing-exceptions",
    "functions-basics",
    "lambda-functions",
    "higher-order-functions",
    "extension-functions",
    "standard-functions",
    "read-only-vs-mutable",
    "transformations",
    "filtering",
    "aggregate-operations",
    "sequences",
    "defining-classes",
    "properties-methods",
    "inheritance",
    "data-classes",
    "sealed-class",
    "object-declarations",
    "class-generics",
    "visibility-modifiers",
    "what-is-null-safety",
    "nullability-check-operators",
    "suspending-functions",
    "coroutine-builders",
    "asynchronous-flow",
    "coroutines-best-practices",
    "understanding-packages",
    "standard-library",
    "serialization",
    "gradle",
)

private fun node(id: String, title: String, parentId: String?, category: String) =
    RoadmapNode(id = id, title = title, parentId = parentId, category = category, hasContent = id in CONTENT_TOPIC_IDS)

/**
 * The full Kotlin roadmap tree, mirroring docs/kotlin.pdf (the roadmap.sh Kotlin roadmap).
 * Top-level nodes (parentId == null) are categories; their children are individual topics.
 */
object RoadmapContent {
    val nodes: List<RoadmapNode> = listOf(
        // Introduction to Kotlin
        node("introduction", "Introduction to Kotlin", null, "Introduction to Kotlin"),
        node("history-of-kotlin", "History of Kotlin", "introduction", "Introduction to Kotlin"),
        node("why-use-kotlin", "Why use Kotlin", "introduction", "Introduction to Kotlin"),
        node("java-interoperability-intro", "Java Interoperability", "introduction", "Introduction to Kotlin"),

        // Language Basics
        node("language-basics", "Language Basics", null, "Language Basics"),
        node("setting-up-environment", "Setting up the Environment", "language-basics", "Language Basics"),
        node("code-organisation", "Code Organisation", "language-basics", "Language Basics"),
        node("main-function", "main Function", "language-basics", "Language Basics"),
        node("comments", "Comments", "language-basics", "Language Basics"),
        node("val-vs-var", "val vs var", "language-basics", "Language Basics"),
        node("variables", "Variables", "language-basics", "Language Basics"),
        node("data-types", "Data types", "language-basics", "Language Basics"),
        node("integers", "Integers", "language-basics", "Language Basics"),
        node("unsigned-integers", "Unsigned Integers", "language-basics", "Language Basics"),
        node("floats", "Floats", "language-basics", "Language Basics"),
        node("characters", "Characters", "language-basics", "Language Basics"),
        node("strings", "Strings", "language-basics", "Language Basics"),
        node("booleans", "Booleans", "language-basics", "Language Basics"),
        node("arrays", "Arrays", "language-basics", "Language Basics"),
        node("type-inference", "Type Inference", "language-basics", "Language Basics"),
        node("type-checks-casts", "Type Checks & Casts", "language-basics", "Language Basics"),
        node("print-println", "print & println", "language-basics", "Language Basics"),
        node("string-templates", "String Templates", "language-basics", "Language Basics"),

        // Control Flow
        node("control-flow", "Control Flow", null, "Control Flow"),
        node("conditional-expressions", "Conditional Expressions (if/when)", "control-flow", "Control Flow"),
        node("loops", "Loops (for/while)", "control-flow", "Control Flow"),
        node("label-loops", "Label Loops", "control-flow", "Control Flow"),
        node("break-continue", "break & continue", "control-flow", "Control Flow"),

        // Functions
        node("functions", "Functions", null, "Functions"),
        node("functions-basics", "Function Parameters & Return", "functions", "Functions"),
        node("local-functions", "Local Functions", "functions", "Functions"),
        node("member-functions", "Member Functions", "functions", "Functions"),
        node("varargs", "varargs", "functions", "Functions"),
        node("standard-functions", "Standard functions", "functions", "Functions"),
        node("tail-recursive-functions", "Tail-recursive Functions", "functions", "Functions"),
        node("higher-order-functions", "Higher-order Functions", "functions", "Functions"),
        node("lambda-functions", "Lambda Functions", "functions", "Functions"),
        node("anonymous-functions", "Anonymous Functions", "functions", "Functions"),
        node("extension-functions", "Extension Functions", "functions", "Functions"),
        node("function-types", "Function Types", "functions", "Functions"),

        // Exceptions
        node("exceptions", "Exceptions", null, "Exceptions"),
        node("throwing-exceptions", "Throwing Exceptions", "exceptions", "Exceptions"),
        node("catching-exceptions", "Catching Exceptions", "exceptions", "Exceptions"),

        // Collections
        node("collections", "Collections", null, "Collections"),
        node("lists-sets-maps", "Lists, Sets, Maps", "collections", "Collections"),
        node("ranges", "Ranges", "collections", "Collections"),
        node("progressions", "Progressions", "collections", "Collections"),
        node("sequences", "Sequences", "collections", "Collections"),
        node("iterators", "Iterators", "collections", "Collections"),
        node("read-only-vs-mutable", "read-only vs mutable", "collections", "Collections"),

        // Collection Operations
        node("collection-operations", "Collection Operations", null, "Collection Operations"),
        node("transformations", "Transformations", "collection-operations", "Collection Operations"),
        node("filtering", "Filtering", "collection-operations", "Collection Operations"),
        node("retrieving-collection-parts", "Retrieving Collection Parts", "collection-operations", "Collection Operations"),
        node("retrieving-single-elements", "Retrieving Single Elements", "collection-operations", "Collection Operations"),
        node("plus-minus-operators", "plus & minus Operators", "collection-operations", "Collection Operations"),
        node("ordering", "Ordering", "collection-operations", "Collection Operations"),
        node("grouping", "Grouping", "collection-operations", "Collection Operations"),
        node("aggregate-operations", "Aggregate Operations", "collection-operations", "Collection Operations"),
        node("collection-specific-operations", "Collection-specific Operations", "collection-operations", "Collection Operations"),

        // Classes & Objects
        node("classes-objects", "Classes & Objects", null, "Classes & Objects"),
        node("defining-classes", "Defining Classes", "classes-objects", "Classes & Objects"),
        node("creating-instances", "Creating Instances", "classes-objects", "Classes & Objects"),
        node("class-members", "Class Members", "classes-objects", "Classes & Objects"),
        node("properties-methods", "Properties & Methods", "classes-objects", "Classes & Objects"),
        node("constructors", "Constructors", "classes-objects", "Classes & Objects"),
        node("nested-inner-classes", "Nested & Inner Classes", "classes-objects", "Classes & Objects"),
        node("object-declarations", "Object Declarations", "classes-objects", "Classes & Objects"),
        node("inheritance", "Inheritance", "classes-objects", "Classes & Objects"),
        node("abstract-class", "Abstract Class", "classes-objects", "Classes & Objects"),
        node("interfaces", "Interfaces", "classes-objects", "Classes & Objects"),
        node("visibility-modifiers", "Visibility Modifiers", "classes-objects", "Classes & Objects"),
        node("property-delegates", "Property Delegates", "classes-objects", "Classes & Objects"),
        node("class-generics", "Class Generics", "classes-objects", "Classes & Objects"),

        // More on Classes & Objects
        node("more-classes-objects", "More on Classes & Objects", null, "More on Classes & Objects"),
        node("data-classes", "Data Classes", "more-classes-objects", "More on Classes & Objects"),
        node("sealed-class", "Sealed Class", "more-classes-objects", "More on Classes & Objects"),
        node("enum-class", "Enum Class", "more-classes-objects", "More on Classes & Objects"),
        node("inline-class", "Inline Class", "more-classes-objects", "More on Classes & Objects"),
        node("type-aliases", "Type Aliases", "more-classes-objects", "More on Classes & Objects"),

        // Null Safety
        node("null-safety", "Null Safety", null, "Null Safety"),
        node("what-is-null-safety", "What is Null Safety?", "null-safety", "Null Safety"),
        node("nullable-vs-non-nullable", "Nullable vs Non-nullable", "null-safety", "Null Safety"),
        node("nullability-check-operators", "Nullability Check Operators", "null-safety", "Null Safety"),
        node("safe-casts", "Safe Casts", "null-safety", "Null Safety"),

        // Coroutines & Async Programming
        node("coroutines", "Coroutines & Async Programming", null, "Coroutines & Async Programming"),
        node("suspending-functions", "Suspending Functions", "coroutines", "Coroutines & Async Programming"),
        node("coroutine-builders", "Coroutines Builders", "coroutines", "Coroutines & Async Programming"),
        node("coroutines-behavior", "Coroutines Behavior", "coroutines", "Coroutines & Async Programming"),
        node("asynchronous-flow", "Asynchronous Flow", "coroutines", "Coroutines & Async Programming"),
        node("coroutines-best-practices", "Coroutines Best Practices", "coroutines", "Coroutines & Async Programming"),

        // Packages & Ecosystem
        node("packages-ecosystem", "Packages & Ecosystem", null, "Packages & Ecosystem"),
        node("understanding-packages", "Understanding Packages", "packages-ecosystem", "Packages & Ecosystem"),
        node("importing-packages", "Importing Packages", "packages-ecosystem", "Packages & Ecosystem"),
        node("standard-library", "Standard Library", "packages-ecosystem", "Packages & Ecosystem"),
        node("default-imports", "Default Imports", "packages-ecosystem", "Packages & Ecosystem"),
        node("opt-in-requirements", "Opt-in Requirements", "packages-ecosystem", "Packages & Ecosystem"),

        // Documentation
        node("documentation", "Documentation", null, "Documentation"),
        node("dokka", "Dokka", "documentation", "Documentation"),
        node("kdoc", "KDoc", "documentation", "Documentation"),

        // Build Tools
        node("build-tools", "Build Tools", null, "Build Tools"),
        node("gradle", "Gradle", "build-tools", "Build Tools"),
        node("gradle-plugins", "Gradle Plugins", "build-tools", "Build Tools"),
        node("maven", "Maven", "build-tools", "Build Tools"),
        node("build-tool-api", "Build Tool API", "build-tools", "Build Tools"),
        node("teamcity", "CI/CD: TeamCity", "build-tools", "Build Tools"),

        // Kotlin Libraries
        node("kotlin-libraries", "Kotlin Libraries", null, "Kotlin Libraries"),
        node("test-library", "Test Library", "kotlin-libraries", "Kotlin Libraries"),
        node("coroutines-library", "Coroutines", "kotlin-libraries", "Kotlin Libraries"),
        node("serialization", "Serialization", "kotlin-libraries", "Kotlin Libraries"),
        node("io-library", "I/O Library", "kotlin-libraries", "Kotlin Libraries"),
        node("date-time", "Date & Time", "kotlin-libraries", "Kotlin Libraries"),
        node("jvm-metadata", "JVM Metadata", "kotlin-libraries", "Kotlin Libraries"),

        // I/O
        node("io", "I/O", null, "I/O"),
        node("creating-files", "Creating Files", "io", "I/O"),
        node("writing-reading-files", "Writing & Reading Files", "io", "I/O"),
        node("buffered-streams", "Buffered Streams", "io", "I/O"),

        // IDEs
        node("ides", "IDEs", null, "IDEs"),
        node("intellij-idea", "IntelliJ IDEA", "ides", "IDEs"),
        node("android-studio-ide", "Android Studio", "ides", "IDEs"),
        node("kotlin-notebook", "Kotlin Notebook", "ides", "IDEs"),

        // Kotlin/Java Interop
        node("kotlin-java", "Kotlin/Java Interop", null, "Kotlin/Java Interop"),
        node("java-from-kotlin", "Java from Kotlin", "kotlin-java", "Kotlin/Java Interop"),
        node("kotlin-from-java", "Kotlin from Java", "kotlin-java", "Kotlin/Java Interop"),

        // Kotlin Platforms
        node("kotlin-platforms", "Kotlin Platforms", null, "Kotlin Platforms"),
        node("server-side-apps", "Server-side Apps", "kotlin-platforms", "Kotlin Platforms"),
        node("kotlin-js", "Kotlin/JavaScript", "kotlin-platforms", "Kotlin Platforms"),
        node("kotlin-wasm", "Kotlin/WASM", "kotlin-platforms", "Kotlin Platforms"),
        node("kotlin-multiplatform", "Kotlin Multiplatform", "kotlin-platforms", "Kotlin Platforms"),
        node("kotlin-native", "Kotlin/Native", "kotlin-platforms", "Kotlin Platforms"),
        node("kotlin-scripting", "Kotlin Scripting", "kotlin-platforms", "Kotlin Platforms"),
        node("c-interop", "C Interoperability", "kotlin-platforms", "Kotlin Platforms"),
        node("swift-objc-interop", "Swift/Objective-C Interop.", "kotlin-platforms", "Kotlin Platforms"),
        node("compose-multiplatform", "Compose Multiplatform", "kotlin-platforms", "Kotlin Platforms"),

        // Android
        node("android", "Android", null, "Android"),
        node("android-sdk", "Android SDK", "android", "Android"),
        node("android-studio", "Android Studio", "android", "Android"),
        node("android-jetpack", "Android Jetpack", "android", "Android"),

        // Kotlin Applications
        node("kotlin-applications", "Kotlin Applications", null, "Kotlin Applications"),
        node("ktor", "Ktor", "kotlin-applications", "Kotlin Applications"),
        node("spring", "Spring", "kotlin-applications", "Kotlin Applications"),
        node("quarkus", "Quarkus", "kotlin-applications", "Kotlin Applications"),
        node("vertx", "Vert.x", "kotlin-applications", "Kotlin Applications"),

        // Data Analysis
        node("data-analysis", "Data Analysis", null, "Data Analysis"),
        node("kotlin-notebooks", "Kotlin Notebooks", "data-analysis", "Data Analysis"),
        node("kotlin-dataframe", "Kotlin DataFrame", "data-analysis", "Data Analysis"),
        node("kandy", "Kandy", "data-analysis", "Data Analysis"),

        // Competitive Programming (standalone, no sub-topics on the source roadmap)
        node("competitive-programming", "Competitive Programming", null, "Competitive Programming"),

        // AI Development
        node("ai-development", "AI Development", null, "AI Development"),
        node("koog", "Koog", "ai-development", "AI Development"),
    )
}
