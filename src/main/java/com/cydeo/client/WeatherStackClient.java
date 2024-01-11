package com.cydeo.client;


import com.cydeo.dto.weather.WeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(url = "http://api.weatherstack.com", name = "WeatherStack-Client")
public interface WeatherStackClient {

    //access_key=3c02d6990d036aa4ac52769ad819d085
    @GetMapping("/current")
    WeatherResponse getCurrentTemperatureOfRequestedState(
            @RequestParam("access_key") String accessKey,
            @RequestParam("query") String city);


}
