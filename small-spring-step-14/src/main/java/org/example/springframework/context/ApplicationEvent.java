package org.example.springframework.context;

import java.awt.*;
import java.util.EventObject;

public abstract class ApplicationEvent extends EventObject {

    public ApplicationEvent(Object resource) {
        super(resource);
    }

}
