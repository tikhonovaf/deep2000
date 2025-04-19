package ru.atikhonov.deep2000.backend.thread;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import ru.atikhonov.deep2000.backend.model.ExchangeRate;
import ru.atikhonov.deep2000.backend.repository.ExchangeRateRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@AllArgsConstructor

public class CbrCurrencyGetThread implements Runnable {
    private ExchangeRateRepository exchangeRateRepository;
    private static final String CBR_DAILY_JSON_URL =
            "https://www.cbr-xml-daily.ru/daily_json.js";

    @Override
    public void run() {
        RestTemplate restTemplate = new RestTemplate();

// Добавляем конвертер, который обрабатывает application/javascript
        restTemplate.getMessageConverters().add(0, new MappingJackson2HttpMessageConverter() {
            @Override
            public boolean canRead(Class<?> clazz, MediaType mediaType) {
                // Обрабатываем как JSON, даже если Content-Type = application/javascript
                return mediaType != null && (mediaType.includes(MediaType.APPLICATION_JSON)
                        || mediaType.getType().equals("application")
                        && mediaType.getSubtype().equals("javascript"));
            }
        });

//        JsonNode response = restTemplate.getForObject(CBR_DAILY_JSON_URL, JsonNode.class);
        while (true) {
            try {
                // Получаем данные в формате JSON
                JsonNode response = restTemplate.getForObject(
                        CBR_DAILY_JSON_URL, JsonNode.class);

                if (response != null) {
                    JsonNode valute = response.path("Valute");
                    // Пример получения курса USD
                    Iterator<String> fieldNames = valute.fieldNames();
                    List<String> namesList = new ArrayList<>();
                    fieldNames.forEachRemaining(namesList::add);
                    //  Удаляем данные из таблицы с курсами валют
                    exchangeRateRepository.deleteAll();
                    // Добавляем новые курсы валют
                    for (String charCode : namesList) {
                        ExchangeRate exchangeRate = new ExchangeRate();
                        exchangeRate.setCharCode(charCode);
                        BigDecimal curRate = valute.path(charCode).path("Value").decimalValue();
                        exchangeRate.setValue(curRate);
                        String numCode = valute.path(charCode).path("NumCode").asText();
                        exchangeRate.setNumCode(numCode);
                        String name = valute.path(charCode).path("Name").asText();
                        exchangeRate.setName(name);
                        exchangeRateRepository.save(exchangeRate);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(600000);
            } catch (Exception e1) {
                log.error("Error migration", e1);
            }
        }

    }
}
