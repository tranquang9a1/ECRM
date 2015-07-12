package com.ecrm.Controller;

import com.ecrm.DAO.Impl.CategoryDAOImpl;
import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.ReportDAOImpl;
import com.ecrm.DAO.Impl.ReportDetailDAOImpl;
import com.ecrm.Entity.*;
import com.google.gson.Gson;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Htang on 7/6/2015.
 */
@Controller
@RequestMapping("/staff")
public class StatisticController {
    @Autowired
    ClassroomDAOImpl classroomDAO;
    @Autowired
    CategoryDAOImpl categoryDAO;
    @Autowired
    ReportDetailDAOImpl reportDetailDAO;
    @Autowired
    ReportDAOImpl reportDAO;
    @RequestMapping(value = "statistic")
    public String statistic(HttpServletRequest request, @RequestParam( value = "time") int time,@RequestParam(value = "category") int category ){
        LocalDate localDate = new LocalDate();
        Gson gson = new Gson();
        List<String> lstJson = new ArrayList<String>();
        List<StatisticEntity> statisticEntities = new ArrayList<StatisticEntity>();
        String text = "";
        String legendText = "";
        if(category==0){
            legendText = "Phòng";
            text="10 Phòng Học Được Báo Cáo";
            List<TblClassroomEntity> tblClassroomEntities = new ArrayList<TblClassroomEntity>();
                tblClassroomEntities = classroomDAO.getAllClassroom();
            for(TblClassroomEntity classroomEntity:tblClassroomEntities){
                StatisticEntity statisticEntity  = new StatisticEntity();
                List<TblReportEntity> tblReportEntities = new ArrayList<TblReportEntity>();
                if(time == 0){
                    tblReportEntities = reportDAO.findAllReportInOneMonth(classroomEntity.getId());
                    text.concat(" Trong Tháng");
                }else{
                    tblReportEntities = classroomEntity.getTblReportsById();
                }
                if(!tblReportEntities.isEmpty()){
                    statisticEntity.setY(tblReportEntities.size());
                }
                statisticEntity.setLabel(classroomEntity.getName());
                statisticEntities.add(statisticEntity);
            }
        }else{
            text="Thiết Bị Được Báo Cáo";
            legendText = "Thiết Bị";
            List<TblEquipmentCategoryEntity> tblEquipmentCategoryEntities = categoryDAO.findAll();
            for(int i = 0 ; i<tblEquipmentCategoryEntities.size(); i++){
                StatisticEntity statisticEntity = new StatisticEntity();
                statisticEntity.setLabel(tblEquipmentCategoryEntities.get(i).getName());
                statisticEntities.add(i, statisticEntity);
            }
            List<TblReportDetailEntity> tblReportDetailEntities = new ArrayList<TblReportDetailEntity>();
            if(time ==0){
                text.concat(" Trong Tháng");
                tblReportDetailEntities = reportDetailDAO.findAllReportDTInOneMonth();
            }else{
                tblReportDetailEntities = reportDetailDAO.findAll();
            }
            for(TblReportDetailEntity tblReportDetailEntity: tblReportDetailEntities){
                for(StatisticEntity statisticEntity: statisticEntities){
                    if(tblReportDetailEntity.getTblEquipmentByEquipmentId().getTblEquipmentCategoryByCategoryId().getName().equals(statisticEntity.getLabel())){
                        int y = statisticEntity.getY();
                        statisticEntity.setY(y+1);
                        break;
                    }
                }
            }
        }
        Collections.sort(statisticEntities, new Comparator<StatisticEntity>() {
            @Override
            public int compare(StatisticEntity o1, StatisticEntity o2) {
                return o2.getY()- o1.getY();
            }
        });

        int i = 0;
        for(StatisticEntity statisticEntity: statisticEntities){
            String json = gson.toJson(statisticEntity);
            lstJson.add(json);
            i+=1;
            if(i>9){
                break;
            }
        }
        request.setAttribute("STATISTIC",lstJson);
        request.setAttribute("TEXT",text);
        request.setAttribute("LEGENDTEXT",legendText);
        request.setAttribute("TIME",time);
        request.setAttribute("CATEGORY",category);
        return "Staff_Statistic";
    }

}
