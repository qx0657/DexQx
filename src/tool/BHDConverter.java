package tool;

/**
 * 进制转换
 * @author 胡光邦
 */
public class BHDConverter {
    /**
     * 16进制字符串转2进制字符串（每位16进制转换为4位）
     * @param hexString
     * @return
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        String b = "", temp;
        for (int i = 0; i < hexString.length(); i++) {
            temp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(
                    hexString.substring(i, i + 1), 16));
            b += temp.substring(temp.length() - 4);
        }
        return b;
    }

    /**
     * 2进制字符串(64位)转16进制字符串（16位）（每4位2进制转换为1位16进制）
     * @param a 输入：64位2进制
     * @return  输出：16位16进制
     */
    public static String binaryString2hexString(int[] a){
        StringBuffer sb = new StringBuffer();
        StringBuffer result = new StringBuffer();
        for(int i = 0;i < 64;i ++){
            sb.append(a[i]);
        }
        for(int i = 0;i < 16;i ++){
            String substr = sb.toString().substring(i * 4,i * 4 + 4);
            result.append(Integer.toString (Integer.parseInt (substr, 2), 16));
        }
        return result.toString().toUpperCase();
    }

    /**
     * 十进制转2进制字符串（用于S盒代换，返回4位2进制）
     * @param a 输入：十进制数（0~15）
     * @return  输出：4位2进制
     */
    public static String integer2binaryString(int a){
        String temp = "0000" + Integer.toBinaryString(a);
        return temp.substring(temp.length() - 4);
    }
}
