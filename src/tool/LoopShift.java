package tool;

/**
 * @author 胡光邦
 */
public class LoopShift {

    /**
     * 循环左移（先部分反转，后整体反转）
     * @param sa
     * @param n
     * @param move
     * @return
     */
    public static int[] loopShiftLeft(int[] sa,int n,int move){
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < n;i ++){
            sb.append(sa[i]);
        }
        String from = sb.toString();
        String first = from.substring(0,move);
        String second = from.substring(move);
        first = reChange(first);
        second = reChange(second);
        from = first + second;
        from = reChange(from);
        for(int i = 0;i<from.length();i++){
            sa[i] = Integer.valueOf(String.valueOf(from.charAt(i)));
        }
        return sa;
    }

    /**
     * 循环右移（先整体反转，后部分反转）
     * @param sa
     * @param n
     * @param move
     * @return
     */
    public static int[] loopShiftRight(int[] sa,int n,int move){
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < n;i ++){
            sb.append(sa[i]);
        }
        String from = sb.toString();
        from = reChange(from);
        String first = from.substring(0,move);
        String second = from.substring(move);
        first = reChange(first);
        second = reChange(second);
        from = first + second;
        for(int i = 0;i<from.length();i++){
            sa[i] = Integer.valueOf(String.valueOf(from.charAt(i)));
        }
        return sa;
    }
    /**
     * 反转字符串
     * @param from
     * @return
     */
    public static String reChange(String from){
        char[] froms = from.toCharArray();
        int length = froms.length;
        for (int i = 0; i < length/2; i++){
            char temp = froms[i];
            froms[i] = froms[length - 1 -i];
            froms[length - 1 -i] = temp;
        }
        return String.valueOf(froms);
    }

}
