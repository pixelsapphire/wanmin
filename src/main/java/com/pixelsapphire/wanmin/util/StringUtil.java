package com.pixelsapphire.wanmin.util;

import org.jetbrains.annotations.NotNull;

public class StringUtil {

    private StringUtil() {
    }

    public static int countCharacters(@NotNull String string, char c) {
        int count = 0;
        for (char ch : string.toCharArray()) if (ch == c) count++;
        return count;
    }
}
