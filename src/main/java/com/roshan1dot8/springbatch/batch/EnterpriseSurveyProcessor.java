package com.roshan1dot8.springbatch.batch;

import com.roshan1dot8.springbatch.model.EnterpriseSurvey;
import com.roshan1dot8.springbatch.model.EnterpriseSurveyEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EnterpriseSurveyProcessor implements ItemProcessor<EnterpriseSurvey, EnterpriseSurveyEntity> {

    @Override
    public EnterpriseSurveyEntity process(EnterpriseSurvey enterpriseSurvey) throws Exception {
        return EnterpriseSurveyEntity.builder()
                .industryCode(enterpriseSurvey.getIndustryCode())
                .industryName(enterpriseSurvey.getIndustryName())
                .rmeSize(enterpriseSurvey.getRmeSize())
                .unit(enterpriseSurvey.getUnit())
                .value(enterpriseSurvey.getValue())
                .variable(enterpriseSurvey.getVariable())
                .year(enterpriseSurvey.getYear())
                .build();
    }
}
