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
		
		initEventList(this.vote.getId());
		
		
	}
	
	
	public void initEventList(Long t) {
		this.eventList=eventRep.findByVote(t);
	}
	
	public String giveDate(Event event) {
		
		return "le "+event.getDate().getDayOfMonth()+" / "+event.getDate().getMonthValue()+" à "+event.getDate().getHour()+":"+event.getDate().getMinute();
	}
	
	public String save(Vote vote) {
		voteRep.update(vote.getId(),vote);
		return "/user/userPageVote.xhtml";
	}
	
	public String cancel() {
		return "/user/userPageVote.xhtml";
	}
	
	public String starAction1(SkillNote sN) {
		skillProfileRep.setSkillScore(sN.getId(), this.vote.getSkillProfile(), 1);
		return "detail/detailVotePage.xhtml";
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
