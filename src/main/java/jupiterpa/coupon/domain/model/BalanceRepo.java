package jupiterpa.coupon.domain.model;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BalanceRepo extends MongoRepository<BalanceEntity,String>{ 
	public BalanceEntity findByName(String name);
	public List<BalanceEntity> findAllByOrderByName();
}
