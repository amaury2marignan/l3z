package fr.l3z.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table
public class Family {
	
		@Id
		@GeneratedValue
		private Long id;
		
		private String familyName;
		private String password;
		
		

		public Family() {
			super();
		}

		public Family(String name, String password) {
			super();
			this.familyName = name;
			this.password = password;
		}

		public String getFamilyName() {
			return familyName;
		}

		public void setFamilyName(String name) {
			this.familyName = name;
		}

		public Long getId() {
			return id;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Family [id=");
			builder.append(id);
			builder.append(", name=");
			builder.append(familyName);
			builder.append("]");
			return builder.toString();
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		
		

}
