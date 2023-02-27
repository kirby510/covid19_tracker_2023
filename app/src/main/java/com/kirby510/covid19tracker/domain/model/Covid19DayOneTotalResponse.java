package com.kirby510.covid19tracker.domain.model;

import com.google.gson.annotations.SerializedName;

public class Covid19DayOneTotalResponse {
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

        @SerializedName("Cases")
        long cases = 0;

        @SerializedName("Status")
        String status = null;

        @SerializedName("Date")
        String date = null;
    }
}
