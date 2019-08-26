package design_pattern.chain.singlechain;

public class Test {
    public static void main(String[] args) {
        Filter htmlFilter = new HTMLFilter();
        Filter sensitiveFilter = new SensitiveFilter();
        Filter faceFilter = new FaceFilter();

        htmlFilter.setFilter(sensitiveFilter);
        sensitiveFilter.setFilter(faceFilter);

        String msg = ":):,<script>,敏感,被就业,网络授课";
        String result = htmlFilter.doFilter(msg);
        System.out.println("result："+result);
    }
}
