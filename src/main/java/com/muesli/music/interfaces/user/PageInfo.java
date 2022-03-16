package com.muesli.music.interfaces.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public class PageInfo {
    private int startNum;
    private int endNum;
    private int size;
    private int count;

    public PageInfo(Pageable pageable, int count) {
        this.startNum = (pageable.getPageNumber() - 1) * pageable.getPageSize();
        this.endNum = pageable.getPageNumber() * pageable.getPageSize();
        this.size = pageable.getPageSize();
        this.count = count;

        if (this.endNum > count) this.endNum = count;
        if (this.startNum > count) {
            this.startNum = 0;
            this.endNum = 0;
        }
    }
}
