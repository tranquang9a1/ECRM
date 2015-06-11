package com.ecrm.Controller;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DAO.ReportDetailDAO;
import com.ecrm.DAO.RoomTypeDAO;
import com.ecrm.DTO.AccountDTO;
import com.ecrm.DTO.ReportDTO;
import com.ecrm.DTO.ResultDTO;
import com.ecrm.Entity.*;
import com.ecrm.Utils.Utils;
import org.omg.Dynamic.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Htang on 6/2/2015.
 */
@Controller
@RequestMapping("/api")
public class APIController {
    @Autowired
    ClassroomDAOImpl classroomDAO;

    @Autowired
    UserDAOImpl userDAO;

    @Autowired
    ReportDAOImpl reportDAO;

    @Autowired
    EquipmentDAOImpl equipmentDAO;

    @Autowired
    ReportDetailDAOImpl reportDetailDAO;

    @Autowired
    EquipmentCategoryDAOImpl equipmentCategoryDAO;
    @RequestMapping(value = "/map")
    public String generateMap(HttpServletRequest request, @RequestParam("id")int classroomId){
        TblClassroomEntity classroomEntity = new TblClassroomEntity();
        classroomEntity = classroomDAO.find(classroomId);
        TblRoomTypeEntity roomTypeEntity = classroomEntity.getTblRoomTypeByRoomTypeId();
        request.setAttribute("ROOMTYPE", roomTypeEntity);
        return "ClassroomMap";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public @ResponseBody AccountDTO login(@RequestParam("username")String username, @RequestParam("password") String password,
                                          HttpServletRequest request) {
        AccountDTO accountDTO = new AccountDTO();

        TblUserEntity userEntity = userDAO.login(username, password);
        if (userEntity != null) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            userDAO.updateLastLogin(username, timestamp);
            accountDTO = Utils.convertFromUserToAccountDTO(userEntity);
        }
        return accountDTO;
    }

    @RequestMapping(value = "/getReportByUsername", method = RequestMethod.GET)
    public @ResponseBody
    List<ReportDTO> getReport(@RequestParam("username") String username) {
        List<TblReportEntity> entities = reportDAO.getReportByUserId(username);
        return Utils.convertFromListEntityToListDTO(entities);
    }

    @RequestMapping(value="/getAllReport", method = RequestMethod.GET)
    public @ResponseBody List<ReportDTO> getAllReport(@RequestParam("limit") int limit, @RequestParam("offset") int offset) {
        List<TblReportEntity> entities = reportDAO.getAllReport(limit, offset);
        return Utils.convertFromListEntityToListDTO(entities);
    }

    @RequestMapping(value="/createReport", method = RequestMethod.POST)
    public @ResponseBody
    ResultDTO createReport( @RequestParam("username") String username,@RequestParam("RoomId") int roomId,
                            @RequestParam("Evaluate") String evaluate, @RequestParam("Description") String desc,
                            @RequestParam("ListDamaged") String listDamaged,
                            @RequestParam("ListPosition") String listPosition,
                            @RequestParam("ListEvaluate") String listEvaluate
                            ) {
        ResultDTO resultDTO = new ResultDTO();

        try {
            TblReportEntity report = new TblReportEntity(username, roomId, evaluate, desc);
            reportDAO.insert(report);
            int reportId = report.getId();

            //equipmentCategoryDAO.findEquipmentId("Bàn");





            String[] equipments = listDamaged.split(",");
            String[] evaluates = listEvaluate.split(",");
            String[] positions = listPosition.split("-");
            List<Integer> listId = new ArrayList<Integer>();

            for(int i = 0; i < equipments.length; i++) {
                TblEquipmentEntity entity = new TblEquipmentEntity(equipmentCategoryDAO.findEquipmentId(equipments[i]),
                        roomId, "Cái gì đây", "", positions[i], 0, "Damaged", 100);
                equipmentDAO.insert(entity);
                TblReportDetailEntity detailEntity = new TblReportDetailEntity(entity.getId(), reportId, evaluates[i], false, positions[i]);
                reportDetailDAO.insert(detailEntity);

            }

            resultDTO.setError_code(100);
            resultDTO.setError("OK");
            return resultDTO;
        }catch (Exception e) {
            resultDTO.setError_code(101);
            resultDTO.setError(e.getMessage());
            return resultDTO;
        }




    }



}
