package com.muesli.music.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordScanner {
    public static String makeSearchKeyword(String keyword) {
        String text = "가깋낗닣딯띻맇밓빟삫싷잏짛찧칳킿팋핗힣";
        String jamoRef = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅇㅈㅉㅊㅋㅌㅍㅎ";


        /*
            명타
            명팋
         */
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

    public static String makeSearchKeywordJong(String keyword) {

        final String[] JONG = {"","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};

        char uniVal = keyword.charAt(0);
        char jong = (char)((uniVal-0xAC00)%28);
        char newval = (char) (uniVal-jong);
        return newval+JONG[jong];
    }

    public static class EnToKo { // 코드타입 - 초성, 중성, 종성
        enum CodeType { chosung, jungsung, jongsung }
        static String ignoreChars = "`1234567890-=[]\\;',./~!@#$%^&*()_+{}|:\"<>? ";
        /** * 영어를 한글로... */
        public static String engToKor(String eng) { StringBuffer sb = new StringBuffer();
            int initialCode = 0, medialCode = 0, finalCode = 0;
            int tempMedialCode, tempFinalCode; for (int i = 0; i < eng.length(); i++) { // 숫자특수문자 처리
                if(ignoreChars.indexOf(eng.substring(i, i + 1)) > -1){
                    sb.append(eng.substring(i, i + 1));
                    continue;
                }
                // 초성코드 추출
                initialCode = getCode(CodeType.chosung, eng.substring(i, i + 1));
                i++;
                // 다음문자로
                // 중성코드 추출
                tempMedialCode = getDoubleMedial(i, eng);
                // 두 자로 이루어진 중성코드 추출
                if (tempMedialCode != -1) {
                    medialCode = tempMedialCode; i += 2;
                } else {
                    // 없다면,
                    medialCode = getSingleMedial(i, eng);
                    // 한 자로 이루어진 중성코드 추출
                    i++;
                }
                // 종성코드 추출
                tempFinalCode = getDoubleFinal(i, eng);
                // 두 자로 이루어진 종성코드 추출
                if (tempFinalCode != -1) {
                    finalCode = tempFinalCode;
                    // 그 다음의 중성 문자에 대한 코드를 추출한다.
                    tempMedialCode = getSingleMedial(i + 2, eng);
                    if (tempMedialCode != -1) {
                        // 코드 값이 있을 경우
                        finalCode = getSingleFinal(i, eng);
                        // 종성 코드 값을 저장한다.
                    } else {
                        i++;
                    }
                } else {
                    // 코드 값이 없을 경우 ,
                    tempMedialCode = getSingleMedial(i + 1, eng);
                    // 그 다음의 중성 문자에 대한 코드 추출.
                    if (tempMedialCode != -1) {
                        // 그 다음에 중성 문자가 존재할 경우,
                        finalCode = 0; // 종성 문자는 없음.
                        i--;
                    }
                    else {
                        finalCode = getSingleFinal(i, eng);
                        // 종성 문자 추출
                        if (finalCode == -1){
                            finalCode = 0; i--;
                            // 초성,중성 + 숫자,특수문자,
                            //기호가 나오는 경우 index를 줄임.
                        }
                    }
                }
                // 추출한 초성 문자 코드,
                //중성 문자 코드, 종성 문자 코드를 합한 후 변환하여 스트링버퍼에 넘김
                sb.append((char) (0xAC00 + initialCode + medialCode + finalCode));
            }
            return sb.toString();
        }
        /** * 해당 문자에 따른 코드를 추출한다. * * @param type * 초성 : chosung, 중성 : jungsung, 종성 : jongsung 구분 * @param char 해당 문자 */
        static private int getCode(CodeType type, String c) {
            // 초성
            String init = "rRseEfaqQtTdwWczxvg";
            // 중성
            String[] mid = { "k", "o", "i", "O", "j", "p", "u", "P", "h", "hk", "ho", "hl", "y", "n", "nj", "np", "nl", "b", "m", "ml", "l" };
            // 종성
            String[] fin = { "r", "R", "rt", "s", "sw", "sg", "e", "f", "fr", "fa", "fq", "ft", "fx", "fv", "fg", "a", "q", "qt", "t", "T", "d", "w", "c", "z", "x", "v", "g" };
            switch (type) {
                case chosung:
                    int index = init.indexOf(c);
                    if (index != -1) { return index * 21 * 28; }
                    break;
                case jungsung:
                    for (int i = 0; i < mid.length; i++) {
                        if (mid[i].equals(c)) {
                            return i * 28;
                        }
                    }
                    break;
                case jongsung:
                    for (int i = 0; i < fin.length; i++) {
                        if (fin[i].equals(c)) {
                            return i + 1;
                        }
                    }
                    break;
                default:
                    System.out.println("잘못된 타입 입니다");
            }
            return -1;
        }
        // 한 자로 된 중성값을 리턴한다
        // 인덱스를 벗어낫다면 -1을 리턴
        static private int getSingleMedial(int i, String eng) {
            if ((i + 1) <= eng.length()) {
                return getCode(CodeType.jungsung, eng.substring(i, i + 1));
            } else {
                return -1;
            }
        }
        // 두 자로 된 중성을 체크하고, 있다면 값을 리턴한다.
        // 없으면 리턴값은 -1
        static private int getDoubleMedial(int i, String eng) {
            int result; if ((i + 2) > eng.length()) {
                return -1;
            } else {
                result = getCode(CodeType.jungsung, eng.substring(i, i + 2));
                if (result != -1) { return result; } else { return -1;
                }
            }
        }
        // 한 자로된 종성값을 리턴한다
        // 인덱스를 벗어낫다면 -1을 리턴
        static private int getSingleFinal(int i, String eng) {
            if ((i + 1) <= eng.length()) {
                return getCode(CodeType.jongsung, eng.substring(i, i + 1));
            } else {
                return -1;
            }
        }
        // 두 자로된 종성을 체크하고, 있다면 값을 리턴한다.
        // 없으면 리턴값은 -1
        static private int getDoubleFinal(int i, String eng) {
            if ((i + 2) > eng.length()) {
                return -1;
            } else {
                return getCode(CodeType.jongsung, eng.substring(i, i + 2));
            }
        }
    }

    public static String getEnToKo(String keyword) {
        boolean isEnglish;
        // 컴퓨터를 샀다.


        Pattern p = Pattern.compile("[a-zA-Z0-9]");
        Matcher m = p.matcher(keyword);
//        System.out.println("Found = " + (m.find() ? "True" : "Flase"));
//        if (m.find()) {
//            String str = typoText.substring(m.start(), m.end());
//            System.out.println("Result = " + str);
//        }
        isEnglish = m.find();
        if(isEnglish) {
            return EnToKo.engToKor(keyword);
        } else {
            return keyword;
        }
    }
}

