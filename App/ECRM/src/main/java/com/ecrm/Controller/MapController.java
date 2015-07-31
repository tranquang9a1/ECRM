package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblRoomTypeEntity2;
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
        TblRoomTypeEntity2 roomTypeEntity = classroomEntity.getTblRoomType2ByRoomTypeId2();
        request.setAttribute("ROOMTYPE", roomTypeEntity);
        return "ClassroomMap";
    }
}
