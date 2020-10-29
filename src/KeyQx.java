import tool.BHDConverter;
import tool.LoopShift;

/**
 * @author 胡光邦
 */
public class KeyQx {
    /**
     * 循环移位表
     */
    private static final short[] loopShift = {1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};
    /**
     * 子密钥生成 置换选择PC1(4*14) 左边部分：Ci 右半部分：Di
     */
    private static final int[][] pc1 = {
            {57,49,41,33,25,17, 9,63,55,47,39,31,23,15},
            { 1,58,50,42,34,26,18, 7,62,54,46,38,30,22},
            {10, 2,59,51,43,35,27,14, 6,61,53,45,37,29},
            {19,11, 3,60,52,44,36,21,13, 5,28,20,12, 4}
    };
    /**
     * 子密钥生成 置换选择PC2(4*12)上2行：Ci 下2行：Di
     */
    private static final int[][] pc2 = {
            {14,17,11,24, 1, 5, 3,28,15, 6,21,10},
            {23,19,12, 4,26, 8,16, 7,27,20,13, 2},
            {41,52,31,37,47,55,30,40,51,45,33,48},
            {44,49,39,56,34,53,46,42,50,36,29,32}
    };
    /**
     * 十六进制密钥
     */
    private String key = "";
    /**
     * 64位二进制密钥
     */
    private int[] keyBinary = new int[64];
    /**
     * 16个子密钥
     */
    private int[][] subKey = new int[16][48];

    KeyQx(String key){
        if(key.length()!=16){
            System.out.println("密钥长度错误");
            System.exit(-1);
        }
        this.key = key;
        System.out.println("密钥:\n" + this.key);
        String bs = BHDConverter.hexString2binaryString(key);
        for(int i = 0;i < bs.length();i ++){
            keyBinary[i] = Integer.parseInt(String.valueOf(bs.charAt(i)));
        }
        System.out.println("二进制key:");
        for(int i = 0;i < 64;i ++){
            System.out.print(keyBinary[i]);
        }
        System.out.println();
        System.out.println("——————————— 子密钥生成开始 ———————————");
        generateSubKey();
        System.out.println("——————————— 子密钥生成结束 ———————————");
    }

    /**
     * 生成子密钥
     */
    protected void generateSubKey(){
        int[] C = new int[28];
        int[] D = new int[28];
        //置换选择1
        for(int i = 0;i < 4;i ++){
            for(int j = 0;j < 7;j ++){
                C[i * 7 + j] = keyBinary[pc1[i][j]-1];
            }
            for(int j = 7;j < 14;j ++){
                D[i * 7 + j - 7] = keyBinary[pc1[i][j]-1];
            }
        }
        //循环16次，生成子密钥
        for(int ii = 0;ii < 16;ii ++){
            System.out.print("subkey"+(ii+1)+":\t");
            C = LoopShift.loopShiftLeft(C,28,loopShift[ii]);
            D = LoopShift.loopShiftLeft(D,28,loopShift[ii]);
            //合并C和D
            int[] CD = new int[56];
            for(int i = 0;i < 28;i ++){
                CD[i] = C[i];
            }
            for(int i = 28;i < 56;i ++){
                CD[i] = D[i-28];
            }
            //合并后根据置换选择2生成子密钥
            for(int i = 0;i < 4;i ++){

                for(int j = 0;j < 12;j ++){
                    subKey[ii][i*12+j] = CD[pc2[i][j]-1];
                }
            }
            for(int i = 0;i < 48;i ++){
                System.out.print(subKey[ii][i]);
            }
            System.out.println();
        }
    }

    /**
     * 获取子密钥i
     * @param i
     * @return
     */
    protected int[] getSubKey(int i){
        return subKey[i];
    }
}
