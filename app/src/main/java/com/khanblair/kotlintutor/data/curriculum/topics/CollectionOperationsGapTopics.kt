package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val collectionOperationsGapTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "retrieving-collection-parts",
        title = "Retrieving Collection Parts",
        category = "Collection Operations",
        recap = Recap(
            previousTopicTitle = "Iterators",
            recapText = "Iterator<T> exposes hasNext()/next(); a for loop desugars to that pattern, and MutableIterator.remove() is the safe way to delete elements mid-iteration instead of mutating the collection directly.",
            quickCheckQuestion = "What does calling list.remove(x) directly inside a for loop over that same list throw?",
            quickCheckAnswer = "ConcurrentModificationException.",
        ),
        explain = "Several stdlib functions carve a sub-collection out of a larger one without touching the original. slice(range) picks out the elements at the given indices. take(n)/takeLast(n) grab the first/last n elements by count; drop(n)/dropLast(n) do the opposite, returning everything except the first/last n. chunked(n) splits the collection into consecutive, non-overlapping groups of size n (the final group may be smaller). windowed(n) instead produces overlapping sliding groups of size n, advancing by step (default 1) each time — useful for things like moving averages.",
        example = """
            |fun main() {
            |    val letters = ('a'..'j').toList()          // [a, b, c, d, e, f, g, h, i, j]
            |
            |    println(letters.slice(2..5))                 // [c, d, e, f]
            |    println(letters.take(3))                      // [a, b, c]
            |    println(letters.takeLast(3))                   // [h, i, j]
            |    println(letters.drop(7))                        // [h, i, j]
            |    println(letters.dropLast(7))                     // [a, b, c]
            |
            |    println(letters.chunked(3))                       // [[a,b,c], [d,e,f], [g,h,i], [j]]
            |    println(letters.windowed(3).size)                  // 8 — overlapping windows, one per start index
            |    println(letters.windowed(3, step = 3))               // [[a,b,c], [d,e,f], [g,h,i]]
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "slice(range) pulls out elements at those indices as a new List; take/drop count from the front, their *Last variants count from the back.",
            "chunked(n) splits into non-overlapping groups of n (the final group may be smaller); windowed(n) produces overlapping sliding groups of n, advancing by step (default 1).",
            "All of these return a brand-new collection — the original collection is never mutated.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "retrieving-collection-parts-q1",
                topicId = "retrieving-collection-parts",
                question = "What does listOf(1, 2, 3, 4, 5).chunked(2) return?",
                options = listOf("[[1,2],[3,4],[5]]", "[[1,2],[3,4,5]]", "[1,2,3,4,5]", "[[1,2,3],[4,5]]"),
                correctIndex = 0,
                explanation = "chunked splits into consecutive non-overlapping groups of the given size; the final group holds whatever remainder is left.",
            ),
            QuizQuestion(
                id = "retrieving-collection-parts-q2",
                topicId = "retrieving-collection-parts",
                question = "On a 5-element list, how does take(3) differ from slice(0..2)?",
                options = listOf(
                    "take(3) throws if the list has fewer than 3 elements, slice doesn't",
                    "They happen to return the same 3 elements here, but take() only needs a count while slice() takes an explicit index range and can select any range, not just a prefix",
                    "slice(0..2) returns 2 elements, take(3) returns 3",
                    "There is no difference in any case",
                ),
                correctIndex = 1,
                explanation = "take(3) grabs the first 3 elements by count (and won't throw even if the list is shorter); slice(0..2) grabs whatever indices you specify, which happens to be the same 3 elements here but generalizes to any range.",
            ),
            QuizQuestion(
                id = "retrieving-collection-parts-q3",
                topicId = "retrieving-collection-parts",
                question = "How does windowed(3) differ from chunked(3)?",
                options = listOf(
                    "They produce the same result",
                    "windowed produces overlapping groups of 3, advancing one element at a time by default; chunked produces non-overlapping consecutive groups of 3",
                    "windowed skips elements; chunked never does",
                    "windowed only works on Sets",
                ),
                correctIndex = 1,
                explanation = "windowed slides a window of the given size across the collection (overlapping by default); chunked partitions the collection into disjoint consecutive groups.",
            ),
        ),
        tutorFocus = "Contrast chunked (non-overlapping, for batching work) with windowed (overlapping, for sliding-window analysis like moving averages). Exercise: given a list of daily temperatures, compute a 3-day moving average using windowed(3).map { it.average() }.",
    ),
    CurriculumTopic(
        id = "retrieving-single-elements",
        title = "Retrieving Single Elements",
        category = "Collection Operations",
        recap = Recap(
            previousTopicTitle = "Retrieving Collection Parts",
            recapText = "slice/take/drop/chunked/windowed all return new sub-collections without mutating the original — take/drop count from the front (or back with the *Last variants), chunked makes non-overlapping groups, windowed makes overlapping sliding groups.",
            quickCheckQuestion = "What's the difference between chunked(3) and windowed(3)?",
            quickCheckAnswer = "chunked makes non-overlapping groups of 3; windowed makes overlapping sliding groups of 3.",
        ),
        explain = "A family of functions retrieves one element instead of a sub-collection. first()/last() return the first/last element and throw NoSuchElementException on an empty collection (or when a predicate matches nothing); firstOrNull()/lastOrNull() return null instead of throwing. Indexed access — get(index) or the [index] operator — returns the element at that position and throws IndexOutOfBoundsException if it's out of range; elementAt(index)/elementAtOrNull(index) behave the same way, throwing vs. returning null. indexOf(element) is the odd one out: it never throws, returning -1 when the element isn't found.",
        example = """
            |fun main() {
            |    val nums = listOf(10, 20, 30, 40)
            |
            |    println(nums.first())                    // 10
            |    println(nums.firstOrNull { it > 100 })     // null — no match, no exception
            |    println(nums.last())                        // 40
            |    println(nums[2])                             // 30 — indexed access
            |    println(nums.elementAtOrNull(10))              // null — index out of range
            |    println(nums.indexOf(30))                        // 2
            |    println(nums.indexOf(99))                         // -1 — not found, not an exception
            |
            |    val empty = emptyList<Int>()
            |    // empty.first()                                   // throws NoSuchElementException
            |    println(empty.firstOrNull())                        // null
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "first()/last()/get(index)/elementAt(index) throw (NoSuchElementException or IndexOutOfBoundsException) when there's nothing to return; their *OrNull counterparts return null instead.",
            "indexOf(element) never throws — it returns -1 when the element isn't present, so there's no separate OrNull variant for it.",
            "Prefer the OrNull family (or indexOf's -1) when absence is a normal, expected outcome; use the throwing form when absence signals a bug you want surfaced loudly.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "retrieving-single-elements-q1",
                topicId = "retrieving-single-elements",
                question = "What does emptyList<Int>().first() do?",
                options = listOf("Returns null", "Throws NoSuchElementException", "Returns 0", "Returns an empty list"),
                correctIndex = 1,
                explanation = "first() throws NoSuchElementException when there's no element to return; use firstOrNull() to get null instead.",
            ),
            QuizQuestion(
                id = "retrieving-single-elements-q2",
                topicId = "retrieving-single-elements",
                question = "What does listOf(1, 2, 3).indexOf(9) return?",
                options = listOf("null", "It throws an exception", "-1", "3"),
                correctIndex = 2,
                explanation = "indexOf returns -1 for a missing element rather than throwing or returning null.",
            ),
            QuizQuestion(
                id = "retrieving-single-elements-q3",
                topicId = "retrieving-single-elements",
                question = "On a 3-element list, what's the difference between list[5] and list.elementAtOrNull(5)?",
                options = listOf(
                    "They behave identically",
                    "list[5] throws IndexOutOfBoundsException; elementAtOrNull(5) returns null",
                    "list[5] returns null; elementAtOrNull(5) throws",
                    "Both return the last element",
                ),
                correctIndex = 1,
                explanation = "Indexed access throws when the index is out of range; elementAtOrNull returns null instead of throwing.",
            ),
        ),
        tutorFocus = "Drill the throwing-vs-OrNull naming convention as a pattern that recurs across the stdlib (first/firstOrNull, single/singleOrNull, elementAt/elementAtOrNull). Exercise: given a list of user IDs, look up one by index safely with elementAtOrNull and handle the null case instead of letting an exception crash the app.",
    ),
    CurriculumTopic(
        id = "plus-minus-operators",
        title = "plus & minus Operators",
        category = "Collection Operations",
        recap = Recap(
            previousTopicTitle = "Retrieving Single Elements",
            recapText = "first()/last()/get(index)/elementAt(index) throw when there's nothing to return; their OrNull counterparts return null instead, and indexOf returns -1 rather than throwing.",
            quickCheckQuestion = "What does emptyList<Int>().firstOrNull() return?",
            quickCheckAnswer = "null.",
        ),
        explain = "The + and - operators (backed by the plus/minus functions) build a brand-new collection with an element or collection added or removed — the original collection is always left untouched. What += and -= do next depends on the declared type of the variable. On a MutableList, += calls plusAssign, which mutates the same list object in place; this works even when the variable is a val, because no reassignment happens. On a read-only List, there's no plusAssign to call, so list += x desugars to list = list + x — a plain reassignment, which requires the variable to be declared with var.",
        example = """
            |fun main() {
            |    val original = listOf(1, 2, 3)
            |    val withFour = original + 4              // new list: [1, 2, 3, 4]
            |    val withoutTwo = original - 2               // new list: [1, 3]
            |    println(original)                            // [1, 2, 3] — original is untouched
            |    println(withFour)                              // [1, 2, 3, 4]
            |    println(withoutTwo)                              // [1, 3]
            |
            |    val mutable: MutableList<Int> = mutableListOf(1, 2, 3)
            |    mutable += 4                                       // val is fine — plusAssign mutates in place
            |    println(mutable)                                    // [1, 2, 3, 4], same list instance
            |
            |    var readOnly: List<Int> = listOf(1, 2, 3)
            |    readOnly += 4                                         // desugars to readOnly = readOnly + 4 — needs var
            |    println(readOnly)                                      // [1, 2, 3, 4], a brand-new list
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "+ and - (plus/minus) always return a new collection; the original collection is never modified.",
            "On a MutableList, += / -= mutate the existing collection in place via plusAssign/minusAssign — that works even when the variable is declared val, since no reassignment occurs.",
            "On a read-only List, += / -= have no plusAssign to call, so they desugar to list = list + x / list = list - x — a reassignment that requires the variable to be a var.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "plus-minus-operators-q1",
                topicId = "plus-minus-operators",
                question = "After val a = listOf(1, 2); val b = a + 3, what is a?",
                options = listOf("[1,2,3]", "[1,2]", "[3]", "A compilation error"),
                correctIndex = 1,
                explanation = "plus returns a brand-new list; a itself is never mutated by the + operator.",
            ),
            QuizQuestion(
                id = "plus-minus-operators-q2",
                topicId = "plus-minus-operators",
                question = "val nums: MutableList<Int> = mutableListOf(1, 2); nums += 3 — is this legal, and what happens?",
                options = listOf(
                    "Illegal — val cannot be reassigned",
                    "Legal — nums is mutated in place via plusAssign, no reassignment needed",
                    "Legal, but it silently does nothing",
                    "Legal only if nums were declared var",
                ),
                correctIndex = 1,
                explanation = "MutableList defines plusAssign as an in-place mutation, so += mutates the same list object; since there's no reassignment, val works fine.",
            ),
            QuizQuestion(
                id = "plus-minus-operators-q3",
                topicId = "plus-minus-operators",
                question = "var list: List<Int> = listOf(1, 2); list += 3 — why does this need var instead of val?",
                options = listOf(
                    "It doesn't need var, val also works",
                    "List has no plusAssign, so += desugars to list = list + 3, which reassigns the variable",
                    "+= is illegal on List entirely",
                    "List is always mutable at runtime in Kotlin",
                ),
                correctIndex = 1,
                explanation = "Without a plusAssign to call, the compiler translates list += 3 into list = list + 3, and reassignment always requires var.",
            ),
        ),
        tutorFocus = "This is a classic Kotlin gotcha — identical += syntax, two totally different mechanisms depending on the declared type. Exercise: have the learner predict, before running, whether += mutates or reassigns for a MutableList<Int> val versus a List<Int> var, then verify by printing the collections afterward.",
    ),
    CurriculumTopic(
        id = "ordering",
        title = "Ordering",
        category = "Collection Operations",
        recap = Recap(
            previousTopicTitle = "plus & minus Operators",
            recapText = "+ and - build a new collection with an element added or removed, leaving the original untouched; += / -= mutate a MutableList in place via plusAssign, but reassign a read-only List var.",
            quickCheckQuestion = "Does list + 4 change list itself?",
            quickCheckAnswer = "No — it returns a new list; list is unchanged.",
        ),
        explain = "sorted() and sortedDescending() order elements by their natural order, which requires the elements implement Comparable (as Int, String, etc. already do). sortedBy { selector } and sortedByDescending { selector } instead sort by a key extracted from each element via the selector, without you needing to make the element itself Comparable. sortedWith(comparator) sorts using a full Comparator<T>, handy for multi-key sorts built with compareBy { }.thenBy { }. reversed() is different from all of these — it doesn't sort anything, it just returns the elements in the opposite iteration order of whatever order they're already in.",
        example = """
            |data class Person(val name: String, val age: Int)
            |
            |fun main() {
            |    val nums = listOf(3, 1, 4, 1, 5, 9, 2, 6)
            |    println(nums.sorted())                    // [1, 1, 2, 3, 4, 5, 6, 9]
            |    println(nums.sortedDescending())            // [9, 6, 5, 4, 3, 2, 1, 1]
            |    println(nums.reversed())                      // [6, 2, 9, 5, 1, 4, 1, 3] — flips order, doesn't sort
            |
            |    val people = listOf(Person("Bea", 34), Person("Al", 40), Person("Cy", 34))
            |    println(people.sortedBy { it.age })            // Bea(34), Cy(34), Al(40)
            |    println(people.sortedByDescending { it.age })    // Al(40), Bea(34), Cy(34)
            |
            |    val byAgeThenName = compareBy<Person> { it.age }.thenBy { it.name }
            |    println(people.sortedWith(byAgeThenName))          // Bea(34), Cy(34), Al(40)
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "sorted()/sortedDescending() use natural order via Comparable; sortedBy/sortedByDescending sort by a derived key; sortedWith takes a full Comparator for custom or multi-key logic.",
            "All of these return a new sorted list — the original list's order is untouched.",
            "reversed() does not sort; it returns the elements in the opposite of their current iteration order.",
            "compareBy { }.thenBy { } builds a Comparator for multi-key sorts: a primary key plus a tie-breaker.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "ordering-q1",
                topicId = "ordering",
                question = "What does sortedBy { it.age } do differently from sorted()?",
                options = listOf(
                    "Nothing, they're identical",
                    "sortedBy sorts by a key extracted from each element via the given selector; sorted() uses the elements' own natural Comparable order directly",
                    "sortedBy always sorts descending, sorted() always ascending",
                    "sortedBy mutates the original list",
                ),
                correctIndex = 1,
                explanation = "sortedBy derives a sort key per element via the selector lambda, while sorted() relies on the elements already implementing Comparable.",
            ),
            QuizQuestion(
                id = "ordering-q2",
                topicId = "ordering",
                question = "What does reversed() return for listOf(3, 1, 2)?",
                options = listOf("Sorted descending: [3,2,1]", "Sorted ascending: [1,2,3]", "The current order flipped: [2,1,3]", "It throws because the list isn't sorted"),
                correctIndex = 2,
                explanation = "reversed() only reverses the current iteration order — it does not sort, so [3,1,2] simply becomes [2,1,3].",
            ),
            QuizQuestion(
                id = "ordering-q3",
                topicId = "ordering",
                question = "How do you sort a list of Person by age ascending, then by name ascending to break ties?",
                options = listOf(
                    "list.sortedBy { it.age }.sortedBy { it.name }",
                    "list.sortedWith(compareBy<Person> { it.age }.thenBy { it.name })",
                    "list.sortedBy { it.age && it.name }",
                    "list.sorted().sortedBy { it.name }",
                ),
                correctIndex = 1,
                explanation = "compareBy { }.thenBy { } builds a Comparator with a primary key and a tie-breaker, which sortedWith applies in that priority order.",
            ),
        ),
        tutorFocus = "Distinguish 'sort order' from 'iteration order' — reversed() only flips the latter. Exercise: given a list of Person objects, sort by age descending with ties broken alphabetically by name, using sortedWith(compareByDescending<Person> { it.age }.thenBy { it.name }).",
    ),
    CurriculumTopic(
        id = "grouping",
        title = "Grouping",
        category = "Collection Operations",
        recap = Recap(
            previousTopicTitle = "Ordering",
            recapText = "sorted/sortedDescending use natural order; sortedBy/sortedByDescending sort by a derived key; sortedWith takes a Comparator; reversed() just flips iteration order without sorting.",
            quickCheckQuestion = "Does reversed() sort a list?",
            quickCheckAnswer = "No — it only reverses the current order.",
        ),
        explain = "groupBy { keySelector } buckets elements by a computed key, returning a Map<K, List<T>> — one list of matching elements per key. Passing a second lambda, groupBy(keySelector, valueTransform), keeps the same grouping but stores transformed values in each list instead of the original elements. When all you need is an aggregate per group — a count, a sum, a fold — groupingBy { keySelector } builds a lazy Grouping<T, K> that supports eachCount(), fold(), and aggregate() directly, skipping the intermediate Map<K, List<T>> that groupBy would otherwise build. partition { predicate } is a simpler, special case: it always splits a collection into exactly a Pair of two lists — elements matching the predicate, then the elements that don't.",
        example = """
            |fun main() {
            |    val words = listOf("apple", "banana", "avocado", "blueberry", "cherry")
            |
            |    val byFirstLetter = words.groupBy { it.first() }
            |    println(byFirstLetter)                            // {a=[apple, avocado], b=[banana, blueberry], c=[cherry]}
            |
            |    val lengthsByLetter = words.groupBy({ it.first() }, { it.length })
            |    println(lengthsByLetter)                            // {a=[5, 7], b=[6, 9], c=[6]}
            |
            |    val counts = words.groupingBy { it.first() }.eachCount()
            |    println(counts)                                       // {a=2, b=2, c=1}
            |
            |    val (short, long) = words.partition { it.length <= 6 }
            |    println(short)                                          // [apple, banana, cherry]
            |    println(long)                                            // [avocado, blueberry]
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "groupBy { keySelector } returns a Map<K, List<T>> — one list of matching elements per computed key.",
            "groupBy(keySelector, valueTransform) groups the same way but stores transformed values instead of the original elements.",
            "groupingBy { }.eachCount()/fold/aggregate skips building an intermediate Map<K, List<T>>, streaming each group's aggregation directly — more efficient for large collections.",
            "partition { predicate } always splits into exactly two lists as a Pair: elements matching the predicate, then everything else.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "grouping-q1",
                topicId = "grouping",
                question = "What type does listOf(1, 2, 3, 4).groupBy { it % 2 == 0 } return?",
                options = listOf("List<List<Int>>", "Map<Boolean, List<Int>>", "Pair<List<Int>, List<Int>>", "Set<Int>"),
                correctIndex = 1,
                explanation = "groupBy always returns a Map from the computed key to a list of the elements that share that key.",
            ),
            QuizQuestion(
                id = "grouping-q2",
                topicId = "grouping",
                question = "What's the advantage of words.groupingBy { it.first() }.eachCount() over words.groupBy { it.first() }.mapValues { it.value.size }?",
                options = listOf(
                    "There is none, they behave identically in every way",
                    "groupingBy avoids materializing an intermediate Map<K, List<T>> before counting, which is more efficient",
                    "groupingBy returns a List instead of a Map",
                    "eachCount() only works on Sets",
                ),
                correctIndex = 1,
                explanation = "Grouping's eachCount() aggregates directly per group without first building the full Map<K, List<T>> that groupBy + mapValues requires.",
            ),
            QuizQuestion(
                id = "grouping-q3",
                topicId = "grouping",
                question = "What does listOf(-2, -1, 0, 1, 2).partition { it > 0 } return?",
                options = listOf("A Map<Boolean, List<Int>>", "A single filtered List", "A Pair of two lists: matches first, non-matches second", "A List of Pairs"),
                correctIndex = 2,
                explanation = "partition always produces a Pair<List<T>, List<T>> — the first list holds elements satisfying the predicate, the second holds the rest.",
            ),
        ),
        tutorFocus = "Make sure the learner can articulate when to reach for groupBy (need the grouped elements themselves) vs. groupingBy + eachCount/fold (need only an aggregate per group) vs. partition (exactly two outcomes). Exercise: given a list of transactions, use groupingBy { it.category }.fold(0.0) { acc, t -> acc + t.amount } to get a total per category.",
    ),
    CurriculumTopic(
        id = "collection-specific-operations",
        title = "Collection-specific Operations",
        category = "Collection Operations",
        recap = Recap(
            previousTopicTitle = "Grouping",
            recapText = "groupBy returns a Map<K, List<T>>; groupingBy { }.eachCount()/fold aggregates per group without building the intermediate lists; partition splits into a Pair of two lists by a predicate.",
            quickCheckQuestion = "What does partition return?",
            quickCheckAnswer = "A Pair of two Lists — matches first, then non-matches.",
        ),
        explain = "Beyond the operations shared by every collection, List, Set, and Map each expose their own toolbox. List: binarySearch(element) finds an element's index in O(log n), but only works correctly if the list is already sorted — it returns the index if found, or a negative number encoding the insertion point if not; on a MutableList, add(index, element) inserts and removeAt(index) removes and returns the element at that position. Set: union, intersect, and subtract are infix set-algebra operators — combine both sets' distinct elements, keep only elements common to both, or keep only elements exclusive to the first. Map: getOrDefault(key, default) and getOrElse(key) { default } read a value without inserting anything (getOrElse computes its default lazily via a lambda); mapKeys/mapValues transform entries; filterKeys/filterValues keep only entries whose key or value matches a predicate.",
        example = """
            |fun main() {
            |    val sorted = listOf(1, 3, 5, 7, 9, 11)
            |    println(sorted.binarySearch(7))              // 3 — index of 7
            |    println(sorted.binarySearch(4))                // negative — 4 isn't present
            |
            |    val mutable = mutableListOf("a", "b", "c")
            |    mutable.add(1, "x")                              // insert at index 1
            |    println(mutable)                                  // [a, x, b, c]
            |    println(mutable.removeAt(0))                        // "a" — removed and returned
            |    println(mutable)                                     // [x, b, c]
            |
            |    val a = setOf(1, 2, 3)
            |    val b = setOf(3, 4, 5)
            |    println(a union b)                                    // [1, 2, 3, 4, 5]
            |    println(a intersect b)                                  // [3]
            |    println(a subtract b)                                    // [1, 2]
            |
            |    val ages = mapOf("Ada" to 36, "Grace" to 41, "Alan" to 29)
            |    println(ages.getOrDefault("Linus", 0))                     // 0 — key absent, default returned
            |    println(ages.getOrElse("Linus") { -1 })                      // -1 — lazily computed default
            |    println(ages.mapValues { it.value + 1 })                       // {Ada=37, Grace=42, Alan=30}
            |    println(ages.filterKeys { it.startsWith("A") })                 // {Ada=36, Alan=29} — Grace dropped
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "binarySearch(element) only gives correct results on an already-sorted list; it returns the index if found, or a negative number encoding where the element would be inserted if not found.",
            "add(index, element)/removeAt(index) are List-specific mutating operations, available only on MutableList.",
            "union/intersect/subtract are Set algebra: combine both sets, keep only elements common to both, or keep only elements exclusive to the first — each returns a new Set.",
            "getOrDefault/getOrElse read a Map without inserting anything; mapKeys/mapValues transform entries; filterKeys/filterValues keep only entries matching a predicate on the key or value.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "collection-specific-operations-q1",
                topicId = "collection-specific-operations",
                question = "What must be true before calling list.binarySearch(x)?",
                options = listOf("The list must contain no duplicates", "The list must already be sorted (in the order binarySearch expects)", "The list must be a MutableList", "Nothing — it works correctly on any list"),
                correctIndex = 1,
                explanation = "binarySearch assumes the list is already sorted; calling it on an unsorted list produces undefined or incorrect results.",
            ),
            QuizQuestion(
                id = "collection-specific-operations-q2",
                topicId = "collection-specific-operations",
                question = "What does setOf(1, 2, 3) intersect setOf(2, 3, 4) return?",
                options = listOf("[1,2,3,4]", "[2,3]", "[1]", "[4]"),
                correctIndex = 1,
                explanation = "intersect keeps only the elements present in both sets.",
            ),
            QuizQuestion(
                id = "collection-specific-operations-q3",
                topicId = "collection-specific-operations",
                question = "What's the difference between map.getOrDefault(key, default) and map.getOrElse(key) { default }?",
                options = listOf(
                    "No real difference for a simple default value",
                    "getOrDefault inserts the default into the map, getOrElse doesn't",
                    "getOrElse computes its default lazily via a lambda, useful for expensive or side-effecting defaults, while getOrDefault takes an already-evaluated value",
                    "getOrDefault only works on MutableMap",
                ),
                correctIndex = 2,
                explanation = "getOrElse's default is a lambda evaluated only on a miss; getOrDefault's default value is computed eagerly whether or not the key is present.",
            ),
        ),
        tutorFocus = "Cover each collection-type-specific toolbox separately rather than lumping them together — reinforce that binarySearch/add/removeAt are List-only, union/intersect/subtract are Set algebra, and getOrDefault/mapKeys/filterValues are Map-only. Exercise: given two Sets of user IDs (active vs. subscribed), compute which users are active-but-not-subscribed using subtract.",
    ),
)
