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
import fr.l3z.models.SkillNote;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.SkillVote;
import fr.l3z.models.Task;
import fr.l3z.models.User;
import fr.l3z.repositories.EventRepository;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.SkillProfileRepository;
import fr.l3z.repositories.SkillRepository;
import fr.l3z.repositories.SkillVoteRepository;
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
	@Inject
	private SkillProfileRepository skillProfileRep;
	@Inject
	private TaskRepository taskRep;
	@Inject
	private SkillVoteRepository skillVoteRep;
	
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
	
	public Boolean newSkillButtonOK() {
		SkillNote parentSkillNote = new SkillNote(skillRep.findByNameAndFamily(this.family.getId(),"Parent"),5);
		SkillProfile parentSkillProfile = new SkillProfile();
		skillProfileRep.setSkillScore(parentSkillNote.getSkill().getId(), parentSkillProfile, 5);
		
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(), parentSkillProfile)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String newSkill() {
		return "/detail/newSkillPage.xhtml";
	}
	
	public Boolean buttonAskOK(Skill s) {
		Boolean lessThan5 = false;
		for(SkillNote sN:this.user.getSkillProfile().getSkillNoteList()) {
			if(sN.getSkill().getId()==s.getId()&&(sN.getScore()<5)) {
				lessThan5=true;
			} 
		}
		
		Boolean notAlreadyAsked = true;
		for(SkillVote sV:skillVoteRep.findAll()) {
			if((sV.getSkill().getId()==s.getId())&&(sV.getWhoAsked().getId()==this.user.getId())&&(sV.getStatus()==1)){
				notAlreadyAsked=false;
			}
		}
		
		if(lessThan5&&notAlreadyAsked) {
			return true;
		} else {
		return false;
		}
	}
	
	
	
	public String askForStar(Skill s) {
		SkillVote newSkillVote = new SkillVote(this.family,this.user,s);
		newSkillVote.setStatus(1);
		SkillVote savedNewSkillVote=skillVoteRep.save(newSkillVote);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				null,
				null,	
				null,
				s,
				savedNewSkillVote,
				0,
				user.getUserName()+" a demandé une étoile pour la compétence "+s.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		
		return "/user/userSkillProfilePage.xhtml";
	}
	public String detailsAction(Skill skillD) {
		this.skill = skillD;
		return "/detail/skillPage.xhtml";
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
