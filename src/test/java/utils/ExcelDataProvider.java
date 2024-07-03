package utils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ExcelDataProvider {

   /* @DataProvider(name="test1data")
    public static Object[][] getData(){
        String excelPath =".//src//test//java//excel//testdata.xlsx";
        Object data[][]= testData(excelPath,"Sheet1");
        return data;
    }*/
    public static Object[][] testData(String excelPath,String sheetName){
        ExcelUtils excel = new ExcelUtils(excelPath,sheetName);
        int rowCount =excel.getRowcount();
        int colCount =excel.getColcount();

        Object data1 [][] = new Object[rowCount-1][colCount];

        for(int i=1;i<rowCount;i++){
            for(int j=0;j<colCount;j++){
              String  cellData=excel.getCelldataString(i,j);
               // System.out.print(cellData+" | ");
                data1[i-1][j]=cellData;
            }
            System.out.println();
        }
        return data1;
    }
}
