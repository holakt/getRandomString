package com.hola.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author hola
 */
public class RandomString {
    public static void main(String[] args) {
        int len = 1000;
        String str = getRandomChar(len, 200);
        System.out.println(str);
    }

    private static String[] lowercase = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
            "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    private static String[] capital = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private static String[] number = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

    private static String[] sign = {
            "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "`", "-", "=",
            "{", "}", "|", ":", "\"", "<", ">", "?",
            "[", "]", "\\", ";", "'", ",", ".", "/"};

    /**
     * 随机生成常见汉字
     */
    public static String getChineseRandomChar() {
        String str = "";
        int highCode;
        int lowCode;

        Random random = new Random();
        //B0 + 0~39(16~55) 一级汉字所占区
        highCode = (176 + Math.abs(random.nextInt(39)));
        //A1 + 0~93 每区有94个汉字
        lowCode = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(highCode)).byteValue();
        b[1] = (Integer.valueOf(lowCode)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 获取随机字符串
     *
     * @param len     字符串长度
     * @param simpChi 出现汉字的概率
     * @return 生成的随机字符串
     */
    public static String getRandomChar(int len, int simpChi) {
        Random random = new Random();
        StringBuilder buffer = new StringBuilder();
        int max = simpChi + lowercase.length + capital.length + number.length + sign.length;
        for (int i = 0; i < len; i++) {
            int temp = random.nextInt(max);
            if (temp < simpChi) {
                buffer.append(getChineseRandomChar());
                continue;
            }
            if (temp >= simpChi && temp < simpChi + lowercase.length) {
                buffer.append(getRandom(TYPE.LETTER));
                continue;
            }
            if (temp >= simpChi + lowercase.length && i < simpChi + lowercase.length + capital.length) {
                buffer.append(getRandom(TYPE.CAPITAL));
                continue;
            }
            if (temp >= simpChi + lowercase.length + capital.length && temp < simpChi + lowercase.length + capital.length + number.length) {
                buffer.append(getRandom(TYPE.NUMBER));
                continue;
            }
            if (temp >= simpChi + lowercase.length + capital.length + number.length && temp < max) {
                buffer.append(getRandom(TYPE.SIGN));
            }
        }
        return buffer.toString();
    }


    /**
     * 获取随机组合码
     *
     * @param type 类型
     * @type <br>小写字符型 LETTER,
     * <br>大写字符型 CAPITAL,
     * <br>数字型 NUMBER,
     * <br>符号型 SIGN
     */
    public static String getRandom(TYPE type) {
        Random random = new Random();
        ArrayList<String> temp = new ArrayList<>();
        StringBuilder code = new StringBuilder();
        if (type == TYPE.LETTER) {
            temp.addAll(Arrays.asList(lowercase));
        } else if (type == TYPE.CAPITAL) {
            temp.addAll(Arrays.asList(capital));
        } else if (type == TYPE.NUMBER) {
            temp.addAll(Arrays.asList(number));
        } else if (type == TYPE.SIGN) {
            temp.addAll(Arrays.asList(sign));
        }
        code.append(temp.get(random.nextInt(temp.size())));

        return code.toString();
    }

    /**
     * 字符类型枚举
     */
    public enum TYPE {
        /**
         * 字符型
         */
        LETTER,
        /**
         * 大写字符型
         */
        CAPITAL,
        /**
         * 数字型
         */
        NUMBER,
        /**
         * 符号型
         */
        SIGN
    }
}


