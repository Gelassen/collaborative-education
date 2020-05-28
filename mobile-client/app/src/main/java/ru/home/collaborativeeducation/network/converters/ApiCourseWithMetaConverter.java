package ru.home.collaborativeeducation.network.converters;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.home.collaborativeeducation.model.Comment;
import ru.home.collaborativeeducation.model.CourseSourceItem;
import ru.home.collaborativeeducation.model.CourseWithMetadataAndComments;
import ru.home.collaborativeeducation.model.ItemMetadata;
import ru.home.collaborativeeducation.model.Likes;
import ru.home.collaborativeeducation.network.model.ApiResponse;
import ru.home.collaborativeeducation.network.model.CourseSourcePayload;
import ru.home.collaborativeeducation.network.model.Status;

public class ApiCourseWithMetaConverter implements JsonDeserializer<ApiResponse<CourseWithMetadataAndComments>> {

    private CourseSourcePayloadConverter converter = new CourseSourcePayloadConverter();
    private LikesConverter likesConverter = new LikesConverter();

    public static Type getType() {
        return new TypeToken<ApiResponse<CourseWithMetadataAndComments>>(){}.getType();
    }

    @Override
    public ApiResponse<CourseWithMetadataAndComments> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        ApiResponse<CourseWithMetadataAndComments> result = new ApiResponse<>();
        JsonObject root = (JsonObject) json;
        JsonObject statusJson = root.get("status").getAsJsonObject();

        // TODO complete me
        Status status = new Status();
        status.setMessage(statusJson.get("message").getAsString());
        status.setPayload(getPayload(statusJson.get("payload").getAsJsonArray(), context));

        result.setCode(root.get("code").getAsInt());
        result.setStatus(status);
        return result;
    }


    /**
     * Parse array of objects in form of
     *             {
     *                 "source" : {}
     *                 "meta" : {
     *                       "likes" : {}
     *                       "comments": []
     *                 }
     *             }
     * */
    private List<CourseWithMetadataAndComments> getPayload(JsonArray array, JsonDeserializationContext context) {
        List<CourseWithMetadataAndComments> result = new ArrayList<>();
        for (int id = 0; id < array.size(); id++) {
            JsonElement item = array.get(id);
            CourseSourcePayload courseSource = converter.deserialize(
                    ((JsonObject) item).get("source"),
                    new TypeToken<CourseSourcePayload>(){}.getType(),
                    context
            );

            JsonElement metadata = ((JsonObject) item).getAsJsonObject("metadata");

            Likes likes = likesConverter.deserialize(
                    ((JsonObject) metadata).getAsJsonObject("likes"),
                    new TypeToken<Likes>(){}.getType(),
                    context
            );
            likes.setCourseUid(courseSource.getUid()); // majority of items in server db has null params

            // TODO parse comments
            List<Comment> comments = new ArrayList<>();
            result.add(new CourseWithMetadataAndComments(
                    new CourseSourceItem(
                            courseSource.getUid(),
                            courseSource.getTitle(),
                            courseSource.getSource(),
                            courseSource.getCourseUid(),
                            courseSource.getAuthor()
                    ),
                    new ItemMetadata(likes, comments)
            ));
        }
        return result;
    }

}
