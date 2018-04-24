package jupiterpa.coupon.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jupiterpa.coupon.domain.model.*;
import jupiterpa.infrastructure.actuator.*;

@Service
public class CouponService {

	@Autowired
	ActionRepo actionRepo;
	@Autowired
	BalanceRepo balanceRepo;
	@Autowired 
	InterfaceHealth health;

	public int withdraw(String name) throws BalanceException {

		BalanceEntity balance = balanceRepo.findByName(name);
		if (balance == null) throw new BalanceException(name + " does not exist");

		ActionEntity action = new ActionEntity(name,-1);
		actionRepo.insert(action);
		
		balance.setValue(balance.getValue() - 1);			
		balanceRepo.save(balance);

//		// Update Health
//		if (success) 
//			health.setHealth(new HealthInfo("TemplateClient",true,"running"));
//		else 
//			health.setHealth(new HealthInfo("TemplateClient",false,"down"));
		
		return balance.getValue();
	}
	
	public int issue(String name, int amount) {
		BalanceEntity balance = balanceRepo.findByName(name);
		if (balance == null) {
			balance = new BalanceEntity(name,amount);
		} else {
			balance.setValue(balance.getValue() + amount);
		}
		balanceRepo.save(balance);
		
		actionRepo.insert( new ActionEntity(name,amount) ); 
		
		return balance.getValue();		
	}
}
