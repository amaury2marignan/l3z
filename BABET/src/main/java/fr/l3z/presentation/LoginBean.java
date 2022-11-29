package fr.l3z.presentation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import fr.l3z.models.Family;
import fr.l3z.models.Skill;
import fr.l3z.models.User;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.SkillProfileRepository;
import fr.l3z.repositories.SkillRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.session.SessionUtils;

@ManagedBean
@ViewScoped
public class LoginBean {

	private String userName;
	private String password;
	private String userFamilyName;
	private String familyName;
	private String passwordFamily;

	@Inject
	private UserRepository userRep;
	@Inject
	private FamilyRepository familyRep;
	@Inject
	private SkillProfileRepository skillProfileRep;
	@Inject
	private SkillRepository skillRep;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getPasswordFamily() {
		return passwordFamily;
	}

	public void setPasswordFamily(String passwordFamily) {
		this.passwordFamily = passwordFamily;
	}

	public String getUserFamilyName() {
		return userFamilyName;
	}

	public void setUserFamilyName(String userFamilyName) {
		this.userFamilyName = userFamilyName;
	}

	public String doLoginFamily() {
		Family family = familyRep.findByName(familyName);
		System.out.println("famille trouvée : " + family);
		if ((family != null) && (family.getPassword().equals(passwordFamily))) {
			SessionUtils.setFamilyId(family.getId());
			System.out.println("connecté : " + familyRep.find(SessionUtils.getFamilyId()));

			return "family/familyPage.xhtml?faces-redirect=true";
		}
		return "index.xhtml";
	}

	public String doLogin() {

		if ((userName.equals("BabetAdmin")) && (password.equals("Gyne301"))) {
			SessionUtils.setAdminConnect(Long.valueOf(1));
			return "/admin/adminPage.xhtml?faces-redirect=true";
		} else {
			if (userFamilyName != null) {
				Family family = familyRep.findByName(userFamilyName);
				User user = userRep.findByNameAndFamily(userName, family.getId());
				if ((user != null) && (user.getPassword().equals(password))) {
					SessionUtils.setUserId(user.getId());
					SessionUtils.setFamilyId(user.getFamily().getId());
					Skill parentSkill = skillRep.findByNameAndFamily(SessionUtils.getFamilyId(), "Parent");
					System.out.println(parentSkill);
					System.out.println("user : "+user.getUserName()+" parent skill score : "+skillProfileRep.getScore(parentSkill.getId(), user.getSkillProfile()));
					if (skillProfileRep.getScore(parentSkill.getId(), user.getSkillProfile()) == 5) {
						return "/parent/parentPage.xhtml?faces-redirect=true";
					} else {
						return "/user/userPage.xhtml?faces-redirect=true";
					}
				} else {
					return "/index.xhtml?faces-redirect=true";
				}
			} else {
				return "/index.xhtml?faces-redirect=true";
			}

		}
	}

}
