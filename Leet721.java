import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 并查集解法
 * 关于并查集：https://segmentfault.com/a/1190000004023326
 * <p>
 * 1. 用集合中的某个元素来代表这个集合，该元素称为集合的代表元。
 * 2. 一个集合内的所有元素组织成以代表元为根的树形结构。
 * 3. 对于每一个元素 parent[x]指向x在树形结构上的父亲节点。如果x是根节点，则令parent[x] = x。
 * 4. 对于查找操作，假设需要确定x所在的的集合，也就是确定集合的代表元。可以沿着parent[x]不断在树形结构中向上移动，直到到达根节点
 */
public class Leet721 {

    public static void main(String[] args) {
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

        List<List<String>> mergedAccounts = accountsMerge(testCase3);
        mergedAccounts.stream().forEach(account -> {
            try {
                System.out.println(new ObjectMapper().writeValueAsString(account));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 1. 每个account中，第一个已构建的Node的RootNode(都未构建可随机选取一个构建为Node), 作为这个account所有Node及其RootNode的父节点
     * 2. 拥有同一RootNode的Node同属一个集合
     */
    public static List<List<String>> accountsMerge(List<List<String>> accounts) {

        /**
         * 每个account中，第一个已构建的Node的RootNode, 作为这个account所有Node及其RootNode的父节点
         * nodeMap 用于存储已构建的Node, key是email名字
         */
        Map<String, Node> nodeMap = new HashMap();
        for (List<String> account : accounts) {
            String name = account.get(0);
            List<String> emails = account.subList(1, account.size());
            Node representativeNode = getRepresentativeNode(nodeMap, name, emails);
            for (String email : emails) {
                Node node = nodeMap.get(email);
                if (node == null) {
                    node = new Node(name, email, representativeNode);
                    nodeMap.put(email, node);
                } else {
                    getRootNode(node).parent = representativeNode;
                }
            }
        }

        /**
         * nodeMap的所有Node, 拥有同一parent的同属一个集合
         */
        Map<Node, Set<Node>> nodeEmailMap = new HashMap();
        for (Node node : nodeMap.values()) {
            Node rootNode = getRootNode(node);
            if (nodeEmailMap.containsKey(rootNode)) {
                nodeEmailMap.get(rootNode).add(node);
            } else {
                nodeEmailMap.put(rootNode, new HashSet<>(Arrays.asList(node)));
            }
        }

        return nodeEmailMap
                .values()
                .stream()
                .map((Set<Node> set) -> {
                    LinkedList<String> account = new LinkedList<>();
                    String name = null;
                    for (Node node : set) {
                        name = node.name;
                        account.add(node.email);
                    }
                    account.push(name);
                    return account;
                })
                .collect(Collectors.toList());

    }

    /**
     * 获取account集合里代表Node
     * 1. emails 中第一个已在 nodeMap 中出现的 Node 的 RootNode
     * 2. 若 emails 在 nodeMap 中都未出现过，用第一个email构建一个新的 RootNode（并存入nodeMap）
     *
     * @param nodeMap
     * @param name
     * @param emails
     * @return
     */
    private static Node getRepresentativeNode(Map<String, Node> nodeMap, String name, List<String> emails) {
        Node node;
        for (String email : emails) {
            node = nodeMap.get(email);
            if (node != null) {
                return getRootNode(node);
            }
        }
        String firstEmail = emails.get(0);
        node = new Node(name, firstEmail);
        nodeMap.put(firstEmail, node);
        return node;
    }

    /**
     * 获取Node的RootNode
     *
     * @param node
     * @return
     */
    private static Node getRootNode(Node node) {
        if (node.parent == node) {
            return node;
        }
        return getRootNode(node.parent);
    }

    static class Node {
        public String name;
        public String email;
        public Node parent;

        public Node(String name, String email) {
            this.name = name;
            this.email = email;
            this.parent = this;
        }

        public Node(String name, String email, Node parent) {
            this.name = name;
            this.email = email;
            this.parent = parent;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Node node = (Node) o;
            return Objects.equals(email, node.email);
        }

        @Override
        public int hashCode() {
            return email != null ? email.hashCode() : 0;
        }
    }
}
