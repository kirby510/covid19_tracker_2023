package com.kirby510.covid19tracker.ui.screens.covid19.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class Covid19CasesAxisFormatter extends IndexAxisValueFormatter {
    ArrayList<String> items = new ArrayList<>();

    public Covid19CasesAxisFormatter() {

    }

    public Covid19CasesAxisFormatter(ArrayList<String> items) {
        this.items = items;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        int index = (int) value;

        if (index < items.size()) {
            return items.get(index);
        } else {
            return null;
        }
    }
}
