package com.crypto.cryptoinvestmentadvisor.cryptovalue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoValue {

    public Long timestamp;
    public String symbol;
    public BigDecimal price;
}
