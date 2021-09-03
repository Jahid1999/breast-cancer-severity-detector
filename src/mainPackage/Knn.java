package mainPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Knn {
    private List<SingleData> trainingDataSet = new ArrayList<SingleData>();
    private List<SingleData> testDataSet = new ArrayList<SingleData>();
    public List<SingleData> dataSet = new ArrayList<SingleData>();
    List<Distance> resultPoints = new ArrayList<>();

    public void run() {
        try {

            double total_accuracy = 0;

            ReadData readData = new ReadData();
            dataSet = readData.readAllData(new File("src/mammographic_masses.data"));
            Collections.shuffle(dataSet);

            for (int i=0; i<10; i++) {
                makeTrainingAndTestDataSet(i);
                total_accuracy += doKNN() ;
            }

            System.out.println("Accuracy: " + String.format("%.2f", total_accuracy*100/10.0) + " %");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void makeTrainingAndTestDataSet(int turn) {

        int size = dataSet.size();
        int setSize = size/10;

        for (int i=0; i<size; i++) {
            if ( i >= turn * setSize && i < (turn+1) * setSize ) {
                testDataSet.add(dataSet.get(i));
            }
            else {
                trainingDataSet.add(dataSet.get(i));
            }
        }
    }

    public double doKNN ()
    {
        int success = 0;
        int fail = 0;
        double accuracy = 0;

        for(SingleData singleData : testDataSet)
        {
            for (SingleData trainingPoint: trainingDataSet)
            {
                Distance distance = new Distance(singleData,trainingPoint);
                resultPoints.add(distance);
            }
            Collections.sort(resultPoints, new DComparator());

            if(singleData.getCls() == calcClassName())
                success++;
            else
                fail++;

            resultPoints.clear();
        }

       accuracy=(double)success/testDataSet.size();
        return accuracy;
    }

    public int testSeverity (SingleData testPoint)
    {
        int success = 0;
        int fail = 0;
        double accuracy = 0;

        for (SingleData trainingPoint: dataSet)
        {
            Distance distance = new Distance(testPoint,trainingPoint);
            resultPoints.add(distance);
        }
        Collections.sort(resultPoints, new DComparator());

        int result = calcClassName();
        return result;
    }

    public int calcClassName ()
    {
        double benign=0, malignant=0;

        for (int i=0; i<15; i++)
        {
            if (resultPoints.get(i).getClassName() == 0)
                benign ++;
            else if (resultPoints.get(i).getClassName() == 1)
                malignant ++;
        }

        if(benign > malignant)
            return 0;
        else
            return 1;
    }
}
