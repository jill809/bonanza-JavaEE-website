/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.controller;

import bupt.pangu.entity.User;
import bupt.pangu.facade.UserFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author 46639
 */
@Named("loginController")
@SessionScoped
public class LoginController implements Serializable {

    private User current;
    @EJB
    private UserFacade userFacade;

    public LoginController() {
        current = userFacade.find(1);
    }

}
