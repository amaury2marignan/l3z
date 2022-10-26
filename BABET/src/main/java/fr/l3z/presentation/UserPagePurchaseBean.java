package fr.l3z.presentation;


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
import fr.l3z.models.SkillNote;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.Purchase;
import fr.l3z.models.User;
import fr.l3z.repositories.EventRepository;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.PurchaseRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.session.SessionUtils;


@ManagedBean
@ViewScoped
public class UserPagePurchaseBean  {
	
	
	
	
	@Inject
	private UserRepository userRep;
	@Inject
	private FamilyRepository familyRep;
	@Inject
	private PurchaseRepository purchaseRep;
	@Inject
	private EventRepository eventRep;
	
	private User user = new User();
	private Family family = new Family();
	private List<Purchase> purchasesList = new ArrayList<Purchase>();
	private Purchase purchase = new Purchase();
	
	
	

	@PostConstruct
	private void init() {
		
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		this.setPurchasesList(purchaseRep.findPurchasesToDo(this.family.getId()));
	}
	
	public String doPurchase(Long purchaseId) {
		Purchase donePurchase = purchaseRep.find(purchaseId);
		donePurchase.setStatus(2);
		donePurchase.setWhoDidIt(this.user);
		purchaseRep.update(purchaseId, donePurchase);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				null,
				null,
				purchaseRep.find(purchaseId),
				null,
				user.getUserName()+" a acheté : "+donePurchase.getDescription()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		this.setPurchasesList(purchaseRep.findPurchasesToDo(this.family.getId()));
		return "user/userPagePurchase.xhtml";
	}
	
	public String cancelPurchase(Long purchaseId) {
		Purchase cancelledPurchase = purchaseRep.find(purchaseId);
		
		cancelledPurchase.setStatus(3);
		purchaseRep.update(purchaseId,cancelledPurchase);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				null,
				null,
				cancelledPurchase,
				null,
				user.getUserName()+" a enlevé de la liste de courses : "+cancelledPurchase.getDescription()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		this.setPurchasesList(purchaseRep.findPurchasesToDo(this.family.getId()));
		return "user/userPagePurchase.xhtml";
	}
	
	public String planPurchaseText(String purchaseDescription) {
		this.purchase=new Purchase(this.family,purchaseDescription);
		this.purchase.setStatus(1);
		purchaseRep.save(this.purchase);
		
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				null,
				null,
				this.purchase,
				null,
				user.getUserName()+" a ajouté à la liste de courses : "+purchase.getDescription()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		this.setPurchasesList(purchaseRep.findPurchasesToDo(this.family.getId()));
		return "/user/userPagePurchase.xhtml";
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

	

	public List<Purchase> getPurchasesList() {
		return purchasesList;
	}

	public void setPurchasesList(List<Purchase> purchasesList) {
		this.purchasesList = purchasesList;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}


	

	

	
}
