package pl.adamludzia.service;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import pl.adamludzia.model.CreditApplicationParameters;

public class CreditApplicationParametersProvider {
    public CreditApplicationParameters getCreditParameters() {
        Scanner scanner = new Scanner(System.in);
        int lengthOfEmployment = getLengthOfEmployment(scanner);
        BigDecimal monthlyIncome = getMonthlyIncome(scanner);
        BigDecimal monthlyCostsOfLiving = getMonthlyCostsOfLiving(scanner);
        BigDecimal monthlyCreditPayments = getMonthlyCreditPayments(scanner);
        BigDecimal remainingCreditsToPay = getRemainingCreditsToPay(scanner);
        scanner.close();

        try {
            var params = new CreditApplicationParameters(lengthOfEmployment,
                monthlyIncome, monthlyCostsOfLiving, monthlyCreditPayments,
                remainingCreditsToPay);
            return params;
        }
        catch (InputMismatchException e) {
            System.out.println("ERR: Błędne parametry wniosku o kredyt.");
        }

        return null;
    }

    private BigDecimal getRemainingCreditsToPay(Scanner scanner) {
        System.out.println("Podaj saldo wszystkich pozostałych zobowiązań kredytowych:");
        double remainingCredtits = scanner.nextDouble();
        return new BigDecimal(remainingCredtits);
    }

    private BigDecimal getMonthlyCreditPayments(Scanner scanner) {
        System.out.println("Podaj sumę miesięcznych obciążeń kredytowych:");
        double monthlyCreditPayments = scanner.nextDouble();
        return new BigDecimal(monthlyCreditPayments);
    }

    private BigDecimal getMonthlyCostsOfLiving(Scanner scanner) {
        System.out.println("Podaj sumę miesięcznych kosztów życia:");
        double monthlyCostsOfLiving = scanner.nextDouble();
        return new BigDecimal(monthlyCostsOfLiving);
    }

    private BigDecimal getMonthlyIncome(Scanner scanner) {
        System.out.println("Podaj miesięczne przychody:");
        double monthlyIncome = scanner.nextDouble();
        return new BigDecimal(monthlyIncome);
    }

    private int getLengthOfEmployment(Scanner scanner) {
        System.out.println("Podaj długość obecnego zatrudnienia (w miesiącach):");
        return scanner.nextInt();
    }

}
