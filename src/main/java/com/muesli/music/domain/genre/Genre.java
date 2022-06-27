package com.muesli.music.domain.genre;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "popularity")
    private int popularity;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "orders")
    private int order;

    @Column(name = "views")
    private int views;

    public Genre(Long id, String name, String displayName) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.displayName = displayName;
    }
}
