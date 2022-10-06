package com.crypto.cryptoinvestmentadvisor.cryptostats;

import com.crypto.cryptoinvestmentadvisor.cryptovalue.CryptoValue;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class StatsCalculatorTest {

    @Test
    public void findCorrectMinPriceTest() {
        List<CryptoValue> values = asList(
                new CryptoValue(Long.valueOf("1641009600000"), "BTC", new BigDecimal("46813.21")),
                new CryptoValue(Long.valueOf("1641020400000"), "BTC", new BigDecimal("46979.61")),
                new CryptoValue(Long.valueOf("1641031200000"), "BTC", new BigDecimal("47143.98")),
                new CryptoValue(Long.valueOf("1641034800000"), "BTC", new BigDecimal("46871.09")),
                new CryptoValue(Long.valueOf("1641045600000"), "BTC", new BigDecimal("47023.24")));

        assertThat(StatsCalculator.findMinPriceForMarket(values)).
                hasValue(StatsCalculator.MarketPrice.of("BTC", new BigDecimal("46813.21")));
    }
    @Test
    public void findCorrectMaxPriceTest() {
        List<CryptoValue> values = asList(
                new CryptoValue(Long.valueOf("1641009600000"), "BTC", new BigDecimal("46813.21")),
                new CryptoValue(Long.valueOf("1641020400000"), "BTC", new BigDecimal("46979.61")),
                new CryptoValue(Long.valueOf("1641031200000"), "BTC", new BigDecimal("47143.98")),
                new CryptoValue(Long.valueOf("1641034800000"), "BTC", new BigDecimal("46871.09")),
                new CryptoValue(Long.valueOf("1641045600000"), "BTC", new BigDecimal("47023.24")));

        assertThat(StatsCalculator.findMaxPriceForMarket(values)).
                hasValue(StatsCalculator.MarketPrice.of("BTC", new BigDecimal("47143.98")));
    }

    @Test
    public void findCorrectOldestPriceTest() {
        List<CryptoValue> values = asList(
                new CryptoValue(Long.valueOf("1641009600000"), "BTC", new BigDecimal("46813.21")),
                new CryptoValue(Long.valueOf("1641020400000"), "BTC", new BigDecimal("46979.61")),
                new CryptoValue(Long.valueOf("1641031200000"), "BTC", new BigDecimal("47143.98")),
                new CryptoValue(Long.valueOf("1641034800000"), "BTC", new BigDecimal("46871.09")),
                new CryptoValue(Long.valueOf("1641045600000"), "BTC", new BigDecimal("47023.24")));

        assertThat(StatsCalculator.findOldestPriceForMarket(values)).
                hasValue(StatsCalculator.MarketPrice.of("BTC", new BigDecimal("46813.21")));
    }

    @Test
    public void findCorrectNewestPriceTest() {
        List<CryptoValue> values = asList(
                new CryptoValue(Long.valueOf("1641009600000"), "BTC", new BigDecimal("46813.21")),
                new CryptoValue(Long.valueOf("1641020400000"), "BTC", new BigDecimal("46979.61")),
                new CryptoValue(Long.valueOf("1641031200000"), "BTC", new BigDecimal("47143.98")),
                new CryptoValue(Long.valueOf("1641034800000"), "BTC", new BigDecimal("46871.09")),
                new CryptoValue(Long.valueOf("1641045600000"), "BTC", new BigDecimal("47023.24")));

        assertThat(StatsCalculator.findNewestPriceForMarket(values)).
                hasValue(StatsCalculator.MarketPrice.of("BTC", new BigDecimal("47023.24")));
    }

    @Test
    public void findNoMinPriceWhenNoValuesTest() {
        List<CryptoValue> values = Collections.emptyList();
        assertThat(StatsCalculator.findMinPriceForMarket(values))
                .isEmpty();
    }

    @Test
    public void findNoMaxPriceWhenNoValuesTest() {
        List<CryptoValue> values = Collections.emptyList();
        assertThat(StatsCalculator.findMaxPriceForMarket(values))
                .isEmpty();
    }

    @Test
    public void findNoOldestPriceWhenNoValuesTest() {
        List<CryptoValue> values = Collections.emptyList();
        assertThat(StatsCalculator.findOldestPriceForMarket(values))
                .isEmpty();
    }

    @Test
    public void findNoNewestPriceWhenNoValuesTest() {
        List<CryptoValue> values = Collections.emptyList();
        assertThat(StatsCalculator.findNewestPriceForMarket(values))
                .isEmpty();
    }

    @Test
    public void findCorrectPricesWhenOnlyOneValueTest() {
        List<CryptoValue> values = List.of(
                new CryptoValue(Long.valueOf("1641045600000"), "BTC", new BigDecimal("47023.24")));
        assertThat(StatsCalculator.findMinPriceForMarket(values))
                .hasValue(StatsCalculator.MarketPrice.of("BTC", new BigDecimal("47023.24")));
        assertThat(StatsCalculator.findMaxPriceForMarket(values))
                .hasValue(StatsCalculator.MarketPrice.of("BTC", new BigDecimal("47023.24")));
        assertThat(StatsCalculator.findNewestPriceForMarket(values))
                .hasValue(StatsCalculator.MarketPrice.of("BTC", new BigDecimal("47023.24")));
    }

    @Test
    public void calculateCorrectRangeTest() {
        List<CryptoValue> values = asList(
                new CryptoValue(Long.valueOf("1641009600000"), "BTC", new BigDecimal("46813.21")),
                new CryptoValue(Long.valueOf("1641020400000"), "BTC", new BigDecimal("46979.61")),
                new CryptoValue(Long.valueOf("1641031200000"), "BTC", new BigDecimal("47143.98")),
                new CryptoValue(Long.valueOf("1641034800000"), "BTC", new BigDecimal("46871.09")),
                new CryptoValue(Long.valueOf("1641045600000"), "BTC", new BigDecimal("47023.24")));

        assertThat(StatsCalculator.calculateNormalizedRangeForMarket(values)).
                hasValue(StatsCalculator.MarketRange.of("BTC", new BigDecimal("0.007065740631757574")));
    }

    @Test
    public void calculateWhenNoRangeTest() {
        List<CryptoValue> values = asList(
                new CryptoValue(Long.valueOf("1641009600000"), "BTC", new BigDecimal("46813.21")),
                new CryptoValue(Long.valueOf("1641020400000"), "BTC", new BigDecimal("46813.21")),
                new CryptoValue(Long.valueOf("1641031200000"), "BTC", new BigDecimal("46813.21")),
                new CryptoValue(Long.valueOf("1641034800000"), "BTC", new BigDecimal("46813.21")),
                new CryptoValue(Long.valueOf("1641045600000"), "BTC", new BigDecimal("46813.21")));

        assertThat(StatsCalculator.calculateNormalizedRangeForMarket(values)).
                hasValue(StatsCalculator.MarketRange.of("BTC", new BigDecimal("0")));
    }

    @Test
    public void calculateNoRangeWhenNoValuesTest() {
        List<CryptoValue> values = Collections.emptyList();
        assertThat(StatsCalculator.calculateNormalizedRangeForMarket(values))
                .isEmpty();
    }


}