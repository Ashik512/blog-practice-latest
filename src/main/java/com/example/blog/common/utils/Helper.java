package com.example.blog.common.utils;

import java.util.Arrays;
import java.util.Objects;

import static com.example.blog.common.constants.ApplicationConstant.MESSAGE_SEPARATOR;

public class Helper {
    public static String createDynamicCode(String errorCode, String... placeHolders) {
        StringBuilder builder = new StringBuilder(errorCode);

        if(Objects.isNull(placeHolders)) {
            return errorCode;
        }
        Arrays.stream(placeHolders).forEach(placeHolder -> builder.append(MESSAGE_SEPARATOR).append(placeHolder));
        return builder.toString();
    }
}
