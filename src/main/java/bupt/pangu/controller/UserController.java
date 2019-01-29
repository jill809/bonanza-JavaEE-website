package bupt.pangu.controller;

import bupt.pangu.entity.User;
import bupt.pangu.entity.util.JsfUtil;
import bupt.pangu.entity.util.PaginationHelper;
import bupt.pangu.facade.UserFacade;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named("userController")
@SessionScoped
public class UserController implements Serializable {

    private final UIOutput wrongPNBText = null;
    private final UIOutput wrongPSWText = null;
    private User current;
    private DataModel items = null;
    @EJB
    private UserFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Image image;

    private boolean showLogin = true, showRegister = false;

    public boolean isShowLogin() {
        return showLogin;
    }

    public void setShowLogin(boolean showLogin) {
        this.showLogin = showLogin;
        showRegister = false;
    }

    public boolean isShowRegister() {
        return showRegister;
    }

    public void setShowRegister(boolean showRegister) {
        this.showRegister = showRegister;
        showLogin = false;
    }

    public UserController() {
    }

    public User getSelected() {
        if (current == null) {
            current = new User();
        }
        return current;
    }

    private UserFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public StreamedContent getPic() {
        if (current.getUserPic() != null) {
            InputStream bs = new ByteArrayInputStream(current.getUserPic());
            return new DefaultStreamedContent(bs, "image/png");
        }
        return null;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new User();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCreated"));

            current.setUserId(getFacade().findByUserPNB(current).getUserId());
            System.out.println(current.getUserId());
            return "clientArea/admin.xhtml";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return "register_fault.xhtml";
        }
    }

    public boolean isExist() {
        User userNow = getFacade().findByUserPNB(current);
        return userNow.getUserId() != -1;
    }

    public User getUser() {
        User userNow = getFacade().findByUserPNB(current);
        return userNow;
    }

    public Integer getUserId() {
        User userNow = getFacade().findByUserPNB(current);
        return userNow.getUserId();
    }

    public String getUserName() {
        System.out.println(current.getUserName());
        return current.getUserName();
    }

    public String getUserPhoneNum() {
        return current.getUserPhoneNum();
    }

    public Date getUserDate() {
        return current.getUserDate();
    }

    public String getUserGender() {
        return current.getUserGender();
    }

    public void logout() {
        current = new User();
        selectedItemIndex = -1;
//           System.out.println("logout runned");
    }

    public boolean getWrongPNBText() {
        User user_login = getFacade().findByUserPNB(current);
        return user_login == null;
    }

    public boolean getWrongPSWText() {
        User user_login = getFacade().findByUserPNB(current);
        return (user_login != null) && !(current.getUserPassword().equals(user_login.getUserPassword()));
    }

    public String login() {
        User user_login = getFacade().findByUserPNB(current);
        if ((user_login != null && (current.getUserPassword().equals(user_login.getUserPassword())))) {
            current.setUserName(user_login.getUserName());
            current.setUserId(getFacade().findByUserPNB(current).getUserId());
            return "clientArea/admin.xhtml";
        } else {
            return "login_fault.xhtml";
        }
    }

    public String prepareEdit() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public void update() {
        try {

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public String destroy() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public User getUser(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = User.class)
    public static class UserControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserController controller = (UserController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "userController");
            return controller.getUser(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof User) {
                User o = (User) object;
                return getStringKey(o.getUserId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + User.class.getName());
            }
        }

    }

    //转换成日期
    public String date2Date(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    //转换成标准日期输出
    public String date2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        return dateStr;
    }
}
