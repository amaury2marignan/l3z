package fr.l3z.presentation;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.ManyToMany;

import fr.l3z.models.Family;
import fr.l3z.models.Task;
import fr.l3z.models.User;
import fr.l3z.models.Event;
import fr.l3z.repositories.EventRepository;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.TaskRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.session.SessionUtils;


@ManagedBean
@ViewScoped
public class PlanTaskPageBean implements Serializable {
	private static final long serialVersionUID = 6978367080470271677L;
	
	@Inject
	private UserRepository userRep;
	@Inject
	private FamilyRepository familyRep;
	@Inject
	private TaskRepository taskRep;
	@Inject 
	private EventRepository eventRep;
	
	private User user = new User();
	private Family family = new Family();
	private Task task = new Task();
	private int planDays;
	
	
	
	
	@PostConstruct
	private void init() {
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String taskIdStr = map.get("taskId");
		System.out.println("id de la tache : "+taskIdStr);
		if(taskIdStr!=null) {
		Long taskId = Long.valueOf(taskIdStr);
		this.task = taskRep.find(taskId);
		} 
	
	}
	
	
	
	
	public String giveDate(Event event) {
		
	return "le "+event.getDate().getDayOfMonth()+" / "+event.getDate().getMonthValue()+" à "+event.getDate().getHour()+":"+event.getDate().getMinute();
	
	
	}
	
	public boolean isOkReservationButton() {
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),this.task.getSkillProfileMinimumToDo()) ) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isOkDoButton() {
	
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),this.task.getSkillProfileMinimumToDo()) ) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public String boutonPlanNow() {
		Task planTask = new Task(
				this.family,
				this.task.getName(),
				this.task.getDescription(),
				LocalDate.now(),
				this.task.getRepeatAfter(),
				this.task.getSkillProfileMinimumToDo(),
				this.task.getSkillProfileMinimumToCheck()
				);
		planTask.setStatus(1);
		planTask.setNbPoints(this.task.getNbPoints());
		Task savedPlanTask = taskRep.save(planTask);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				savedPlanTask,
				null,		
				null,
				null,
				user.getUserName()+" a planifié la tâche "+task.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		return "/user/userPage.xhtml";
	}
	
	public String boutonPlanReservation() {
		Task planTask = new Task(
				this.family,
				this.task.getName(),
				this.task.getDescription(),
				LocalDate.now(),
				this.task.getRepeatAfter(),
				this.task.getSkillProfileMinimumToDo(),
				this.task.getSkillProfileMinimumToCheck()
				);
		planTask.setStatus(2);
		planTask.setNbPoints(this.task.getNbPoints());
		planTask.setWhoDidIt(this.user);
		Task savedPlanTask = taskRep.save(planTask);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				savedPlanTask,
				null,	
				null,
				null,
				user.getUserName()+" a planifié la tâche "+task.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		Event newEvent2 = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				savedPlanTask,
				null,	
				null,
				null,
				user.getUserName()+" a réservé la tâche "+task.getName()	
				);
		Event savedNewEvent2 = eventRep.save(newEvent2);
		
				
		return "/user/userPage.xhtml";
	}
	
	public String boutonPlanDo() {
		Task planTask = new Task(
				this.family,
				this.task.getName(),
				this.task.getDescription(),
				LocalDate.now(),
				this.task.getRepeatAfter(),
				this.task.getSkillProfileMinimumToDo(),
				this.task.getSkillProfileMinimumToCheck()
				);
		planTask.setStatus(3);
		planTask.setNbPoints(this.task.getNbPoints());
		planTask.setWhoDidIt(this.user);
		Task savedPlanTask = taskRep.save(planTask);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				savedPlanTask,
				null,
				null,
				null,
				user.getUserName()+" a planifié la tâche "+task.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		Event newEvent2 = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				savedPlanTask,
				null,	
				null,
				null,
				user.getUserName()+" a réalisé la tâche "+task.getName()	
				);
		Event savedNewEvent2 = eventRep.save(newEvent2);		
		return "/user/userPage.xhtml";
	}
	
	public String boutonPlanTomorrow() {
		System.out.println(LocalDateTime.now());
		System.out.println(LocalDateTime.now().plusDays(1));
		Task planTask = new Task(
				this.family,
				this.task.getName(),
				this.task.getDescription(),
				LocalDate.now().plusDays(1),
				this.task.getRepeatAfter(),
				this.task.getSkillProfileMinimumToDo(),
				this.task.getSkillProfileMinimumToCheck()
				);
		planTask.setStatus(1);
		planTask.setNbPoints(this.task.getNbPoints());
		Task savedPlanTask = taskRep.save(planTask);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				savedPlanTask,
				null,
				null,
				null,
				user.getUserName()+" a planifié la tâche "+task.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
				
		return "/user/userPage.xhtml";
	}
	
	public String boutonPlanWeek() {
		Task planTask = new Task(
				this.family,
				this.task.getName(),
				this.task.getDescription(),
				LocalDate.now().plusWeeks(1),
				this.task.getRepeatAfter(),
				this.task.getSkillProfileMinimumToDo(),
				this.task.getSkillProfileMinimumToCheck()
				);
		planTask.setStatus(1);
		planTask.setNbPoints(this.task.getNbPoints());
		Task savedPlanTask = taskRep.save(planTask);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				savedPlanTask,
				null,	
				null,
				null,
				user.getUserName()+" a planifié la tâche "+task.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
				
		return "/user/userPage.xhtml";
	}
	
	public String boutonPlanMonth() {
		Task planTask = new Task(
				this.family,
				this.task.getName(),
				this.task.getDescription(),
				LocalDate.now().plusMonths(1),
				this.task.getRepeatAfter(),
				this.task.getSkillProfileMinimumToDo(),
				this.task.getSkillProfileMinimumToCheck()
				);
		planTask.setStatus(1);
		planTask.setNbPoints(this.task.getNbPoints());
		Task savedPlanTask = taskRep.save(planTask);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				savedPlanTask,
				null,	
				null,
				null,
				user.getUserName()+" a planifié la tâche "+task.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
				
		return "/user/userPage.xhtml";
	}
	
	public String boutonPlanDate() {
		
		Task planTask = new Task(
				this.family,
				this.task.getName(),
				this.task.getDescription(),
				LocalDate.now().plusDays(this.planDays),
				this.task.getRepeatAfter(),
				this.task.getSkillProfileMinimumToDo(),
				this.task.getSkillProfileMinimumToCheck()
				);
		planTask.setStatus(1);
		planTask.setNbPoints(this.task.getNbPoints());
		Task savedPlanTask = taskRep.save(planTask);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				savedPlanTask,
				null,	
				null,
				null,
				user.getUserName()+" a planifié la tâche "+task.getName()
				);
		Event savedNewEvent = eventRep.save(newEvent);
				
		return "/user/userPage.xhtml";
	}
	
	
	public Boolean repeat() {
		if(this.task.getRepeatAfter()>1) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean repeatDay() {
		if(this.task.getRepeatAfter()==1) {
			return true;
		} else {
			return false;
		}
	}
	public Boolean next() {
		if (this.task.getNextTask()==null){
			return false;
		} else {
			return true;
		}
	}
	public Boolean skillNote1(int score){
		if(score>=1) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public Boolean skillNote2(int score){
		if(score>=2) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillNote3(int score){
		if(score>=3) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillNote4(int score){
		if(score>=4) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillNote5(int score){
		if(score>=5) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean skillNote1Inverted(int score){
		if(score>=1) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean skillNote2Inverted(int score){
		if(score>=2) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean skillNote3Inverted(int score){
		if(score>=3) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean skillNote4Inverted(int score){
		if(score>=4) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean skillNote5Inverted(int score){
		if(score>=5) {
			return false;
		} else {
			return true;
		}
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public EventRepository getEventRep() {
		return eventRep;
	}

	public void setEventRep(EventRepository eventRep) {
		this.eventRep = eventRep;
	}






	public int getPlanDays() {
		return planDays;
	}




	public void setPlanDays(int planDays) {
		this.planDays = planDays;
	}

	
	
	
}
