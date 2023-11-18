package com.iissoft.assignment.app.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class NaturalKey {
    private String depCode;
    private String depJob;

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(!(o instanceof NaturalKey))
            return false;
        NaturalKey naturalKey = (NaturalKey) o;

        boolean depCodeEquals = (this.depCode == null && naturalKey.depCode == null)
                || (this.depCode != null && this.depCode.equals(naturalKey.depCode));
        boolean depJobEquals = (this.depJob == null && naturalKey.depJob == null)
                || (this.depJob != null && this.depJob.equals(naturalKey.depJob));


        return depCodeEquals && depJobEquals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(depCode, depJob);
    }
}
