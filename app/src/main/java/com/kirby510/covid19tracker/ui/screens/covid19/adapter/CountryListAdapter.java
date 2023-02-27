package com.kirby510.covid19tracker.ui.screens.covid19.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kirby510.covid19tracker.databinding.ItemCountryBinding;
import com.kirby510.covid19tracker.domain.model.Country;
import com.kirby510.covid19tracker.domain.model.CountrySort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ItemViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<Country> countryList = new ArrayList<>();
    private ArrayList<Country> filteredCountryList = new ArrayList<>();
    OnItemClickListener listener = null;
    private ItemFilter mFilter = new ItemFilter();
    CountrySort countrySort = CountrySort.ALPHABETICIALY;

    public CountryListAdapter(Context context, ArrayList<Country> countryList) {
        this.mContext = context;
        this.countryList.clear();
        this.countryList.addAll(countryList);
        this.filteredCountryList.clear();
        this.filteredCountryList.addAll(countryList);
    }

    public CountryListAdapter(Context context, ArrayList<Country> countryList, OnItemClickListener listener) {
        this.mContext = context;
        this.countryList.clear();
        this.countryList.addAll(countryList);
        this.filteredCountryList.clear();
        this.filteredCountryList.addAll(countryList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(ItemCountryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Country country = filteredCountryList.get(position);

        holder.cvCountry.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(country);
            }
        });

        holder.tvCountry.setText(country.getCountry());
    }

    @Override
    public int getItemCount() {
        return filteredCountryList.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public CountrySort getSortBy() {
        return countrySort;
    }

    public void sortBy(CountrySort countrySort) {
        this.countrySort = countrySort;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cvCountry;
        AppCompatTextView tvCountry;

        public ItemViewHolder(@NonNull ItemCountryBinding itemBinding) {
            super(itemBinding.getRoot());

            cvCountry = itemBinding.cvCountry;
            tvCountry = itemBinding.tvCountry;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Country country);
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            final ArrayList<Country> filteredCountryList = new ArrayList<>();

            if (!charSequence.toString().isEmpty()) {
                for (int i = 0; i < countryList.size(); i++) {
                    Country country = countryList.get(i);

                    if (country.getCountry().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredCountryList.add(country);
                    }
                }
            } else {
                filteredCountryList.addAll(countryList);
            }

            if (countrySort == CountrySort.NUMBER_OF_ACTIVE_CASES) {
                filteredCountryList.sort(Comparator.comparingLong(Country::getNewConfirmed));
                Collections.reverse(filteredCountryList);
            } else if (countrySort == CountrySort.NUMBER_OF_DEATHS) {
                filteredCountryList.sort(Comparator.comparingLong(Country::getNewDeaths));
                Collections.reverse(filteredCountryList);
            } else if (countrySort == CountrySort.ACTIVE_CASES_FOR_100K_HAB) {
                filteredCountryList.sort(Comparator.comparingLong(Country::getTotalConfirmed));
                Collections.reverse(filteredCountryList);
            } else if (countrySort == CountrySort.DEATHS_FOR_100K_HAB) {
                filteredCountryList.sort(Comparator.comparingLong(Country::getTotalDeaths));
                Collections.reverse(filteredCountryList);
            } else {
                filteredCountryList.sort(Comparator.comparing(Country::getCountry));
            }

            FilterResults results = new FilterResults();
            results.values = filteredCountryList;
            results.count = filteredCountryList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredCountryList.clear();
            filteredCountryList.addAll((ArrayList<Country>) filterResults.values);

            notifyDataSetChanged();
        }
    }
}
