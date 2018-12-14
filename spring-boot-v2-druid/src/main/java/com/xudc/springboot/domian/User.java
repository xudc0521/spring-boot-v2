package com.xudc.springboot.domian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xudc
 * @date 2018/11/28 15:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private Integer userId;

    private String userName;

    private Integer age;
}
