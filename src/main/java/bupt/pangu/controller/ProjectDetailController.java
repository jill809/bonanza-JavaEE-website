/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.controller;

import bupt.pangu.entity.Plan;
import bupt.pangu.entity.Task;
import bupt.pangu.entity.util.JsfUtil;
import bupt.pangu.facade.PlanFacade;
import bupt.pangu.facade.TaskFacade;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
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
public class ProjectDetailController implements Serializable {

    @Inject
    private ProjectController projectController;
    @Inject
    private TaskController taskController;
    private String projectName;
    private List<Plan> planList;
    private Plan plan = new Plan();
    @EJB
    private PlanFacade planFacade;
    @EJB
    private TaskFacade taskFacade;
    private Plan selectedPlan;

    public ProjectDetailController() {
    }

//    public List<Task> getTaskList() {
//        List<Task> taskList = taskFacade.findTaskListByPlan(selectedPlan);
//        return taskList;
//    }
    public String getProjectName() {
        return projectController.getProject().getProjectName();
    }

    public List<Plan> getPlanList() {
        List<Plan> planList = planFacade.findPlanListByproject(projectController.getProject());
        if (planList == null) {
            return null;
        }
        for (Plan p : planList) {
            p.setTaskCollection(taskFacade.findTaskListByPlan(p));
        }
        return planList;
    }

    public Plan getSelected() {
        if (plan == null) {
            plan = new Plan();
        }
        return plan;
    }

    public Plan getSelectedPlan() {
        return selectedPlan;
    }

    public void findSelectedPlan(Plan plan) {
        selectedPlan = plan;
    }

    public String createPlan() {
        try {
            plan.setProjectprojectId(projectController.getProject());
            planFacade.create(plan);
            plan.setPlanId(planFacade.findPlanListByname(plan.getPlanName()).getPlanId());
            return "projectDetail.xhtml";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return "team.xhtml";
        }

    }

    public void delPlan(Plan plan) {
        List<Task> tasks = taskFacade.findTaskListByPlan(plan);
        if (tasks == null) {
            planFacade.remove(plan);
            return;
        }
        for (Task task : tasks) {
            if (task == null) {
                return;
            }
            taskController.deleteTask(task);
        }
        planFacade.remove(plan);
    }

    public void updatePlan() {
        System.out.println(selectedPlan.getPlanName());
        planFacade.edit(selectedPlan);
    }
}
