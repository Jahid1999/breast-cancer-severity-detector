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
    double total_precision = 0, precision = 0, total_recall = 0, recall = 0, f1_score = 0, total_f1_score = 0;
    double average_accuracy = 0;

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

            average_accuracy = total_accuracy*100/10.0;
            precision = total_precision*100/10.0;
            recall = total_recall*100/10.0;
            f1_score = total_f1_score*100/10.0;

            System.out.println("Accuracy: " + String.format("%.2f", average_accuracy) + " %");
            System.out.println("Precision: " + String.format("%.2f", precision) + " %");
            System.out.println("Recall: " + String.format("%.2f", recall) + " %");
            System.out.println("F1: " + String.format("%.2f", f1_score) + " %");

//            return average_accuracy;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        return average_accuracy;
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
        int[][] confusion_matrix = new int[2][2];

        for(SingleData singleData : testDataSet)
        {
            for (SingleData trainingPoint: trainingDataSet)
            {
                Distance distance = new Distance(singleData,trainingPoint);
                resultPoints.add(distance);
            }
            Collections.sort(resultPoints, new DComparator());

            if(singleData.getCls() == detectClass())
                success++;
            else
                fail++;

            int r,c;
            if(singleData.getCls() == 0)
                r=0;
            else
                r=1;

            if(detectClass() == 0)
                c=0;
            else
                c=1;

            confusion_matrix[r][c]++;

            resultPoints.clear();
        }

        int tp,tn,fp,fn;

        tp = confusion_matrix[0][0];
        tn = confusion_matrix[1][1];
        fp = confusion_matrix[1][0];
        fn = confusion_matrix[0][1];

        double pre = (double)tp/(tp+fp);
        double rec = (double)tp/(tp+fn);
        total_precision += pre;
        total_recall += rec;
        total_f1_score +=(double)(2* pre * rec)/(pre+rec);

       accuracy=(double)success/testDataSet.size();
//       accuracy=(double)(tp+tn)/testDataSet.size();
        return accuracy;
    }

    public int testSeverity (SingleData testPoint)
    {
        for (SingleData trainingPoint: dataSet)
        {
            Distance distance = new Distance(testPoint,trainingPoint);
            resultPoints.add(distance);
        }
        Collections.sort(resultPoints, new DComparator());

        int result = detectClass();
        return result;
    }

    public int detectClass ()
    {
        double benign=0, malignant=0;

        for (int i=0; i<10; i++)
        {
            if (resultPoints.get(i).getClassName() == 0)
                benign +=1.0/(resultPoints.get(i).getDistance()+1);
//                benign++;
            else if (resultPoints.get(i).getClassName() == 1)
                malignant +=1.0/(resultPoints.get(i).getDistance()+1);
//                malignant++;
        }

        if(benign > malignant)
            return 0;
        else
            return 1;
    }

    public double getPrecision() {
        return precision;
    }

    public double getRecall() {
        return recall;
    }

    public double getF1Score() {
        return f1_score;
    }

    public double getAccuracy() {
        return average_accuracy;
    }
}
