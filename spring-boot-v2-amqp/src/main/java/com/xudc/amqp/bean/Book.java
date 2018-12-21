package com.xudc.amqp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xudc
 * @date 2018/12/19 21:41
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String name;
    private String author;
}
