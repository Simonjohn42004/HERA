package com.example.hera12.loginactivities.homepageactivities.homepagefragments.connectpagefragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hera12.R;

class GridAdapter extends BaseAdapter {
    private Context context;
    private final String[] metrics;
    private final int[] imageIcons;
    private final int[] metricValues;

    public GridAdapter(Context context, String[] metrics, int[] imageIcons, int[] metricValues) {
        this.context = context;
        this.metrics = metrics;
        this.imageIcons = imageIcons;
        this.metricValues = metricValues;
    }

    @Override
    public int getCount() {
        return metrics.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridItem;
        if (convertView == null) {
            gridItem = inflater.inflate(R.layout.health_dashboard_grid_view_item, null);

            // Find views for the metric title, value, and icon
            TextView metricTitle = gridItem.findViewById(R.id.metricTitle);
            TextView metricValue = gridItem.findViewById(R.id.metricValue);
            ImageView metricIcon = gridItem.findViewById(R.id.metricIcon);

            // Set the metric title, value, and icon for each grid item
            metricTitle.setText(metrics[position]);
            metricValue.setText(String.valueOf(metricValues[position]));
            metricIcon.setImageResource(imageIcons[position]);
        } else {
            gridItem = convertView;
        }

        return gridItem;
    }
}

