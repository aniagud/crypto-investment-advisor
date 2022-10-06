package com.crypto.cryptoinvestmentadvisor.cryptostats.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(staticName = "of")
public class MarketSymbol {

    String symbol;
}
