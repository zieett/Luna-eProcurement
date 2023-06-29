package com.example.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LegalEntityInfoDTO {

    @JsonProperty("legalEntityCode")
    private String code;
    @JsonProperty("legalEntityName")
    private String name;
    private List<DepartmentInfoDTO> departments;
}
