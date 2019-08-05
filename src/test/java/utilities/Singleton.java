package utilities;

public class Singleton {

    private static String string;

    public static String getInstance() {
        if(string ==null) {
            string = "I have a value now";

        }else {
            System.out.println("already has value");
        }
        return string;
    }

}


