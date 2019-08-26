package design_pattern.decorator.av.decorator;

public class EggDecorator extends PancakeDecorator{

	public EggDecorator(IPancake pancake) {
		super(pancake);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cook() {
		System.out.println("��һ��������");
		super.cook();
	}
}
