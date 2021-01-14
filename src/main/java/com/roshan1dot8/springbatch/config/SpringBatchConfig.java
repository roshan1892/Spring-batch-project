package com.roshan1dot8.springbatch.config;

import com.roshan1dot8.springbatch.model.EnterpriseSurvey;
import com.roshan1dot8.springbatch.model.EnterpriseSurveyEntity;
import com.roshan1dot8.springbatch.model.UserEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class SpringBatchConfig {

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<UserEntity> itemReader,
                   ItemProcessor<UserEntity, UserEntity> itemProcessor,
                   ItemWriter<UserEntity> itemWriter,
                   ItemReader<EnterpriseSurvey> enterpriseSurveyItemReader,
                   ItemWriter<EnterpriseSurveyEntity> enterpriseSurveyItemWriter,
                   ItemProcessor<EnterpriseSurvey, EnterpriseSurveyEntity> enterpriseSurveyitemProcessor) {

        Step userFileLoadStep = stepBuilderFactory.get("User-File-Load")
                .<UserEntity, UserEntity>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        Step enterpriseSurveyFileLoadStep = stepBuilderFactory.get("Enterprise-Survey-File-Load")
                .<EnterpriseSurvey, EnterpriseSurveyEntity>chunk(100)
                .reader(enterpriseSurveyItemReader)
                .processor(enterpriseSurveyitemProcessor)
                .writer(enterpriseSurveyItemWriter)
                .build();

        return jobBuilderFactory.get("Load-CSV-File-TO-DB")
                .incrementer(new RunIdIncrementer())
                .start(userFileLoadStep)
                .next(enterpriseSurveyFileLoadStep)
                .build();
    }

    @Bean
    public LineMapper<UserEntity> getUserLineMapper() {
        DefaultLineMapper<UserEntity> defaultLineMapper = new DefaultLineMapper<>();

        // map read values to user entity
        BeanWrapperFieldSetMapper<UserEntity> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(UserEntity.class);

        return getLineMapper(defaultLineMapper, new String[]{"id", "name", "department", "salary"}, beanWrapperFieldSetMapper);
    }

    @Bean
    public LineMapper<EnterpriseSurvey> getEnterpriseSurveyLineMapper() {
        DefaultLineMapper<EnterpriseSurvey> defaultLineMapper = new DefaultLineMapper<>();

        // map read values to enterprise survey
        BeanWrapperFieldSetMapper<EnterpriseSurvey> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(EnterpriseSurvey.class);

        return getLineMapper(defaultLineMapper, new String[]{"year", "industryCode", "industryName", "rmeSize", "variable",
                "value", "unit"}, beanWrapperFieldSetMapper);
    }

    @Bean
    public FlatFileItemReader<UserEntity> flatFileUserItemReader() {
        return getItemReader(new FlatFileItemReader<UserEntity>(), "src/main/resources/users.csv", getUserLineMapper());
    }

    @Bean
    public FlatFileItemReader<EnterpriseSurvey> flatFileEnterpriseSurveyItemReader() {
        return getItemReader(new FlatFileItemReader<EnterpriseSurvey>(), "src/main/resources/annual-survey.csv", getEnterpriseSurveyLineMapper());
    }

    private LineMapper getLineMapper(DefaultLineMapper defaultLineMapper, String[] columnNames, BeanWrapperFieldSetMapper beanWrapperFieldSetMapper) {

        DelimitedLineTokenizer lineTokenizer = getLineTokenizer(columnNames);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return defaultLineMapper;
    }

    private FlatFileItemReader getItemReader(FlatFileItemReader flatFileItemReader, String filePath, LineMapper lineMapper) {
        flatFileItemReader.setResource(new FileSystemResource(filePath));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper);
        return flatFileItemReader;
    }

    private DelimitedLineTokenizer getLineTokenizer(String[] names) {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        //Read csv file instructions
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(names);

        return lineTokenizer;
    }

}
