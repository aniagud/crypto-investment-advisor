package com.crypto.cryptoinvestmentadvisor.cryptostats.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Value
@RequiredArgsConstructor(staticName = "of")
public class Measures {

    BigDecimal newest;
    BigDecimal oldest;
    BigDecimal min;
    BigDecimal max;
}
