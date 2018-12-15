package com.xudc.springboot.service;

import com.xudc.springboot.domain.EsBlog;
import com.xudc.springboot.dto.BlogDTO;
import org.springframework.data.domain.Page;

/**
 * @author xudc
 * @date 2018/12/15 16:28
 */
public interface EsBlogService {

    Page<EsBlog> listPage(BlogDTO dto);
}
