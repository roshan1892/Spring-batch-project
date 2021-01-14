package com.roshan1dot8.springbatch.batch;

import com.roshan1dot8.springbatch.model.UserEntity;
import com.roshan1dot8.springbatch.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserWriter implements ItemWriter<UserEntity> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void write(List<? extends UserEntity> userEntities) throws Exception {
        userRepository.saveAll(userEntities);
        log.info("Data successfully saved.");
    }
}
