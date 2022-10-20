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
import javax.inject.Inject;
import javax.persistence.ManyToMany;

import fr.l3z.models.Family;
import fr.l3z.models.SkillNote;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.Task;
import fr.l3z.models.User;
import fr.l3z.models.Vote;
import fr.l3z.models.Event;
import fr.l3z.repositories.EventRepository;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.SkillNoteRepository;
import fr.l3z.repositories.SkillProfileRepository;
import fr.l3z.repositories.TaskRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.repositories.VoteRepository;
import fr.l3z.session.SessionUtils;


@ManagedBean
@ViewScoped
public class DetailVotePageBean implements Serializable {
	private static final long serialVersionUID = 6978367080470271677L;
	
	@Inject
	private UserRepository userRep;
	@Inject
	private FamilyRepository familyRep;
	@Inject 
	private EventRepository eventRep;
	@Inject
	private VoteRepository voteRep;
	@Inject
	private SkillNoteRepository skillNoteRep;
	@Inject
	private SkillProfileRepository skillProfileRep;
	@Inject
	private TaskRepository taskRep;
	
	private User user = new User();
	private Family family = new Family();
	private Vote vote= new Vote();
	
	@ManyToMany
	private List<Event> eventList = new ArrayList<Event>();
	
	
	
	
	
	@PostConstruct
	private void init() {
		
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String voteIdStr = map.get("voteId");
		
		Long voteId = Long.valueOf(voteIdStr);
		this.vote = voteRep.find(voteId);
		System.out.println("originalTask.sPMTCheck : "+this.vote.getOriginalTask().getSkillProfileMinimumToCheck());
		
		initEventList(this.vote.getId());
		
		
		
		
	}
	
	
	public void initEventList(Long t) {
		this.eventList=eventRep.findByVote(t);
	}
	
	public String giveDate(Event event) {
		
		return "le "+event.getDate().getDayOfMonth()+" / "+event.getDate().getMonthValue()+" à "+event.getDate().getHour()+":"+event.getDate().getMinute();
	}
	
	public String save() {
		
		this.vote.getWhoVote().add(this.user);
		
		
		if(!(this.vote.getDidSom1Vot())) {
			this.vote.setDidSom1Vot(true);
		}
		System.out.println("saving vote : "+this.vote);
		skillProfileRep.update(this.vote.getSkillProfile().getId(), this.vote.getSkillProfile());
		voteRep.update(this.vote.getId(),this.vote);
		
		Event newEvent = new Event(
				this.user,
				LocalDateTime.now(),
				this.vote.getOriginalTask(),
				this.vote,
				null,
				null,
				user.getUserName()+" a voté pour la modification de la tâche "+this.vote.getOriginalTask().getName()
				);
		Event savedNewEvent = eventRep.save(newEvent);
		return "/user/userPageVote.xhtml";
	}
	
	
	
	public Boolean isOkSaveButton() {
		if(this.vote.getOpenToVote()&&!(this.vote.getWhoVote().contains(this.user.getId()))&&!(taskRep.compareSkillProfile(this.vote.getSkillProfile(),this.vote.getOriginalTask().getSkillProfileMinimumToCheck()))) {
			return true;
		} else {
			return false;
			}
	}
	
	public Boolean isOkModifyButton() {
		if(taskRep.compareSkillProfile(this.vote.getSkillProfile(),this.vote.getOriginalTask().getSkillProfileMinimumToCheck())) {
			return true;
		} else {
		return false;
		}
	}
	
	public String saveModify() {
		this.vote.setOpenToVote(false);
		voteRep.update(this.vote.getId(),this.vote);
		
		Task newTask = new Task();
		if(this.vote.getTaskNewName().equals(" ")) {
			newTask.setNextTask(null);
		} else {
			newTask.setNextTask(taskRep.findByName(this.vote.getTaskNewName()));
		}
		
		newTask.setStatus(0);
		newTask.setDescription(this.vote.getTaskNewDescription());
		newTask.setName(this.vote.getTaskNewName());
		newTask.setRepeatAfter(this.vote.getTaskNewRepeatAfter());
		newTask.setNextTask(this.vote.getTaskNewNextTask());
		skillProfileRep.update(this.vote.getOriginalTask().getSkillProfileMinimumToDo().getId(),this.vote.getTaskNewSPMDo());
		
		SkillProfile newSPMCheck = new SkillProfile();
		for(SkillNote sN:this.vote.getTaskNewSPMDo().getSkillNoteList()) {
			System.out.println("savedModify : copie des SkillProfiles. sN : "+sN);
			skillProfileRep.setSkillScore(sN.getSkill().getId(), newSPMCheck, (sN.getScore()+1));
		}
		
		skillProfileRep.update(this.vote.getOriginalTask().getSkillProfileMinimumToCheck().getId(),newSPMCheck);
		newTask.setSkillProfileMinimumToCheck(skillProfileRep.find(this.vote.getOriginalTask().getSkillProfileMinimumToCheck().getId()));
		skillProfileRep.update(this.vote.getOriginalTask().getSkillProfileMinimumToDo().getId(),this.vote.getTaskNewSPMDo());
		newTask.setSkillProfileMinimumToDo(skillProfileRep.find(this.vote.getOriginalTask().getSkillProfileMinimumToDo().getId()));
		taskRep.update(this.vote.getOriginalTask().getId(), newTask);
		
		Event newEvent = new Event(
				this.user,
				LocalDateTime.now(),
				this.vote.getOriginalTask(),
				this.vote,
				null,
				null,
				user.getUserName()+" a validé la modification de la tâche "+this.vote.getOriginalTask().getName()
				);
		Event savedNewEvent = eventRep.save(newEvent);
		return "/user/userPageNew.xhtml";
	}
	
	public String cancel() {
		return "/user/userPageVote.xhtml";
	}
	
	public String starAction1(Long sNID) {
		System.out.println("staraction1 SN-ID : "+sNID );
		SkillNote sN = skillNoteRep.find(sNID);
		this.vote.setSkillProfile(skillProfileRep.setSkillScore(sN.getSkill().getId(), this.vote.getSkillProfile(), 1));
		return "detail/detailVotePage.xhtml?faces-redirect=true";
	}
	
	public String starAction2(Long sNID) {
		System.out.println("staraction2 SN-ID : "+sNID );
		SkillNote sN = skillNoteRep.find(sNID);
		this.vote.setSkillProfile(skillProfileRep.setSkillScore(sN.getSkill().getId(), this.vote.getSkillProfile(), 2));
		return "detail/detailVotePage.xhtml?faces-redirect=true";
	}
	
	public String starAction3(Long sNID) {
		System.out.println("staraction3 SN-ID : "+sNID );
		SkillNote sN = skillNoteRep.find(sNID);
		this.vote.setSkillProfile(skillProfileRep.setSkillScore(sN.getSkill().getId(), this.vote.getSkillProfile(), 3));
		return "detail/detailVotePage.xhtml?faces-redirect=true";
	}
	
	public String starAction4(Long sNID) {
		System.out.println("staraction4 SN-ID : "+sNID );
		SkillNote sN = skillNoteRep.find(sNID);
		this.vote.setSkillProfile(skillProfileRep.setSkillScore(sN.getSkill().getId(), this.vote.getSkillProfile(), 4));
		return "detail/detailVotePage.xhtml?faces-redirect=true";
	}
	
	public String starAction5(Long sNID) {
		System.out.println("staraction5 SN-ID : "+sNID );
		SkillNote sN = skillNoteRep.find(sNID);
		this.vote.setSkillProfile(skillProfileRep.setSkillScore(sN.getSkill().getId(), this.vote.getSkillProfile(), 5));
		return "detail/detailVotePage.xhtml?faces-redirect=true";
	}
	
	
	
	public Boolean repeatNew() {
		if(this.vote.getTaskNewRepeatAfter()>1) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean repeatDayNew() {
		if(this.vote.getTaskNewRepeatAfter()==1) {
			return true;
		} else {
			return false;
		}
	}
	public Boolean nextNew() {
		if (this.vote.getTaskNewNextTask()==null){
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean repeat() {
		if(this.vote.getOriginalTask().getRepeatAfter()>1) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean repeatDay() {
		if(this.vote.getOriginalTask().getRepeatAfter()==1) {
			return true;
		} else {
			return false;
		}
	}
	public Boolean next() {
		if (this.vote.getOriginalTask().getNextTask()==null){
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean yellow1(Long id){
		SkillNote sNToReach = skillNoteRep.find(id);
		SkillNote sN = new SkillNote();
		for(SkillNote sNsearch: this.vote.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sN.setSkill(sNsearch.getSkill());
				sN.setScore(sNsearch.getScore());
			}
		}
		if(sNToReach.getScore()>=1 && sN.getScore()>=1) {
			return true;
		} else {
		return false;
		}
	}
	
	public Boolean yellow2(Long id){
		SkillNote sNToReach = skillNoteRep.find(id);
		SkillNote sN = new SkillNote();
		for(SkillNote sNsearch: this.vote.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sN.setSkill(sNsearch.getSkill());
				sN.setScore(sNsearch.getScore());
			}
		}
		if(sNToReach.getScore()>=2 && sN.getScore()>=2) {
			return true;
		} else {
		return false;
		}
	}
	
	public Boolean yellow3(Long id){
		SkillNote sNToReach = skillNoteRep.find(id);
		SkillNote sN = new SkillNote();
		for(SkillNote sNsearch: this.vote.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sN.setSkill(sNsearch.getSkill());
				sN.setScore(sNsearch.getScore());
			}
		}
		if(sNToReach.getScore()>=3 && sN.getScore()>=3) {
			return true;
		} else {
		return false;
		}
	}
	
	public Boolean yellow4(Long id){
		SkillNote sNToReach = skillNoteRep.find(id);
		SkillNote sN = new SkillNote();
		for(SkillNote sNsearch: this.vote.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sN.setSkill(sNsearch.getSkill());
				sN.setScore(sNsearch.getScore());
			}
		}
		if(sNToReach.getScore()>=4 && sN.getScore()>=4) {
			return true;
		} else {
		return false;
		}
	}
	
	public Boolean yellow5(Long id){
		SkillNote sNToReach = skillNoteRep.find(id);
		SkillNote sN = new SkillNote();
		for(SkillNote sNsearch: this.vote.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sN.setSkill(sNsearch.getSkill());
				sN.setScore(sNsearch.getScore());
			}
		}
		if(sNToReach.getScore()>=5 && sN.getScore()>=5) {
			return true;
		} else {
		return false;
		}
	}
	
	public Boolean grey5(Long id){
		SkillNote sNToReach = skillNoteRep.find(id);
		SkillNote sN = new SkillNote();
		SkillNote sNUser =new SkillNote();
		
		for(SkillNote sNsearch: this.vote.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sN.setSkill(sNsearch.getSkill());
				sN.setScore(sNsearch.getScore());
			}
		}
		
		for(SkillNote sNsearch: this.user.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sNUser.setSkill(sNsearch.getSkill());
				sNUser.setScore(sNsearch.getScore());
			}
		}
		
		if(sNToReach.getScore()>=5 && sN.getScore()<5 && sNUser.getScore()>=5 ) {
			return true;
		} else {
		return false;
		}
	}
	
	public Boolean grey4(Long id){
		SkillNote sNToReach = skillNoteRep.find(id);
		SkillNote sN = new SkillNote();
		SkillNote sNUser =new SkillNote();
		
		for(SkillNote sNsearch: this.vote.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sN.setSkill(sNsearch.getSkill());
				sN.setScore(sNsearch.getScore());
			}
		}
		
		for(SkillNote sNsearch: this.user.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sNUser.setSkill(sNsearch.getSkill());
				sNUser.setScore(sNsearch.getScore());
			}
		}
		
		if(sNToReach.getScore()>=4 && sN.getScore()<4 && sNUser.getScore()>=4 ) {
			return true;
		} else {
		return false;
		}
	}
	
	public Boolean grey3(Long id){
		SkillNote sNToReach = skillNoteRep.find(id);
		SkillNote sN = new SkillNote();
		SkillNote sNUser =new SkillNote();
		
		for(SkillNote sNsearch: this.vote.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sN.setSkill(sNsearch.getSkill());
				sN.setScore(sNsearch.getScore());
			}
		}
		
		for(SkillNote sNsearch: this.user.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sNUser.setSkill(sNsearch.getSkill());
				sNUser.setScore(sNsearch.getScore());
			}
		}
		
		if(sNToReach.getScore()>=3 && sN.getScore()<3 && sNUser.getScore()>=3 ) {
			return true;
		} else {
		return false;
		}
	}
	
	public Boolean grey2(Long id){
		SkillNote sNToReach = skillNoteRep.find(id);
		SkillNote sN = new SkillNote();
		SkillNote sNUser =new SkillNote();
		
		for(SkillNote sNsearch: this.vote.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sN.setSkill(sNsearch.getSkill());
				sN.setScore(sNsearch.getScore());
			}
		}
		
		for(SkillNote sNsearch: this.user.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sNUser.setSkill(sNsearch.getSkill());
				sNUser.setScore(sNsearch.getScore());
			}
		}
		
		if(sNToReach.getScore()>=2 && sN.getScore()<2 && sNUser.getScore()>=2 ) {
			return true;
		} else {
		return false;
		}
	}
	
	public Boolean grey1(Long id){
		SkillNote sNToReach = skillNoteRep.find(id);
		SkillNote sN = new SkillNote();
		SkillNote sNUser =new SkillNote();
		
		for(SkillNote sNsearch: this.vote.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sN.setSkill(sNsearch.getSkill());
				sN.setScore(sNsearch.getScore());
			}
		}
		
		for(SkillNote sNsearch: this.user.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sNUser.setSkill(sNsearch.getSkill());
				sNUser.setScore(sNsearch.getScore());
			}
		}
		
		if(sNToReach.getScore()>=1 && sN.getScore()<1 && sNUser.getScore()>=1 ) {
			return true;
		} else {
		return false;
		}
	}
	
	public Boolean white1(Long id){
		SkillNote sNToReach= skillNoteRep.find(id);
		SkillNote sN = new SkillNote();
		SkillNote sNUser =new SkillNote();
		
		for(SkillNote sNsearch: this.vote.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sN.setSkill(sNsearch.getSkill());
				sN.setScore(sNsearch.getScore());
			}
		}
		
		for(SkillNote sNsearch: this.user.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sNUser.setSkill(sNsearch.getSkill());
				sNUser.setScore(sNsearch.getScore());
			}
		}
		
		if(sNToReach.getScore()>=1 && sN.getScore()<1 && sNUser.getScore()<1 ) {
			return true;
		} else {
		return false;
		}
	}
	
	public Boolean white2(Long id){
		SkillNote sNToReach= skillNoteRep.find(id);
		SkillNote sN = new SkillNote();
		SkillNote sNUser =new SkillNote();
		
		for(SkillNote sNsearch: this.vote.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sN.setSkill(sNsearch.getSkill());
				sN.setScore(sNsearch.getScore());
			}
		}
		
		for(SkillNote sNsearch: this.user.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sNUser.setSkill(sNsearch.getSkill());
				sNUser.setScore(sNsearch.getScore());
			}
		}
		
		if(sNToReach.getScore()>=2 && sN.getScore()<2 && sNUser.getScore()<2 ) {
			return true;
		} else {
		return false;
		}
	}
	
	public Boolean white3(Long id){
		SkillNote sNToReach= skillNoteRep.find(id);
		SkillNote sN = new SkillNote();
		SkillNote sNUser =new SkillNote();
		
		for(SkillNote sNsearch: this.vote.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sN.setSkill(sNsearch.getSkill());
				sN.setScore(sNsearch.getScore());
			}
		}
		
		for(SkillNote sNsearch: this.user.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sNUser.setSkill(sNsearch.getSkill());
				sNUser.setScore(sNsearch.getScore());
			}
		}
		
		if(sNToReach.getScore()>=3 && sN.getScore()<3 && sNUser.getScore()<3 ) {
			return true;
		} else {
		return false;
		}
	}
	
	public Boolean white4(Long id){
		SkillNote sNToReach= skillNoteRep.find(id);
		SkillNote sN = new SkillNote();
		SkillNote sNUser =new SkillNote();
		
		for(SkillNote sNsearch: this.vote.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sN.setSkill(sNsearch.getSkill());
				sN.setScore(sNsearch.getScore());
			}
		}
		
		for(SkillNote sNsearch: this.user.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sNUser.setSkill(sNsearch.getSkill());
				sNUser.setScore(sNsearch.getScore());
			}
		}
		
		if(sNToReach.getScore()>=4 && sN.getScore()<4 && sNUser.getScore()<4 ) {
			return true;
		} else {
		return false;
		}
	}
	
	public Boolean white5(Long id){
		SkillNote sNToReach= skillNoteRep.find(id);
		SkillNote sN = new SkillNote();
		SkillNote sNUser =new SkillNote();
		
		for(SkillNote sNsearch: this.vote.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sN.setSkill(sNsearch.getSkill());
				sN.setScore(sNsearch.getScore());
			}
		}
		
		for(SkillNote sNsearch: this.user.getSkillProfile().getSkillNoteList()) {
			if (sNsearch.getSkill().getId()==sNToReach.getSkill().getId()){
				sNUser.setSkill(sNsearch.getSkill());
				sNUser.setScore(sNsearch.getScore());
			}
		}
		
		if(sNToReach.getScore()>=5 && sN.getScore()<5 && sNUser.getScore()<5 ) {
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

	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}


	public Vote getVote() {
		return vote;
	}


	public void setVote(Vote vote) {
		this.vote = vote;
	}


	

	
	
}
