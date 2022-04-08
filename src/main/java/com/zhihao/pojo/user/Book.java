package com.zhihao.pojo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description:用户书本的实体类
 * @Author: 王憨憨
 * @Date: 2022/3/9 9:22
 * @version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {
    private int id;
    private int userId;
    @NotNull(message = "书名不能为空")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bookName;
    @NotBlank(message = "书名类型不能为空")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bookValue;
}
