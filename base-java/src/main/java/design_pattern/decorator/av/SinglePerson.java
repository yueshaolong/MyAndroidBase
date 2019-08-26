package design_pattern.decorator.av;

public class SinglePerson extends Person {

	public SinglePerson(String name) {
		super(name);
	}
	
	@Override
	public void show() {
		System.out.println("光人");
		
	}

	

}
