package com.example.hera12.loginactivities.api.dietactivities;

import java.util.List;

public class FoodResponse {

    private List<FoodItem> foods;

    public List<FoodItem> getFoods() {
        return foods;
    }

    public static class FoodItem {
        private String food_name;
        private float nf_calories;
        private float nf_total_fat;
        private float nf_protein;
        private float nf_total_carbohydrate;
        private float nf_sugars;

        // Getters
        public String getFoodName() { return food_name; }
        public float getCalories() { return nf_calories; }
        public float getTotalFat() { return nf_total_fat; }
        public float getProtein() { return nf_protein; }
        public float getTotalCarbohydrate() { return nf_total_carbohydrate; }
        public float getTotalSugar() {
            return nf_sugars;
        }

    }
}
