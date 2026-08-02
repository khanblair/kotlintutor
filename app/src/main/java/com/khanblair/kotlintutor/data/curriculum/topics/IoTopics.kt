package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val ioTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "creating-files",
        title = "Creating Files",
        category = "I/O",
        recap = Recap(
            previousTopicTitle = "JVM Metadata",
            recapText = "@kotlin.Metadata carries Kotlin-specific info in a versioned format; mismatched Kotlin/library versions across dependencies cause \"incompatible version of Kotlin\" errors.",
            quickCheckQuestion = "Why does bumping just one Kotlin-ecosystem library's version risk breakage?",
            quickCheckAnswer = "Its @kotlin.Metadata version can become incompatible with the other pinned libraries.",
        ),
        explain = "java.io.File represents a path on disk — the file or directory doesn't have to exist yet just because you created a File object. File(\"path\") builds that reference; .exists() checks whether something is actually there; .createNewFile() creates an empty file (returning false if it already existed); .mkdirs() creates a directory along with any missing parent directories. Since Java 7, there's a newer, more modern alternative: java.nio.file.Path together with java.nio.file.Files, which Kotlin exposes ergonomically via kotlin.io.path — Path(\"...\") builds a path, and Files.createFile(path) creates it. Path/Files is generally preferred for new code because it has richer APIs (symbolic links, file attributes, better error reporting), but File is still extremely common and perfectly fine for simple cases.",
        example = """
            |import java.io.File
            |import kotlin.io.path.Path
            |import kotlin.io.path.createFile
            |import kotlin.io.path.exists
            |
            |fun withJavaIoFile() {
            |    val file = File("data/output.txt")
            |    if (!file.exists()) {
            |        file.parentFile?.mkdirs()      // create data/ if it doesn't exist
            |        file.createNewFile()            // create the empty file
            |    }
            |}
            |
            |fun withNioPath() {
            |    val path = Path("data/output2.txt")
            |    if (!path.exists()) {
            |        path.createFile()               // java.nio.file.Files.createFile under the hood
            |    }
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "File(\"path\") just builds a reference — nothing is created on disk until you call something like createNewFile().",
            ".mkdirs() creates a directory plus any missing parent directories; .exists() checks whether the path is already there.",
            "java.nio.file.Path/Files (exposed in Kotlin via kotlin.io.path) is the newer, more capable alternative to java.io.File.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "creating-files-q1",
                topicId = "creating-files",
                question = "Does File(\"notes.txt\") create a file on disk?",
                options = listOf(
                    "Yes, immediately",
                    "No — it just builds a reference to that path; you must call something like createNewFile() to actually create it",
                    "Only if the file already exists",
                    "Only on Android, not on plain JVM",
                ),
                correctIndex = 1,
                explanation = "Constructing a File is just building a path reference in memory; nothing touches the filesystem until you call a method like createNewFile() or mkdirs().",
            ),
            QuizQuestion(
                id = "creating-files-q2",
                topicId = "creating-files",
                question = "What does .mkdirs() do, as opposed to .mkdir()?",
                options = listOf(
                    "Deletes a directory and its contents",
                    "Creates the directory plus any missing parent directories",
                    "Renames a directory",
                    "Lists the contents of a directory",
                ),
                correctIndex = 1,
                explanation = "mkdirs() (plural) creates the target directory along with any missing parent directories in the path; mkdir() only creates the final directory and fails if parents are missing.",
            ),
            QuizQuestion(
                id = "creating-files-q3",
                topicId = "creating-files",
                question = "What is kotlin.io.path's Path(\"...\") paired with to create a file?",
                options = listOf(
                    "File.writeText()",
                    "Files.createFile(path), exposed via the createFile() extension",
                    "It cannot create files, only read them",
                    "BufferedWriter directly",
                ),
                correctIndex = 1,
                explanation = "kotlin.io.path wraps java.nio.file.Path and Files with Kotlin extension functions; createFile() on a Path delegates to Files.createFile.",
            ),
        ),
        tutorFocus = "Make sure the learner internalizes that constructing File/Path is just a reference, not an on-disk action. Exercise: write a function that ensures a directory and a file inside it both exist, checking .exists() before creating either.",
    ),
    CurriculumTopic(
        id = "writing-reading-files",
        title = "Writing & Reading Files",
        category = "I/O",
        recap = Recap(
            previousTopicTitle = "Creating Files",
            recapText = "File(\"path\") only builds a reference; createNewFile()/mkdirs() actually create things on disk. java.nio.file.Path/Files is the newer alternative to java.io.File.",
            quickCheckQuestion = "Does creating a File object put anything on disk?",
            quickCheckAnswer = "No — only calling createNewFile() (or similar) does.",
        ),
        explain = "For simple, whole-file operations, kotlin.io provides extension functions directly on java.io.File that handle opening, reading/writing, and closing for you in one call: File.writeText(...) overwrites the file with a String (creating it if needed), File.readText() reads the entire file into one String, File.appendText(...) adds a String to the end of the file without overwriting it, and File.readLines() reads the whole file and returns its lines as a List<String>. These are convenient exactly because they hide the underlying stream management — but that convenience has a cost: every one of these loads the entire file into memory, so they're a great fit for small config files, logs, or notes, and a poor fit for anything gigabytes in size.",
        example = """
            |import java.io.File
            |
            |fun main() {
            |    val log = File("app.log")
            |
            |    log.writeText("startup\n")             // overwrites (or creates) the file
            |    log.appendText("request received\n")   // adds to the end, doesn't overwrite
            |    log.appendText("response sent\n")
            |
            |    val whole = log.readText()              // entire file as one String
            |    println(whole)
            |
            |    val lines = log.readLines()              // entire file as List<String>
            |    println(lines.size)                       // 3
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "writeText(...) overwrites the whole file (creating it if it doesn't exist yet); appendText(...) adds to the end without erasing existing content.",
            "readText() returns the whole file as one String; readLines() returns it as a List<String>, one entry per line.",
            "All four load the entire file into memory — ideal for small files, not for large ones.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "writing-reading-files-q1",
                topicId = "writing-reading-files",
                question = "What's the difference between writeText(...) and appendText(...)?",
                options = listOf(
                    "They're identical, just different names",
                    "writeText overwrites the whole file; appendText adds to the end without erasing existing content",
                    "writeText only works on new files; appendText only works on existing files",
                    "appendText overwrites the file; writeText appends to it",
                ),
                correctIndex = 1,
                explanation = "writeText replaces the file's entire contents; appendText adds the given text after whatever is already there.",
            ),
            QuizQuestion(
                id = "writing-reading-files-q2",
                topicId = "writing-reading-files",
                question = "What type does File.readLines() return?",
                options = listOf("String", "List<String>", "Sequence<String>", "ByteArray"),
                correctIndex = 1,
                explanation = "readLines() reads the entire file and returns its lines as a List<String>.",
            ),
            QuizQuestion(
                id = "writing-reading-files-q3",
                topicId = "writing-reading-files",
                question = "Why are readText()/readLines()/writeText() a poor fit for a multi-gigabyte file?",
                options = listOf(
                    "They only support .txt file extensions",
                    "They require an active network connection",
                    "They load the entire file's contents into memory at once",
                    "They can only be called from a suspend function",
                ),
                correctIndex = 2,
                explanation = "These whole-file convenience functions read (or write) everything in one shot, holding it all in memory — fine for small files, risky for very large ones.",
            ),
        ),
        tutorFocus = "Frame these as the 'convenient but memory-hungry' tier of file I/O, setting up buffered/streaming alternatives for large files. Exercise: use writeText, appendText, readText, and readLines together on one file and predict the output before running it.",
    ),
    CurriculumTopic(
        id = "buffered-streams",
        title = "Buffered Streams",
        category = "I/O",
        recap = Recap(
            previousTopicTitle = "Writing & Reading Files",
            recapText = "writeText/readText/appendText/readLines are convenient whole-file operations, but they load the entire file into memory — fine for small files, not huge ones.",
            quickCheckQuestion = "Why shouldn't you use readText() on a multi-gigabyte file?",
            quickCheckAnswer = "It loads the whole file into memory at once.",
        ),
        explain = "Reading or writing one byte (or character) at a time means one system call per byte, which is extremely slow — buffering fixes this by reading/writing in larger chunks internally, so the OS is asked for data far less often. File.bufferedReader() and File.bufferedWriter() return a buffered Reader/Writer over the file, giving you efficient sequential access without loading everything into memory at once. For processing a huge file line-by-line, File.useLines { lines -> ... } is the key tool: it lazily streams lines to you (as a Sequence<String>) instead of materializing a List<String> like readLines() does, so memory use stays flat regardless of file size — but the lines are only valid inside that lambda, since the underlying stream closes when it returns. All of these are typically wrapped in use { } — Kotlin's equivalent of try-with-resources — which guarantees the stream is closed (even if an exception is thrown) once the block finishes.",
        example = """
            |import java.io.File
            |
            |fun countLongLines(file: File): Int {
            |    // useLines streams lines lazily — the whole file is never held in memory at once.
            |    return file.useLines { lines ->
            |        lines.count { it.length > 80 }
            |    }
            |}
            |
            |fun copyWithBuffering(source: File, destination: File) {
            |    source.bufferedReader().use { reader ->
            |        destination.bufferedWriter().use { writer ->
            |            reader.forEachLine { line ->
            |                writer.write(line)
            |                writer.newLine()
            |            }
            |        }
            |    }
            |    // both reader and writer are guaranteed closed here, even if an exception was thrown
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Buffering batches reads/writes into larger chunks, avoiding one system call per byte.",
            "File.bufferedReader()/bufferedWriter() give efficient sequential access without loading the whole file into memory.",
            "File.useLines { } streams lines lazily as a Sequence — memory use stays flat even for huge files; the lines are only valid inside the lambda.",
            "use { } is Kotlin's try-with-resources equivalent: it always closes the resource, even if the block throws.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "buffered-streams-q1",
                topicId = "buffered-streams",
                question = "Why does buffering matter when reading or writing files?",
                options = listOf(
                    "It encrypts the data automatically",
                    "It batches reads/writes into larger chunks, avoiding one system call per byte",
                    "It's required to read text files, but not binary files",
                    "It compresses the file contents",
                ),
                correctIndex = 1,
                explanation = "Without buffering, each byte (or small read) can trigger its own system call, which is very slow; buffering reads/writes in larger chunks amortizes that cost.",
            ),
            QuizQuestion(
                id = "buffered-streams-q2",
                topicId = "buffered-streams",
                question = "What makes File.useLines { } different from File.readLines()?",
                options = listOf(
                    "useLines is slower but uses less code",
                    "useLines streams lines lazily without loading the whole file into memory; readLines materializes the whole file as a List<String>",
                    "They are exactly the same, just different names",
                    "useLines only works on binary files",
                ),
                correctIndex = 1,
                explanation = "useLines gives you a lazily-evaluated Sequence<String> scoped to its lambda, so memory usage stays flat; readLines() eagerly loads every line into a List<String> in memory.",
            ),
            QuizQuestion(
                id = "buffered-streams-q3",
                topicId = "buffered-streams",
                question = "What does wrapping a stream in use { } guarantee?",
                options = listOf(
                    "The stream is closed automatically when the block finishes, even if an exception is thrown",
                    "The stream is opened lazily, only when first read",
                    "The block runs on a background thread automatically",
                    "The file is automatically buffered even without bufferedReader()",
                ),
                correctIndex = 0,
                explanation = "use { } is Kotlin's try-with-resources equivalent — it always closes the Closeable resource when the block exits, whether normally or via exception.",
            ),
        ),
        tutorFocus = "Tie this together as the answer to the previous topic's memory problem: buffering plus useLines solves large-file processing without loading everything into memory. Exercise: have the learner write a function that uses useLines to count lines matching a condition in a large file, wrapped correctly so resources are closed.",
    ),
)
