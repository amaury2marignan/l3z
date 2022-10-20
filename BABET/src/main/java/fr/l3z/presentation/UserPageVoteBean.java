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
import fr.l3z.models.SkillVote;
import fr.l3z.models.Vote;
import fr.l3z.models.User;
import fr.l3z.repositories.EventRepository;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.SkillProfileRepository;
import fr.l3z.repositories.SkillVoteRepository;
import fr.l3z.repositories.TaskRepository;
import fr.l3z.repositories.VoteRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.session.SessionUtils;


@ManagedBean
@ViewScoped
public class UserPageVoteBean  {
	
	
	
	
	@Inject
	private UserRepository userRep;
	@Inject
	private FamilyRepository familyRep;
	@Inject
	private VoteRepository voteRep;
	@Inject
	private SkillVoteRepository skillVoteRep;
	@Inject 
	private TaskRepository taskRep;
	@Inject
	private SkillProfileRepository skillProfileRep;
	@Inject
	private EventRepository eventRep;
	
	private User user = new User();
	private Family family = new Family();
	private List<Vote> votesList = new ArrayList<Vote>();
	private Vote vote = new Vote();
	private List<SkillVote> skillVotesList = new ArrayList<SkillVote>();
	
	

	@PostConstruct
	private void init() {
		
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		this.setVotesList(voteRep.findAll());
		this.setSkillVotesList(skillVoteRep.findAllToDecide());
	}
	
	public String detailVote(Vote voteD) {
		this.vote=voteD;
		
		return "/detail/detailVotePage.xhtml";
	}
	
	public String cancelSkillVote(SkillVote sV) {
		sV.setStatus(0);
		skillVoteRep.update(sV.getId(), sV);
		Event newEvent = new Event(
				this.user,
				LocalDateTime.now(),
				null,
				null,	
				null,
				sV.getSkill(),
				user.getUserName()+" a annulé la demande d'étoile en "+sV.getSkill().getName()+" de "+sV.getWhoAsked().getUserName()
				);
		Event savedNewEvent = eventRep.save(newEvent);this.setSkillVotesList(skillVoteRep.findAllToDecide());
		return "/user/userPageVote.xhtml";
	}
	
	public String validSkillVote(SkillVote sV) {
		sV.setStatus(2);
		skillVoteRep.update(sV.getId(), sV);
		SkillProfile newSP=skillProfileRep.setSkillScore(sV.getSkill().getId(), sV.getWhoAsked().getSkillProfile(),1+skillProfileRep.getScore(sV.getSkill().getId(), sV.getWhoAsked().getSkillProfile()));
		skillProfileRep.update(sV.getWhoAsked().getSkillProfile().getId(), newSP);
		Event newEvent = new Event(
				this.user,
				LocalDateTime.now(),
				null,
				null,	
				null,
				sV.getSkill(),
				user.getUserName()+" a validé une étoile en "+sV.getSkill().getName()+" pour "+sV.getWhoAsked().getUserName()
				);
		Event savedNewEvent = eventRep.save(newEvent);
		this.setSkillVotesList(skillVoteRep.findAllToDecide());
		return "/user/userPageVote.xhtml";
	}
	
	
	
	public Boolean cancelSkillVoteOK(SkillVote sV) {
		SkillProfile sP = new SkillProfile();
		sP.getSkillNoteList().add(new SkillNote(sV.getSkill(),1+skillProfileRep.getScore(sV.getSkill().getId(),sV.getWhoAsked().getSkillProfile())));
		if((taskRep.compareSkillProfile(this.user.getSkillProfile(), sP))|(this.user.getId()==sV.getWhoAsked().getId())) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean validSkillVoteOK(SkillVote sV) {
		SkillProfile sP = new SkillProfile();
		sP.getSkillNoteList().add(new SkillNote(sV.getSkill(),1+skillProfileRep.getScore(sV.getSkill().getId(),sV.getWhoAsked().getSkillProfile())));
		if((taskRep.compareSkillProfile(this.user.getSkillProfile(), sP))&&(this.user.getId()!=sV.getWhoAsked().getId())) {
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
	
	

	public Boolean skillVoteNote1(SkillVote sV) {
		SkillNote sN = new SkillNote(sV.getSkill(),1);
		SkillProfile sP = new SkillProfile();
		sP.getSkillNoteList().add(sN);
		if(taskRep.compareSkillProfile(sV.getWhoAsked().getSkillProfile(), sP)) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillVoteNote2(SkillVote sV) {
		SkillNote sN = new SkillNote(sV.getSkill(),2);
		SkillProfile sP = new SkillProfile();
		sP.getSkillNoteList().add(sN);
		if(taskRep.compareSkillProfile(sV.getWhoAsked().getSkillProfile(), sP)) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillVoteNote3(SkillVote sV) {
		SkillNote sN = new SkillNote(sV.getSkill(),3);
		SkillProfile sP = new SkillProfile();
		sP.getSkillNoteList().add(sN);
		if(taskRep.compareSkillProfile(sV.getWhoAsked().getSkillProfile(), sP)) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillVoteNote4(SkillVote sV) {
		SkillNote sN = new SkillNote(sV.getSkill(),4);
		SkillProfile sP = new SkillProfile();
		sP.getSkillNoteList().add(sN);
		if(taskRep.compareSkillProfile(sV.getWhoAsked().getSkillProfile(), sP)) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillVoteNote5(SkillVote sV) {
		SkillNote sN = new SkillNote(sV.getSkill(),5);
		SkillProfile sP = new SkillProfile();
		sP.getSkillNoteList().add(sN);
		if(taskRep.compareSkillProfile(sV.getWhoAsked().getSkillProfile(), sP)) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillVoteNote1Inverted(SkillVote sV) {
		if(!(skillVoteNote1(sV))) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillVoteNote2Inverted(SkillVote sV) {
		if(!(skillVoteNote2(sV))) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillVoteNote3Inverted(SkillVote sV) {
		if(!(skillVoteNote3(sV))) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillVoteNote4Inverted(SkillVote sV) {
		if(!(skillVoteNote4(sV))) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillVoteNote5Inverted(SkillVote sV) {
		if(!(skillVoteNote5(sV))) {
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

	

	public List<Vote> getVotesList() {
		return votesList;
	}

	public void setVotesList(List<Vote> votesList) {
		this.votesList = votesList;
	}

	public Vote getVote() {
		return vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}

	public List<SkillVote> getSkillVotesList() {
		return skillVotesList;
	}

	public void setSkillVotesList(List<SkillVote> skillVotesList) {
		this.skillVotesList = skillVotesList;
	}


	

	

	
}
