package com.example.hera12.loginactivities.api.fitnessactivities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hera12.R;

import java.util.List;

public class YogaAdapter extends RecyclerView.Adapter<YogaAdapter.PoseViewHolder>{

    private Context context;
    private List<Pose> poseList;

    public YogaAdapter(Context context, List<Pose> poseList) {
        this.context = context;
        this.poseList = poseList;
    }

    @NonNull
    @Override
    public PoseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pose, parent, false);
        return new PoseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoseViewHolder holder, int position) {
        Pose pose = poseList.get(position);
        holder.textViewName.setText(pose.getName());
        holder.textViewDescription.setText(pose.getDescription());

        Glide.with(context)
                .load(pose.getImageUrl() )
                .placeholder(R.drawable.placeholder) // Placeholder image
                .error(R.drawable.baseline_error_24) // Error image
                .into(holder.imageViewPose);
    }

    @Override
    public int getItemCount() {
        return poseList.size();
    }

    public static class PoseViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPose;
        TextView textViewName;
        TextView textViewDescription;

        public PoseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPose = itemView.findViewById(R.id.imageViewPose);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }
}
