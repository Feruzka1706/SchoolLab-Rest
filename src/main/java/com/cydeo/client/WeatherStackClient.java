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

    @GetMapping("/current")
    WeatherStack getCurrentTemperatureOfRequestedState(@RequestParam("access_key") String apiKey,
                                                       @RequestParam("query") String state);


}
