package package_eight;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-09-06
 **/

public class ByteArrayToString {
    public static void main(String[] args) {
        byte[] ss = ByteArrayToString.strToByteArray("sunhaoyu");
        String[] sss = new String[]{"1","23"};
        String hh = String.join(",", sss);

   /*     for(byte i : ss) {
            String.join(",", String(i));
        }*/

        System.out.println(hh);
    }
    public static byte[] strToByteArray(String str) {
        if (str == null) {
            return null;
        }
        byte[] byteArray = str.getBytes();
        return byteArray;
    }

    public static String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String str = new String(byteArray);
        return str;
    }
}