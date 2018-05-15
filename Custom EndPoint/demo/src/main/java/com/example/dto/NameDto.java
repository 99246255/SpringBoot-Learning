package com.example.dto;

import org.hibernate.validator.constraints.NotBlank;

public class NameDto {

    @NotBlank(message = "姓名不可为空")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
