/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.controller;

import bupt.pangu.facade.UserFacade;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

@Named
@SessionScoped
public class FileUploadController implements Serializable {

    @Inject
    private UserController userController;
    private UploadedFile file;
    private byte[] bt;

    @EJB
    private UserFacade ejbFacade;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void handleFileUpload() throws IOException {
        System.out.println("hello");
        if (file == null) {
            System.out.println("null");
            return;
        }
        try {
            InputStream stream;
            stream = file.getInputstream();
            bt = IOUtils.toByteArray(stream);
            userController.getSelected().setUserPic(bt);
            ejbFacade.edit(userController.getSelected());

        } catch (IOException ex) {
        }
    }

}
