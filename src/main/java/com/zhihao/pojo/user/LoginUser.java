package com.zhihao.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**0
 * @Description:保存用户登录实体类状态
 * @Author: 王憨憨
 * @Date: 2022/2/25 11:55
 * @version:1.0
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginUser {
    private Integer id;
    private String username;
}
