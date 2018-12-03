package package_one;
import com.sun.tools.javac.util.Convert;
import org.spongycastle.util.encoders.Hex;

/**
 * @program: java-test-1
 * @description: Kad
 * @author: shydesky@gmail.com
 * @create: 2018-07-16
 **/

public class Kad {
    public static int distance(byte[] ownerId, byte[] targetId) {
        byte[] h1 = targetId;
        byte[] h2 = ownerId;

        byte[] hash = new byte[Math.min(h1.length, h2.length)];

        for (int i = 0; i < hash.length; i++) {
            hash[i] = (byte) (((int) h1[i]) ^ ((int) h2[i]));
        }

        int d = 256;

        for (byte b : hash) {
            if (b == 0) {
                d -= 8;
            } else {
                int count = 0;
                for (int i = 7; i >= 0; i--) {
                    boolean a = (b & (1 << i)) == 0;
                    if (a) {
                        count++;
                    } else {
                        break;
                    }
                }

                d -= count;

                break;
            }
        }
        return d;
    }

    public static byte[] mockTargetIdWithDistance(byte[] nodeId, int distance) {
        byte[] targetId = new byte[nodeId.length];

        for(int i=0; i<nodeId.length; i++){
            targetId[i] = nodeId[i];
        }

        int m = (distance - 1) / 8;
        int n = (distance - 1) % 8;
        int start = nodeId.length / 2 - 1;
        start = start - m;
        byte b = targetId[start];
        b ^= 1 << (n);
        targetId[start] = b;
        if (distance(nodeId, targetId) == distance) {
            return targetId;
        }
        return targetId;
    }
    /*public static String calcNodeIdWithDistanceN(String hexString, int distance){
        int len = hexString.length() / 2;
        int m = (distance-1) / 8;
        int n = (distance-1) % 8;
        if(distance == 0){
            return hexString;
        }

        String modifyHexString = hexString.substring(len - 2 * m - 2, len - 2 * m);

        int ss = Integer.valueOf(modifyHexString, 16);
        ss ^= (1<<(n));
        String ss_;
        if(ss <= 0xf){
            ss_ = "0" + Integer.toHexString(ss);
        }else{
            ss_ = Integer.toHexString(ss);
        }
        return hexString.substring(0,len - 2 * m - 2) + ss_ + hexString.substring(len - 2 * m,hexString.length());
    }*/

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static void main(String args[]){
/*        String hexNodeId = "d466420fa2c92b4d182fbdf835d254922c41f949442c0cb488cb709e746cf282f735f3f6e50e30e8b0607f2143b2c8124b46066c5dc59b6ccc8af6ec20fa450b";
        byte[] a = hexStringToByteArray(hexNodeId);
        System.out.println(hexStringToByteArray(hexNodeId)=="20125fb005be395917e4f18fc3f3b514ee5692d734909ccab2565106d98aaaea5fd23b541ccaff7460d45c17abfcd2b3bea8181c20b0abe215dcd26afae19450".getBytes());

        byte[] ownerId = Hex.decode(hexNodeId);
        byte[] targetId = "d466420fa2c92b4d182fbdf835d254922c41f949442c0cb488cb709e746cf282f735f3f6e50e30e8b0607f2143b2c8124b46066c5dc59b6ccc8af6ec20fa450b".getBytes();
        System.out.println(distance(ownerId, targetId));
        System.out.println(distance(targetId, ownerId));*/

        String hexNodeId = "d466420fa2c92b4d182fbdf835d25492d466420fa2c92b4d182fbdf835d25492";
        mockTargetIdWithDistance(hexNodeId.getBytes(), 1);
        for(int i=1;i<=1;i++){
            //System.out.println("距离为"+ i +"的节点：" + calcNodeIdWithDistanceN(hexNodeId,i));
            //targetId = Hex.decode(calcNodeIdWithDistanceN(hexNodeId,i));
            //System.out.println("距离为"+ i +"的节点：" + calcNodeIdWithDistanceN(hexNodeId,i));
            //System.out.println(i==distance(ownerId, targetId));
            //System.out.println(distance(ownerId, targetId));

        }
    }

}