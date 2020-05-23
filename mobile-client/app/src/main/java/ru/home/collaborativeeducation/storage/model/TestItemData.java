package ru.home.collaborativeeducation.storage.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.Relation;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.List;

import ru.home.collaborativeeducation.storage.typeconverter.CommentsEntityTypeConverter;

@Entity
@TypeConverters(CommentsEntityTypeConverter.class)
public class TestItemData {
    @Embedded public LikesEntity likes;
    @Relation(
            parentColumn = "likesUid",
            entity = CommentsEntity.class,
            entityColumn = "commentUid",
            associateBy = @Junction(LikesAndCommentsCrossRef.class)
    )
    public List<CommentsEntity> comments;
}
