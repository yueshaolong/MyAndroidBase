package design_pattern.builder;

public class Builder {
    public House build(IBuildHouse buildHouse){
        buildHouse.buildBasic();
        buildHouse.buildWall();
        buildHouse.buildRoof();
        return buildHouse.create();
    }
}
