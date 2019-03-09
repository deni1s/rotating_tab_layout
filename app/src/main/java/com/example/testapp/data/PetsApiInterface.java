package com.example.testapp.data;

import com.example.testapp.data.PetResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface PetsApiInterface {
    @GET("xim/api.php")
    Observable<PetResponse> getCatsList(@Query("query") String query);
}
