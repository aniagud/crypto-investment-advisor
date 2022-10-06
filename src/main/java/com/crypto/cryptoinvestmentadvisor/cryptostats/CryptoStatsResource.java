package com.crypto.cryptoinvestmentadvisor.cryptostats;

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

    /**
     * Return a descending sorted list of all the currently supported cryptos,
     * comparing the normalized range (i.e. (max-min)/min)
     * @return Instruments.class
     */
    @GetMapping("/all-by-range")
    public Instruments getSortedInstruments() {
        return Instruments.of(statsProcessor.getInstrumentsSortedByRange());
    }


    /**
     * Return the oldest/newest/min/max values for a requested crypto
     * @param market - requested crypto symbol
     * @return ResponseEntity<Measures>
     */
    @GetMapping("/markets/{market}")
    public ResponseEntity<Measures> getMeasuresForCrypto(@PathVariable("market") String market) {
        return statsProcessor.getMeasures(market)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Return the crypto with the highest normalized range for a specific day
     * @param day - specified day in 'yyyy-MM-dd' format
     * @return ResponseEntity<MarketSymbol>
     */
    @GetMapping("/best-for-day")
    public ResponseEntity<MarketSymbol> getBestCryptoForDay(
            @RequestParam("day") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {

        return statsProcessor.getBestMarketForDay(day)
                .map(MarketSymbol::of)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
