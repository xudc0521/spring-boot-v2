package com.xudc.springboot.service.impl;

import com.xudc.springboot.domain.EsBlog;
import com.xudc.springboot.dto.BlogDTO;
import com.xudc.springboot.repository.EsBlogRepository;
import com.xudc.springboot.service.EsBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author xudc
 * @date 2018/12/15 16:30
 */
@Service("esBlogService")
public class EsBlogServiceImpl implements EsBlogService {
    @Autowired
    private EsBlogRepository esBlogRepository;
    @Override
    public Page<EsBlog> listPage(BlogDTO dto) {
        Pageable pageable = new PageRequest(dto.getPage(),dto.getSize());
        String title = dto.getTitle();
        String summary = dto.getSummary();
        String content = dto.getContent();
        return esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(title,summary,content,pageable);
    }
}
