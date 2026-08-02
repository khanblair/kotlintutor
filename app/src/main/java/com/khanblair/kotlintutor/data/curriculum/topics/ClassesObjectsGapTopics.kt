package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val classesObjectsGapTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "creating-instances",
        title = "Creating Instances",
        category = "Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Collection-specific Operations",
            recapText = "Beyond the generic Iterable functions, List/Set/Map each expose operations specific to their shape — indexing and binarySearch on List, union/intersect/subtract on Set, getOrDefault and mapValues on Map.",
            quickCheckQuestion = "Name one operation that's specific to Set rather than available on any Iterable.",
            quickCheckAnswer = "A set-algebra operation like union, intersect, or subtract.",
        ),
        explain = "You create an instance of a class by calling its name like a function: ClassName(args) — there is no new keyword in Kotlin. That call invokes the class's primary constructor directly, running any parameter defaults, property initializers, and init blocks along the way. Any concrete (non-abstract) class can be instantiated this way, regardless of whether it's open. Being open only controls whether other classes may extend it — it has no bearing on whether you can create instances of it directly. Classes are final by default, so unless a class is explicitly marked open, no subclass — and therefore no instance of a subclass — can exist at all.",
        example = """
            |class Vehicle(val brand: String)              // final by default — can't be subclassed
            |
            |open class Animal(val name: String)           // explicitly open — subclassing is allowed
            |class Dog(name: String) : Animal(name)
            |
            |fun main() {
            |    val v = Vehicle("Toyota")                 // ClassName(args) invokes the primary constructor — no 'new'
            |    println(v.brand)
            |
            |    val d = Dog("Rex")                        // Dog's own instance; its constructor delegates to Animal's
            |    println(d.name)
            |
            |    // class Bike : Vehicle()                 // compile error — Vehicle isn't open
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Instantiate with ClassName(args) — Kotlin has no new keyword.",
            "Calling ClassName(...) invokes the primary constructor, running property initializers and init blocks.",
            "Classes are final by default; mark a class open to allow it to be subclassed. This is unrelated to instantiating the class itself, which works either way as long as it isn't abstract.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "creating-instances-q1",
                topicId = "creating-instances",
                question = "How do you create an instance of a class named Widget in Kotlin?",
                options = listOf("new Widget()", "Widget.new()", "Widget()", "Widget.create()"),
                correctIndex = 2,
                explanation = "Kotlin has no new keyword — calling the class name like a function, Widget(), constructs an instance via the primary constructor.",
            ),
            QuizQuestion(
                id = "creating-instances-q2",
                topicId = "creating-instances",
                question = "By default, can other classes subclass a Kotlin class you just wrote?",
                options = listOf(
                    "Yes, any class can be subclassed unless marked final",
                    "No — classes are final by default and must be marked open to allow subclassing",
                    "Only if the class has a companion object",
                    "Only abstract classes can be subclassed",
                ),
                correctIndex = 1,
                explanation = "Kotlin classes are final by default, the opposite of Java. You must explicitly mark a class open for it to be subclassed.",
            ),
            QuizQuestion(
                id = "creating-instances-q3",
                topicId = "creating-instances",
                question = "Does marking a class open change whether you can instantiate it directly with ClassName(args)?",
                options = listOf(
                    "Yes — only open classes can be instantiated",
                    "No — open only controls whether the class can be subclassed; instantiation works the same either way",
                    "Yes — open classes require a factory function instead",
                    "No — but open classes require the new keyword",
                ),
                correctIndex = 1,
                explanation = "open governs subclassing, not instantiation. Any concrete class, open or not, is created the same way: ClassName(args).",
            ),
        ),
        tutorFocus = "Reinforce that 'no new keyword' and 'final by default' are two separate facts learners often conflate. Exercise: write a final class and an open class, instantiate both, then subclass only the open one and instantiate the subclass too.",
    ),
    CurriculumTopic(
        id = "class-members",
        title = "Class Members",
        category = "Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Creating Instances",
            recapText = "ClassName(args) invokes the primary constructor directly — no new keyword. Classes are final by default; open only controls subclassing, not instantiation.",
            quickCheckQuestion = "How do you create an instance of a class named Widget?",
            quickCheckAnswer = "Widget(args) — call it like a function.",
        ),
        explain = "A class body can hold several distinct kinds of members, and it helps to have a map of them before going deeper into each. Properties (val/var) hold state. Methods (fun) define behavior. Constructors — the primary one in the header, plus optional secondary ones in the body — set up new instances. Nested and inner classes let you scope a helper type inside another class. Object declarations create singletons as members. A companion object holds members tied to the class itself rather than to any instance, similar to Java statics. Every class you write is some combination of these pieces.",
        example = """
            |class Library(val name: String) {                  // primary constructor + property
            |
            |    val books = mutableListOf<String>()             // property
            |
            |    constructor(name: String, initial: List<String>) : this(name) {  // secondary constructor
            |        books.addAll(initial)
            |    }
            |
            |    fun addBook(title: String) {                     // method
            |        books.add(title)
            |    }
            |
            |    class Catalog(val entries: Int)                  // nested class
            |
            |    inner class Card(val bookTitle: String) {         // inner class — reaches outer's 'name'
            |        fun summary(): String = "${'$'}bookTitle @ ${'$'}name"
            |    }
            |
            |    object Defaults {                                 // object declaration (member)
            |        const val MAX_BOOKS = 500
            |    }
            |
            |    companion object {                                // companion object
            |        fun empty() = Library("Untitled")
            |    }
            |}
            |
            |fun main() {
            |    val lib = Library("City Library", listOf("Dune"))
            |    lib.addBook("Foundation")
            |    println(lib.books)                                // [Dune, Foundation]
            |    println(lib.Card("Dune").summary())                // Dune @ City Library
            |    println(Library.empty().name)                      // Untitled — called on the class, via companion
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "A class body can mix properties, methods, constructors, nested/inner classes, object declarations, and a companion object.",
            "This is an overview — each of these member kinds (constructors in depth, nested vs inner, etc.) gets its own dedicated topic next.",
            "Not every class needs every kind of member; most classes use only a handful of these.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "class-members-q1",
                topicId = "class-members",
                question = "Which of these can all appear inside a single class body?",
                options = listOf(
                    "Only properties and methods",
                    "Properties, methods, constructors, nested/inner classes, object declarations, and a companion object",
                    "Only a primary constructor and properties",
                    "Only methods and a companion object",
                ),
                correctIndex = 1,
                explanation = "A Kotlin class body can combine all of these member kinds — properties, methods, constructors, nested/inner classes, object declarations, and a companion object.",
            ),
            QuizQuestion(
                id = "class-members-q2",
                topicId = "class-members",
                question = "What is a companion object, at a high level?",
                options = listOf(
                    "A member tied to the class itself rather than any particular instance",
                    "A required part of every class",
                    "A synonym for a secondary constructor",
                    "A class that can only be nested, never top-level",
                ),
                correctIndex = 0,
                explanation = "A companion object holds members that belong to the class as a whole, similar to Java's static members, rather than to any one instance.",
            ),
            QuizQuestion(
                id = "class-members-q3",
                topicId = "class-members",
                question = "Which member type is scoped inside another class purely for organization, without necessarily needing an instance of the outer class?",
                options = listOf("A method", "A property", "A nested class", "A primary constructor"),
                correctIndex = 2,
                explanation = "A nested class groups a helper type under its outer class's name for organization — the next topic covers exactly how it relates (or doesn't) to an outer instance.",
            ),
        ),
        tutorFocus = "Treat this as a map, not a deep dive — the goal is recognition of the anatomy, since constructors, nested/inner classes, and delegation each get their own topic. Exercise: given a class skeleton, have the learner label each member with its kind.",
    ),
    CurriculumTopic(
        id = "constructors",
        title = "Constructors",
        category = "Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Class Members",
            recapText = "A class body can hold properties, methods, constructors, nested/inner classes, object declarations, and a companion object — the pieces that make up a class's anatomy.",
            quickCheckQuestion = "Name three kinds of members a class body can contain.",
            quickCheckAnswer = "Any three of: properties, methods, constructors, nested/inner classes, object declarations, companion object.",
        ),
        explain = "The primary constructor lives in the class header — class Widget(val name: String) — and val/var parameters there automatically become properties. A class may also declare secondary constructors: constructor(...) blocks in the class body. If a class has a primary constructor, every secondary constructor must delegate to it, either directly or via another secondary constructor, using : this(...). Execution order matters: property initializers and init blocks run top to bottom, in the order they're declared in the class body, as part of the primary constructor's execution. Only after all of that completes does a secondary constructor's own body run.",
        example = """
            |class Widget(val name: String) {
            |    val a = println("1: property init (a)").let { "a" }
            |
            |    init {
            |        println("2: init block — name=${'$'}name")
            |    }
            |
            |    val b = println("3: property init (b)").let { "b" }
            |
            |    constructor(name: String, extra: Int) : this(name) {
            |        println("4: secondary constructor body, extra=${'$'}extra")
            |    }
            |}
            |
            |fun main() {
            |    Widget("gadget", 5)
            |    // Output, in order:
            |    // 1: property init (a)
            |    // 2: init block — name=gadget
            |    // 3: property init (b)
            |    // 4: secondary constructor body, extra=5
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "A secondary constructor must delegate to the primary constructor, directly or through another secondary constructor, via : this(...).",
            "Property initializers and init blocks run top to bottom, interleaved in declaration order, as part of primary-constructor execution.",
            "All property initializers and init blocks finish running before any secondary constructor body executes.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "constructors-q1",
                topicId = "constructors",
                question = "If a class has a primary constructor, what must every secondary constructor do?",
                options = listOf(
                    "Nothing special — it can do whatever it wants",
                    "Delegate to the primary constructor, directly or indirectly, via : this(...)",
                    "Redeclare all the primary constructor's parameters",
                    "Be marked with the open keyword",
                ),
                correctIndex = 1,
                explanation = "Every secondary constructor must delegate to the primary constructor — directly, or indirectly through another secondary constructor — using : this(...).",
            ),
            QuizQuestion(
                id = "constructors-q2",
                topicId = "constructors",
                question = "In what order do property initializers and init blocks run?",
                options = listOf(
                    "All init blocks first, then all property initializers",
                    "All property initializers first, then all init blocks",
                    "Top to bottom, in the order they're declared in the class body",
                    "In reverse declaration order",
                ),
                correctIndex = 2,
                explanation = "Property initializers and init blocks run top to bottom, interleaved exactly in the order they appear in the class body.",
            ),
            QuizQuestion(
                id = "constructors-q3",
                topicId = "constructors",
                question = "When does a secondary constructor's own body execute?",
                options = listOf(
                    "Before any property initializers run",
                    "Interleaved with the init blocks, in declaration order",
                    "After all property initializers and init blocks have already run",
                    "Only if the primary constructor throws an exception",
                ),
                correctIndex = 2,
                explanation = "Because a secondary constructor must delegate to the primary constructor first, all property initializers and init blocks complete before the secondary constructor's body runs.",
            ),
        ),
        tutorFocus = "Execution order is the trap here — walk through the print-order example line by line and have the learner predict the output before running it. Exercise: add a second secondary constructor that delegates through the first one via this(...), and trace the full call chain.",
    ),
    CurriculumTopic(
        id = "nested-inner-classes",
        title = "Nested & Inner Classes",
        category = "Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Constructors",
            recapText = "Secondary constructors must delegate to the primary via : this(...); property initializers and init blocks run top to bottom and finish before any secondary constructor body runs.",
            quickCheckQuestion = "What must every secondary constructor do, and when does its body run relative to init blocks?",
            quickCheckAnswer = "Delegate to the primary constructor via : this(...); its body runs after all init blocks and property initializers.",
        ),
        explain = "A plain nested class — declared with just class inside another class, no inner keyword — has no reference to an instance of the outer class. It behaves like a top-level class that just happens to be scoped by name under its outer class, and you create it without needing an outer instance: Outer.Nested(args). An inner class, declared with the inner modifier, is different: it holds an implicit reference to the specific outer instance that created it, accessible explicitly via this@Outer, and it can freely read the outer instance's members. Because of that reference, an inner class can only be instantiated through an outer instance — outer.Inner(args) — never Outer.Inner(args).",
        example = """
            |class Outer(val label: String) {
            |    val value = 42
            |
            |    class Nested(val info: String)                     // no reference to an Outer instance
            |
            |    inner class Inner(val detail: String) {              // holds an implicit outer reference
            |        fun describe(): String =
            |            "${'$'}{this@Outer.label}: ${'$'}detail (value=${'$'}value)"
            |    }
            |}
            |
            |fun main() {
            |    val nested = Outer.Nested("standalone")             // scoped by name, no Outer instance needed
            |    println(nested.info)
            |
            |    val outer = Outer("config")
            |    val inner = outer.Inner("detail-a")                  // must go through an outer instance
            |    println(inner.describe())                            // config: detail-a (value=42)
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "A plain nested class (no inner) has no link to an outer instance — create it via Outer.Nested(...), with no outer instance required.",
            "An inner class holds an implicit reference to its enclosing instance, reachable via this@Outer, and can access outer members directly.",
            "You can only construct an inner class through an outer instance, outer.Inner(...) — never Outer.Inner(...).",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "nested-inner-classes-q1",
                topicId = "nested-inner-classes",
                question = "How do you create an instance of a plain nested class (no inner keyword)?",
                options = listOf(
                    "outer.Nested(args), through an outer instance",
                    "Outer.Nested(args), with no outer instance needed",
                    "Nested(args) as a fully top-level call",
                    "new Outer.Nested(args)",
                ),
                correctIndex = 1,
                explanation = "A plain nested class has no link to an outer instance, so it's created directly on the outer class's name: Outer.Nested(args).",
            ),
            QuizQuestion(
                id = "nested-inner-classes-q2",
                topicId = "nested-inner-classes",
                question = "Inside an inner class, how do you explicitly refer to the enclosing Outer instance?",
                options = listOf("outer.this", "this@Outer", "Outer.this", "super@Outer"),
                correctIndex = 1,
                explanation = "this@Outer is the qualified this syntax for referring to the enclosing instance from within an inner class.",
            ),
            QuizQuestion(
                id = "nested-inner-classes-q3",
                topicId = "nested-inner-classes",
                question = "Can you instantiate an inner class without first having an instance of the outer class?",
                options = listOf(
                    "Yes, exactly like a plain nested class",
                    "No — it must be created through an outer instance, e.g. outer.Inner(...)",
                    "Yes, but only from within a companion object",
                    "No — inner classes can never be instantiated",
                ),
                correctIndex = 1,
                explanation = "Because an inner class holds a reference to its enclosing instance, it can only be created through one: outer.Inner(...).",
            ),
        ),
        tutorFocus = "Contrast the two side by side — same syntax minus one keyword, very different capabilities. Exercise: turn a plain nested class into an inner class and have the learner fix the now-broken instantiation call.",
    ),
    CurriculumTopic(
        id = "abstract-class",
        title = "Abstract Class",
        category = "Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Nested & Inner Classes",
            recapText = "A plain nested class has no reference to an outer instance; an inner class does, reachable via this@Outer, and can only be created through an outer instance (outer.Inner()).",
            quickCheckQuestion = "Can you instantiate an inner class without an outer instance?",
            quickCheckAnswer = "No — you need outer.Inner(...).",
        ),
        explain = "An abstract class is declared with the abstract modifier and can't be instantiated directly — only its concrete subclasses can be. It can mix abstract members (no body, signature only, must be overridden by subclasses) with concrete members that provide a default implementation subclasses inherit as-is or choose to override. abstract classes are implicitly open, since a non-open abstract class would be unusable. Compared to an interface, an abstract class can hold constructor parameters and stored state (fields), while a class can extend only one abstract class but implement many interfaces — so reach for an abstract class when subclasses share real state and construction logic, and an interface when you need a contract multiple unrelated classes can adopt.",
        example = """
            |abstract class Employee(val name: String) {
            |    abstract fun monthlySalary(): Double               // no body — every subclass must override it
            |
            |    fun describe(): String = "${'$'}name earns ${'$'}{monthlySalary()}"   // concrete, inherited as-is
            |}
            |
            |class Manager(name: String, private val base: Double, private val bonus: Double) : Employee(name) {
            |    override fun monthlySalary(): Double = base + bonus
            |}
            |
            |fun main() {
            |    // val e = Employee("Ada")                          // compile error — abstract class
            |    val m = Manager("Ada", 6000.0, 500.0)
            |    println(m.describe())                               // Ada earns 6500.0
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "An abstract class can't be instantiated directly — only its concrete subclasses can.",
            "Abstract members have no body and must be overridden; concrete members provide a default implementation subclasses can inherit or override.",
            "abstract classes are implicitly open, and — unlike an interface — can hold constructor parameters and stored state.",
            "A class can extend only one abstract class but implement many interfaces; pick whichever matches how much shared state and construction logic the hierarchy needs.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "abstract-class-q1",
                topicId = "abstract-class",
                question = "Can you write val e = Employee(\"Ada\") if Employee is declared as abstract class Employee(...)?",
                options = listOf(
                    "Yes, abstract classes can always be instantiated",
                    "No — abstract classes can't be instantiated directly",
                    "Only if Employee has no abstract members",
                    "Only inside the same file as Employee",
                ),
                correctIndex = 1,
                explanation = "An abstract class can never be instantiated directly, regardless of how many (or few) abstract members it declares — only concrete subclasses can be.",
            ),
            QuizQuestion(
                id = "abstract-class-q2",
                topicId = "abstract-class",
                question = "How many abstract classes can a single class extend, compared to interfaces it can implement?",
                options = listOf(
                    "Many abstract classes, one interface",
                    "One abstract class, many interfaces",
                    "Unlimited of both",
                    "Exactly one of each, never more",
                ),
                correctIndex = 1,
                explanation = "Kotlin allows single class (including abstract class) inheritance, but a class can implement multiple interfaces.",
            ),
            QuizQuestion(
                id = "abstract-class-q3",
                topicId = "abstract-class",
                question = "What's the key capability an abstract class has that an interface doesn't?",
                options = listOf(
                    "Default method implementations",
                    "Constructor parameters and stored instance state",
                    "The ability to be extended at all",
                    "The ability to declare functions",
                ),
                correctIndex = 1,
                explanation = "An abstract class can declare a constructor and hold real backing-field state; an interface cannot hold state the way a class can.",
            ),
        ),
        tutorFocus = "Drive the abstract-class-vs-interface decision home with the state/single-inheritance angle rather than syntax alone. Exercise: model a Shape abstract class with constructor state (e.g. a color) plus an abstract area(), then implement two concrete shapes.",
    ),
    CurriculumTopic(
        id = "interfaces",
        title = "Interfaces",
        category = "Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Abstract Class",
            recapText = "abstract class can't be instantiated directly; abstract members have no body and must be overridden; a class extends only one abstract class but can implement many interfaces.",
            quickCheckQuestion = "How many abstract classes can a class extend, versus interfaces it can implement?",
            quickCheckAnswer = "One abstract class; many interfaces.",
        ),
        explain = "An interface, declared with interface, can declare abstract members and also provide default method implementations that implementing classes inherit unless they override them. Unlike a class, an interface holds no backing-field state: a property in an interface can't have an initializer or a backing field — it must be either abstract (no value) or computed via a custom getter. A class can implement multiple interfaces, and when two of them provide a default member with the same signature, the implementing class must override it itself; inside that override, super<InterfaceName>.member() lets you call one specific interface's version explicitly to resolve the conflict.",
        example = """
            |interface Named {
            |    val name: String                                    // abstract property — no backing field allowed
            |    fun greet(): String = "Hello, ${'$'}name"            // default implementation
            |}
            |
            |interface Aged {
            |    val age: Int
            |    fun greet(): String = "I am ${'$'}age years old"     // same signature as Named.greet — a diamond
            |}
            |
            |class Person(override val name: String, override val age: Int) : Named, Aged {
            |    override fun greet(): String =
            |        "${'$'}{super<Named>.greet()} and ${'$'}{super<Aged>.greet()}"   // resolve explicitly
            |}
            |
            |fun main() {
            |    println(Person("Ada", 36).greet())
            |    // Hello, Ada and I am 36 years old
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Interface properties can't have backing fields — only abstract (no initializer) or computed via a custom getter.",
            "Interfaces can mix abstract members with default method implementations.",
            "A class can implement multiple interfaces; if two provide the same default member, the class must override it, optionally picking a specific one with super<InterfaceName>.member().",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "interfaces-q1",
                topicId = "interfaces",
                question = "Can an interface property be written as val name: String = \"default\" with a stored value?",
                options = listOf(
                    "Yes, interface properties work exactly like class properties",
                    "No — interface properties can't have backing fields; only abstract or with a custom getter",
                    "Only if the interface has no other members",
                    "Only for var properties, not val",
                ),
                correctIndex = 1,
                explanation = "Interfaces can't hold backing-field state. A property must be left abstract (no initializer) or defined with a custom get() instead.",
            ),
            QuizQuestion(
                id = "interfaces-q2",
                topicId = "interfaces",
                question = "If two interfaces a class implements both provide a default greet() with the same signature, what must the class do?",
                options = listOf(
                    "Nothing — Kotlin picks one automatically",
                    "Override greet() itself, and it may call a specific one via super<InterfaceName>.greet()",
                    "It's a compile error with no fix",
                    "Rename one of the interfaces' methods",
                ),
                correctIndex = 1,
                explanation = "A conflicting default member forces the implementing class to override it explicitly; super<InterfaceName>.member() lets it delegate to a specific interface's version.",
            ),
            QuizQuestion(
                id = "interfaces-q3",
                topicId = "interfaces",
                question = "How many interfaces can a single class implement?",
                options = listOf("Exactly one", "At most two", "Multiple — there's no limit of one", "None, unless it's abstract"),
                correctIndex = 2,
                explanation = "Unlike class inheritance, a class can implement any number of interfaces.",
            ),
        ),
        tutorFocus = "Make the no-backing-field rule concrete by contrasting it with a class property, and walk through the diamond-conflict example step by step. Exercise: create two interfaces with a colliding default method and resolve it with super<InterfaceName>.",
    ),
    CurriculumTopic(
        id = "property-delegates",
        title = "Property Delegates",
        category = "Classes & Objects",
        recap = Recap(
            previousTopicTitle = "Interfaces",
            recapText = "Interface properties can't have backing fields (abstract or custom-getter only); interfaces can provide default methods; a class can implement multiple interfaces and resolve conflicts with super<InterfaceName>.member().",
            quickCheckQuestion = "How do you resolve two interfaces providing the same default method?",
            quickCheckAnswer = "Override it in the implementing class, optionally calling a specific one via super<InterfaceName>.method().",
        ),
        explain = "The by keyword delegates a property's accessors to another object instead of you writing custom get()/set() by hand. lazy { } is the most common built-in delegate: it computes the property's value once, the first time it's accessed, caches the result, and by default is thread-safe (synchronized), so concurrent first-access from multiple threads still only runs the initializer once. Delegates.observable { } (from kotlin.properties) wraps a var and invokes a callback with the property name plus its old and new value every time it's reassigned. Under the hood, by works because these delegates implement operator functions: getValue() (matching the ReadOnlyProperty interface) for val, and additionally setValue() (matching ReadWriteProperty) for var — a custom delegate is any object that implements those same operators.",
        example = """
            |import kotlin.properties.Delegates
            |
            |class Config {
            |    val expensiveValue: String by lazy {                // computed once, on first access
            |        println("computing...")
            |        "loaded"
            |    }
            |
            |    var name: String by Delegates.observable("initial") { _, old, new ->
            |        println("name changed from ${'$'}old to ${'$'}new")
            |    }
            |}
            |
            |fun main() {
            |    val c = Config()
            |    println(c.expensiveValue)      // prints "computing..." then "loaded"
            |    println(c.expensiveValue)      // cached — no second "computing..."
            |
            |    c.name = "Ada"                 // triggers the observable callback
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "by delegates a property's get (and set) to another object, instead of writing custom accessors by hand.",
            "lazy { } computes its value once, on first access, and caches it — thread-safe (synchronized) by default.",
            "Delegates.observable { } (kotlin.properties) invokes a callback with the old and new value every time the property is reassigned.",
            "A delegate object implements getValue() (the ReadOnlyProperty shape) and, for var, also setValue() (ReadWriteProperty) — the operator functions by calls under the hood.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "property-delegates-q1",
                topicId = "property-delegates",
                question = "What does val x by lazy { ... } guarantee about the initializer block?",
                options = listOf(
                    "It runs every time x is accessed",
                    "It runs once, on first access, and the result is cached — thread-safe by default",
                    "It runs immediately when the containing object is constructed",
                    "It never runs unless explicitly triggered",
                ),
                correctIndex = 1,
                explanation = "lazy { } defers computation until the first access, then caches the result for all subsequent accesses; by default it's also thread-safe.",
            ),
            QuizQuestion(
                id = "property-delegates-q2",
                topicId = "property-delegates",
                question = "What does Delegates.observable { } do?",
                options = listOf(
                    "Prevents the property from ever changing",
                    "Invokes a callback with the old and new value each time the property is reassigned",
                    "Computes the property once, like lazy",
                    "Makes the property visible only within the same module",
                ),
                correctIndex = 1,
                explanation = "Delegates.observable wraps a var so every reassignment triggers a callback receiving the property, the old value, and the new value.",
            ),
            QuizQuestion(
                id = "property-delegates-q3",
                topicId = "property-delegates",
                question = "Which operator function(s) must an object implement to be usable as a delegate for a var property via by?",
                options = listOf(
                    "Only getValue()",
                    "getValue() and setValue()",
                    "Only setValue()",
                    "No functions — by works on any object automatically",
                ),
                correctIndex = 1,
                explanation = "A var property's delegate needs both getValue() (read) and setValue() (write) — matching the ReadWriteProperty shape — while a val only needs getValue().",
            ),
        ),
        tutorFocus = "Contrast lazy (val, computed once) against observable (var, runs on every change) so the two don't blur together. Exercise: add a Delegates.vetoable or a second observable property and predict when the callback fires.",
    ),
)
