package com.example.hera12.loginactivities.homepageactivities.homepagefragments.trackpagefragments.fragments.pcodtrackpagecalenderfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hera12.R;
import com.example.hera12.loginactivities.api.dietactivities.FoodAdapter;
import com.example.hera12.loginactivities.api.dietactivities.Query;
import com.example.hera12.loginactivities.api.dietactivities.FoodResponse;
import com.example.hera12.loginactivities.api.dietactivities.NutritionixDietAPI;
import com.example.hera12.loginactivities.api.dietactivities.NutritionixAPIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DietPlanActivity extends AppCompatActivity {

    private EditText foodInput;
    private Button calculateButton;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<FoodResponse.FoodItem> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diet_plan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        foodInput = findViewById(R.id.foodInput);
        calculateButton = findViewById(R.id.calculateButton);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodAdapter = new FoodAdapter(foodList, this);
        recyclerView.setAdapter(foodAdapter);



        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInput = foodInput.getText().toString().trim();

                if (userInput.isEmpty()) {
                    Toast.makeText(DietPlanActivity.this, "Please enter a food item", Toast.LENGTH_SHORT).show();
                }

                // Make API call to get food details
                fetchFoodDetails(userInput);
            }
        });
    }

    private void fetchFoodDetails(String foodName) {

        NutritionixDietAPI api = NutritionixAPIClient.getRetrofitClient().create(NutritionixDietAPI.class);
        Query query = new Query(foodName);

        api.getFoodPlan(query).enqueue(new Callback<FoodResponse>() {
            @Override
            public void onResponse(@NonNull Call<FoodResponse> call, @NonNull Response<FoodResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    foodList.clear();
                    foodList.addAll(response.body().getFoods());

                    // Notify the adapter that data has changed
                    foodAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(DietPlanActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(@NonNull Call<FoodResponse> call, @NonNull Throwable throwable) {

                Toast.makeText(DietPlanActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}