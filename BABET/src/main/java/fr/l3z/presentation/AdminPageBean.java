package fr.l3z.presentation;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import fr.l3z.models.Event;
import fr.l3z.models.Family;
import fr.l3z.models.Skill;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.Task;
import fr.l3z.models.User;
import fr.l3z.repositories.EventRepository;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.SkillProfileRepository;
import fr.l3z.repositories.SkillRepository;
import fr.l3z.repositories.TaskRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.session.SessionUtils;


@ManagedBean
@ViewScoped
public class AdminPageBean  implements Serializable {
	
	
	private static final long serialVersionUID = 6978367080470271677L;
	 
	
	
	
	@Inject
	private FamilyRepository familyRep;
	@Inject 
	private EventRepository eventRep;
	@Inject
	private UserRepository userRep;
	@Inject
	private SkillRepository skillRep;
	@Inject
	private SkillProfileRepository skillProfileRep;
	
	private User user = new User();
	private Family family = new Family();
	private List<Family>familiesList = new ArrayList<Family>();
	private List<Event>eventsList = new ArrayList<Event>();
	private List<User>usersList = new ArrayList<User>();
	private List<Skill>skillsList = new ArrayList<Skill>();
	private String newFamilyName;
	private String newFamilyPassword;
	private String newUserName;
	private String newUserPassword;
	private Boolean newUserIsParent;
	private String newUserColor;
	private int newUserIdPicNumber;
	
	
	

	@PostConstruct
	private void init() {
		this.setFamiliesList(familyRep.findAll());
		this.setNewFamilyName("nouvelle Famille");
		this.setNewFamilyPassword("mot de passe");
		this.setNewUserName("nouveau membre");
		this.setNewUserPassword("mot de passe");
		this.setNewUserIsParent(false);
		this.setNewUserColor("grey");
	}
	
	public String updateUserList(Family family) {
		this.usersList=userRep.usersByFamily(family.getId());
		this.eventsList=eventRep.findByFamily(family.getId());
		eventsList.sort(Comparator.comparing(Event::getDate).reversed());
		this.skillsList=skillRep.findWithFamily(family.getId());
		this.family=familyRep.find(family.getId());
		this.user=null;
		return "admin/adminPage.xhtml?faces-redirect=true";
	}
	
	public String logOff() {
		SessionUtils.setAdminConnect(Long.valueOf(0));
		return "/index.xhtml?faces-redirect=true";
	}
	
	public String changeUserPassword() {
		this.user.setPassword(user.getPassword());
		userRep.update(this.user.getId(), user);
		return "admin/adminPage.xhtml?faces-redirect=true";
	}
	
	public String updateEventListWithUser(User user) {
		this.eventsList = eventRep.findByUser(user.getId());
		this.user=userRep.find(user.getId());
		return "admin/adminPage.xhtml?faces-redirect=true";
	}
	
	public String newFamily() {
		Family newFamily = new Family(newFamilyName,newFamilyPassword);
		Family savedNewFamily=familyRep.save(newFamily);
		this.setFamiliesList(familyRep.findAll());
		Skill newParentSkill= new Skill("Parent","Possibilité de créer de nouvelle compétences ou de valider des modifications de compétences.","","","","","",savedNewFamily);
		Skill savedNewParentSkill=skillRep.save(newParentSkill);
		return "admin/adminPage.xhtml?faces-redirect=true";
	}
	
	public String newUser() {
		if (this.family.getId()==null){
			return "admin/adminPage.xhtml?faces-redirect=true";
		} else {
			
			User newUser = new User(newUserName,newUserPassword,newUserColor);
			newUser.setFamily(this.family);
			SkillProfile newUserSP = new SkillProfile();
			
			if(newUserIsParent) {
				skillProfileRep.setSkillScore(skillRep.findByNameAndFamily(this.family.getId(),"Parent").getId(), newUserSP, 5);
			}
			SkillProfile savedNewUserSP = skillProfileRep.save(newUserSP);
			newUser.setSkillProfile(savedNewUserSP);
			User savedNewUser=userRep.save(newUser);
			this.usersList=userRep.usersByFamily(family.getId());
			return "admin/adminPage.xhtml?faces-redirect=true";
		}
	} 
	
	public String deleteFamily(Family family) {
		familyRep.delete(family.getId());
		this.setFamiliesList(familyRep.findAll());
		return "/admin/familyPageAdmin.xhtml";
	}
	
	public String deleteUser(User user) {
		userRep.delete(user.getId());
		this.usersList=userRep.usersByFamily(family.getId());
		return "/admin/familyPageAdmin.xhtml";
	}
	
	public Boolean deleteFamilyOK(Long id) {
		
		List<Event> eventByFamily = eventRep.findByFamily(id);
		if(eventByFamily.isEmpty()) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public Boolean deleteUserOK(Long id) {
		
		List<Event> eventByUser = eventRep.findByUser(id);
		if(eventByUser.isEmpty()) {
			return true;
		} else {
			return false;
		}
		
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
	
	
	public String detailsUser(Long userId) {
		this.setUser(userRep.find(userId));
		return "/admin/userPageAdmin.xhtml";
	}
	
	public String colorGrey(){
		this.newUserColor="grey";
		return "/admin/userPageAdmin.xhtml";
	}
	
	public String colorOrange(){
		this.newUserColor="orange";
		return "/admin/userPageAdmin.xhtml";
	}
	
	public String colorYellow(){
		this.newUserColor="yellow";
		return "/admin/userPageAdmin.xhtml";
	}
	
	public String colorBlue(){
		this.newUserColor="blue";
		return "/admin/userPageAdmin.xhtml";
	}
	
	public String colorGreen(){
		this.newUserColor="green";
		return "/admin/userPageAdmin.xhtml";
	}
	
	public String colorPink(){
		this.newUserColor="pink";
		return "/admin/userPageAdmin.xhtml";
	}
	
	public Boolean greyNotSelect() {
		if(newUserColor.equals("grey")) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean greySelect() {
		if(newUserColor.equals("grey")) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean orangeNotSelect() {
		if(newUserColor.equals("orange")) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean orangeSelect() {
		if(newUserColor.equals("orange")) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean yellowNotSelect() {
		if(newUserColor.equals("yellow")) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean yellowSelect() {
		if(newUserColor.equals("yellow")) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean blueNotSelect() {
		if(newUserColor.equals("blue")) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean blueSelect() {
		if(newUserColor.equals("blue")) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean greenNotSelect() {
		if(newUserColor.equals("green")) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean greenSelect() {
		if(newUserColor.equals("green")) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean pinkNotSelect() {
		if(newUserColor.equals("pink")) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean pinkSelect() {
		if(newUserColor.equals("pink")) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean adminConnectOK() {
		return SessionUtils.isAdminLogged();
	}
	
	public Boolean adminNotConnect() {
		if (SessionUtils.isAdminLogged()){
			return false;
		} else {
			return true;
		}
	}

	public List<Family> getFamiliesList() {
		return familiesList;
	}

	public void setFamiliesList(List<Family> familiesList) {
		this.familiesList = familiesList;
	}

	public List<Event> getEventsList() {
		return eventsList;
	}

	public void setEventsList(List<Event> eventsList) {
		this.eventsList = eventsList;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public List<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNewFamilyName() {
		return newFamilyName;
	}

	public void setNewFamilyName(String newFamilyName) {
		this.newFamilyName = newFamilyName;
	}

	public String getNewFamilyPassword() {
		return newFamilyPassword;
	}

	public void setNewFamilyPassword(String newFamilyPassword) {
		this.newFamilyPassword = newFamilyPassword;
	}

	public String getNewUserPassword() {
		return newUserPassword;
	}

	public void setNewUserPassword(String newUserPassword) {
		this.newUserPassword = newUserPassword;
	}

	public String getNewUserName() {
		return newUserName;
	}

	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
	}

	public List<Skill> getSkillsList() {
		return skillsList;
	}

	public void setSkillsList(List<Skill> skillsList) {
		this.skillsList = skillsList;
	}

	public Boolean getNewUserIsParent() {
		return newUserIsParent;
	}

	public void setNewUserIsParent(Boolean newUserIsParent) {
		this.newUserIsParent = newUserIsParent;
	}

	public String getNewUserColor() {
		return newUserColor;
	}

	public void setNewUserColor(String newUserColor) {
		this.newUserColor = newUserColor;
	}

	public int getNewUserIdPicNumber() {
		return newUserIdPicNumber;
	}

	public void setNewUserIdPicNumber(int newUserIdPicNumber) {
		this.newUserIdPicNumber = newUserIdPicNumber;
	}

}
