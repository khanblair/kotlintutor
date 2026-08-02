package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val classesObjectsTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "defining-classes",
        title = "Defining Classes",
        category = "Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Sequences",
            recapText = "Sequences are lazy and element-by-element with no intermediate lists; a terminal op triggers the work.",
            quickCheckQuestion = "What triggers a sequence? Best use case?",
            quickCheckAnswer = "a terminal op like collect/first; large data or long chains.",
        ),
        explain = "A class is declared with class. Kotlin puts the primary constructor in the class header: class Person(val name: String, var age: Int). Parameters prefixed with val/var automatically become properties. An init block runs initialization logic when an instance is created. Secondary constructors (constructor(...)) are occasionally needed but often replaced by default parameter values. You create instances without new — just call the class like a function.",
        example = """
            |class Person(val name: String, var age: Int) {
            |    init {
            |        require(age >= 0) { "age must be non-negative" }
            |    }
            |}
            |
            |fun main() {
            |    val p = Person("Ada", 36)     // no 'new' keyword
            |    p.age = 37                    // var property is mutable
            |    println("${'$'}{p.name} is ${'$'}{p.age}")
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "val/var in the constructor header declares properties automatically.",
            "No new keyword — instantiate by calling the class.",
            "Prefer default parameters over multiple secondary constructors.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "defining-classes-q1",
                topicId = "defining-classes",
                question = "What does putting val/var before a constructor parameter do?",
                options = listOf(
                    "Nothing — it's just documentation",
                    "It declares that parameter as a property",
                    "It makes the parameter optional",
                    "It marks the parameter as private",
                ),
                correctIndex = 1,
                explanation = "val/var in the primary constructor header turns that parameter into a property on the class.",
            ),
            QuizQuestion(
                id = "defining-classes-q2",
                topicId = "defining-classes",
                question = "How do you create an instance in Kotlin?",
                options = listOf(
                    "new Person(\"Ada\")",
                    "Call the class like a function, e.g. Person(\"Ada\")",
                    "Person.new(\"Ada\")",
                    "Person.create(\"Ada\")",
                ),
                correctIndex = 1,
                explanation = "Kotlin has no new keyword — calling the class name constructs an instance.",
            ),
            QuizQuestion(
                id = "defining-classes-q3",
                topicId = "defining-classes",
                question = "What runs an init block?",
                options = listOf(
                    "Calling it manually, like a method",
                    "It runs during object construction",
                    "It runs the first time a property is accessed",
                    "It only runs in debug builds",
                ),
                correctIndex = 1,
                explanation = "init blocks execute as part of the object's construction, in declaration order.",
            ),
        ),
        tutorFocus = "The primary-constructor-as-header idea surprises Java learners — show how one line replaces fields + constructor + getters. Exercise: define a Rectangle(width, height) with an init that validates positivity.",
    ),
    CurriculumTopic(
        id = "properties-methods",
        title = "Properties & Methods",
        category = "Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Defining Classes",
            recapText = "Primary constructor sits in the header; val/var params become properties; no new; init runs at construction.",
            quickCheckQuestion = "How do you instantiate? What does val in the header do?",
            quickCheckAnswer = "call the class; declares a property.",
        ),
        explain = "Properties are declared with val/var and can have custom getters and setters. A property may be computed (no backing field) by providing a get(). Methods are just functions declared inside a class. The special identifier field refers to the backing field inside a custom accessor.",
        example = """
            |class Circle(val radius: Double) {
            |    val area: Double
            |        get() = Math.PI * radius * radius     // computed property, no backing field
            |
            |    var label: String = ""
            |        set(value) {
            |            field = value.trim()              // 'field' is the backing field
            |        }
            |
            |    fun describe(): String = "Circle(r=${'$'}radius, area=%.2f)".format(area)
            |}
            |
            |fun main() {
            |    val c = Circle(2.0)
            |    c.label = "  round  "
            |    println(c.describe())
            |    println("label='${'$'}{c.label}'")            // trimmed → 'round'
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Access properties directly (c.area) — don't write Java-style getArea().",
            "Use field (not the property name) inside a custom accessor, or you'll cause infinite recursion.",
            "Computed properties have no backing field; they run their getter each access.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "properties-methods-q1",
                topicId = "properties-methods",
                question = "How do you make a property computed from other values?",
                options = listOf(
                    "Mark it lateinit",
                    "Provide a custom get() and no initializer",
                    "Declare it as var instead of val",
                    "Use a companion object",
                ),
                correctIndex = 1,
                explanation = "A property with a custom get() and no stored initializer is computed fresh on each access.",
            ),
            QuizQuestion(
                id = "properties-methods-q2",
                topicId = "properties-methods",
                question = "What is field inside a setter?",
                options = listOf(
                    "A reference to another property named field",
                    "The backing field of the property",
                    "The parameter passed to the setter",
                    "A typo — it should be this",
                ),
                correctIndex = 1,
                explanation = "field is the special identifier that refers to the property's own backing field inside a custom accessor.",
            ),
            QuizQuestion(
                id = "properties-methods-q3",
                topicId = "properties-methods",
                question = "Why avoid using the property name directly inside its own getter?",
                options = listOf(
                    "It's a syntax error",
                    "It would call the accessor again, causing infinite recursion",
                    "It's slower than using field",
                    "It breaks smart casting",
                ),
                correctIndex = 1,
                explanation = "Referring to the property name inside its own getter/setter re-invokes the accessor, recursing infinitely.",
            ),
        ),
        tutorFocus = "The infinite-recursion trap (using the property name in its accessor) is worth demonstrating. Exercise: add a computed diameter property and a setter that normalizes input via field.",
    ),
    CurriculumTopic(
        id = "inheritance",
        title = "Inheritance & Interfaces",
        category = "Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Properties & Methods",
            recapText = "Custom get/set; computed properties have no backing field; use field inside accessors.",
            quickCheckQuestion = "Make a computed property? What is field?",
            quickCheckAnswer = "provide a get(); the backing field.",
        ),
        explain = "Classes are final by default — mark a class open to allow subclassing, and open a member to allow overriding. Subclasses use : Base(...) and override members. Abstract classes (abstract) can't be instantiated and may declare members without implementations. Interfaces (interface) declare contracts and may include default method implementations, but hold no state (beyond abstract properties). A class can implement multiple interfaces but extend only one class.",
        example = """
            |interface Shape {
            |    fun area(): Double
            |    fun describe(): String = "A shape with area ${'$'}{area()}"   // default method
            |}
            |
            |open class Rectangle(val w: Double, val h: Double) : Shape {
            |    override fun area(): Double = w * h
            |}
            |
            |class Square(side: Double) : Rectangle(side, side)
            |
            |fun main() {
            |    println(Square(3.0).describe())   // A shape with area 9.0
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Everything is final unless open — the opposite of Java's default.",
            "override is required (not optional) when overriding.",
            "Interfaces can provide default implementations but can't store state.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "inheritance-q1",
                topicId = "inheritance",
                question = "Why won't class B : A() compile if A is a normal class?",
                options = listOf(
                    "B must implement an interface first",
                    "Classes are final by default; A must be marked open",
                    "Kotlin doesn't support inheritance",
                    "A needs a companion object",
                ),
                correctIndex = 1,
                explanation = "Kotlin classes are final unless explicitly marked open, so subclassing a normal class fails to compile.",
            ),
            QuizQuestion(
                id = "inheritance-q2",
                topicId = "inheritance",
                question = "What's the difference between an abstract class and an interface?",
                options = listOf(
                    "No real difference in Kotlin",
                    "An abstract class can hold state and have constructors; an interface can't hold state",
                    "Interfaces can have constructors, abstract classes can't",
                    "Abstract classes can't have default method bodies",
                ),
                correctIndex = 1,
                explanation = "Abstract classes support constructors and stored state; interfaces can only declare abstract members plus default method bodies.",
            ),
            QuizQuestion(
                id = "inheritance-q3",
                topicId = "inheritance",
                question = "How many classes vs interfaces can a class inherit from?",
                options = listOf(
                    "Many classes, one interface",
                    "One class, many interfaces",
                    "Unlimited of both",
                    "Exactly one of each",
                ),
                correctIndex = 1,
                explanation = "Kotlin allows single class inheritance but multiple interface implementation.",
            ),
        ),
        tutorFocus = "Lead with \"final by default\" — it's the biggest surprise for Java devs. Probe when to pick an interface vs abstract class. Exercise: define a Shape interface with a default describe() and two implementing classes.",
    ),
    CurriculumTopic(
        id = "data-classes",
        title = "Data Classes",
        category = "Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Inheritance & Interfaces",
            recapText = "Classes are final unless open; override is required; interfaces have default methods but no state.",
            quickCheckQuestion = "Why won't B : A() compile? How many classes vs interfaces can you inherit?",
            quickCheckAnswer = "A must be open; one class, many interfaces.",
        ),
        explain = "A data class models a value that's defined by its data. From the properties in its primary constructor, the compiler generates equals(), hashCode(), toString(), componentN() functions (for destructuring), and copy(). This removes enormous boilerplate and gives you value-style semantics. Data classes are ideal for DTOs, model objects, and results.",
        example = """
            |data class User(val name: String, val age: Int)
            |
            |fun main() {
            |    val a = User("Ada", 36)
            |    val b = User("Ada", 36)
            |    println(a == b)                 // true — structural equality via equals()
            |    println(a)                      // User(name=Ada, age=36) via toString()
            |
            |    val older = a.copy(age = 37)    // copy with one field changed
            |    println(older)
            |
            |    val (name, age) = a             // destructuring via componentN()
            |    println("${'$'}name / ${'$'}age")
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "== uses equals() (structural), while === checks reference identity.",
            "Only properties in the primary constructor are used for the generated functions.",
            "Prefer val properties; a data class with var weakens its value semantics.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "data-classes-q1",
                topicId = "data-classes",
                question = "Name three things a data class generates for you.",
                options = listOf(
                    "equals, hashCode, toString (and copy, componentN)",
                    "A companion object, a builder, and a singleton",
                    "getters only, no equals or toString",
                    "A database table mapping",
                ),
                correctIndex = 0,
                explanation = "Data classes generate equals, hashCode, toString, copy, and componentN functions from the primary constructor.",
            ),
            QuizQuestion(
                id = "data-classes-q2",
                topicId = "data-classes",
                question = "What's the difference between == and ===?",
                options = listOf(
                    "No difference in Kotlin",
                    "== is structural equality; === is reference identity",
                    "=== is structural equality; == is reference identity",
                    "== only works on primitives",
                ),
                correctIndex = 1,
                explanation = "== calls equals() for structural equality; === checks whether two references point to the same object.",
            ),
            QuizQuestion(
                id = "data-classes-q3",
                topicId = "data-classes",
                question = "Which properties are used for a data class's equals/hashCode?",
                options = listOf(
                    "All properties, including those declared in the class body",
                    "Only those in the primary constructor",
                    "Only var properties",
                    "None — you must override them manually",
                ),
                correctIndex = 1,
                explanation = "Only primary-constructor properties participate in the generated equals/hashCode/toString/copy.",
            ),
        ),
        tutorFocus = "Contrast a data class with a plain class for == and printing. Probe == vs ===. Exercise: model a Point, compare two equal points, and use copy to shift one.",
    ),
    CurriculumTopic(
        id = "sealed-class",
        title = "Sealed & Enum Classes",
        category = "Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Data Classes",
            recapText = "Auto-generate equals/hashCode/toString/copy/componentN; == is structural, === is identity.",
            quickCheckQuestion = "== vs ===? Which properties are used?",
            quickCheckAnswer = "structural vs reference; the primary-constructor ones.",
        ),
        explain = "An enum class is a fixed set of named constants, each of which can hold data and methods. A sealed class (or interface) defines a restricted hierarchy: all direct subclasses must be declared in the same module/package. Because the compiler knows every possible subtype, a when over a sealed type can be exhaustive without an else — perfect for modeling states and results.",
        example = """
            |enum class Direction { NORTH, SOUTH, EAST, WEST }
            |
            |sealed interface Result
            |data class Success(val data: String) : Result
            |data class Failure(val error: String) : Result
            |
            |fun handle(r: Result): String = when (r) {   // no else needed — exhaustive
            |    is Success -> "OK: ${'$'}{r.data}"
            |    is Failure -> "Error: ${'$'}{r.error}"
            |}
            |
            |fun main() {
            |    println(Direction.NORTH)
            |    println(handle(Success("loaded")))
            |    println(handle(Failure("timeout")))
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Sealed hierarchies + when give you exhaustiveness checks at compile time — add a subtype and the compiler flags every when you forgot to update.",
            "Enums are for a fixed set of constants; sealed types are for a fixed set of shapes (each possibly carrying different data).",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "sealed-class-q1",
                topicId = "sealed-class",
                question = "Why can a when over a sealed type omit else?",
                options = listOf(
                    "It can't — else is always required",
                    "The compiler knows all subtypes, so it can verify exhaustiveness",
                    "Sealed types don't support when at all",
                    "Only enum classes can omit else, not sealed types",
                ),
                correctIndex = 1,
                explanation = "Since all subtypes of a sealed hierarchy are known at compile time, the compiler can verify a when covers every case.",
            ),
            QuizQuestion(
                id = "sealed-class-q2",
                topicId = "sealed-class",
                question = "When would you choose a sealed class over an enum?",
                options = listOf(
                    "When you just need a fixed set of named constants",
                    "When each case needs to carry different data/shape, not just a constant",
                    "When you need Java interop and nothing else",
                    "Sealed classes and enums are always interchangeable",
                ),
                correctIndex = 1,
                explanation = "Sealed types let each subtype carry its own distinct data, unlike a plain enum constant.",
            ),
            QuizQuestion(
                id = "sealed-class-q3",
                topicId = "sealed-class",
                question = "What benefit do you get when adding a new subtype to a sealed hierarchy?",
                options = listOf(
                    "Nothing changes automatically",
                    "The compiler flags every non-exhaustive when, so you can't forget a case",
                    "Existing when blocks silently ignore the new subtype",
                    "You must rewrite all existing subtypes",
                ),
                correctIndex = 1,
                explanation = "The compiler re-checks exhaustiveness everywhere the sealed type is used in a when, catching missed cases.",
            ),
        ),
        tutorFocus = "This is a Kotlin highlight — model a Result/UiState type and show the exhaustive when. Exercise: define a sealed PaymentState (Pending, Paid(amount), Failed(reason)) and a when that handles each.",
    ),
    CurriculumTopic(
        id = "object-declarations",
        title = "Object Declarations",
        category = "Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Sealed & Enum Classes",
            recapText = "Enums are fixed constants; sealed types are a fixed set of subtype shapes, enabling exhaustive when without else.",
            quickCheckQuestion = "Why can when skip else on a sealed type? Sealed vs enum?",
            quickCheckAnswer = "all subtypes are known; sealed cases can carry different data.",
        ),
        explain = "The object keyword creates a singleton — a class with exactly one instance, created lazily on first use. A companion object inside a class holds members tied to the class itself (like Java statics), including factory functions. An object expression (object : Type { … }) creates a one-off anonymous instance, similar to an anonymous class.",
        example = """
            |object Registry {                       // singleton
            |    val items = mutableListOf<String>()
            |    fun add(x: String) = items.add(x)
            |}
            |
            |class User private constructor(val name: String) {
            |    companion object {                  // holds a factory
            |        fun create(name: String) = User(name.trim())
            |    }
            |}
            |
            |fun main() {
            |    Registry.add("a")
            |    println(Registry.items)             // [a]
            |    val u = User.create("  Ada ")       // called on the class, via companion
            |    println(u.name)                     // Ada
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "object singletons are initialized lazily and are thread-safe on the JVM.",
            "A companion object is the idiomatic home for factory methods and constants.",
            "Overusing singletons for shared mutable state can create hidden coupling.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "object-declarations-q1",
                topicId = "object-declarations",
                question = "What does the object keyword create?",
                options = listOf(
                    "A new instance every time it's referenced",
                    "A singleton (single-instance object)",
                    "An abstract class",
                    "A Java-style static nested class only",
                ),
                correctIndex = 1,
                explanation = "object declares a class with exactly one lazily-created instance.",
            ),
            QuizQuestion(
                id = "object-declarations-q2",
                topicId = "object-declarations",
                question = "What is a companion object used for?",
                options = listOf(
                    "Storing instance-specific state",
                    "Class-level members like factories and constants (Java-static-like)",
                    "Declaring private constructors only",
                    "Nothing — it's purely stylistic",
                ),
                correctIndex = 1,
                explanation = "A companion object holds members that belong to the class itself, similar to Java's static members.",
            ),
            QuizQuestion(
                id = "object-declarations-q3",
                topicId = "object-declarations",
                question = "How do you call a companion function?",
                options = listOf(
                    "Through an instance only, e.g. user.create()",
                    "On the class itself, e.g. User.create(...)",
                    "Via a global function with the same name",
                    "It must be imported explicitly every time",
                ),
                correctIndex = 1,
                explanation = "Companion members are called directly on the class name.",
            ),
        ),
        tutorFocus = "Distinguish the three object uses (declaration, companion, expression). Probe why a private constructor + companion factory is a common pattern. Exercise: give a class a companion fromJson-style factory.",
    ),
    CurriculumTopic(
        id = "class-generics",
        title = "Generics",
        category = "Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Object Declarations",
            recapText = "object = singleton; companion object = class-level members/factories; object expressions = anonymous instances.",
            quickCheckQuestion = "What does object create? How do you call a companion function?",
            quickCheckAnswer = "a singleton; on the class, e.g. User.create().",
        ),
        explain = "Generics let a class or function work with a type parameter, written <T>. This gives type safety without duplicating code. Variance controls subtyping of generic types: out T (covariant) means the type only produces T (safe to read), while in T (contravariant) means it only consumes T. A reified type parameter (with inline functions) lets you access the actual type at runtime — something normally erased on the JVM.",
        example = """
            |class Box<T>(val value: T)                      // generic class
            |
            |fun <T> firstOrDefault(list: List<T>, default: T): T =
            |    list.firstOrNull() ?: default               // generic function
            |
            |inline fun <reified T> isType(value: Any): Boolean = value is T   // reified
            |
            |fun main() {
            |    val box = Box("hello")
            |    println(box.value)
            |    println(firstOrDefault(listOf(1, 2), 0))    // 1
            |    println(firstOrDefault(emptyList<Int>(), -1)) // -1
            |    println(isType<String>("hi"))               // true
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Type parameters give compile-time safety and avoid casting.",
            "out/in express who produces vs consumes the type (producer/consumer rule).",
            "Runtime type checks on a plain T don't work due to type erasure — use reified inside an inline function.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "class-generics-q1",
                topicId = "class-generics",
                question = "What problem do generics solve?",
                options = listOf(
                    "They make code run faster",
                    "Reusable, type-safe code across different types without casting",
                    "They remove the need for interfaces",
                    "They allow multiple inheritance",
                ),
                correctIndex = 1,
                explanation = "Generics let one implementation work with many types safely, without manual casts.",
            ),
            QuizQuestion(
                id = "class-generics-q2",
                topicId = "class-generics",
                question = "What does out T signify?",
                options = listOf(
                    "Contravariance — the type only consumes T",
                    "Covariance — the type only produces T, so it's safe to read",
                    "The type parameter is nullable",
                    "The type parameter must be a subclass of T",
                ),
                correctIndex = 1,
                explanation = "out marks a type parameter as covariant/producer-only, which is safe to read but not write.",
            ),
            QuizQuestion(
                id = "class-generics-q3",
                topicId = "class-generics",
                question = "Why do you need reified to check a generic type at runtime?",
                options = listOf(
                    "reified isn't actually required for this",
                    "Because the JVM erases generic types at runtime; reified preserves the concrete type in an inline function",
                    "Because generics only exist at compile time in Java, not Kotlin",
                    "Because reified converts T to a String automatically",
                ),
                correctIndex = 1,
                explanation = "Generic type information is erased on the JVM; reified (combined with inline) keeps the actual type available at the call site.",
            ),
        ),
        tutorFocus = "Keep variance intuitive with the producer/consumer framing rather than jargon first. Exercise: write a generic Stack<T> with push/pop, then a generic firstOrDefault.",
    ),
    CurriculumTopic(
        id = "visibility-modifiers",
        title = "Visibility Modifiers",
        category = "Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Generics",
            recapText = "<T> gives type-safe reuse; out produces, in consumes; reified (inline) reads the type at runtime.",
            quickCheckQuestion = "What does out T mean? Why reified?",
            quickCheckAnswer = "covariant / producer; generics are erased at runtime.",
        ),
        explain = "Kotlin has four visibility modifiers:\n\n" +
            "- public (default) — visible everywhere.\n" +
            "- private — visible only within the file (top-level) or the class.\n" +
            "- protected — visible in the class and its subclasses (not for top-level).\n" +
            "- internal — visible everywhere within the same module.\n\n" +
            "internal is distinctive: it scopes visibility to a compilation module, which is great for library boundaries. Default-public means you should consciously restrict what you expose.",
        example = """
            |class BankAccount(private var balance: Double) {
            |    fun deposit(amount: Int) { balance += amount }     // public API
            |    private fun audit() { /* internal detail */ }      // hidden from outside
            |    fun currentBalance(): Double = balance
            |}
            |
            |internal fun moduleHelper() = "only visible in this module"
            |
            |fun main() {
            |    val acc = BankAccount(100.0)
            |    acc.deposit(50)
            |    println(acc.currentBalance())     // 150.0
            |    // acc.balance                    // won't compile — private
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Default is public — explicitly narrow visibility for encapsulation.",
            "internal is per-module and has no direct Java equivalent.",
            "protected isn't available for top-level declarations.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "visibility-modifiers-q1",
                topicId = "visibility-modifiers",
                question = "What's the default visibility in Kotlin?",
                options = listOf("private", "internal", "public", "protected"),
                correctIndex = 2,
                explanation = "Declarations are public by default unless narrowed explicitly.",
            ),
            QuizQuestion(
                id = "visibility-modifiers-q2",
                topicId = "visibility-modifiers",
                question = "What does internal scope visibility to?",
                options = listOf("The current file", "The same module", "The same package only", "The same class hierarchy"),
                correctIndex = 1,
                explanation = "internal makes a declaration visible anywhere within the same compilation module.",
            ),
            QuizQuestion(
                id = "visibility-modifiers-q3",
                topicId = "visibility-modifiers",
                question = "Where is a protected member visible?",
                options = listOf(
                    "Everywhere in the same module",
                    "The declaring class and its subclasses",
                    "Only inside the same file",
                    "Only from Java callers",
                ),
                correctIndex = 1,
                explanation = "protected members are visible to the declaring class and any subclasses.",
            ),
        ),
        tutorFocus = "Emphasize \"public by default → restrict deliberately.\" Highlight internal as the module-boundary tool. Exercise: take a class exposing internal fields and tighten it with private, exposing only a clean public API.",
    ),
)
