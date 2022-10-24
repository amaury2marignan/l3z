package fr.l3z.presentation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import fr.l3z.models.Family;
import fr.l3z.models.User;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.UserRepository;
import fr.l3z.session.SessionUtils;


@ManagedBean
@ViewScoped
public class LoginBean {
	
	private String userName;
	private String password;
	private String familyName;
	private String passwordFamily;
	
	@Inject
	private UserRepository userRep;
	@Inject
	private FamilyRepository familyRep;
	
	
	
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
	
	public String doLoginFamily() {
		Family family = familyRep.findByName(familyName);
		System.out.println("famille trouvée : "+family);
		if((family != null)&&(family.getPassword().equals(passwordFamily))){
			SessionUtils.setFamilyId(family.getId());
			System.out.println("connecté : " + familyRep.find(SessionUtils.getFamilyId()));
			
			return "family/familyPage.xhtml?faces-redirect=true";
		}
		return "index.xhtml";
	}
	
	
	
	public String doLogin() {
		
		if((userName.equals("BabetAdmin"))&&(password.equals("Gyne301"))){
			SessionUtils.setAdminConnect(Long.valueOf(1));
			return "/admin/adminPage.xhtml?faces-redirect=true";
		}
		return "index.xhtml";
	}
	
	
}
