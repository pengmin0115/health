package com.apple;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * @author pengmin
 * @date 2020/10/15 0:14
 */
public class ExcelTest {

    //@Test
    public void testPOI() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\pengmin\\Desktop\\ordersetting_template.xlsx");
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);
        for (Cell cell : row) {
            String value = cell.getStringCellValue();
            System.out.println(value);
        }
        workbook.close();
    }

    //@Test
    public void testPOI02() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\pengmin\\Desktop\\ordersetting_template.xlsx");
        XSSFSheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 1; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            // short lastCellNum = row.getLastCellNum();
            double cellValue = row.getCell(1).getNumericCellValue();
            System.out.println(cellValue);
           /* for (int j = 0; j < lastCellNum; j++) {
                if (j == 0) {
                    String cellValue = row.getCell(j).getStringCellValue();
                    System.out.println(cellValue);
                } else {
                    double numericCellValue = row.getCell(j).getNumericCellValue();
                    System.out.println(numericCellValue);
                }
            }*/
        }
        workbook.close();
    }

    //@Test
    public void createExcel() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("POI测试");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("年龄");

        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("小明");
        row1.createCell(2).setCellValue("10");

        XSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("2");
        row2.createCell(1).setCellValue("小王");
        row2.createCell(2).setCellValue("20");

        FileOutputStream out = new FileOutputStream("C:\\Users\\pengmin\\Desktop\\POITest.xlsx");
        workbook.write(out);
        out.flush();//刷新
        out.close();//关闭
        workbook.close();
    }
}
