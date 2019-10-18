package com.hola.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CardID {

    private static String cardNum;//身份证号码

    private static final int IDCARD_NEW = 18; // 新身份证18位
    //身份证第18位校检码
    private String[] verifyCode = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
    //身份证前17位每位加权因子
    private static int[] power = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        CardID.cardNum = cardNum;
    }

    CardID(String cardNum) {
        this.cardNum = cardNum.toUpperCase();
    }

    /**
     * 位数校验：正确应该为18位。
     * @return
     */
    boolean lenghtVerify() {
        return cardNum.length() == IDCARD_NEW;

    }

    /**
     * 数字校验：字符串是否为全数字
     * @param in 字符串
     * @return 是否为全数字
     */
    public boolean isNumber(String in) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(in);
        return isNum.matches();
    }

    /**
     * 检查是否为正确的十八位身份证号
     */
    boolean charVerify() {//前面17个应该是数字，最后一位可以为‘X’或‘x’
        if (this.lenghtVerify()) {
            String code17 = cardNum.substring(0, 17);
            String code18 = cardNum.toUpperCase().substring(17, IDCARD_NEW);
            return isNumber(code17) && (isNumber(code18) || code18.equals("X"));
        } else return false;
    }


    /**
     * 校验身份证第18位是否正确(只适合18位身份证)
     */
    public boolean checkCodeVerify() {
        if (cardNum.length() != IDCARD_NEW) {
            return false;
        }
        char[] tmp = cardNum.toCharArray();
        String checkCode = sumPower(cardNum);
        String lastNum = String.valueOf(tmp[tmp.length - 1]);
        return checkCode.equalsIgnoreCase(lastNum);
    }

    /**
     * 计算身份证的第十八位校验码
     * @param cardNum 身份证号
     * @return 第十八位校验码
     */
    public  String sumPower(String cardNum) {
        int result = 0;

        int[] cardIdArray = new int[cardNum.length() - 1];
        for (int i = 0; i < cardNum.length() - 1; i++) {
            cardIdArray[i] = Integer.parseInt(cardNum.toCharArray()[i] + "");
        }

        for (int i = 0; i < power.length; i++) {
            result += power[i] * cardIdArray[i];
        }
        return verifyCode[(result % 11)];
    }

    void output() {//信息输出
        if (checkCodeVerify()) {
            int year = Integer.parseInt(cardNum.substring(6, 10));
            int month = Integer.parseInt(cardNum.substring(10, 12));
            int day = Integer.parseInt(cardNum.substring(12, 14));

            int sexNum = cardNum.charAt(cardNum.length() - 2);
            //奇数为男性，偶数为女性
            sexNum %= 2;
            GregorianCalendar gc = new GregorianCalendar();
            int nowYear = gc.get(Calendar.YEAR);
            int nowMonth = gc.get(Calendar.MONTH)+1;
            int nowDay = gc.get(Calendar.DAY_OF_MONTH);
            int age = nowYear - year;
            if (month > nowMonth) age--;
            if (month == nowMonth && day > nowDay) age--;
            if (isDate(year + "-" + month + "-" + day)) {
                System.out.println("生日:" + year + "-" + month + "-" + day);
                System.out.println("年龄:" + age);
                String sex = (sexNum == 0) ? "女" : "男";
                System.out.println("性别:" + sex);
            } else System.out.println("身份证日期错误");
        }
    }

    /**
     * 检验日期是否规范
     * @param date 日期字符串
     */
    public boolean isDate(String date) {
        Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-/\\s]?((((0?[13578])|(1[02]))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-/\\s]?((((0?[13578])|(1[02]))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
        Matcher m = pattern.matcher(date);
        return m.matches();
    }
}
