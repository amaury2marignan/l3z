package fr.l3z.presentation;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import fr.l3z.models.Event;
import fr.l3z.models.Family;
import fr.l3z.models.Task;
import fr.l3z.models.User;
import fr.l3z.repositories.EventRepository;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.TaskRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.session.SessionUtils;


@ManagedBean
@ViewScoped
public class UserProfilePageBean  implements Serializable {
	
	
	private static final long serialVersionUID = 6978367080470271677L;
	 
	
	
	@Inject
	private UserRepository userRep;
	@Inject
	private FamilyRepository familyRep;
	@Inject
	private TaskRepository taskRep;
	@Inject 
	private EventRepository eventRep;
	
	private User user = new User();
	private Family family = new Family();
	private List<Task> tasksList = new ArrayList<Task>();
	private Task task = new Task();
	private int pointsOfDay;
	private int pointsOfMonth;
	private int ringsScore;
	
	private List<Integer> picList = new ArrayList<Integer>();
	
	

	@PostConstruct
	private void init() {
		
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		this.setTasksList(taskRep.findTasksToDo(this.family.getId()));
		tasksList.sort(Comparator.comparing(Task::getNextDate));
		this.pointsOfDay=eventRep.pointsOfDay(this.user.getId());
		this.pointsOfMonth=eventRep.pointsOfMonth(this.user.getId());
		this.ringsScore=this.user.getCoins();
		
		this.picList.add(2);
		this.picList.add(3);
		this.picList.add(4);
		this.picList.add(5);
		this.picList.add(6);
		this.picList.add(7);
		this.picList.add(8);
		this.picList.add(9);
		this.picList.add(10);
		this.picList.add(11);
		this.picList.add(12);
		this.picList.add(13);
		this.picList.add(14);
		this.picList.add(15);
		this.picList.add(16);
		this.picList.add(17);
		this.picList.add(18);
		this.picList.add(19);
		
	}
	
	public String picString() {
		return "../resources/idPics/"+this.user.getColor()+"/id"+this.user.getIdPicNumber()+".png";
	}
	
	public String picString2(int i) {
		return "../resources/idPics/"+this.user.getColor()+"/id"+i+".png";
		
	}
	
	public int costOfPic(int i) {
		return i*10+100;
	}
	
	public Boolean buyOk(int i) {
		if (costOfPic(i)>this.ringsScore) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean buyNotOk(int i) {
		if (costOfPic(i)>this.ringsScore) {
			return true;
		} else {
			return false;
		}
	}
	
	public String buyPic(int i) {
		this.user.setIdPicNumber(i);
		this.user.setCoins(this.user.getCoins()-costOfPic(i));
		userRep.update(this.user.getId(),this.user);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				null,
				null,
				null,
				null,
				null,
				0,
				user.getUserName()+" a dépensé "+costOfPic(i)+" pièces"	
				);
		Event savedNewEvent = eventRep.save(newEvent);
		this.ringsScore=this.user.getCoins();
		return "user/userProfilePage.xhtml";
	}
	
	public String changeUser() {
		userRep.update(this.user.getId(), this.user);
		return "user/userProfilePage.xhtml?faces-redirect=true";
	}
	
	public String colorGrey(){
		this.user.setColor("grey");
		userRep.update(this.user.getId(),this.user);
		return "user/userProfilePage.xhtml";
	}
	
	public String colorOrange(){
		this.user.setColor("orange");
		userRep.update(this.user.getId(),this.user);
		return "user/userProfilePage.xhtml";
	}
	
	public String colorYellow(){
		this.user.setColor("yellow");
		userRep.update(this.user.getId(),this.user);
		return "user/userProfilePage.xhtml";
	}
	
	public String colorBlue(){
		this.user.setColor("blue");
		userRep.update(this.user.getId(),this.user);
		return "user/userProfilePage.xhtml";
	}
	
	public String colorGreen(){
		this.user.setColor("green");
		userRep.update(this.user.getId(),this.user);
		return "user/userProfilePage.xhtml";
	}
	
	public String colorPink(){
		this.user.setColor("pink");
		userRep.update(this.user.getId(),this.user);
		return "user/userProfilePage.xhtml";
	}
	
	public Boolean greyNotSelect() {
		if(this.user.getColor().equals("grey")) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean greySelect() {
		if(this.user.getColor().equals("grey")) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean orangeNotSelect() {
		if(this.user.getColor().equals("orange")) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean orangeSelect() {
		if(this.user.getColor().equals("orange")) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean yellowNotSelect() {
		if(this.user.getColor().equals("yellow")) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean yellowSelect() {
		if(this.user.getColor().equals("yellow")) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean blueNotSelect() {
		if(this.user.getColor().equals("blue")) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean blueSelect() {
		if(this.user.getColor().equals("blue")) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean greenNotSelect() {
		if(this.user.getColor().equals("green")) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean greenSelect() {
		if(this.user.getColor().equals("green")) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean pinkNotSelect() {
		if(this.user.getColor().equals("pink")) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean pinkSelect() {
		if(this.user.getColor().equals("pink")) {
			return true;
		} else {
			return false;
		}
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

			
	public Boolean today(Task task) {
		if((task.getNextDate().getYear()==(LocalDate.now().getYear())&&(task.getNextDate().getDayOfYear()==(LocalDate.now().getDayOfYear())))){
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean tomorrow(Task task) {
		if((task.getNextDate().getYear()==(LocalDate.now().getYear())&&(task.getNextDate().getDayOfYear()==(LocalDate.now().getDayOfYear()+1)))){
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean moreThanTomorrow(Task task) {
		if((task.getNextDate().getYear()==(LocalDate.now().getYear())&&(task.getNextDate().getDayOfYear()>(1+LocalDate.now().getDayOfYear())))|(task.getNextDate().getYear()>LocalDate.now().getYear())){
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean past(Task task) {
		if((task.getNextDate().getYear()==(LocalDate.now().getYear())&&(task.getNextDate().getDayOfYear()<(LocalDate.now().getDayOfYear())))|(task.getNextDate().getYear()<LocalDate.now().getYear())){
			return true;
		} else {
			return false;
		}
	}
	
	public String nbDays(Task task) {
		if(task.getNextDate().getYear()==LocalDate.now().getYear()) {
			String s = (task.getNextDate().getDayOfYear()-LocalDate.now().getDayOfYear())+" jours";
			return s;
		} else {
			return "longtemps";
			
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


	public List<Task> getTasksList() {
		return tasksList;
	}

	public void setTasksList(List<Task> tasksList) {
		this.tasksList = tasksList;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	

	public int getPointsOfMonth() {
		return pointsOfMonth;
	}

	public void setPointsOfMonth(int pointsOfMonth) {
		this.pointsOfMonth = pointsOfMonth;
	}

	public int getRingsScore() {
		return ringsScore;
	}

	public void setRingsScore(int ringsScore) {
		this.ringsScore = ringsScore;
	}

	public int getPointsOfDay() {
		return pointsOfDay;
	}

	public void setPointsOfDay(int pointsOfDay) {
		this.pointsOfDay = pointsOfDay;
	}

	public List<Integer> getPicList() {
		return picList;
	}

	public void setPicList(List<Integer> picList) {
		this.picList = picList;
	}

	
}
