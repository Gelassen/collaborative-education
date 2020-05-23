package ru.home.collaborativeeducation.storage.model

import androidx.room.*
import ru.home.collaborativeeducation.storage.typeconverter.CommentsEntityTypeConverter

@TypeConverters(CommentsEntityTypeConverter::class)
data class ItemMetadataEntity(
    @Embedded var likes: LikesEntity? = null,
    @Relation(
        entity = CommentsEntity::class,
        parentColumn = "likesUid",
        entityColumn = "commentUid",
        associateBy = Junction(LikesAndCommentsCrossRef::class)
    )
    var comments: List<CommentsEntity>? = null
)