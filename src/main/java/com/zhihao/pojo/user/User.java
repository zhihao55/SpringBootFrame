package com.zhihao.pojo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.Serializable;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {
    private int id;
    private String username;
    @JsonIgnore
    private String password;
    @JsonInclude(Include.NON_EMPTY)
    private String userpic;
    @JsonInclude(Include.NON_EMPTY)
    private String email;
    @JsonInclude(Include.NON_EMPTY)
    private String mobile;
    @JsonInclude(Include.NON_EMPTY)
    private String status;
    @JsonInclude(Include.NON_EMPTY)
    private String create_time;
    @JsonIgnore
    private Integer code;
}
