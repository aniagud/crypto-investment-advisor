package com.crypto.cryptoinvestmentadvisor.cryptostats;

import com.crypto.cryptoinvestmentadvisor.cryptostats.dto.Instruments;
import com.crypto.cryptoinvestmentadvisor.cryptostats.dto.MarketSymbol;
import com.crypto.cryptoinvestmentadvisor.cryptostats.dto.Measures;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/crypto-stats")
@RequiredArgsConstructor
public class CryptoStatsResource {

    private final StatsProcessor statsProcessor;

    @GetMapping("/all-by-range")
    public Instruments getSortedInstruments() {
        return new Instruments(statsProcessor.getInstrumentsSortedByRange());
    }

    @GetMapping("/markets/{crypto}")
    public Measures getMeasuresByCrypto(@PathVariable("crypto") String crypto) {
        return null;
    }

    @GetMapping("/best-for-day")
    public MarketSymbol getBestCryptoForDay(@RequestParam("day") Long day) {
        return null;
    }
}
