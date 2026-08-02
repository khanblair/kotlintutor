package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val functionsGapTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "local-functions",
        title = "Local Functions",
        category = "Functions",
        recap = Recap(
            previousTopicTitle = "Catching Exceptions",
            recapText = "try/catch catches exceptions by type — most specific catch blocks should come first — and finally always runs, whether or not an exception was thrown.",
            quickCheckQuestion = "Does finally run even if a catch block itself throws?",
            quickCheckAnswer = "Yes — finally always runs.",
        ),
        explain = "A local function is a function declared inside the body of another function. Like a lambda, it closes over the enclosing function's parameters and local val/var declarations (a closure) — it can read them, and if it captures a var, it can even reassign it. Local functions exist to avoid duplicating logic that's only meaningful inside one outer function, without polluting the file with a private top-level helper. Because they're scoped to the enclosing function, they're invisible outside it — nothing outside that function can call them.",
        example = """
            |fun sumPositives(numbers: List<Int>): Int {
            |    var total = 0
            |
            |    fun addIfPositive(n: Int) {      // local function — closes over 'total'
            |        if (n > 0) total += n
            |    }
            |
            |    numbers.forEach { addIfPositive(it) }
            |    return total
            |}
            |
            |fun main() {
            |    println(sumPositives(listOf(-3, 5, -1, 8, 2)))   // 15 — only positives added
            |}
            |
            |// addIfPositive(5) called from outside sumPositives would be a compile error —
            |// it isn't visible there.
        """.trimMargin(),
        keyPoints = listOf(
            "Local functions close over the enclosing function's parameters and local vals/vars, just like lambdas do — including mutating a captured var.",
            "They're only visible inside the function that declares them; nothing outside can call them.",
            "Use them to extract logic that's only relevant to one outer function, instead of adding a private top-level function that clutters the file's namespace.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "local-functions-q1",
                topicId = "local-functions",
                question = "What can a local function access that a private top-level function cannot?",
                options = listOf(
                    "Nothing, they behave identically",
                    "The enclosing function's parameters and local variables directly, via closure",
                    "Private members of any class in the file",
                    "Global variables only",
                ),
                correctIndex = 1,
                explanation = "Local functions are closures over the function they're declared in, so they can read (and, for var, reassign) its parameters and locals without those being passed in explicitly.",
            ),
            QuizQuestion(
                id = "local-functions-q2",
                topicId = "local-functions",
                question = "Where can a local function be called from?",
                options = listOf(
                    "Anywhere in the same file",
                    "Only from within the function that declares it",
                    "Only from subclasses of the enclosing class",
                    "From any function in the same package",
                ),
                correctIndex = 1,
                explanation = "A local function's scope is limited to the body of the function that declares it — it isn't visible or callable from outside.",
            ),
            QuizQuestion(
                id = "local-functions-q3",
                topicId = "local-functions",
                question = "Why reach for a local function instead of a private top-level function for a helper?",
                options = listOf(
                    "Local functions run faster at runtime",
                    "When the helper is only relevant to one function and needs to close over its locals",
                    "Local functions are the only way to write recursion in Kotlin",
                    "Top-level functions can't take parameters",
                ),
                correctIndex = 1,
                explanation = "A local function keeps single-use logic scoped to where it's needed and gets closure access to the enclosing state for free — a top-level function would need that state passed in explicitly.",
            ),
        ),
        tutorFocus = "Emphasize closures — addIfPositive sees total without it being a parameter, because it's captured from the enclosing scope. Contrast with extracting a private top-level function, which would need total passed in and returned back out. Exercise: write a local function that closes over and mutates an outer var (a running counter or accumulator).",
    ),
    CurriculumTopic(
        id = "member-functions",
        title = "Member Functions",
        category = "Functions",
        recap = Recap(
            previousTopicTitle = "Local Functions",
            recapText = "Local functions live inside another function's body, close over its parameters and locals, and are invisible outside it — ideal for single-use helpers.",
            quickCheckQuestion = "Can a local function be called from outside the function that declares it?",
            quickCheckAnswer = "No.",
        ),
        explain = "A member function (commonly called a method) is a function declared inside a class body. It's called on an instance using dot-notation: instance.function(). Inside a member function, this implicitly refers to the instance it was called on, so the function has direct access to the class's properties and other member functions without qualifying them. This is the key contrast with the other two kinds of functions you've seen: top-level functions have no receiver and are just called by name, while extension functions are attached to a type from outside and — unlike members — cannot access that type's private members.",
        example = """
            |class Rectangle(val width: Double, val height: Double) {
            |    fun area(): Double = width * height   // member function — reads width/height via implicit this
            |
            |    fun describe(): String =
            |        "Rectangle ${'$'}width x ${'$'}height, area = ${'$'}{area()}"   // calls area() unqualified
            |}
            |
            |fun main() {
            |    val r = Rectangle(3.0, 4.0)
            |    println(r.area())        // 12.0 — called via dot-notation
            |    println(r.describe())    // Rectangle 3.0 x 4.0, area = 12.0
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Member functions are declared inside a class body and implicitly operate on this instance.",
            "Call them with instance.function() — Kotlin's standard dot-notation.",
            "Unlike extension functions, member functions can access the class's private properties and functions directly.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "member-functions-q1",
                topicId = "member-functions",
                question = "Where are member functions declared?",
                options = listOf(
                    "At the top level of a file",
                    "Inside a class body",
                    "Inside another function",
                    "Only inside a companion object",
                ),
                correctIndex = 1,
                explanation = "Member functions (methods) live inside a class body and are called on instances of that class.",
            ),
            QuizQuestion(
                id = "member-functions-q2",
                topicId = "member-functions",
                question = "What can a member function access that an extension function on the same type cannot?",
                options = listOf(
                    "Nothing, they're identical",
                    "The class's private members",
                    "The this keyword",
                    "Other classes' public members",
                ),
                correctIndex = 1,
                explanation = "Extension functions are resolved statically from outside the class and can't reach its private members; true member functions can.",
            ),
            QuizQuestion(
                id = "member-functions-q3",
                topicId = "member-functions",
                question = "How do you call a member function named area() on an instance r?",
                options = listOf("area(r)", "r.area()", "r::area()", "r->area()"),
                correctIndex = 1,
                explanation = "Member functions are invoked with dot-notation on the instance: r.area().",
            ),
        ),
        tutorFocus = "Contrast the three kinds of functions — top-level, member, extension — to place member functions correctly. The private-member-access boundary is the concrete test that separates a member from an extension. Exercise: take a top-level function that took an object as its first parameter and turn it into a member function that reads the object's properties via implicit this.",
    ),
    CurriculumTopic(
        id = "varargs",
        title = "varargs",
        category = "Functions",
        recap = Recap(
            previousTopicTitle = "Member Functions",
            recapText = "Member functions live inside a class, run with an implicit this, and — unlike extensions — can reach the class's private members.",
            quickCheckQuestion = "What can a member function access that an extension function can't?",
            quickCheckAnswer = "The class's private members.",
        ),
        explain = "The vararg modifier on a parameter lets a function accept a variable number of arguments of that type. Inside the function, a vararg parameter behaves as an array — vararg numbers: Int is exposed as an IntArray, vararg items: String as an Array<String>, and so on. To pass an already-built array where individual vararg arguments are expected, use the spread operator: *array. A function may declare only one vararg parameter, and if it isn't the last parameter, any parameters that follow it must be supplied as named arguments.",
        example = """
            |fun sumAll(vararg numbers: Int): Int {
            |    var total = 0
            |    for (n in numbers) total += n   // numbers is an IntArray inside the function
            |    return total
            |}
            |
            |fun labeled(vararg items: String, separator: String = ", "): String =
            |    items.joinToString(separator)
            |
            |fun main() {
            |    println(sumAll(1, 2, 3))                     // 6 — passed individually
            |    val data = intArrayOf(4, 5, 6)
            |    println(sumAll(*data))                       // 15 — spread an existing array
            |
            |    println(labeled("a", "b", "c"))                      // a, b, c
            |    println(labeled("a", "b", separator = " | "))        // a | b — named arg required after vararg
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Inside the function, a vararg parameter behaves as an array (IntArray for vararg x: Int, Array<String> for vararg x: String, and so on).",
            "Use the spread operator *array to pass an already-built array where individual vararg arguments are expected.",
            "Only one parameter per function may be vararg, and any parameters declared after it must be passed by name.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "varargs-q1",
                topicId = "varargs",
                question = "Inside a function, how does a vararg numbers: Int parameter behave?",
                options = listOf("Like a single Int", "Like an IntArray", "Like a List<Int>", "Like a String"),
                correctIndex = 1,
                explanation = "vararg on a primitive type is exposed inside the function as the matching primitive array — IntArray for Int, DoubleArray for Double, and so on.",
            ),
            QuizQuestion(
                id = "varargs-q2",
                topicId = "varargs",
                question = "How do you pass an existing array to a vararg parameter?",
                options = listOf(
                    "Pass it directly, no special syntax needed",
                    "Using the spread operator: *array",
                    "Wrap it with vararg(array)",
                    "It's not possible — elements must be listed individually",
                ),
                correctIndex = 1,
                explanation = "The spread operator * expands an array's elements into the vararg positions of the call.",
            ),
            QuizQuestion(
                id = "varargs-q3",
                topicId = "varargs",
                question = "Given fun tag(vararg names: String, prefix: String), how must prefix be supplied at the call site?",
                options = listOf(
                    "As the next positional argument, no special syntax needed",
                    "As a named argument: prefix = \"...\"",
                    "It can't be supplied — vararg consumes all remaining arguments",
                    "Only as the very first argument",
                ),
                correctIndex = 1,
                explanation = "When a vararg parameter isn't last, any parameters after it must be passed by name so the compiler can tell them apart from vararg elements.",
            ),
        ),
        tutorFocus = "The two details people forget: the array type inside the function, and the named-argument requirement for anything after a non-trailing vararg. Exercise: write a function with a vararg followed by a defaulted parameter, then call it once with individual arguments and once by spreading an existing array.",
    ),
    CurriculumTopic(
        id = "tail-recursive-functions",
        title = "Tail-recursive Functions",
        category = "Functions",
        recap = Recap(
            previousTopicTitle = "varargs",
            recapText = "vararg accepts any number of arguments, exposed inside the function as an array; spread an existing array with *array; only one vararg per function, and later params need named arguments.",
            quickCheckQuestion = "How do you pass an existing array where a vararg is expected?",
            quickCheckAnswer = "Spread it: *array.",
        ),
        explain = "The tailrec modifier tells the compiler a recursive function is tail-recursive, so it can be optimized. A call is in tail position when it's the very last operation the function performs — its result is returned directly, with nothing further done to it afterward (no multiplying, no concatenating, no wrapping). When every recursive call is in tail position, the compiler rewrites the function into an equivalent loop, reusing a single stack frame instead of pushing a new one per call — which means arbitrarily deep recursion no longer risks a StackOverflowError. The standard technique for making a function tail-recursive is to add an accumulator parameter that carries the running result, so the recursive call can return it directly instead of combining it with something after the call returns. If you mark a function tailrec but its recursive call isn't actually in tail position, the compiler emits a warning and leaves the function as ordinary, non-optimized recursion — it still risks overflowing the stack for deep input.",
        example = """
            |tailrec fun factorial(n: Long, accumulator: Long = 1): Long =
            |    if (n <= 1) accumulator else factorial(n - 1, accumulator * n)   // recursive call is the LAST operation
            |
            |tailrec fun sumTo(n: Long, accumulator: Long = 0): Long =
            |    if (n == 0L) accumulator else sumTo(n - 1, accumulator + n)      // also tail position
            |
            |fun main() {
            |    println(factorial(10))         // 3628800
            |    println(sumTo(1_000_000))      // 500000500000 — a normal recursive version would overflow the stack here
            |}
            |
            |// NOT tail-recursive — the multiplication happens AFTER the recursive call returns:
            |// tailrec fun factorialBad(n: Long): Long = if (n <= 1) 1 else n * factorialBad(n - 1)
            |// compiler warning: "a function is marked as tail-recursive, but no tail calls are found"
        """.trimMargin(),
        keyPoints = listOf(
            "A call is in tail position only when its return value is used directly as the function's result — no further arithmetic, concatenation, or wrapping.",
            "An accumulator parameter is the standard trick for turning \"do something with the result\" recursion into pure tail recursion.",
            "tailrec on a function that isn't actually tail-recursive produces a compiler warning and simply isn't optimized — it still risks StackOverflowError.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "tail-recursive-functions-q1",
                topicId = "tail-recursive-functions",
                question = "What must be true for a recursive call to be \"in tail position\"?",
                options = listOf(
                    "It must be the first statement in the function",
                    "It must be the last operation performed, with nothing done to its result afterward",
                    "The function must take no parameters",
                    "It must call itself with the exact same arguments every time",
                ),
                correctIndex = 1,
                explanation = "Tail position means the recursive call's result is returned directly as the function's result, with no further computation applied to it.",
            ),
            QuizQuestion(
                id = "tail-recursive-functions-q2",
                topicId = "tail-recursive-functions",
                question = "What does the compiler do with a function correctly marked tailrec?",
                options = listOf(
                    "Nothing different from an ordinary recursive function",
                    "Rewrites it into an iterative loop, avoiding a new stack frame per call",
                    "Runs it on a separate thread",
                    "Converts it into a lambda",
                ),
                correctIndex = 1,
                explanation = "The compiler transforms a valid tail-recursive function into a loop under the hood, so deep recursion doesn't grow the call stack.",
            ),
            QuizQuestion(
                id = "tail-recursive-functions-q3",
                topicId = "tail-recursive-functions",
                question = "What happens if you mark a function tailrec but its recursive call isn't actually in tail position?",
                options = listOf(
                    "A compile error — the code won't build",
                    "The compiler warns and leaves the function as ordinary, non-optimized recursion",
                    "It's silently optimized anyway",
                    "The function is automatically rewritten to use an accumulator",
                ),
                correctIndex = 1,
                explanation = "tailrec on a non-tail-recursive call is only a warning, not an error — the optimization simply doesn't apply, so stack overflow is still possible.",
            ),
        ),
        tutorFocus = "The accumulator-parameter trick is the key skill — walk through why factorial(n) = n * factorial(n-1) isn't tail-recursive (multiplication happens after the call returns) but factorial(n, acc) = factorial(n-1, acc*n) is. Exercise: convert an ordinary recursive sum function into a tailrec version using an accumulator.",
    ),
    CurriculumTopic(
        id = "anonymous-functions",
        title = "Anonymous Functions",
        category = "Functions",
        recap = Recap(
            previousTopicTitle = "Tail-recursive Functions",
            recapText = "tailrec only optimizes when the recursive call is in tail position; the compiler rewrites valid cases into a loop, and just warns (without optimizing) otherwise.",
            quickCheckQuestion = "What's the standard trick for turning result-processing recursion into tail recursion?",
            quickCheckAnswer = "Add an accumulator parameter that carries the running result.",
        ),
        explain = "An anonymous function is a function literal written with the fun keyword but no name: fun(x: Int): Int { return x * 2 }. Like a lambda, it can be assigned to a variable or passed where a function value is expected. It differs from a lambda in two ways: it can declare an explicit return type as part of its signature, and — crucially — return inside an anonymous function is a local return that exits only the anonymous function itself. This is unlike a lambda, where return performs a non-local return that exits the nearest enclosing named function (and is only legal when the higher-order function receiving the lambda is inline, such as forEach). Reach for an anonymous function when you need return to behave locally inside something like forEach without escaping the enclosing function, or when you want an explicit, self-documenting signature.",
        example = """
            |fun findFirstEven(numbers: List<Int>): Int? {
            |    numbers.forEach { n ->
            |        if (n % 2 == 0) return n   // lambda: non-local return — exits findFirstEven directly
            |    }
            |    return null
            |}
            |
            |fun findFirstEvenAnon(numbers: List<Int>): Int? {
            |    var result: Int? = null
            |    numbers.forEach(fun(n: Int) {
            |        if (n % 2 == 0) {
            |            result = n
            |            return                  // anonymous function: local return — exits only this function
            |        }
            |    })
            |    return result
            |}
            |
            |val double = fun(x: Int): Int { return x * 2 }   // assigned to a variable, explicit types
            |
            |fun main() {
            |    println(findFirstEven(listOf(1, 3, 4, 5, 6)))       // 4
            |    println(findFirstEvenAnon(listOf(1, 3, 4, 5, 6)))   // 4 — same result, different return semantics
            |    println(double(21))                                   // 42
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Anonymous functions look like named functions minus the name — fun(params): ReturnType { ... } — and can be assigned to variables or passed as arguments.",
            "return inside an anonymous function is a local return, exiting only that function; return inside a lambda is a non-local return, exiting the nearest enclosing named function (legal only when the receiving higher-order function is inline).",
            "Reach for an anonymous function when you need return to behave locally inside a loop-like call, or want an explicit signature.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "anonymous-functions-q1",
                topicId = "anonymous-functions",
                question = "What does return do inside an anonymous function passed to forEach?",
                options = listOf(
                    "Exits the anonymous function only; forEach continues to the next element",
                    "Exits the enclosing named function immediately",
                    "Causes a compile error — return isn't allowed there",
                    "Breaks out of the forEach loop entirely",
                ),
                correctIndex = 0,
                explanation = "return inside an anonymous function is a local return — it exits just that function call, so forEach keeps iterating.",
            ),
            QuizQuestion(
                id = "anonymous-functions-q2",
                topicId = "anonymous-functions",
                question = "What does return do inside a lambda passed to an inline function like forEach?",
                options = listOf(
                    "Exits the lambda only, like an anonymous function would",
                    "Performs a non-local return, exiting the nearest enclosing named function",
                    "Is always a compile error",
                    "Restarts the loop from the beginning",
                ),
                correctIndex = 1,
                explanation = "A lambda's return is non-local — it exits the enclosing named function directly, which is only legal because forEach is inline.",
            ),
            QuizQuestion(
                id = "anonymous-functions-q3",
                topicId = "anonymous-functions",
                question = "What can an anonymous function's signature include that a lambda's cannot?",
                options = listOf(
                    "Being passed as a trailing argument",
                    "An explicit return type as part of the function literal",
                    "Capturing variables from the enclosing scope",
                    "Being assigned to a variable",
                ),
                correctIndex = 1,
                explanation = "Anonymous functions use a fun(params): ReturnType header where the return type can be written explicitly; lambdas rely on inference and have no such header.",
            ),
        ),
        tutorFocus = "The local-vs-non-local return distinction is the crux — walk through the forEach example twice, once with a lambda that exits findFirstEven early, once with an anonymous function that doesn't. Exercise: given a lambda that tries to return from inside a non-inline higher-order function (a compile error), fix it by converting it to an anonymous function.",
    ),
    CurriculumTopic(
        id = "function-types",
        title = "Function Types",
        category = "Functions",
        recap = Recap(
            previousTopicTitle = "Anonymous Functions",
            recapText = "Anonymous functions can declare an explicit return type and, unlike lambdas, return inside one exits only itself rather than the enclosing function.",
            quickCheckQuestion = "Where does return exit to when used inside an anonymous function vs. a lambda?",
            quickCheckAnswer = "The anonymous function itself; a lambda's return exits the enclosing named function (non-local).",
        ),
        explain = "A function type describes the shape of a function value: (ParamTypes) -> ReturnType. For example, (Int, Int) -> Int is the type of a function that takes two Ints and returns an Int. You use function types to type a variable, parameter, or property that holds a lambda, an anonymous function, or a function reference. A nullable function type wraps the whole signature in parentheses before the ?: (() -> Unit)? — writing () -> Unit? instead would mean \"a function returning Unit?\", not \"a nullable function\". Call a nullable function type safely with ?.invoke(). To turn an existing function into a value matching its function type without wrapping it in a lambda, reference it with ::functionName.",
        example = """
            |val add: (Int, Int) -> Int = { a, b -> a + b }
            |val square: (Int) -> Int = ::squareOf
            |
            |fun squareOf(n: Int) = n * n
            |
            |fun runAndLog(x: Int, operation: (Int) -> Int): Int {
            |    val result = operation(x)
            |    println("operation(${'$'}x) = ${'$'}result")
            |    return result
            |}
            |
            |fun onComplete(callback: (() -> Unit)? = null) {
            |    println("Doing work...")
            |    callback?.invoke()   // nullable function type — safe-call to invoke it
            |}
            |
            |fun main() {
            |    println(add(2, 3))              // 5
            |    println(square(9))              // 81
            |    runAndLog(4, square)            // operation(4) = 16
            |    onComplete { println("Done!") } // "Doing work..." then "Done!"
            |    onComplete()                    // only "Doing work..." — callback is null
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "(ParamTypes) -> ReturnType is a type just like Int or String — you can store it in a val/var, pass it as a parameter, or return it.",
            "Parenthesize the whole signature before adding ? for a nullable function type: (() -> Unit)?, and invoke it safely with ?.invoke().",
            "::functionName turns an existing top-level or member function into a value matching its function type, with no lambda wrapper needed.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "function-types-q1",
                topicId = "function-types",
                question = "How do you write the type of a function taking two Ints and returning an Int?",
                options = listOf("Int, Int -> Int", "(Int, Int) -> Int", "fun(Int, Int): Int", "Function2<Int, Int>"),
                correctIndex = 1,
                explanation = "Function types are written as (ParamTypes) -> ReturnType.",
            ),
            QuizQuestion(
                id = "function-types-q2",
                topicId = "function-types",
                question = "How do you write a nullable function type for a no-arg, Unit-returning callback?",
                options = listOf("() -> Unit?", "(() -> Unit)?", "?() -> Unit", "() -> Unit | null"),
                correctIndex = 1,
                explanation = "The whole function type must be parenthesized before the ?; () -> Unit? would instead mean \"returns Unit?\", not \"the function reference itself is nullable\".",
            ),
            QuizQuestion(
                id = "function-types-q3",
                topicId = "function-types",
                question = "What does ::squareOf produce?",
                options = listOf(
                    "A call to squareOf() with no arguments",
                    "A reference to the squareOf function, usable as a value matching its function type",
                    "A new function named squareOf",
                    "A compile error outside a class",
                ),
                correctIndex = 1,
                explanation = "The :: operator creates a callable reference to an existing function, which can be assigned or passed anywhere its function type is expected.",
            ),
        ),
        tutorFocus = "Make the (() -> Unit)? vs () -> Unit? parenthesization distinction concrete — it's a common source of confusing compiler errors. Exercise: write a function that accepts an optional callback parameter with a nullable function type and safely invokes it with ?.invoke().",
    ),
)
