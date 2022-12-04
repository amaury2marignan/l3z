package fr.l3z.presentation;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import fr.l3z.repositories.SkillProfileRepository;
import fr.l3z.repositories.SkillRepository;
import fr.l3z.repositories.TaskRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.session.SessionUtils;


@ManagedBean
@ViewScoped
public class DetailTaskPageBean implements Serializable {
	private static final long serialVersionUID = 6978367080470271677L;
	
	@Inject
	private UserRepository userRep;
	@Inject
	private FamilyRepository familyRep;
	@Inject
	private TaskRepository taskRep;
	@Inject 
	private EventRepository eventRep;
	@Inject
	private SkillProfileRepository skillProfileRep;
	@Inject
	private SkillRepository skillRep;
	
	private User user = new User();
	private Family family = new Family();
	private Task task = new Task();
	
	@ManyToMany
	private List<Event> eventList = new ArrayList<Event>();
	
	
	
	
	
	@PostConstruct
	private void init() {
		System.out.println("detailtaskbeaninit");
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String taskIdStr = map.get("taskId");
		System.out.println("id de la tache : "+taskIdStr);
		Long taskId = Long.valueOf(taskIdStr);
		this.task = taskRep.find(taskId);
		initEventList(this.task.getId());
		
	}
	
	
	public void initEventList(Long t) {
		this.eventList=eventRep.findByTask(t);
	}
	
	public String giveDate(Event event) {
		
		return "le "+event.getDate().getDayOfMonth()+" / "+event.getDate().getMonthValue()+" à "+event.getDate().getHour()+":"+event.getDate().getMinute();
	}
	
	public String reservationAction() {
		this.task.setStatus(2);
		this.task.setWhoDidIt(this.user);
		taskRep.update(this.task.getId(),this.task);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				this.task,
				null,
				null,
				null,
				null,
				0,
				user.getUserName()+" a réservé la tâche "+task.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		return "/user/userPage.xhtml";
	}
	
	public String validAction() {
		this.task.setStatus(4);
		taskRep.update(this.task.getId(),this.task);
		this.user.setScore(this.user.getScore()+1);
		this.user.setCoins(this.user.getCoins()+1);
		userRep.update(this.user.getId(), this.user);
		
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				this.task,
				null,
				null,
				null,
				null,
				1,
				user.getUserName()+" a validé la tâche "+task.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		
		
		return "/user/userPage.xhtml";
	}
	
	public String doAction() {
		this.task.setStatus(3);
		this.task.setWhoDidIt(this.user);
		taskRep.update(this.task.getId(),this.task);
		this.user.setScore(this.user.getScore()+this.task.getNbPoints());
		this.user.setCoins(this.user.getCoins()+this.task.getNbPoints());
		userRep.update(this.user.getId(), this.user);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				this.task,
				null,	
				null,
				null,
				null,
				this.task.getNbPoints(),
				user.getUserName()+" a réalisé la tâche "+task.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		if(this.task.getRepeatAfter()!=0) {
			Task planTask = new Task(
					this.family,
					this.task.getName(),
					this.task.getDescription(),
					LocalDate.now().plusDays(this.task.getRepeatAfter()),
					this.task.getRepeatAfter(),
					this.task.getSkillProfileMinimumToDo(),
					this.task.getSkillProfileMinimumToCheck(),
					this.task.getDifficulty()
					);
			planTask.setStatus(1);
			planTask.setNbPoints(this.task.getNbPoints());
			Task savedPlanTask = taskRep.save(planTask);

			Event newEvent2 = new Event(
					this.family,
					this.user,
					LocalDateTime.now(),
					savedPlanTask,
					null,	
					null,
					null,
					null,
					0,
					task.getName()+" a été planifiée automatiquement pour dans "+this.task.getRepeatAfter()+" jours"
					);
			Event savedNewEvent2 = eventRep.save(newEvent2);
		}
		if(this.task.getNextTask()!=null) {
			Task planTask2 = new Task(
					this.family,
					this.task.getNextTask().getName(),
					this.task.getNextTask().getDescription(),
					LocalDate.now(),
					this.task.getNextTask().getRepeatAfter(),
					this.task.getNextTask().getSkillProfileMinimumToDo(),
					this.task.getNextTask().getSkillProfileMinimumToCheck(),
					this.task.getDifficulty()
					);
			planTask2.setStatus(1);
			planTask2.setNbPoints(this.task.getNextTask().getNbPoints());
			Task savedPlanTask = taskRep.save(planTask2);

			Event newEvent3 = new Event(
					this.family,
					this.user,
					LocalDateTime.now(),
					savedPlanTask,
					null,	
					null,
					null,
					null,
					0,
					task.getNextTask().getName()+" a été planifiée automatiquement"
					);
			Event savedNewEvent3 = eventRep.save(newEvent3);
		}
		
		return "/user/userPage.xhtml";
	}
	
	public String cancelAction() {
		this.task.setStatus(5);
		taskRep.update(this.task.getId(),this.task);
		this.task.getWhoDidIt().setScore(this.task.getWhoDidIt().getScore()-this.task.getNbPoints());
		this.task.getWhoDidIt().setCoins(this.task.getWhoDidIt().getCoins()-this.task.getNbPoints());
		userRep.update(this.task.getWhoDidIt().getId(), this.task.getWhoDidIt());
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				this.task,
				null,	
				null,
				null,
				null,
				0,
				user.getUserName()+" a annulé la tâche "+task.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		
		Event newEvent2 = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				this.task,
				null,	
				null,
				null,
				null,
				-this.task.getNbPoints(),
				this.task.getNbPoints()+" points ont été retirés à "+this.task.getWhoDidIt().getUserName()+" pour l'annulation de la tâche "+task.getName()	
				);
		Event savedNewEvent2 = eventRep.save(newEvent2);
		return "/user/userPage.xhtml";
	}
	
	public String modifyAction() {
		
		return "/detail/modifyTaskPage.xhtml";
	}
	
	public String planAction(Task taskD) {
		System.out.println("this.task = "+this.task);
		System.out.println("taskD = "+taskD);
		this.task=taskD;
		return "/detail/planTaskPage.xhtml";
	}
	
	public Boolean cancelParentOK() {
		if((this.task.getStatus()>0)&&(this.task.getStatus()<3)&&(skillProfileRep.isThisSkillIn(skillRep.findByNameAndFamily(this.family.getId(), "Parent").getId(),this.user.getSkillProfile()))) {
			return true;
		} else {
			return false;
		}
	}
	
	public String cancelParent() {
		this.task.setStatus(5);
		taskRep.update(this.task.getId(),this.task);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				null,
				null,
				null,
				null,
				null,
				0,
				user.getUserName()+" a annulé la tache "+this.task.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		return "/user/userPage.xhtml";
	}
	
	
	public boolean isOkReservationButton() {
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),this.task.getSkillProfileMinimumToDo()) && this.task.getStatus()==1 ) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isOkDoButton() {
	
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),this.task.getSkillProfileMinimumToDo()) && ((this.task.getStatus()==1) || ((task.getStatus()==2)&&(task.getWhoDidIt().getId()==user.getId())))) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isOkCheckButton() {
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),this.task.getSkillProfileMinimumToCheck()) && this.task.getStatus()==3 && this.task.getWhoDidIt().getId()!=this.user.getId()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isOkCancelButton() {
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),this.task.getSkillProfileMinimumToCheck()) && this.task.getStatus()==3) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isOkPlanButton() {
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),this.task.getSkillProfileMinimumToCheck()) && this.task.getStatus()==0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isOkModifyButton() {
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),this.task.getSkillProfileMinimumToCheck()) && this.task.getStatus()==0) {
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
	
	public Boolean difNote1(){
		if(this.task.getDifficulty()>0){
			return true;
		} else {
			return false;
		}
		
	}
	
	public Boolean difNote2(){
		if(this.task.getDifficulty()>1){
			return true;
		} else {
			return false;
		}
		
	}
	
	public Boolean difNote3(){
		if(this.task.getDifficulty()>2){
			return true;
		} else {
			return false;
		}
		
	}
	
	public Boolean difNote4(){
		if(this.task.getDifficulty()>3){
			return true;
		} else {
			return false;
		}
		
	}
	public Boolean difNote5(){
		if(this.task.getDifficulty()>4){
			return true;
		} else {
			return false;
		}
		
	}
	
	public Boolean difNote5Inverted(){
		if(this.task.getDifficulty()<5) {
			return true;
		} else {
			return false;
		}
	}
	public Boolean difNote4Inverted(){
		if(this.task.getDifficulty()<4) {
			return true;
		} else {
			return false;
		}
	}
	public Boolean difNote3Inverted(){
		if(this.task.getDifficulty()<3) {
			return true;
		} else {
			return false;
		}
	}
	public Boolean difNote2Inverted(){
		if(this.task.getDifficulty()<2) {
			return true;
		} else {
			return false;
		}
	}
	public Boolean difNote1Inverted(){
		if(this.task.getDifficulty()<1) {
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

	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}


	
	
}
