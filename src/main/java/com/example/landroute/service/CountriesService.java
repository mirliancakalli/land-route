package com.example.landroute.service;

import com.example.landroute.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CountriesService {

    @Autowired
    private  RestTemplate restTemplate;

    @Value("${countries.url}")
    private String url;

    public List<Country> getAllCountries() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);

        ResponseEntity<List<Country>> response = restTemplate.exchange(url,HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        if (response.getStatusCode().value() == HttpStatus.OK.value())
            return response.getBody();
        return null;
    }
}
