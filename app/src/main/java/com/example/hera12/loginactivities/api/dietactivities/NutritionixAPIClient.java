package com.example.hera12.loginactivities.api.dietactivities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NutritionixAPIClient {

    private static final String BASE_URL = "https://trackapi.nutritionix.com/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
