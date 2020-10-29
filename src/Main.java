
/**
 * @author 胡光邦
 */
public class Main {
    public static void main(String[] args){
        DexQx mDexQx = new DexQx("2019a10b09c21d37");
        String c = mDexQx.desEncrypt("0123456789ABCDEF");
        System.out.println("密文：\n"+c);
    }
}
