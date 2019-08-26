package design_pattern.decorator.av;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SinglePerson pFinery = new SinglePerson("AV");
		RShoes rShoes = new RShoes(pFinery);
		TShirts shirts = new TShirts(rShoes);
		shirts.show();

		new TShirts(new RShoes(new SinglePerson("ad"))).show();
	}

}
