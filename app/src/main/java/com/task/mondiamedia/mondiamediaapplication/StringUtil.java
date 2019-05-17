package com.task.mondiamedia.mondiamediaapplication;

import android.text.TextUtils;

/**
 * Created by Summer on 5/17/2019.
 */
public class StringUtil {
    public static String getFullText(String text, String stingResource) {
        if (TextUtils.isEmpty(text))
            text = "not Found";
        return new StringBuilder().append(stingResource).append(" ").append(text).toString();
    }
}
