package fr.l3z.presentation;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import fr.l3z.models.Event;
import fr.l3z.models.Family;
import fr.l3z.models.Skill;
import fr.l3z.models.Task;
import fr.l3z.models.User;
import fr.l3z.repositories.EventRepository;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.SkillRepository;
import fr.l3z.repositories.TaskRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.session.SessionUtils;


@ManagedBean
@ViewScoped
public class UserSkillProfilePageBean  implements Serializable {
	
	
	private static final long serialVersionUID = 6978367080470271677L;
	 
	
	
	@Inject
	private UserRepository userRep;
	@Inject
	private FamilyRepository familyRep;
	@Inject
	private SkillRepository skillRep;
	@Inject 
	private EventRepository eventRep;
	
	private User user = new User();
	private Family family = new Family();
	private List<Skill>skillsList = new ArrayList<Skill>();
	private Skill skill = new Skill();
	
	
	
	

	@PostConstruct
	private void init() {
		
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		this.setSkillsList(skillRep.findWithFamily(this.family.getId()));
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
	

	public EventRepository getEventRep() {
		return eventRep;
	}

	public void setEventRep(EventRepository eventRep) {
		this.eventRep = eventRep;
	}


	

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public List<Skill> getSkillsList() {
		return skillsList;
	}

	public void setSkillsList(List<Skill> skillsList) {
		this.skillsList = skillsList;
	}
}
