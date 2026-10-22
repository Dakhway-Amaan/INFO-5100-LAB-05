/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.regex.Pattern;

/**
 *
 * @author amaan
 */
public class UserValidator {
    
    public static final int MIN_AGE = 1;
    public static final int MAX_AGE = 120;

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^\\p{L}+(?:[ '\\-]\\p{L}+)*$");

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9](?:[A-Za-z0-9\\-]*[A-Za-z0-9])?"
                    + "(?:\\.[A-Za-z0-9](?:[A-Za-z0-9\\-]*[A-Za-z0-9])?)*\\.[A-Za-z]{2,}$");

    private UserValidator() { }

    public static String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Name is required.";
        }
        String n = name.trim();
        if (n.length() < 2 || n.length() > 50) {
            return "Name must be 2-50 characters.";
        }
        if (!NAME_PATTERN.matcher(n).matches()) {
            return "Use letters, spaces, hyphens or apostrophes only.";
        }
        return null;
    }

    public static String validateAge(String ageText) {
        if (ageText == null || ageText.trim().isEmpty()) {
            return "Age is required.";
        }
        int age;
        try {
            age = Integer.parseInt(ageText.trim());
        } catch (NumberFormatException e) {
            return "Age must be a whole number.";
        }
        if (age < MIN_AGE || age > MAX_AGE) {
            return "Age must be between " + MIN_AGE + " and " + MAX_AGE + ".";
        }
        return null;
    }

    public static String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email is required.";
        }
        String e = email.trim();
        if (e.length() > 254) {
            return "Email is too long.";
        }
        if (e.contains("..")) {
            return "Email cannot contain consecutive dots.";
        }
        if (!EMAIL_PATTERN.matcher(e).matches()) {
            return "Enter a valid email (e.g. name@example.com).";
        }
        return null;
    }
    
}
