package com.crypto.cryptoinvestmentadvisor.cryptostats;

import com.crypto.cryptoinvestmentadvisor.cryptostats.dto.Measures;
import com.crypto.cryptoinvestmentadvisor.cryptovalue.CryptoValue;
import com.crypto.cryptoinvestmentadvisor.cryptovalue.CsvToCryptoValueMapper;
import com.crypto.cryptoinvestmentadvisor.util.FileReader;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StatsProcessor {

    private static final String DATA_FILE_SUFFIX = "_values.csv";
    private final FileReader fileReader;

    public List<String> getInstrumentsSortedByRange() {
        return fileReader.findAllDataFiles()
                .stream()
                .filter(fileName -> fileName.endsWith(DATA_FILE_SUFFIX))
                .map(fileReader::readFile)
                .map(CsvToCryptoValueMapper::map)
                .map(StatsCalculator::calculateNormalizedRangeForMarket)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparing(StatsCalculator.MarketRange::getRange).reversed())
                .map(StatsCalculator.MarketRange::getSymbol)
                .collect(Collectors.toList());
    }

    public Optional<Measures> getMeasures(String marketSymbol) {
        List<CryptoValue> cryptoValues = CsvToCryptoValueMapper.map(
                fileReader.readFile(marketSymbol + DATA_FILE_SUFFIX));

        return StatsCalculator.findMaxPriceForMarket(cryptoValues)
                .flatMap(maxMarketPrice ->
                        StatsCalculator.findMinPriceForMarket(cryptoValues)
                                .flatMap(minMarketPrice ->
                                        StatsCalculator.findNewestPriceForMarket(cryptoValues)
                                                .flatMap(newestMarketPrice ->
                                                        StatsCalculator.findOldestPriceForMarket(cryptoValues)
                                                                .map(oldestMarketPrice ->
                                                                        Measures.of(
                                                                                newestMarketPrice.getPrice(),
                                                                                oldestMarketPrice.getPrice(),
                                                                                minMarketPrice.getPrice(),
                                                                                maxMarketPrice.getPrice()
                                                                        )))));

    }

    public Optional<String> getBestMarketForDay(LocalDate day){

        Long startTimestamp = day.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
        Long endTimestamp = day.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();

        return fileReader.findAllDataFiles()
                .stream()
                .filter(fileName -> fileName.endsWith(DATA_FILE_SUFFIX))
                .map(fileReader::readFile)
                .map(CsvToCryptoValueMapper::map)
                .map(cryptoValues -> cryptoValues
                        .stream()
                        .filter(v -> v.getTimestamp() >= startTimestamp && v.getTimestamp() < endTimestamp)
                        .collect(Collectors.toList()))
                .map(StatsCalculator::calculateNormalizedRangeForMarket)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparing(StatsCalculator.MarketRange::getRange).reversed())
                .map(StatsCalculator.MarketRange::getSymbol)
                .findFirst();
    }
}
