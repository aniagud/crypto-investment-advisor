package com.crypto.cryptoinvestmentadvisor.cryptostats;

import com.crypto.cryptoinvestmentadvisor.cryptovalue.CryptoValue;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatsCalculator {

    public static Optional<MarketPrice> findMinPriceForMarket(List<CryptoValue> cryptoValues) {
        return cryptoValues
                .stream()
                .min(Comparator.comparing(CryptoValue::getPrice))
                .map(cryptoValue -> MarketPrice.of(cryptoValue.getSymbol(), cryptoValue.getPrice()));
    }

    public static Optional<MarketPrice> findMaxPriceForMarket(List<CryptoValue> cryptoValues) {
        return cryptoValues
                .stream()
                .max(Comparator.comparing(CryptoValue::getPrice))
                .map(cryptoValue -> MarketPrice.of(cryptoValue.getSymbol(), cryptoValue.getPrice()));
    }

    public static Optional<MarketPrice> findOldestPriceForMarket(List<CryptoValue> cryptoValues) {
        return cryptoValues
                .stream()
                .min(Comparator.comparing(CryptoValue::getTimestamp))
                .map(cryptoValue -> MarketPrice.of(cryptoValue.getSymbol(), cryptoValue.getPrice()));
    }

    public static Optional<MarketPrice> findNewestPriceForMarket(List<CryptoValue> cryptoValues) {
        return cryptoValues
                .stream()
                .max(Comparator.comparing(CryptoValue::getTimestamp))
                .map(cryptoValue -> MarketPrice.of(cryptoValue.getSymbol(), cryptoValue.getPrice()));
    }


    public static Optional<MarketRange> calculateNormalizedRangeForMarket(List<CryptoValue> cryptoValues) {
        return findMaxPriceForMarket(cryptoValues)
                .flatMap(maxMarketPrice ->
                        findMinPriceForMarket(cryptoValues)
                                .map(minMarketPrice ->
                                        maxMarketPrice.getPrice().subtract(minMarketPrice.getPrice())
                                                .divide(minMarketPrice.getPrice(), MathContext.DECIMAL64))
                                .map(range -> MarketRange.of(maxMarketPrice.getSymbol(), range)));
    }

    @Value
    @RequiredArgsConstructor(staticName = "of")
    public static class MarketPrice {
        String symbol;
        BigDecimal price;
    }

    @Value
    @RequiredArgsConstructor(staticName = "of")
    public static class MarketRange {
        String symbol;
        BigDecimal range;
    }
}
