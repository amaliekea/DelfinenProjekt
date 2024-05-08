package Delfinen;

import java.util.HashSet;
import java.util.Set;

public class SvømmeTid {
    private static final Set<String> svømmeTider = new HashSet<>();

    private String value;

    public SvømmeTid(String value) {
        this.value = value;
    }

    public static void tilføjSvømmeTid(String svømmeTid) {
        svømmeTider.add(svømmeTid);
    }

    @Override
    public String toString() {
        return value;
    }
}