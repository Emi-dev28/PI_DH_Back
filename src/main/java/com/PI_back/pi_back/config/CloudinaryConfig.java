package com.PI_back.pi_back.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    private final String API_KEY = "726558329132752";
    // la de gabi es: 967176416429188
    private final String CLOUD_NAME = "dgjjvwsyt";
    // la de gabi es: dkixtok7a
    private final String API_SECRET = "R5Yrf_Kvh3DB5UnLTVa9qsBTQSA";
    // la de gabi es: Kc4UQqc49St540aLiorG-BRJskg

    private final String CLOUD_URL ="cloudinary://726558329132752:R5Yrf_Kvh3DB5UnLTVa9qsBTQSA@dgjjvwsyt";
    // la de gabi es: cloudinary://967176416429188:Kc4UQqc49St540aLiorG-BRJskg@dkixtok7a
    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);
        return new Cloudinary(config);
    }

}
