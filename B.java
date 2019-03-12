package com.ctrip.flight.mobile.nb.mega.transport.web;


import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenheng
 * @date 2019/3/12
 */
public class Test2 {

    /**
     * 找出最长回文子串
     * 动态规划解
     *
     * @param args
     */
    public static void main(String[] args) {
        String testCase = "baabbaababcdefggfedcbaswqqccqq";
        System.out.println(foo(testCase));
    }

    /**
     * 找出最长回文子串
     *
     * @param testCase
     * @return
     */
    public static String foo(String testCase) {
        Map<Pair<Integer, Integer>, Boolean> resultMap = new HashMap();
        /**
         *
         */
        int gap = 0;
        while (gap < testCase.length()) {
            for (int start = 0; start + gap < testCase.length(); start++) {
                checkSubString(resultMap, testCase, start, start + gap);
            }
            gap++;
        }
        Pair<Integer, Integer> maxPair = findMaxPair(resultMap);
        return testCase.substring(maxPair.getKey(), maxPair.getValue() + 1);
    }

    /**
     * @param resultMap
     * @return 最大的i, j差值Pair
     */
    private static Pair<Integer, Integer> findMaxPair(Map<Pair<Integer, Integer>, Boolean> resultMap) {
        Pair<Integer, Integer> maxPair = null;
        for (Map.Entry<Pair<Integer, Integer>, Boolean> entry : resultMap.entrySet()) {
            if (!entry.getValue()) {
                continue;
            }
            Pair<Integer, Integer> pair = entry.getKey();
            if (maxPair == null) {
                maxPair = pair;
            } else {
                maxPair = (pair.getValue() - pair.getKey()) > (maxPair.getValue() - maxPair.getKey())
                        ? pair
                        : maxPair;
            }
        }
        return maxPair;
    }

    /**
     * TestCase[i,j] 为回文的条件：
     * 1. j - i <= 1 ：TestCase[i] == TestCase[j]
     * 2. j - i  > 1 ：TestCase[i+1,j-1] 且 TestCase[i] == TestCase[j]
     *
     * @param resultMap
     * @param in
     * @param i
     * @param j
     */
    public static void checkSubString(Map<Pair<Integer, Integer>, Boolean> resultMap, String in, int i, int j) {
        Pair pair = new Pair(i, j);
        if (j - i <= 1) {
            resultMap.put(pair, in.charAt(i) == in.charAt(j));
        } else {
            boolean isValid = resultMap.get(new Pair(i + 1, j - 1)) && in.charAt(i) == in.charAt(j);
            resultMap.put(pair, isValid);
        }
    }

}
