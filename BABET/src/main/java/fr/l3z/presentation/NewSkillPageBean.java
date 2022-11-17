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
public class NewSkillPageBean  implements Serializable {
	
	
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
	private Skill skill = new Skill();
	private List<User> userList =new ArrayList<User>();
	
	

	@PostConstruct
	private void init() {
		
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		this.skill.setName("Nom de la Nouvelle Compétence");
		this.skill.setDescription("Description");
		this.skill.setDescriptionLevel1("Description niveau 1");
		this.skill.setDescriptionLevel2("Description niveau 2");
		this.skill.setDescriptionLevel3("Description niveau 3");
		this.skill.setDescriptionLevel4("Description niveau 4");
		this.skill.setDescriptionLevel5("Description niveau 5");
		this.userList=userRep.usersByFamily(this.family.getId());
		}
		
		
	public String boutonCancel() {
		return "/user/userSkillProfilePage.xhtml";
	}
	
	public String newSkill() {
		Skill newSkill= new Skill();
		newSkill.setFamily(this.family);
		newSkill.setName(this.skill.getName());
		newSkill.setDescription(this.skill.getDescription());
		newSkill.setDescriptionLevel1(this.skill.getDescriptionLevel1());
		newSkill.setDescriptionLevel2(this.skill.getDescriptionLevel2());
		newSkill.setDescriptionLevel3(this.skill.getDescriptionLevel3());
		newSkill.setDescriptionLevel4(this.skill.getDescriptionLevel4());
		newSkill.setDescriptionLevel5(this.skill.getDescriptionLevel5());
		
		Skill savedNewSkill = skillRep.save(newSkill);
		
		for(User u:this.userList) {
			Long idSkillParent=null;
			
			for(Skill s:skillRep.findWithFamily(this.family.getId())) {
				if(s.getName().equals("Parent")) {
					idSkillParent = s.getId();
				}
			}
			
			if(skillProfileRep.isThisSkillIn(idSkillParent, u.getSkillProfile())) {
				skillProfileRep.setSkillScore(savedNewSkill.getId(), u.getSkillProfile(), 5);
				skillProfileRep.update(u.getSkillProfile().getId(), u.getSkillProfile());
				userRep.update(u.getId(), u);
			} else {
			skillProfileRep.setSkillScore(savedNewSkill.getId(), u.getSkillProfile(), 1);
			skillProfileRep.update(u.getSkillProfile().getId(), u.getSkillProfile());
			userRep.update(u.getId(), u);
				}
			}
		
		
		skillProfileRep.setSkillScore(savedNewSkill.getId(), this.user.getSkillProfile(), 5);
		skillProfileRep.update(this.user.getSkillProfile().getId(), this.user.getSkillProfile());
		userRep.update(this.user.getId(), this.user);
		
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				null,
				null,	
				null,
				savedNewSkill,
				null,
				0,
				user.getUserName()+" a créé la compétence "+savedNewSkill.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		return "/user/userSkillProfilePage.xhtml";
	}
	
	public Boolean saveButtonOK() {
		
		SkillNote parentSkillNote = new SkillNote(skillRep.findByNameAndFamily(this.family.getId(),"parent"),5);
		SkillProfile parentSkillProfile = new SkillProfile();
		skillProfileRep.setSkillScore(parentSkillNote.getSkill().getId(), parentSkillProfile, 5);
		
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(), parentSkillProfile)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String newSkillVote() {
		
		
		return "/user/userSkillProfilePage.xhtml";
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


	public List<User> getUserList() {
		return userList;
	}


	public void setUserList(List<User> userList) {
		this.userList = userList;
	}


	

}
