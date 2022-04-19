package com.muesli.music.common.util;

public class KeywordScanner {
    public static String makeSearchKeyword(String keyword) {
        String text = "가깋낗닣딯띻맇밓빟삫싷잏짛찧칳킿팋핗힣";
        String jamoRef = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅇㅈㅉㅊㅋㅌㅍㅎ";

        char[] textArr = text.toCharArray();
        char[] jamoArr = jamoRef.toCharArray();

        char targetChar = keyword.charAt(keyword.length() - 1);
        // System.out.println((int) targetChar);
        char endChar = '0';

        // 입력값의 끝 문자가 ㄱ...ㅎ 인 경우
        if(targetChar <= 12622) {
            for(int i = 0; i < jamoArr.length; i++) {
                if(targetChar == jamoArr[i]) {
                    System.out.println(targetChar + " to the end: " + textArr[i + 1]);
                    endChar = textArr[i + 1];
                }
            }
        } else {
            // 입력값의 끝 문자가 가...힣 인 경우
            for(int i = 0; i < text.length(); i++) {
                if(targetChar >= textArr[i] && targetChar < textArr[i + 1]) {
                    endChar = textArr[i + 1];
                }
            }
        }
        return keyword.substring(0, keyword.length() - 1) + endChar;
    }

}
