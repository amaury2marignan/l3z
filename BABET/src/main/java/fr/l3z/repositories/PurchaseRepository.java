package fr.l3z.repositories;


import java.util.List;

import fr.l3z.models.Purchase;


public interface PurchaseRepository extends GenericRepository<Long, Purchase> {
	List<Purchase> findModels();

	List<Purchase> findByStatus(int i);

	List<Purchase> findPurchasesToDo();

	


}