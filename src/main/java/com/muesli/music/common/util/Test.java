package com.muesli.music.common.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public class Test {
    public static void main(String[] args) {
        Timestamp updateAt = new Timestamp(System.currentTimeMillis());
        System.out.println(updateAt.getTime()+"");
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.MONTH, 3);
        time.setTime(cal.getTime().getTime());
        Long exp = time.getTime();

        System.out.println(updateAt);
        System.out.println(time);
        System.out.println(exp);
    }
}
