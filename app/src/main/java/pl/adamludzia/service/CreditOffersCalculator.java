package pl.adamludzia.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pl.adamludzia.model.CreditApplicationParameters;
import pl.adamludzia.model.CreditOffer;

public class CreditOffersCalculator {
    private final int MAX_CREDIT_LENGTH = 100;
    private final int MAX_CREDIT_AMOUNT = 150000;
    private final int MAX_CREDIT_ENGAGEMENT = 200000;
    private final int MIN_CREDIT_LENGTH = 6;
    private final int MIN_CREDIT_AMOUNT = 5000;
    private enum Dtl {
        SIX_TO_TWELVE(0.6d, 0.02d, 6,12),
        THIRTEEN_TO_THIRTY_SIX(0.6d, 0.03d, 13,36),
        THIRTY_SEVEN_TO_SIXTY(0.5d, 0.03d, 37, 60),
        SIXTY_ONE_TO_ONE_HUNDRED(0.55d, 0.03d, 61, 100);

        private double dtl;
        private double rate;
        private int minLength;
        private int maxLength;

        Dtl(double dtl, double rate, int minLength, int maxLength) {
            this.dtl = dtl;
            this.rate = rate;
            this.minLength = minLength;
            this.maxLength = maxLength;
        }

        public double dtl() {
            return dtl;
        }
        public double rate() {
            return rate;
        }
    }


    public List<CreditOffer> getOffers(CreditApplicationParameters parameters) {
        List<CreditOffer> offersList = new ArrayList<>();

        for (Dtl variant : Dtl.values()) {

            int maxCreditLength = calculateMaximumCreditLength(parameters.lengthOfEnployment());
            if (maxCreditLength < MIN_CREDIT_LENGTH) {
                continue;
            }

            BigDecimal maxMonthlyPayment = calculateMaxMonthlyPayment(parameters.monthlyIncome(),
                parameters.monthlyCostsOfLiving(),
                parameters.monthlyCreditPayments(),
                variant);

            BigDecimal maxCreditAmount = calculateMaxCreditAmount(parameters.remainingCredisToPay(),
                maxMonthlyPayment,
                variant.rate());
            if (maxCreditAmount.compareTo(BigDecimal.valueOf(MIN_CREDIT_AMOUNT)) < 0) {
                continue;
            }

            if (maxCreditLength >= variant.minLength) {
                CreditOffer offer = new CreditOffer(maxCreditLength, maxMonthlyPayment, maxCreditAmount);
                offersList.add(offer);
            }
        }

        return offersList;
    }

    private int calculateMaximumCreditLength(int employmentLength) {
        return Math.min(employmentLength, MAX_CREDIT_LENGTH);
    }

    private BigDecimal calculateMaxMonthlyPayment(BigDecimal monthlyIncome, BigDecimal monthlyCostOfLiving,
                                                  BigDecimal monthlyCreditPayments, Dtl dtl) {
        var firstValue = monthlyIncome.subtract(monthlyCostOfLiving).subtract(monthlyCreditPayments);
        var secondValue = monthlyIncome.multiply(BigDecimal.valueOf(dtl.dtl())).subtract(monthlyCreditPayments);

        return firstValue.compareTo(secondValue) < 0 ? firstValue : secondValue;
    }

    private BigDecimal calculateMaxCreditAmount(BigDecimal remainingCreditsToPay, BigDecimal maxMonthlyPayment, double rate) {
        final int FIRST_ELEMENT = 0;

        BigDecimal firstValue = BigDecimal.valueOf(MAX_CREDIT_ENGAGEMENT).subtract(remainingCreditsToPay);
        BigDecimal secondValue = BigDecimal.valueOf(MAX_CREDIT_AMOUNT);

        double MI = rate / 12;
        double maxMonthlyInstallment = 1 - Math.pow((1 + MI), -1 * MAX_CREDIT_LENGTH);

        BigDecimal thirdValue = maxMonthlyPayment.multiply(BigDecimal.valueOf(maxMonthlyInstallment / MI));

        return sortValues(firstValue, secondValue, thirdValue).get(FIRST_ELEMENT);
    }

    private List<BigDecimal> sortValues(BigDecimal firstValue, BigDecimal secondValue, BigDecimal thirdValue) {
        List<BigDecimal> listOfValues = new ArrayList<>();
        listOfValues.add(firstValue);
        listOfValues.add(secondValue);
        listOfValues.add(thirdValue);

        Collections.sort(listOfValues);
        return listOfValues;
    }

}
