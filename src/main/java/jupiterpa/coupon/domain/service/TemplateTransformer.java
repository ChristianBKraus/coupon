//package jupiterpa.coupon.domain.service;
//
//import org.slf4j.*;
//
//import jupiterpa.coupon.domain.model.*;
//
//public class TemplateTransformer {
//    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
//	private static final Logger logger = LoggerFactory.getLogger(new TemplateTransformer().getClass());
//	
//	public static BalanceEntity transform(BalanceEntity entity) {
//		logger.info(TECHNICAL,"Entity {} should be transformed", entity);
//		BalanceEntity newEntity = new BalanceEntity(entity.getValue()+"T");
//		logger.info(TECHNICAL,"Entity {} was transformed to {}", entity, newEntity);
//		return newEntity;
//	}
//}
