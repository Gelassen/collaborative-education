package ru.home.collaborativeeducation.network;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.home.collaborativeeducation.network.model.ApiResponse;
import ru.home.collaborativeeducation.network.model.CourseSourcePayload;

public interface TestApi {

    @POST
    Flowable<String> postResult(@Query("category_uid") String uid);

    @POST("/v1/source")
    Observable<ApiResponse<CourseSourcePayload>> getAllSourcesForCategory(@Query("category_uid") String uid);

}
