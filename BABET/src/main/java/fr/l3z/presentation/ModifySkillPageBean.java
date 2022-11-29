package fr.l3z.presentation;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
public class ModifySkillPageBean  implements Serializable {
	
	
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
	
	
	

	@PostConstruct
	private void init() {
		
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String skillIdStr = map.get("skillId");
		Long skillId = Long.valueOf(skillIdStr);
		this.skill = skillRep.find(skillId);
		}
		
		
	public String boutonCancel() {
		return "/user/userSkillProfilePage.xhtml";
	}
	
	public String modifySkill() {
		skillRep.update(this.skill.getId(), this.skill);
		
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				null,
				null,	
				null,
				this.skill,
				null,
				0,
				user.getUserName()+" a modifié la compétence "+this.skill.getName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		return "/user/userSkillProfilePage.xhtml";
	}
	
	public Boolean saveButtonOK() {
		
		SkillNote parentSkillNote = new SkillNote(skillRep.findByNameAndFamily(this.family.getId(),"Parent"),5);
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


	

}
