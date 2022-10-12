package fr.l3z.presentation;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import fr.l3z.models.Family;
import fr.l3z.models.SkillNote;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.Vote;
import fr.l3z.models.User;
import fr.l3z.repositories.FamilyRepository;
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
	
	private User user = new User();
	private Family family = new Family();
	private List<Vote> votesList = new ArrayList<Vote>();
	private Vote vote = new Vote();
	
	
	

	@PostConstruct
	private void init() {
		
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		this.setVotesList(voteRep.findAll());
	}
	
	public String detailVote(Vote voteD) {
		this.vote=voteD;
		
		return "/detail/detailVotePage.xhtml";
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


	

	

	
}
