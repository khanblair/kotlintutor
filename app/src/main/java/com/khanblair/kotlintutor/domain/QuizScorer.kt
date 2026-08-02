package com.khanblair.kotlintutor.domain

import com.khanblair.kotlintutor.model.QuizQuestion

object QuizScorer {
    /** Number of [questions] whose id maps to its correct option index in [answers]. */
    fun score(questions: List<QuizQuestion>, answers: Map<String, Int>): Int =
        questions.count { question -> answers[question.id] == question.correctIndex }
}
