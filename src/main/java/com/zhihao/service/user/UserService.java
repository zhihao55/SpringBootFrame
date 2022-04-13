package com.zhihao.service.user;

import com.zhihao.common.MyException;
import com.zhihao.mapper.UserMapper;
import com.zhihao.pojo.user.Book;
import com.zhihao.pojo.user.User;
import com.zhihao.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User login(String username, String password) {
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        User user = this.userMapper.queryUserByUsername(username, password);
        if (user == null) {
            throw new MyException(200, "账号或者密码不正确", "");
        } else {
            return user;
        }
    }

    public String getToken(String id, String username) {
        HashMap<String, String> claimMap = new HashMap();
        String id1 = (String) claimMap.put("id", id);
        claimMap.put("username", username);
        return TokenUtil.GenerateToken(claimMap);
    }

    public int register(String username, String password) {
        User user = this.userMapper.getUserByUsername(username);
        System.out.println(user);
        if (null != user) {
            throw new MyException(400, "该账号已被注册", "");
        } else {
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            int result = this.userMapper.save(username, password);
            return result;
        }
    }

    /**
     * 用户添加书本
     * @param user
     * @return
     */
    public int addBooks(User user) {
        Book book =user.getBook();
        Integer number=userMapper.addBooks(book);
        return number;
    }

    /**
     * 获取全部好友，默认全部好友
     * @return
     */
    public List<User> findAll(){
        List<User> user= userMapper.queryUserList();
        return user;
    }


}
