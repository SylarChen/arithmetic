
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by chenheng on 2019/2/18.
 */
public class A {

    /**
     * 给一个有序字母字符串，按字母序打印所有可能
     */

    public static void main(String[] args) {
        String test = "abcde";
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < test.length(); i++) {
            String arg = pick(i, test);
            ret.addAll(fn(test.charAt(i), arg));
        }
        System.currentTimeMillis();
    }

    public static List<String> fn(char first, String origin) {

        if (origin.length() == 1) {
            return Arrays.asList(first + origin);
        }

        List<String> ret = new ArrayList<>();
        for (int i = 0; i < origin.length(); i++) {
            String arg = pick(i, origin);
            ret.addAll(fn(origin.charAt(i), arg));
        }

        return ret.stream().map(p -> first + p).collect(Collectors.toList());
    }

    private static String pick(int in, String origin) {
        String ret = "";
        for (int i = 0; i < origin.length(); i++) {
            if (i != in) {
                ret += origin.charAt(i);
            }
        }
        return ret;
    }

}
