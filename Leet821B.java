    public static void main(String[] args) {
        String testCase = "loveleetcode";
        char target = 'e';
        System.out.println(Arrays.toString(shortestToChar(testCase, target)));
    }

    /**
     * 第一遍循环: 利用treeset插入target的index
     * 第二遍循环: 对每个index找treeset的floor和celling, 取与index的差最小
     *
     * @param str
     * @param t
     * @return
     */
    public static int[] shortestToChar(String str, char t) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == t) {
                treeSet.add(i);
            }
        }
        int[] result = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            Integer leftIndexOfTarget = treeSet.floor(i);
            Integer rightIndexOfTarget = treeSet.ceiling(i);
            if (leftIndexOfTarget == null) {
                result[i] = Math.abs(rightIndexOfTarget - i);
            } else if (rightIndexOfTarget == null) {
                result[i] = Math.abs(leftIndexOfTarget - i);
            } else {
                result[i] = Math.min(Math.abs(rightIndexOfTarget - i), Math.abs(leftIndexOfTarget - i));
            }
        }
        return result;
    }
