package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ScheduleConfigDAOImpl;
import com.ecrm.Entity.TblScheduleConfigEntity;
import com.ecrm.Utils.Utils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.Time;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Htang on 7/15/2015.
 */
@Controller
@RequestMapping("staff")
public class ScheduleConfigController {
    @Autowired
    ScheduleConfigDAOImpl scheduleConfigDAO;

    @RequestMapping(value = "scheduleConfig")
    public String scheduleConfig(HttpServletRequest request) {
        List<TblScheduleConfigEntity> tblScheduleConfigEntities = scheduleConfigDAO.findAll();
        Time timeTo = tblScheduleConfigEntities.get(0).getTimeTo();
        Time timeFrom = tblScheduleConfigEntities.get(0).getTimeFrom();
        long duration = timeTo.getTime()-timeFrom.getTime();
        duration = duration/60000;
        request.setAttribute("LIST", tblScheduleConfigEntities);
        request.setAttribute("TABCONTROL", "STAFF_SCHEDULECONFIG");
        request.setAttribute("SIZE", tblScheduleConfigEntities.size() + 1);
        request.setAttribute("DURATION", duration);

        return "Staff_ScheduleConfig";
    }

    @RequestMapping(value = "updateScheduleConfig")
    public String updateScheduleConfig(HttpServletRequest request, @RequestParam("config") String config, @RequestParam("size") int size) throws IOException, InvalidFormatException {
        String[] array = config.split("-");
        for (int i = 0; i < array.length; i++) {
            String[] a = array[i].split(":");
            if (a.length == 2) {
                array[i] = array[i] + ":00";
            }
        }
        int j = 0;
        for (int i = 1; i <= size; i++) {
            List<TblScheduleConfigEntity> scheduleConfigEntities = scheduleConfigDAO.findById(i);
            if (scheduleConfigEntities != null) {
                TblScheduleConfigEntity scheduleConfigEntity = scheduleConfigEntities.get(0);
                scheduleConfigEntity.setSlot(i);
                scheduleConfigEntity.setTimeFrom(java.sql.Time.valueOf(array[j]));
                scheduleConfigEntity.setTimeTo(java.sql.Time.valueOf(array[j + 1]));
                scheduleConfigDAO.merge(scheduleConfigEntity);
                j += 2;
            } else {
                TblScheduleConfigEntity scheduleConfigEntity = new TblScheduleConfigEntity();
                scheduleConfigEntity.setId(i);
                scheduleConfigEntity.setSlot(i);
                scheduleConfigEntity.setTimeFrom(java.sql.Time.valueOf(array[j]));
                scheduleConfigEntity.setTimeTo(java.sql.Time.valueOf(array[j + 1]));
                scheduleConfigDAO.persist(scheduleConfigEntity);
                j += 2;
            }
        }
        if(size>0){
            //create file exel
            String filePath = "";
            ServletContext servletContext = request.getSession().getServletContext();
            filePath = servletContext.getRealPath("Schedule_Sample.xlsx");
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            //Get the workbook instance for XLS file
            Workbook workbook = WorkbookFactory.create(fileInputStream);

            //Get first sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);
            CellStyle cellStyle = createBorderedStyle(workbook);
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 1;
            j = 0;
            List<TblScheduleConfigEntity> tblScheduleConfigEntities = scheduleConfigDAO.findAll();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell = null;
                if (row.getRowNum() > 4) {
                    cell = sheet.getRow(row.getRowNum()).getCell(1);
                    if(cell==null){
                        cell = row.createCell(1);
                    }
                    if (j <= size - 1) {
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(tblScheduleConfigEntities.get(j).getTimeFrom() + " - " + tblScheduleConfigEntities.get(j).getTimeTo());
                    } else {
                        row.removeCell(cell);
                    }
                    j += 1;
                }
                i += 1;
            }
            if (j <= size) {
                for (int k = j; k < size; k++) {
                    Row row = workbook.getSheetAt(0).createRow(i);
                    Cell cell = row.createCell(1);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(tblScheduleConfigEntities.get(k).getTimeFrom() + " - " + tblScheduleConfigEntities.get(k).getTimeTo());
                    i += 1;
                }
            }
            sheet.addMergedRegion(CellRangeAddress.valueOf("A6:A12"));
            fileInputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        }

        return "redirect:/staff/scheduleConfig";
    }

    private static CellStyle createBorderedStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }
}
