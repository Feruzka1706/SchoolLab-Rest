package com.cydeo.client;

import com.cydeo.dto.countriesInfo.CountriesInfo;
import com.cydeo.dto.weather.WeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(url = "https://restcountries.com/v3.1", name = "CountriesInfo-Client")
public interface CountriesInfoClient {

    // Corrected the URL mapping
    @GetMapping("/name/{countryName}")
    List<CountriesInfo> getCountryInfoByName(@PathVariable String countryName);

}
