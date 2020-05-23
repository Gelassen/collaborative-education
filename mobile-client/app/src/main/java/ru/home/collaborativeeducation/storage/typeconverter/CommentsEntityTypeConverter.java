package ru.home.collaborativeeducation.storage.typeconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ru.home.collaborativeeducation.storage.model.CommentsEntity;


public class CommentsEntityTypeConverter {

    @TypeConverter
    public static List<CommentsEntity> stringToComments(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<CommentsEntity>>(){}.getType();
        List<CommentsEntity> comments = gson.fromJson(json, type);
        return comments;
    }

    @TypeConverter
    public static String commentsToString(List<CommentsEntity> comments) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<CommentsEntity>>() {}.getType();
        String json = gson.toJson(comments, type);
        return json;
    }
}
