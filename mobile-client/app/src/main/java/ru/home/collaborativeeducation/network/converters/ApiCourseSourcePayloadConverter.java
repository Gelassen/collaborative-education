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

import ru.home.collaborativeeducation.network.model.ApiResponse;
import ru.home.collaborativeeducation.network.model.CourseSourcePayload;
import ru.home.collaborativeeducation.network.model.Status;

public class ApiCourseSourcePayloadConverter implements JsonDeserializer<ApiResponse<CourseSourcePayload>> {

    private CourseSourcePayloadConverter converter = new CourseSourcePayloadConverter();

    public static Type getType() {
        return new TypeToken<ApiResponse<CourseSourcePayload>>(){}.getType();
    }

    @Override
    public ApiResponse<CourseSourcePayload> deserialize(JsonElement json, Type typeOfT,
                                           JsonDeserializationContext context) throws JsonParseException {
        ApiResponse<CourseSourcePayload> result = new ApiResponse<CourseSourcePayload>();
        JsonObject root = (JsonObject) json;
        JsonObject statusJson = root.get("status").getAsJsonObject();
//        JsonArray payload = statusJson.get("payload").getAsJsonArray();

        Status status = new Status();
        status.setMessage(statusJson.get("message").getAsString());
        status.setPayload(getPayload(statusJson.get("payload"), context));

        result.setCode(root.get("code").getAsInt());
        result.setStatus(status);
        return result;
    }

    private List<CourseSourcePayload> getPayload(JsonElement payload, JsonDeserializationContext context) {
        if (payload.isJsonObject() || payload.isJsonPrimitive()) {
            return getPayload(payload.getAsString());
        } else {
            return getPayload(payload.getAsJsonArray(), context);
        }
    }

    private List<CourseSourcePayload> getPayload(JsonArray payload, JsonDeserializationContext context) {
        List<CourseSourcePayload> result = new ArrayList<>();
        for (int id = 0; id < payload.size(); id++) {
            result.add(converter.deserialize(payload.get(id), new TypeToken<CourseSourcePayload>(){}.getType(), context));
        }
        return result;
    }

 /*   private ArrayList<CourseSourcePayload> getPayload(JsonArray jsonArray) {
        ArrayList<CourseSourcePayload> result = new ArrayList<>();
        for (JsonElement item : jsonArray) {
            result.add(((JsonObject) item).getAsString());
        }
        return result;
    }*/

    private List<CourseSourcePayload> getPayload(String jsonArrayAsString) {
        Type usersType = new TypeToken<List<CourseSourcePayload>>(){}.getType();
        return new Gson().fromJson(jsonArrayAsString, usersType);
    }
}
