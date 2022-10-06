package com.crypto.cryptoinvestmentadvisor.cryptovalue;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class CsvToCryptoValueMapperTest {

    @Test
    public void mapToCorrectCryptoValuesTest() {
        List<String> data = asList(
                "timestamp,symbol,price",
                "1641009600000,BTC,46813.21",
                "1641020400000,BTC,46979.61",
                "1641031200000,BTC,47143.98",
                "1641034800000,BTC,46871.09",
                "1641045600000,BTC,47023.24");
        List<CryptoValue> expectedCryptoValues = asList(
                new CryptoValue(Long.valueOf("1641009600000"), "BTC", new BigDecimal("46813.21")),
                new CryptoValue(Long.valueOf("1641020400000"), "BTC", new BigDecimal("46979.61")),
                new CryptoValue(Long.valueOf("1641031200000"), "BTC", new BigDecimal("47143.98")),
                new CryptoValue(Long.valueOf("1641034800000"), "BTC", new BigDecimal("46871.09")),
                new CryptoValue(Long.valueOf("1641045600000"), "BTC", new BigDecimal("47023.24")));

        assertThat(CsvToCryptoValueMapper.map(data)).isEqualTo(expectedCryptoValues);
    }

    @Test
    public void mapEmptyListTest() {
        List<String> data = Collections.emptyList();
        assertThat(CsvToCryptoValueMapper.map(data)).isEqualTo(Collections.emptyList());
    }

    @Test
    public void mapWhenNoDataTest() {
        List<String> data = asList("timestamp,symbol,price");
        assertThat(CsvToCryptoValueMapper.map(data)).isEqualTo(Collections.emptyList());
    }
}