package com.iissoft.assignment.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Employee {
    private int id;
    private String depCode;
    private String depJob;
    private String description;

}
