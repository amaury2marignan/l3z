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
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.transaction.Transactional;

import fr.l3z.models.Event;
import fr.l3z.models.Family;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.Task;
import fr.l3z.models.User;
import fr.l3z.models.Vote;
import fr.l3z.repositories.EventRepository;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.SkillNoteRepository;
import fr.l3z.repositories.SkillProfileRepository;
import fr.l3z.repositories.SkillRepository;
import fr.l3z.repositories.TaskRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.repositories.VoteRepository;
import fr.l3z.session.SessionUtils;
import fr.l3z.models.Skill;
import fr.l3z.models.SkillNote;


@ManagedBean
@ViewScoped
public class ModifyTaskPageBean  implements Serializable {
	
	
	private static final long serialVersionUID = 6978367080470271677L;
	 
	
	
	@Inject
	private UserRepository userRep;
	@Inject
	private FamilyRepository familyRep;
	@Inject
	private TaskRepository taskRep;
	@Inject 
	private EventRepository eventRep;
	@Inject
	private SkillRepository skillRep;
	@Inject
	private SkillProfileRepository skillProfileRep;
	@Inject
	private VoteRepository voteRep;
	@Inject
	private SkillNoteRepository skillNoteRep;
	
	private User user = new User();
	private Family family = new Family();
	private Task task = new Task();
	private List<Skill> skillList = new ArrayList<Skill>();
	private List<String> allTaskNames=new ArrayList<String>();	
	private String nextTaskName=" ";
	
	

	@PostConstruct
	private void init() {
		
		this.user = userRep.find(SessionUtils.getUserId());
		this.family = familyRep.find(SessionUtils.getFamilyId());
		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String taskIdStr = map.get("taskId");
		System.out.println("id de la tache : "+taskIdStr);
		if(taskIdStr!=null) {
		Long taskId = Long.valueOf(taskIdStr);
		this.task = taskRep.find(taskId);
		}
		this.task.setNbPoints(this.task.getDifficulty()+skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		this.skillList = skillRep.findWithFamily(this.family.getId());
		this.allTaskNames.add(" ");
		for(Task taskD:taskRep.findByStatus0(this.family.getId())) {
			this.allTaskNames.add(taskD.getName());
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




	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	
	
	
	
	
	public boolean isOkSaveButton() {
		
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),taskRep.find(this.task.getId()).getSkillProfileMinimumToCheck()) ) {
			return true;
		} else {
			return false;
		}
	}
	
public boolean isOkNewProjectButton() {
		
		if(taskRep.compareSkillProfile(this.user.getSkillProfile(),taskRep.find(this.task.getId()).getSkillProfileMinimumToCheck()) ) {
			return false;
		} else {
			return true;
		}
	}
	
	
	
	
	
	
	public Boolean skillNote1(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=1) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	
	public Boolean skillNote2(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=2) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillNote3(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=3) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillNote4(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=4) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean skillNote5(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=5) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean skillNote1Inverted(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=1) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean skillNote2Inverted(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=2) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean skillNote3Inverted(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=3) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean skillNote4Inverted(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=4) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean skillNote5Inverted(int skillId){
		if(skillProfileRep.getScore(Long.valueOf(skillId), this.task.getSkillProfileMinimumToDo())>=5) {
			return false;
		} else {
			return true;
		}
	}
	
	public String star1Action(Skill s) {
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToDo(), 1);
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToCheck(), 2);
		this.task.setNbPoints(this.task.getDifficulty()+skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}
	public String star0Action(Skill s) {
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToDo(), 0);
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToCheck(), 1);
		this.task.setNbPoints(this.task.getDifficulty()+skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}
	public String star2Action(Skill s) {
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToDo(), 2);
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToCheck(), 3);
		this.task.setNbPoints(this.task.getDifficulty()+skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
				
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}
	public String star3Action(Skill s) {
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToDo(), 3);
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToCheck(), 4);	
		this.task.setNbPoints(this.task.getDifficulty()+skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}
	public String star4Action(Skill s) {
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToDo(), 4);
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToCheck(), 5);	
		this.task.setNbPoints(this.task.getDifficulty()+skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}
	public String star5Action(Skill s) {
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToDo(), 5);
		skillProfileRep.setSkillScore(s.getId(), this.task.getSkillProfileMinimumToCheck(), 5);	
		this.task.setNbPoints(this.task.getDifficulty()+skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}

	public Boolean difNote1(){
		if(this.task.getDifficulty()>0){
			return true;
		} else {
			return false;
		}
		
	}
	
	public Boolean difNote2(){
		if(this.task.getDifficulty()>1){
			return true;
		} else {
			return false;
		}
		
	}
	
	public Boolean difNote3(){
		if(this.task.getDifficulty()>2){
			return true;
		} else {
			return false;
		}
		
	}
	
	public Boolean difNote4(){
		if(this.task.getDifficulty()>3){
			return true;
		} else {
			return false;
		}
		
	}
	public Boolean difNote5(){
		if(this.task.getDifficulty()>4){
			return true;
		} else {
			return false;
		}
		
	}
	
	public Boolean difNote5Inverted(){
		if(this.task.getDifficulty()<5) {
			return true;
		} else {
			return false;
		}
	}
	public Boolean difNote4Inverted(){
		if(this.task.getDifficulty()<4) {
			return true;
		} else {
			return false;
		}
	}
	public Boolean difNote3Inverted(){
		if(this.task.getDifficulty()<3) {
			return true;
		} else {
			return false;
		}
	}
	public Boolean difNote2Inverted(){
		if(this.task.getDifficulty()<2) {
			return true;
		} else {
			return false;
		}
	}
	public Boolean difNote1Inverted(){
		if(this.task.getDifficulty()<1) {
			return true;
		} else {
			return false;
		}
	}
	
	public String starDif5Action() {
		this.task.setDifficulty(5);
		this.task.setNbPoints(this.task.getDifficulty()+skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}
	
	public String starDif4Action() {
		this.task.setDifficulty(4);
		this.task.setNbPoints(this.task.getDifficulty()+skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}
	
	public String starDif3Action() {
		this.task.setDifficulty(3);
		this.task.setNbPoints(this.task.getDifficulty()+skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}
	
	public String starDif2Action() {
		this.task.setDifficulty(2);
		this.task.setNbPoints(this.task.getDifficulty()+skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}
	
	public String starDif1Action() {
		this.task.setDifficulty(1);
		this.task.setNbPoints(this.task.getDifficulty()+skillRep.getNbPoints(this.task.getSkillProfileMinimumToDo()));
		return "detail/newTaskPage.xhtml?faces-redirect=true";
	}
	
	public String boutonCancel() {
		return "/user/userPageNew.xhtml";
		
	}
	
	public String saveTask() {
		
		
		if(this.nextTaskName.equals(" ")) {
			this.task.setNextTask(null);
		} else {
			this.task.setNextTask(taskRep.findByName(this.nextTaskName));
		}
		
		this.task.setStatus(0);
		skillProfileRep.update(this.task.getSkillProfileMinimumToDo().getId(),this.task.getSkillProfileMinimumToDo());
		skillProfileRep.update(this.task.getSkillProfileMinimumToCheck().getId(),this.task.getSkillProfileMinimumToCheck());
		taskRep.update(this.task.getId(), this.task);
		
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				this.task,
				null,
				null,
				null,
				null,
				0,
				user.getUserName()+" a modifié la tâche "+this.task.getName()
				);
		Event savedNewEvent = eventRep.save(newEvent);
		return "/user/userPageNew.xhtml";
	}
	
	public String newProject() {
		Vote vote = new Vote();
		vote.setFamily(this.family);
		SkillProfile voteSp =new SkillProfile();
		for (Skill s : this.skillList) {
				SkillNote sN = new SkillNote(s,0);
				
				voteSp.getSkillNoteList().add(sN);
		}
		vote.setSkillProfile(skillProfileRep.save(voteSp));
		vote.setOriginalTask(taskRep.find(this.task.getId()));
		vote.setOpenToVote(true);
		vote.setTaskNewName(this.task.getName());
		vote.setTaskNewDescription(this.task.getDescription());
		vote.setTaskNewRepeatAfter(this.task.getRepeatAfter());
		vote.setWhoDidIt(this.user);
		vote.setTaskNewDifficulty(this.task.getDifficulty());
		
		if(nextTaskName.equals(" ")) {
			vote.setTaskNewNextTask(null);
		} else {
			vote.setTaskNewNextTask(taskRep.findByName(nextTaskName));
		}
		
		SkillProfile sp = new SkillProfile();
		for (SkillNote s : this.task.getSkillProfileMinimumToDo().getSkillNoteList()) {
			SkillNote s2 = new SkillNote(s.getSkill(),s.getScore());
			sp.getSkillNoteList().add(s2);
			}
		vote.setTaskNewSPMDo(skillProfileRep.save(sp));
		System.out.println(this.task.getSkillProfileMinimumToDo());
		System.out.println("vote : "+vote);
		Vote savedVote = voteRep.save(vote);
		System.out.println("savedVote : "+savedVote);
		Event newEvent = new Event(
				this.family,
				this.user,
				LocalDateTime.now(),
				this.task,
				savedVote,	
				null,
				null,
				null,
				0,
				user.getUserName()+" a proposé une modification de la tâche "+taskRep.find(this.task.getId()).getName()
				);
		Event savedNewEvent = eventRep.save(newEvent);
		
		return "/user/userPageVote.xhtml";
	}
	
	

	public List<Skill> getSkillList() {
		return skillList;
	}


	public void setSkillList(List<Skill> skillList) {
		this.skillList = skillList;
	}


	

	public List<String> getAllTaskNames() {
		return allTaskNames;
	}


	public void setAllTaskNames(List<String> allTaskNames) {
		this.allTaskNames = allTaskNames;
	}


	public String getNextTaskName() {
		return nextTaskName;
	}


	public void setNextTaskName(String nextTaskName) {
		this.nextTaskName = nextTaskName;
	}


	

}
