package com.muesli.music.domain.search.history;

import com.muesli.music.domain.AbstractEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "search_history")
public class History extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "ip")
    private String ip;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "result_count")
    private int resultCount;

    @Builder
    public History(String keyword, String ip, Long userId, int resultCount) {
        this.keyword = keyword;
        this.ip = ip;
        this.userId = userId;
        this.resultCount = resultCount;
    }

}
