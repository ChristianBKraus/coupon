package jupiterpa.coupon.domain.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActionRepo extends MongoRepository<ActionEntity,Long>{ }
