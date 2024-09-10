package com.example.hera12.loginactivities.homepageactivities.homepagefragments.trackpagefragments.fragments.pcodtrackpagecalenderfragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hera12.R;
import com.example.hera12.loginactivities.api.fitnessactivities.Pose;
import com.example.hera12.loginactivities.api.fitnessactivities.YogaAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ExcerciseActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://yoga-api-nzy4.onrender.com/v1";
    private OkHttpClient client;
    private YogaAdapter adapter;
    private List<Pose> poseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Button buttonSearch;
    private static final int TIMEOUT = 30; // Timeout in seconds
    private Spinner categorySpinner;
    private List<String> categoryNames = new ArrayList<>();
    private List<String> categoryIds = new ArrayList<>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excercise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewPoses);
        buttonSearch = findViewById(R.id.buttonSearch);

        client = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new YogaAdapter(ExcerciseActivity.this, poseList);
        recyclerView.setAdapter(adapter);

        // Initialize the Spinner
        categorySpinner = findViewById(R.id.categorySpinner);

        // Fetch categories and populate the spinner
        fetchCategories();

        // Set up the spinner's item selected listener
        setupSpinner();

        buttonSearch.setOnClickListener(v -> {
            // Get the selected category ID from the spinner
            int selectedPosition = categorySpinner.getSelectedItemPosition();
            if (selectedPosition != AdapterView.INVALID_POSITION) {
                String selectedCategoryId = categoryIds.get(selectedPosition);

                // Fetch yoga poses for the selected category
                fetchYogaData(selectedCategoryId);
            } else {
                Toast.makeText(ExcerciseActivity.this, "Please select a category", Toast.LENGTH_LONG).show();
            }
        });




    }

    private void fetchYogaData(String categoryId) {
        String url = BASE_URL + "/categories?id=" + Uri.encode(categoryId);

        Log.d("ExerciseActivity", "Request URL: " + url);


        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(ExcerciseActivity.this, "Failed to fetch category data. Please try again.", Toast.LENGTH_LONG).show();
                    Log.e("ExerciseActivity", "Network request failed", e);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    runOnUiThread(() -> handleCategoryResponse(responseData));
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(ExcerciseActivity.this, "Server error: " + response.code(), Toast.LENGTH_LONG).show();
                        Log.e("ExerciseActivity", "Server response error: " + response.code() + " - " + response.message());
                    });
                }
            }
        });
    }

    private void handleCategoryResponse(String responseData) {
        try {
            // Parse the response JSON
            JSONObject jsonResponse = new JSONObject(responseData);

            // Get the "poses" array from the response
            JSONArray posesArray = jsonResponse.getJSONArray("poses");

            Log.d("ExerciseActivity", "Response data: " + responseData);

            // Clear the existing list of poses
            poseList.clear();

            // Iterate through the poses array
            for (int i = 0; i < posesArray.length(); i++) {
                JSONObject pose = posesArray.getJSONObject(i);

                // Extract the required attributes
                String name = pose.optString("english_name", "Unknown Pose");
                String description = pose.optString("pose_description", "No description available");
                String imageUrl = pose.optString("url_png", null);

                // Add the pose to the list
                poseList.add(new Pose(name, description, imageUrl));
            }

            // Notify the adapter that the data has changed
            runOnUiThread(() -> adapter.notifyDataSetChanged());

        } catch (Exception e) {
            runOnUiThread(() -> {
                Toast.makeText(ExcerciseActivity.this, "Failed to parse category data. Please try again.", Toast.LENGTH_LONG).show();
                Log.e("ExerciseActivity", "JSON parsing error", e);
            });
        }
    }


    private void fetchCategories() {
        String url = BASE_URL + "/categories";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(ExcerciseActivity.this, "Failed to fetch categories. Please try again.", Toast.LENGTH_LONG).show();
                    Log.e("ExerciseActivity", "Network request failed", e);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    runOnUiThread(() -> handleCategoriesResponse(responseData));
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(ExcerciseActivity.this, "Server error: " + response.code(), Toast.LENGTH_LONG).show();
                        Log.e("ExerciseActivity", "Server response error: " + response.code() + " - " + response.message());
                    });
                }
            }
        });
    }

    private void handleCategoriesResponse(String responseData) {
        try {
            // Assuming response is a JSON array of categories
            JSONArray categoriesArray = new JSONArray(responseData);
            categoryNames.clear();
            categoryIds.clear();

            for (int i = 0; i < categoriesArray.length(); i++) {
                JSONObject category = categoriesArray.getJSONObject(i);
                String name = category.getString("category_name");
                String id = category.getString("id");

                categoryNames.add(name);   // Add category name for the spinner
                categoryIds.add(id);       // Keep track of corresponding category ID
            }

            // Populate the spinner
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(spinnerAdapter);

        } catch (JSONException e) {
            Toast.makeText(this, "Failed to parse categories. Please try again.", Toast.LENGTH_LONG).show();
            Log.e("ExerciseActivity", "JSON parsing error", e);
        }
    }

    private void setupSpinner() {
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected category ID based on the position
                String selectedCategoryId = categoryIds.get(position);

                // Now fetch yoga poses for the selected category using the ID
                fetchYogaData(selectedCategoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case when no selection is made (optional)
            }
        });
    }







}