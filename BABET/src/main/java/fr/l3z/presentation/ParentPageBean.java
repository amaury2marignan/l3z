package fr.l3z.presentation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import fr.l3z.models.Event;
import fr.l3z.models.Family;
import fr.l3z.models.User;
import fr.l3z.repositories.EventRepository;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.session.SessionUtils;

@ManagedBean
@ViewScoped
public class ParentPageBean {
	
	@Inject
	private FamilyRepository familyRep;
	@Inject
	private UserRepository userRep;
	@Inject
	private EventRepository eventRep;
	
	
	private Family family = new Family();
	private List<User> users = new ArrayList<User>();
	private User connectedUser = new User();
	private int newPoints;
	
	
	

	@PostConstruct
	private void init() {
		System.out.println("familyBeanOf: " + SessionUtils.getFamilyId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		this.connectedUser = userRep.find(SessionUtils.getUserId());
		
		this.setUsers(userRep.usersByFamily(family.getId()));
		setNewPoints(0);
	}
	
	
	
	public int getDayPoints(User u) {
		return eventRep.pointsOfDay(u.getId());
	}
	
	public int getMonthPoints(User u) {
		return eventRep.pointsOfMonth(u.getId());
	}

	public String getPic(User u) {
		return "../resources/idPics/"+u.getColor()+"/id"+u.getIdPicNumber()+".png";
	}
	
	public String withdraw10Points(User u) {
		u.setScore(u.getScore()-10);
		userRep.update(u.getId(), u);
		Event newEvent = new Event(
				this.family,
				this.connectedUser,
				LocalDateTime.now(),
				null,
				null,
				null,
				null,
				null,
				0,
				connectedUser.getUserName()+" a retiré 10 points à "+u.getUserName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		
		Event newEvent2 = new Event(
				this.family,
				u,
				LocalDateTime.now(),
				null,
				null,
				null,
				null,
				null,
				-100,
				u.getUserName()+" a perdu 10 points"	
				);
		Event savedNewEvent2 = eventRep.save(newEvent2);
		return "parent/parentPage.xhtml";
	}
	
	
	public String add10Points(User u) {
		
		u.setScore(u.getScore()+10);
		userRep.update(u.getId(), u);
		Event newEvent = new Event(
				this.family,
				this.connectedUser,
				LocalDateTime.now(),
				null,
				null,
				null,
				null,
				null,
				0,
				connectedUser.getUserName()+" a ajouté 10 points à "+u.getUserName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		
		Event newEvent2 = new Event(
				this.family,
				u,
				LocalDateTime.now(),
				null,
				null,
				null,
				null,
				null,
				10,
				u.getUserName()+" a reçu 10 points"	
				);
		Event savedNewEvent2 = eventRep.save(newEvent2);
				
		return "parent/parentPage.xhtml";
	}
	
	public String withdraw100Points(User u) {
		u.setScore(u.getScore()-100);
		userRep.update(u.getId(), u);
		Event newEvent = new Event(
				this.family,
				this.connectedUser,
				LocalDateTime.now(),
				null,
				null,
				null,
				null,
				null,
				0,
				connectedUser.getUserName()+" a retiré 100 points à "+u.getUserName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		
		Event newEvent2 = new Event(
				this.family,
				u,
				LocalDateTime.now(),
				null,
				null,
				null,
				null,
				null,
				-100,
				u.getUserName()+" a perdu 100 points"	
				);
		Event savedNewEvent2 = eventRep.save(newEvent2);
		return "parent/parentPage.xhtml";
	}
	
	
	public String add100Points(User u) {
		
		u.setScore(u.getScore()+100);
		userRep.update(u.getId(), u);
		Event newEvent = new Event(
				this.family,
				this.connectedUser,
				LocalDateTime.now(),
				null,
				null,
				null,
				null,
				null,
				0,
				connectedUser.getUserName()+" a ajouté 100 points à "+u.getUserName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		
		Event newEvent2 = new Event(
				this.family,
				u,
				LocalDateTime.now(),
				null,
				null,
				null,
				null,
				null,
				100,
				u.getUserName()+" a reçu 100 points"	
				);
		Event savedNewEvent2 = eventRep.save(newEvent2);
				
		return "parent/parentPage.xhtml";
	}
	
	public String withdraw1Points(User u) {
		u.setScore(u.getScore()-1);
		userRep.update(u.getId(), u);
		Event newEvent = new Event(
				this.family,
				this.connectedUser,
				LocalDateTime.now(),
				null,
				null,
				null,
				null,
				null,
				0,
				connectedUser.getUserName()+" a retiré 1 point à "+u.getUserName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		
		Event newEvent2 = new Event(
				this.family,
				u,
				LocalDateTime.now(),
				null,
				null,
				null,
				null,
				null,
				-1,
				u.getUserName()+" a perdu 1 point"	
				);
		Event savedNewEvent2 = eventRep.save(newEvent2);
		return "parent/parentPage.xhtml";
	}
	
	
	public String add1Points(User u) {
		
		u.setScore(u.getScore()+1);
		userRep.update(u.getId(), u);
		Event newEvent = new Event(
				this.family,
				this.connectedUser,
				LocalDateTime.now(),
				null,
				null,
				null,
				null,
				null,
				0,
				connectedUser.getUserName()+" a ajouté 1 points à "+u.getUserName()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		
		Event newEvent2 = new Event(
				this.family,
				u,
				LocalDateTime.now(),
				null,
				null,
				null,
				null,
				null,
				1,
				u.getUserName()+" a reçu 1 points"	
				);
		Event savedNewEvent2 = eventRep.save(newEvent2);
				
		return "parent/parentPage.xhtml";
	}

	public FamilyRepository getFamilyRep() {
		return familyRep;
	}


	public void setFamilyRep(FamilyRepository familyRep) {
		this.familyRep = familyRep;
	}


	public Family getFamily() {
		return family;
	}


	public void setFamily(Family family) {
		this.family = family;
	}


	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}


	public User getConnectedUser() {
		return connectedUser;
	}


	public void setConnectedUser(User connectedUser) {
		this.connectedUser = connectedUser;
	}



	public int getNewPoints() {
		return newPoints;
	}



	public void setNewPoints(int newPoints) {
		this.newPoints = newPoints;
	}

	
	

}
