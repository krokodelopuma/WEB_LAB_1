import java.util.HashMap;
public class parsjsonstr {
    public static HashMap<String, String> parse(String strj) {
        HashMap<String, String> parsstr = new HashMap<String, String>();
        strj = strj.replaceAll("[{}'\"]", "");
        for (String pair : strj.split(",")) {
            String[] strj2 = pair.split(":");
            if (strj2.length > 1) {
                parsstr.put(strj2[0], strj2[1]);
            } else {
                parsstr.put(strj2[0], "");
            }
        }

        return parsstr;
    }
}
