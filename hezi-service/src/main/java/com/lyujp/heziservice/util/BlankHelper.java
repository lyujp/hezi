package com.lyujp.heziservice.util;

public class BlankHelper {
    public static Boolean strIsBlank(String str){
        if(str == null) return true;
        if(str.isBlank()) return true;
        return false;
    }
}
