/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.controller;

import bupt.pangu.entity.Task;
import bupt.pangu.entity.User;
import bupt.pangu.facade.TaskFacade;
import bupt.pangu.facade.UserFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 46639
 */
@Named
@SessionScoped
public class TaskController implements Serializable {

    @EJB
    private UserFacade userFacade;

    @EJB
    private TaskFacade taskFacade;

    @Inject
    private UserController userController;
    @Inject
    private ProjectDetailController projectDetailController;
    @Inject
    private TeamDetailController teamDetailController;
    private Task task = new Task();
    private User tmpUser = new User();
    private List<Task> finishAndOvertimeTaskList;

    private List<Task> UnfinishTaskList;

    private Task selectedTask;

    public TaskController() {
    }

    public User getTmpUser() {
        return tmpUser;
    }

    public void setTmpUser(User user) {
        tmpUser = user;
    }

    public List<Task> getFinishAndOvertimeTaskList() {
        User user = new User();
        user = userFacade.find(userController.getUser().getUserId());

        List<Task> taskList = taskFacade.findTaskListByUser(user);
        // List<Task> taskList = userFacade.findTaskByUser(user);
        if (taskList == null) {
//             System.out.println("taskList == null");
            return null;
        }
        List<Task> tmpTaskList = new ArrayList<>();
        for (Task task : taskList) {
            if (!"Unfinish".equals(task.getTaskState())) {
                tmpTaskList.add(task);
            }
        }
        if (tmpTaskList.isEmpty()) {
            finishAndOvertimeTaskList = null;
            return finishAndOvertimeTaskList;
        }
        finishAndOvertimeTaskList = tmpTaskList;
//        System.out.println(finishAndOvertimeTaskList.get(0).getTaskId());
        return finishAndOvertimeTaskList;
    }

    public Task getSelected() {
        if (task == null) {
            task = new Task();
        }
        return task;
    }

    public void findSelectedTask(Task task) {
        selectedTask = task;
    }

    public Task getSelectedTask() {
        return selectedTask;
    }

    public List<Task> getUnfinishTaskList() {
        User user = new User();
        user = userFacade.find(userController.getUser().getUserId());
        List<Task> taskList = taskFacade.findTaskListByUser(user);
        if (taskList == null) {
            return null;
        }
        List<Task> tmpTaskList = new ArrayList<>();
        for (Task task : taskList) {
            if (task == null) {
                return null;
            }
            if ("Unfinish".equals(task.getTaskState())) {
                tmpTaskList.add(task);
            }
        }
        if (tmpTaskList.isEmpty()) {
            UnfinishTaskList = null;
            return UnfinishTaskList;
        }
        UnfinishTaskList = tmpTaskList;
        return UnfinishTaskList;
    }

    public void ChangeToUnfinish(Task task) {
        selectedTask = task;
        selectedTask.setTaskState("Unfinish");
        taskFacade.edit(selectedTask);
    }

    public void ChangeToFinish(Task task) {
        selectedTask = task;
        selectedTask.setTaskState("Finish");
        taskFacade.edit(selectedTask);
    }

    public String createTask() {
        if (projectDetailController == null) {
            System.out.println("task shi null");
        }
        task.setPlanplanId(projectDetailController.getSelectedPlan());
        task.setTaskState("Unfinish");
        task.getUserCollection().add(userController.getUser());
        userController.getUser().getTaskCollection().add(task);
        userFacade.edit(userController.getUser());
        taskFacade.create(task);
        task = null;
        return "projectDetail.xhtml";
    }

    public String updateMemTask() {
        selectedTask.getUserCollection().add(tmpUser);
        taskFacade.edit(selectedTask);
        return "projectDetail.xhtml";
    }

    public String deleteTask() {
        selectedTask = getSelectedTask();
        taskFacade.remove(selectedTask);
        return "projectDetail.xhtml";
    }

    public String deleteTask(Task task) {
        taskFacade.remove(task);
        return "projectDetail.xhtml";
    }

    public String removeUserForTask() {
        selectedTask = getSelectedTask();
        taskFacade.removeUserFromTask(selectedTask, tmpUser);
        return "projectDetail.xhtml";
    }

    public void update() {
        taskFacade.edit(selectedTask);
    }

    public List<User> getUserNotIn() {
        int book = 0;
        List<User> users = new ArrayList<>();
        for (User user1 : teamDetailController.getMember()) {
            for (User user2 : selectedTask.getUserCollection()) {
                if (Objects.equals(user1.getUserId(), user2.getUserId())) {
                    book = 1;
                }
            }
            if (1 == book) {
                break;
            } else {
                users.add(user1);
            }
        }
        return users;
    }
}
