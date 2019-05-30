package com.ionep.weather.model;

public class Forecast {

	private String requestId;
	private String cityName;
    private String forecast;

    public Forecast() {

    }

    public Forecast(ForecastRequest request, String forecast) {
        this.requestId = request.getRequestId();
    	this.cityName = request.getCityName();
        this.forecast = forecast;
    }
    
    public String getRequestId() {
        return requestId;
    }

    public Forecast setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public Forecast setCityName(City city) {
        this.cityName = city.getName();
        return this;
    }
    
    public Forecast setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getForecast() {
        return forecast;
    }

    public Forecast setForecast(String forecast) {
        this.forecast = forecast;
        return this;
    }
}
