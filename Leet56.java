
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 思路：先排序，再合并
 * 解C和解D证明了对数组的排序快于对list的排序
 *
 * @author chenheng
 * @date 2019/3/14
 */
public class Leet56 {

    /**
     * Given [1,3],[2,6],[8,10],[15,18],
     * return [1,6],[8,10],[15,18].
     */
    public static void main(String[] args) {
        List<Interval> testCase = Arrays.asList(
                new Interval(1, 3),
                new Interval(2, 6),
                new Interval(8, 10),
                new Interval(15, 18));

        System.out.println(mergeIntervalsA(testCase)
                .stream()
                .map(p -> String.format("[%s,%s]", p.start, p.end))
                .collect(Collectors.joining(", ")));

        testCase = Arrays.asList(
                new Interval(1, 3),
                new Interval(2, 6),
                new Interval(8, 10),
                new Interval(15, 18));
        System.out.println(mergeIntervalsB(testCase)
                .stream()
                .map(p -> String.format("[%s,%s]", p.start, p.end))
                .collect(Collectors.joining(", ")));

        testCase = Arrays.asList(
                new Interval(1, 3),
                new Interval(2, 6),
                new Interval(8, 10),
                new Interval(15, 18));
        System.out.println(mergeIntervalsC(testCase)
                .stream()
                .map(p -> String.format("[%s,%s]", p.start, p.end))
                .collect(Collectors.joining(", ")));

        testCase = Arrays.asList(
                new Interval(1, 3),
                new Interval(2, 6),
                new Interval(8, 10),
                new Interval(15, 18));
        System.out.println(mergeIntervalsD(testCase)
                .stream()
                .map(p -> String.format("[%s,%s]", p.start, p.end))
                .collect(Collectors.joining(", ")));
    }

    /**
     * 1. 排序
     * 2. 后一个的start小于前一个的end, 合并(end=max(last.end, curr.end))
     * 复杂度较高，体现在排序上
     *
     * @param list
     * @return
     */
    public static List<Interval> mergeIntervalsA(List<Interval> list) {
        if (list == null || list.size() <= 1) {
            return list;
        }
        list.sort(Comparator.comparing(p -> p.start));
        List<Interval> result = new ArrayList<>();
        Integer start = list.get(0).start;
        Integer end = list.get(0).end;
        for (Interval interval : list) {
            if (interval.start <= end) {
                end = Math.max(interval.end, end);
                continue;
            } else {
                result.add(new Interval(start, end));
                start = interval.start;
                end = interval.end;
            }
        }
        result.add(new Interval(start, end));
        return result;
    }

    /**
     * A解的lambda版本
     *
     * @param list
     * @return
     */
    public static List<Interval> mergeIntervalsB(List<Interval> list) {
        return list.stream()
                .sorted(Comparator.comparing(p -> p.start))
                .collect(Collector.of(
                        () -> new LinkedList<Interval>(),
                        (stack, currentInterval) -> {
                            if (stack.size() == 0) {
                                stack.push(currentInterval);
                                return;
                            }
                            Interval lastInterval = stack.peek();
                            if (currentInterval.start <= lastInterval.end) {
                                lastInterval.end = Math.max(currentInterval.end, lastInterval.end);
                            } else {
                                stack.push(currentInterval);
                            }
                        },
                        (r1, r2) -> r1));
    }

    public static List<Interval> mergeIntervalsC(List<Interval> list) {
        if (list == null || list.size() <= 1) {
            return list;
        }
        List<Integer> startList = list.stream().map(p -> p.start).collect(Collectors.toList());
        List<Integer> endList = list.stream().map(p -> p.end).collect(Collectors.toList());
        Collections.sort(startList);
        Collections.sort(endList);

        Integer start = startList.get(0);
        Integer end = endList.get(0);
        List<Interval> result = new ArrayList<>();
        for (int i = 0; i < startList.size(); i++) {
            if (startList.get(i) <= end) {
                end = Math.max(endList.get(i), end);
                continue;
            } else {
                result.add(new Interval(start, end));
                start = startList.get(i);
                end = endList.get(i);
            }
        }
        result.add(new Interval(start, end));
        return result;
    }

    public static List<Interval> mergeIntervalsD(List<Interval> list) {
        if (list == null || list.size() <= 1) {
            return list;
        }
        int n = list.size();
        int[] starts = new int[n];
        int[] ends = new int[n];
        for (int i = 0; i < n; i++) {
            starts[i] = list.get(i).start;
            ends[i] = list.get(i).end;
        }
        Arrays.sort(starts);
        Arrays.sort(ends);
        List<Interval> result = new ArrayList(list.size() * 2);
        Integer start = starts[0];
        Integer end = ends[0];
        for (int i = 0, j = 0; i < n; i++) {
            if (starts[i] <= end) {
                end = Math.max(ends[i], end);
                continue;
            } else {
                result.add(new Interval(start, end));
                start = starts[i];
                end = ends[i];
            }
        }
        result.add(new Interval(start, end));
        return result;
    }
}

class Interval {
    int start;
    int end;

    Interval(int s, int e) {
        start = s;
        end = e;
    }
}
