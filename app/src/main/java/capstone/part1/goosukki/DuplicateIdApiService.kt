package capstone.part1.goosukki

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DuplicateIdApiService {
    @GET("/api/members/duplicate/id")
    fun getDuplicateId(@Query("id") id: String) : Call<Duplicate>
}