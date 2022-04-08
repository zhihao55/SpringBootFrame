package com.zhihao.mapper;

import com.zhihao.pojo.user.Book;
import com.zhihao.pojo.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> queryUserList();

    User queryUserByUsername(String username, String password);

    int save(String username, String password);

    User getUserByUsername(String username);

    int addBooks(Book book);
}
