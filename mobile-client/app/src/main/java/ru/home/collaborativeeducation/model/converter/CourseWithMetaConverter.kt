package ru.home.collaborativeeducation.model.converter

import ru.home.collaborativeeducation.model.*
import ru.home.collaborativeeducation.model.database.CourseSourceEntity
import ru.home.collaborativeeducation.storage.model.CommentsEntity
import ru.home.collaborativeeducation.storage.model.CourseWithMetadataAndCommentsEntity
import ru.home.collaborativeeducation.storage.model.ItemMetadataEntity
import ru.home.collaborativeeducation.storage.model.LikesEntity

class CourseWithMetaConverter: IConverter<CourseWithMetadataAndCommentsEntity, CourseWithMetadataAndComments> {

    override fun convert(input: CourseWithMetadataAndCommentsEntity): CourseWithMetadataAndComments {
        val result = CourseWithMetadataAndComments(
            getSource(input.source),
            getMetadata(input.metadata)
        )
        return result
    }

    override fun convert(input: List<CourseWithMetadataAndCommentsEntity>): List<CourseWithMetadataAndComments> {
        val result = mutableListOf<CourseWithMetadataAndComments>()
        for (item in input) {
            result.add(convert(item))
        }
        return result
    }

    private fun getSource(source: CourseSourceEntity): CourseSourceItem {
        return CourseSourceItem(
            if (source.uid == null) -1 else source.uid,
            source.title,
            source.source,
            source.courseId,
            source.author
        )
    }

    private fun getMetadata(metadata: ItemMetadataEntity?): ItemMetadata {
        if (metadata == null) {
            return ItemMetadata(
                getLikes(null),
                getComments(null)
            )
        } else {
            return ItemMetadata(
                getLikes(metadata.likes),
                getComments(metadata.comments)
            )
        }

    }

    private fun getComments(comments: List<CommentsEntity>?): List<Comment> {
        if (comments == null) return mutableListOf<Comment>()
        val result = mutableListOf<Comment>()
        for (item in comments)  {
            result.add(
                Comment(
                    item.commentUid,
                    item.topicUid,
                    item.comment
                )
            )
        }
        return result
    }

    private fun getLikes(likes: LikesEntity?): Likes {
        if (likes == null) return Likes(-1, -1, -1, arrayListOf())
        return Likes(
            likes.likesUid,
            likes.counter,
            likes.courseUid,
            likes.users
        )
    }

}