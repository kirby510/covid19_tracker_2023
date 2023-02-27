package com.kirby510.covid19tracker.domain.model;

import com.google.gson.annotations.SerializedName;

public class Covid19DayOneTotalAllStatusResponse {
    public class Country {
        @SerializedName("Country")
        String country = null;

        @SerializedName("CountryCode")
        String countryCode = null;

        @SerializedName("Province")
        String province = null;

        @SerializedName("City")
        String city = null;

        @SerializedName("CityCode")
        String cityCode = null;

        @SerializedName("Lat")
        String lat = null;

        @SerializedName("Lon")
        String lon = null;

        @SerializedName("Confirmed")
        long confirmed = 0;

        @SerializedName("Deaths")
        long deaths = 0;

        @SerializedName("Recovered")
        long recovered = 0;

        @SerializedName("Active")
        long active = 0;

        @SerializedName("Date")
        String date = null;
    }
}
