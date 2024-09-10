package com.example.hera12.loginactivities.apputils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.hera12.R;

public class StringNumberPickerDialog {
    Context context;
    String finalString;

    public StringNumberPickerDialog(Context context) {
        this.context = context;

        final AlertDialog.Builder d = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.custom_number_picker_layout, null);
        d.setTitle("Title");
        d.setMessage("Message");
        d.setView(dialogView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
        numberPicker.setMaxValue(7);
        numberPicker.setMinValue(0);
        String[] bloodGroup = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        numberPicker.setDisplayedValues(bloodGroup);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                finalString = bloodGroup[i1];
            }
        });
        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "number finally picked is "+ finalString, Toast.LENGTH_SHORT).show();

            }
        });
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "no number picked", Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();
    }
}
