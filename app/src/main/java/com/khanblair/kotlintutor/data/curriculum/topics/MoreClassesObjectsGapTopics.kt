package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val moreClassesObjectsGapTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "enum-class",
        title = "Enum Class",
        category = "More on Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Property Delegates",
            recapText = "A delegate (by lazy, by Delegates.observable, or a custom class with getValue/setValue) hands a property's storage and access logic to another object instead of a plain backing field.",
            quickCheckQuestion = "What two operator functions does a custom property delegate need?",
            quickCheckAnswer = "getValue and setValue (setValue only for var).",
        ),
        explain = "An enum class defines a fixed set of named constants, each one a singleton instance of the enum type — you can never have more instances than the ones listed. Enum constants can carry constructor properties, just like a regular class: enum class Planet(val mass: Double) { EARTH(5.97e24), MARS(6.39e23) }. Every constant automatically gets a name property (its declared identifier as a String) and an ordinal property (its 0-based declaration position). entries is the modern, allocation-free way to iterate all constants (it replaces the older values() array-returning function). Because the compiler knows every possible constant, a when expression over an enum can be exhaustive without an else branch — as long as every constant is handled.",
        example = """
            |enum class Color { RED, GREEN, BLUE }               // simplest form
            |
            |enum class Planet(val mass: Double) {                // constants with constructor args
            |    EARTH(5.97e24),
            |    MARS(6.39e23);                                   // semicolon needed before extra members
            |
            |    fun describe() = "${'$'}name has mass ${'$'}mass kg"
            |}
            |
            |enum class Direction {
            |    NORTH { override fun opposite() = SOUTH },        // each constant can override a member
            |    SOUTH { override fun opposite() = NORTH };
            |    abstract fun opposite(): Direction
            |}
            |
            |fun main() {
            |    val c = Color.GREEN
            |    println(c.name)                                  // GREEN
            |    println(c.ordinal)                                // 1
            |    println(Color.entries)                            // [RED, GREEN, BLUE]
            |
            |    val label = when (c) {                            // exhaustive, no else needed
            |        Color.RED -> "Stop"
            |        Color.GREEN -> "Go"
            |        Color.BLUE -> "Chill"
            |    }
            |    println(label)                                    // Go
            |    println(Planet.EARTH.describe())                  // EARTH has mass 5.97E24 kg
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Each enum constant is a singleton instance of the enum class — comparable with == and safe in a when.",
            "Constants can take constructor arguments and even override a member with a per-constant body.",
            "entries (Kotlin's preferred alternative to values()) lists all constants without reallocating an array each call.",
            "A when over an enum can skip else once every constant is covered — the compiler checks exhaustiveness.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "enum-class-q1",
                topicId = "enum-class",
                question = "What is each constant of an enum class?",
                options = listOf(
                    "A separate subclass you can instantiate freely",
                    "A singleton instance of the enum type",
                    "A String constant only",
                    "A companion object member",
                ),
                correctIndex = 1,
                explanation = "Every enum constant is a fixed, single instance of the enum class — no more instances can ever be created.",
            ),
            QuizQuestion(
                id = "enum-class-q2",
                topicId = "enum-class",
                question = "Which built-in property gives a constant's 0-based declaration position?",
                options = listOf("name", "ordinal", "entries", "hashCode"),
                correctIndex = 1,
                explanation = "ordinal returns the position (starting at 0) in which the constant was declared.",
            ),
            QuizQuestion(
                id = "enum-class-q3",
                topicId = "enum-class",
                question = "Why can a when over an enum omit the else branch?",
                options = listOf(
                    "else is never allowed in a when",
                    "The compiler can verify all possible constants are covered, making it exhaustive",
                    "Enums don't support when expressions",
                    "It can't — else is always required for enums",
                ),
                correctIndex = 1,
                explanation = "Because the full set of enum constants is fixed and known at compile time, the compiler can confirm every case is handled without else.",
            ),
        ),
        tutorFocus = "Demo the per-constant override (Direction.opposite()) — it's the detail learners miss, since it looks like anonymous-object syntax. Exercise: model a TrafficLight enum with a constructor property (durationSeconds) and an exhaustive when that prints an action per color.",
    ),
    CurriculumTopic(
        id = "inline-class",
        title = "Inline Class",
        category = "More on Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Enum Class",
            recapText = "enum class defines a fixed set of singleton constants; they can carry constructor properties and per-constant bodies, and when over an enum is exhaustive without else.",
            quickCheckQuestion = "What property gives a constant's declared name as a String?",
            quickCheckAnswer = "name.",
        ),
        explain = "A value class (declared with value class, and annotated @JvmInline on Kotlin/JVM) wraps a single read-only property to add a distinct, type-safe wrapper around a primitive or other type — without the runtime cost of a normal wrapper object. At compile time UserId(\"u-42\") behaves like a real class with its own type, but at most use sites the compiler inlines it away, representing it as just the underlying value at runtime — no extra object allocation. An init block can validate the wrapped value at construction time, e.g. rejecting a blank String. The tradeoff is a real limitation: a value class may declare exactly one property in its primary constructor (as val) and cannot hold extra backing fields beyond it, though it can still declare functions and computed properties.",
        example = """
            |@JvmInline
            |value class UserId(val value: String) {
            |    init {
            |        require(value.isNotBlank()) { "UserId cannot be blank" }
            |    }
            |
            |    fun shortForm(): String = value.take(8)
            |}
            |
            |fun sendMessage(to: UserId, text: String) {
            |    println("Sending to ${'$'}{to.value}: ${'$'}text")
            |}
            |
            |fun main() {
            |    val id = UserId("u-4471")
            |    sendMessage(id, "hi!")             // sendMessage("u-4471", "hi!") won't compile — type safety
            |    println(id.shortForm())            // u-4471
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "value class (@JvmInline on Kotlin/JVM) wraps exactly one val property, gaining type safety over a raw primitive with little to no runtime overhead.",
            "It's a genuinely distinct type at compile time — you can't pass a plain String where a UserId is expected — but it's often represented as just the underlying value at runtime.",
            "An init block can validate the wrapped value once, at construction, instead of re-validating everywhere it's used.",
            "Limitation: only one property in the primary constructor, and no other backing fields besides it.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "inline-class-q1",
                topicId = "inline-class",
                question = "What does a value class primarily add around a wrapped primitive like String?",
                options = listOf(
                    "Automatic serialization",
                    "Type safety, with little to no extra runtime overhead in most cases",
                    "Thread safety",
                    "Mutability by default",
                ),
                correctIndex = 1,
                explanation = "A value class creates a distinct compile-time type around the wrapped value, and the compiler inlines it away at most use sites so there's little to no runtime cost.",
            ),
            QuizQuestion(
                id = "inline-class-q2",
                topicId = "inline-class",
                question = "How many properties can a value class's primary constructor declare?",
                options = listOf("Exactly one", "Up to three", "Any number, like a regular class", "Zero — properties must be declared in the body"),
                correctIndex = 0,
                explanation = "A value class is restricted to exactly one property in its primary constructor; it can't hold additional backing fields.",
            ),
            QuizQuestion(
                id = "inline-class-q3",
                topicId = "inline-class",
                question = "What can an init block inside a value class be used for?",
                options = listOf(
                    "Nothing — value classes can't have init blocks",
                    "Validating the wrapped value at construction time",
                    "Declaring a second property",
                    "Overriding equals() and hashCode()",
                ),
                correctIndex = 1,
                explanation = "An init block runs when the value class is constructed, so it's a natural place to require() the wrapped value is valid.",
            ),
        ),
        tutorFocus = "Contrast UserId(String) with a plain typealias for String to show value class actually prevents mixing IDs of different kinds, while a typealias would not (save the full typealias comparison for the next topic). Exercise: wrap a raw Int as a validated Age value class with an init check that it's non-negative.",
    ),
    CurriculumTopic(
        id = "type-aliases",
        title = "Type Aliases",
        category = "More on Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Inline Class",
            recapText = "value class (@JvmInline) wraps one val property into a genuinely distinct type, usually with no extra runtime cost; it can validate itself in an init block but is limited to that single property.",
            quickCheckQuestion = "Is a value class a new type at compile time, or just a naming shortcut?",
            quickCheckAnswer = "a new, distinct type — a plain String can't be passed where the wrapped type is expected.",
        ),
        explain = "typealias gives an existing type a new, shorter or more descriptive name, e.g. typealias UserMap = Map<String, User> or typealias ClickHandler = (View) -> Unit for a function type. Unlike value class, a type alias creates no new type at all — it's purely a compile-time naming convenience. A UserMap is a Map<String, User> as far as the compiler and the runtime are concerned; the two names are fully interchangeable in both directions, with no wrapping, no conversion, and no extra safety. This is the key contrast with the inline/value class from the previous topic: value class deliberately creates a distinct type so a raw String can't be used where a UserId is expected, while typealias intentionally does not — it only makes long or complex type signatures easier to read.",
        example = """
            |typealias UserMap = Map<String, User>                    // shorter name for a generic type
            |typealias ClickHandler = (view: View) -> Unit             // shorter name for a function type
            |
            |data class User(val name: String)
            |class View
            |
            |fun greetAll(users: UserMap) {                            // UserMap == Map<String, User>
            |    for ((id, user) in users) println("${'$'}id -> ${'$'}{user.name}")
            |}
            |
            |fun main() {
            |    val users: Map<String, User> = mapOf("1" to User("Ada"))
            |    greetAll(users)                                        // a plain Map works — no conversion needed
            |
            |    val handler: ClickHandler = { v -> println("clicked ${'$'}v") }
            |    val plain: (View) -> Unit = handler                    // fully interchangeable, either direction
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "typealias is purely a compile-time naming convenience — it does not create a new type.",
            "The alias and its underlying type are fully interchangeable everywhere, unlike a value class which is a distinct type.",
            "Great for shortening deeply nested generics or giving a function type a self-documenting name, like ClickHandler.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "type-aliases-q1",
                topicId = "type-aliases",
                question = "Does typealias UserMap = Map<String, User> create a new type?",
                options = listOf(
                    "Yes, a distinct type incompatible with Map<String, User>",
                    "No — UserMap is just another name for the exact same Map<String, User> type",
                    "Only at runtime, not at compile time",
                    "Only if annotated with @JvmInline",
                ),
                correctIndex = 1,
                explanation = "A type alias introduces no new type — it's purely a naming convenience fully interchangeable with the original type.",
            ),
            QuizQuestion(
                id = "type-aliases-q2",
                topicId = "type-aliases",
                question = "What is a good use case for a typealias like ClickHandler = (View) -> Unit?",
                options = listOf(
                    "Adding runtime validation to a function type",
                    "Giving a complex or repeated type signature a shorter, more descriptive name",
                    "Preventing a plain (View) -> Unit lambda from being passed where ClickHandler is expected",
                    "Making the function type immutable",
                ),
                correctIndex = 1,
                explanation = "Type aliases exist to improve readability for long or repeated type signatures, not to add restrictions or behavior.",
            ),
            QuizQuestion(
                id = "type-aliases-q3",
                topicId = "type-aliases",
                question = "How does typealias differ from value class in terms of type safety?",
                options = listOf(
                    "They behave identically — both create a distinct, incompatible type",
                    "typealias creates no new type (fully interchangeable), while value class creates a genuinely distinct type",
                    "value class creates no new type, while typealias does",
                    "Neither one affects type safety in any way",
                ),
                correctIndex = 1,
                explanation = "value class introduces real compile-time type safety by creating a distinct type; typealias is just an alternate name with no such distinction.",
            ),
        ),
        tutorFocus = "Drive home the contrast with the previous topic: swap a UserId value-class example for an equivalent typealias UserId = String and show it now compiles when a raw String is passed in — that's the tell. Exercise: alias a nested generic (e.g. Map<String, List<Int>>) and a function type used more than once in a small snippet.",
    ),
)
