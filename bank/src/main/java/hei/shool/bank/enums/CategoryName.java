package hei.shool.bank.enums;

import lombok.Getter;

@Getter
public enum CategoryName {
    EXPENSES_FIXED("frais fixes"),

    EXPENSES_VARIABLE("frais variables"),

    INCOME_REGULAR("revenus réguliers"),

    INCOME_VARIABLE("revenus variables"),

    SAVINGS("épargne"),

    INVESTMENTS("investissements"),

    TRANSFERS("transferts"),

    OTHER("autre");

    private final String label;
    CategoryName(String label) {
        this.label = label.toLowerCase();
    }
}
