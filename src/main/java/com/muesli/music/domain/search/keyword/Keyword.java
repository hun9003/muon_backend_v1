package com.muesli.music.domain.search.keyword;

import com.muesli.music.domain.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "search_keyword")
public class Keyword extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "views")
    private int views;

    @Column(name = "public")
    private int isPublic;

    public Keyword(String keyword) {
        this.keyword = keyword;
        this.isPublic = 1;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
