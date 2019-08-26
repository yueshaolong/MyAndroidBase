package design_pattern.decorator.av.decorator;

public class HamDecorator extends PancakeDecorator{

	public HamDecorator(IPancake pancake) {
		super(pancake);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void cook() {
		System.out.println("��һ�����ȵ�");
		super.cook();
	}
}
