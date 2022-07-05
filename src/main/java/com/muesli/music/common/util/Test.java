package com.muesli.music.common.util;


import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {

        URL url = null;
        String readLine = null;
        StringBuilder buffer = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        HttpURLConnection urlConnection = null;

        int connTimeout = 5000;
        int readTimeout = 3000;

        String apiUrl = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=ya29.A0AVA9y1uiB1GySFz5Lmn8RlAl2llgBaMPZNIO3u6e-uZE-bShek4BH1H6jWg7uhr-M9bV9XLpdjUw59YICMriyEBzmgbA7PxPxroBwcQBD71TZvMfWRaJuQPBkmjBH_QLppCqMucO8egfQyi1aeCfiUkEU0fLYUNnWUtBVEFTQVRBU0ZRRl91NjFWeWZMb1lQaVAyX3BEdjhfZEhRQUQxZw0163";	// 각자 상황에 맞는 IP & url 사용

        try
        {
            url = new URL(apiUrl);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(connTimeout);
            urlConnection.setReadTimeout(readTimeout);
            urlConnection.setRequestProperty("Accept", "application/json;");

            buffer = new StringBuilder();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
                while((readLine = bufferedReader.readLine()) != null)
                {
                    buffer.append(readLine).append("\n");
                }
            }
            else
            {
                buffer.append("code : ");
                buffer.append(urlConnection.getResponseCode()).append("\n");
                buffer.append("message : ");
                buffer.append(urlConnection.getResponseMessage()).append("\n");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (bufferedWriter != null) { bufferedWriter.close(); }
                if (bufferedReader != null) { bufferedReader.close(); }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }


//        System.out.println("결과 : " + buffer.toString());
        var result = JSONValue.parse(buffer.toString());
        JSONObject js = (JSONObject) result;
        System.out.println(buffer.toString());
    }

    public static String makeLyrics(String lyrics) {
        var lyricsArr = List.of(lyrics.split("\\n"));
        var lyricsArr1 = lyricsArr.stream().map(
                s -> s.substring(0, s.indexOf("|"))
        ).collect(Collectors.toList());
        var lyricsArr2 = lyricsArr.stream().map(
                s -> s.substring(s.indexOf("|")+1).replaceAll("#", " ").replaceAll("\\n", "")
        ).collect(Collectors.toList());

        return String.join("/", lyricsArr2);
    }

    public static String makeAlbumName(String name) {
        String frontName = name.substring(0, name.indexOf("("));
        String koreaName = name.substring(name.indexOf("(")+1, name.indexOf(")"));
        String otherName = name.substring(name.indexOf(")")+1);

        System.out.println(koreaName.trim() + " (" + frontName.trim() + ") " + otherName.trim());
        return null;
    }

    public static void getKeyword(){

        String keyword = "명탐정 코난";

        for(int i=0; i < keyword.length();i++) {

            String k = keyword.substring(i, i+1);
            System.out.println(k + "//=" + getInitialSound(k));
        }
    }

    /**
     * 초성 추출, 중성, 종성 구하는 방법 추가
     * @param text
     * @return
     */
    public static String getInitialSound(String text) {

        // 초성 19자
        final String[] initialChs = {
                "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ",
                "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ",
                "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ",
                "ㅋ", "ㅌ", "ㅍ", "ㅎ"
        };

        // 중성 21자
        final String[] medialChs = {
                "ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ",
                "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅘ",
                "ㅙ", "ㅚ", "ㅛ", "ㅜ", "ㅝ",
                "ㅞ", "ㅟ", "ㅠ", "ㅡ", "ㅢ",
                "ㅣ"
        };

        // 종성 없는 경우 포함하여 28자
        final String[] finalChs = {
                " ",  "ㄱ", "ㄲ", "ㄳ", "ㄴ",
                "ㄵ", "ㄶ", "ㄷ", "ㄹ", "ㄺ",
                "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ",
                "ㅀ", "ㅁ", "ㅂ", "ㅄ", "ㅅ",
                "ㅆ", "ㅇ", "ㅈ", "ㅊ", "ㅋ",
                "ㅌ", "ㅍ", "ㅎ"
        };

        // 19: 초성
        // 21: 중성
        // 28: 종성
        if(text.length() > 0) {
            char chName = text.charAt(0);
            if(chName >= 0xAC00 && chName <= 0xD7A3) {  // 0xAC00(가) ~ 0xD7A3(힣)

                int uniVal = chName - 0xAC00;
                int initialCh = ((uniVal) / (21 * 28)); // 초성 index
                System.out.println(initialChs[initialCh]);

                // 중성
                int medialCh = ((uniVal % (28 * 21)) / 28);
                System.out.println(medialChs[medialCh]);

                // 종성
                int finalCh = ((uniVal % 28));
                System.out.println(finalChs[finalCh]);

                return initialChs[initialCh];
            } else {
                return ""+chName;
            }
        }

        return "";
    }

}
