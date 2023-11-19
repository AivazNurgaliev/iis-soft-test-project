package com.iissoft.assignment.app.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private int id;
    private String depCode;
    private String depJob;
    private String description;

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(!(o instanceof Employee))
            return false;
        Employee employee = (Employee) o;

        boolean idEquals = this.id == employee.id;
        boolean depCodeEquals = (this.depCode == null && employee.depCode == null)
                || (this.depCode != null && this.depCode.equals(employee.depCode));
        boolean depJobEquals = (this.depJob == null && employee.depJob == null)
                || (this.depJob != null && this.depJob.equals(employee.depJob));
        boolean descriptionEquals = (this.description == null && employee.description == null)
                || (this.description != null && this.description.equals(employee.description));

        
        return idEquals && depCodeEquals && depJobEquals && descriptionEquals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, depCode, depJob, description);
    }
}
