package org.xudc.valid.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author xudc
 */
@Data
public class Person {

    @NotNull
    @Size(min = 4, max = 16, message = "用户名须在4-16位之间")
    private String userName;

    @NotNull
    @Min(value = 18, message = "年龄最小18岁")
    private Integer age;
}
