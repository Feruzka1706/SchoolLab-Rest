package com.cydeo.client;


import com.cydeo.dto.WeatherStack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(url = "http://api.weatherstack.com", name = "WeatherStack-Client")
public interface WeatherStackClient {

    @GetMapping("/current?access_key=3c02d6990d036aa4ac52769ad819d085")
    WeatherStack getCurrentTemperatureOfRequestedState(@RequestParam("query") String city);


}
