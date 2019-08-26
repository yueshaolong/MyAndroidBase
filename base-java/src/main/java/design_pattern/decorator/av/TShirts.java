package design_pattern.decorator.av;

public class TShirts extends Decorator{

	public TShirts(Person finery) {
		super(finery);
		// TODO Auto-generated constructor stub
	}
	
	public void show(){
		super.show();
		System.out.println("穿 Tshirts");
	}

}
