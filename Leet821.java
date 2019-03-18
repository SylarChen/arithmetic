


import java.util.Arrays;

/**
 * @author chenheng
 * @date 2019/3/14
 */
public class Leet53B {

    public static void main(String[] args) {
        String testCase = "loveleetcode";
        char target = 'e';
        System.out.println(Arrays.toString(shortestToChar(testCase, target)));
    }

    /**
     * 2次循环
     * shortestToChar("loveleetcode", e)
     * 输入：         [l   o   v   e   l   e   e   t   c   o   d   e]
     * 1. 从左到右    [max,max,max,0,  1,  0,  0,  1,  2,  3,  4,  0]
     * 2. 从右到左    [(3),(2),(1),0,  1,  0,  0,  1,  2,(2),(1),  0]
     *
     * @param str
     * @param t
     * @return
     */
    public static int[] shortestToChar(String str, char t) {
        int[] result = new int[str.length()];
        Integer currentTargetIndex = null;
        char[] in = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (in[i] == t) {
                currentTargetIndex = i;
            }
            if (currentTargetIndex == null) {
                result[i] = Integer.MAX_VALUE;
            } else {
                result[i] = Math.abs(i - currentTargetIndex);
            }
        }
        for (int i = str.length() - 1; i >= 0; i--) {
            if (in[i] == t) {
                currentTargetIndex = i;
            }
            if (currentTargetIndex == null) {
                continue;
            } else {
                result[i] = Math.min(Math.abs(i - currentTargetIndex), result[i]);
            }
        }
        return result;
    }

}
