package com.khanblair.kotlintutor.data.curriculum

import com.khanblair.kotlintutor.data.curriculum.topics.aiDevelopmentTopics
import com.khanblair.kotlintutor.data.curriculum.topics.androidTopics
import com.khanblair.kotlintutor.data.curriculum.topics.buildToolsGapTopics
import com.khanblair.kotlintutor.data.curriculum.topics.classesObjectsGapTopics
import com.khanblair.kotlintutor.data.curriculum.topics.classesObjectsTopics
import com.khanblair.kotlintutor.data.curriculum.topics.collectionOperationsGapTopics
import com.khanblair.kotlintutor.data.curriculum.topics.collectionOperationsTopics
import com.khanblair.kotlintutor.data.curriculum.topics.collectionsGapTopics
import com.khanblair.kotlintutor.data.curriculum.topics.collectionsTopics
import com.khanblair.kotlintutor.data.curriculum.topics.competitiveProgrammingTopics
import com.khanblair.kotlintutor.data.curriculum.topics.controlFlowGapTopics
import com.khanblair.kotlintutor.data.curriculum.topics.controlFlowTopics
import com.khanblair.kotlintutor.data.curriculum.topics.coroutinesGapTopics
import com.khanblair.kotlintutor.data.curriculum.topics.coroutinesTopics
import com.khanblair.kotlintutor.data.curriculum.topics.dataAnalysisTopics
import com.khanblair.kotlintutor.data.curriculum.topics.documentationTopics
import com.khanblair.kotlintutor.data.curriculum.topics.exceptionsGapTopics
import com.khanblair.kotlintutor.data.curriculum.topics.functionsGapTopics
import com.khanblair.kotlintutor.data.curriculum.topics.functionsTopics
import com.khanblair.kotlintutor.data.curriculum.topics.idesTopics
import com.khanblair.kotlintutor.data.curriculum.topics.introductionTopics
import com.khanblair.kotlintutor.data.curriculum.topics.ioTopics
import com.khanblair.kotlintutor.data.curriculum.topics.kotlinApplicationsTopics
import com.khanblair.kotlintutor.data.curriculum.topics.kotlinJavaInteropTopics
import com.khanblair.kotlintutor.data.curriculum.topics.kotlinLibrariesTopics
import com.khanblair.kotlintutor.data.curriculum.topics.kotlinPlatformsTopics
import com.khanblair.kotlintutor.data.curriculum.topics.languageBasicsGapTopics
import com.khanblair.kotlintutor.data.curriculum.topics.languageBasicsTopics
import com.khanblair.kotlintutor.data.curriculum.topics.moreClassesObjectsGapTopics
import com.khanblair.kotlintutor.data.curriculum.topics.nullSafetyGapTopics
import com.khanblair.kotlintutor.data.curriculum.topics.nullSafetyTopics
import com.khanblair.kotlintutor.data.curriculum.topics.packagesEcosystemGapTopics
import com.khanblair.kotlintutor.data.curriculum.topics.packagesEcosystemTopics
import com.khanblair.kotlintutor.model.CurriculumTopic

/**
 * The full curriculum: the original 42 topics transcribed from
 * docs/kotlin-tutor-content.md, followed by the remaining roadmap topics
 * authored directly against docs/kotlin.pdf. Order matters — each topic's
 * recap chains to the title of the one immediately before it.
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
            packagesEcosystemTopics +
            languageBasicsGapTopics +
            controlFlowGapTopics +
            exceptionsGapTopics +
            functionsGapTopics +
            collectionsGapTopics +
            collectionOperationsGapTopics +
            classesObjectsGapTopics +
            moreClassesObjectsGapTopics +
            nullSafetyGapTopics +
            coroutinesGapTopics +
            packagesEcosystemGapTopics +
            documentationTopics +
            buildToolsGapTopics +
            kotlinLibrariesTopics +
            ioTopics +
            idesTopics +
            kotlinJavaInteropTopics +
            kotlinPlatformsTopics +
            androidTopics +
            kotlinApplicationsTopics +
            dataAnalysisTopics +
            competitiveProgrammingTopics +
            aiDevelopmentTopics
}
