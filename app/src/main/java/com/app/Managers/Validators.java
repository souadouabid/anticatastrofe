package com.app.Managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {

    public static Boolean validateEmail (String email) {
        //retorna el contrari per tema de les funcions al Registro.java
        //retorna false si esta be el mail i retorna true si esta malament el mail
        String emailRegex =  "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(email);
        Boolean bool = !matcher.find();
        System.out.println(bool);
        return bool;
    }

    public static boolean validatePhone (String phone) {
        //retorna el contrari per tema de les funcions al Registro.java
        //retorna false si esta be el numero i retorna true si esta malament el numero
        int num;
        try {
            num = Integer.parseInt(phone);
            if(9 == phone.length()) return false;
            else return true;

        } catch (NumberFormatException e) {
            return true;
        }
    }

    public static boolean validateCP (String CP) {
        //retorna el contrari per tema de les funcions al Registro.java
        //retorna false si esta be el numero i retorna true si esta malament el numero
        int num;
        try {
            num = Integer.parseInt(CP);
            if(9 == CP.length()) return false;
            else return true;

        } catch (NumberFormatException e) {
            return true;
        }
    }
}
