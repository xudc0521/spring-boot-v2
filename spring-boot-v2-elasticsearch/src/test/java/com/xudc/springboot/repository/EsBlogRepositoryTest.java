package com.xudc.springboot.repository;

import com.xudc.springboot.domain.EsBlog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author xudc
 * @date 2018/12/15 15:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBlogRepositoryTest {

    @Autowired
    private EsBlogRepository esBlogRepository;

    @Before
    public void setUp() throws Exception {
        esBlogRepository.deleteAll();
        esBlogRepository.save(new EsBlog("静夜思","作者李白","床前明月光，疑是地上霜。举头望明月，低头思故乡。"));
        esBlogRepository.save(new EsBlog("相思","作者王维","红豆生南国,春来发几枝.愿君多采撷,此物最相思."));
        esBlogRepository.save(new EsBlog("登鹳雀楼","作者王之涣","白日依山尽,黄河入海流.欲穷千里目,更上一层楼."));
    }

    @Test
    public void testFindDistinctEsBlog(){
        Pageable pageable = new PageRequest(0,20);
        String title = "思";
        String summary = "相思";
        String content = "相思";
        Page<EsBlog> esBlogPage =
                esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(title,summary,content,pageable);
        esBlogPage.getContent().stream().forEach(System.out::println);
    }
}