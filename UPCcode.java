package app;

public class UPCcode {
    /*
     * The Universal Product Code (UPC-A) is a bar code used in many parts of the
     * world. The bars encode a 12-digit number used to identify a product for sale,
     * for example:
     * 
     * 042100005264
     * 
     * The 12th digit (4 in this case) is a redundant check digit, used to catch
     * errors. Using some simple calculations, a scanner can determine, given the
     * first 11 digits, what the check digit must be for a valid code. 
     * 
     * Sum the digits at odd-numbered positions (1st, 3rd, 5th, ..., 11th). If you
     * use 0-based indexing, this is the even-numbered positions (0th, 2nd, 4th, ...
     * 10th).
     * 
     * Multiply the result from step 1 by 3.
     * 
     * Take the sum of digits at even-numbered positions (2nd, 4th, 6th, ..., 10th)
     * in the original number, and add this sum to the result from step 2.
     * 
     * Find the result from step 3 modulo 10 (i.e. the remainder, when divided by
     * 10) and call it M.
     * 
     * If M is 0, then the check digit is 0; otherwise the check digit is 10 - M.
     */

    public static int lastnum(String x) {
        int sum = 0;
        while (x.length() < 11)
            x = "0" + x;

        for (int i = 0; i < x.length(); i += 2) {
            sum += Character.getNumericValue(x.charAt(i));
        }
        sum = sum * 3;
        for (int k = 1; k < x.length(); k += 2) {
            sum += Character.getNumericValue(x.charAt(k));
        }
        sum = sum % 10;
        return (sum == 0 ? 0 : 10 - sum);
    }

}