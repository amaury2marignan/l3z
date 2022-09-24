package fr.l3z.presentation;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import fr.l3z.models.Family;
import fr.l3z.models.Event;
import fr.l3z.models.User;
import fr.l3z.repositories.FamilyRepository;
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
	
	private User user = new User();
	private Family family = new Family();
	private List<Event> eventsList = new ArrayList<Event>();
	
	
	
	

	@PostConstruct
	private void init() {
		
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		this.setEventsList(eventRep.findAll());
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

	

	

	

	
}
