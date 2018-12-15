package com.xudc.springboot.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author xudc
 * @date 2018/12/15 16:23
 */
@ToString
@Data
public class BlogDTO {

    private String title;
    private String summary;
    private String content;
    private Integer page;
    private Integer size;

    public Integer getPage() {
        if (page == null) {
            page = 0;
        }
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        if (size == null) {
            size = 20;
        }
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
