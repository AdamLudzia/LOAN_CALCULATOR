package pl.adamludzia.model;

import java.math.BigDecimal;

public record CreditOffer(int maxCreditLength,
                          BigDecimal maxMontlyCreditPayment,
                          BigDecimal maxCreditAmout)
{}

