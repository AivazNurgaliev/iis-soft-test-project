package com.iissoft.assignment.app.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private String depCode;
    private String depJob;
    private String description;

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(!(o instanceof EmployeeDto))
            return false;
        EmployeeDto employee = (EmployeeDto) o;

        boolean depCodeEquals = (this.depCode == null && employee.depCode == null)
                || (this.depCode != null && this.depCode.equals(employee.depCode));
        boolean depJobEquals = (this.depJob == null && employee.depJob == null)
                || (this.depJob != null && this.depJob.equals(employee.depJob));
        boolean descriptionEquals = (this.description == null && employee.description == null)
                || (this.description != null && this.description.equals(employee.description));

        return depCodeEquals && depJobEquals && descriptionEquals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(depCode, depJob, description);
    }
}
