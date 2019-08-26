package com.ysl.netty.common;

import java.util.Arrays;
import java.util.UUID;

public class StringUtil {
    public static boolean isEmpty(String string){
        if(string == null || string.trim().length() == 0){
            return true;
        }
        return false;
    }

    public static String genUUIDHexString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static UUID parseUUIDFromHexString(String hexUUID) throws Exception {
        byte[] data = hexStringToByteArray(hexUUID);
        long msb = 0;
        long lsb = 0;

        for (int i = 0; i < 8; i++)
            msb = (msb << 8) | (data[i] & 0xff);
        for (int i = 8; i < 16; i++)
            lsb = (lsb << 8) | (data[i] & 0xff);

        return new UUID(msb, lsb);
    }

    private static char convertDigit(int value) {

        value &= 0x0f;
        if (value >= 10)
            return ((char) (value - 10 + 'a'));
        else
            return ((char) (value + '0'));

    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String convert(final byte bytes[]) {

        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(convertDigit((int) (bytes[i] >> 4)));
            sb.append(convertDigit((int) (bytes[i] & 0x0f)));
        }
        return (sb.toString());

    }

    public static String convert(final byte bytes[], int pos, int len) {

        StringBuffer sb = new StringBuffer(len * 2);
        for (int i = pos; i < pos + len; i++) {
            sb.append(convertDigit((int) (bytes[i] >> 4)));
            sb.append(convertDigit((int) (bytes[i] & 0x0f)));
        }
        return (sb.toString());

    }

    public static void main(String[] args) {
        byte[] bytes = StringUtil.hexStringToByteArray("d13a99dfc06c2275004a0b856a491bc3");
        System.out.println("1--->"+ Arrays.toString(bytes));

        byte[] bytes1 = new byte[]{45, -113, -20, -34, 44, -107, -29, -115, 3, -106, 33, 26, 87, 77, -8, -108};
        String convert = convert(bytes1);
        System.out.println("2--->"+convert);
    }

}
