package com.muesli.music.common.util;

import java.util.UUID;

public class UUIDGenerator {
    public static String makeUUID() {
        return UUID.randomUUID().toString();
    }
}
