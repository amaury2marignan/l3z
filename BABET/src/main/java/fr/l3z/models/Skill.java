package fr.l3z.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table

public class Skill {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private String description;
	private String descriptionLevel1;
	private String descriptionLevel2;
	private String descriptionLevel3;
	private String descriptionLevel4;
	private String descriptionLevel5;
	
	@ManyToOne
	private Family family;

	public Skill(String name, String description, String descriptionLevel1, String descriptionLevel2,
			String descriptionLevel3, String descriptionLevel4, String descriptionLevel5, Family family) {
		super();
		this.name = name;
		this.description = description;
		this.descriptionLevel1 = descriptionLevel1;
		this.descriptionLevel2 = descriptionLevel2;
		this.descriptionLevel3 = descriptionLevel3;
		this.descriptionLevel4 = descriptionLevel4;
		this.descriptionLevel5 = descriptionLevel5;
		this.family = family;
	}

	public Skill() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptionLevel1() {
		return descriptionLevel1;
	}

	public void setDescriptionLevel1(String descriptionLevel1) {
		this.descriptionLevel1 = descriptionLevel1;
	}

	public String getDescriptionLevel2() {
		return descriptionLevel2;
	}

	public void setDescriptionLevel2(String descriptionLevel2) {
		this.descriptionLevel2 = descriptionLevel2;
	}

	public String getDescriptionLevel3() {
		return descriptionLevel3;
	}

	public void setDescriptionLevel3(String descriptionLevel3) {
		this.descriptionLevel3 = descriptionLevel3;
	}

	public String getDescriptionLevel4() {
		return descriptionLevel4;
	}

	public void setDescriptionLevel4(String descriptionLevel4) {
		this.descriptionLevel4 = descriptionLevel4;
	}

	public String getDescriptionLevel5() {
		return descriptionLevel5;
	}

	public void setDescriptionLevel5(String descriptionLevel5) {
		this.descriptionLevel5 = descriptionLevel5;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Skill [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", family=");
		builder.append(family);
		builder.append("]");
		return builder.toString();
	}
	
	
	


}
