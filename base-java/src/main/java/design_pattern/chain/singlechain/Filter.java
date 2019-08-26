package design_pattern.chain.singlechain;

public abstract class Filter {

    private Filter nextFilter;

    public void setFilter(Filter nextFilter) {
        this.nextFilter = nextFilter;
    }

    public String doFilter(String str){
        //首先调用自己的过滤方法
        String result = handle(str);
        //如果有下一过滤条件，继续调用；
        if (nextFilter != null){
            return nextFilter.doFilter(result);
        }else {
            return result;
        }
    }

    abstract String handle(String str);
}
