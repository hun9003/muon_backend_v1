package com.muesli.music.common.util;


import java.util.*;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {

//        Collection<Integer> olds = new ArrayList<>(Arrays.asList(7,2,6,5,3,1));
//        Collection<Integer> news = new ArrayList<>(Arrays.asList(2,3,4,7,8));
//        List<Integer> olds2 = new ArrayList<>(olds);
//        List<Integer> news2 = new ArrayList<>(news);
//
//        olds2.removeAll(news);
//        news2.removeAll(olds);
//
//        System.out.println("삭제되어야 하는것 : " + olds2);
//        System.out.println("생성되어야 하는것 : " + news2);


        // 가사 가공 테스트
//        String lyrics = "0.0|전주 중~♬#\n" +
//                "12.0|오늘도 어제도 똑같은 비디오를 틀고#\n" +
//                "19.6|인생이란 게임을 재시작해#\n" +
//                "24.0|나의 마음이 받아들일 수 있는 것보다#\n" +
//                "30.0|끔찍한 인간이야 라는 말을 들었어#\n" +
//                "36.0|(내 옆에 있자 살자)#\n" +
//                "40.6|간주 중~♬#\n" +
//                "46.4|아직까지도 우리의 향수 냄새를#\n" +
//                "50.4|기억한다면 이걸 열어봐#\n" +
//                "54.8|내가 준비한 추억의 향기야#\n" +
//                "60.4|싫다고? 그래 알겠어#\n" +
//                "63.6|하지만 난 너를 좋아해 애정 어린 눈물#\n" +
//                "70.8|(내 옆에 있자 살자)#\n" +
//                "75.0|간주 중~♬#\n" +
//                "82.8|조그만 디딤돌을 밟고 올라가 연 창문#\n" +
//                "90.0|악몽이란 하루를 재시작해#\n" +
//                "94.0|나의 마음이 받아들이지 못하는 걸#\n" +
//                "99.6|알려준 당신들이 무서워#\n" +
//                "105.0|(내 옆에 있자 살자)#\n" +
//                "110.5|간주 중~♬#\n" +
//                "116.8|아직까지도 우리의 향수 냄새를#\n" +
//                "120.4|기억한다면 이걸 열어봐#\n" +
//                "125.2|내가 준비한 추억의 향기야#\n" +
//                "130.4|싫다고? 그래 알겠어#\n" +
//                "133.6|하지만 난 너를 좋아해 애정 어린 눈물#\n" +
//                "141.2|간주 중~♬#\n" +
//                "175.0|아직까지도 우리의 향수 냄새를#\n" +
//                "179.0|기억한다면 이걸 열어봐#\n" +
//                "184.0|내가 준비한 추억의 향기야#\n" +
//                "189.0|싫다고? 그래 알겠어#\n" +
//                "192.0|하지만 난 너를 좋아해 (싫어해)#\n" +
//                "196.5|눈물 어린 애정#\n" +
//                "199.0|내 옆에 있자 살자 내 옆에#";
//
//        System.out.println(makeLyrics(lyrics));
//        String ssr = "おねがいぎゅっと抱きしめて\\n(오네가이 큣토 다키시메테)/夢見ているの Charming Do！\\n(유메미테 이루노 Charming Do！)/もっとちゃんと近づいて\\n(못토 찬토 치카즈이테)/ねえドキドキしようよ\\n(네에 도키도키시요오요)/간주 중~♬/ジタバタしてるキモチ\\n(지타바타 시테루 키모치)/気づいてほしいだから\\n(키즈이테 호시이 다카라)/勇気のかけら集め\\n(유우키노 카케라 아츠메)/フルスロットルでいこ！\\n(후루스롯토루데 이코！)/想像以上過剰？/乙女のホンキモード/ハートのゲージ上げて/あの雲のかなたまで\n";
//        System.out.println(Arrays.toString(ssr.split("/")));
//
//        var lyricsArr = List.of(ssr.split("/"));
//        var lyricsArr1 = lyricsArr.stream().map(
//                s -> {
//                    if(s.contains("\\n")){
//                        s = s.substring(s.indexOf("\\n")+2);
//                        if(s.contains("(")) {
//                            s = s.substring(s.indexOf("(")+1, s.indexOf(")"));
//                        }
//                    }
//                    return s;
//                }
//        ).collect(Collectors.toList());
//        System.out.println(String.join("/", lyricsArr1));
//        makeAlbumName("チントン
//        シャン (친톤샨) off vocal");
//        getKeyword();

        getInitialSound("명탐정 코난");

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
