package net.aspenk12.exed.util;

public class Util {

    /**
     * Finds the ID in a student email and returns it as an integer.
     */
    public static int getIDFromEmail(String email){
        int atIndex = email.indexOf("@"); //finds the index of the at symbol in the student's email
        String idString = email.substring(atIndex - 5, atIndex);

        try {
            return Integer.parseInt(idString);
        } catch (NumberFormatException e){
            throw new BadDataException("A bad email was in your student dataset, the algorithm was unable to deduce the student id from this Email: " + email);
        }
    }
}
