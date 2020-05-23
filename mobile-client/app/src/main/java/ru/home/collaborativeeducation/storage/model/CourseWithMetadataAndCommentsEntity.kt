package ru.home.collaborativeeducation.storage.model

import androidx.room.Embedded
import androidx.room.Relation
import ru.home.collaborativeeducation.model.database.CourseSourceEntity


data class CourseWithMetadataAndCommentsEntity (
    @Embedded val source: CourseSourceEntity,
    @Relation(
        entity = LikesEntity::class,
        parentColumn = "uid",
        entityColumn = "course_uid"
    )
    val metadata: ItemMetadataEntity?
)