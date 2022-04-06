package com.muesli.music.domain.genre.genreable;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "genreables")
public class Genreable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "genre_id")
    private Long genreId;

    @Column(name = "genreable_id")
    private Long genreableId;

    @Column(name = "genreable_type")
    private String genreableType;

}
