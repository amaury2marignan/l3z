package fr.l3z.presentation;

import java.util.ArrayList;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import fr.l3z.models.Family;
import fr.l3z.models.User;
import fr.l3z.repositories.EventRepository;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.session.SessionUtils;

@ManagedBean
@ViewScoped
public class FamilyPageBean {
	
	@Inject
	private FamilyRepository familyRep;
	@Inject
	private UserRepository userRep;
	@Inject
	private EventRepository eventRep;
	
	
	private Family family = new Family();
	private List<User> users = new ArrayList<User>();
	private User connectedUser = new User();
	
	@PostConstruct
	private void init() {
		System.out.println("familyBeanOf: " + SessionUtils.getFamilyId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		
		
		
		this.setUsers(userRep.usersByFamily(family.getId()));
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
	
	public String doLoginUser(User user) {
		System.out.println("user en train de se connecter : "+user);
		SessionUtils.setUserId(user.getId());
		System.out.println("ID user connecte sur la session : "+SessionUtils.getUserId());
		
		return "/user/userPage.xhtml?faces-redirect=true";
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
	
	
	
}
