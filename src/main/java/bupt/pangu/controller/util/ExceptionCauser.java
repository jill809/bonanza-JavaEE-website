package bupt.pangu.controller.util;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ExceptionCauser {
    public Object getCause() {
        throw new RuntimeException("ExceptionCauser");
    }
}
