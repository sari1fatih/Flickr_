package com.appcent.flickr.RestApi;

import com.appcent.flickr.Entity.FlickrData;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RestInterface {
    @GET(RestApiBaseUrl.URL)
    Call<FlickrData> getRepo();
}
