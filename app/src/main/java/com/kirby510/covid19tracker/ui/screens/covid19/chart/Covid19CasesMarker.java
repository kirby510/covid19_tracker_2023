package com.kirby510.covid19tracker.ui.screens.covid19.chart;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.kirby510.covid19tracker.R;

public class Covid19CasesMarker extends MarkerView {
    private TextView tvContent;

    public Covid19CasesMarker(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry entry, Highlight highlight) {
        tvContent.setText(String.valueOf((long) entry.getY()));

        super.refreshContent(entry, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {
        if (mOffset == null) {
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
    }
}