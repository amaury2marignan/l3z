package fr.l3z.presentation;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import fr.l3z.models.Family;
import fr.l3z.models.SkillNote;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.Task;
import fr.l3z.models.User;
import fr.l3z.models.Vote;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.TaskRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.repositories.VoteRepository;
import fr.l3z.session.SessionUtils;


@ManagedBean
@ViewScoped
public class UserPageBean  {
	
	
	
	
	@Inject
	private UserRepository userRep;
	@Inject
	private FamilyRepository familyRep;
	@Inject
	private TaskRepository taskRep;
	
	private User user = new User();
	private Family family = new Family();
	private List<Task> tasksList = new ArrayList<Task>();
	
	
	
	

	@PostConstruct
	private void init() {
		
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		this.setTasksList(taskRep.findAll());
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

	public List<Task> getTasksList() {
		return tasksList;
	}

	public void setTasksList(List<Task> tasksList) {
		this.tasksList = tasksList;
	}

	public boolean isOkReservationButton(Task task) {
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),task.getSkillProfileMinimumToDo()) && task.getStatus()==1 ) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isOkDoButton(Task task) {
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),task.getSkillProfileMinimumToDo()) && (task.getStatus()==1 || task.getStatus()==2)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isOkCheckButton(Task task) {
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),task.getSkillProfileMinimumToCheck()) && task.getStatus()==3 ) {
			return true;
		} else {
			return false;
		}
	}

	

	

	
}
