package fr.l3z.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import fr.l3z.models.Domain;
import fr.l3z.models.Family;
import fr.l3z.models.Rule;
import fr.l3z.models.Skill;
import fr.l3z.models.SkillNote;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.Task;
import fr.l3z.models.User;
import fr.l3z.repositories.DomainRepository;
import fr.l3z.repositories.FamilyRepository;
import fr.l3z.repositories.RuleRepository;
import fr.l3z.repositories.SkillNoteRepository;
import fr.l3z.repositories.SkillProfileRepository;
import fr.l3z.repositories.SkillRepository;
import fr.l3z.repositories.TaskRepository;
import fr.l3z.repositories.UserRepository;

@Singleton
@Startup
public class Launcher {
	
	@Inject
	private UserRepository userRep;
	@Inject
	private FamilyRepository familyRep;
	@Inject 
	private SkillRepository skillRep;
	@Inject
	private SkillNoteRepository skillNoteRep;
	@Inject
	private DomainRepository domainRep;
	@Inject
	private TaskRepository taskRep;
	@Inject
	private SkillProfileRepository skillProfileRep;
	@Inject
	private RuleRepository ruleRep;
	
	
	@PostConstruct
	public void data() {
		
		Family f1 = new Family("Famille1","0000");
		Family savedF1 = familyRep.save(f1);
		Family f2 = new Family("Famille2","0000");
		Family savedF2 = familyRep.save(f2);
		
		Skill menage = new Skill("ménage", "aptitude à être autonome sur le ménage", "sait participer à un chantier ménage", "sait nettoyer la cuisine après un repas, passer l'aspirateur et faire la poussière","sait nettoyer les vitres et une salle de bain", "Peut prendre en charge le nettoyage d'un étage complet","Capable de superviser le ménage de toute la maison", savedF1);
		Skill cuisine = new Skill("cuisine", "aptitude à être autonome sur la préparation des repas", "sait participer à un chantier cuisine", "sait préparer des pâtes ou du riz","sait faire un gateau ou un plat complet", "Peut préparer un repas complet","Capable de préparer les menus, de mettre à jour la liste de course et de superviser toute activité liée à la cuisine", savedF1);
		Skill parent = new Skill("parent", "compétence spéciale réservée aux parents", " ", " "," ", " ","fait partie de la caste des parents", savedF1);
		Skill animaux = new Skill("animaux", "aptitude à être autonome sur la gestion des animaux domestiques", "sait nourrir les animaux domestiques", "sait nettoyer les lieux de vie ou objets des animaux domestiques","sait donner des médicaments aux animaux domestiques", "Peut identifier les besoins spéciaux d'un animal domestique, gérer les visites vétérinaires et l'y emmener le cas échéant","Peut prendre des décisions d'adoption sur de nouveaux animaux", savedF1);
		Skill savedMenage = skillRep.save(menage);
		Skill savedCuisine = skillRep.save(cuisine);
		Skill savedParent = skillRep.save(parent);
		Skill savedAnimaux = skillRep.save(animaux);
		
		
		
		
		
		User u1 = new User("papaBabet","0000");
		User savedU1 = userRep.save(u1);
		List<Family> list1 = new ArrayList<Family>();
		list1.add(savedF1);
		list1.add(savedF2);
		savedU1.setFamilyList(list1);
		SkillNote menageLevel1 = new SkillNote(savedMenage,5);
		SkillNote cuisineLevel1 = new SkillNote(savedCuisine,4);
		SkillNote parentLevel1 = new SkillNote(savedParent,5);
		SkillNote animauxLevel1 = new SkillNote(savedAnimaux,5);
		SkillNote SavedMenageLevel1 = skillNoteRep.save(menageLevel1);
		SkillNote SavedCuisineLevel1 = skillNoteRep.save(cuisineLevel1);
		SkillNote SavedParentLevel1 = skillNoteRep.save(parentLevel1);
		SkillNote SavedAnimauxLevel1 = skillNoteRep.save(animauxLevel1);
		
		SkillProfile skillProfile1 = new SkillProfile();
		skillProfile1.getSkillNoteList().add(SavedMenageLevel1);
		skillProfile1.getSkillNoteList().add(SavedCuisineLevel1);
		skillProfile1.getSkillNoteList().add(SavedParentLevel1);
		skillProfile1.getSkillNoteList().add(SavedAnimauxLevel1);
		SkillProfile savedSkillProfile1 = skillProfileRep.save(skillProfile1);
		savedU1.setSkillProfile(savedSkillProfile1);
		userRep.update(savedU1.getId(), savedU1);
		
		System.out.println("nouvelle entrée : " + savedU1);
		
		User u2 = new User("mamaBabet","0000");
		User savedU2 = userRep.save(u2);
		List<Family> list2 = new ArrayList<Family>();
		list2.add(savedF2);
		savedU2.setFamilyList(list2);
		SkillNote menageLevel2 = new SkillNote(savedMenage,4);
		SkillNote cuisineLevel2 = new SkillNote(savedCuisine,5);
		SkillNote parentLevel2 = new SkillNote(savedParent,5);
		SkillNote animauxLevel2 = new SkillNote(savedAnimaux,3);
		SkillNote SavedMenageLevel2 = skillNoteRep.save(menageLevel2);
		SkillNote SavedCuisineLevel2 = skillNoteRep.save(cuisineLevel2);
		SkillNote SavedParentLevel2 = skillNoteRep.save(parentLevel2);
		SkillNote SavedAnimauxLevel2 = skillNoteRep.save(animauxLevel2);
		
		SkillProfile skillProfile2 = new SkillProfile();
		skillProfile2.getSkillNoteList().add(SavedMenageLevel2);
		skillProfile2.getSkillNoteList().add(SavedCuisineLevel2);
		skillProfile2.getSkillNoteList().add(SavedParentLevel2);
		skillProfile2.getSkillNoteList().add(SavedAnimauxLevel2);
		SkillProfile savedSkillProfile2 = skillProfileRep.save(skillProfile2);
		savedU2.setSkillProfile(savedSkillProfile2);
		
		userRep.update(savedU2.getId(), savedU2);
		
		
		System.out.println("nouvelle entrée : " + savedU2);
		
		User u3 = new User("soeurBabet","0000");
		User savedU3 = userRep.save(u3);
		List<Family> list3 = new ArrayList<Family>();
		list3.add(savedF2);
		savedU3.setFamilyList(list3);
		SkillNote menageLevel3 = new SkillNote(savedMenage,3);
		SkillNote cuisineLevel3 = new SkillNote(savedCuisine,2);
		SkillNote parentLevel3 = new SkillNote(savedParent,0);
		SkillNote animauxLevel3 = new SkillNote(savedAnimaux,3);
		SkillNote SavedMenageLevel3 = skillNoteRep.save(menageLevel3);
		SkillNote SavedCuisineLevel3 = skillNoteRep.save(cuisineLevel3);
		SkillNote SavedParentLevel3 = skillNoteRep.save(parentLevel3);
		SkillNote SavedAnimauxLevel3 = skillNoteRep.save(animauxLevel3);
		
		SkillProfile skillProfile3 = new SkillProfile();
		skillProfile3.getSkillNoteList().add(SavedMenageLevel3);
		skillProfile3.getSkillNoteList().add(SavedCuisineLevel3);
		skillProfile3.getSkillNoteList().add(SavedParentLevel3);
		skillProfile3.getSkillNoteList().add(SavedAnimauxLevel3);
		SkillProfile savedSkillProfile3 = skillProfileRep.save(skillProfile3);
		savedU3.setSkillProfile(savedSkillProfile3);
		userRep.update(savedU3.getId(), savedU3);
		
		
		System.out.println("nouvelle entrée : " + savedU3);
		
		
		User u4 = new User("fereBabet","0000");
		User savedU4 = userRep.save(u4);
		List<Family> list4 = new ArrayList<Family>();
		list4.add(savedF2);
		savedU4.setFamilyList(list4);
		SkillNote menageLevel4 = new SkillNote(savedMenage,1);
		SkillNote cuisineLevel4 = new SkillNote(savedCuisine,1);
		SkillNote parentLevel4 = new SkillNote(savedParent,0);
		SkillNote animauxLevel4 = new SkillNote(savedAnimaux,2);
		SkillNote SavedMenageLevel4 = skillNoteRep.save(menageLevel4);
		SkillNote SavedCuisineLevel4 = skillNoteRep.save(cuisineLevel4);
		SkillNote SavedParentLevel4 = skillNoteRep.save(parentLevel4);
		SkillNote SavedAnimauxLevel4 = skillNoteRep.save(animauxLevel4);
		
		SkillProfile skillProfile4 = new SkillProfile();
		skillProfile4.getSkillNoteList().add(SavedMenageLevel4);
		skillProfile4.getSkillNoteList().add(SavedCuisineLevel4);
		skillProfile4.getSkillNoteList().add(SavedParentLevel4);
		skillProfile4.getSkillNoteList().add(SavedAnimauxLevel4);
		SkillProfile savedSkillProfile4 = skillProfileRep.save(skillProfile4);
		savedU4.setSkillProfile(savedSkillProfile4);
		userRep.update(savedU4.getId(), savedU4);
		
		
		System.out.println("nouvelle entrée : " + savedU4);
		
		System.out.println("findAll :");
		System.out.println(userRep.findAll());
		
		
		SkillNote menageLevelNotreCuisine = new SkillNote(savedMenage,5);
		SkillProfile notreCuisineSkillProfile = new SkillProfile();
		SkillNote savedMenageLevelNotreCuisine = skillNoteRep.save(menageLevelNotreCuisine);
		notreCuisineSkillProfile.getSkillNoteList().add(savedMenageLevelNotreCuisine);
		SkillProfile savedNotreCuisineSkillProfile = skillProfileRep.save(notreCuisineSkillProfile);
		Domain notreCuisine = new Domain("Cuisine","Lieu de préaration et consommation des repas",savedF2,savedNotreCuisineSkillProfile);
			
		Domain notreCuisineSaved = domainRep.save(notreCuisine);
		
		SkillProfile petitDejMinimumSkillProfileToDo = new SkillProfile();
		SkillNote savedCuisineLevelPetitDej = skillNoteRep.save(new SkillNote(savedCuisine,2));
		petitDejMinimumSkillProfileToDo.getSkillNoteList().add(savedCuisineLevelPetitDej);
		SkillProfile savedPetitDejMinimumSkillProfileToDo = skillProfileRep.save(petitDejMinimumSkillProfileToDo);
		
		SkillProfile petitDejMinimumSkillProfileToCheck = new SkillProfile();
		SkillNote savedCuisineLevelPetitDejCheck = skillNoteRep.save(new SkillNote(savedCuisine,4));
		petitDejMinimumSkillProfileToCheck.getSkillNoteList().add(savedCuisineLevelPetitDejCheck);
		SkillProfile savedPetitDejMinimumSkillProfileToCheck = skillProfileRep.save(petitDejMinimumSkillProfileToCheck);
		
		
		Task preparerPetitDej = new Task(
				"Préparer le petit déjeuner",
				"Mettre le couvert, couper les fruits, preparer la carafe, le pain, le beurre et les confitures",
				LocalDateTime.of(2022, 9, 1, 20, 52),
				1,
				savedPetitDejMinimumSkillProfileToDo,
				savedPetitDejMinimumSkillProfileToCheck);
		
		Task savedPreparerPetitDej = taskRep.save(preparerPetitDej);
		System.out.println("Nouvelle Tâche ajoutée : " + savedPreparerPetitDej);
		
		
		Rule preparerRepas = new Rule();
		preparerRepas.setName("Preparation des Repas");
		preparerRepas.setDescription("règles et tâches concernantla préparation des repas");
		preparerRepas.getTasksList().add(savedPreparerPetitDej);
		SkillNote savedCuisinePreparerRepasSkillNote = skillNoteRep.save(new SkillNote(savedCuisine,5));
		SkillNote savedParentPreparerRepasSkillNote = skillNoteRep.save(new SkillNote(savedParent,5));
		SkillProfile preparerRepasSkillProfile = new SkillProfile();
		preparerRepasSkillProfile.getSkillNoteList().add(savedParentPreparerRepasSkillNote);
		preparerRepasSkillProfile.getSkillNoteList().add(savedCuisinePreparerRepasSkillNote);
		SkillProfile savedPreparerRepasSkillProfile = skillProfileRep.save(preparerRepasSkillProfile);
		preparerRepas.setSkillProfileMinimumToUpdate(savedPreparerRepasSkillProfile);
		preparerRepas.setDomain(notreCuisineSaved);
		Rule savedPreparerRepas = ruleRep.save(preparerRepas);
		
		System.out.println(savedPreparerRepas);
		
		
		SkillProfile dejMinimumSkillProfileToDo = new SkillProfile();
		SkillNote savedCuisineLevelDej = skillNoteRep.save(new SkillNote(savedCuisine,2));
		dejMinimumSkillProfileToDo.getSkillNoteList().add(savedCuisineLevelDej);
		SkillProfile savedDejMinimumSkillProfileToDo = skillProfileRep.save(dejMinimumSkillProfileToDo);
		
		SkillProfile dejMinimumSkillProfileToCheck = new SkillProfile();
		SkillNote savedCuisineLevelDejCheck = skillNoteRep.save(new SkillNote(savedCuisine,4));
		dejMinimumSkillProfileToCheck.getSkillNoteList().add(savedCuisineLevelDejCheck);
		SkillProfile savedDejMinimumSkillProfileToCheck = skillProfileRep.save(dejMinimumSkillProfileToCheck);
		
		
		Task preparerDej = new Task(
				"Préparer le déjeuner",
				"Mettre le couvert, couper les fruits, preparer la carafe, le pain, le beurre et les confitures",
				LocalDateTime.of(2022, 9, 1, 20, 52),
				1,
				savedDejMinimumSkillProfileToDo,
				savedDejMinimumSkillProfileToCheck);
		
		Task savedPreparerDej = taskRep.save(preparerDej);
		System.out.println("Nouvelle Tâche ajoutée : " + savedPreparerDej);
		

		SkillProfile dinerMinimumSkillProfileToDo = new SkillProfile();
		SkillNote savedCuisineLevelDiner = skillNoteRep.save(new SkillNote(savedCuisine,2));
		dinerMinimumSkillProfileToDo.getSkillNoteList().add(savedCuisineLevelDiner);
		SkillProfile savedDinerMinimumSkillProfileToDo = skillProfileRep.save(dinerMinimumSkillProfileToDo);
		
		SkillProfile dinerMinimumSkillProfileToCheck = new SkillProfile();
		SkillNote savedCuisineLevelDinerCheck = skillNoteRep.save(new SkillNote(savedCuisine,4));
		dinerMinimumSkillProfileToCheck.getSkillNoteList().add(savedCuisineLevelDinerCheck);
		SkillProfile savedDinerMinimumSkillProfileToCheck = skillProfileRep.save(dinerMinimumSkillProfileToCheck);
		
		
		Task preparerDiner = new Task(
				"Préparer le diner",
				"Mettre le couvert, couper les fruits, preparer la carafe, le pain, le beurre et les confitures",
				LocalDateTime.of(2022, 9, 1, 20, 52),
				1,
				savedDinerMinimumSkillProfileToDo,
				savedDinerMinimumSkillProfileToCheck);
		
		Task savedPreparerDiner = taskRep.save(preparerDiner);
		System.out.println("Nouvelle Tâche ajoutée : " + savedPreparerDiner);
		
		SkillProfile nourrirChatMinimumSkillProfileToDo = new SkillProfile();
		SkillNote savedCuisineLevelNourrirChat = skillNoteRep.save(new SkillNote(savedCuisine,2));
		nourrirChatMinimumSkillProfileToDo.getSkillNoteList().add(savedCuisineLevelNourrirChat);
		SkillProfile savedNourrirChatMinimumSkillProfileToDo = skillProfileRep.save(nourrirChatMinimumSkillProfileToDo);
		
		SkillProfile nourrirChatMinimumSkillProfileToCheck = new SkillProfile();
		SkillNote savedCuisineLevelNourrirChatCheck = skillNoteRep.save(new SkillNote(savedCuisine,4));
		nourrirChatMinimumSkillProfileToCheck.getSkillNoteList().add(savedCuisineLevelNourrirChatCheck);
		SkillProfile savedNourrirChatMinimumSkillProfileToCheck = skillProfileRep.save(nourrirChatMinimumSkillProfileToCheck);
		
		
		Task preparerNourrirChat = new Task(
				"Nourrir le Chat",
				"Mettre le couvert, couper les fruits, preparer la carafe, le pain, le beurre et les confitures",
				LocalDateTime.of(2022, 9, 1, 20, 52),
				1,
				savedNourrirChatMinimumSkillProfileToDo,
				savedNourrirChatMinimumSkillProfileToCheck);
		
		Task savedPreparerNourrirChat = taskRep.save(preparerNourrirChat);
		System.out.println("Nouvelle Tâche ajoutée : " + savedPreparerNourrirChat);
	
		SkillProfile laverVitresMinimumSkillProfileToDo = new SkillProfile();
		SkillNote savedCuisineLevelLaverVitres = skillNoteRep.save(new SkillNote(savedCuisine,2));
		laverVitresMinimumSkillProfileToDo.getSkillNoteList().add(savedCuisineLevelLaverVitres);
		SkillProfile savedLaverVitresMinimumSkillProfileToDo = skillProfileRep.save(laverVitresMinimumSkillProfileToDo);
		
		SkillProfile laverVitresMinimumSkillProfileToCheck = new SkillProfile();
		SkillNote savedCuisineLevelLaverVitresCheck = skillNoteRep.save(new SkillNote(savedCuisine,4));
		laverVitresMinimumSkillProfileToCheck.getSkillNoteList().add(savedCuisineLevelLaverVitresCheck);
		SkillProfile savedLaverVitresMinimumSkillProfileToCheck = skillProfileRep.save(laverVitresMinimumSkillProfileToCheck);
		
		
		Task preparerLaverVitres = new Task(
				"Laver les vitres",
				"Mettre le couvert, couper les fruits, preparer la carafe, le pain, le beurre et les confitures",
				LocalDateTime.of(2022, 9, 1, 20, 52),
				1,
				savedLaverVitresMinimumSkillProfileToDo,
				savedLaverVitresMinimumSkillProfileToCheck);
		
		Task savedPreparerLaverVitres = taskRep.save(preparerLaverVitres);
		System.out.println("Nouvelle Tâche ajoutée : " + savedPreparerLaverVitres);
	
		SkillProfile passerVadorEnBasMinimumSkillProfileToDo = new SkillProfile();
		SkillNote savedCuisineLevelPasserVadorEnBas = skillNoteRep.save(new SkillNote(savedCuisine,2));
		passerVadorEnBasMinimumSkillProfileToDo.getSkillNoteList().add(savedCuisineLevelPasserVadorEnBas);
		SkillProfile savedPasserVadorEnBasMinimumSkillProfileToDo = skillProfileRep.save(passerVadorEnBasMinimumSkillProfileToDo);
		
		SkillProfile passerVadorEnBasMinimumSkillProfileToCheck = new SkillProfile();
		SkillNote savedCuisineLevelPasserVadorEnBasCheck = skillNoteRep.save(new SkillNote(savedCuisine,4));
		passerVadorEnBasMinimumSkillProfileToCheck.getSkillNoteList().add(savedCuisineLevelPasserVadorEnBasCheck);
		SkillProfile savedPasserVadorEnBasMinimumSkillProfileToCheck = skillProfileRep.save(passerVadorEnBasMinimumSkillProfileToCheck);
		
		
		Task preparerPasserVadorEnBas = new Task(
				"Passer Aspirateur en bas",
				"Mettre le couvert, couper les fruits, preparer la carafe, le pain, le beurre et les confitures",
				LocalDateTime.of(2022, 9, 1,20,52),
				1,
				savedPasserVadorEnBasMinimumSkillProfileToDo,
				savedPasserVadorEnBasMinimumSkillProfileToCheck);
		
		Task savedPreparerPasserVadorEnBas = taskRep.save(preparerPasserVadorEnBas);
		System.out.println("Nouvelle Tâche ajoutée : " + savedPreparerPasserVadorEnBas);
		
		SkillProfile passerVadorEnHautMinimumSkillProfileToDo = new SkillProfile();
		SkillNote savedCuisineLevelPasserVadorEnHaut = skillNoteRep.save(new SkillNote(savedCuisine,2));
		passerVadorEnHautMinimumSkillProfileToDo.getSkillNoteList().add(savedCuisineLevelPasserVadorEnHaut);
		SkillProfile savedPasserVadorEnHautMinimumSkillProfileToDo = skillProfileRep.save(passerVadorEnHautMinimumSkillProfileToDo);
		
		SkillProfile passerVadorEnHautMinimumSkillProfileToCheck = new SkillProfile();
		SkillNote savedCuisineLevelPasserVadorEnHautCheck = skillNoteRep.save(new SkillNote(savedCuisine,4));
		passerVadorEnHautMinimumSkillProfileToCheck.getSkillNoteList().add(savedCuisineLevelPasserVadorEnHautCheck);
		SkillProfile savedPasserVadorEnHautMinimumSkillProfileToCheck = skillProfileRep.save(passerVadorEnHautMinimumSkillProfileToCheck);
		
		
		Task preparerPasserVadorEnHaut = new Task(
				"Passer Aspirateur en haut",
				"Mettre le couvert, couper les fruits, preparer la carafe, le pain, le beurre et les confitures",
				LocalDateTime.of(2022, 9, 1, 20, 52),
				1,
				savedPasserVadorEnHautMinimumSkillProfileToDo,
				savedPasserVadorEnHautMinimumSkillProfileToCheck);
		
		Task savedPreparerPasserVadorEnHaut = taskRep.save(preparerPasserVadorEnHaut);
		System.out.println("Nouvelle Tâche ajoutée : " + savedPreparerPasserVadorEnHaut);
		
		SkillProfile laverSdBHautMinimumSkillProfileToDo = new SkillProfile();
		SkillNote savedCuisineLevelLaverSdBHaut = skillNoteRep.save(new SkillNote(savedCuisine,2));
		laverSdBHautMinimumSkillProfileToDo.getSkillNoteList().add(savedCuisineLevelLaverSdBHaut);
		SkillProfile savedLaverSdBHautMinimumSkillProfileToDo = skillProfileRep.save(laverSdBHautMinimumSkillProfileToDo);
		
		SkillProfile laverSdBHautMinimumSkillProfileToCheck = new SkillProfile();
		SkillNote savedCuisineLevelLaverSdBHautCheck = skillNoteRep.save(new SkillNote(savedCuisine,4));
		laverSdBHautMinimumSkillProfileToCheck.getSkillNoteList().add(savedCuisineLevelLaverSdBHautCheck);
		SkillProfile savedLaverSdBHautMinimumSkillProfileToCheck = skillProfileRep.save(laverSdBHautMinimumSkillProfileToCheck);
		
		
		Task preparerLaverSdBHaut = new Task(
				"Nettoyer la SdB en haut",
				"Mettre le couvert, couper les fruits, preparer la carafe, le pain, le beurre et les confitures",
				LocalDateTime.of(2022, 9, 1, 20, 52),
				1,
				savedLaverSdBHautMinimumSkillProfileToDo,
				savedLaverSdBHautMinimumSkillProfileToCheck);
		
		Task savedPreparerLaverSdBHaut = taskRep.save(preparerLaverSdBHaut);
		System.out.println("Nouvelle Tâche ajoutée : " + savedPreparerLaverSdBHaut);
	
		
		SkillProfile laverSdBBasMinimumSkillProfileToDo = new SkillProfile();
		SkillNote savedCuisineLevelLaverSdBBas = skillNoteRep.save(new SkillNote(savedCuisine,2));
		laverSdBBasMinimumSkillProfileToDo.getSkillNoteList().add(savedCuisineLevelLaverSdBBas);
		SkillProfile savedLaverSdBBasMinimumSkillProfileToDo = skillProfileRep.save(laverSdBBasMinimumSkillProfileToDo);
		
		SkillProfile laverSdBBasMinimumSkillProfileToCheck = new SkillProfile();
		SkillNote savedCuisineLevelLaverSdBBasCheck = skillNoteRep.save(new SkillNote(savedCuisine,4));
		laverSdBBasMinimumSkillProfileToCheck.getSkillNoteList().add(savedCuisineLevelLaverSdBBasCheck);
		SkillProfile savedLaverSdBBasMinimumSkillProfileToCheck = skillProfileRep.save(laverSdBBasMinimumSkillProfileToCheck);
		
		
		Task preparerLaverSdBBas = new Task(
				"Nettoyer la SdB en bas",
				"Mettre le couvert, couper les fruits, preparer la carafe, le pain, le beurre et les confitures",
				LocalDateTime.of(2022, 9, 1,20, 52),
				1,
				savedLaverSdBBasMinimumSkillProfileToDo,
				savedLaverSdBBasMinimumSkillProfileToCheck);
		
		Task savedPreparerLaverSdBBas = taskRep.save(preparerLaverSdBBas);
		System.out.println("Nouvelle Tâche ajoutée : " + savedPreparerLaverSdBBas);
	
	}
	
}
