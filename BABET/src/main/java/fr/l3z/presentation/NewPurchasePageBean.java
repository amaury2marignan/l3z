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
import fr.l3z.models.Purchase;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.Task;
import fr.l3z.models.User;
import fr.l3z.repositories.EventRepository;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.PurchaseRepository;
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
public class NewPurchasePageBean  implements Serializable {
	
	
	private static final long serialVersionUID = 6978367080470271677L;
	 
	
	
	@Inject
	private UserRepository userRep;
	@Inject
	private FamilyRepository familyRep;
	@Inject
	private PurchaseRepository purchaseRep;
	@Inject 
	private EventRepository eventRep;
	@Inject
	private SkillProfileRepository skillProfileRep;
	
	private User user = new User();
	private Family family = new Family();
	private Purchase purchase = new Purchase();
	private List<Purchase> purchaseList = new ArrayList<Purchase>();
	

	@PostConstruct
	private void init() {
		
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		this.setPurchaseList(purchaseRep.findByStatus(0));
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


	public Purchase getPurchase() {
		return purchase;
	}





	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}





	public String boutonCancel() {
		return "/user/userPagePurchase.xhtml";
		
	}
	
	public String planPurchaseText(String purchaseDescription) {
		this.purchase=new Purchase(purchaseDescription);
		this.purchase.setStatus(1);
		purchaseRep.save(this.purchase);
		Purchase modelPurchase = new Purchase(purchaseDescription);
		modelPurchase.setStatus(0);
		purchaseRep.save(modelPurchase);
		Event newEvent = new Event(
				this.user,
				LocalDateTime.now(),
				null,
				null,
				this.purchase,
				user.getUserName()+" a ajouté à la liste de courses : "+purchase.getDescription()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		return "/user/userPagePurchase.xhtml";
	}

	public String planPurchase(Long purchaseId) {
		this.purchase=purchaseRep.find(purchaseId);
		Purchase newPurchase = new Purchase(this.purchase.getDescription());
		newPurchase.setStatus(1);
		purchaseRep.save(newPurchase);
		
		
		Event newEvent = new Event(
				this.user,
				LocalDateTime.now(),
				null,
				null,
				this.purchase,
				user.getUserName()+" a ajouté à la liste de courses : "+purchase.getDescription()	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		return "/user/userPagePurchase.xhtml";
	}





	public List<Purchase> getPurchaseList() {
		return purchaseList;
	}





	public void setPurchaseList(List<Purchase> purchaseList) {
		this.purchaseList = purchaseList;
	}
	

}
