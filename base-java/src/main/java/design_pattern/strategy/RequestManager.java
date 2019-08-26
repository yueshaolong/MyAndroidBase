package design_pattern.strategy;

public class RequestManager {
    private RequestStrategy requestStrategy;

    public RequestManager(RequestStrategy requestStrategy) {
        this.requestStrategy = requestStrategy;
    }

    public RequestStrategy getRequestStrategy() {
        return requestStrategy;
    }

    public void setRequestStrategy(RequestStrategy requestStrategy) {
        this.requestStrategy = requestStrategy;
    }

    public String getResult(){
        return requestStrategy.getResult();
    }
}
