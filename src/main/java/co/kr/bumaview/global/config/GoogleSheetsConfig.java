package co.kr.bumaview.global.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.services.sheets.v4.Sheets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.net.URI;
import java.util.Collections;

@Configuration
public class GoogleSheetsConfig {

    @Value("${google.sheets.credentials-url}")
    private String credentialsUrl;

    @Bean
    public Sheets sheetsService() throws Exception {
        try (InputStream inputStream = URI.create(credentialsUrl).toURL().openStream()) {
            GoogleCredentials credentials = GoogleCredentials
                    .fromStream(inputStream)
                    .createScoped(Collections.singletonList("https://www.googleapis.com/auth/spreadsheets"));

            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

            return new Sheets.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    requestInitializer
            ).setApplicationName("MyApp").build();
        }
    }
}
