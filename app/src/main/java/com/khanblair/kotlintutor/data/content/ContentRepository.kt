package com.khanblair.kotlintutor.data.content

import com.khanblair.kotlintutor.model.Lesson
import com.khanblair.kotlintutor.model.QuizQuestion

interface ContentRepository {
    fun getLesson(topicId: String): Lesson?
    fun getQuizQuestions(topicId: String): List<QuizQuestion>
}

class DefaultContentRepository : ContentRepository {
    override fun getLesson(topicId: String): Lesson? =
        LessonContent.lessons.find { it.topicId == topicId }

    override fun getQuizQuestions(topicId: String): List<QuizQuestion> =
        QuizContent.questions.filter { it.topicId == topicId }
}
