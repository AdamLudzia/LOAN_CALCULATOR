package pl.adamludzia.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.adamludzia.model.CreditApplicationParameters;
import pl.adamludzia.model.CreditOffer;

class CreditOffersCalculatorTest {
    private CreditOffersCalculator calculator = new CreditOffersCalculator();

    @DisplayName("Should return empty list if length of credit shorter than minimal")
    @Test
    public void shouldReturnEmptyListForShorterThanMinimalCreditLength() {
        CreditApplicationParameters params = new CreditApplicationParameters(
            3,
            new BigDecimal(10000),
            new BigDecimal(1000),
            new BigDecimal(1000),
            new BigDecimal(1000));

        List<CreditOffer> offers = calculator.getOffers(params);

        Assertions.assertEquals(0, offers.size(), "The expected size of list is 0, but was " + offers.size());
    }

    @DisplayName("Should return empty list if length if max credit amount is less than minimal credit")
    @Test
    public void shouldReturnEmptyOffersForAmountLessThanMinimalCreditAmount() {
        CreditApplicationParameters params = new CreditApplicationParameters(100, new BigDecimal(10000),
            new BigDecimal(1000), new BigDecimal(1000), new BigDecimal(199000));

        List<CreditOffer> offers = calculator.getOffers(params);

        Assertions.assertEquals(0, offers.size(), "Number of credit offers should be 0, but was " + offers.size());
    }

    @DisplayName("Should return two offers for max length 24 months")
    @Test
    public void shouldReturnTwoOffersForMaxLengthTwentyFourMonths() {
        CreditApplicationParameters params = new CreditApplicationParameters(24, new BigDecimal(10000),
            new BigDecimal(1000), new BigDecimal(1000), new BigDecimal(1000));

        List<CreditOffer> offers = calculator.getOffers(params);

        Assertions.assertEquals(2, offers.size(), "Number of credit offers should be 2, but was " + offers.size());
    }

    @DisplayName("Should return four offers for max length 100 months")
    @Test
    public void shouldReturnFourOffersForMaxLengthOneHundredMonths() {
        CreditApplicationParameters params = new CreditApplicationParameters(100, new BigDecimal(10000),
            new BigDecimal(1000), new BigDecimal(1000), new BigDecimal(1000));

        List<CreditOffer> offers = calculator.getOffers(params);

        Assertions.assertEquals(4, offers.size(), "Number of credit offers should be 4, but was " + offers.size());
    }


}