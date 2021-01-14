package com.roshan1dot8.springbatch.batch;

import com.roshan1dot8.springbatch.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class UserProcessor implements ItemProcessor<UserEntity, UserEntity> {

    private Map<String, String> departmentMap = new HashMap<>();

    public UserProcessor() {
        departmentMap.put("001", "Technology");
        departmentMap.put("002", "Operations");
        departmentMap.put("003", "Accounts");
    }

    @Override
    public UserEntity process(UserEntity userEntity) throws Exception {
        userEntity.setDepartment(departmentMap.get(userEntity.getDepartment()));
        log.info("Department form csv {} converted to {}.", userEntity.getDepartment(), departmentMap.get(userEntity.getDepartment()));
        return userEntity;
    }
}
