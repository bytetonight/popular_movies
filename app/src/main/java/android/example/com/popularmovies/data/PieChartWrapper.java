package android.example.com.popularmovies.data;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ByteTonight on 03.11.2017.
 */

public class PieChartWrapper {

    private PieChart pieChart;
    private List<PieEntry> entries = new ArrayList<>();


    public PieChartWrapper(Context context, View v) {
        pieChart = (PieChart) v;
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.setClickable(false);
        pieChart.setTouchEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.setRotationEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.alpha(0));
        pieChart.setHoleRadius(90.0f);
        pieChart.setTransparentCircleRadius(0.0f);
        pieChart.setTransparentCircleAlpha(255);
    }

    public void setRatingValue(Double ratingValue) {

        pieChart.setCenterText(String.valueOf(ratingValue.intValue()) +" %");
        pieChart.setCenterTextColor(Color.WHITE);
        float rValue = Float.parseFloat(String.valueOf(ratingValue));
        entries.add(new PieEntry(rValue) );
        entries.add(new PieEntry(100.0f - rValue) );
        Log.v("PieChartWrapper", "ratingValue: "+String.valueOf(rValue));
        PieDataSet dataSet = new PieDataSet(entries, null);
        dataSet.setSliceSpace(0f);
        int[] colors = {Color.MAGENTA, getColorWithAlpha(Color.WHITE, 0.2f)};

        dataSet.setColors( colors  );
        dataSet.setDrawValues(false);
        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.invalidate();
    }

    private static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }
}
