/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.management;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author jill
 */
@FacesValidator("jill.phoneValidater")
public class checkoutFormBean implements Validator {

    private static final String PHONEPATTERN = "^[\\d]{11}|[\\d]{13}$";

    private final Pattern pattern;
    private Matcher matcher;

    public checkoutFormBean() {
        pattern = Pattern.compile(PHONEPATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
        Object value) throws ValidatorException {

        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {

            FacesMessage msg
                = new FacesMessage("Phone validation failed.",
                    "Invalid Phone format.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}
