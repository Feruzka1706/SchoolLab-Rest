package com.cydeo.service.impl;

import com.cydeo.client.CountriesInfoClient;
import com.cydeo.client.WeatherStackClient;
import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.countriesInfo.CountriesInfo;
import com.cydeo.dto.weather.WeatherResponse;
import com.cydeo.entity.Address;
import com.cydeo.exception.NotFoundException;
import com.cydeo.util.MapperUtil;
import com.cydeo.repository.AddressRepository;
import com.cydeo.service.AddressService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final MapperUtil mapperUtil;
    private final WeatherStackClient weatherStackClient;
    @Value("${access_key}")
    private String access_key;
    private final CountriesInfoClient countriesInfoClient;

    public AddressServiceImpl(AddressRepository addressRepository,
                              MapperUtil mapperUtil,
                              WeatherStackClient weatherStackClient,
                              CountriesInfoClient countriesInfoClient) {
        this.addressRepository = addressRepository;
        this.mapperUtil = mapperUtil;
        this.weatherStackClient = weatherStackClient;
        this.countriesInfoClient = countriesInfoClient;
    }

    public List<AddressDTO> findAll() {
        return addressRepository.findAll()
                .stream()
                .map(address -> {
                    AddressDTO addressDTO = mapperUtil.convert(address, new AddressDTO());

                        addressDTO.setState(retrieveStateByCityName(addressDTO.getCity()));
                        addressDTO.setCurrentTemperature(retrieveTemperatureByCity(addressDTO.getCity()));
                        addressDTO.setFlag(retrieveCountryFlagLinkByCountryName(addressDTO.getCountry()));
                    return addressDTO;
                })
                .collect(Collectors.toList());

    }

    @Override
    public AddressDTO findById(Long id) throws Exception {
        Address foundAddress = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Address Found!"));

        AddressDTO addressDTO = mapperUtil.convert(foundAddress,new AddressDTO());

        /**
         * We will get the current temperature and set based on city information, then return the DTO
         * To get current temp based on city we need to use FeignClient end point
         */

        addressDTO.setState(retrieveStateByCityName(addressDTO.getCity()));
        addressDTO.setCurrentTemperature(retrieveTemperatureByCity(addressDTO.getCity()));
        addressDTO.setFlag(retrieveCountryFlagLinkByCountryName(addressDTO.getCountry()));

        return addressDTO;
    }

    @Override
    public AddressDTO update(AddressDTO addressDTO) throws Exception {

        addressRepository.findById(addressDTO.getId())
                .orElseThrow(() -> new NotFoundException("No Address Found!"));

        Address addressToSave = mapperUtil.convert(addressDTO, new Address());
        addressRepository.save(addressToSave);
        return mapperUtil.convert(addressToSave, new AddressDTO());

    }

    @Override
    public AddressDTO create(AddressDTO addressDTO) throws Exception {

        Optional<Address> foundAddress = addressRepository.findById(addressDTO.getId());

        if (foundAddress.isPresent()) {
            throw new Exception("Address Already Exists!");
        }

        Address addressToSave = mapperUtil.convert(addressDTO, new Address());

        addressRepository.save(addressToSave);

        return mapperUtil.convert(addressToSave, new AddressDTO());

    }

    private String retrieveStateByCityName(String city) {
        WeatherResponse weatherResponse=    weatherStackClient.getCurrentTemperatureOfRequestedState(
                access_key,city);
        if(weatherResponse == null || weatherResponse.getLocation().getRegion() == null){
            return null;
        }

      return   weatherResponse.getLocation().getRegion();
    }

    private Integer retrieveTemperatureByCity(String city){

        WeatherResponse weatherResponse=   weatherStackClient.getCurrentTemperatureOfRequestedState(
                access_key,city);

        if(weatherResponse == null || weatherResponse.getCurrent() == null){
            return null;
        }

        return weatherResponse.getCurrent().getTemperature();
    }

    private String retrieveCountryFlagLinkByCountryName(String country) {
        List<CountriesInfo> countriesInfo = countriesInfoClient.getCountryInfoByName(country);
        return countriesInfo.stream()
                .filter(each -> each != null && each.getFlags() != null)
                .findFirst()
                .map(each -> each.getFlags().getPng())
                .orElse(null); // Return null if no matching country with valid flags is found
    }

}