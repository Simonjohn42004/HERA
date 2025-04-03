package com.example.hera12.loginactivities.homepageactivities.homepagefragments.connectpagefragments.report;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.hera12.loginactivities.database.PatientDatabase;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class ReportGenerator {

    private Context context;

    public ReportGenerator(Context context) {
        this.context = context;
    }

    // Method to generate PDF report

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("Recycle")
    public void generatePDF(OutputStream outputStream, PatientDatabase patient) {
        try {
            PdfWriter writer = new PdfWriter(outputStream);
            com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Adding Patient Information
            document.add(new Paragraph("Patient Health Report"));
            document.add(new Paragraph("Patient Details:"));
            document.add(new Paragraph("Pregnant: " + patient.getIsPregnant()));
            document.add(new Paragraph("Number of Abortions: " + patient.getNumberOfAbortions()));
            document.add(new Paragraph("Waist: " + patient.getWaist()));
            document.add(new Paragraph("Waist-Hip Ratio: " + patient.getWaist_hip_ratio()));

            // Adding Hormonal Levels Table
            Table hormoneTable = new Table(2);
            hormoneTable.addCell("Hormone");
            hormoneTable.addCell("Value");
            hormoneTable.addCell("FSH");
            hormoneTable.addCell(patient.getFSH());
            hormoneTable.addCell("LH");
            hormoneTable.addCell(patient.getLH());
            hormoneTable.addCell("AMH");
            hormoneTable.addCell(patient.getAMH());
            hormoneTable.addCell("TSH");
            hormoneTable.addCell(patient.getTSH());
            document.add(hormoneTable);

            // Generate and add Hormonal Level Graph
            Bitmap chartBitmap = createHormoneChart(patient);
            Uri chartUri = saveBitmap(chartBitmap, "hormone_chart.png");

            if (chartUri != null) {
                try (InputStream imageStream = context.getContentResolver().openInputStream(chartUri)) {
                    if (imageStream != null) {
                        document.add(new com.itextpdf.layout.element.Image(
                                com.itextpdf.io.image.ImageDataFactory.create(imageStream.readAllBytes())));
                    } else {
                        Log.e("ReportGenerator", "Image stream is null");
                    }
                } catch (IOException e) {
                    Log.e("ReportGenerator", "Error opening image stream: " + e.getMessage(), e);
                }
            }


            document.close();
        } catch (Exception e) {
            Log.e("ReportGenerator", "Error generating PDF: " + e.getMessage(), e);
        }
    }


    // Method to generate chart for hormone levels
    private Bitmap createHormoneChart(PatientDatabase patient) {
        BarChart barChart = new BarChart(context);

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, Float.parseFloat(patient.getFSH()))); // FSH at index 0
        entries.add(new BarEntry(1f, Float.parseFloat(patient.getLH()))); // LH at index 1
        entries.add(new BarEntry(2f, Float.parseFloat(patient.getAMH()))); // AMH at index 2
        entries.add(new BarEntry(3f, Float.parseFloat(patient.getTSH()))); // TSH at index 3

        Log.d("ReportGenerator", entries.toString());

        BarDataSet dataSet = new BarDataSet(entries, "Hormonal Levels");

        // Set colors for each bar
        dataSet.setColors(Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA);

        BarData data = new BarData(dataSet);

        // Remove the description
        barChart.getDescription().setEnabled(false); // Disable the description



        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(barChart);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(4);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.setData(data);
        barChart.invalidate();

        // Measure and layout the chart
        barChart.measure(500, 500);
        barChart.layout(0, 0, 500, 500);
        Bitmap chartBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(chartBitmap);
        barChart.draw(canvas);

        return chartBitmap;
    }




    // Save the chart bitmap as a file in MediaStore
    private Uri saveBitmap(Bitmap bitmap, String fileName) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

            ContentResolver resolver = context.getContentResolver();
            Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            if (uri != null) {
                OutputStream outputStream = resolver.openOutputStream(uri);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
                return uri;
            } else {
                Log.e("ReportGenerator", "Failed to create image Uri");
                return null;
            }
        } catch (Exception e) {
            Log.e("ReportGenerator", "Error saving bitmap: " + e.getMessage(), e);
            return null;
        }
    }

    public static class DayAxisValueFormatter extends ValueFormatter {
        private final BarLineChartBase<?> chart;
        public DayAxisValueFormatter(BarLineChartBase<?> chart) {
            this.chart = chart;
        }
        @Override
        public String getFormattedValue(float value) {
            switch ((int) value) { // Cast float to int for matching
                case 0:
                    return "FSH";
                case 1:
                    return "LH";
                case 2:
                    return "AMH";
                case 3:
                    return "TSH";
                default:
                    return "";
            }
        }
    }
}


