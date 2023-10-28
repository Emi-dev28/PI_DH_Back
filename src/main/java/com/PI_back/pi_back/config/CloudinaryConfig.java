package com.PI_back.pi_back.config;
import com.cloudinary.Cloudinary;

import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;


@Configuration

public class CloudinaryConfig {

    // Dotenv dotenv = Dotenv.load();
    Dotenv dotenv = null;

    private final String API_KEY = "226395323826498";
            // System.getenv("API_KEY");
            //System.getenv("API_KEY");
            //Dotenv.load().get("API_KEY");
            //dotenv.get("API_KEY");

    private final String CLOUD_NAME = "dgjjvwsyt";
            //System.getenv("CLOUD_NAME");
            //System.getenv("CLOUD_NAME");
            //
            //Dotenv.load().get("CLOUD_NAME");
            //dotenv.get("CLOUD_NAME");
    private final String API_SECRET = "Qmjr-y0zzB1saRvRkDZufI1enp0";
                    //"System.getenv("API_SECRET");
                    //System.getenv("API_SECRET");
                    //
                    //Dotenv.load().get("API_SECRET");
                    //dotenv.get("API_SECRET");

    private final String CLOUD_URL = "cloudinary://226395323826498:Qmjr-y0zzB1saRvRkDZufI1enp0@dgjjvwsyt";
            //System.getenv("CLOUD_URL");
            //System.getenv("CLOUD_URL");
            //
            //Dotenv.load().get("API_CLOUD_URL");
            //dotenv.get("CLOUD_URL");
    @Bean
    public Cloudinary cloudinary(){
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET));
        return cloudinary;
    }

}
