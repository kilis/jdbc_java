package com.company;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by Kristaps on 16.05.2015.
 * Pieslegsanas Excel tabulai izmantojot Apache POI driveri
 */
class excell {
    /**
     * @throws IOException
     * Pieslegsanas excel failam izmantojot java 8 Apache POI connectoru
     */
    public void readExcelFile() {
        //noinspection TryWithIdenticalCatches,TryWithIdenticalCatches
        try {
        // Atever xlxs failu java
        File excel = new File("A:/automasinas.xlsx");
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook book = new XSSFWorkbook(fis);
            XSSFSheet sheet = book.getSheetAt(0);

            // Iziet cauri excell failam 
            for (Row row : sheet) {
                // Iziet cauri katrai excell faila ðunai
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t");
                            break;
                        default:

                    }
                }
                System.out.println("");
            }

            // Raksta datus xlxs tabula
            Map<String, Object[]> newData = new HashMap<>();
            newData.put("4", new Object[] { 4d, "Audi", "RS6", "14.03.2016",
                    "JZ-123t" });
            newData.put("5", new Object[] { 5d, "VW", "JETTA", "16.03.2013",
                    "PT-491" });
            newData.put("6", new Object[] { 6d, "VW", "Multivan", "10.01.2015",
                    "VV-252" });

            Set<String> newRows = newData.keySet();
            int rownum = sheet.getLastRowNum();
            //Nosaka ðunu vertibas tipu
            for (String key : newRows) {
                Row row = sheet.createRow(rownum++);
                Object[] objArr = newData.get(key);
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof Boolean) {
                        cell.setCellValue((Boolean) obj);
                    } else if (obj instanceof Date) {
                        cell.setCellValue((Date) obj);
                    } else if (obj instanceof Double) {
                        cell.setCellValue((Double) obj);
                    }
                }
            }

            //Atver izvadi, lai saglabatu ierakstititos datus excell faila
            FileOutputStream os = new FileOutputStream(excel);
            book.write(os);
            System.out.println("Ieraksitit Excell faila izdevas ...");
            //Aizver lapu, izejoðo ievadi un excell lai nepielautu faila nopludi
            os.close();
            book.close();
            fis.close();

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}