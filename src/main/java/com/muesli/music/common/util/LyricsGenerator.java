package com.muesli.music.common.util;

import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.track.lyrics.Lyrics;

import java.util.ArrayList;
import java.util.List;

public class LyricsGenerator {

    public static List<TrackInfo.LyricsDetailInfo> makeLyrics(Lyrics lyrics) {
        var lyricsList = new ArrayList<TrackInfo.LyricsDetailInfo>();
        var timelineList = lyrics.getTimaline() != null ? lyrics.getTimaline().split("/") : new String[0];
        var textList = lyrics.getText() != null ? lyrics.getText().split("/") : new String[0];
        var textOriginalList = lyrics.getTextOriginal() != null ? lyrics.getTextOriginal().split("/") : new String[0];
        var textPronList = lyrics.getTextPron() != null ? lyrics.getTextPron().split("/") : new String[0];

        for (int i = 0; i < timelineList.length; i++) {
            String basic;
            String original;
            String pron;
            try{basic = textList[i]; }catch (ArrayIndexOutOfBoundsException e) {basic = "";}
            try{original = textOriginalList[i]; }catch (ArrayIndexOutOfBoundsException e) {original = "";}
            try{pron = textPronList[i]; }catch (ArrayIndexOutOfBoundsException e) {pron = "";}

            var lyricsInfo = new TrackInfo.LyricsDetailInfo(timelineList[i], basic, original, pron);
            lyricsList.add(lyricsInfo);
        }
        return lyricsList;
    }

}
