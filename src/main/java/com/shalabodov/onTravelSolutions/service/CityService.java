package com.shalabodov.onTravelSolutions.service;

import com.shalabodov.onTravelSolutions.entity.City;

import java.util.Optional;

public interface CityService {

    Optional <City> getByCity(String city);

    int update(String description, String city);

    City save(City city);

    int delete(String city);
}
