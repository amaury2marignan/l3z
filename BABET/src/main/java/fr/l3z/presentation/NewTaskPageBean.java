package fr.l3z.presentation;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.transaction.Transactional;

import fr.l3z.models.Event;
import fr.l3z.models.Family;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.Task;
import fr.l3z.models.User;
import fr.l3z.repositories.EventRepository;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.SkillNoteRepository;
import fr.l3z.repositories.SkillProfileRepository;
import fr.l3z.repositories.SkillRepository;
import fr.l3z.repositories.TaskRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.session.SessionUtils;
import fr.l3z.models.Skill;
import fr.l3z.models.SkillNote;


@ManagedBean
@ViewScoped
public class NewTaskPageBean  implements Serializable {
	
	
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
	private SkillRepository skillRep;
	@Inject
	private SkillProfileRepository skillProfileRep;
	@Inject
	private SkillNoteRepository skillNoteRep;
	
	
	private User user = new User();
	private Family family = new Family();
	private Task task = new Task();
	private List<Skill> skillList = new ArrayList<Skill>();
	private List<String> allTaskNames=new ArrayList<String>();	
	private String nextTaskName=" ";
	
	

	@PostConstruct
	private void init() {
		
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		this.task.setName("Nom de la Tache");
		this.task.setDescription("Description");
		this.task.setRepeatAfter(0);
		this.task.setNextTask(new Task("-","-",null,0,null,null));
		this.task.setSkillProfileMinimumToDo(new SkillProfile());
		this.task.setSkillProfileMinimumToCheck(new SkillProfile());
		this.task.setStatus(0);
		this.task.setNbPoints(skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		this.skillList = skillRep.findAll();
		this.allTaskNames.add(" ");
		for(Task taskD:taskRep.findAll()) {
			this.allTaskNames.add(taskD.getName());
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
	

	public EventRepository getEventRep() {
		return eventRep;
	}

	public void setEventRep(EventRepository eventRep) {
		this.eventRep = eventRep;
	}




	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
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
	public Boolean skillNote1(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=1) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	
	public Boolean skillNote2(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=2) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillNote3(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=3) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillNote4(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=4) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillNote5(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=5) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean skillNote1Inverted(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=1) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean skillNote2Inverted(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=2) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean skillNote3Inverted(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=3) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean skillNote4Inverted(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=4) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean skillNote5Inverted(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=5) {
			return false;
		} else {
			return true;
		}
	}
	
	public String star1Action(Skill s) {
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToDo(), 1);
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToCheck(), 2);
		this.task.setNbPoints(skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}
	public String star0Action(Skill s) {
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToDo(), 0);
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToCheck(), 1);
		this.task.setNbPoints(skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}
	public String star2Action(Skill s) {
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToDo(), 2);
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToCheck(), 3);
		this.task.setNbPoints(skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
				
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}
	public String star3Action(Skill s) {
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToDo(), 3);
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToCheck(), 4);	
		this.task.setNbPoints(skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}
	public String star4Action(Skill s) {
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToDo(), 4);
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToCheck(), 5);	
		this.task.setNbPoints(skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}
	public String star5Action(Skill s) {
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToDo(), 5);
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToCheck(), 5);	
		this.task.setNbPoints(skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}

	public String boutonCancel() {
		return "/user/userPageNew.xhtml";
		
	}
	
	public String newTask() {
		if(this.nextTaskName.equals(" ")) {
			this.task.setNextTask(null);
		} else {
		this.task.setNextTask(taskRep.findByName(this.nextTaskName));
		}
		
		this.task.setStatus(0);
		
		SkillProfile savedSPMDo = skillProfileRep.save(this.task.getSkillProfileMinimumToDo());
		this.task.setSkillProfileMinimumToDo(savedSPMDo);
		

		SkillProfile savedSPMCh = skillProfileRep.save(this.task.getSkillProfileMinimumToCheck());
		this.task.setSkillProfileMinimumToCheck(savedSPMCh);
		
		
		Task savedNewTask = taskRep.save(this.task);
		Event newEvent = new Event(
				this.user,
				LocalDateTime.now(),
				savedNewTask,
				null,	
				null,
				user.getUserName()+" a créé la tâche "+task.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		return "/user/userPageNew.xhtml";
	}

	public List<Skill> getSkillList() {
		return skillList;
	}


	public void setSkillList(List<Skill> skillList) {
		this.skillList = skillList;
	}


	

	public List<String> getAllTaskNames() {
		return allTaskNames;
	}


	public void setAllTaskNames(List<String> allTaskNames) {
		this.allTaskNames = allTaskNames;
	}


	public String getNextTaskName() {
		return nextTaskName;
	}


	public void setNextTaskName(String nextTaskName) {
		this.nextTaskName = nextTaskName;
	}


	

}
