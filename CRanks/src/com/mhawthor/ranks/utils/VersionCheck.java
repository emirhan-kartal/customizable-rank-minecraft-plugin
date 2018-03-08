package com.mhawthor.ranks.utils;

import java.net.URL;
import java.util.Scanner;

public class VersionCheck {

    private static final String VERSION = "1.2.0";

    public static boolean versionCheck() {
        boolean check = false;
        try {

            final URL l = new URL("http://mhawthor.16mb.com/CRanks/version.txt");
            final Scanner in = new Scanner(l.openStream());
            final String str = in.nextLine();
            check = VERSION.equalsIgnoreCase(str);
            in.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return check;
    }
}
