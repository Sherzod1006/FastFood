package uz.pdp.entity.enums;

public enum PageEnum {

    ORDER("Orders"),
    PRODUCT("Products"),
    POSITION("Positions"),
    EMPLOYEE("Employees"),
    CATEGORY("Categories"),
    FEEDBACK("Feedbacks");

    private final String name;

    PageEnum(String name) {
        this.name = name;
    }
}
