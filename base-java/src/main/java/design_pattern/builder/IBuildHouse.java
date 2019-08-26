package design_pattern.builder;

public interface IBuildHouse {
    void buildBasic();
    void buildWall();
    void buildRoof();

    House create();
}
