package com.roshan1dot8.springbatch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "enterprise_survey")
public class EnterpriseSurveyEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private int year;
    private String industryCode;
    private String industryName;
    private String rmeSize;
    private String variable;
    private String value;
    private String unit;
}
