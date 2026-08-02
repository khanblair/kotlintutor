package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val dataAnalysisTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "kotlin-notebooks",
        title = "Kotlin Notebooks",
        category = "Data Analysis",
        recap = Recap(
            previousTopicTitle = "Vert.x",
            recapText = "Vert.x is a toolkit for building reactive, event-driven applications on the JVM, using an event loop, verticles, and asynchronous APIs instead of one-thread-per-request.",
            quickCheckQuestion = "What concurrency model does Vert.x use instead of one-thread-per-request?",
            quickCheckAnswer = "An event loop with non-blocking, asynchronous APIs.",
        ),
        explain = "A Kotlin Notebook is a single document made of cells — some hold Kotlin code, others hold Markdown notes — that you run one at a time, in any order, with state kept alive between runs. This is a fundamentally different workflow from writing a compiled application: instead of writing a whole program upfront and running it end to end, you explore incrementally — load some data in one cell, inspect it, tweak a filter in the next cell without re-running everything, and immediately see the result rendered inline underneath the cell. That inline output isn't just printed text: a DataFrame renders as an interactive table, and a library like Kandy renders a chart directly beneath the cell that produced it. Because Markdown cells sit alongside code cells, the notebook doubles as a written narrative — you can note why you tried an approach or record a surprising finding, then keep coding right below it. This iterative, keep-everything-in-memory style is what makes notebooks well suited to exploratory data analysis, where you don't know the shape of the investigation in advance, unlike production application code, whose structure is decided upfront and which is compiled and run as a single unit.",
        example = "",
        keyPoints = listOf(
            "Notebooks mix Kotlin code cells, Markdown cells, and inline rendered output (tables, charts) in one document.",
            "Cells can run out of order and be re-run individually; variables stay alive in memory between runs.",
            "This iterative, keep-state-around style suits exploratory data analysis, unlike a compiled application that runs start-to-finish as a whole.",
            "Rich objects like DataFrames and Kandy charts render visually beneath the cell that produced them, not just as printed text.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "kotlin-notebooks-q1",
                topicId = "kotlin-notebooks",
                question = "What makes a Kotlin Notebook workflow different from writing a compiled application?",
                options = listOf(
                    "Cells run independently and can be re-executed individually while state persists in memory",
                    "Notebooks compile to a single JAR before any code runs",
                    "Notebooks cannot use third-party libraries",
                    "Notebooks only support Markdown, not code",
                ),
                correctIndex = 0,
                explanation = "Notebooks let you run and re-run individual cells while keeping variables alive between runs, enabling incremental, exploratory work rather than one start-to-finish compiled run.",
            ),
            QuizQuestion(
                id = "kotlin-notebooks-q2",
                topicId = "kotlin-notebooks",
                question = "How does a Kotlin Notebook typically display a DataFrame or chart produced by a cell?",
                options = listOf(
                    "As plain printed text only",
                    "Rendered inline beneath the cell, e.g. as an interactive table or chart",
                    "In a separate compiled application window",
                    "It cannot display anything other than numbers",
                ),
                correctIndex = 1,
                explanation = "Kotlin Notebooks render rich output like DataFrame tables and Kandy charts directly beneath the cell that produced them, not just as plain text.",
            ),
            QuizQuestion(
                id = "kotlin-notebooks-q3",
                topicId = "kotlin-notebooks",
                question = "Why are notebooks well suited to exploratory data analysis specifically?",
                options = listOf(
                    "They enforce a strict upfront program structure like a compiled app",
                    "Their incremental, mix-code-notes-and-output style fits investigations whose shape isn't known in advance",
                    "They run faster than any compiled Kotlin program",
                    "They eliminate the need for any data libraries",
                ),
                correctIndex = 1,
                explanation = "The ability to explore incrementally, mixing code, notes, and immediate visual output, matches the undirected, iterative nature of exploratory data analysis better than a rigid, compiled program structure.",
            ),
        ),
        tutorFocus = "Distinguish this from the earlier 'Kotlin Notebook' IDE topic: that one covered the plugin/tooling itself, this one covers the exploratory data-analysis workflow it enables. Exercise: ask the learner to describe how they'd explore an unfamiliar CSV file cell-by-cell versus writing a full compiled program to do the same thing.",
    ),
    CurriculumTopic(
        id = "kotlin-dataframe",
        title = "Kotlin DataFrame",
        category = "Data Analysis",
        recap = Recap(
            previousTopicTitle = "Kotlin Notebooks",
            recapText = "Kotlin Notebooks combine code cells, Markdown notes, and inline-rendered output in one document, letting you explore data incrementally rather than writing a whole compiled program upfront.",
            quickCheckQuestion = "Why are notebooks well suited to exploratory data analysis?",
            quickCheckAnswer = "You can run cells incrementally, keep state alive, and see rendered output immediately — matching an investigation whose shape isn't known upfront.",
        ),
        explain = "Kotlin DataFrame is JetBrains's library for working with tabular data in Kotlin — filling a role similar to pandas in Python or dplyr in R. A DataFrame is a table of rows and typed columns; you can read one in from CSV, JSON, Excel, or a JDBC result set with a call like DataFrame.readCsv(\"file.csv\"), then manipulate it with a fluent, chainable API: filter { } to keep matching rows, groupBy { } and aggregate { } to summarize, and join to combine two DataFrames on a key. What sets Kotlin DataFrame apart from a plain Map- or List-based table is its support for typed, generated schemas: given a concrete CSV or JSON shape, the library can generate a typed API so columns are accessed as strongly-typed properties (e.g. score, grade) rather than untyped string lookups (row[\"score\"]), catching typos and type mismatches at compile time instead of at runtime.",
        example = """
            |import org.jetbrains.kotlinx.dataframe.DataFrame
            |import org.jetbrains.kotlinx.dataframe.api.*
            |
            |// After reading, a generated schema exposes columns as typed properties
            |val df = DataFrame.readCsv("students.csv")
            |
            |val topScorersByGrade = df
            |    .filter { score > 80 }               // typed column access, not row["score"]
            |    .groupBy { grade }
            |    .aggregate { mean(score) into "avgScore" }
        """.trimMargin(),
        keyPoints = listOf(
            "Kotlin DataFrame plays a role similar to pandas (Python) or dplyr (R): reading, wrangling, and summarizing tabular data.",
            "It reads CSV, JSON, Excel, and JDBC results directly into a DataFrame with a single call like DataFrame.readCsv(...).",
            "Its fluent API chains operations like filter, groupBy, aggregate, and join.",
            "Given a concrete data shape, it can generate a typed schema so columns are accessed as compile-time-checked properties instead of untyped string lookups.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "kotlin-dataframe-q1",
                topicId = "kotlin-dataframe",
                question = "What role does Kotlin DataFrame play in the Kotlin ecosystem?",
                options = listOf(
                    "A tabular data manipulation library similar to pandas or dplyr",
                    "A charting/plotting library",
                    "A build tool for Kotlin Multiplatform",
                    "A dependency injection framework",
                ),
                correctIndex = 0,
                explanation = "Kotlin DataFrame is JetBrains's library for reading, filtering, grouping, and aggregating tabular data — the same role pandas plays in Python or dplyr plays in R.",
            ),
            QuizQuestion(
                id = "kotlin-dataframe-q2",
                topicId = "kotlin-dataframe",
                question = "What advantage does Kotlin DataFrame's generated typed schema give over untyped, string-keyed column access?",
                options = listOf(
                    "It removes the need to read any external files",
                    "It catches column-name typos and type mismatches at compile time instead of at runtime",
                    "It makes the DataFrame immutable",
                    "It automatically renders charts",
                ),
                correctIndex = 1,
                explanation = "A generated typed schema turns columns into strongly-typed properties, so a misspelled or wrongly-typed column access is a compile error rather than a runtime surprise.",
            ),
            QuizQuestion(
                id = "kotlin-dataframe-q3",
                topicId = "kotlin-dataframe",
                question = "Which combination of Kotlin DataFrame operations is typical for summarizing rows by category?",
                options = listOf(
                    "groupBy { } combined with aggregate { }",
                    "readCsv { } alone",
                    "join { } alone",
                    "plot { } alone",
                ),
                correctIndex = 0,
                explanation = "groupBy { } partitions rows by a key and aggregate { } summarizes each group; join combines two DataFrames, readCsv only loads data, and plot { } belongs to a charting library, not DataFrame itself.",
            ),
        ),
        tutorFocus = "Draw the parallel to pandas explicitly since many learners will have that reference point. Exercise: have the learner sketch (in words or code) reading a CSV, filtering to one condition, then grouping and aggregating — and explain what the generated typed schema buys them over row[\"columnName\"].",
    ),
    CurriculumTopic(
        id = "kandy",
        title = "Kandy",
        category = "Data Analysis",
        recap = Recap(
            previousTopicTitle = "Kotlin DataFrame",
            recapText = "Kotlin DataFrame is a pandas-like library for tabular data: read CSV/JSON/etc. into a DataFrame, then filter/groupBy/aggregate/join it with a fluent, typed API.",
            quickCheckQuestion = "What does Kotlin DataFrame's generated typed schema protect against?",
            quickCheckAnswer = "Column-name typos and type mismatches that untyped string lookups wouldn't catch until runtime.",
        ),
        explain = "Kandy is JetBrains's Kotlin library for building charts and plots with a declarative DSL, purpose-built to pair with Kotlin DataFrame and Kotlin Notebooks. Rather than imperatively drawing shapes, you describe what a chart should show — plot { line { x(...); y(...) } } for a line chart, bars { } for a bar chart, points { } for a scatter plot — and Kandy handles the rendering, using the grammar-of-graphics-style Lets-Plot engine as its default rendering backend. It integrates directly with Kotlin DataFrame: you can call a plotting function straight on a DataFrame or one of its columns, so there's no manual conversion step between 'the data I wrangled' and 'the chart I want to see.' Inside a Kotlin Notebook, this closes the loop of a typical exploratory data analysis pipeline: read raw data into a DataFrame, wrangle it with filter/groupBy/aggregate, then hand the result straight to Kandy, which renders the chart inline beneath the cell — all without leaving the notebook or switching tools.",
        example = """
            |import org.jetbrains.kotlinx.kandy.dsl.*
            |import org.jetbrains.kotlinx.kandy.letsplot.layers.*
            |
            |// df was already wrangled with Kotlin DataFrame — no manual conversion needed
            |df.plot {
            |    bars {
            |        x(grade)
            |        y(avgScore)
            |    }
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Kandy provides a declarative DSL for charts (plot { line { } }, bars { }, points { } for scatter) rather than imperative drawing calls.",
            "It integrates directly with Kotlin DataFrame, so a wrangled DataFrame can be plotted with no manual conversion step.",
            "Inside a Kotlin Notebook, charts render inline beneath the cell that produced them.",
            "Typical EDA pipeline: Kotlin DataFrame wrangles the data, then Kandy visualizes it, both inside the same notebook.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "kandy-q1",
                topicId = "kandy",
                question = "How does Kandy's charting API work?",
                options = listOf(
                    "A declarative DSL describing what the chart should show, e.g. plot { bars { x(...); y(...) } }",
                    "Imperative pixel-by-pixel drawing calls",
                    "A command-line tool separate from Kotlin code",
                    "An XML layout format like Android views",
                ),
                correctIndex = 0,
                explanation = "Kandy uses a declarative plot { } DSL where you describe the chart's structure (layers like line, bars, points), and Kandy handles rendering it.",
            ),
            QuizQuestion(
                id = "kandy-q2",
                topicId = "kandy",
                question = "How does Kandy typically fit with Kotlin DataFrame in an exploratory data analysis workflow?",
                options = listOf(
                    "They are unrelated and can't be used together",
                    "A DataFrame is wrangled first, then plotted directly with Kandy, often inside the same Kotlin Notebook",
                    "Kandy replaces the need for DataFrame entirely",
                    "DataFrame is used only for charts, and Kandy only for storage",
                ),
                correctIndex = 1,
                explanation = "The typical pipeline is DataFrame for wrangling (filter/groupBy/aggregate) followed by Kandy for visualization, both inside the same Kotlin Notebook.",
            ),
            QuizQuestion(
                id = "kandy-q3",
                topicId = "kandy",
                question = "Where does a Kandy chart typically render when used inside a Kotlin Notebook?",
                options = listOf(
                    "In a separate compiled desktop application",
                    "Inline, beneath the cell that produced it",
                    "Only after exporting to a PDF file",
                    "It cannot render inside notebooks, only in production apps",
                ),
                correctIndex = 1,
                explanation = "Like DataFrame tables, Kandy charts render inline beneath the cell that produced them, keeping the wrangle-and-visualize loop inside the notebook.",
            ),
        ),
        tutorFocus = "Frame the three Data Analysis topics as one pipeline: Notebook (environment) -> DataFrame (wrangle) -> Kandy (visualize). Exercise: ask the learner to describe, end to end, how they'd take a raw CSV, load and filter it with DataFrame, then produce a bar chart of a grouped aggregate with Kandy — all inside one notebook.",
    ),
)
