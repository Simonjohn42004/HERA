package com.example.hera12.loginactivities.homepageactivities.homepagefragments.trackpagefragments.fragments.pcodtrackpagecalenderfragment;

import android.annotation.SuppressLint;
import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

    // TextViews for the total nutrition count
    private TextView totalCaloriesText, totalProteinText, totalFatText, totalSugarText, totalCarbsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diet_plan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()).toPlatformInsets();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            }
            return insets;
        });

        // Initialize TextView for total nutrition counts
        totalCaloriesText = findViewById(R.id.calories);
        totalProteinText = findViewById(R.id.protein);
        totalFatText = findViewById(R.id.fat);
        totalSugarText = findViewById(R.id.sugar);
        totalCarbsText = findViewById(R.id.carbs);

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
                } else {
                    // Make API call to get food details
                    fetchFoodDetails(userInput);
                }
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

                    // Calculate and display the total nutrition count
                    calculateTotalNutrition();
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

    @SuppressLint("SetTextI18n")
    private void calculateTotalNutrition() {
        // Initialize the totals
        float totalCalories = 0;
        float totalProtein = 0;
        float totalFat = 0;
        float totalSugar = 0;
        float totalCarbs = 0;

        // Iterate through foodList to sum the values
        for (FoodResponse.FoodItem item : foodList) {
            totalCalories += item.getCalories();
            totalProtein += item.getProtein();
            totalFat += item.getTotalFat();
            totalSugar += item.getTotalSugar();
            totalCarbs += item.getTotalCarbohydrate();
        }

        // Update the TextViews with the totals
        totalCaloriesText.setText("Total Calories: " + totalCalories);
        totalProteinText.setText("Total Protein: " + totalProtein + "g");
        totalFatText.setText("Total Fat: " + totalFat + "g");
        totalSugarText.setText("Total Sugar: " + totalSugar + "g");
        totalCarbsText.setText("Total Carbohydrates: " + totalCarbs + "g");
    }
}


