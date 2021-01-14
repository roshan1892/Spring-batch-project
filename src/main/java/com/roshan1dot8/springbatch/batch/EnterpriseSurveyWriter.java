package com.roshan1dot8.springbatch.batch;

import com.roshan1dot8.springbatch.model.EnterpriseSurveyEntity;
import com.roshan1dot8.springbatch.repository.EnterpriseSurveyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EnterpriseSurveyWriter implements ItemWriter<EnterpriseSurveyEntity> {

    @Autowired
    private EnterpriseSurveyRepository enterpriseSurveyRepository;

    @Override
    public void write(List<? extends EnterpriseSurveyEntity> enterpriseSurveyEntities) throws Exception {
        enterpriseSurveyRepository.saveAll(enterpriseSurveyEntities);
        log.info("Enterprise survey data successfully saved.");
    }
}
