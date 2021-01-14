package com.roshan1dot8.springbatch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnterpriseSurvey {
    private int year;
    private String industryCode;
    private String industryName;
    private String rmeSize;
    private String variable;
    private String value;
    private String unit;
}
