package com.xudc.router.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author xudc
 * @date 2019/3/25 15:49
 */
@Document(collection = "user")
@Data
@Accessors(chain = true)
public class User {
    @Id
    private String id;
    private String name;
    private int age;
}
