package fr.l3z.presentation;


import java.io.Serializable;
import java.util.ArrayList;
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
	
	
	
	
	
	
	
	
	
	public Boolean status0() {
		if (this.task.getStatus()==0) {
			return true;
				} else {
					return false;
				}
	}
	
	public Boolean status1() {
		if (this.task.getStatus()==1) {
			return true;
				} else {
					return false;
				}
	}
	
	public Boolean status2() {
		if (this.task.getStatus()==2) {
			return true;
				} else {
					return false;
				}
	}
	
	public Boolean status3() {
		if (this.task.getStatus()==3) {
			return true;
				} else {
					return false;
				}
	}
	
	public Boolean status4() {
		if (this.task.getStatus()==4) {
			return true;
				} else {
					return false;
				}
	}
	
	public Boolean status5() {
		if (this.task.getStatus()==5) {
			return true;
				} else {
					return false;
				}
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

	
	
	
}
