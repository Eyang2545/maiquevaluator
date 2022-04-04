package com.alipay.model;

public enum ContactTypeEnum {
    OFFICE, PERSONAL, BIZ;

    public static ContactTypeEnum toType(String value) {
        return switch (value) {
            case "A" -> ContactTypeEnum.OFFICE;
            case "B" -> ContactTypeEnum.PERSONAL;
            case "C" -> ContactTypeEnum.BIZ;
            default -> throw new IllegalArgumentException("Error");
        };
    }

    public static String decode(ContactTypeEnum typeEnum) {
        return switch (typeEnum) {
            case OFFICE -> "A";
            case PERSONAL -> "B";
            case BIZ -> "C";
        };
    }
}
