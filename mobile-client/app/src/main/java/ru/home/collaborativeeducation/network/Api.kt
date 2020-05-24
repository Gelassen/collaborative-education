package ru.home.collaborativeeducation.network

import io.reactivex.Observable
import retrofit2.http.*
import ru.home.collaborativeeducation.model.*
import ru.home.collaborativeeducation.network.model.ApiResponse
import ru.home.collaborativeeducation.network.model.CategoryPayload
import ru.home.collaborativeeducation.network.model.CoursePayload
import ru.home.collaborativeeducation.network.model.CourseSourcePayload

interface Api {

    @get:GET("/v1/category")
    val getAllCategories: Observable<ApiResponse<CategoryPayload>>

    @POST("/v1/category/create")
    fun create(@Body payload: CategoryViewItem): Observable<ApiResponse<CategoryPayload>>

    @GET("/v1/category/{id}/course")
    fun getAllCoursesForCategory(@Path("id") uid: String): Observable<ApiResponse<CoursePayload>>

    @POST("/v1/course/create")
    fun create(@Body payload: CoursePayload): Observable<ApiResponse<CoursePayload>>

    @GET("/v1/category/{id}/course/{courseId}/source")
    fun getAllSourcesForCourse(@Path("id") uid: String, @Path("courseId") courseId: String): Observable<ApiResponse<CourseSourcePayload>>

    @GET("/v1/category/{id}/course/{courseId}/source/meta")
    fun getAllSourcesWithMetaForCourse(@Path("id") uid: String, @Path("courseId") courseId: String)
            : Observable<ApiResponse<CourseWithMetadataAndComments>>

    @POST("/v1/source/create")
    fun create(@Body payload: CourseSourceItem): Observable<ApiResponse<CourseWithMetadataAndComments>>

    @POST("/v1/like/create")
    fun create(@Body payload: CourseWithMetadataAndComments):
            Observable<ApiResponse<CourseWithMetadataAndComments>>
}