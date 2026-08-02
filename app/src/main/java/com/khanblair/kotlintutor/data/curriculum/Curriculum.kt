package com.khanblair.kotlintutor.data.curriculum

import com.khanblair.kotlintutor.data.curriculum.topics.classesObjectsTopics
import com.khanblair.kotlintutor.data.curriculum.topics.collectionOperationsTopics
import com.khanblair.kotlintutor.data.curriculum.topics.collectionsTopics
import com.khanblair.kotlintutor.data.curriculum.topics.controlFlowTopics
import com.khanblair.kotlintutor.data.curriculum.topics.coroutinesTopics
import com.khanblair.kotlintutor.data.curriculum.topics.functionsTopics
import com.khanblair.kotlintutor.data.curriculum.topics.introductionTopics
import com.khanblair.kotlintutor.data.curriculum.topics.languageBasicsTopics
import com.khanblair.kotlintutor.data.curriculum.topics.nullSafetyTopics
import com.khanblair.kotlintutor.data.curriculum.topics.packagesEcosystemTopics
import com.khanblair.kotlintutor.model.CurriculumTopic

/**
 * The full 42-topic curriculum transcribed from docs/kotlin-tutor-content.md,
 * in the same order the doc presents them (recap chains depend on this order).
 */
object Curriculum {
    val topics: List<CurriculumTopic> =
        introductionTopics +
            languageBasicsTopics +
            collectionsTopics +
            controlFlowTopics +
            functionsTopics +
            collectionOperationsTopics +
            classesObjectsTopics +
            nullSafetyTopics +
            coroutinesTopics +
            packagesEcosystemTopics
}
