package com.iissoft.assignment.app.exception;

import com.iissoft.assignment.app.model.NaturalKey;

public class KeyDuplicationException extends RuntimeException {
    public KeyDuplicationException(NaturalKey naturalKey) {
        super("Duplicate key: " + naturalKey.getDepCode()
                + " " + naturalKey.getDepJob() + " in xml");
    }
}
