package com.crypto.cryptoinvestmentadvisor.cryptostats;

import com.crypto.cryptoinvestmentadvisor.cryptostats.dto.Instruments;
import com.crypto.cryptoinvestmentadvisor.cryptostats.dto.MarketSymbol;
import com.crypto.cryptoinvestmentadvisor.cryptostats.dto.Measures;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CryptoStatsResourceTest {

    @Mock
    private StatsProcessor statsProcessor;

    private CryptoStatsResource cryptoStatsResource;

    @Before
    public void before() {
        cryptoStatsResource = new CryptoStatsResource(statsProcessor);
    }

    @Test
    public void getCorrectSortedInstrumentsTest() {

        when(statsProcessor.getInstrumentsSortedByRange())
                .thenReturn(List.of("BTC", "ETH", "LTC"));

        assertThat(cryptoStatsResource.getSortedInstruments())
                .isEqualTo(Instruments.of(List.of("BTC", "ETH", "LTC")));
    }

    @Test
    public void getInstrumentsWhenNoDataTest() {
        when(statsProcessor.getInstrumentsSortedByRange())
                .thenReturn(Collections.emptyList());
        assertThat(cryptoStatsResource.getSortedInstruments())
                .isEqualTo(Instruments.of(Collections.emptyList()));
    }

    @Test
    public void getCorrectMeasuresForCryptoTest() {
        Measures measures = Measures.of(
                new BigDecimal("47023.24"),
                new BigDecimal("46813.21"),
                new BigDecimal("46813.21"),
                new BigDecimal("47143.98"));
        when(statsProcessor.getMeasures("BTC"))
                .thenReturn(Optional.ofNullable(measures));
        assertThat(cryptoStatsResource.getMeasuresForCrypto("BTC"))
                .isEqualTo(new ResponseEntity<Measures>(
                        Measures.of(
                                new BigDecimal("47023.24"),
                                new BigDecimal("46813.21"),
                                new BigDecimal("46813.21"),
                                new BigDecimal("47143.98")),
                        HttpStatus.OK));
    }

    @Test
    public void getNotFoundResponseForUnsupportedCryptoRequestTest() {
        when(statsProcessor.getMeasures("unsupportedCrypto"))
                .thenReturn(Optional.empty());
        assertThat(cryptoStatsResource.getMeasuresForCrypto("unsupportedCrypto"))
                .isEqualTo(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Test
    public void getCorrectBestCryptoForDay() {
        when(statsProcessor.getBestMarketForDay(LocalDate.of(2022, 1, 1)))
                .thenReturn(Optional.of("BTC"));
        assertThat(cryptoStatsResource.getBestCryptoForDay(LocalDate.of(2022, 1, 1)))
                .isEqualTo(ResponseEntity.ok(MarketSymbol.of("BTC")));
    }

    @Test
    public void getBadRequestForUnsupportedDay() {
        when(statsProcessor.getBestMarketForDay(LocalDate.of(2022, 2, 1)))
                .thenReturn(Optional.empty());
        assertThat(cryptoStatsResource.getBestCryptoForDay(LocalDate.of(2022, 2, 1)))
                .isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}