package fr.l3z.presentation;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import fr.l3z.models.Event;
import fr.l3z.models.Family;
import fr.l3z.models.Task;
import fr.l3z.models.User;
import fr.l3z.repositories.EventRepository;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.TaskRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.session.SessionUtils;


@ManagedBean
@ViewScoped
public class UserPageBean  implements Serializable {
	
	
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
	private List<Task> tasksList = new ArrayList<Task>();
	private Task task = new Task();
	private int pointsOfMonth;
	
	
	

	@PostConstruct
	private void init() {
		
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		this.setTasksList(taskRep.findTasksToDo(this.family.getId()));
		tasksList.sort(Comparator.comparing(Task::getNextDate));
		this.pointsOfMonth=eventRep.pointsOfMonth(this.user.getId());
	}
	
	public String picString() {
		return "../resources/idPics/"+this.user.getColor()+"/id"+this.user.getIdPicNumber()+".png";
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

	

	public boolean isOkReservationButton(Task task) {
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),task.getSkillProfileMinimumToDo()) && task.getStatus()==1 ) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public boolean isOkDoButton(Task task) {
	
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),task.getSkillProfileMinimumToDo()) && ((task.getStatus()==1) || ((task.getStatus()==2)&&(task.getWhoDidIt().getId()==user.getId())))) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public boolean isOkCheckButton(Task task) {
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),task.getSkillProfileMinimumToCheck()) && task.getStatus()==3 && task.getWhoDidIt().getId()!=this.user.getId()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isOkPlanButton(Task task) {
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),task.getSkillProfileMinimumToDo()) && task.getStatus()==0) {
			return true;
		} else {
			return false;
		}
	}
	
	public String reservationAction(Task task) {
		task.setWhoDidIt(this.user);
		task.setStatus(2);
		System.out.println(task);
		taskRep.update(task.getId(), task);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				task,
				null,
				null,
				null,
				null,
				0,
				user.getUserName()+" a réservé la tâche "+task.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		System.out.println(savedNewEvent);
		
		return "user/userPage.xhtml?faces-redirect=true";
		
	}

	public String doAction(Task task) {
		task.setWhoDidIt(this.user);
		task.setStatus(3);
		System.out.println(task);
		taskRep.update(task.getId(), task);
		this.user.setScore(this.user.getScore()+task.getNbPoints());
		userRep.update(this.user.getId(), this.user);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				task,
				null,
				null,
				null,
				null,
				task.getNbPoints(),
				user.getUserName()+" a réalisé la tâche "+task.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		if(task.getRepeatAfter()!=0) {
			Task planTask = new Task(
					this.family,
					task.getName(),
					task.getDescription(),
					LocalDate.now().plusDays(task.getRepeatAfter()),
					task.getRepeatAfter(),
					task.getSkillProfileMinimumToDo(),
					task.getSkillProfileMinimumToCheck(),
					task.getDifficulty()
					);
			planTask.setStatus(1);
			planTask.setNbPoints(task.getNbPoints());
			planTask.setNextTask(task.getNextTask());
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
					task.getName()+" a été planifiée automatiquement pour dans "+task.getRepeatAfter()+" jours"
					);
			Event savedNewEvent2 = eventRep.save(newEvent2);
		}
		if(task.getNextTask()!=null) {
			Task planTask2 = new Task(
					this.family,
					task.getNextTask().getName(),
					task.getNextTask().getDescription(),
					LocalDate.now(),
					task.getNextTask().getRepeatAfter(),
					task.getNextTask().getSkillProfileMinimumToDo(),
					task.getNextTask().getSkillProfileMinimumToCheck(),
					task.getNextTask().getDifficulty()
					);
			planTask2.setStatus(1);
			planTask2.setNbPoints(task.getNextTask().getNbPoints());
			planTask2.setNextTask(task.getNextTask().getNextTask());
			Task savedPlanTask2 = taskRep.save(planTask2);

			Event newEvent3 = new Event(
					this.family,
					this.user,
					LocalDateTime.now(),
					savedPlanTask2,
					null,	
					null,
					null,
					null,
					0,
					task.getNextTask().getName()+" a été planifiée automatiquement"
					);
			Event savedNewEvent3 = eventRep.save(newEvent3);
		}
		this.user = userRep.find(SessionUtils.getUserId());
		return "/user/userPage.xhtml?faces-redirect=true";
		
	}
	
	public Boolean today(Task task) {
		if((task.getNextDate().getYear()==(LocalDate.now().getYear())&&(task.getNextDate().getDayOfYear()==(LocalDate.now().getDayOfYear())))){
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean tomorrow(Task task) {
		if((task.getNextDate().getYear()==(LocalDate.now().getYear())&&(task.getNextDate().getDayOfYear()==(LocalDate.now().getDayOfYear()+1)))){
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean moreThanTomorrow(Task task) {
		if((task.getNextDate().getYear()==(LocalDate.now().getYear())&&(task.getNextDate().getDayOfYear()>(1+LocalDate.now().getDayOfYear())))|(task.getNextDate().getYear()>LocalDate.now().getYear())){
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean past(Task task) {
		if((task.getNextDate().getYear()==(LocalDate.now().getYear())&&(task.getNextDate().getDayOfYear()<(LocalDate.now().getDayOfYear())))|(task.getNextDate().getYear()<LocalDate.now().getYear())){
			return true;
		} else {
			return false;
		}
	}
	
	public String nbDays(Task task) {
		if(task.getNextDate().getYear()==LocalDate.now().getYear()) {
			String s = (task.getNextDate().getDayOfYear()-LocalDate.now().getDayOfYear())+" jours";
			return s;
		} else {
			return "longtemps";
			
		}
	}
	
	
	
	
	public String checkAction(Task task) {
		task.setStatus(4);
		System.out.println(task);
		taskRep.update(task.getId(), task);
		this.user.setScore(this.user.getScore()+1);
		userRep.update(this.user.getId(), this.user);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				task,
				null,	
				null,
				null,
				null,
				1,
				user.getUserName()+" a validé la tâche "+task.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		System.out.println(savedNewEvent);
		this.setTasksList(taskRep.findTasksToDo(this.family.getId()));
		tasksList.sort(Comparator.comparing(Task::getNextDate));
		this.user = userRep.find(SessionUtils.getUserId());
		return "user/userPage.xhtml?faces-redirect=true";
		
	}
	
	public String modifyAction(Task taskD) {
		this.task = taskD;
		return "/detail/modifyTaskPage.xhtml";
	}

	public String planAction(Task taskD) {
		this.task = taskD;
		return "/detail/planTaskPage.xhtml";
	}
	
	public String detailsAction(Task taskD) {
		this.task = taskD;
		return "/detail/detailTaskPage.xhtml";
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
	

	public EventRepository getEventRep() {
		return eventRep;
	}

	public void setEventRep(EventRepository eventRep) {
		this.eventRep = eventRep;
	}


	public List<Task> getTasksList() {
		return tasksList;
	}

	public void setTasksList(List<Task> tasksList) {
		this.tasksList = tasksList;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public int getPointsOfMonth() {
		return pointsOfMonth;
	}

	public void setPointsOfMonth(int pointsOfMonth) {
		this.pointsOfMonth = pointsOfMonth;
	}
}
