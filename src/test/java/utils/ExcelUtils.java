package utils;

import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
    static XSSFWorkbook workbook;
    static XSSFSheet sheet;
    public ExcelUtils(String excelPath,String sheetName){
        try{
            workbook = new XSSFWorkbook(excelPath);
            sheet = workbook.getSheet(sheetName);
        }catch (Exception exp){

        }
    }

    public static void main(String[] args) {
        getRowcount();
        getCelldataString(0,0);
        getCellDataNumber(1,1);
    }

    public static int getRowcount() {
        int rowCount=0;
        try {
            rowCount = sheet.getPhysicalNumberOfRows();
            System.out.println("No of rows:" + rowCount);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
        return rowCount;
    }

    public static String getCelldataString(int rowNum,int cellNum) {
        //String cellData=null;
        Object cellData = null;
        try {
            DataFormatter formatter = new DataFormatter();
            cellData = formatter.formatCellValue(sheet.getRow(rowNum).getCell(cellNum));
            //cellData = sheet.getRow(rowNum).getCell(cellNum).getStringCellValue();
            //System.out.println(cellData);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
        return (String) cellData;
    }
    public static double getCellDataNumber(int rowNum, int cellNum) {
        double cellData=0;
        try {
             cellData = sheet.getRow(rowNum).getCell(cellNum).getNumericCellValue();
            //System.out.println(cellData);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
        return cellData;
    }
    public static int getColcount() {
        int colCount=0;
        try {
            colCount = sheet.getRow(0).getPhysicalNumberOfCells();
            System.out.println("No of columns:" + colCount);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
        return colCount;
    }
}

