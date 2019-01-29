package bupt.pangu.controller;

import bupt.pangu.entity.Comment;
import bupt.pangu.entity.Project;
import bupt.pangu.entity.User;
import bupt.pangu.facade.CommentFacade;
import bupt.pangu.facade.ProjectFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class CommentControler implements Serializable {

    private String currentText;

    @EJB
    private CommentFacade commentFacade;
    @EJB
    private ProjectFacade projectFacade;
    @Inject
    private UserController userController;
    @Inject
    private ProjectController projectController;

    public String getCurrentText() {
        return currentText;
    }

    public void setCurrentText(String currentText) {
        this.currentText = currentText;
    }

    public String create() {
        Comment comment = new Comment(currentText);

        User user = userController.getUser();
        Project project = projectController.getProject();

        comment.setUseruserId(userController.getUser());
        comment.setProjectprojectId(projectController.getProject());

        // 确保从当前 project 中能获取到 comment
        project.getCommentCollection().add(comment);
        projectFacade.edit(project);
        commentFacade.create(comment);

        return "comment?faces-redirect=true";
    }
}
