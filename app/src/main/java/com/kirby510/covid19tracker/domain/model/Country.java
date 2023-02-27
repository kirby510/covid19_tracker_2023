package com.kirby510.covid19tracker.domain.model;

import java.io.Serializable;
import java.util.Optional;

public class Country implements Serializable {
    String country = "";
    String countryCode = "";
    String slug = "";
    long newConfirmed = 0;
    long totalConfirmed = 0;
    long newDeaths = 0;
    long totalDeaths = 0;
    long newRecovered = 0;
    long totalRecovered = 0;
    String date = "";

    public Country() {

    }

    public Country(Covid19SummaryResponse.Country response) {
        this.country = Optional.ofNullable(response.country).orElse("");
        this.countryCode = Optional.ofNullable(response.countryCode).orElse("");
        this.slug = Optional.ofNullable(response.slug).orElse("");
        this.newConfirmed = response.newConfirmed;
        this.totalConfirmed = response.totalConfirmed;
        this.newDeaths = response.newDeaths;
        this.totalDeaths = response.totalDeaths;
        this.newRecovered = response.newRecovered;
        this.totalRecovered = response.totalRecovered;
        this.date = Optional.ofNullable(response.date).orElse("");
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public long getNewConfirmed() {
        return newConfirmed;
    }

    public void setNewConfirmed(long newConfirmed) {
        this.newConfirmed = newConfirmed;
    }

    public long getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(long totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public long getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(long newDeaths) {
        this.newDeaths = newDeaths;
    }

    public long getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(long totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public long getNewRecovered() {
        return newRecovered;
    }

    public void setNewRecovered(long newRecovered) {
        this.newRecovered = newRecovered;
    }

    public long getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(long totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
