/**
 * @author 胡光邦
 */
public class P {
    /**
     * 置换运算P表
     */
    private static final int[][] P_TABLE = {
            {16, 7,20,21,29,12,28,17},
            { 1,15,23,26, 5,18,31,10},
            { 2, 8,24,14,32,27, 3, 9},
            {19,13,30, 6,22,11, 4,25}
    };

    /**
     * P置换
     * @param a 输入：32位
     * @return  输出：32位
     */
    public static int[] pReplace(int[] a){
        StringBuffer result = new StringBuffer();
        int[] b = new int[32];
        for(int i = 0;i < 4;i ++){
            for(int j = 0;j < 8;j ++){
                result.append(a[P_TABLE[i][j] - 1]);
            }
        }
        for(int i = 0;i < 32;i ++){
            b[i] = Integer.valueOf(String.valueOf(result.toString().charAt(i)));
        }
        return b;
    }
}
