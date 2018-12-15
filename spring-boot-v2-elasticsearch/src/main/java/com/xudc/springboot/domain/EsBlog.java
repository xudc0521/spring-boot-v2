package com.xudc.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * @author xudc
 * @date 2018/12/15 15:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "blog",type = "blog")
public class EsBlog implements Serializable {
    private static final long serialVersionUID = -4308352400589160831L;
    @Id
    private String id;
    private String title;
    private String summary;
    private String content;

    public EsBlog(String title, String summary, String content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }
}
