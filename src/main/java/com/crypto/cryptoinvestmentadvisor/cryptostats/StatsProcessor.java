package com.crypto.cryptoinvestmentadvisor.cryptostats;

import com.crypto.cryptoinvestmentadvisor.cryptovalue.CsvToCryptoValueMapper;
import com.crypto.cryptoinvestmentadvisor.util.FileReader;
import lombok.RequiredArgsConstructor;

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
}
