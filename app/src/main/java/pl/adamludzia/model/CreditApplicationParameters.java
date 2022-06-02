package pl.adamludzia.model;

import java.math.BigDecimal;
import java.util.InputMismatchException;

public record CreditApplicationParameters(int lengthOfEnployment,
                                          BigDecimal monthlyIncome,
                                          BigDecimal monthlyCostsOfLiving,
                                          BigDecimal monthlyCreditPayments,
                                          BigDecimal remainingCredisToPay) {

    public CreditApplicationParameters {
        if (lengthOfEnployment < 0) {
            throw new InputMismatchException();
        }

        if (monthlyIncome.intValue() < 0) {
            throw new InputMismatchException();
        }

        if (monthlyCostsOfLiving.intValue() < 0) {
            throw new InputMismatchException();
        }

        if (monthlyCreditPayments.intValue() < 0) {
            throw new InputMismatchException();
        }

        if (remainingCredisToPay.intValue() < 0) {
            throw new InputMismatchException();
        }
    }
}

