package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ReportDAOImpl;
import com.ecrm.DAO.Impl.ReportDetailDAOImpl;
import com.ecrm.DAO.Impl.RoomTypeDAOImpl;
import com.ecrm.Entity.TblReportEntity;
import com.ecrm.Entity.TblRoomTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by ChiDNMSE60717 on 5/30/2015.
 */
@Controller
@RequestMapping("/giang-vien/")
public class UserController {
    @Autowired
    ReportDAOImpl reportDAO;
    @Autowired
    ReportDetailDAOImpl reportDetailDAO;
    @Autowired
    RoomTypeDAOImpl roomTypeDAO;

    @RequestMapping(value = "thong-bao", method = RequestMethod.GET)
    public String notifications(HttpServletRequest request){
        List<TblReportEntity> list = reportDAO.findAll();
        request.setAttribute("NOTIFICATIONS", list);

        TblRoomTypeEntity type = roomTypeDAO.find(1);
        request.setAttribute("ROOMTYPE", type);

        request.setAttribute("ACTIVETAB", "USER_NOTIFY");
        return "user/Notifications";
    }
}
