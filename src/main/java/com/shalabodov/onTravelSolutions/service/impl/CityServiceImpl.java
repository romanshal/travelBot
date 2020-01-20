package com.shalabodov.onTravelSolutions.service.impl;

import com.shalabodov.onTravelSolutions.entity.City;
import com.shalabodov.onTravelSolutions.repository.CityRepository;
import com.shalabodov.onTravelSolutions.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public Optional<City> getByCity(String city) {
        return cityRepository.getByCity(city);
    }

    @Override
    public int update(String description, String city) {
        return cityRepository.updateCity(description,city);
    }

    @Override
    public City save(City city) {
        return cityRepository.save(city);
    }

    @Override
    public int delete(String city) {
        return cityRepository.deleteCity(city);
    }
}
