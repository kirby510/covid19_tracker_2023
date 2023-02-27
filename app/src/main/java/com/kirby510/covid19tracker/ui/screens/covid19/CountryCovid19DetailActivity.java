package com.kirby510.covid19tracker.ui.screens.covid19;

import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.kirby510.covid19tracker.R;
import com.kirby510.covid19tracker.databinding.ActivityCountryCovid19DetailBinding;
import com.kirby510.covid19tracker.domain.model.Country;
import com.kirby510.covid19tracker.domain.model.DailyCovid19Cases;
import com.kirby510.covid19tracker.ui.screens.covid19.chart.Covid19CasesAxisFormatter;
import com.kirby510.covid19tracker.ui.screens.covid19.chart.Covid19CasesMarker;
import com.kirby510.covid19tracker.ui.viewmodel.Covid19TrackerViewModel;
import com.kirby510.covid19tracker.utils.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class CountryCovid19DetailActivity extends AppCompatActivity {
    private ActivityCountryCovid19DetailBinding binding = null;
    private Covid19TrackerViewModel viewmodel = null;

    public static final String COUNTRY = "country";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCountryCovid19DetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.mtToolbar);

        viewmodel = new ViewModelProvider(this).get(Covid19TrackerViewModel.class);

        if (getIntent() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                viewmodel.setCountry(getIntent().getSerializableExtra(COUNTRY, Country.class));
            } else {
                @SuppressWarnings("DEPRECATION")
                Serializable country = getIntent().getSerializableExtra(COUNTRY);

                if (country != null) {
                    viewmodel.setCountry((Country) country);
                }
            }

        }

        loadView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getCovid19DayOneTotal();
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();

        return super.onSupportNavigateUp();
    }

    void loadView() {
        binding.sflCovid19.setOnRefreshListener(() -> {
            getCovid19DayOneTotal();
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(viewmodel.getCountry().getCountry());

        binding.tvNumberOfActiveCases.setText(String.format("%,d", viewmodel.getCountry().getNewConfirmed()));
        binding.tvNumberOfDeaths.setText(String.format("%,d", viewmodel.getCountry().getNewDeaths()));
        binding.tvActiveCasesFor100kHab.setText(String.format("%,d", viewmodel.getCountry().getTotalConfirmed() / 100000));
        binding.tvDeathsFor100kHab.setText(String.format("%,d", viewmodel.getCountry().getTotalDeaths() / 100000));

        binding.tvLastUpdated.setText(getString(R.string.last_updated, DateUtils.getInstance(this).getDateFromString(viewmodel.getCountry().getDate(), null, null)));

        viewmodel.getDailyCovid19CasesList().observe(this, dailyCovid19CasesList -> loadLineChart(dailyCovid19CasesList));
    }

    void getCovid19DayOneTotal() {
        viewmodel.getCovid19DayOneTotal(viewmodel.getCountry().getSlug(), "confirmed", errorMessage -> {
            runOnUiThread(() -> {
                binding.sflCovid19.setRefreshing(false);

                AlertDialog.Builder builder = new AlertDialog.Builder(CountryCovid19DetailActivity.this);
                builder.setTitle(R.string.error);
                builder.setMessage(errorMessage);
                builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss());
                builder.show();
            });
        });
    }

    void loadLineChart(ArrayList<DailyCovid19Cases> dailyCovid19CasesList) {
        binding.lcCovid19.setVisibility(View.VISIBLE);

        ArrayList<String> covid19DayList = new ArrayList<>();
        ArrayList<Entry> covid19DailyCasesList = new ArrayList<>();

        for (DailyCovid19Cases dailyCovid19Cases: dailyCovid19CasesList) {
            covid19DayList.add(DateUtils.getInstance(this).getDateFromString(dailyCovid19Cases.getDate(), "yyyy-MM-dd'T'HH:mm:ss'Z'", "dd MMM yyyy"));
            covid19DailyCasesList.add(new Entry((float) covid19DailyCasesList.size(), (float) dailyCovid19Cases.getConfirmed()));
        }

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);

        int colorRes;

        if (typedValue.resourceId != 0) {
            colorRes = typedValue.resourceId;
        } else {
            colorRes = typedValue.data;
        }

        binding.lcCovid19.animateX(1200, Easing.EaseInSine);
        binding.lcCovid19.getDescription().setEnabled(false);
        binding.lcCovid19.getDescription().setTextColor(ContextCompat.getColor(this, colorRes));

        binding.lcCovid19.getXAxis().setDrawGridLines(false);
        binding.lcCovid19.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        binding.lcCovid19.getXAxis().setGranularity(1f);
        binding.lcCovid19.getXAxis().setTextColor(ContextCompat.getColor(this, colorRes));
        binding.lcCovid19.getXAxis().setValueFormatter(new Covid19CasesAxisFormatter(covid19DayList));

        binding.lcCovid19.getAxisLeft().setTextColor(ContextCompat.getColor(this, colorRes));
        binding.lcCovid19.getAxisRight().setTextColor(ContextCompat.getColor(this, colorRes));
        binding.lcCovid19.getAxisRight().setEnabled(false);
        binding.lcCovid19.setExtraRightOffset(30f);

        binding.lcCovid19.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        binding.lcCovid19.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        binding.lcCovid19.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        binding.lcCovid19.getLegend().setTextColor(ContextCompat.getColor(this, colorRes));
        binding.lcCovid19.getLegend().setTextSize(15f);
        binding.lcCovid19.getLegend().setEnabled(false);
        binding.lcCovid19.getLegend().setForm(Legend.LegendForm.LINE);

        LineDataSet cases = new LineDataSet(covid19DailyCasesList, getString(R.string.cases));
        cases.setLineWidth(3f);
        cases.setValueTextSize(15f);
        cases.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        cases.setColor(ContextCompat.getColor(this, R.color.blue));
        cases.setValueTextColor(ContextCompat.getColor(this, R.color.blue));
        cases.enableDashedLine(20F, 10F, 0F);
        cases.setDrawValues(false);

        ArrayList<ILineDataSet> dataSet = new ArrayList<>();
        dataSet.add(cases);

        LineData lineData = new LineData(dataSet);
        binding.lcCovid19.setData(lineData);

        binding.lcCovid19.setMarker(new Covid19CasesMarker(this, R.layout.marker_covid19_cases));
        binding.lcCovid19.invalidate();

        binding.sflCovid19.setRefreshing(false);
    }
}