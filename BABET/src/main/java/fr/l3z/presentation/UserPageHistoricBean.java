package fr.l3z.presentation;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import fr.l3z.models.Family;
import fr.l3z.models.Skill;
import fr.l3z.models.Task;
import fr.l3z.models.Event;
import fr.l3z.models.User;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.SkillRepository;
import fr.l3z.repositories.TaskRepository;
import fr.l3z.repositories.EventRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.session.SessionUtils;


@ManagedBean
@SessionScoped
public class UserPageHistoricBean  {

	
	
	
	@Inject
	private UserRepository userRep;
	@Inject
	private FamilyRepository familyRep;
	@Inject
	private EventRepository eventRep;
	@Inject
	private TaskRepository taskRep;
	@Inject
	private SkillRepository skillRep;
	
	private User user = new User();
	private Family family = new Family();
	private List<Event> eventsList = new ArrayList<Event>();
	private List<User> userList = new ArrayList<User>();
	private List<Skill> skillList = new ArrayList<Skill>();
	private List<Task> runningTaskList = new ArrayList<Task>();
	private List<Task> modelTaskList = new ArrayList<Task>();
	
	
	
	

	@PostConstruct
	private void init() {
		
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		this.setEventsList(eventRep.findByFamily(this.family.getId()));
		this.eventsList.sort(Comparator.comparing(Event::getDate).reversed());
		this.setModelTaskList(taskRep.findByStatus0(this.family.getId()));
		this.setRunningTaskList(taskRep.findTasksToDo(this.family.getId()));
		this.setUserList(userRep.usersByFamily(this.family.getId()));
		this.setSkillList(skillRep.findWithFamily(this.family.getId()));
	}
	
	public String updateEventListWithUser(Long userId) {
		this.setEventsList(eventRep.findByUser(userId));
		this.eventsList.sort(Comparator.comparing(Event::getDate).reversed());
		return "user/userPageHistoric.xhtml?faces-redirect=true";
	}
	
	public String updateEventListWithRunningTask(Long taskId) {
		this.setEventsList(eventRep.findByTask(taskId));
		this.eventsList.sort(Comparator.comparing(Event::getDate).reversed());
		return "user/userPageHistoric.xhtml?faces-redirect=true";
	}
	
	public String updateEventListWithModelTask(Long taskId) {
		this.setEventsList(eventRep.findByTask(taskId));
		this.eventsList.sort(Comparator.comparing(Event::getDate).reversed());
		return "user/userPageHistoric.xhtml?faces-redirect=true";
	}
	
	public String updateTasksListWithSkill(Long skillId) {
		this.setModelTaskList(taskRep.findBySkill0(skillId));
		this.setRunningTaskList(taskRep.findBySkillToDo(skillId));
		return "user/userPageHistoric.xhtml?faces-redirect=true";
	}
	
	public String initLists() {
		this.setEventsList(eventRep.findByFamily(this.family.getId()));
		this.eventsList.sort(Comparator.comparing(Event::getDate).reversed());
		this.setSkillList(skillRep.findWithFamily(this.family.getId()));
		this.setModelTaskList(taskRep.findByStatus0(this.family.getId()));
		this.setRunningTaskList(taskRep.findTasksToDo(this.family.getId()));
		return "user/userPageHistoric.xhtml?faces-redirect=true";
	}
	
	public String giveDay(Event event) {
		if((event.getDate().getYear()==(LocalDate.now().getYear())&&(event.getDate().getDayOfYear()==(LocalDate.now().getDayOfYear())))){
			return "Aujourd'hui, à "+event.getDate().getHour()+":"+event.getDate().getMinute()+", ";
		} else {
			if((event.getDate().getYear()==(LocalDate.now().getYear())&&(event.getDate().getDayOfYear()==(LocalDate.now().getDayOfYear()-1)))){
			return "Hier, à "+event.getDate().getHour()+":"+event.getDate().getMinute()+", ";
			} else {
				if(event.getDate().getYear()==(LocalDate.now().getYear())){
					return "Il y a "+(LocalDate.now().getDayOfYear()-event.getDate().getDayOfYear())+" jours, à "+event.getDate().getHour()+":"+event.getDate().getMinute()+", ";
				} else {
					return giveDate(event);
				}
			}
		}
	}

	public String giveDate(Event event) {
	
		return "le "+event.getDate().getDayOfMonth()+" / "+event.getDate().getMonthValue()+" à "+event.getDate().getHour()+":"+event.getDate().getMinute();
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

	public List<Event> getEventsList() {
		return eventsList;
	}

	public void setEventsList(List<Event> eventsList) {
		this.eventsList = eventsList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<Skill> getSkillList() {
		return skillList;
	}

	public void setSkillList(List<Skill> skillList) {
		this.skillList = skillList;
	}

	public List<Task> getRunningTaskList() {
		return runningTaskList;
	}

	public void setRunningTaskList(List<Task> runningTaskList) {
		this.runningTaskList = runningTaskList;
	}

	public List<Task> getModelTaskList() {
		return modelTaskList;
	}

	public void setModelTaskList(List<Task> modelTaskList) {
		this.modelTaskList = modelTaskList;
	}

	

	

	

	
}
