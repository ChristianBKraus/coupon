package jupiterpa.coupon.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

@Document
public class BalanceEntity {
	
	@Id
	String name;
	int value;
	
	public BalanceEntity() {}
	public BalanceEntity(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Balance of " + name + ": " + value; 
	}
}
