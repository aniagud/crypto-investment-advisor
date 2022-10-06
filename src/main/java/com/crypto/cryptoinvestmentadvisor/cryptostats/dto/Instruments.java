package com.crypto.cryptoinvestmentadvisor.cryptostats.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@RequiredArgsConstructor(staticName = "of")
public class Instruments {

    List<String> instruments;
}
