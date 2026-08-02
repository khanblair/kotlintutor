package com.khanblair.kotlintutor.data.curriculum

import com.khanblair.kotlintutor.model.CurriculumTopic

interface CurriculumRepository {
    fun getTopic(topicId: String): CurriculumTopic?
}

class DefaultCurriculumRepository : CurriculumRepository {
    override fun getTopic(topicId: String): CurriculumTopic? =
        Curriculum.topics.find { it.id == topicId }
}
