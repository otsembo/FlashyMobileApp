package com.flashy.application.network.services

import com.flashy.application.models.DataResponse
import com.flashy.application.models.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    //adding the rest endpoints
    //USERS END POINTS
    @POST("/users")
    suspend fun registerUser(@Body user:User) : Response<DataResponse>

    //CATEGORIES END POINTS
    @GET("/categories")
    suspend fun getCategories() : Response<DataResponse>

    //PRODUCTS END POINTS
    @GET("/products/latest")
    suspend fun getLatestProducts() : Response<DataResponse>

    //IMAGES END POINTS
    @GET("/images")
    suspend fun getAllImages() : Response<DataResponse>

    //CART END POINTS
    @GET("/cart/mycart/{id}")
    suspend fun getAllCartItems(@Path("id") id:Int) : Response<DataResponse>

}