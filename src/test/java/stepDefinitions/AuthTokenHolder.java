// src/test/java/utils/AuthTokenHolder.java
package stepDefinitions;

public class AuthTokenHolder {
    private static String token;

    public static void setToken(String t) { token = t; }
    public static String getToken() { return token; }
}