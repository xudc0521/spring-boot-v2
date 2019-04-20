package com.xudc.swagger.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xudc
 * @date 2019/4/18 15:07
 */
@Data
@Accessors(chain = true)
public class User {
    private Integer id;
    private String name;
    private Integer age;
}
