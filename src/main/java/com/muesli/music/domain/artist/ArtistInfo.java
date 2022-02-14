package com.muesli.music.domain.artist;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.like.Like;
import com.muesli.music.domain.like.LikeInfo;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import java.time.ZonedDateTime;
import java.util.List;

public class ArtistInfo {

    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final String name;
        private final String originalName;
        private final String englishName;
        private final String image;
        private final String birthday;
        private final String country;
        private final Long debut;
        private final String agency;
        private final String label;
        private final int views;
        private final String imageSmall;
        private final List<AlbumInfo.AlbumBasicInfo> albumList;
        private final LikeInfo likeInfo;

        public Main(Artist artist, List<AlbumInfo.AlbumBasicInfo> albumList, LikeInfo likeInfo) {
            this.id = artist.getId();
            this.name = artist.getName();
            this.originalName = artist.getOriginalName();
            this.englishName = artist.getEnglishName();
            this.image = artist.getImage();
            this.birthday = artist.getBirthday();
            this.country = artist.getCountry();
            this.debut = artist.getDebut();
            this.agency = artist.getAgency();
            this.label = artist.getLabel();
            this.views = artist.getViews();
            this.imageSmall = artist.getImageSmall();
            this.albumList = albumList;
            this.likeInfo = likeInfo;
        }
    }

}
