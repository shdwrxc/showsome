package org.xyc.showsome.util;

import com.google.common.base.Strings;

public class StringUtils {

    public static boolean isEmpty(String str) {
        return Strings.isNullOrEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static int findFirstSplit(String str, String split) {
        if (isEmpty(str)) {
            return 0;
        }
        int i = str.indexOf(split);
        return i > -1 ? i : str.length();
    }
}
