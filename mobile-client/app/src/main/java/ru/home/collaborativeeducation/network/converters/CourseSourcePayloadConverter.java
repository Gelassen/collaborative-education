package ru.home.collaborativeeducation.network.converters;

import android.util.Log;

import com.google.gson.Gson;
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

import ru.home.collaborativeeducation.App;
import ru.home.collaborativeeducation.network.model.CourseSourcePayload;

public class CourseSourcePayloadConverter implements JsonDeserializer<CourseSourcePayload> {

    @Override
    public CourseSourcePayload deserialize(JsonElement json, Type typeOfT,
                                           JsonDeserializationContext context) throws JsonParseException {
        Log.d(App.DEBUG, "Source response: " + json);
        CourseSourcePayload result = new CourseSourcePayload();
        JsonObject root = (JsonObject) json;
        result.setUid(root.get("uid").getAsLong());
        result.setTitle(root.get("title").getAsString());
        result.setCourseUid(root.get("course_uid").getAsLong());
        result.setSource(root.get("source").getAsString());
        Log.d("GSON", result.toString());
        JsonElement users = root.get("users");
        result.setUsers(getUsers(users));
        return result;
    }

    private ArrayList<String> getUsers(JsonElement users) {
        if (users.isJsonObject() || users.isJsonPrimitive()) {
            return getUsers(users.getAsString());
        } else {
            return getUsers(users.getAsJsonArray());
        }
    }

    private ArrayList<String> getUsers(JsonArray jsonArray) {
        ArrayList<String> result = new ArrayList<>();
        for (JsonElement item : jsonArray) {
            result.add((item).getAsString());
        }
        return result;
    }

    private ArrayList<String> getUsers(String jsonArrayAsString) {
        Type usersType = new TypeToken<List<String>>(){}.getType();
        return new Gson().fromJson(jsonArrayAsString, usersType);
    }
}
