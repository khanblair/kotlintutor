package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val kotlinJavaInteropTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "java-from-kotlin",
        title = "Java from Kotlin",
        category = "Kotlin/Java Interop",
        recap = Recap(
            previousTopicTitle = "Kotlin Notebook",
            recapText = "Kotlin Notebook is a JetBrains IntelliJ IDEA plugin for Jupyter-style interactive Kotlin cells that mix code, Markdown, and rendered output; unlike a .kts script, it keeps state between cell runs.",
            quickCheckQuestion = "What's the key difference between a Kotlin Notebook cell and running a plain .kts script?",
            quickCheckAnswer = "A notebook keeps state between cell runs and renders rich output inline; a script reruns everything from the top and only prints text.",
        ),
        explain = "Because Kotlin compiles to JVM bytecode, it can call existing Java code directly — no wrapper or bridge needed. Java types appear in Kotlin as \"platform types,\" written with a trailing ! (for example String!) in tooling and error messages. A platform type is neither definitely nullable nor definitely non-nullable: Java has no nullability information in its bytecode, so Kotlin can't verify it and instead trusts the caller to check. That means a value from Java can compile fine as non-null and still throw a NullPointerException at runtime if it was actually null — the compiler won't stop you. Kotlin also smooths over Java's getter/setter convention: a Java method getName() (or isX() for booleans) is automatically usable as a read-only Kotlin property .name, and if a matching setName(value) also exists, .name becomes a mutable property you can assign to. Finally, Kotlin doesn't have checked exceptions, so it doesn't force you to catch or declare Java's checked exceptions — you can call a Java method that declares throws IOException without a try/catch or throws clause, and the compiler won't complain, though the exception can still be thrown at runtime.",
        example = """
            |// Java (from a library):
            |// public class Person {
            |//     public String getName() { return name; }
            |//     public void setName(String name) { this.name = name; }
            |//     public String getNickname() { return nickname; }   // may be null
            |// }
            |
            |fun greet(person: Person) {
            |    println(person.name)              // getName() used as a property: person.name
            |    person.name = "Ada"                // setName("Ada") called via property assignment
            |
            |    val nickname = person.nickname     // type is String! — a platform type
            |    if (nickname != null) {            // Kotlin trusts you to check; it won't force this
            |        println(nickname.uppercase())
            |    }
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Platform types (String!) mean Kotlin trusts you to judge nullability yourself — the compiler won't catch a null that slips through from Java.",
            "Java getX()/setX() pairs become Kotlin properties automatically: get-only if there's no setter, mutable if there is.",
            "Kotlin doesn't enforce Java's checked exceptions, so calling a throwing Java method needs no try/catch or throws declaration.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "java-from-kotlin-q1",
                topicId = "java-from-kotlin",
                question = "What is a Kotlin \"platform type\" like String!?",
                options = listOf(
                    "A type that only exists on Android devices",
                    "A value from Java whose nullability Kotlin can't verify, so it trusts the caller",
                    "A deprecated type kept only for backward compatibility",
                    "A type that can never be null, guaranteed by the compiler",
                ),
                correctIndex = 1,
                explanation = "Platform types come from Java, which has no nullability info in bytecode, so Kotlin can't classify them as nullable or non-null and trusts the caller instead.",
            ),
            QuizQuestion(
                id = "java-from-kotlin-q2",
                topicId = "java-from-kotlin",
                question = "How does Kotlin expose a Java method pair getName()/setName(String)?",
                options = listOf(
                    "As two separate methods with no special treatment",
                    "As a Kotlin property .name that can be read and assigned",
                    "Only as a read-only property, never assignable",
                    "It cannot be called from Kotlin at all",
                ),
                correctIndex = 1,
                explanation = "A matching getX()/setX() pair on a Java class is exposed as a mutable Kotlin property, so person.name works for both reading and assignment.",
            ),
            QuizQuestion(
                id = "java-from-kotlin-q3",
                topicId = "java-from-kotlin",
                question = "What happens when you call a Java method that declares a checked exception, from Kotlin?",
                options = listOf(
                    "The code fails to compile unless wrapped in try/catch",
                    "Kotlin compiles it without requiring try/catch or a throws declaration",
                    "Kotlin silently swallows the exception",
                    "You must first convert the method to Kotlin",
                ),
                correctIndex = 1,
                explanation = "Kotlin has no checked exceptions, so it doesn't force you to catch or declare exceptions thrown by Java methods, even though they can still occur at runtime.",
            ),
        ),
        tutorFocus = "The platform-type nullability trap is the most important real-world danger here — spend the most time on it. A good exercise is giving the learner a Java method signature that returns a platform type and having them write defensive Kotlin that checks it before use.",
    ),
    CurriculumTopic(
        id = "kotlin-from-java",
        title = "Kotlin from Java",
        category = "Kotlin/Java Interop",
        recap = Recap(
            previousTopicTitle = "Java from Kotlin",
            recapText = "Calling Java from Kotlin: Java values arrive as platform types (String!) since Kotlin can't verify their nullability; Java getX()/setX() pairs become Kotlin properties; and Kotlin doesn't enforce Java's checked exceptions.",
            quickCheckQuestion = "What does a platform type like String! mean, and how does Kotlin expose a Java getX()/setX() pair?",
            quickCheckAnswer = "Its nullability is unverified so Kotlin trusts the caller; the pair becomes a mutable Kotlin property.",
        ),
        explain = "Calling Kotlin code from Java requires understanding how the Kotlin compiler maps Kotlin constructs onto JVM bytecode, since Java has no concept of top-level functions, default parameters, or companion objects. Top-level functions and properties declared in a file like Foo.kt are compiled into static methods on a generated class named FooKt — so a function fun greet() in Greeting.kt becomes GreetingKt.greet() from Java. You can control that generated name with the @file:JvmName(\"CustomName\") file annotation. A function or property inside a companion object is, by default, only reachable through the companion instance (MyClass.Companion.doThing()); annotating it @JvmStatic generates a true static method on the outer class instead, so Java can call MyClass.doThing() directly. Kotlin's default parameter values have no Java equivalent — Java can't omit arguments — so by default Java callers must supply every parameter; adding @JvmOverloads to a function tells the compiler to generate one Java-visible overload for each dropped trailing default parameter. Visibility maps over directly: Kotlin's default public modifier compiles to Java public, and internal, protected, and private map to their closest Java equivalents.",
        example = """
            |// Greeting.kt
            |@file:JvmName("Greetings")                 // Java calls Greetings.hello(...), not GreetingKt
            |
            |fun hello(name: String) = "Hello, ${'$'}name!"   // top-level fun -> static method on Greetings
            |
            |class Counter {
            |    companion object {
            |        @JvmStatic
            |        fun create(): Counter = Counter()   // true static method: Counter.create()
            |    }
            |
            |    @JvmOverloads
            |    fun greet(name: String, punctuation: String = "!") =
            |        "Hi ${'$'}name${'$'}punctuation"
            |        // Java sees two overloads: greet(String) and greet(String, String)
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Top-level functions in Foo.kt compile to static methods on a generated FooKt class, or a custom name via @file:JvmName.",
            "@JvmStatic turns a companion object member into a true Java static method.",
            "@JvmOverloads generates one Java-visible overload per dropped trailing default parameter, since Java has no default parameters.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "kotlin-from-java-q1",
                topicId = "kotlin-from-java",
                question = "Where do top-level functions from a file named Greeting.kt end up, as seen from Java?",
                options = listOf(
                    "Nowhere — Java can't see top-level functions",
                    "As static methods on a generated class named GreetingKt, or a custom name via @file:JvmName",
                    "As instance methods on the first class declared in the file",
                    "As methods on a class named after the package",
                ),
                correctIndex = 1,
                explanation = "The compiler generates a class named after the file (e.g. GreetingKt) to hold top-level functions as static methods for Java callers; @file:JvmName can rename it.",
            ),
            QuizQuestion(
                id = "kotlin-from-java-q2",
                topicId = "kotlin-from-java",
                question = "What does @JvmStatic do?",
                options = listOf(
                    "Marks a function as thread-safe",
                    "Exposes a companion object member as a true static method on the containing class",
                    "Prevents a function from being overridden",
                    "Converts a Kotlin property into a Java field",
                ),
                correctIndex = 1,
                explanation = "@JvmStatic generates a genuine static method for a companion object member, so Java can call it as ClassName.method() instead of going through Companion.",
            ),
            QuizQuestion(
                id = "kotlin-from-java-q3",
                topicId = "kotlin-from-java",
                question = "Why is @JvmOverloads needed for a Kotlin function with default parameter values, when called from Java?",
                options = listOf(
                    "It isn't needed — Java understands default parameters natively",
                    "Java has no default parameters, so without it Java callers must supply every argument; it generates the missing overloads",
                    "It hides the function from Java entirely",
                    "It converts default values into required constructor arguments",
                ),
                correctIndex = 1,
                explanation = "Java has no concept of default parameter values, so Kotlin normally compiles the function with all parameters required from Java; @JvmOverloads generates the convenient shorter overloads.",
            ),
        ),
        tutorFocus = "This is about designing Kotlin APIs that Java callers will actually enjoy using. A good exercise is having the learner add @JvmStatic and @JvmOverloads to a small companion-object factory function with a default parameter, and predict what Java call sites become valid.",
    ),
)
