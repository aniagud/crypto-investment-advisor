package com.crypto.cryptoinvestmentadvisor;

import com.crypto.cryptoinvestmentadvisor.cryptostats.StatsProcessor;
import com.crypto.cryptoinvestmentadvisor.cryptostats.dto.Instruments;
import com.crypto.cryptoinvestmentadvisor.cryptostats.dto.MarketSymbol;
import com.crypto.cryptoinvestmentadvisor.cryptostats.dto.Measures;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "api/crypto-stats")
@RequiredArgsConstructor
public class CryptoStatsResource {

    private final StatsProcessor statsProcessor;

    @GetMapping("/all-by-range")
    public Instruments getSortedInstruments() {
        return Instruments.of(statsProcessor.getInstrumentsSortedByRange());
    }

    @GetMapping("/markets/{market}")
    public ResponseEntity<Measures> getMeasuresByCrypto(@PathVariable("market") String market) {
        return statsProcessor.getMeasures(market)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)    .build());
    }

    @GetMapping("/best-for-day")
    public ResponseEntity<MarketSymbol> getBestCryptoForDay(
            @RequestParam("day") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {

        return statsProcessor.getBestMarketForDay(day)
                .map(MarketSymbol::of)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
