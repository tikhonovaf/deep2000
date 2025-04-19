package ru.atikhonov.deep2000.backend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CbrCurrencyService {
        private static final String CBR_DAILY_JSON_URL =
                "https://www.cbr-xml-daily.ru/daily_json.js";

        public void getCurrencyRates() {
            RestTemplate restTemplate = new RestTemplate();

            // Получаем данные в формате JSON
            JsonNode response = restTemplate.getForObject(
                    CBR_DAILY_JSON_URL, JsonNode.class);

            Iterator<String> fieldNames = response.fieldNames();
            List<String> namesList = new ArrayList<>();
            fieldNames.forEachRemaining(namesList::add);


            if (response != null) {
                JsonNode valute = response.path("Valute");
                // Пример получения курса USD
                double usdRate = valute.path("USD").path("Value").asDouble();
                System.out.println("Курс USD: " + usdRate);

                // Пример получения курса EUR
                double eurRate = valute.path("EUR").path("Value").asDouble();
                System.out.println("Курс EUR: " + eurRate);
            }
        }
    }

