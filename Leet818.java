package com.ctrip.flight.mobile.home.fuzzysearch.web;

public class Solution {

    public static void main(String[] args) {
        System.out.println(racecar(20));
    }

    public static int racecar(int target) {
        return dp(target);
    }

    /**
     * 存储已计算过的数据
     */
    private static int[] dpResult = new int[10000];

    /**
     * dp(t) 代表距离从 0 -> t，初速度为 1 的最优解
     */
    private static int dp(int t) {
        if (dpResult[t] > 0) {
            return dpResult[t];
        }

        /**
         * [2^n - 1] <= t < [2^(n+1) - 1]
         * dl : 越过t前的位置 即 [2^n - 1]
         * dr : 越过t后的位置 即 [2^(n+1) - 1]
         */
        int n = (int) (Math.log(t + 1) / Math.log(2));
        int dl = (int) (Math.pow(2, n) - 1);
        int dr = (int) (Math.pow(2, n + 1) - 1);

        /**
         * dl刚好等于t，则d(t)的最优解就是n
         */
        if (t == dl) {
            return saveResult(t, n);
        }

        /**
         * n次A后到达dl，一次R, 接着找回走i次后的最优解 （i为0～n-1）
         */
        int leftMin = Integer.MAX_VALUE;
        for (int i = 0; i <= n - 1; i++) {
            /**
             * n -> n次A
             * 1 -> R
             * i -> i次A（反方向在走）
             * 1 -> R (再次反向后变正方向)
             * t - dl + (2^i - 1) -> n次A 1次R i次A 1次R 后的位置到t的距离
             */
            leftMin = Math.min(leftMin, n + 1 + i + 1 + dp(t - dl + ((int) Math.pow(2, i) - 1)));
        }

        /**
         * n+1次A后，越过了t，到达dr
         * n+1 -> n+1次A
         * 1 -> R
         * dr - t -> 当前位置dr到t的距离
         */
        int rightMin = n + 1 + 1 + dp(dr - t);

        
        int result = Math.min(leftMin, rightMin);
        return saveResult(t, result);
    }

    /**
     * @param t     距离
     * @param steps dp(t)的最优解
     * @return steps
     */
    private static int saveResult(int t, int steps) {
        dpResult[t] = steps;
        return steps;
    }

}
