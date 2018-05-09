package jupiterpa.coupon.domain.model;

import org.springframework.data.mongodb.core.mapping.*;

@Document
public class ActionEntity {
	
	String name;
	int value;
	
	public ActionEntity() {}
	public ActionEntity(String name, int value) {
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
		return "Withdrawal of " + name + ": " + value; 
	}
}
