package ru.home.collaborativeeducation.storage.model

import androidx.room.Entity
import org.jetbrains.annotations.NotNull

@Entity(primaryKeys = ["likesUid", "commentUid"])
class LikesAndCommentsCrossRef {
    @NotNull var likesUid: Long? = null
    @NotNull var commentUid: Long? = null
}