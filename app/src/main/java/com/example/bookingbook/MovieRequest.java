package com.example.bookingbook;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MovieRequest {
    @GET("book.json")
    Call<MovieResponse> getBook(
            @Header("X-Naver-Client-Id") String CLIENT_ID,
            @Header("X-Naver-Client-Secret") String CLIENT_SECRET,
            @Query("query") String query

    );
}
