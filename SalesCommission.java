package app;

import java.util.HashMap;
import java.util.Scanner;

public class SalesCommission {
    /*
     * You're a regional manager for an office beverage sales company, and right now
     * you're in charge of paying your sales team they're monthly commissions.
     * 
     * Sales people get paid using the following formula for the total commission:
     * commission is 6.2% of profit, with no commission for any product to total
     * less than zero.
     */

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<String, HashMap<String, Double>> commissionCalc = new HashMap<String, HashMap<String, Double>>();

        String[] salesmen = new String[] { "Johnver", "Vanston", "Danbree", "Vansey", "Mundyke" };

        String[] products = new String[] { "tea", "coffee", "water", "milk" };

        for (String person : salesmen) {
            System.out.println(person + " commisions: ");

            HashMap<String, Double> productCommission = new HashMap<String, Double>();
            for (int i = 0; i < 4; i++) {

                System.out.println("Enter revenue for " + products[i]);
                int revenue = sc.nextInt();
                System.out.println("Enter expenses for " + products[i]);
                int expenses = sc.nextInt();

                double commission = calculateCommision(revenue, expenses);

                productCommission.put(products[i], commission);

            }
            commissionCalc.put(person, productCommission);
        }

        for (String person : salesmen) {
            int commission = 0;
            for (int i = 0; i < 4; i++) {
                commission += commissionCalc.get(person).get(products[i]);
            }
            System.out.println(person + " commision is " + commission);
        }

    }

    public static double calculateCommision(int revenue, int expenses) {

        if (revenue <= expenses) {
            return 0;
        } else {
            return ((revenue - expenses) * 0.062);
        }
    }
}
