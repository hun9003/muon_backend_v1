package com.muesli.music.common.util;

public class Constant {
    // 구분자
    public static final String SEPARATOR = "（＃）";

    /**
     * 정렬 타입
      */
    public static class Order {
        /**
         * 관련도 순
         */
        public static final String SIMILAR = "similar";

        /**
         * 최근 순
         */
        public static final String NEWEST = "newest";

        /**
         * 인기도 순
         */
        public static final String POPULARITY = "popularity";

        /**
         * 가나다 순
         */
        public static final String ALPHA = "alpha";
    }

    public static class Item {
        public static final String TRACK = "track";
        public static final String ALBUM = "album";
        public static final String ARTIST = "artist";
        public static final String LYRICS = "lyrics";
        public static final String PLAYLIST = "playlist";
    }

}
