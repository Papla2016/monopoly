package src.assets;

import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class CheckedScanner {
    public static String scan(Vector<String> inputString, String helloMessage, String errorMessage) {
        String payback;
        while (true) {
            System.out.println(helloMessage);

            Scanner scanner = new Scanner(System.in);
            payback = scanner.next();
            if (Collections.frequency(inputString,payback) > 0) {
                break;
            } else {
                System.out.println(errorMessage);
            }
        }
        return payback;
    }
}
