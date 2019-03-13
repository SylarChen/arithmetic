package com.ctrip.flight.mobile.nb.mega.web;

/**
 * @author chenheng
 * @date 2019/3/13
 */
public class IntUtils {

    public static int reverse(int in) {
        int remain = Math.abs(in);
        int result = 0;
        boolean isNegative = in < 0;
        while (remain > 0) {
            int num = remain % 10;
            remain = remain / 10;
            if (isOverFlow(result, num, isNegative)) {
                return 0;
            }
            result = result * 10 + num;
        }
        return isNegative ? -result : result;
    }

    /**
     * 1. Integer.MAX_VALUE / 10 就小于 result
     * 2. Integer.MAX_VALUE / 10 等于 result， 就看num和Integer.MAX_VALUE的个位相比
     *
     * @param result
     * @param num
     * @param isNegative
     * @return
     */
    private static boolean isOverFlow(int result, int num, boolean isNegative) {
        if (isNegative) {
            return Integer.MIN_VALUE / 10 > -result
                    || (Integer.MIN_VALUE / 10 == -result && -num < -8);
        } else {
            return Integer.MAX_VALUE / 10 < result
                    || (Integer.MAX_VALUE / 10 == result && num > 7);
        }
    }

    public static void main(String[] args) {
        System.out.println(reverse(123));
        System.out.println(reverse(-123));
        System.out.println(reverse(120));
    }

}
