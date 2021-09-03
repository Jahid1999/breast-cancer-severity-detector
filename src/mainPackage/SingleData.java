package mainPackage;

public class SingleData
{
    private int cls;
    private int bd_rads;
    private int age;
    private int shape;
    private int margin;
    private int density;

    public SingleData() {
    }

    public SingleData(int bd_rads,int age, int shape, int margin, int density, int cls) {
        this.cls = cls;
        this.bd_rads = bd_rads;
        this.age = age;
        this.shape = shape;
        this.margin = margin;
        this.density = density;
    }

    public void setBdRads(int bd_rads) {
        this.bd_rads = bd_rads;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public void setDensity(int density) {
        this.density = density;
    }

    public void setClassName(int className) {
        this.cls = className;
    }

    public int getBdRads() {
        return bd_rads;
    }
    public int getAge() {
        return age;
    }

    public int getShape() {
        return shape;
    }

    public int getDensity() {
        return density;
    }

    public int getMargin() {
        return margin;
    }
    public int getCls() {
        return cls;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "BD-Rads=" + bd_rads +
                ", Age=" + age +
                ", Shape=" + shape +
                ", Margin=" + margin +
                ", Density=" + density +
                '}';
    }
}
