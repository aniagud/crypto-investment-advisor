package com.crypto.cryptoinvestmentadvisor.cryptostats;

import com.crypto.cryptoinvestmentadvisor.cryptostats.dto.Instruments;
import com.crypto.cryptoinvestmentadvisor.cryptostats.dto.MarketSymbol;
import com.crypto.cryptoinvestmentadvisor.cryptostats.dto.Measures;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "api/crypto-stats")
public class CryptoStatsResource {

    @GetMapping
    public Instruments getSortedInstruments(){
        return new Instruments(new ArrayList<>());
    }

    @GetMapping("/{crypto}")
    public Measures getMeasuresByCrypto(@PathVariable("crypto") String crypto){
        return null;
    }

    @GetMapping("/day/{day}")
    public MarketSymbol getBestCryptoByDay(@PathVariable("day") Long day){
        return null;
    }
}
