package design_pattern.strategy;

public class GetRequest implements RequestStrategy {
    @Override
    public String getResult() {
        return "get请求到数据";
    }
}
