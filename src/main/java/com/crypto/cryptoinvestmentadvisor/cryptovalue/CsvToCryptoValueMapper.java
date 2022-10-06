package com.crypto.cryptoinvestmentadvisor.cryptovalue;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CsvToCryptoValueMapper {

    public static List<CryptoValue> map(List<String> data) {
        if (data.size() >= 2) {
            return data
                    .subList(1, data.size())
                    .stream()
                    .map(s -> s.split(","))
                    .map(s -> new CryptoValue(Long.parseLong(s[0]), s[1], new BigDecimal(s[2])))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
