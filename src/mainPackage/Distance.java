package mainPackage;

public class Distance
{
    private double distance;
    private int className;

    private SingleData testPoint;
    private SingleData trainingPoint;

    public Distance(SingleData testPoint, SingleData trainingPoint) {
        this.testPoint = testPoint;
        this.trainingPoint = trainingPoint;

        calcDistance();
    }

    private void calcDistance ()
    {
        distance = Math.sqrt(Math.pow(testPoint.getBdRads()-trainingPoint.getBdRads(),2)+
                Math.pow(testPoint.getAge()-trainingPoint.getAge(),2) +
                Math.pow(testPoint.getShape()-trainingPoint.getShape(),2)+
                Math.pow(testPoint.getMargin()-trainingPoint.getMargin(),2)+
                Math.pow(testPoint.getDensity()-trainingPoint.getDensity(),2));

        className = trainingPoint.getCls();
    }

    public double getDistance() {
        return distance;
    }

    public int getClassName() {
        return className;
    }
}
