package com.william.boss.service.impl;

import com.william.boss.orm.model.UserInfo;
import com.william.boss.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author john
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    AtomicInteger index = new AtomicInteger(0);
    Map<Integer, UserInfo> users = new ConcurrentHashMap<>(10);

    @Override
    public List<UserInfo> getAllUsers() {
        log.info(users.values().toString());
        return new ArrayList<>(users.values());
    }

    @Override
    public void addOrEdit(UserInfo userInfo) {
        Integer id = userInfo.getUserId();
        if (id == null) {
            id = generateId();
            userInfo.setUserId(id);
        }
        users.put(id, userInfo);
        log.info(userInfo.toString());
    }

    @Override
    public UserInfo detail(Integer id) {
        return users.get(id);
    }

    @Override
    public void delete(Integer id) {
        users.remove(id);
        log.info(String.valueOf(id));
    }

    private Integer generateId() {
        return index.incrementAndGet();
    }
}
