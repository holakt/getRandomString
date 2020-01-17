package com.hola.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author hola
 */
public class CardID {

    /**
     * 身份证号码
     */
    private String idCardNum;

    /**
     * 新身份证18位
     */
    private static final int ID_CARD_NEW = 18;
    /**
     * 身份证第18位校检码
     */
    private final String[] verifyCode = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
    /**
     * 身份证前17位每位加权因子
     */
    private final int[] power = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    public CardID(String idCardNum) {
        this.idCardNum = idCardNum.toUpperCase();
    }

    public String getCardNum() {
        return idCardNum;
    }

    public void setCardNum(String cardNum) {
        this.idCardNum = cardNum;
    }

    /**
     * 前面应该是数字，最后一位可以为‘X’或‘x’
     *
     * @return 字符是否合法
     */
    boolean charVerify(String idCardNum) {
        String code17 = idCardNum.substring(0, idCardNum.length() - 1);
        String code18 = idCardNum.substring(idCardNum.length() - 1).toUpperCase();
        return isNumber(code17) && (isNumber(code18) || code18.equals("X"));
    }


    /**
     * 校验身份证第18位是否正确(只适合18位身份证)
     *
     * @return
     */
    public boolean checkCodeVerify() {
        if (idCardNum.length() != ID_CARD_NEW) {
            return false;
        }
        char[] tmp = idCardNum.toCharArray();
        String checkCode = sumPower(idCardNum);
        String lastNum = tmp[tmp.length - 1] + "";
        if (!checkCode.equalsIgnoreCase(lastNum)) {
            return false;
        }
        return true;
    }

    /**
     * 计算身份证的第十八位校验码
     *
     * @param cardNum
     * @return
     */
    public String sumPower(String cardNum) {
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

    /**
     * 判断字符串是否是一个合法时间
     *
     * @param date
     * @return
     */
    public boolean isDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 位数校验：正确应该为18位。
     *
     * @return 是否为18位身份证号码
     */
    boolean lengthVerify(String idCardNum) {
        return idCardNum.length() == ID_CARD_NEW;
    }

    /**
     * 数字校验：字符串是否为全数字
     *
     * @param in 待验证的字符串
     * @return 是否为全数字
     */
    public boolean isNumber(String in) {
        String numReg = "\\d*";
        Pattern pattern = Pattern.compile(numReg);
        Matcher isNum = pattern.matcher(in);
        return isNum.matches();
    }

    void output() {//信息输出
        if (lengthVerify(this.idCardNum) && charVerify(this.idCardNum) && checkCodeVerify()) {
            int year = Integer.parseInt(idCardNum.substring(6, 10));
            int month = Integer.parseInt(idCardNum.substring(10, 12));
            int day = Integer.parseInt(idCardNum.substring(12, 14));

            int sexNum = idCardNum.charAt(idCardNum.length() - 2);
            //奇数为男性，偶数为女性
            sexNum %= 2;
            GregorianCalendar gc = new GregorianCalendar();
            int nowYear = gc.get(Calendar.YEAR);
            int nowMonth = gc.get(Calendar.MONTH) + 1;
            int nowDay = gc.get(Calendar.DAY_OF_MONTH);
            int age = nowYear - year;
            if (month > nowMonth) {
                age--;
            }
            if (month == nowMonth && day > nowDay) {
                age--;
            }
            if (isDate(year + "-" + month + "-" + day)) {
                System.out.println("生日:" + year + "-" + month + "-" + day);
                System.out.println("年龄:" + age);
                String sex = (sexNum == 0) ? "女" : "男";
                System.out.println("性别:" + sex);
            } else {
                System.out.println("身份证月日错误");
            }
        }
    }
}
