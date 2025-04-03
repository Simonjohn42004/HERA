package com.example.hera12.loginactivities.api.dietactivities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hera12.R;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{

    private List<FoodResponse.FoodItem> foodList;
    private Context context;

    public FoodAdapter(List<FoodResponse.FoodItem> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_card, parent, false);
        return new FoodViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {

        FoodResponse.FoodItem foodItem = foodList.get(position);

        holder.foodName.setText(foodItem.getFoodName());
        holder.calories.setText("Calories: " + foodItem.getCalories());
        holder.protein.setText("Protein: " + foodItem.getProtein() + "g");
        holder.fat.setText("Fat: " + foodItem.getTotalFat() + "g");
        holder.carbs.setText("Carbohydrates: " + foodItem.getTotalCarbohydrate() + "g");
        holder.sugar.setText("Sugar: " + foodItem.getTotalSugar() + "g");

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, calories, protein, fat, carbs, sugar;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            calories = itemView.findViewById(R.id.calories);
            protein = itemView.findViewById(R.id.protein);
            fat = itemView.findViewById(R.id.fat);
            carbs = itemView.findViewById(R.id.carbs);
            sugar = itemView.findViewById(R.id.sugar);
        }
    }
}