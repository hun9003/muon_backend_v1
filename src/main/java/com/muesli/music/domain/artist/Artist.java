package com.muesli.music.domain.artist;

import com.google.common.collect.Lists;
import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.artist.bios.Bios;
import com.muesli.music.domain.like.Like;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "original_name")
    private String originalName;
    @Column(name = "english_name")
    private String englishName;
    @Column(name = "image")
    private String image;
    @Column(name = "birthday")
    private String birthday;
    @Column(name = "country")
    private String country;
    @Column(name = "debut")
    private Long debut;
    @Column(name = "agency")
    private String agency;
    @Column(name = "label")
    private String label;
    @Column(name = "views")
    private int views;
    @Column(name = "image_small")
    private String imageSmall;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artist", cascade = CascadeType.PERSIST)
    private List<Album> albumList = Lists.newArrayList();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "artist", cascade = CascadeType.PERSIST)
    private List<Like> likeList = Lists.newArrayList();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "artist", cascade = CascadeType.PERSIST)
    private Bios bios;

    @Builder
    public Artist(Long id, String name, String originalName, String englishName, String image, String birthday, String country, Long debut, String agency, String label, int views, String imageSmall) {
        if (id == null) throw new InvalidParamException("Artist.id");

        this.id = id;
        this.name = name;
        this.originalName = originalName;
        this.englishName = englishName;
        this.image = image;
        this.birthday = birthday;
        this.country = country;
        this.debut = debut;
        this.agency = agency;
        this.label = label;
        this.views = views;
        this.imageSmall = imageSmall;
    }

    @Builder
    public Artist(Long id) {
        if (id == null) throw new InvalidParamException("Artist.id");

        this.id = id;
    }
}
