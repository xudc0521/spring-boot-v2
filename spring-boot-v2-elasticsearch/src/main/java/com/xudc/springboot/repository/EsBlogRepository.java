package com.xudc.springboot.repository;

import com.xudc.springboot.domain.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author xudc
 * @date 2018/12/15 15:37
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {
    /**
     * 分页检索Blog
     * @param title
     * @param summary
     * @param content
     * @param pageable
     * @return
     */
    Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(String title,String summary,String content,Pageable pageable);
}
