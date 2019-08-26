package design_pattern.decorator.av;

public abstract class Person {
	public String name;
	
	public Person() {
		
	}
	public Person(String name) {
		this.name = name;
	}
	public abstract void show();
	
}
