package com.william.boss.service.impl;

import com.william.boss.dto.UserDTO;
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
    Map<Integer, UserDTO> users = new ConcurrentHashMap<>(10);

    @Override
    public List<UserDTO> getAllUsers() {
        log.info(users.values().toString());
        return new ArrayList<>(users.values());
    }

    @Override
    public void addOrEdit(UserDTO userDTO) {
        Integer id = userDTO.getUserId();
        if (id == null) {
            id = generateId();
            userDTO.setUserId(id);
        }
        users.put(id, userDTO);
        log.info(userDTO.toString());
    }

    @Override
    public UserDTO detail(Integer id) {
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
