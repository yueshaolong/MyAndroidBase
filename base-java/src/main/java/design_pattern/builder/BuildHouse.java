package design_pattern.builder;

public class BuildHouse implements IBuildHouse {

    private House house;

    public BuildHouse() {
        house = new House();
    }

    @Override
    public void buildBasic() {
        house.setBasic("建造地基!");
    }

    @Override
    public void buildWall() {
        house.setWall("建造墙!");
    }

    @Override
    public void buildRoof() {
        house.setRoof("建造屋顶!");
    }

    @Override
    public House create() {
        return house;
    }
}
