package mainPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadData
{
    public List<SingleData> points = new ArrayList<SingleData>();


    public List<SingleData> readAllData (File file) throws FileNotFoundException {

        Scanner cin = new Scanner(file);

        while (cin.hasNextLine())
        {
            String temp = cin.nextLine();

            Scanner tempScanner = new Scanner(temp).useDelimiter(",");
            SingleData singleData = new SingleData(tempScanner.nextInt(),
                    tempScanner.nextInt(),tempScanner.nextInt(),tempScanner.nextInt(),
                    tempScanner.nextInt(),tempScanner.nextInt());
            points.add(singleData);

            tempScanner.close();
        }
        cin.close();

        return points;
    }

}
