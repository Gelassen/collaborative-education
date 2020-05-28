package ru.home.collaborativeeducation.network.converters;

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

import ru.home.collaborativeeducation.model.Likes;

public class LikesConverter implements JsonDeserializer<Likes> {

    @Override
    public Likes deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Likes likes = new Likes();
        JsonObject root = (JsonObject) json;
        likes.setLikesUid((root.get("likesUid") == null || root.get("likesUid").isJsonNull()) ? -1 : root.get("likesUid").getAsLong());
        likes.setCounter((root.get("counter") == null || root.get("counter").isJsonNull()) ? 0 : root.get("counter").getAsLong());
        likes.setCourseUid((root.get("courseUid") == null || root.get("courseUid").isJsonNull()) ? 0 : root.get("courseUid").getAsLong());
        likes.setUsers(getUsers(root.get("users")));
        return likes;
    }

    private ArrayList<String> getUsers(JsonElement users) {
        if (users == null || users.isJsonNull()) {
            return new ArrayList<>();
        } else if (users.isJsonObject() || users.isJsonPrimitive()) {
            return getUsers(users.getAsString());
        } else if (users.isJsonArray()){
            return getUsers(users.getAsJsonArray());
        } else {
            throw new IllegalArgumentException("Did you pass valid json response? " + users.toString());
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
