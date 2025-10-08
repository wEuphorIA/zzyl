package com.zzyl.nursing.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public final class IDCardUtils {

    // 私有构造器，防止实例化
    private IDCardUtils() {
        throw new AssertionError("Cannot instantiate static class IDCardUtils");
    }

    /**
     * 根据身份证号提取个人年龄。
     *
     * @param idCard 身份证号码
     * @return 个人年龄，数值类型
     */
    public static int getAgeByIdCard(String idCard) {
        LocalDate birthDate = extractBirthDate(idCard);
        if (birthDate != null) {
            LocalDate currentDate = LocalDate.now();
            return Period.between(birthDate, currentDate).getYears();
        }
        throw new IllegalArgumentException("Invalid ID card number.");
    }

    /**
     * 根据身份证号提取出生日期。
     *
     * @param idCard 身份证号码
     * @return 出生日期，LocalDateTime类型
     */
    public static LocalDateTime getBirthDateByIdCard(String idCard) {
        String birthDateString = idCard.substring(6, 14); // 身份证号码中的出生日期部分
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
        return LocalDateTime.of(birthDate, LocalTime.MIDNIGHT);
    }

    public static int getGenderFromIdCard(String idCard) {
        if (idCard == null || idCard.length() != 18) {
            throw new IllegalArgumentException("Invalid ID card number");
        }

        String genderCode = idCard.substring(16, 17);
        int gender = Integer.parseInt(genderCode);

        return gender % 2 == 0 ? 0 : 1;
    }

    /**
     * 内部辅助方法，从身份证号中提取出生日期的LocalDate对象。
     *
     * @param idCard 身份证号码
     * @return 出生日期的LocalDate对象，或null（如果身份证号码无效）
     */
    private static LocalDate extractBirthDate(String idCard) {
        if (idCard == null || idCard.length() != 18) {
            return null;
        }
        try {
            String birthStr = idCard.substring(6, 14);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            return LocalDate.parse(birthStr, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(getAgeByIdCard("330103199001011234"));
        System.out.println(getBirthDateByIdCard("330103199001011234"));
    }
}
