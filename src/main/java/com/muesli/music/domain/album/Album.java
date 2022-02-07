package com.muesli.music.domain.album;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.AbstractEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "albums")
public class Album extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String album_code;
    private String name;
    private String release_date;
    private String original_name;
    private String image;
    private String artist_id;
    private String description;

    @Builder
    public Album(Long id, String album_code, String name, String release_date, String original_name, String image, String artist_id, String description) {
        if (id == null) throw new InvalidParamException("Album.id");

        this.id = id;
        this.album_code = album_code;
        this.name = name;
        this.release_date = release_date;
        this.original_name = original_name;
        this.image = image;
        this.artist_id = artist_id;
        this.description = description;
    }
}
