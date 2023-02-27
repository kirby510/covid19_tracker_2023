package com.kirby510.covid19tracker.domain.model;

public class DailyCovid19Cases {
    String country = "";
    String countryCode = "";
    String province = "";
    String city = "";
    String cityCode = "";
    String lat = "";
    String lon = "";
    long confirmed = 0;
    long deaths = 0;
    long recovered = 0;
    long active = 0;
    String date = "";

    public DailyCovid19Cases() {

    }

    public DailyCovid19Cases(Covid19DayOneTotalResponse.Country response) {
        this.country = response.country;
        this.countryCode = response.countryCode;
        this.province = response.province;
        this.city = response.city;
        this.cityCode = response.cityCode;
        this.lat = response.lat;
        this.lon = response.lon;

        if (response.status.equals("confirmed")) {
            this.confirmed = response.cases;
        } else if (response.status.equals("deaths")) {
            this.deaths = response.cases;
        } else if (response.status.equals("recovered")) {
            this.recovered = response.cases;
        } else if (response.status.equals("active")) {
            this.active = response.cases;
        }

        this.date = response.date;
    }

    public DailyCovid19Cases(Covid19DayOneTotalAllStatusResponse.Country response) {
        this.country = response.country;
        this.countryCode = response.countryCode;
        this.province = response.province;
        this.city = response.city;
        this.cityCode = response.cityCode;
        this.lat = response.lat;
        this.lon = response.lon;
        this.confirmed = response.confirmed;
        this.deaths = response.deaths;
        this.recovered = response.recovered;
        this.active = response.active;
        this.date = response.date;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public long getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(long confirmed) {
        this.confirmed = confirmed;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public long getRecovered() {
        return recovered;
    }

    public void setRecovered(long recovered) {
        this.recovered = recovered;
    }

    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
