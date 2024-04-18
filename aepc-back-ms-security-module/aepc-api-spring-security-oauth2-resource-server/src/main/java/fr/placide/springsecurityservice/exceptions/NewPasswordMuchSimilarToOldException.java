package fr.placide.springsecurityservice.exceptions;

public class NewPasswordMuchSimilarToOldException extends Exception {
    public NewPasswordMuchSimilarToOldException(String s) {
        super(s);
    }
}
