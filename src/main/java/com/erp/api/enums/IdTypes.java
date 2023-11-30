package com.erp.api.enums;

import java.util.LinkedHashMap;

public enum IdTypes {

    PVC("Permanent Voters Card"),
    NIN("National Identity Card"),
    DRIVERS_LICENSE("Driver's Licence"),
    INTERNATIONAL_PASSPORT("International Passport");

    private static LinkedHashMap<String,IdTypes> map = new LinkedHashMap<>();
    private String idType;
    static {
        for(IdTypes idType: values())
        {
            map.put(idType.idType, idType);
        }
    }
    IdTypes(String s) {
        this.idType = s;

    }

    public static String getKey(String s1)
    {
       return map.get(s1).toString();
    }
}
