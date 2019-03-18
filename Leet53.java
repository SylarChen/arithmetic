

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;

/**
 * @author chenheng
 * @date 2019/3/14
 */
public class Leet53 {

    public static void main(String[] args) {
        System.out.println(getMaxPriceSum(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    private static HashMap<Pair<Integer, Integer>, Integer> resultMap = new HashMap();

    /**
     * 动态规划解
     *
     * @param in
     * @return
     */
    private static int getMaxPriceSum(int[] in) {
        int gap = 0;
        while (gap < in.length) {
            int i, j;
            for (i = 0; (j = i + gap) < in.length; i++) {
                calculateFromItoJ(in, i, j);
            }
            gap++;
        }
        return resultMap.values().stream().max(Integer::compare).get();
    }

    private static void calculateFromItoJ(int[] in, int i, int j) {
        int sum;
        if (i == j) {
            sum = in[i];
        } else {
            sum = resultMap.get(Pair.of(i, j - 1)) + in[j];
        }
        resultMap.put(Pair.of(i, j), sum);
    }

}
