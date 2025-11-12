package com.moghoneim.roomily.api;


import com.moghoneim.roomily.config.RestTemplateConfig;
import com.moghoneim.roomily.property.dto.PropertyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PropertyApiClient {

    private final RestTemplateConfig restTemplateConfig;

    private final Environment env;

    public List<PropertyResponse> getExtractedPropertiesByLocation(String location) {
        String propertiesBaseUrl = env.getProperty("properties.api.base-url");
        assert propertiesBaseUrl != null;

        // Construct the URL with location parameter if provided
        String url = UriComponentsBuilder.fromUriString(propertiesBaseUrl)
                .queryParam("location", location)
                .toUriString();

        RestTemplate restTemplate = restTemplateConfig.createRestTemplate(6000, 6000);
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PropertyResponse>>() {
                }
        ).getBody();
    }


}
