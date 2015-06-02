package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.RoomTypeDAOImpl;
import com.ecrm.DAO.RoomTypeDAO;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblRoomTypeEntity;
import org.omg.Dynamic.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Htang on 6/2/2015.
 */
@Controller
@RequestMapping("/map")
public class MapController {
    @Autowired
    ClassroomDAOImpl classroomDAO;
    @RequestMapping(value = "generate")
    public String generateMap(HttpServletRequest request, @RequestParam("classroomId")int classroomId){
        TblClassroomEntity classroomEntity = new TblClassroomEntity();
        classroomEntity = classroomDAO.find(classroomId);
        TblRoomTypeEntity roomTypeEntity = classroomEntity.getTblRoomTypeByRoomTypeId();
        request.setAttribute("ROOMTYPE", roomTypeEntity);
        return "ClassroomMap";
    }
}
