package com.kirby510.covid19tracker.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Covid19SummaryResponse {
    @SerializedName("ID")
    String id = null;

    @SerializedName("Message")
    String message = null;

    @SerializedName("Global")
    Global global = null;

    @SerializedName("Countries")
    ArrayList<Country> countries = null;

    @SerializedName("Date")
    String date = null;

    public Global getGlobal() {
        return global;
    }

    public void setGlobal(Global global) {
        this.global = global;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public class Global {
        @SerializedName("NewConfirmed")
        long newConfirmed = 0;

        @SerializedName("TotalConfirmed")
        long totalConfirmed = 0;

        @SerializedName("NewDeaths")
        long newDeaths = 0;

        @SerializedName("TotalDeaths")
        long totalDeaths = 0;

        @SerializedName("NewRecovered")
        long newRecovered = 0;

        @SerializedName("TotalRecovered")
        long totalRecovered = 0;

        @SerializedName("Date")
        String date = null;
    }

    public class Country {
        @SerializedName("ID")
        String id = null;

        @SerializedName("Country")
        String country = null;

        @SerializedName("CountryCode")
        String countryCode = null;

        @SerializedName("Slug")
        String slug = null;

        @SerializedName("NewConfirmed")
        long newConfirmed = 0;

        @SerializedName("TotalConfirmed")
        long totalConfirmed = 0;

        @SerializedName("NewDeaths")
        long newDeaths = 0;

        @SerializedName("TotalDeaths")
        long totalDeaths = 0;

        @SerializedName("NewRecovered")
        long newRecovered = 0;

        @SerializedName("TotalRecovered")
        long totalRecovered = 0;

        @SerializedName("Date")
        String date = null;

        @SerializedName("Premium")
        CountryPremium premium = null;

        public CountryPremium getPremium() {
            return premium;
        }

        public void setPremium(CountryPremium premium) {
            this.premium = premium;
        }
    }

    public class CountryPremium {
        @SerializedName("CountryStats")
        CountryStats countryStats = null;

        public CountryStats getCountryStats() {
            return countryStats;
        }

        public void setCountryStats(CountryStats countryStats) {
            this.countryStats = countryStats;
        }
    }

    public class CountryStats {
        @SerializedName("ID")
        String id = null;

        @SerializedName("CountryISO")
        String countryIso = null;

        @SerializedName("Country")
        String country = null;

        @SerializedName("Continent")
        String continent = null;

        @SerializedName("Population")
        long population = 0;

        @SerializedName("PopulationDensity")
        double populationDensity = 0;

        @SerializedName("MedianAge")
        double medianAge = 0;

        @SerializedName("Aged65Older")
        double aged65Older = 0;

        @SerializedName("Aged70Older")
        double aged70Older = 0;

        @SerializedName("ExtremePoverty")
        double extremePoverty = 0;

        @SerializedName("GdpPerCapita")
        double gdpPerCapita = 0;

        @SerializedName("CvdDeathRate")
        double cvdDeathRate = 0;

        @SerializedName("DiabetesPrevalence")
        double diabetesPrevalence = 0;

        @SerializedName("HandwashingFacilities")
        double handwashingFacilities = 0;

        @SerializedName("HospitalBedsPerThousand")
        double hospitalBedsPerThousand = 0;

        @SerializedName("LifeExpectancy")
        double lifeExpectancy = 0;

        @SerializedName("FemaleSmokers")
        double femaleSmokers = 0;

        @SerializedName("MaleSmokers")
        double maleSmokers = 0;
    }
}
