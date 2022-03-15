package com.muesli.music.common.util;


import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        // 가사 가공 테스트
        String lyrics = "0.0|전주 중~♬#\n" +
                "12.0|오늘도 어제도 똑같은 비디오를 틀고#\n" +
                "19.6|인생이란 게임을 재시작해#\n" +
                "24.0|나의 마음이 받아들일 수 있는 것보다#\n" +
                "30.0|끔찍한 인간이야 라는 말을 들었어#\n" +
                "36.0|(내 옆에 있자 살자)#\n" +
                "40.6|간주 중~♬#\n" +
                "46.4|아직까지도 우리의 향수 냄새를#\n" +
                "50.4|기억한다면 이걸 열어봐#\n" +
                "54.8|내가 준비한 추억의 향기야#\n" +
                "60.4|싫다고? 그래 알겠어#\n" +
                "63.6|하지만 난 너를 좋아해 애정 어린 눈물#\n" +
                "70.8|(내 옆에 있자 살자)#\n" +
                "75.0|간주 중~♬#\n" +
                "82.8|조그만 디딤돌을 밟고 올라가 연 창문#\n" +
                "90.0|악몽이란 하루를 재시작해#\n" +
                "94.0|나의 마음이 받아들이지 못하는 걸#\n" +
                "99.6|알려준 당신들이 무서워#\n" +
                "105.0|(내 옆에 있자 살자)#\n" +
                "110.5|간주 중~♬#\n" +
                "116.8|아직까지도 우리의 향수 냄새를#\n" +
                "120.4|기억한다면 이걸 열어봐#\n" +
                "125.2|내가 준비한 추억의 향기야#\n" +
                "130.4|싫다고? 그래 알겠어#\n" +
                "133.6|하지만 난 너를 좋아해 애정 어린 눈물#\n" +
                "141.2|간주 중~♬#\n" +
                "175.0|아직까지도 우리의 향수 냄새를#\n" +
                "179.0|기억한다면 이걸 열어봐#\n" +
                "184.0|내가 준비한 추억의 향기야#\n" +
                "189.0|싫다고? 그래 알겠어#\n" +
                "192.0|하지만 난 너를 좋아해 (싫어해)#\n" +
                "196.5|눈물 어린 애정#\n" +
                "199.0|내 옆에 있자 살자 내 옆에#";

        System.out.println(makeLyrics(lyrics));

    }

    public static StringBuilder makeLyrics(String lyrics) {
        var lyricsArr = List.of(lyrics.split("\\n"));

        var lyricsArr2 = lyricsArr.stream().map(
                s -> s.substring(s.indexOf("|")+1).replaceAll("#", " ").replaceAll("\\n", "")
        ).collect(Collectors.toList());
        StringBuilder lyricsArr3 = new StringBuilder();
        for (String l : lyricsArr2) {
            lyricsArr3.append(l);
        }

        return lyricsArr3;
    }

}
