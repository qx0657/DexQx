import tool.BHDConverter;

/**
 * @author 胡光邦
 */
public class DexQx {
    /**
     * 扩展置换E表（4*12）
     */
    private static final int[][] E_TABLE = {
            {32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9},
            { 8, 9,10,11,12,13,12,13,14,15,16,17},
            {16,17,18,19,20,21,20,21,22,23,24,25},
            {24,25,26,27,28,29,28,29,30,31,32, 1}
    };
    /**
     * 明文字符串
     */
    private String messageStr = "";
    /**
     * 明文（二进制）
     */
    private int[] message = new int[64];

    /**
     * 密钥管理类，用于管理和生成子密钥
     */
    private KeyQx mkey;

    DexQx(String key){
        //key长度小于16位前补0、key过长截取后16位
        if(key.length() < 16){
            StringBuffer sb = new StringBuffer();
            for(int i = 0;i < 16 - key.length();i ++){
                sb.append('0');
            }
            key = sb.toString() + key;
        }else{
            key = key.substring(key.length()-16);
        }
        //初始化Key（生成子密钥）
        mkey = new KeyQx(key);
    }

    /**
     * des加密
     */
    protected String desEncrypt(String msg){
        if(msg.length() != 16){
            System.out.println("明文长度错误");
            System.exit(-2);
        }
        System.out.println("\n明文：\n" + msg);
        this.messageStr = msg;
        String bs = BHDConverter.hexString2binaryString(this.messageStr);
        for(int i = 0;i < bs.length();i ++){
            message[i] = Integer.parseInt(String.valueOf(bs.charAt(i)));
        }
        System.out.println("明文M二进制：");
        for(int i = 0;i < 64;i ++){
            System.out.print(message[i]);
        }
        //初始置换IP
        int[] ip = new int[64];
        copy(IP.initialReplacementIP(message),ip,64);
        System.out.println("\n置换后IP：");
        for(int i = 0;i < 64;i ++){
            System.out.print(ip[i]);
        }
        System.out.println();
        int[] L = new int[32];
        int[] R = new int[32];
        int[] temp = new int[32];
        //将ip分为左右两部分L、R
        for(int i = 0;i < 32;i ++){
            L[i] = ip[i];
            R[i] = ip[i+32];
        }
        System.out.println("————————————— 十六轮迭代开始 —————————————");
        Print(L,R,0);
        //迭代执行16次轮函数f
        for(int i = 0;i < 16;i ++){
            copy(L,temp,32);
            copy(R,L,32);
            copy(Xor(temp,f(R,i),32),R,32);
            Print(L,R,i+1);
        }
        System.out.println("————————————— 十六轮迭代结束 —————————————");
        //合并L、R
        for(int i = 0;i < 32;i ++){
            ip[i] = R[i];
            ip[i + 32] = L[i];
        }
        System.out.println("IP(R16 L16)：");
        for(int i = 0;i < 64;i ++){
            System.out.print(ip[i]);
        }
        //逆初始置换IP-1
        copy(IP.initialReplacementIP_1(ip),ip,64);
        System.out.println("\n逆初始置换IP-1后：");
        for(int i = 0;i < 64;i ++){
            System.out.print(ip[i]);
        }
        System.out.println();
        //转为16进制返回
        return BHDConverter.binaryString2hexString(ip);
    }

    /**
     * f轮函数
     * @param R 输入：32位
     * @param k 使用哪个key
     * @return  输出：32位
     */
    private int[] f(int[] R,int k){
        //将32位扩展到48位，与子密钥异或
        int[] ER = Expend(R);
        /*System.out.println("\nE(R0):");
        for(int i = 0;i < 48;i ++){
            System.out.print(ER[i]);
        }*/
        int[] k1 = mkey.getSubKey(k);
        /*System.out.println("\nK1:");
        for(int i = 0;i < 48;i ++){
            System.out.print(k1[i]);
        }*/
        int[] xor = Xor(ER,k1,48);
        /*System.out.println("\nXor(ER,k1):");
        for(int i = 0;i < 48;i ++){
            System.out.print(xor[i]);
        }*/
        //S盒代换
        int[] AfterSBox = S.sBoxReplace(xor);
        /*System.out.println("\nAfterSBox:");
        for(int i = 0;i < 32;i ++){
            System.out.print(AfterSBox[i]);
        }*/
        //P置换
        int[] AfterPBox = P.pReplace(AfterSBox);
        /*System.out.println("\nAfterPBox:");
        for(int i = 0;i < 32;i ++){
            System.out.print(AfterPBox[i]);
        }*/
        return AfterPBox;
    }

    /**
     * 将32位扩展成48位
     * @param a
     * @return
     */
    private int[] Expend(int[] a){
        int[] b = new int[48];
        for(int i = 0;i < 4;i ++){
            for(int j = 0;j < 12;j ++){
                b[i * 12 + j] = a[E_TABLE[i][j] - 1];
            }
        }
        return b;
    }

    /**
     * 异或运算
     * @param a
     * @param b
     * @param n
     * @return
     */
    private int[] Xor(int[] a,int[] b,int n){
        int[] c = new int[n];
        for(int i = 0;i < n;i ++){
            c[i] = a[i] ^ b[i];
        }
        return c;
    }

    /**
     * A数组赋值给B数组
     * @param A
     * @param B
     */
    private void copy(int[] A,int[] B,int n){
        for(int i = 0;i < n;i ++){
            B[i] = A[i];
        }
    }
    private void Print(int[] L,int[] R,int a){
        System.out.printf("L%-2d\t\t\t\t\t\t\t\t\tR%-2d\n",a,a);
        for(int i = 0;i < 32;i ++){
            System.out.print(L[i]);
        }
        System.out.print("\t");
        for(int i = 0;i < 32;i ++){
            System.out.print(R[i]);
        }
        System.out.println();
    }
    public static void main(String[] args){
        DexQx mDexQx = new DexQx("2019a10b09c21d37");
        String c = mDexQx.desEncrypt("0123456789ABCDEF");
        System.out.println("密文：\n"+c);
    }
}
