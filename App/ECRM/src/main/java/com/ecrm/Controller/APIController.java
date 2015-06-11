package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.ReportDAOImpl;
import com.ecrm.DAO.Impl.RoomTypeDAOImpl;
import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.DAO.RoomTypeDAO;
import com.ecrm.DTO.AccountDTO;
import com.ecrm.DTO.ReportDTO;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblReportEntity;
import com.ecrm.Entity.TblRoomTypeEntity;
import com.ecrm.Entity.TblUserEntity;
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
}
