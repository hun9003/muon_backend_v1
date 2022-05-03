package com.muesli.music.domain.channel;

import com.muesli.music.domain.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "channels")
public class Channel extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "slug")
    private String slug;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "auto_update")
    private String autoUpdate;

    @Column(name = "layout")
    private String layout;

    @Column(name = "hide_title")
    private String hideTitle;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "seo_title")
    private String seoTitle;

    @Column(name = "seo_description")
    private String seoDescription;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "public")
    private Long isPublic;

}
