/**
 * @author 胡光邦
 */
public class IP {
    /**
     * 置换IP表（8 * 8）
     */
    private static final int[][] IP_TABLE = {
            {58,50,42,34,26,18,10, 2},
            {60,52,44,36,28,20,12, 4},
            {62,54,46,38,30,22,14, 6},
            {64,56,48,40,32,24,16, 8},
            {57,49,41,33,25,17, 9, 1},
            {59,51,43,35,27,19,11, 3},
            {61,53,45,37,29,21,13, 5},
            {63,55,47,39,31,23,15, 7}
    };
    /**
     * 逆初始置换IP-1表（8 * 8）
     */
    private static final int[][] IP_1_TABLE = {
            {40, 8,48,16,56,24,64,32},
            {39, 7,47,15,55,23,63,31},
            {38, 6,46,14,54,22,62,30},
            {37, 5,45,13,53,21,61,29},
            {36, 4,44,12,52,20,60,28},
            {35, 3,43,11,51,19,59,27},
            {34, 2,42,10,50,18,58,26},
            {33, 1,41, 9,49,17,57,25}
    };
    /**
     * IP(置换后)
     */
    private static int[] ip = new int[64];

    /**
     * 初始置换IP
     * @param message   输入：64位
     * @return  输入：64位
     */
    protected static int[] initialReplacementIP(int[] message){
        for(int i = 0;i < 8;i ++){
            for(int j = 0;j < 8;j ++){
                ip[i * 8 + j] = message[IP_TABLE[i][j] - 1];
            }
        }
        return ip;
    }

    /**
     * 逆初始置换IP-1
     * @param a 输入：64位
     * @return 输入：64位
     */
    protected  static int[] initialReplacementIP_1(int[] a){
        for(int i = 0;i < 8;i ++){
            for(int j = 0;j < 8;j ++){
                ip[i * 8 + j] = a[IP_1_TABLE[i][j] - 1];
            }
        }
        return ip;
    }
}
