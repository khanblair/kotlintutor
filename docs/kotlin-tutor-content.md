# Kotlin Tutor — Complete Learning Roadmap & Content

| | |
|---|---|
| **Companion to** | Kotlin Tutor (Android app) |
| **Purpose** | Full curriculum content: every topic explained, quizzed, and mapped to the in-app AI tutor |
| **Status** | Draft |
| **Version** | 0.2 |
| **Last updated** | 2026-08-02 |

---

## How to use this document

This is the **content backbone** for the Kotlin Tutor app. It mirrors the
[roadmap.sh Kotlin roadmap](https://roadmap.sh/kotlin) and, for **every topic**,
gives you these pieces:

- **↻ Recap of the previous topic** — each new topic *opens* with a short recap
  of the one before it: a one-line refresher plus a *quick check* (with answers).
  This is deliberate spaced retrieval — you re-activate the last idea before
  building the next. (The very first topic has no recap, since there's nothing
  before it.)
- **Explain** — a full explanation of the concept, with runnable Kotlin.
- **Key points & pitfalls** — what to remember and what trips people up.
- **Quiz** — the full topic quiz (deeper than the recap check), ready for
  *Quiz me* mode.
- **Tutor focus** — how the AI tutor should teach this specific topic: what to
  emphasize, misconceptions to probe, and a good exercise to set.

**Recap flow for the app:** when the learner opens topic *N*, the tutor first
runs the recap of topic *N−1* (brief + quick check), then moves into topic *N*.
The recap blocks are short by design so they gate the new material without
becoming a chore.

The *Tutor focus* blocks map directly onto the app's `TutorMode` design
(Explain / Quiz me / Review my code / Give an exercise) and can be folded into
each topic's `summary` or a per-topic prompt override in `Curriculum.kt`.

> All code targets Kotlin 2.x on the JVM. Snippets are written to run inside
> `fun main() { … }` unless noted.

---

## Table of contents

1. [Introduction to Kotlin](#1-introduction-to-kotlin)
2. [Language Basics](#2-language-basics)
3. [Collections](#3-collections)
4. [Control Flow](#4-control-flow)
5. [Functions](#5-functions)
6. [Collection Operations](#6-collection-operations)
7. [Classes & Objects](#7-classes--objects)
8. [Null Safety](#8-null-safety)
9. [Coroutines & Async](#9-coroutines--async)
10. [Packages & Ecosystem](#10-packages--ecosystem)

---

## 1. Introduction to Kotlin

### 1.1 Why use Kotlin

**Explain.** Kotlin is a modern, statically-typed programming language that runs
on the Java Virtual Machine (JVM), and also compiles to JavaScript, native
binaries, and WebAssembly. It was designed to fix long-standing pain points in
Java while staying 100% interoperable with it. The headline reasons developers
choose Kotlin:

- **Conciseness.** A data class that would take dozens of lines in Java is one
  line in Kotlin. Boilerplate like getters, setters, `equals`, and `hashCode` is
  generated for you.
- **Null safety.** Nullability is part of the type system, so the compiler stops
  most `NullPointerException`s before your program ever runs.
- **Interoperability.** You can call Java from Kotlin and Kotlin from Java in the
  same project, which makes gradual adoption painless.
- **Official Android support.** Google endorses Kotlin as the preferred language
  for Android development, and most modern Android APIs are Kotlin-first.
- **Coroutines.** First-class, lightweight concurrency built into the language
  and standard library.

**Example.**

```kotlin
// A complete, immutable value type with equals/hashCode/toString/copy — one line.
data class User(val name: String, val age: Int)

fun main() {
    val user = User("Ada", 36)
    println(user)            // User(name=Ada, age=36)
    val older = user.copy(age = 37)
    println(older)
}
```

**Key points & pitfalls.**

- Kotlin is not "a different runtime" — on the JVM it produces ordinary bytecode
  and uses Java libraries.
- Conciseness is a means, not a goal: readability still matters.

**Quiz.**

1. Name two problems Kotlin was designed to reduce compared to Java.
2. True or false: Kotlin can only run on the JVM.
3. Which company promotes Kotlin as the preferred Android language?

*Answers:* 1) null-pointer errors and boilerplate (also verbosity/concurrency).
2) False — it also targets JS, native, and WASM. 3) Google.

**Tutor focus.** Keep this motivational and concrete. Contrast a Java snippet
with its Kotlin equivalent to make conciseness *felt*, not just claimed. Probe
the misconception that Kotlin replaces the JVM. Good exercise: ask the learner to
list one feature they hope Kotlin improves, then confirm whether Kotlin addresses
it.

---

### 1.2 History of Kotlin

> **↻ Recap of the previous topic — Why use Kotlin**
>
> Kotlin is a concise, null-safe, fully Java-interoperable JVM language, and Google's preferred language for Android.
>
> *Quick check:* Name one thing Kotlin reduces vs Java. · Can it run outside the JVM? **→** null errors / boilerplate; yes (JS, native, WASM).

**Explain.** Kotlin was created by **JetBrains** (makers of IntelliJ IDEA) and
first announced in 2011, with version 1.0 released in 2016. It's named after
Kotlin Island near St. Petersburg. In 2017, Google announced first-class support
for Kotlin on Android, and in 2019 Google made Kotlin its preferred language for
Android development. The language is open source and governed with input from the
**Kotlin Foundation** (a partnership between JetBrains and Google).

**Key points & pitfalls.**

- JetBrains builds the tooling *and* the language, which is why IntelliJ / Android
  Studio support is excellent.
- Kotlin's evolution is deliberate and backward-compatibility-conscious.

**Quiz.**

1. Which company created Kotlin?
2. In roughly what year did Kotlin reach 1.0?
3. What is the significance of 2017/2019 for Kotlin and Android?

*Answers:* 1) JetBrains. 2) 2016. 3) Google announced official Android support
(2017) and then made Kotlin the preferred Android language (2019).

**Tutor focus.** Brief and factual — this topic is context, not a skill. If the
learner is impatient, the tutor should offer to move straight to hands-on basics.

---

### 1.3 Java Interoperability

> **↻ Recap of the previous topic — History of Kotlin**
>
> Created by JetBrains, 1.0 in 2016; Google backed it for Android (2017) and made it preferred (2019).
>
> *Quick check:* Who made Kotlin? · When was 1.0? **→** JetBrains; 2016.

**Explain.** Because Kotlin compiles to JVM bytecode, Kotlin and Java code can
coexist and call each other directly. You can add Kotlin files to an existing
Java project, use any Java library from Kotlin, and expose Kotlin code to Java.

- **Calling Java from Kotlin:** Java classes appear as normal Kotlin types.
  Getters/setters become properties (`user.getName()` → `user.name`).
- **Calling Kotlin from Java:** Kotlin `fun`s become methods; top-level functions
  live in a generated `FileNameKt` class. Annotations like `@JvmStatic`,
  `@JvmField`, and `@JvmName` fine-tune the Java-facing API.
- **Platform types:** values coming from Java have unknown nullability (shown as
  `String!`). Kotlin trusts you here, so guard Java values that might be null.

**Example.**

```kotlin
import java.util.ArrayList   // a Java class, used as if it were Kotlin

fun main() {
    val list = ArrayList<String>()   // Java type
    list.add("Kotlin")
    list.add("Java")
    for (item in list) println(item) // Kotlin for-loop over a Java collection
}
```

**Key points & pitfalls.**

- **Platform types are the classic trap:** the compiler won't force a null check
  on a Java-returned value, so a `NullPointerException` can still sneak in.
- Use `@Jvm*` annotations when designing Kotlin APIs meant to be called from Java.

**Quiz.**

1. Where do Kotlin top-level functions end up when viewed from Java?
2. What is a "platform type" and why is it risky?
3. Which annotation exposes a Kotlin function as a static Java method?

*Answers:* 1) In a generated class named `<FileName>Kt`. 2) A type from Java with
unknown nullability (`String!`); the compiler skips null checks, so nulls can slip
through. 3) `@JvmStatic`.

**Tutor focus.** Emphasize the *practical* value (adopt Kotlin gradually) and the
*one real danger* (platform-type nulls). Good exercise: give the learner a Java
method signature that may return null and ask them to write safe Kotlin that
consumes it.

---

## 2. Language Basics

### 2.1 val vs var

> **↻ Recap of the previous topic — Java Interoperability**
>
> Kotlin and Java call each other freely on the JVM; watch platform types (Java values with unknown nullability).
>
> *Quick check:* What's the risk with a Java-returned value? **→** it may be null yet skips Kotlin's null checks.

**Explain.** Kotlin has two keywords for declaring variables:

- **`val`** (from "value") declares a **read-only** reference. Once assigned, you
  can't reassign it. This is the default you should reach for.
- **`var`** (from "variable") declares a **mutable** reference that can be
  reassigned.

Preferring `val` makes code easier to reason about. Note the subtlety: `val`
makes the *reference* immutable, not necessarily the *object* it points to. A
`val` holding a `MutableList` can't be reassigned, but its contents can change.

**Example.**

```kotlin
fun main() {
    val name = "Ada"      // read-only
    var count = 0         // mutable
    count += 1            // OK
    // name = "Grace"     // Compile error: val cannot be reassigned

    val numbers = mutableListOf(1, 2, 3)
    numbers.add(4)        // OK — reference is fixed, list is mutable
    println("$name $count $numbers")
}
```

**Key points & pitfalls.**

- `val` fixes the reference, not the referenced object's mutability.
- Default to `val`; use `var` only when reassignment is genuinely needed.

**Quiz.**

1. Which keyword should you prefer by default, and why?
2. Can you modify the contents of a `MutableList` stored in a `val`?
3. What error do you get if you reassign a `val`?

*Answers:* 1) `val`, because immutable references are easier to reason about.
2) Yes — `val` fixes the reference, not the object. 3) A compile-time error.

**Tutor focus.** Probe the "val means immutable object" misconception directly:
ask what happens when they `.add()` to a `val` mutable list. Exercise: give a
snippet that overuses `var` and ask them to tighten it to `val`.

---

### 2.2 Type Inference

> **↻ Recap of the previous topic — val vs var**
>
> `val` is a read-only reference, `var` is reassignable; `val` fixes the reference, not the object.
>
> *Quick check:* Which is the default? · Can a `val` MutableList change contents? **→** `val`; yes.

**Explain.** Kotlin is statically typed, but you rarely write types explicitly
because the compiler **infers** them from the initializer. `val x = 5` is `Int`;
`val s = "hi"` is `String`. Inference is compile-time — the type is fixed, just
not spelled out. Single-expression function return types are inferred too.

**Example.**

```kotlin
fun main() {
    val count = 42            // inferred Int
    val price = 9.99          // inferred Double
    val label: String         // explicit type, assigned later
    label = "ready"
    println("$count $price $label")
}

fun square(n: Int) = n * n   // return type Int inferred
```

**Key points & pitfalls.**

- Inference is static, not dynamic typing.
- A bare integer literal is `Int`; a decimal literal is `Double` (not `Float`).
- Prefer explicit types on public APIs for readability and stability.

**Quiz.**

1. What type is inferred for `val x = 10`? For `val y = 10.0`?
2. Does inference make Kotlin dynamically typed?
3. When must you write the type explicitly?

*Answers:* 1) `Int` and `Double`. 2) No — still static. 3) When there's no
initializer, or to widen/clarify the type.

**Tutor focus.** Reinforce "inferred ≠ dynamic." Ask what type `val y = 10.0`
is — learners often guess `Float`. Exercise: declare a variable without an
initializer and assign it later with the right type.

---
### 2.3 Basic Types

> **↻ Recap of the previous topic — Type Inference**
>
> The compiler infers types from initializers — still static typing, just not spelled out.
>
> *Quick check:* Type of `val y = 10.0`? · Is inference dynamic typing? **→** Double; no.

**Explain.** Kotlin's built-in types:

- **Integers:** `Byte` (8-bit), `Short` (16), `Int` (32), `Long` (64). Use an `L`
  suffix for `Long` literals (`10L`).
- **Unsigned integers:** `UByte`, `UShort`, `UInt`, `ULong` (suffix `u`, e.g. `10u`).
- **Floating point:** `Float` (32-bit, suffix `f`) and `Double` (64-bit, default).
- **Boolean:** `true` / `false`.
- **Char:** a single character in single quotes, `'A'`.
- **String:** double-quoted text; supports templates and multiline `"""…"""`.
- **Arrays:** fixed-size via `arrayOf(...)` or typed variants like `IntArray`.

Unlike Java, Kotlin has no primitive/wrapper split in the language — you always
write `Int`, and the compiler uses primitives under the hood where possible.

**Example.**

```kotlin
fun main() {
    val i: Int = 100
    val big: Long = 10_000_000_000L      // underscores aid readability
    val ratio: Double = 3.14
    val flag: Boolean = true
    val letter: Char = 'K'
    val nums: IntArray = intArrayOf(1, 2, 3)
    val positive: UInt = 42u
    println("$i $big $ratio $flag $letter ${nums.sum()} $positive")
}
```

**Key points & pitfalls.**

- No implicit widening: assign an `Int` to a `Long` only via `.toLong()`.
- Literals default to `Int` / `Double`; use suffixes for `Long` / `Float`.
- Arrays are fixed-size; use lists when you need to grow or shrink.

**Quiz.**

1. What's the default type of `5`? Of `5.0`?
2. How do you write a `Long` literal? A `Float`?
3. Why use `UInt` instead of `Int`?
4. Does Kotlin auto-convert `Int` to `Long`?

*Answers:* 1) `Int` and `Double`. 2) `5L` and `5.0f`. 3) For non-negative values
with a wider positive range. 4) No — use `.toLong()`.

**Tutor focus.** The key gotcha is **no implicit numeric conversion**. Set an
exercise adding an `Int` and a `Long` so the learner discovers `.toLong()`. Also
quiz the `Int` vs `Double` literal defaults.

---

### 2.4 String Templates

> **↻ Recap of the previous topic — Basic Types**
>
> Int/Long/Double etc., no implicit widening; literals default to Int/Double.
>
> *Quick check:* How do you write a Long literal? · Convert Int to Long? **→** `10L`; `.toLong()`.

**Explain.** String templates embed values and expressions in a string using `$`.
Use `$name` for a simple variable and `${expression}` for anything more complex.
**Raw (multiline) strings** with triple quotes don't process escapes and are great
for JSON, SQL, or multi-line text.

**Example.**

```kotlin
fun main() {
    val name = "Ada"
    val items = listOf("a", "b", "c")
    println("Hello, $name!")                     // simple
    println("You have ${items.size} items.")     // expression
    println("Uppercase: ${name.uppercase()}")

    val json = """
        {
          "name": "$name",
          "count": ${items.size}
        }
    """.trimIndent()
    println(json)
}
```

**Key points & pitfalls.**

- Use `${}` for property access, method calls, or arithmetic; `$var` only for a
  bare variable.
- Print a literal `$` with `${'$'}` or a raw string.
- `.trimIndent()` cleans leading whitespace in multiline strings.

**Quiz.**

1. Difference between `$name` and `${name.length}`?
2. How do you write a multi-line string?
3. How do you include a literal dollar sign?

*Answers:* 1) `$name` inserts a variable; `${...}` evaluates an expression.
2) Triple-quoted `"""…"""`, usually with `.trimIndent()`. 3) `${'$'}` or a raw
string.

**Tutor focus.** Have the learner convert `+`-concatenation into a template.
Probe the `${}` vs `$` rule with `items.size`. Exercise: build a receipt line
using a template with a calculation.

---
### 2.5 Type Checks & Casts

> **↻ Recap of the previous topic — String Templates**
>
> `$var` and `${expr}` embed values; triple quotes make raw multiline strings.
>
> *Quick check:* When do you need `${}`? · Multiline string syntax? **→** for expressions; `"""…"""`.

**Explain.** Kotlin checks types with **`is`** (and `!is`). A key feature is the
**smart cast**: after `x is String`, the compiler treats `x` as `String` in that
scope — no manual cast needed. For explicit casting, `as` is an unsafe cast (throws
on failure) and **`as?`** is a safe cast that returns `null` on failure.

**Example.**

```kotlin
fun describe(x: Any): String {
    if (x is String) {
        return "String of length ${x.length}"   // smart cast to String
    }
    return when (x) {
        is Int -> "Int: ${x + 1}"                // smart cast to Int
        is Boolean -> "Boolean: ${!x}"
        else -> "Unknown"
    }
}

fun main() {
    val maybeText: Any = "hello"
    val safe: String? = maybeText as? String     // safe cast → "hello"
    println(describe(42))
    println(safe?.uppercase())
}
```

**Key points & pitfalls.**

- Smart casts apply only when the compiler can prove the value didn't change
  (e.g. not on a mutable `var` property).
- Prefer `as?` over `as` to avoid `ClassCastException`.

**Quiz.**

1. What is a smart cast?
2. Difference between `as` and `as?`?
3. Why might a smart cast not apply to a `var` property?

*Answers:* 1) After an `is` check, the value is auto-treated as that type without
an explicit cast. 2) `as` throws on failure; `as?` returns `null`. 3) The compiler
can't guarantee a mutable value is unchanged between check and use.

**Tutor focus.** Smart casts are the "wow" moment — show that no manual cast is
needed after `is`. Push the safe-cast habit (`as?` by default). Exercise: write a
function handling an `Any` that returns different results per type via `when (x)`.

---

### 2.6 Printing data

> **↻ Recap of the previous topic — Type Checks & Casts**
>
> `is` checks type and enables smart casts; `as?` casts safely (null on failure).
>
> *Quick check:* What is a smart cast? · `as` vs `as?`? **→** auto-treats value as the checked type; `as` throws, `as?` returns null.

**Explain.** The core output functions are **`print`** (no newline) and
**`println`** (adds a newline). They accept any value and call its `toString()`.
Data classes give a readable representation for free; plain classes print an
unhelpful hash unless you override `toString()`. Read console input with
`readLine()`.

**Example.**

```kotlin
data class Point(val x: Int, val y: Int)

fun main() {
    print("no newline ")
    println("with newline")
    println(Point(1, 2))            // Point(x=1, y=2) via generated toString
    println(listOf(1, 2, 3))        // [1, 2, 3]
    // val name = readLine()        // reads a line from stdin
}
```

**Key points & pitfalls.**

- Printing a plain (non-data) class shows a hash — motivate `toString()`.
- Templates are usually cleaner than passing many arguments.

**Quiz.**

1. Difference between `print` and `println`?
2. Why does a data class print more nicely than a plain class?
3. How do you read a line of console input?

*Answers:* 1) `println` appends a newline. 2) Data classes auto-generate
`toString()`. 3) `readLine()`.

**Tutor focus.** Quick and practical. Use the plain-class hash output to motivate
`toString()` and data classes later. Exercise: print the same object as a plain
class vs a data class and compare.

---
## 3. Collections

### 3.1 Lists, Sets, Maps

> **↻ Recap of the previous topic — Printing data**
>
> `print`/`println` call `toString()`; data classes print nicely, plain classes show a hash.
>
> *Quick check:* Difference between print and println? · Read a line of input? **→** println adds a newline; `readLine()`.

**Explain.** Kotlin's three core collection types:

- **List** — an ordered collection that allows duplicates; access by index.
- **Set** — an unordered collection of unique elements; no duplicates.
- **Map** — a collection of key→value pairs with unique keys.

Each comes in a **read-only** flavor (`List`, `Set`, `Map`) created with
`listOf`, `setOf`, `mapOf`, and a **mutable** flavor (`MutableList`, etc.) created
with `mutableListOf`, `mutableSetOf`, `mutableMapOf`. Read-only interfaces don't
expose add/remove — reach for mutable only when you need to change the collection.

**Example.**

```kotlin
fun main() {
    val list = listOf("a", "b", "a")        // duplicates kept, order preserved
    val set = setOf("a", "b", "a")           // → [a, b] duplicates removed
    val map = mapOf("one" to 1, "two" to 2)  // key→value pairs

    println(list[0])                         // index access → "a"
    println(set.contains("b"))               // true
    println(map["two"])                      // 2

    val mutable = mutableListOf(1, 2)
    mutable.add(3)
    println(mutable)                         // [1, 2, 3]
}
```

**Key points & pitfalls.**

- `listOf(...)` is read-only, not deeply immutable, but you can't add/remove.
- Use `to` to build map entries: `key to value`.
- Choosing the right type communicates intent (uniqueness → Set, lookup → Map).

**Quiz.**

1. Which collection removes duplicates?
2. How do you create a read-only vs mutable list?
3. What does `map["key"]` return if the key is absent?

*Answers:* 1) Set. 2) `listOf(...)` vs `mutableListOf(...)`. 3) `null`.

**Tutor focus.** Emphasize picking the type by intent. Probe the read-only vs
mutable distinction (learners often default to mutable). Exercise: given a list
with duplicates, produce the unique values and a count per value.

---

### 3.2 Ranges & Progressions

> **↻ Recap of the previous topic — Lists, Sets, Maps**
>
> List keeps order + duplicates, Set is unique, Map is key→value; each has read-only and mutable forms.
>
> *Quick check:* Which removes duplicates? · Read-only list builder? **→** Set; `listOf(...)`.

**Explain.** A **range** expresses a sequence of values. `1..5` is an inclusive
`IntRange` (1,2,3,4,5). Use **`until`** for an exclusive upper bound (`1 until 5`
→ 1..4), **`downTo`** to count down, and **`step`** to change the increment. A
range with a step becomes a **progression**. Ranges are handy in `for` loops and
for `in` membership checks.

**Example.**

```kotlin
fun main() {
    for (i in 1..5) print("$i ")          // 1 2 3 4 5
    println()
    for (i in 1 until 5) print("$i ")     // 1 2 3 4
    println()
    for (i in 10 downTo 1 step 2) print("$i ")  // 10 8 6 4 2
    println()
    println(3 in 1..5)                    // true — membership check
    println('c' in 'a'..'z')              // true — works for Chars too
}
```

**Key points & pitfalls.**

- `..` is inclusive; `until` excludes the upper bound — a common off-by-one trap.
- `in` works for ranges of comparable types, including `Char`.
- `step` must be positive even when using `downTo`.

**Quiz.**

1. What values does `1..3` produce? And `1 until 3`?
2. How do you iterate from 5 down to 1?
3. How do you check if a value is inside a range?

*Answers:* 1) `1,2,3` and `1,2`. 2) `for (i in 5 downTo 1)`. 3) `value in a..b`.

**Tutor focus.** The off-by-one between `..` and `until` is the thing to drill.
Quiz it explicitly. Exercise: print a times table using nested ranges, or check
whether a character is a lowercase letter using `in`.

---
## 4. Control Flow

### 4.1 Conditional Expressions (if / when)

> **↻ Recap of the previous topic — Ranges & Progressions**
>
> `..` is inclusive, `until` exclusive, `downTo`/`step` change direction/increment; `in` checks membership.
>
> *Quick check:* Values of `1 until 3`? · Count down 5 to 1? **→** 1, 2; `5 downTo 1`.

**Explain.** In Kotlin, `if` and `when` are **expressions** — they return a value,
not just execute branches. That means you can assign their result directly, which
removes the need for a ternary operator (Kotlin has none).

- **`if`/`else`** returns the value of the chosen branch.
- **`when`** is a powerful multi-branch selector. It can match values, ranges,
  types (`is`), or arbitrary boolean conditions (when used without a subject).

**Example.**

```kotlin
fun grade(score: Int): String {
    val pass = if (score >= 50) "pass" else "fail"     // if as expression

    return when {                                       // when as expression
        score >= 90 -> "A ($pass)"
        score in 70..89 -> "B ($pass)"
        score >= 50 -> "C ($pass)"
        else -> "F ($pass)"
    }
}

fun main() {
    println(grade(95))   // A (pass)
    println(grade(40))   // F (fail)
}
```

**Key points & pitfalls.**

- When used as an expression, `when`/`if` must be exhaustive — include `else`
  (unless the compiler can prove all cases are covered, e.g. sealed types/enums).
- `when` with a subject (`when (x) { … }`) matches against that value; without a
  subject it evaluates boolean conditions top to bottom.

**Quiz.**

1. Why doesn't Kotlin have a ternary `?:` operator like `a ? b : c`?
2. When is `else` required in a `when`?
3. Can a `when` branch match a range?

*Answers:* 1) Because `if` is already an expression that returns a value.
2) When `when` is used as an expression and the cases aren't provably exhaustive.
3) Yes, e.g. `in 1..10`.

**Tutor focus.** The mental shift is "control flow returns values." Show assigning
an `if` result. Probe why there's no ternary. Exercise: rewrite a nested
`if/else` chain as a clean `when` expression.

---

### 4.2 Loops (for, while, break & continue)

> **↻ Recap of the previous topic — Conditional Expressions**
>
> `if` and `when` are expressions that return values (no ternary needed); `when` matches values, ranges, types.
>
> *Quick check:* Why no ternary? · When is `else` required? **→** `if` already returns a value; when `when` is an expression and not exhaustive.

**Explain.** Kotlin has `for`, `while`, and `do-while`. The `for` loop iterates
over anything **iterable**: ranges, collections, arrays, strings. There's no
C-style `for(i=0;…)` — you iterate ranges instead. `break` exits a loop and
`continue` skips to the next iteration. **Labels** (`outer@`) let `break`/`continue`
target an outer loop in nested loops.

**Example.**

```kotlin
fun main() {
    for (item in listOf("a", "b", "c")) print("$item ")   // a b c
    println()

    for ((index, value) in listOf("x", "y").withIndex())  // index + value
        println("$index -> $value")

    var n = 3
    while (n > 0) { print("$n "); n-- }                   // 3 2 1
    println()

    outer@ for (i in 1..3) {
        for (j in 1..3) {
            if (i * j > 4) break@outer                    // break the outer loop
            print("${i * j} ")
        }
    }
}
```

**Key points & pitfalls.**

- No traditional C-style index loop — use ranges or `withIndex()`.
- Labels are the clean way to break/continue an outer loop.
- Iterating a `Map` gives you entries: `for ((k, v) in map)`.

**Quiz.**

1. How do you loop over indices and values together?
2. What does `break` do vs `continue`?
3. How do you break out of an outer loop from an inner loop?

*Answers:* 1) `for ((i, v) in list.withIndex())`. 2) `break` exits the loop;
`continue` skips to the next iteration. 3) With a label, `break@outer`.

**Tutor focus.** Java/C learners look for `for(i=0;…)` — redirect them to ranges
and `withIndex()`. Exercise: sum only even numbers in a list using `continue`, or
find the first pair whose product exceeds a threshold using a labelled break.

---

### 4.3 Exceptions

> **↻ Recap of the previous topic — Loops**
>
> `for` iterates ranges/iterables; use `withIndex()` for index+value, labels to break an outer loop.
>
> *Quick check:* Loop index and value together? · Break an outer loop? **→** `withIndex()`; labelled `break@outer`.

**Explain.** Kotlin handles errors with `try`/`catch`/`finally`, and **`throw`**
to raise an exception. A key difference from Java: Kotlin has **no checked
exceptions** — you're never forced to declare or catch them. Also, `try` is an
**expression**, so it can return a value. Common exceptions include
`IllegalArgumentException` and `IllegalStateException`, often raised via the
`require(...)` and `check(...)` helper functions.

**Example.**

```kotlin
fun parseAge(text: String): Int {
    return try {
        text.toInt()
    } catch (e: NumberFormatException) {
        -1                                   // try as an expression
    } finally {
        println("parse attempted for '$text'")
    }
}

fun setAge(age: Int) {
    require(age >= 0) { "age must be non-negative, was $age" }  // throws IllegalArgumentException
}

fun main() {
    println(parseAge("42"))   // 42
    println(parseAge("no"))   // -1
    // setAge(-1)             // would throw with the given message
}
```

**Key points & pitfalls.**

- No checked exceptions: the compiler won't force `try/catch`, so document what
  can throw.
- Prefer `require`/`check` for precondition/state validation — they read clearly
  and throw the right exception type.
- `finally` always runs, even after a `return` in `try`.

**Quiz.**

1. How do Kotlin exceptions differ from Java's checked exceptions?
2. What does `require(condition)` do?
3. Can `try` return a value?

*Answers:* 1) Kotlin has no checked exceptions — nothing is forced. 2) Throws
`IllegalArgumentException` if the condition is false. 3) Yes — `try` is an
expression.

**Tutor focus.** Highlight "no checked exceptions" for Java learners and the
`try`-as-expression idiom. Push `require`/`check` for validation. Exercise: write
a function that validates its input with `require` and safely parses a number with
a `try` expression.

---
## 5. Functions

### 5.1 Functions & Parameters

> **↻ Recap of the previous topic — Exceptions**
>
> No checked exceptions; `try` is an expression; use `require`/`check` for validation.
>
> *Quick check:* Does Kotlin force try/catch? · What does `require` throw? **→** no; IllegalArgumentException.

**Explain.** Functions are declared with `fun`. Parameters are `name: Type`, and
the return type follows the parameter list (`: Type`). A function returning
nothing returns `Unit` (which you can omit). **Single-expression functions** use
`=` instead of a block. Kotlin supports **default parameter values** and **named
arguments**, which together remove the need for many overloads. **`vararg`** lets
a function accept any number of arguments.

**Example.**

```kotlin
fun greet(name: String, greeting: String = "Hello"): String =
    "$greeting, $name!"                       // default value + single-expression

fun sum(vararg numbers: Int): Int = numbers.sum()   // vararg

fun main() {
    println(greet("Ada"))                     // uses default → "Hello, Ada!"
    println(greet("Ada", greeting = "Hi"))    // named argument
    println(sum(1, 2, 3, 4))                  // 10
}
```

**Key points & pitfalls.**

- Named arguments make calls self-documenting and let you skip earlier defaults.
- Default values reduce overloads dramatically.
- Only one `vararg` per function; spread an existing array with `*array`.

**Quiz.**

1. What type does a function with no explicit return type return?
2. What do default parameter values let you avoid?
3. How do you pass an existing array to a `vararg` parameter?

*Answers:* 1) `Unit`. 2) Writing multiple overloads. 3) Spread it with `*array`.

**Tutor focus.** Show how default + named args replace overloads. Probe the
`Unit` return concept. Exercise: write one function with defaults that would
otherwise require three Java-style overloads, and call it three different ways.

---

### 5.2 Lambda Functions

> **↻ Recap of the previous topic — Functions & Parameters**
>
> `fun` with typed params; default + named args replace overloads; `vararg` accepts many args.
>
> *Quick check:* Return type when none is given? · Pass an array to a vararg? **→** Unit; spread it with `*array`.

**Explain.** A **lambda** is an anonymous function literal written in braces:
`{ x -> x * 2 }`. Lambdas are values you can pass around. When a lambda is the
**last** argument to a function, you can move it outside the parentheses
(**trailing lambda** syntax). A single-parameter lambda can use the implicit name
**`it`**.

**Example.**

```kotlin
fun main() {
    val double = { x: Int -> x * 2 }
    println(double(21))                       // 42

    val nums = listOf(1, 2, 3, 4)
    println(nums.filter { it % 2 == 0 })      // trailing lambda + it → [2, 4]
    println(nums.map { n -> n * n })          // explicit param → [1, 4, 9, 16]
}
```

**Key points & pitfalls.**

- `it` only exists for single-parameter lambdas; name the parameter when it aids
  clarity or when nesting lambdas.
- The last expression in a lambda is its return value (no `return` keyword).
- Trailing-lambda syntax is why `filter { … }` reads so cleanly.

**Quiz.**

1. What is `it` in a lambda?
2. What does a lambda return?
3. What is trailing-lambda syntax?

*Answers:* 1) The implicit name of a single lambda parameter. 2) The value of its
last expression. 3) Moving a final lambda argument outside the call's parentheses.

**Tutor focus.** Lambdas unlock the collection API, so nail the syntax and `it`.
Probe the "last expression is the result" rule. Exercise: use `map` and `filter`
with lambdas to transform a list, first with `it`, then with a named parameter.

---

### 5.3 Higher-order Functions

> **↻ Recap of the previous topic — Lambda Functions**
>
> Lambdas are `{ x -> … }` values; single param is `it`; the last expression is the result.
>
> *Quick check:* What is `it`? · What does a lambda return? **→** the single parameter; its last expression.

**Explain.** A **higher-order function** takes a function as a parameter or
returns one. The type of a function value is written `(A, B) -> R`. This is the
foundation of Kotlin's expressive collection and coroutine APIs. You can pass a
lambda, an anonymous function, or a **function reference** (`::name`).

**Example.**

```kotlin
fun applyTwice(x: Int, op: (Int) -> Int): Int = op(op(x))   // takes a function

fun makeAdder(amount: Int): (Int) -> Int = { it + amount }  // returns a function

fun main() {
    println(applyTwice(5) { it + 1 })     // op(op(5)) = 7
    println(applyTwice(5, ::square))      // square(square(5)) = 625
    val add10 = makeAdder(10)
    println(add10(32))                    // 42
}

fun square(n: Int) = n * n
```

**Key points & pitfalls.**

- Function type syntax: `(params) -> ReturnType`.
- `::functionName` is a reference to an existing function you can pass around.
- Returning functions enables factories and configurable behavior.

**Quiz.**

1. What makes a function "higher-order"?
2. How do you write the type of a function that takes an `Int` and returns a
   `String`?
3. What does `::square` mean?

*Answers:* 1) It takes and/or returns a function. 2) `(Int) -> String`. 3) A
reference to the existing `square` function, usable as a value.

**Tutor focus.** This is where function *types* click. Draw the `(Int) -> Int`
type explicitly. Exercise: write a higher-order function that takes an operation
and applies it to a list, then pass it different lambdas and a `::` reference.

---
### 5.4 Extension Functions

> **↻ Recap of the previous topic — Higher-order Functions**
>
> Functions that take or return functions; type is `(A) -> R`; pass lambdas or `::refs`.
>
> *Quick check:* Type of an Int→String function? · What is `::square`? **→** `(Int) -> String`; a reference to `square`.

**Explain.** **Extension functions** let you add new functions to an existing type
without inheriting from it or modifying its source. You write the receiver type
before the function name: `fun String.shout() = uppercase() + "!"`. Inside, `this`
refers to the receiver. Extensions are resolved statically (they don't actually
modify the class), and they're everywhere in Kotlin's standard library.

**Example.**

```kotlin
fun String.shout(): String = this.uppercase() + "!"

fun List<Int>.secondOrNull(): Int? = if (size >= 2) this[1] else null

fun main() {
    println("hello".shout())              // HELLO!
    println(listOf(10, 20, 30).secondOrNull())  // 20
    println(listOf(10).secondOrNull())    // null
}
```

**Key points & pitfalls.**

- Extensions are static, not polymorphic — they don't override member functions.
  If a member and an extension clash, the **member wins**.
- They can't access private members of the type they extend.
- Great for readability and for adding helpers to types you don't own.

**Quiz.**

1. What does an extension function let you do?
2. What does `this` refer to inside an extension?
3. If a class has a member function and an extension with the same signature,
   which is called?

*Answers:* 1) Add a function to an existing type without modifying or subclassing
it. 2) The receiver — the instance the function is called on. 3) The member.

**Tutor focus.** Extensions feel magical — clarify that they're static sugar, not
real modification. Probe the member-wins rule. Exercise: add an extension like
`Int.isEven()` or `String.wordCount()` and use it fluently.

---

### 5.5 Standard (scope) Functions

> **↻ Recap of the previous topic — Extension Functions**
>
> Add functions to existing types via a receiver; they're static, so members win over extensions.
>
> *Quick check:* What is `this` in an extension? · Member vs extension clash — who wins? **→** the receiver; the member.

**Explain.** The standard library provides five **scope functions** that run a
block in the context of an object: **`let`**, **`run`**, **`with`**, **`apply`**,
and **`also`**. They differ in how they refer to the object (`it` vs `this`) and
what they return (the object vs the lambda result):

- **`let`** — object as `it`, returns lambda result. Great for null-safe chains.
- **`run`** — object as `this`, returns lambda result.
- **`with`** — like `run` but called as `with(obj) { … }`.
- **`apply`** — object as `this`, returns the object. Ideal for configuration.
- **`also`** — object as `it`, returns the object. Ideal for side effects.

**Example.**

```kotlin
data class Config(var host: String = "", var port: Int = 0)

fun main() {
    val name: String? = "ada"
    name?.let { println("Name is $it") }          // runs only if non-null

    val config = Config().apply {                  // configure, return the object
        host = "localhost"
        port = 8080
    }
    println(config)

    val length = "hello".run { length }            // this = "hello", returns Int
    println(length)                                // 5
}
```

**Key points & pitfalls.**

- Choose by two questions: do you want `it` or `this`, and do you want the
  *object* back or the *lambda result*?
- `apply` for setup, `also` for logging/side effects, `let` for null-safe
  transforms.
- Overusing scope functions can hurt readability — use them where they clarify.

**Quiz.**

1. Which scope functions return the object itself?
2. Which is idiomatic for configuring an object after creation?
3. Which is commonly used for null-safe execution of a block?

*Answers:* 1) `apply` and `also`. 2) `apply`. 3) `let` (with `?.let { … }`).

**Tutor focus.** Give the two-question decision rule (`it`/`this`? object/result?)
rather than rote memorization. Exercise: rewrite an object-configuration snippet
using `apply`, and a null-check using `?.let`.

---
## 6. Collection Operations

### 6.1 read-only vs mutable

> **↻ Recap of the previous topic — Scope Functions**
>
> `let/run/with/apply/also` differ by `it`/`this` and object/result; `apply` configures, `let` is null-safe.
>
> *Quick check:* Which return the object itself? · Which configures an object? **→** apply & also; apply.

**Explain.** Every collection type has a **read-only interface** (`List`, `Set`,
`Map`) and a **mutable interface** (`MutableList`, `MutableSet`, `MutableMap`). The
read-only interface exposes reading operations (`size`, `get`, `contains`) but not
`add`/`remove`. This isn't the same as immutability — a read-only `List` variable
could still point to an object modified elsewhere — but it lets you express and
enforce intent, and it's the safer default for function parameters and return
types.

**Example.**

```kotlin
fun total(items: List<Int>): Int = items.sum()   // read-only param — can't mutate

fun main() {
    val ro: List<Int> = listOf(1, 2, 3)
    // ro.add(4)                                  // won't compile — no add()

    val mut: MutableList<Int> = mutableListOf(1, 2, 3)
    mut.add(4)                                    // OK
    println(total(mut))                           // a MutableList is also a List → 10
}
```

**Key points & pitfalls.**

- Read-only ≠ immutable; it just hides mutating operations.
- Accept `List` (not `MutableList`) as a parameter unless you must mutate it.
- A `MutableList` *is a* `List`, so it can be passed where a `List` is expected.

**Quiz.**

1. What operations does `List` lack compared to `MutableList`?
2. Is a read-only `List` guaranteed to never change?
3. Which type should a function parameter usually be?

*Answers:* 1) Mutating ones like `add`/`remove`. 2) No — the underlying object may
be mutable. 3) The read-only `List`, unless mutation is required.

**Tutor focus.** Clarify "read-only vs truly immutable." Probe the habit of
choosing `List` for parameters. Exercise: given a function that takes
`MutableList`, ask whether it should — and tighten it to `List`.

---

### 6.2 Transformations

> **↻ Recap of the previous topic — read-only vs mutable**
>
> `List` hides mutation, `MutableList` allows it; read-only ≠ immutable; prefer `List` for parameters.
>
> *Quick check:* Is a read-only List guaranteed unchanging? · Best parameter type? **→** no; `List`.

**Explain.** Transformations produce a new collection from an existing one:

- **`map`** applies a function to each element → a new list of results.
- **`mapIndexed`** gives you the index too.
- **`flatMap`** maps each element to a collection and flattens the results.
- **`associate` / `associateWith`** build maps from elements.

These are pure: they don't modify the source.

**Example.**

```kotlin
fun main() {
    val nums = listOf(1, 2, 3)
    println(nums.map { it * it })                 // [1, 4, 9]
    println(nums.mapIndexed { i, n -> "$i:$n" })  // [0:1, 1:2, 2:3]

    val words = listOf("ab", "cd")
    println(words.flatMap { it.toList() })        // [a, b, c, d]

    println(nums.associateWith { it * 10 })       // {1=10, 2=20, 3=30}
}
```

**Key points & pitfalls.**

- `map` returns a **new** list; the original is untouched.
- Use `flatMap` when each element expands into multiple elements.
- Chained transformations each allocate a list — see Sequences (6.5) for large
  data.

**Quiz.**

1. What does `map` return, and does it change the source?
2. When would you use `flatMap` over `map`?
3. What does `associateWith { … }` produce?

*Answers:* 1) A new list; the source is unchanged. 2) When each element produces a
collection you want flattened. 3) A `Map` from each element to the lambda's result.

**Tutor focus.** Anchor the "returns new, source unchanged" idea. Contrast `map`
(same count) vs `flatMap` (flattened). Exercise: turn a list of names into a list
of their lengths, then a map from name to length.

---

### 6.3 Filtering

> **↻ Recap of the previous topic — Transformations**
>
> `map`/`flatMap`/`associate` produce new collections without changing the source.
>
> *Quick check:* Does `map` mutate the source? · When use `flatMap`? **→** no; when each element expands into a collection.

**Explain.** Filtering selects a subset of elements:

- **`filter`** keeps elements matching a predicate.
- **`filterNot`** keeps those that don't match.
- **`filterNotNull`** drops nulls (and refines the type to non-null).
- **`partition`** splits into two lists: matches and non-matches.
- Related predicates: **`any`**, **`all`**, **`none`**, **`count`**.

**Example.**

```kotlin
fun main() {
    val nums = listOf(1, 2, 3, 4, 5, 6)
    println(nums.filter { it % 2 == 0 })      // [2, 4, 6]
    println(nums.filterNot { it % 2 == 0 })   // [1, 3, 5]

    val (evens, odds) = nums.partition { it % 2 == 0 }
    println("$evens / $odds")                 // [2, 4, 6] / [1, 3, 5]

    println(nums.any { it > 5 })              // true
    println(nums.all { it > 0 })              // true
}
```

**Key points & pitfalls.**

- `filter` returns a new list; it never mutates the source.
- `partition` returns a `Pair` you can destructure.
- Use `any`/`all`/`none` when you only need a boolean, not a filtered list.

**Quiz.**

1. What's the difference between `filter` and `filterNot`?
2. What does `partition` return?
3. Which function tells you whether *every* element matches?

*Answers:* 1) `filter` keeps matches; `filterNot` keeps non-matches. 2) A `Pair`
of (matching, non-matching) lists. 3) `all`.

**Tutor focus.** Show destructuring `partition`'s result. Nudge learners toward
`any`/`all` when they only need a boolean. Exercise: from a list of scores, get
the passing ones and check whether anyone scored above 90.

---
### 6.4 Aggregate Operations

> **↻ Recap of the previous topic — Filtering**
>
> `filter`/`filterNot`/`partition` select subsets; `any`/`all`/`none` return booleans.
>
> *Quick check:* What does `partition` return? · Function for 'all match'? **→** a Pair of (match, non-match); `all`.

**Explain.** Aggregate operations collapse a collection into a single value:

- **`count`** — number of (matching) elements.
- **`sum` / `average` / `min` / `max`** (and the null-safe `maxOrNull`, etc.).
- **`fold`** — accumulate with an explicit initial value.
- **`reduce`** — like `fold` but seeds with the first element (throws if empty).
- **`groupBy`** — build a `Map` from a key to the list of elements with that key.

**Example.**

```kotlin
fun main() {
    val nums = listOf(1, 2, 3, 4)
    println(nums.sum())                       // 10
    println(nums.count { it > 2 })            // 2
    println(nums.fold(100) { acc, n -> acc + n })  // 100 + 1+2+3+4 = 110
    println(nums.reduce { acc, n -> acc * n })     // 1*2*3*4 = 24

    val words = listOf("apple", "avocado", "banana")
    println(words.groupBy { it.first() })     // {a=[apple, avocado], b=[banana]}
}
```

**Key points & pitfalls.**

- `reduce` throws on an empty collection; `fold` is safe because it has a seed.
- Prefer `maxOrNull`/`minOrNull` to avoid exceptions on empty input.
- `groupBy` is the go-to for bucketing data by some key.

**Quiz.**

1. What's the difference between `fold` and `reduce`?
2. What happens if you `reduce` an empty list?
3. What does `groupBy { it.first() }` produce for a list of words?

*Answers:* 1) `fold` takes an explicit initial value; `reduce` uses the first
element. 2) It throws an exception. 3) A `Map` from first letter to the list of
words starting with it.

**Tutor focus.** The `fold` vs `reduce` (and the empty-collection trap) is the key
distinction. Exercise: compute the product of a list with `fold`, then group a
list of words by length.

---

### 6.5 Sequences

> **↻ Recap of the previous topic — Aggregate Operations**
>
> `sum`/`count`/`fold`/`reduce`/`groupBy` collapse a collection; `reduce` throws on empty.
>
> *Quick check:* fold vs reduce? · Reduce an empty list? **→** fold has a seed, reduce uses the first element; it throws.

**Explain.** A **`Sequence`** processes elements **lazily** and **one at a time**
through the whole chain, instead of building an intermediate list at each step
(as regular collection operations do — that's "eager"). For large data or long
operation chains, sequences avoid allocating intermediate collections and can
short-circuit (e.g. `first`). Create one with `.asSequence()` or `sequenceOf(...)`.
A terminal operation (like `toList`, `first`, `sum`) triggers the actual work.

**Example.**

```kotlin
fun main() {
    val result = (1..1_000_000).asSequence()
        .map { it * 2 }           // lazy — nothing runs yet
        .filter { it % 3 == 0 }   // still lazy
        .first()                  // terminal — pulls just enough to find one
    println(result)               // 6

    // Eager equivalent would build two huge intermediate lists.
}
```

**Key points & pitfalls.**

- Nothing runs until a **terminal** operation is called.
- Sequences shine for large inputs or long chains; for small collections, plain
  operations are simpler and often faster (less overhead).
- Each element flows through the entire chain before the next one starts.

**Quiz.**

1. How does a `Sequence` differ from an eager collection operation chain?
2. What triggers a sequence to actually compute?
3. When are sequences most beneficial?

*Answers:* 1) It's lazy and processes elements one at a time without intermediate
lists. 2) A terminal operation (e.g. `toList`, `first`, `sum`). 3) For large data
or long chains, especially with short-circuiting.

**Tutor focus.** Contrast eager vs lazy with a big range and a `first()` to make
laziness visible. Warn against over-using sequences on tiny lists. Exercise:
convert an eager chain to a sequence and identify the terminal operation.

---
## 7. Classes & Objects

### 7.1 Defining Classes

> **↻ Recap of the previous topic — Sequences**
>
> Sequences are lazy and element-by-element with no intermediate lists; a terminal op triggers the work.
>
> *Quick check:* What triggers a sequence? · Best use case? **→** a terminal op like collect/first; large data or long chains.

**Explain.** A class is declared with `class`. Kotlin puts the **primary
constructor** in the class header: `class Person(val name: String, var age: Int)`.
Parameters prefixed with `val`/`var` automatically become properties. An **`init`**
block runs initialization logic when an instance is created. **Secondary
constructors** (`constructor(...)`) are occasionally needed but often replaced by
default parameter values. You create instances **without `new`** — just call the
class like a function.

**Example.**

```kotlin
class Person(val name: String, var age: Int) {
    init {
        require(age >= 0) { "age must be non-negative" }
    }
}

fun main() {
    val p = Person("Ada", 36)     // no 'new' keyword
    p.age = 37                    // var property is mutable
    println("${p.name} is ${p.age}")
}
```

**Key points & pitfalls.**

- `val`/`var` in the constructor header declares properties automatically.
- No `new` keyword — instantiate by calling the class.
- Prefer default parameters over multiple secondary constructors.

**Quiz.**

1. What does putting `val`/`var` before a constructor parameter do?
2. How do you create an instance in Kotlin?
3. What runs an `init` block?

*Answers:* 1) It declares that parameter as a property. 2) Call the class like a
function (no `new`). 3) It runs during object construction.

**Tutor focus.** The primary-constructor-as-header idea surprises Java learners —
show how one line replaces fields + constructor + getters. Exercise: define a
`Rectangle(width, height)` with an `init` that validates positivity.

---

### 7.2 Properties & Methods

> **↻ Recap of the previous topic — Defining Classes**
>
> Primary constructor sits in the header; `val`/`var` params become properties; no `new`; `init` runs at construction.
>
> *Quick check:* How do you instantiate? · What does `val` in the header do? **→** call the class; declares a property.

**Explain.** **Properties** are declared with `val`/`var` and can have custom
**getters** and **setters**. A property may be **computed** (no backing field) by
providing a `get()`. **Methods** are just functions declared inside a class. The
special identifier **`field`** refers to the backing field inside a custom
accessor.

**Example.**

```kotlin
class Circle(val radius: Double) {
    val area: Double
        get() = Math.PI * radius * radius     // computed property, no backing field

    var label: String = ""
        set(value) {
            field = value.trim()              // 'field' is the backing field
        }

    fun describe(): String = "Circle(r=$radius, area=%.2f)".format(area)
}

fun main() {
    val c = Circle(2.0)
    c.label = "  round  "
    println(c.describe())
    println("label='${c.label}'")            // trimmed → 'round'
}
```

**Key points & pitfalls.**

- Access properties directly (`c.area`) — don't write Java-style `getArea()`.
- Use `field` (not the property name) inside a custom accessor, or you'll cause
  infinite recursion.
- Computed properties have no backing field; they run their getter each access.

**Quiz.**

1. How do you make a property computed from other values?
2. What is `field` inside a setter?
3. Why avoid using the property name directly inside its own getter?

*Answers:* 1) Provide a custom `get()` and no initializer. 2) The backing field of
the property. 3) It would call the accessor again, causing infinite recursion.

**Tutor focus.** The infinite-recursion trap (using the property name in its
accessor) is worth demonstrating. Exercise: add a computed `diameter` property and
a setter that normalizes input via `field`.

---

### 7.3 Inheritance & Interfaces

> **↻ Recap of the previous topic — Properties & Methods**
>
> Custom get/set; computed properties have no backing field; use `field` inside accessors.
>
> *Quick check:* Make a computed property? · What is `field`? **→** provide a `get()`; the backing field.

**Explain.** Classes are **final by default** — mark a class `open` to allow
subclassing, and `open` a member to allow overriding. Subclasses use `: Base(...)`
and `override` members. **Abstract** classes (`abstract`) can't be instantiated and
may declare members without implementations. **Interfaces** (`interface`) declare
contracts and may include default method implementations, but hold no state
(beyond abstract properties). A class can implement multiple interfaces but extend
only one class.

**Example.**

```kotlin
interface Shape {
    fun area(): Double
    fun describe(): String = "A shape with area ${area()}"   // default method
}

open class Rectangle(val w: Double, val h: Double) : Shape {
    override fun area(): Double = w * h
}

class Square(side: Double) : Rectangle(side, side)

fun main() {
    println(Square(3.0).describe())   // A shape with area 9.0
}
```

**Key points & pitfalls.**

- Everything is `final` unless `open` — the opposite of Java's default.
- `override` is required (not optional) when overriding.
- Interfaces can provide default implementations but can't store state.

**Quiz.**

1. Why won't `class B : A()` compile if `A` is a normal class?
2. What's the difference between an abstract class and an interface?
3. How many classes vs interfaces can a class inherit from?

*Answers:* 1) Classes are final by default; `A` must be `open`. 2) An abstract
class can hold state and have constructors; an interface can't hold state (but can
have default methods). 3) One class, many interfaces.

**Tutor focus.** Lead with "final by default" — it's the biggest surprise for Java
devs. Probe when to pick an interface vs abstract class. Exercise: define a
`Shape` interface with a default `describe()` and two implementing classes.

---
### 7.4 Data Classes

> **↻ Recap of the previous topic — Inheritance & Interfaces**
>
> Classes are final unless `open`; `override` is required; interfaces have default methods but no state.
>
> *Quick check:* Why won't `B : A()` compile? · How many classes vs interfaces can you inherit? **→** A must be `open`; one class, many interfaces.

**Explain.** A **`data class`** models a value that's defined by its data. From the
properties in its primary constructor, the compiler generates `equals()`,
`hashCode()`, `toString()`, `componentN()` functions (for destructuring), and
**`copy()`**. This removes enormous boilerplate and gives you value-style
semantics. Data classes are ideal for DTOs, model objects, and results.

**Example.**

```kotlin
data class User(val name: String, val age: Int)

fun main() {
    val a = User("Ada", 36)
    val b = User("Ada", 36)
    println(a == b)                 // true — structural equality via equals()
    println(a)                      // User(name=Ada, age=36) via toString()

    val older = a.copy(age = 37)    // copy with one field changed
    println(older)

    val (name, age) = a             // destructuring via componentN()
    println("$name / $age")
}
```

**Key points & pitfalls.**

- `==` uses `equals()` (structural), while `===` checks reference identity.
- Only properties in the **primary constructor** are used for the generated
  functions.
- Prefer `val` properties; a data class with `var` weakens its value semantics.

**Quiz.**

1. Name three things a `data class` generates for you.
2. What's the difference between `==` and `===`?
3. Which properties are used for `equals`/`hashCode`?

*Answers:* 1) Any of `equals`, `hashCode`, `toString`, `copy`, `componentN`.
2) `==` is structural equality; `===` is reference identity. 3) Only those in the
primary constructor.

**Tutor focus.** Contrast a data class with a plain class for `==` and printing.
Probe `==` vs `===`. Exercise: model a `Point`, compare two equal points, and use
`copy` to shift one.

---

### 7.5 Sealed & Enum Classes

> **↻ Recap of the previous topic — Data Classes**
>
> Auto-generate equals/hashCode/toString/copy/componentN; `==` is structural, `===` is identity.
>
> *Quick check:* `==` vs `===`? · Which properties are used? **→** structural vs reference; the primary-constructor ones.

**Explain.** An **`enum class`** is a fixed set of named constants, each of which
can hold data and methods. A **`sealed`** class (or interface) defines a
**restricted hierarchy**: all direct subclasses must be declared in the same
module/package. Because the compiler knows every possible subtype, a `when` over a
sealed type can be **exhaustive without an `else`** — perfect for modeling states
and results.

**Example.**

```kotlin
enum class Direction { NORTH, SOUTH, EAST, WEST }

sealed interface Result
data class Success(val data: String) : Result
data class Failure(val error: String) : Result

fun handle(r: Result): String = when (r) {   // no else needed — exhaustive
    is Success -> "OK: ${r.data}"
    is Failure -> "Error: ${r.error}"
}

fun main() {
    println(Direction.NORTH)
    println(handle(Success("loaded")))
    println(handle(Failure("timeout")))
}
```

**Key points & pitfalls.**

- Sealed hierarchies + `when` give you exhaustiveness checks at compile time — add
  a subtype and the compiler flags every `when` you forgot to update.
- Enums are for a fixed set of constants; sealed types are for a fixed set of
  *shapes* (each possibly carrying different data).

**Quiz.**

1. Why can a `when` over a sealed type omit `else`?
2. When would you choose a sealed class over an enum?
3. What benefit do you get when adding a new subtype to a sealed hierarchy?

*Answers:* 1) The compiler knows all subtypes, so it can verify exhaustiveness.
2) When each case needs to carry different data/shape (not just a constant). 3) The
compiler flags every non-exhaustive `when`, so you can't forget a case.

**Tutor focus.** This is a Kotlin highlight — model a `Result`/`UiState` type and
show the exhaustive `when`. Exercise: define a sealed `PaymentState` (Pending,
Paid(amount), Failed(reason)) and a `when` that handles each.

---

### 7.6 Object Declarations

> **↻ Recap of the previous topic — Sealed & Enum Classes**
>
> Enums are fixed constants; sealed types are a fixed set of subtype shapes, enabling exhaustive `when` without `else`.
>
> *Quick check:* Why can `when` skip `else` on a sealed type? · Sealed vs enum? **→** all subtypes are known; sealed cases can carry different data.

**Explain.** The **`object`** keyword creates a **singleton** — a class with
exactly one instance, created lazily on first use. A **`companion object`** inside
a class holds members tied to the class itself (like Java statics), including
factory functions. An **object expression** (`object : Type { … }`) creates a
one-off anonymous instance, similar to an anonymous class.

**Example.**

```kotlin
object Registry {                       // singleton
    val items = mutableListOf<String>()
    fun add(x: String) = items.add(x)
}

class User private constructor(val name: String) {
    companion object {                  // holds a factory
        fun create(name: String) = User(name.trim())
    }
}

fun main() {
    Registry.add("a")
    println(Registry.items)             // [a]
    val u = User.create("  Ada ")       // called on the class, via companion
    println(u.name)                     // Ada
}
```

**Key points & pitfalls.**

- `object` singletons are initialized lazily and are thread-safe on the JVM.
- A `companion object` is the idiomatic home for factory methods and constants.
- Overusing singletons for shared mutable state can create hidden coupling.

**Quiz.**

1. What does the `object` keyword create?
2. What is a `companion object` used for?
3. How do you call a companion function?

*Answers:* 1) A singleton (single-instance object). 2) Class-level members like
factories and constants (Java-static-like). 3) On the class itself, e.g.
`User.create(...)`.

**Tutor focus.** Distinguish the three `object` uses (declaration, companion,
expression). Probe why a private constructor + companion factory is a common
pattern. Exercise: give a class a companion `fromJson`-style factory.

---
### 7.7 Generics

> **↻ Recap of the previous topic — Object Declarations**
>
> `object` = singleton; `companion object` = class-level members/factories; object expressions = anonymous instances.
>
> *Quick check:* What does `object` create? · How do you call a companion function? **→** a singleton; on the class, e.g. `User.create()`.

**Explain.** **Generics** let a class or function work with a type parameter,
written `<T>`. This gives type safety without duplicating code. **Variance**
controls subtyping of generic types: **`out T`** (covariant) means the type only
*produces* `T` (safe to read), while **`in T`** (contravariant) means it only
*consumes* `T`. A **`reified`** type parameter (with `inline` functions) lets you
access the actual type at runtime — something normally erased on the JVM.

**Example.**

```kotlin
class Box<T>(val value: T)                      // generic class

fun <T> firstOrDefault(list: List<T>, default: T): T =
    list.firstOrNull() ?: default               // generic function

inline fun <reified T> isType(value: Any): Boolean = value is T   // reified

fun main() {
    val box = Box("hello")
    println(box.value)
    println(firstOrDefault(listOf(1, 2), 0))    // 1
    println(firstOrDefault(emptyList<Int>(), -1)) // -1
    println(isType<String>("hi"))               // true
}
```

**Key points & pitfalls.**

- Type parameters give compile-time safety and avoid casting.
- `out`/`in` express who produces vs consumes the type (producer/consumer rule).
- Runtime type checks on a plain `T` don't work due to type erasure — use
  `reified` inside an `inline` function.

**Quiz.**

1. What problem do generics solve?
2. What does `out T` signify?
3. Why do you need `reified` to check a generic type at runtime?

*Answers:* 1) Reusable, type-safe code across different types without casting.
2) Covariance — the type only produces `T`, so it's safe to read. 3) Because the
JVM erases generic types at runtime; `reified` preserves the concrete type in an
`inline` function.

**Tutor focus.** Keep variance intuitive with the producer/consumer framing rather
than jargon first. Exercise: write a generic `Stack<T>` with `push`/`pop`, then a
generic `firstOrDefault`.

---

### 7.8 Visibility Modifiers

> **↻ Recap of the previous topic — Generics**
>
> `<T>` gives type-safe reuse; `out` produces, `in` consumes; `reified` (inline) reads the type at runtime.
>
> *Quick check:* What does `out T` mean? · Why `reified`? **→** covariant / producer; generics are erased at runtime.

**Explain.** Kotlin has four visibility modifiers:

- **`public`** (default) — visible everywhere.
- **`private`** — visible only within the file (top-level) or the class.
- **`protected`** — visible in the class and its subclasses (not for top-level).
- **`internal`** — visible everywhere within the same **module**.

`internal` is distinctive: it scopes visibility to a compilation module, which is
great for library boundaries. Default-public means you should consciously restrict
what you expose.

**Example.**

```kotlin
class BankAccount(private var balance: Double) {
    fun deposit(amount: Int) { balance += amount }     // public API
    private fun audit() { /* internal detail */ }      // hidden from outside
    fun currentBalance(): Double = balance
}

internal fun moduleHelper() = "only visible in this module"

fun main() {
    val acc = BankAccount(100.0)
    acc.deposit(50)
    println(acc.currentBalance())     // 150.0
    // acc.balance                    // won't compile — private
}
```

**Key points & pitfalls.**

- Default is **public** — explicitly narrow visibility for encapsulation.
- `internal` is per-module and has no direct Java equivalent.
- `protected` isn't available for top-level declarations.

**Quiz.**

1. What's the default visibility in Kotlin?
2. What does `internal` scope visibility to?
3. Where is a `protected` member visible?

*Answers:* 1) `public`. 2) The same module. 3) The declaring class and its
subclasses.

**Tutor focus.** Emphasize "public by default → restrict deliberately." Highlight
`internal` as the module-boundary tool. Exercise: take a class exposing internal
fields and tighten it with `private`, exposing only a clean public API.

---
## 8. Null Safety

### 8.1 What is Null Safety?

> **↻ Recap of the previous topic — Visibility Modifiers**
>
> public (default), private, protected, internal (module-scoped); restrict deliberately.
>
> *Quick check:* Default visibility? · What does `internal` scope to? **→** public; the module.

**Explain.** Kotlin's type system distinguishes **nullable** from **non-nullable**
types. `String` can never hold `null`; `String?` can. Because nullability is
tracked at compile time, the compiler forces you to handle the null case before
using a nullable value — eliminating most `NullPointerException`s (Tony Hoare's
"billion-dollar mistake"). You literally cannot assign `null` to a non-nullable
type, and you cannot call a method on a nullable value without first handling the
null.

**Example.**

```kotlin
fun main() {
    var name: String = "Ada"
    // name = null                 // won't compile — String is non-nullable

    var nickname: String? = "Ace"  // nullable
    nickname = null                // OK

    // println(nickname.length)    // won't compile — must handle null first
    println(nickname?.length)      // safe: prints null
}
```

**Key points & pitfalls.**

- The `?` on a type is the whole feature: `T` vs `T?`.
- The compiler won't let you dereference a nullable value unsafely.
- Values from Java are "platform types" and bypass these checks — guard them.

**Quiz.**

1. What's the difference between `String` and `String?`?
2. Why does null safety reduce runtime crashes?
3. Can you assign `null` to a non-nullable type?

*Answers:* 1) `String` can't be null; `String?` can. 2) The compiler forces you to
handle nulls before use, catching them at compile time. 3) No.

**Tutor focus.** Frame it as "the compiler is your safety net." Show the compile
error from dereferencing a `T?`. Exercise: given a nullable input, ask the learner
to make the code compile by handling the null.

---

### 8.2 Nullability Operators

> **↻ Recap of the previous topic — What is Null Safety?**
>
> `T` can't be null, `T?` can; the compiler forces you to handle null before use.
>
> *Quick check:* `String` vs `String?`? · Assign null to a non-nullable type? **→** only `String?` allows null; no.

**Explain.** Kotlin provides operators to work with nullable values:

- **Safe call `?.`** — calls a member only if the receiver is non-null, else
  returns `null`. Chains nicely: `a?.b?.c`.
- **Elvis `?:`** — provides a fallback when the left side is null:
  `value ?: default`.
- **Not-null assertion `!!`** — asserts non-null and throws an NPE if wrong. Use
  sparingly; it opts out of null safety.
- **Safe cast `as?`** — casts or returns `null` on failure.

**Example.**

```kotlin
fun lengthOrZero(s: String?): Int = s?.length ?: 0      // safe call + Elvis

fun main() {
    println(lengthOrZero("hello"))   // 5
    println(lengthOrZero(null))      // 0

    val maybe: Any? = "text"
    val str: String? = maybe as? String    // safe cast → "text"
    println(str?.uppercase() ?: "N/A")

    val forced: String? = "x"
    println(forced!!.length)         // 1 — but throws if forced were null
}
```

**Key points & pitfalls.**

- Combine `?.` with `?:` for the common "use it or fall back" pattern.
- `!!` should be rare — each one is a place your program can crash; prefer `?.`,
  `?:`, or a proper null check.
- `?.let { … }` runs a block only when the value is non-null.

**Quiz.**

1. What does `a?.b` do when `a` is null?
2. What does the Elvis operator `?:` provide?
3. Why should `!!` be used sparingly?

*Answers:* 1) Returns `null` without calling `b`. 2) A fallback value when the left
side is null. 3) It throws an NPE if the value is actually null — opting out of
null safety.

**Tutor focus.** Drill the idiomatic `?.` + `?:` combo and treat `!!` as a red
flag. Review mode: flag every `!!` in pasted code and suggest a safer rewrite.
Exercise: rewrite a defensive `if (x != null)` chain using `?.` and `?:`.

---
## 9. Coroutines & Async

### 9.1 Suspending Functions

> **↻ Recap of the previous topic — Nullability Operators**
>
> `?.` safe call, `?:` Elvis fallback, `!!` assert (avoid), `as?` safe cast.
>
> *Quick check:* What does `a?.b` do when a is null? · What does `?:` give? **→** returns null; a fallback value.

**Explain.** A **suspending function** (marked `suspend`) can pause its execution
and resume later **without blocking the underlying thread**. While it's suspended
(e.g. waiting on network I/O), the thread is free to do other work. Suspending
functions can only be called from another suspending function or from a coroutine.
This is what makes coroutines lightweight — you can run thousands concurrently on a
few threads.

**Example.**

```kotlin
import kotlinx.coroutines.*

suspend fun fetchUser(): String {
    delay(100)               // suspends without blocking the thread
    return "Ada"
}

fun main() = runBlocking {    // bridges regular code into a coroutine
    val user = fetchUser()   // call a suspend function
    println("Loaded $user")
}
```

**Key points & pitfalls.**

- `suspend` marks a function that may pause; it doesn't itself start concurrency.
- `delay` suspends; `Thread.sleep` blocks — don't confuse them.
- A `suspend` function needs a coroutine context to run (e.g. `runBlocking`,
  `launch`, `async`).

**Quiz.**

1. What does `suspend` allow a function to do?
2. What's the difference between `delay` and `Thread.sleep`?
3. Where can you call a suspending function?

*Answers:* 1) Pause and resume without blocking its thread. 2) `delay` suspends the
coroutine (frees the thread); `Thread.sleep` blocks the thread. 3) From another
suspend function or a coroutine builder.

**Tutor focus.** Nail "suspend ≠ blocking." Contrast `delay` vs `Thread.sleep`
explicitly. Exercise: write a `suspend` function that "loads" data with `delay` and
call it from `runBlocking`.

---

### 9.2 Coroutine Builders

> **↻ Recap of the previous topic — Suspending Functions**
>
> `suspend` pauses/resumes without blocking the thread; `delay` suspends, `Thread.sleep` blocks.
>
> *Quick check:* delay vs Thread.sleep? · Where can you call suspend funs? **→** delay suspends / sleep blocks; from a coroutine or suspend fun.

**Explain.** **Builders** start coroutines:

- **`launch`** — fire-and-forget; returns a `Job` you can cancel/join. Use for work
  whose result you don't need directly.
- **`async`** — returns a `Deferred<T>`; call `.await()` to get the result. Use for
  concurrent work you'll combine.
- **`runBlocking`** — bridges the non-suspending world into coroutines by blocking
  the current thread until done (mainly for `main` and tests).

Builders run inside a **`CoroutineScope`**.

**Example.**

```kotlin
import kotlinx.coroutines.*

suspend fun load(id: Int): Int { delay(100); return id * 10 }

fun main() = runBlocking {
    launch { println("fire-and-forget") }          // Job

    val a = async { load(1) }                       // starts concurrently
    val b = async { load(2) }
    println("sum = ${a.await() + b.await()}")       // waits for both → 30
}
```

**Key points & pitfalls.**

- Use `async` only when you need the result; otherwise `launch`.
- Two `async` blocks run **concurrently**; awaiting them combines results.
- Avoid `runBlocking` in production app code (it blocks) — it's for entry points
  and tests.

**Quiz.**

1. What's the difference between `launch` and `async`?
2. How do you get the result from an `async`?
3. When is `runBlocking` appropriate?

*Answers:* 1) `launch` is fire-and-forget (returns `Job`); `async` returns a
`Deferred` result. 2) Call `.await()`. 3) At entry points like `main` or in tests.

**Tutor focus.** The `launch` vs `async` choice (need a result?) is the key
decision. Show two `async` blocks running concurrently. Exercise: fetch two values
concurrently with `async` and combine them.

---

### 9.3 Asynchronous Flow

> **↻ Recap of the previous topic — Coroutine Builders**
>
> `launch` = fire-and-forget Job; `async` = Deferred result via `await`; `runBlocking` bridges at entry points.
>
> *Quick check:* launch vs async? · How do you get an async result? **→** fire-and-forget vs a result; `.await()`.

**Explain.** A **`Flow<T>`** is a cold asynchronous stream that emits multiple
values over time (whereas a suspend function returns a single value). "Cold" means
the code inside a flow doesn't run until it's **collected**. Flows use suspending
operators (`map`, `filter`, `collect`) and integrate with structured concurrency.
They're the idiomatic way to model streams like search results, sensor readings,
or database updates.

**Example.**

```kotlin
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun counts(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        emit(i)              // emit a value
    }
}

fun main() = runBlocking {
    counts()
        .map { it * 10 }
        .collect { println(it) }   // triggers the flow → 10, 20, 30
}
```

**Key points & pitfalls.**

- Flows are **cold**: nothing runs until `collect` (a terminal operator).
- Use a `Flow` for *many* values over time; a `suspend fun` for *one*.
- Collection happens in a coroutine and respects cancellation.

**Quiz.**

1. How does a `Flow` differ from a suspend function?
2. What does "cold" mean for a flow?
3. What starts a flow producing values?

*Answers:* 1) A `Flow` emits multiple values over time; a suspend function returns
one. 2) It doesn't run until collected. 3) A terminal operator like `collect`.

**Tutor focus.** Contrast "one value (suspend) vs a stream (Flow)" and the cold
nature. Exercise: write a flow that emits three values with `delay`, transform with
`map`, and collect them.

---

### 9.4 Best Practices

> **↻ Recap of the previous topic — Asynchronous Flow**
>
> `Flow` is a cold stream of many values; the code runs only when collected.
>
> *Quick check:* Flow vs suspend fun? · What does 'cold' mean? **→** many values vs one; nothing runs until collected.

**Explain.** Coroutines follow **structured concurrency**: coroutines are launched
in a **scope**, and the scope won't complete until its children do — so nothing
leaks. Key practices:

- Launch in an appropriate **`CoroutineScope`** (on Android, `viewModelScope` /
  `lifecycleScope`).
- Pick the right **dispatcher**: `Dispatchers.Default` (CPU work), `Dispatchers.IO`
  (blocking I/O), `Dispatchers.Main` (UI).
- **Cancellation is cooperative** — long-running work should check `isActive` or
  call suspending functions that are cancellation-aware.
- Use `withContext(dispatcher) { … }` to switch threads for a block.

**Example.**

```kotlin
import kotlinx.coroutines.*

suspend fun loadData(): String = withContext(Dispatchers.IO) {
    // heavy or blocking work runs on an IO thread
    delay(100)
    "data"
}

fun main() = runBlocking {
    val job = launch {
        val result = loadData()
        println("got $result")
    }
    job.join()   // structured: wait for the child to finish
}
```

**Key points & pitfalls.**

- Don't launch coroutines in `GlobalScope` in app code — it escapes structured
  concurrency and can leak.
- Move blocking work off the main thread with `withContext(Dispatchers.IO)`.
- Respect cancellation; don't swallow `CancellationException`.

**Quiz.**

1. What does structured concurrency guarantee?
2. Which dispatcher is for blocking I/O?
3. Why avoid `GlobalScope` in application code?

*Answers:* 1) Child coroutines are bound to a scope that won't finish until they
do, preventing leaks. 2) `Dispatchers.IO`. 3) It isn't tied to any lifecycle, so
its coroutines can leak.

**Tutor focus.** Emphasize scopes + dispatchers + cooperative cancellation. On
Android, tie this to `viewModelScope`. Exercise: take a snippet using `GlobalScope`
and refactor it to a proper scope with the right dispatcher.

---
## 10. Packages & Ecosystem

### 10.1 Packages & Imports

> **↻ Recap of the previous topic — Coroutine Best Practices**
>
> Structured concurrency ties coroutines to scopes; pick dispatchers (IO/Default/Main); cancellation is cooperative.
>
> *Quick check:* Dispatcher for blocking I/O? · Why avoid GlobalScope? **→** Dispatchers.IO; it has no lifecycle, so it can leak.

**Explain.** A **package** groups related declarations and forms a namespace,
declared with `package` at the top of a file (conventionally matching the folder
path, e.g. `com.example.app`). You bring declarations from other packages into
scope with **`import`**. Some declarations from `kotlin.*` and `kotlin.io.*` are
imported by default. You can alias an import to avoid name clashes with
`import foo.Bar as FooBar`.

**Example.**

```kotlin
package com.example.demo

import kotlin.math.max              // explicit import
import kotlin.math.PI as Pi         // aliased import

fun main() {
    println(max(3, 7))              // 7
    println(Pi)                     // 3.14159...
}
```

**Key points & pitfalls.**

- Package name should mirror the directory structure by convention.
- Default imports mean you don't import basics like `println` or `listOf`.
- Use import aliases to resolve naming conflicts cleanly.

**Quiz.**

1. What does a package provide?
2. Why don't you need to import `println`?
3. How do you resolve two imported names that clash?

*Answers:* 1) A namespace grouping related code. 2) It's part of the default
imports. 3) Alias one with `import … as Name`.

**Tutor focus.** Keep it practical — packages as folders/namespaces, imports as
bringing names into scope. Exercise: split a small program into two packages and
wire them together with imports.

---

### 10.2 Standard Library

> **↻ Recap of the previous topic — Packages & Imports**
>
> Packages namespace code (mirroring folders); `import` brings names in; some are default; alias with `as`.
>
> *Quick check:* Why no import for println? · Resolve a name clash? **→** it's a default import; alias with `as`.

**Explain.** The **Kotlin standard library** (`kotlin-stdlib`) ships with the
language and provides the core types and thousands of helper functions: collection
operations (`map`, `filter`, `fold`), scope functions (`let`, `apply`), string
utilities, math (`kotlin.math`), sequences, ranges, and more. Much of what feels
like "language features" is actually stdlib functions. Knowing it well means
writing less code and fewer bugs.

**Example.**

```kotlin
import kotlin.math.sqrt

fun main() {
    val nums = listOf(4, 9, 16)
    println(nums.map { sqrt(it.toDouble()) })   // stdlib: map + kotlin.math.sqrt
    println("  trim me  ".trim())               // stdlib string helper
    println(listOf(3, 1, 2).sorted())           // stdlib sorting
    println((1..5).sumOf { it * it })           // stdlib aggregate
}
```

**Key points & pitfalls.**

- Before writing a helper, check whether stdlib already has it — it usually does.
- The stdlib is organized into packages like `kotlin.collections`, `kotlin.text`,
  `kotlin.math`.
- Many stdlib functions are extension functions, which is why they chain fluently.

**Quiz.**

1. What kinds of things does the standard library provide?
2. Why do so many stdlib functions chain together nicely?
3. Where would you look for math functions?

*Answers:* 1) Core types plus helpers for collections, strings, math, sequences,
etc. 2) Many are extension functions. 3) `kotlin.math`.

**Tutor focus.** Encourage "search stdlib first." When reviewing code, point out
reinvented helpers that stdlib already provides. Exercise: rewrite a hand-written
loop (e.g. summing squares) using a single stdlib call like `sumOf`.

---

### 10.3 Serialization

> **↻ Recap of the previous topic — Standard Library**
>
> kotlin-stdlib provides core types plus thousands of helpers; many are extension functions, so they chain.
>
> *Quick check:* Where are math functions? · Why do stdlib funcs chain? **→** kotlin.math; they're extension functions.

**Explain.** **`kotlinx.serialization`** is Kotlin's official multiplatform
library for converting objects to and from formats like JSON. You mark a class
`@Serializable`, then use `Json.encodeToString(...)` and
`Json.decodeFromString<T>(...)`. It's compile-time (no reflection required), fast,
and integrates with data classes — making it ideal for APIs, config, and
persistence. (This app's tutor client uses it for the API payloads.)

**Example.**

```kotlin
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class User(val name: String, val age: Int)

fun main() {
    val json = Json.encodeToString(User("Ada", 36))
    println(json)                               // {"name":"Ada","age":36}

    val user = Json.decodeFromString<User>(json)
    println(user)                               // User(name=Ada, age=36)
}
```

**Key points & pitfalls.**

- Requires the `kotlin.plugin.serialization` Gradle plugin plus the runtime
  dependency.
- Mark every serialized class `@Serializable`.
- Use `Json { ignoreUnknownKeys = true }` when parsing APIs that may add fields.

**Quiz.**

1. What annotation marks a class as serializable?
2. Which functions encode/decode JSON?
3. What setting helps when an API adds unexpected fields?

*Answers:* 1) `@Serializable`. 2) `Json.encodeToString` and
`Json.decodeFromString`. 3) `ignoreUnknownKeys = true`.

**Tutor focus.** Connect it to the app: this is exactly how the tutor's API
requests are built. Exercise: model an API response as a `@Serializable` data
class and round-trip it through JSON with `ignoreUnknownKeys` enabled.

---

### 10.4 Build Tools

> **↻ Recap of the previous topic — Serialization**
>
> `kotlinx.serialization`: mark `@Serializable`, use `Json.encodeToString`/`decodeFromString`; `ignoreUnknownKeys` for APIs.
>
> *Quick check:* Annotation to serialize? · Handle unexpected JSON fields? **→** `@Serializable`; `ignoreUnknownKeys = true`.

**Explain.** Kotlin projects are built with **Gradle** (most common, especially on
Android) or **Maven**. Gradle build scripts are usually written in **Kotlin DSL**
(`build.gradle.kts`), where you declare plugins, dependencies, and configuration.
Dependencies are fetched from repositories like Maven Central. Understanding the
basics — plugins block, dependencies block, and versions — is enough to get moving;
depth comes later.

**Example.**

```kotlin
// build.gradle.kts (excerpt)
plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
}
```

**Key points & pitfalls.**

- `build.gradle.kts` is itself Kotlin — the DSL is Kotlin code.
- `implementation(...)` adds a dependency; the coordinate is `group:artifact:version`.
- Keep plugin and library versions compatible with your Kotlin version.

**Quiz.**

1. What are the two main build tools for Kotlin?
2. What language is `build.gradle.kts` written in?
3. What does an `implementation(...)` line do?

*Answers:* 1) Gradle and Maven. 2) Kotlin (the Gradle Kotlin DSL). 3) Declares a
dependency in the form `group:artifact:version`.

**Tutor focus.** Demystify Gradle — plugins + dependencies + versions is the 80%.
Tie it to the app's own `build.gradle.kts`. Exercise: read the app's dependencies
block and identify which library provides coroutines vs serialization.

---

## Appendix — mapping to the app

- Each topic's **Explain / Key points** feeds the tutor's *Explain* mode and can
  seed a topic `summary` in `Curriculum.kt`.
- Each **Quiz** feeds *Quiz me* mode — the tutor asks these one at a time.
- Each **Tutor focus** block is guidance for the system prompt: what to emphasize,
  misconceptions to probe, and an exercise to set (*Give an exercise* mode).
- **Review my code** mode applies the *Key points & pitfalls* as a review rubric.

To extend: add the roadmap's remaining nodes (e.g. build-tool details, platforms
like Kotlin/JS, KMP, Ktor, Compose Multiplatform, testing, Dokka) following this
same five-part template — Explain, Example, Key points, Quiz, Tutor focus.
