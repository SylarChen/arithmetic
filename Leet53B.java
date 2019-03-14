package com.hpe.sylar.puzzlers;

/**
 * Created by Administrator on 2019/3/14 0014.
 */
public class Leet53B {

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    /**
     * 设 d[i] 的值为 0至i的最大子串和（必须包含i）
     * 则推倒 d[i-1] 至 d[i] 的逻辑如下：
     * <p>
     * d[i-1]  |  arr[i]  |       d[i]计算逻辑
     *    +    |    +     |    d[i - 1] + arr[i]
     *    +    |    -     |    d[i - 1] + arr[i]
     *    -    |    +     |          arr[i]
     *    -    |    -     |          arr[i]
     */
    public static int maxSubArray(int[] arr) {
        int[] d = new int[arr.length];
        int max = arr[0];
        d[0] = arr[0];
        for (int i = 1; i < d.length; i++) {
            d[i] = d[i - 1] > 0 ? (d[i - 1] + arr[i]) : arr[i];
            max = Math.max(d[i], max);
        }
        return max;
    }

}
