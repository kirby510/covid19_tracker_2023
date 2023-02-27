package com.kirby510.covid19tracker.ui.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kirby510.covid19tracker.domain.model.Country;
import com.kirby510.covid19tracker.domain.model.Covid19DayOneTotalAllStatusResponse;
import com.kirby510.covid19tracker.domain.model.Covid19DayOneTotalResponse;
import com.kirby510.covid19tracker.domain.model.Covid19SummaryResponse;
import com.kirby510.covid19tracker.domain.model.DailyCovid19Cases;
import com.kirby510.covid19tracker.domain.repository.Covid19APIRequest;

import java.util.ArrayList;

public class Covid19TrackerViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Country>> _countryList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<DailyCovid19Cases>> _dailyCovid19CasesList = new MutableLiveData<>();

    Country country = null;

    public Covid19TrackerViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<Country>> getCountryList() {
        return _countryList;
    }

    public LiveData<ArrayList<DailyCovid19Cases>> getDailyCovid19CasesList() {
        return _dailyCovid19CasesList;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void getCovid19Summary(final OnAPIErrorListener listener) {
        Bundle bundle = new Bundle();
        bundle.putString(Covid19APIRequest.DIRECTORY, "summary");
        bundle.putString(Covid19APIRequest.METHOD, Covid19APIRequest.METHOD_GET);

        Covid19APIRequest apiRequest = new Covid19APIRequest(getApplication().getApplicationContext(), new Covid19APIRequest.OnExecuteListener() {
            @Override
            public void onSuccess(String message) {
                Log.i("Covid19Tracker", "Message: " + message);

                Covid19SummaryResponse response = new Gson().fromJson(message, Covid19SummaryResponse.class);

                ArrayList<Country> countryList = new ArrayList<>();

                if (response.getCountries() != null) {
                    for (Covid19SummaryResponse.Country countryResponse: response.getCountries()) {
                        countryList.add(new Country(countryResponse));
                    }
                }

                _countryList.postValue(countryList);
            }

            @Override
            public void onError(String errorMessage) {
                if (listener != null) {
                    listener.onError(errorMessage);
                }
            }
        });

        apiRequest.execute(bundle);
    }

    public void getCovid19DayOneTotal(String country, String status, final OnAPIErrorListener listener) {
        Bundle bundle = new Bundle();
        bundle.putString(Covid19APIRequest.DIRECTORY, "total/dayone/country/" + country + "/status/" + status);
        bundle.putString(Covid19APIRequest.METHOD, Covid19APIRequest.METHOD_GET);

        Covid19APIRequest apiRequest = new Covid19APIRequest(getApplication().getApplicationContext(), new Covid19APIRequest.OnExecuteListener() {
            @Override
            public void onSuccess(String message) {
                Log.i("Covid19Tracker", "Message: " + message);

                ArrayList<Covid19DayOneTotalResponse.Country> response = new Gson().fromJson(message, new TypeToken<ArrayList<Covid19DayOneTotalResponse.Country>>(){}.getType());

                ArrayList<DailyCovid19Cases> dailyCovid19CasesList = new ArrayList<>();

                if (response != null) {
                    for (Covid19DayOneTotalResponse.Country countryResponse: response) {
                        dailyCovid19CasesList.add(new DailyCovid19Cases(countryResponse));
                    }
                }

                _dailyCovid19CasesList.postValue(dailyCovid19CasesList);
            }

            @Override
            public void onError(String errorMessage) {
                if (listener != null) {
                    listener.onError(errorMessage);
                }
            }
        });

        apiRequest.execute(bundle);
    }

    public void getCovid19DayOneTotalAllStatus(String country, final OnAPIErrorListener listener) {
        Bundle bundle = new Bundle();
        bundle.putString(Covid19APIRequest.DIRECTORY, "total/dayone/country/" + country);
        bundle.putString(Covid19APIRequest.METHOD, Covid19APIRequest.METHOD_GET);

        Covid19APIRequest apiRequest = new Covid19APIRequest(getApplication().getApplicationContext(), new Covid19APIRequest.OnExecuteListener() {
            @Override
            public void onSuccess(String message) {
                Log.i("Covid19Tracker", "Message: " + message);

                ArrayList<Covid19DayOneTotalAllStatusResponse.Country> response = new Gson().fromJson(message, new TypeToken<ArrayList<Covid19DayOneTotalAllStatusResponse.Country>>(){}.getType());

                ArrayList<DailyCovid19Cases> dailyCovid19CasesList = new ArrayList<>();

                if (response != null) {
                    for (Covid19DayOneTotalAllStatusResponse.Country countryResponse: response) {
                        dailyCovid19CasesList.add(new DailyCovid19Cases(countryResponse));
                    }
                }

                _dailyCovid19CasesList.postValue(dailyCovid19CasesList);
            }

            @Override
            public void onError(String errorMessage) {
                if (listener != null) {
                    listener.onError(errorMessage);
                }
            }
        });

        apiRequest.execute(bundle);
    }

    public interface OnAPIErrorListener {
        void onError(String errorMessage);
    }
}
