package com.kirby510.covid19tracker.ui.screens.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.kirby510.covid19tracker.R;
import com.kirby510.covid19tracker.databinding.ActivityCountryListBinding;
import com.kirby510.covid19tracker.domain.model.Country;
import com.kirby510.covid19tracker.domain.model.CountrySort;
import com.kirby510.covid19tracker.ui.screens.covid19.adapter.CountryListAdapter;
import com.kirby510.covid19tracker.ui.viewmodel.Covid19TrackerViewModel;

import java.util.ArrayList;

public class CountryListActivity extends AppCompatActivity {
    private ActivityCountryListBinding binding = null;
    private Covid19TrackerViewModel viewmodel = null;

    SearchView svCountry = null;
    CountryListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCountryListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.mtToolbar);

        viewmodel = new ViewModelProvider(this).get(Covid19TrackerViewModel.class);

        loadView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getCovid19Summary();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options_country_list, menu);

        MenuItem countrySearch = menu.findItem(R.id.country_search);
        svCountry = (SearchView) countrySearch.getActionView();
        svCountry.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                svCountry.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });

        EditText searchEditText = (EditText) svCountry.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(ContextCompat.getColor(this, R.color.white));
        searchEditText.setHint(R.string.search);
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.white_smoke));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.covid19_sort) {
            openContextMenu(binding.mtToolbar);

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.menu_context_country_list_sort, menu);

        menu.setHeaderTitle(R.string.sort_by);

        if (adapter.getSortBy() == CountrySort.NUMBER_OF_ACTIVE_CASES) {
            menu.findItem(R.id.sort_number_of_active_cases).setChecked(true);
        } else if (adapter.getSortBy() == CountrySort.NUMBER_OF_DEATHS) {
            menu.findItem(R.id.sort_number_of_deaths).setChecked(true);
        } else if (adapter.getSortBy() == CountrySort.ACTIVE_CASES_FOR_100K_HAB) {
            menu.findItem(R.id.sort_active_cases_for_100k_hab).setChecked(true);
        } else if (adapter.getSortBy() == CountrySort.DEATHS_FOR_100K_HAB) {
            menu.findItem(R.id.sort_deaths_for_100k_hab).setChecked(true);
        } else {
            menu.findItem(R.id.sort_alphabetically).setChecked(true);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sort_alphabetically) {
            adapter.sortBy(CountrySort.ALPHABETICIALY);
            adapter.getFilter().filter(svCountry.getQuery());

            return true;
        } else if (item.getItemId() == R.id.sort_number_of_active_cases) {
            adapter.sortBy(CountrySort.NUMBER_OF_ACTIVE_CASES);
            adapter.getFilter().filter(svCountry.getQuery());

            return true;
        } else if (item.getItemId() == R.id.sort_number_of_deaths) {
            adapter.sortBy(CountrySort.NUMBER_OF_DEATHS);
            adapter.getFilter().filter(svCountry.getQuery());

            return true;
        } else if (item.getItemId() == R.id.sort_active_cases_for_100k_hab) {
            adapter.sortBy(CountrySort.ACTIVE_CASES_FOR_100K_HAB);
            adapter.getFilter().filter(svCountry.getQuery());

            return true;
        } else if (item.getItemId() == R.id.sort_deaths_for_100k_hab) {
            adapter.sortBy(CountrySort.DEATHS_FOR_100K_HAB);
            adapter.getFilter().filter(svCountry.getQuery());

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (!svCountry.isIconified()) {
            svCountry.setIconified(true);
        } else {
            super.onBackPressed();
        }
    }

    void loadView() {
        registerForContextMenu(binding.mtToolbar);

        binding.sflCountryList.setOnRefreshListener(() -> {
            getCovid19Summary();
        });

        viewmodel.getCountryList().observe(this, countries -> {
            loadAdapter(countries);
        });

        loadAdapter(new ArrayList<>());
    }

    void loadAdapter(ArrayList<Country> countryList) {
        adapter = new CountryListAdapter(this, countryList, (CountryListAdapter.OnItemClickListener) country -> {
            startActivity(new Intent(this, CountryCovid19DetailActivity.class)
                    .putExtra(CountryCovid19DetailActivity.COUNTRY, country));
        });
        binding.rvCountryList.setAdapter(adapter);

        binding.sflCountryList.setRefreshing(false);
    }

    void getCovid19Summary() {
        viewmodel.getCovid19Summary(errorMessage -> {
            runOnUiThread(() -> {
                binding.sflCountryList.setRefreshing(false);

                AlertDialog.Builder builder = new AlertDialog.Builder(CountryListActivity.this);
                builder.setTitle(R.string.error);
                builder.setMessage(errorMessage);
                builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss());
                builder.show();
            });
        });
    }
}