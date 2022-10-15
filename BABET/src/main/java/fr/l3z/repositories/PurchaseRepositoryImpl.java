package fr.l3z.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.l3z.models.SkillNote;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.Purchase;

@ApplicationScoped
@Stateless
public class PurchaseRepositoryImpl implements PurchaseRepository {

	
	@PersistenceContext
	private EntityManager entityManager;

	public PurchaseRepositoryImpl() {

	}

	@Override
	public Purchase save(Purchase t) {
		entityManager.persist(t);
		return t;
	}

	@Override
	public Purchase find(Long id) {
		return entityManager.createQuery("select u from Purchase u where u.id = :purchaseIdParam", Purchase.class)
				.setParameter("purchaseIdParam", id).getSingleResult();
	}

	@Override
	public Purchase findByObject(Purchase t) {
		Purchase purchase = entityManager.find(Purchase.class, t.getId());
		return purchase;
	}

	@Override
	public List<Purchase> findAll() {
		return entityManager.createQuery("select u from Purchase u", Purchase.class).getResultList();

	}

	@Override
	public void delete(Long id) {
		entityManager.remove(entityManager.find(Purchase.class, id));

	}
	@Override
	public List<Purchase> findByStatus(int status){
		return entityManager.createQuery("select u from Purchase u where u.status = :purchaseIdParam", Purchase.class)
				.setParameter("purchaseIdParam", status).getResultList();
	}
	
	@Override
	public List<Purchase> findPurchasesToDo(){
		return entityManager.createQuery("select u from Purchase u where u.status = 1", Purchase.class)
				.getResultList();
	}

	@Override
	public void deleteByObject(Purchase t) {
		entityManager.remove(entityManager.find(Purchase.class, t.getId()));

	}

	@Override
	public void update(Long idAModifier, Purchase t) {
		Purchase purchaseAModifier = entityManager.find(Purchase.class, idAModifier);
		purchaseAModifier.setDescription(t.getDescription());
		purchaseAModifier.setStatus(t.getStatus());
		purchaseAModifier.setWhoDidIt(t.getWhoDidIt());


		entityManager.merge(purchaseAModifier);

	}



	@Override
	public List<Purchase> findModels() {
		
		return entityManager.createQuery("select u from Purchase u where u.status = 0", Purchase.class)
				.getResultList();
	}


}
