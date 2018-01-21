package Utils;

import java.util.Random;

public class RandomInt {


    public static int Take() {
        Random r = new Random();
        int i = r.nextInt(100);
        return i;
    }

}
