package fr.l3z.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity 
@Table
public class Vote {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private SkillProfile skillProfile;
	
	@ManyToOne
	private Task originalTask;
	
	private Boolean openToVote;
	
	private String taskNewName;
	
	private String taskNewDescription;
	
	private int taskNewRepeatAfter;
	
	@ManyToOne
	private Task taskNewNextTask;
	
	@OneToOne
	private SkillProfile taskNewSPMDo;
	
	@OneToOne
	private User whoDidIt;
	
	@ManyToMany
	private List<User> whoVote = new ArrayList<User>();
	
	
	public Vote() {
		super();
	}
	
	

	public SkillProfile getSkillProfile() {
		return skillProfile;
	}

	public void setSkillProfile(SkillProfile skillProfile) {
		this.skillProfile = skillProfile;
	}

	public Long getId() {
		return id;
	}


	public Boolean getOpenToVote() {
		return openToVote;
	}

	public void setOpenToVote(Boolean openToVote) {
		this.openToVote = openToVote;
	}

	public Task getOriginalTask() {
		return originalTask;
	}

	public void setOriginalTask(Task originalTask) {
		this.originalTask = originalTask;
	}

	

	






	


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vote [id=");
		builder.append(id);
		builder.append(", skillProfile=");
		builder.append(skillProfile);
		builder.append(", originalTask=");
		builder.append(originalTask);
		builder.append(", openToVote=");
		builder.append(openToVote);
		builder.append(", taskNewName=");
		builder.append(taskNewName);
		builder.append(", taskNewDescription=");
		builder.append(taskNewDescription);
		builder.append(", taskNewRepeatAfter=");
		builder.append(taskNewRepeatAfter);
		builder.append(", taskNewNextTask=");
		builder.append(taskNewNextTask);
		builder.append(", taskNewSPMDo=");
		builder.append(taskNewSPMDo);
		builder.append(", whoDidIt=");
		builder.append(whoDidIt);
		builder.append(", whoVote=");
		builder.append(whoVote);
		builder.append("]");
		return builder.toString();
	}



	public Task getTaskNewNextTask() {
		return taskNewNextTask;
	}



	public void setTaskNewNextTask(Task taskNewNextTask) {
		this.taskNewNextTask = taskNewNextTask;
	}



	public String getTaskNewName() {
		return taskNewName;
	}



	public void setTaskNewName(String taskNewName) {
		this.taskNewName = taskNewName;
	}



	public String getTaskNewDescription() {
		return taskNewDescription;
	}



	public void setTaskNewDescription(String taskNewDescription) {
		this.taskNewDescription = taskNewDescription;
	}



	public int getTaskNewRepeatAfter() {
		return taskNewRepeatAfter;
	}



	public void setTaskNewRepeatAfter(int taskNewRepeatAfter) {
		this.taskNewRepeatAfter = taskNewRepeatAfter;
	}



	public SkillProfile getTaskNewSPMDo() {
		return taskNewSPMDo;
	}



	public void setTaskNewSPMDo(SkillProfile taskNewSPMDo) {
		this.taskNewSPMDo = taskNewSPMDo;
	}



	public User getWhoDidIt() {
		return whoDidIt;
	}



	public void setWhoDidIt(User whoDidIt) {
		this.whoDidIt = whoDidIt;
	}



	public List<User> getWhoVote() {
		return whoVote;
	}



	public void setWhoVote(List<User> whoVote) {
		this.whoVote = whoVote;
	}
	
	
	
	

}
