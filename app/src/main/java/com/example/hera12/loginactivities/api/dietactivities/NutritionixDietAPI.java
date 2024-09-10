package com.example.hera12.loginactivities.api.dietactivities;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NutritionixDietAPI {

    @Headers({
            "x-app-id: 1c4b0f1e",
            "x-app-key: 6b236d26e577f8dfc25eb3866ea4d369",
            "Content-Type: application/json"
    })
    @POST("v2/natural/nutrients")
    Call<FoodResponse> getFoodPlan(@Body Query query);
}
