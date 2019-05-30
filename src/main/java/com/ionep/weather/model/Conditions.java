package com.ionep.weather.model;

public class Conditions {

    private City city;
	private String conditions;
    private String conditionsId;

    public City getCity() {
    	return city;
    }
    
    public Conditions setCity(City city) {
    	this.city = city;
    	return this;
    }
    
    public String getConditions() {
        return conditions;
    }

    public Conditions setConditions(String conditions) {
        this.conditions = conditions;
        return this;
    }

    public String getConditionsId() {
        return conditionsId;
    }

    public Conditions setConditionsId(String conditionsId) {
        this.conditionsId = conditionsId;
        return this;
    }
}
