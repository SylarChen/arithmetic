import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 图的基本概念 ：http://www.cnblogs.com/skywang12345/p/3691463.html
 * 两种存储方式：
 * 1.邻接矩阵
 * 2.邻接表
 */
public class Leet721B {

    public static void main(String[] args) throws JsonProcessingException {
        List<List<String>> testCase = Arrays.asList(
                Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com"),
                Arrays.asList("John", "johnnybravo@mail.com"),
                Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"),
                Arrays.asList("Mary", "mary@mail.com"));

        List<List<String>> testCase2 = Arrays.asList(
                Arrays.asList("A", "1", "2"),
                Arrays.asList("A", "3", "4"),
                Arrays.asList("A", "1", "3", "5"),
                Arrays.asList("A", "4", "8"),
                Arrays.asList("M", "9", "12"));

        List<List<String>> testCase3 = Arrays.asList(
                Arrays.asList("A", "1", "2"),
                Arrays.asList("A", "2", "3"),
                Arrays.asList("A", "3", "1"));

        List<List<String>> mergedAccounts = accountsMerge(testCase);
        System.out.println(new ObjectMapper().writeValueAsString(mergedAccounts));
    }

    public static List<List<String>> accountsMerge(List<List<String>> accounts) {

        /**
         * 从0开始，给邮箱编码
         */
        Map<String, Integer> emailIdMap = new HashMap();
        Map<Integer, String> idEmailMap = new HashMap();
        Map<String, String> emailNameMap = new HashMap();
        int emailId = 0;
        for (List<String> account : accounts) {
            List<String> emails = account.subList(1, account.size());
            String name = account.get(0);
            for (String email : emails) {
                if (emailIdMap.containsKey(email)) {
                    continue;
                }
                emailIdMap.put(email, emailId);
                idEmailMap.put(emailId, email);
                emailNameMap.put(email, name);
                emailId++;
            }
        }

        /**
         * 构建邻接矩阵，连通同个account中的所有email
         */
        int[][] graph = new int[emailId][emailId];
        for (List<String> account : accounts) {
            String[] emails = account.subList(1, account.size()).toArray(new String[account.size() - 1]);
            for (int i = 0; i < emails.length; i++) {
                for (int j = i; j < emails.length; j++) {
                    String emailA = emails[i];
                    String emailB = emails[j];
                    graph[emailIdMap.get(emailA)][emailIdMap.get(emailB)] = 1;
                    graph[emailIdMap.get(emailB)][emailIdMap.get(emailA)] = 1;
                }
            }
        }

        /**
         * 打印图
         */
        System.out.println(emailIdMap
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt((Map.Entry<String, Integer> entry) -> entry.getValue()))
                .map(p -> p.getKey())
                .collect(Collectors.joining(", ")));
        for (int[] arr : graph) {
            System.out.println(Arrays.toString(arr));
        }

        /**
         * 遍历图
         */
        return bsf(graph, graph[0].length, idEmailMap, emailNameMap);
    }

    /**
     * ================================================================================
     */

    /**
     * 等待遍历的 email id
     */
    private static LinkedList<Integer> waitedQueue = new LinkedList();

    /**
     * 已遍历的 email id
     */
    private static Set<Integer> accessedSet = new HashSet();

    /**
     * 广度优先遍历
     *
     * @param graph     邻接矩阵
     * @param graphSize 最大id
     * @return
     */
    private static List<List<String>> bsf(
            int[][] graph,
            int graphSize,
            Map<Integer, String> idEmailMap,
            Map<String, String> emailNameMap) {
        int startId = 0;
        List<List<String>> result = new ArrayList();
        do {
            if (accessedSet.contains(startId)) {
                continue;
            }
            waitedQueue.add(startId);
            List<Integer> sameGroupId = getAllAbutId(graph);
            List<String> account = new LinkedList<>();
            account.add(emailNameMap.get(idEmailMap.get(sameGroupId.get(0))));
            account.addAll(sameGroupId.stream().map(idEmailMap::get).collect(Collectors.toList()));
            result.add(account);
        } while (++startId < graphSize);
        return result;
    }

    private static LinkedList<Integer> getAllAbutId(int[][] graph) {
        if (waitedQueue.isEmpty()) {
            return new LinkedList();
        }
        LinkedList<Integer> abutIdList = new LinkedList();
        int currentId = waitedQueue.pollFirst();
        if (!accessedSet.contains(currentId)) {
            abutIdList.add(currentId);
            accessedSet.add(currentId);
        }
        for (int otherId = 0; otherId < graph[currentId].length; otherId++) {
            if (!accessedSet.contains(otherId) && graph[currentId][otherId] == 1) {
                abutIdList.add(otherId);
                accessedSet.add(otherId);
                waitedQueue.add(otherId);
            }
        }
        abutIdList.addAll(getAllAbutId(graph));
        return abutIdList;
    }

}
