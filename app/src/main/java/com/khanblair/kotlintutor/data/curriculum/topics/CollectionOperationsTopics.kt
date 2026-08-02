package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val collectionOperationsTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "read-only-vs-mutable",
        title = "read-only vs mutable",
        category = "Collection Operations",
        recap = Recap(
            previousTopicTitle = "Standard (scope) Functions",
            recapText = "let/run/with/apply/also differ by it/this and object/result; apply configures, let is null-safe.",
            quickCheckQuestion = "Which return the object itself? Which configures an object?",
            quickCheckAnswer = "apply & also; apply.",
        ),
        explain = "Every collection type has a read-only interface (List, Set, Map) and a mutable interface (MutableList, MutableSet, MutableMap). The read-only interface exposes reading operations (size, get, contains) but not add/remove. This isn't the same as immutability — a read-only List variable could still point to an object modified elsewhere — but it lets you express and enforce intent, and it's the safer default for function parameters and return types.",
        example = """
            |fun total(items: List<Int>): Int = items.sum()   // read-only param — can't mutate
            |
            |fun main() {
            |    val ro: List<Int> = listOf(1, 2, 3)
            |    // ro.add(4)                                  // won't compile — no add()
            |
            |    val mut: MutableList<Int> = mutableListOf(1, 2, 3)
            |    mut.add(4)                                    // OK
            |    println(total(mut))                           // a MutableList is also a List → 10
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Read-only ≠ immutable; it just hides mutating operations.",
            "Accept List (not MutableList) as a parameter unless you must mutate it.",
            "A MutableList is a List, so it can be passed where a List is expected.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "read-only-vs-mutable-q1",
                topicId = "read-only-vs-mutable",
                question = "What operations does List lack compared to MutableList?",
                options = listOf(
                    "Reading operations like size and get",
                    "Mutating ones like add/remove",
                    "Iteration support",
                    "Nothing — they're identical",
                ),
                correctIndex = 1,
                explanation = "List exposes only read operations; MutableList adds add/remove and similar mutating methods.",
            ),
            QuizQuestion(
                id = "read-only-vs-mutable-q2",
                topicId = "read-only-vs-mutable",
                question = "Is a read-only List guaranteed to never change?",
                options = listOf(
                    "Yes, always",
                    "No — the underlying object may be mutable and changed elsewhere",
                    "Only if declared with val",
                    "Only inside a function",
                ),
                correctIndex = 1,
                explanation = "Read-only just hides mutating methods on that reference; the underlying object could still be a MutableList changed through another reference.",
            ),
            QuizQuestion(
                id = "read-only-vs-mutable-q3",
                topicId = "read-only-vs-mutable",
                question = "Which type should a function parameter usually be?",
                options = listOf(
                    "MutableList, for flexibility",
                    "The read-only List, unless mutation is required",
                    "Array, always",
                    "It doesn't matter",
                ),
                correctIndex = 1,
                explanation = "Preferring List communicates that the function won't mutate the collection, which is the safer default.",
            ),
        ),
        tutorFocus = "Clarify \"read-only vs truly immutable.\" Probe the habit of choosing List for parameters. Exercise: given a function that takes MutableList, ask whether it should — and tighten it to List.",
    ),
    CurriculumTopic(
        id = "transformations",
        title = "Transformations",
        category = "Collection Operations",
        recap = Recap(
            previousTopicTitle = "read-only vs mutable",
            recapText = "List hides mutation, MutableList allows it; read-only ≠ immutable; prefer List for parameters.",
            quickCheckQuestion = "Is a read-only List guaranteed unchanging? Best parameter type?",
            quickCheckAnswer = "no; List.",
        ),
        explain = "Transformations produce a new collection from an existing one:\n\n" +
            "- map applies a function to each element → a new list of results.\n" +
            "- mapIndexed gives you the index too.\n" +
            "- flatMap maps each element to a collection and flattens the results.\n" +
            "- associate / associateWith build maps from elements.\n\n" +
            "These are pure: they don't modify the source.",
        example = """
            |fun main() {
            |    val nums = listOf(1, 2, 3)
            |    println(nums.map { it * it })                 // [1, 4, 9]
            |    println(nums.mapIndexed { i, n -> "${'$'}i:${'$'}n" })  // [0:1, 1:2, 2:3]
            |
            |    val words = listOf("ab", "cd")
            |    println(words.flatMap { it.toList() })        // [a, b, c, d]
            |
            |    println(nums.associateWith { it * 10 })       // {1=10, 2=20, 3=30}
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "map returns a new list; the original is untouched.",
            "Use flatMap when each element expands into multiple elements.",
            "Chained transformations each allocate a list — see Sequences for large data.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "transformations-q1",
                topicId = "transformations",
                question = "What does map return, and does it change the source?",
                options = listOf(
                    "A new list; the source is unchanged",
                    "The same list, mutated in place",
                    "A Set; the source is unchanged",
                    "Nothing — it's a terminal operation",
                ),
                correctIndex = 0,
                explanation = "map produces a new list of transformed elements and leaves the source collection untouched.",
            ),
            QuizQuestion(
                id = "transformations-q2",
                topicId = "transformations",
                question = "When would you use flatMap over map?",
                options = listOf(
                    "When you want to filter out nulls",
                    "When each element produces a collection you want flattened",
                    "When you need the index of each element",
                    "flatMap and map are interchangeable",
                ),
                correctIndex = 1,
                explanation = "flatMap flattens each element's resulting collection into a single combined list.",
            ),
            QuizQuestion(
                id = "transformations-q3",
                topicId = "transformations",
                question = "What does associateWith { … } produce?",
                options = listOf(
                    "A List of pairs",
                    "A Map from each element to the lambda's result",
                    "A Set of the lambda's results",
                    "A single aggregated value",
                ),
                correctIndex = 1,
                explanation = "associateWith builds a Map keyed by the original elements, with values from the lambda.",
            ),
        ),
        tutorFocus = "Anchor the \"returns new, source unchanged\" idea. Contrast map (same count) vs flatMap (flattened). Exercise: turn a list of names into a list of their lengths, then a map from name to length.",
    ),
    CurriculumTopic(
        id = "filtering",
        title = "Filtering",
        category = "Collection Operations",
        recap = Recap(
            previousTopicTitle = "Transformations",
            recapText = "map/flatMap/associate produce new collections without changing the source.",
            quickCheckQuestion = "Does map mutate the source? When use flatMap?",
            quickCheckAnswer = "no; when each element expands into a collection.",
        ),
        explain = "Filtering selects a subset of elements:\n\n" +
            "- filter keeps elements matching a predicate.\n" +
            "- filterNot keeps those that don't match.\n" +
            "- filterNotNull drops nulls (and refines the type to non-null).\n" +
            "- partition splits into two lists: matches and non-matches.\n" +
            "- Related predicates: any, all, none, count.",
        example = """
            |fun main() {
            |    val nums = listOf(1, 2, 3, 4, 5, 6)
            |    println(nums.filter { it % 2 == 0 })      // [2, 4, 6]
            |    println(nums.filterNot { it % 2 == 0 })   // [1, 3, 5]
            |
            |    val (evens, odds) = nums.partition { it % 2 == 0 }
            |    println("${'$'}evens / ${'$'}odds")                 // [2, 4, 6] / [1, 3, 5]
            |
            |    println(nums.any { it > 5 })              // true
            |    println(nums.all { it > 0 })              // true
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "filter returns a new list; it never mutates the source.",
            "partition returns a Pair you can destructure.",
            "Use any/all/none when you only need a boolean, not a filtered list.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "filtering-q1",
                topicId = "filtering",
                question = "What's the difference between filter and filterNot?",
                options = listOf(
                    "No difference",
                    "filter keeps matches; filterNot keeps non-matches",
                    "filterNot mutates the source, filter doesn't",
                    "filter is for lists, filterNot for sets",
                ),
                correctIndex = 1,
                explanation = "filter keeps elements matching the predicate; filterNot keeps the ones that don't match.",
            ),
            QuizQuestion(
                id = "filtering-q2",
                topicId = "filtering",
                question = "What does partition return?",
                options = listOf(
                    "A single filtered list",
                    "A Pair of (matching, non-matching) lists",
                    "A Map from Boolean to element",
                    "The count of matching elements",
                ),
                correctIndex = 1,
                explanation = "partition splits the collection into a Pair of two lists based on the predicate.",
            ),
            QuizQuestion(
                id = "filtering-q3",
                topicId = "filtering",
                question = "Which function tells you whether every element matches a condition?",
                options = listOf("any", "none", "all", "count"),
                correctIndex = 2,
                explanation = "all returns true only if every element satisfies the predicate.",
            ),
        ),
        tutorFocus = "Show destructuring partition's result. Nudge learners toward any/all when they only need a boolean. Exercise: from a list of scores, get the passing ones and check whether anyone scored above 90.",
    ),
    CurriculumTopic(
        id = "aggregate-operations",
        title = "Aggregate Operations",
        category = "Collection Operations",
        recap = Recap(
            previousTopicTitle = "Filtering",
            recapText = "filter/filterNot/partition select subsets; any/all/none return booleans.",
            quickCheckQuestion = "What does partition return? Function for 'all match'?",
            quickCheckAnswer = "a Pair of (match, non-match); all.",
        ),
        explain = "Aggregate operations collapse a collection into a single value:\n\n" +
            "- count — number of (matching) elements.\n" +
            "- sum / average / min / max (and the null-safe maxOrNull, etc.).\n" +
            "- fold — accumulate with an explicit initial value.\n" +
            "- reduce — like fold but seeds with the first element (throws if empty).\n" +
            "- groupBy — build a Map from a key to the list of elements with that key.",
        example = """
            |fun main() {
            |    val nums = listOf(1, 2, 3, 4)
            |    println(nums.sum())                       // 10
            |    println(nums.count { it > 2 })            // 2
            |    println(nums.fold(100) { acc, n -> acc + n })  // 100 + 1+2+3+4 = 110
            |    println(nums.reduce { acc, n -> acc * n })     // 1*2*3*4 = 24
            |
            |    val words = listOf("apple", "avocado", "banana")
            |    println(words.groupBy { it.first() })     // {a=[apple, avocado], b=[banana]}
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "reduce throws on an empty collection; fold is safe because it has a seed.",
            "Prefer maxOrNull/minOrNull to avoid exceptions on empty input.",
            "groupBy is the go-to for bucketing data by some key.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "aggregate-operations-q1",
                topicId = "aggregate-operations",
                question = "What's the difference between fold and reduce?",
                options = listOf(
                    "fold takes an explicit initial value; reduce uses the first element",
                    "reduce takes an explicit initial value; fold uses the first element",
                    "They're identical",
                    "fold works on Maps only",
                ),
                correctIndex = 0,
                explanation = "fold requires an explicit seed value; reduce seeds itself with the collection's first element.",
            ),
            QuizQuestion(
                id = "aggregate-operations-q2",
                topicId = "aggregate-operations",
                question = "What happens if you call reduce on an empty list?",
                options = listOf(
                    "It returns null",
                    "It returns 0",
                    "It throws an exception",
                    "It returns an empty list",
                ),
                correctIndex = 2,
                explanation = "reduce has no seed value to fall back on for an empty collection, so it throws.",
            ),
            QuizQuestion(
                id = "aggregate-operations-q3",
                topicId = "aggregate-operations",
                question = "What does groupBy { it.first() } produce for a list of words?",
                options = listOf(
                    "A List sorted alphabetically",
                    "A Map from first letter to the list of words starting with it",
                    "A Set of first letters",
                    "The count of words per starting letter",
                ),
                correctIndex = 1,
                explanation = "groupBy builds a Map keyed by the lambda's result, bucketing elements accordingly.",
            ),
        ),
        tutorFocus = "The fold vs reduce (and the empty-collection trap) is the key distinction. Exercise: compute the product of a list with fold, then group a list of words by length.",
    ),
    CurriculumTopic(
        id = "sequences",
        title = "Sequences",
        category = "Collection Operations",
        recap = Recap(
            previousTopicTitle = "Aggregate Operations",
            recapText = "sum/count/fold/reduce/groupBy collapse a collection; reduce throws on empty.",
            quickCheckQuestion = "fold vs reduce? Reduce an empty list?",
            quickCheckAnswer = "fold has a seed, reduce uses the first element; it throws.",
        ),
        explain = "A Sequence processes elements lazily and one at a time through the whole chain, instead of building an intermediate list at each step (as regular collection operations do — that's \"eager\"). For large data or long operation chains, sequences avoid allocating intermediate collections and can short-circuit (e.g. first). Create one with .asSequence() or sequenceOf(...). A terminal operation (like toList, first, sum) triggers the actual work.",
        example = """
            |fun main() {
            |    val result = (1..1_000_000).asSequence()
            |        .map { it * 2 }           // lazy — nothing runs yet
            |        .filter { it % 3 == 0 }   // still lazy
            |        .first()                  // terminal — pulls just enough to find one
            |    println(result)               // 6
            |
            |    // Eager equivalent would build two huge intermediate lists.
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Nothing runs until a terminal operation is called.",
            "Sequences shine for large inputs or long chains; for small collections, plain operations are simpler and often faster (less overhead).",
            "Each element flows through the entire chain before the next one starts.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "sequences-q1",
                topicId = "sequences",
                question = "How does a Sequence differ from an eager collection operation chain?",
                options = listOf(
                    "It's identical, just a different name",
                    "It's lazy and processes elements one at a time without intermediate lists",
                    "It can only hold Int values",
                    "It automatically runs on a background thread",
                ),
                correctIndex = 1,
                explanation = "Sequences are lazy: each element flows through the full chain before the next starts, with no intermediate collections.",
            ),
            QuizQuestion(
                id = "sequences-q2",
                topicId = "sequences",
                question = "What triggers a sequence to actually compute?",
                options = listOf(
                    "Declaring it with asSequence()",
                    "A terminal operation, e.g. toList, first, sum",
                    "The next map or filter call",
                    "Nothing — it computes eagerly like a List",
                ),
                correctIndex = 1,
                explanation = "A sequence stays lazy until a terminal operation pulls values through the chain.",
            ),
            QuizQuestion(
                id = "sequences-q3",
                topicId = "sequences",
                question = "When are sequences most beneficial?",
                options = listOf(
                    "For very small collections",
                    "Always — prefer them over List operations",
                    "For large data or long chains, especially with short-circuiting",
                    "Only when working with Strings",
                ),
                correctIndex = 2,
                explanation = "Sequences pay off on large inputs or long operation chains, particularly when a terminal op like first() can short-circuit.",
            ),
        ),
        tutorFocus = "Contrast eager vs lazy with a big range and a first() to make laziness visible. Warn against over-using sequences on tiny lists. Exercise: convert an eager chain to a sequence and identify the terminal operation.",
    ),
)
