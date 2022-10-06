package com.crypto.cryptoinvestmentadvisor.cryptostats;

import com.crypto.cryptoinvestmentadvisor.cryptostats.dto.Measures;
import com.crypto.cryptoinvestmentadvisor.util.FileReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StatsProcessorTest {

    @Mock
    private FileReader fileReader;
    private StatsProcessor statsProcessor;

    @Before
    public void before() {
        statsProcessor = new StatsProcessor(fileReader);
    }

    @Test
    public void getCorrectInstrumentsTest(){

        when(fileReader.findAllDataFiles())
                .thenReturn(
                        Stream.of("sample.txt","BTC_values.csv", "ETH_values.csv", "DOGE_values.csv").collect(Collectors.toSet()));

        when(fileReader.readFile("BTC_values.csv"))
                .thenReturn(asList(
                        "timestamp,symbol,price","1641009600000,BTC,46813.21","1641020400000,BTC,46979.61",
                        "1641031200000,BTC,47143.98","1641034800000,BTC,46871.09","1641045600000,BTC,47023.24"));

        when(fileReader.readFile("ETH_values.csv"))
                .thenReturn(asList(
                        "timestamp,symbol,price","1641009600000,ETH,3715.32","1641020400000,ETH,3718.67",
                        "1641031200000,ETH,3697.04","1641034800000,ETH,3727.61","1641045600000,ETH,3747"));

        when(fileReader.readFile("DOGE_values.csv"))
                .thenReturn(asList(
                        "timestamp,symbol,price","1641009600000,DOGE,0.1702","1641020400000,DOGE,0.1722",
                        "1641031200000,DOGE,0.1727","1641034800000,DOGE,0.1719","1641045600000,DOGE,0.1719"));

        assertThat(statsProcessor.getInstrumentsSortedByRange()).isEqualTo(asList("DOGE","ETH","BTC"));
    }

    @Test
    public void getNoInstrumentsWhenNoDataFiles(){
        when(fileReader.findAllDataFiles())
                .thenReturn(
                        Stream.of("BTC.csv", "ETH.txt", "sample.txt").collect(Collectors.toSet()));

        assertThat(statsProcessor.getInstrumentsSortedByRange()).isEqualTo(Collections.emptyList());
    }

    @Test
    public void getNoInstrumentsWhenDataFilesEmpty(){
        when(fileReader.findAllDataFiles())
                .thenReturn(
                        Stream.of("BTC_values.csv", "ETH_values.csv", "DOGE_values.csv").collect(Collectors.toSet()));
        when(fileReader.readFile(any()))
                .thenReturn(Collections.emptyList());

        assertThat(statsProcessor.getInstrumentsSortedByRange()).isEqualTo(Collections.emptyList());
    }

    @Test
    public void getCorrectMeasuresTest() {

        when(fileReader.readFile("BTC_values.csv"))
                .thenReturn(asList(
                        "timestamp,symbol,price",
                        "1641009600000,BTC,46813.21",
                        "1641020400000,BTC,46979.61",
                        "1641031200000,BTC,47143.98",
                        "1641034800000,BTC,46871.09",
                        "1641045600000,BTC,47023.24"));

        Measures expectedMeasures = Measures.of(
                new BigDecimal("47023.24"),
                new BigDecimal("46813.21"),
                new BigDecimal("46813.21"),
                new BigDecimal("47143.98"));

        assertThat(statsProcessor.getMeasures("BTC")).hasValue(expectedMeasures);
    }

    @Test
    public void getNoMeasuresWhenDataIsNotPresentTest() {

        when(fileReader.readFile(any()))
                .thenReturn(Collections.emptyList());

        assertThat(statsProcessor.getMeasures("BTC")).isEmpty();
    }

    @Test
    public void getCorrectBestMarketForDayTest() {

        when(fileReader.findAllDataFiles())
                .thenReturn(
                        Stream.of("sample.txt","BTC_values.csv", "ETH_values.csv", "DOGE_values.csv").collect(Collectors.toSet()));

        when(fileReader.readFile("BTC_values.csv"))
                .thenReturn(asList(
                        "timestamp,symbol,price","1641009600000,BTC,46813.21","1641020400000,BTC,46979.61",
                        "1641031200000,BTC,47143.98","1641034800000,BTC,46871.09","1641045600000,BTC,47023.24"));
        when(fileReader.readFile("ETH_values.csv"))
                .thenReturn(asList(
                        "timestamp,symbol,price","1641009600000,ETH,3715.32","1641020400000,ETH,3718.67",
                        "1641031200000,ETH,3697.04","1641034800000,ETH,3727.61","1641045600000,ETH,3747"));
        when(fileReader.readFile("DOGE_values.csv"))
                .thenReturn(asList(
                        "timestamp,symbol,price","1641009600000,DOGE,0.1702","1641020400000,DOGE,0.1722",
                        "1641031200000,DOGE,0.1727","1641034800000,DOGE,0.1719","1641045600000,DOGE,0.1719"));

        assertThat(statsProcessor.getBestMarketForDay(LocalDate.of(2022, 1, 1)))
                .hasValue("DOGE");
    }

    @Test
    public void getNoMarketForDayNotPresentInDataTest() {

        when(fileReader.findAllDataFiles())
                .thenReturn(
                        Stream.of("sample.txt","BTC_values.csv", "ETH_values.csv", "DOGE_values.csv").collect(Collectors.toSet()));

        when(fileReader.readFile("BTC_values.csv"))
                .thenReturn(asList(
                        "timestamp,symbol,price","1641009600000,BTC,46813.21","1641020400000,BTC,46979.61",
                        "1641031200000,BTC,47143.98","1641034800000,BTC,46871.09","1641045600000,BTC,47023.24"));
        when(fileReader.readFile("ETH_values.csv"))
                .thenReturn(asList(
                        "timestamp,symbol,price","1641009600000,ETH,3715.32","1641020400000,ETH,3718.67",
                        "1641031200000,ETH,3697.04","1641034800000,ETH,3727.61","1641045600000,ETH,3747"));
        when(fileReader.readFile("DOGE_values.csv"))
                .thenReturn(asList(
                        "timestamp,symbol,price","1641009600000,DOGE,0.1702","1641020400000,DOGE,0.1722",
                        "1641031200000,DOGE,0.1727","1641034800000,DOGE,0.1719","1641045600000,DOGE,0.1719"));

        assertThat(statsProcessor.getBestMarketForDay(LocalDate.of(2022, 2, 1)))
                .isEmpty();
    }

    @Test
    public void getNoMarketForDayWhenNoDataFiles(){
        when(fileReader.findAllDataFiles())
                .thenReturn(
                        Stream.of("BTC.csv", "ETH.txt", "sample.txt").collect(Collectors.toSet()));

        assertThat(statsProcessor.getBestMarketForDay(LocalDate.of(2022, 1, 1)))
                .isEmpty();
    }

    @Test
    public void getNoMarketForDayWhenDataFilesEmpty(){
        when(fileReader.findAllDataFiles())
                .thenReturn(
                        Stream.of("BTC_values.csv", "ETH_values.csv", "DOGE_values.csv").collect(Collectors.toSet()));
        when(fileReader.readFile(any()))
                .thenReturn(Collections.emptyList());

        assertThat(statsProcessor.getBestMarketForDay(LocalDate.of(2022, 1, 1)))
                .isEmpty();
    }

    @Test
    public void fileIsReadOnceToFindAllMeasuresForMarketTest(){
        when(fileReader.readFile(any()))
                .thenReturn(asList(
                        "timestamp,symbol,price","1641009600000,BTC,46813.21","1641045600000,BTC,47023.24"));

        statsProcessor.getMeasures("BTC");
        verify(fileReader, times(1)).readFile(any());
    }
}