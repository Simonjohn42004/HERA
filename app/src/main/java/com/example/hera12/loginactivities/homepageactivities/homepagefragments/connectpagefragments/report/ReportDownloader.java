package com.example.hera12.loginactivities.homepageactivities.homepagefragments.connectpagefragments.report;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.hera12.loginactivities.database.PatientDatabase;

import java.io.OutputStream;

@RequiresApi(api = Build.VERSION_CODES.Q)

public class ReportDownloader {

    private final Context context;
    private final PatientDatabase patient;

    public ReportDownloader(Context context, PatientDatabase patient) {
        this.context = context;
        this.patient = patient;
    }


    public void downloadReport() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "PCOD_Report.pdf");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

            Uri uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);

            if (uri != null) {
                try {
                    OutputStream outputStream = resolver.openOutputStream(uri);
                    ReportGenerator reportGenerator = new ReportGenerator(context);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        reportGenerator.generatePDF(outputStream, patient); // Pass the patient object
                    }

                    outputStream.close();

                    Toast.makeText(context, "Report downloaded successfully", Toast.LENGTH_LONG).show();

                    // Intent to view the PDF
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "application/pdf");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Grant permission to read the file
                    context.startActivity(Intent.createChooser(intent, "Open PDF with"));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error downloading the report", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, "Failed to create file", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "This feature is only available on Android 10 and above", Toast.LENGTH_LONG).show();
        }
    }



}

