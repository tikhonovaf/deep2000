package ru.atikhonov.deep2000.backend.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.atikhonov.deep2000.backend.repository.ExchangeRateRepository;

import javax.annotation.PostConstruct;

@Component
public class OnStartExec {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @PostConstruct
    public void init() throws Exception {
            new Thread(new CbrCurrencyGetThread(
                    exchangeRateRepository
            )).start();

    }
}
