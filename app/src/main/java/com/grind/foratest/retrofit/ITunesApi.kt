package com.grind.foratest.retrofit

import com.grind.foratest.models.SearchResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesApi {

    @GET("search")
    fun searchAlbum(@Query("term") searchSequence: String,
                    @Query("entity") entity: String,
                    @Query("limit") limit:Int) : Observable<SearchResponse>

    @GET("lookup")
    fun getAlbumInfo(@Query("id") albumId: Int,
                     @Query("entity") entity: String): Observable<SearchResponse>
}