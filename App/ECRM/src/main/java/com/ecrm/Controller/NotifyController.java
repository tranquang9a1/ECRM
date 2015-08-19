package com.ecrm.Controller;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DTO.DamagedRoomDTO;
import com.ecrm.DTO.GroupReportsDTO;
import com.ecrm.DTO.ReportResponseObject;
import com.ecrm.Entity.*;
import com.ecrm.Service.ClassroomService;
import com.ecrm.Service.GroupUser;
import com.ecrm.Service.NotificationService;
import com.ecrm.Service.ReportService;
import com.ecrm.Utils.Enumerable;
import com.ecrm.Utils.Enumerable.MessageType;
import com.ecrm.Utils.Enumerable.NotifyType;
import com.ecrm.Utils.Enumerable.ReportStatus;
import com.ecrm.Utils.SmsUtils;
import com.ecrm.Utils.Utils;
import com.ecrm.Utils.socket.SocketIO;
import com.twilio.sdk.TwilioRestException;
import org.joda.time.LocalTime;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by ChiDNMSE60717 on 6/8/2015.
 */
@Controller
@RequestMapping("/bao-cao")
public class NotifyController {
    @Autowired
    private EquipmentDAOImpl equipmentDAO;
    @Autowired
    private UserNotificationDAOImpl userNotificationDAO;
    @Autowired
    private SystemConfigurationDAOImpl configurationDAO;

    @Autowired
    private ReportService reportService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private NotificationService notificationService;


    @RequestMapping(value = "")
    public String notifications(HttpServletRequest request, @RequestParam(value = "trang", defaultValue = "0", required = false) String page) {
        HttpSession session = request.getSession();
        if (session.getAttribute("USER") == null) {
            return "redirect:/";
        }

        if(!reportService.getListReport(request, page)) {
            return "Error";
        }

        return "staff/ListDamagedRoom";
    }

    @RequestMapping(value = "chi-tiet")
    public String viewReportDetail(HttpServletRequest request, @RequestParam(value = "roomId") int roomId) {
        DamagedRoomDTO resultObject = reportService.getReportDetail(roomId);
        if(resultObject == null) {
            return "redirect:/bao-cao";
        }

        request.setAttribute("DAMAGEDROOM", resultObject);
        return "staff/RoomDetail";
    }

    @RequestMapping(value = "hu-hai")
    public String showReportDetail(HttpServletRequest request, @RequestParam(value = "phong") int roomId) {
        HttpSession session = request.getSession();
        if (session.getAttribute("USER") == null) {
            return "redirect:/";
        }

        List<TblEquipmentEntity> equipments = equipmentDAO.getDamagedEquipments(roomId);
        if (equipments.size() > 0) {
            reportService.getListReport(request, null);
            request.setAttribute("SHOWDETAIL", roomId);

            return "staff/ListDamagedRoom";
        }

        return "redirect:/bao-cao";
    }

    @RequestMapping(value = "sua-chua")
    @Transactional
    public String resolveReport(HttpServletRequest request, @RequestParam(value = "RoomId") int roomId,
                                @RequestParam(value = "ListResolve") String listResolve, @RequestParam(value = "ListRealEquip") String listRealEquip) {
        reportService.resolveRoom(roomId, listResolve, listRealEquip);
        return "redirect:/bao-cao";
    }

    @RequestMapping(value = "sua-het")
    @Transactional
    public String resolveAllReportInRoom(HttpServletRequest request, @RequestParam(value = "roomId") int roomId) {
        reportService.resolveAllEquipment(roomId);
        return "redirect:/bao-cao";
    }

    @RequestMapping(value = "doi-phong")
    @ResponseBody
    public String changeRoom(HttpServletRequest request, @RequestParam("currentClassroom") String currentRoom, @RequestParam("changeClassroom") String changeRoom) throws TwilioRestException {
        return classroomService.changeRoom(currentRoom, changeRoom);
    }

    @RequestMapping(value = "thong-bao")
    public String getListNotification(HttpServletRequest request, @RequestParam(value = "little") boolean isLittle,
                                      @RequestParam(value = "trang", defaultValue = "0", required = false) String page,
                                      @RequestParam(value = "quay-lai", required = false, defaultValue = "") String backLink) {
        return notificationService.getListNotifies(request, page, isLittle, backLink);
    }

    @RequestMapping(value = "danh-muc")
    public String getLeftMenu(HttpServletRequest request){
        HttpSession session = request.getSession();
        TblUserEntity user = (TblUserEntity) session.getAttribute("USER");

        int numberUnreadNotify = userNotificationDAO.getNumberUnreadNotifyOfUser(user.getUsername());
        request.setAttribute("NUMBEROFNOTIFY", numberUnreadNotify);

        return "LeftCategory";
    }

    @RequestMapping(value = "notify")
    public String redirectNotify(HttpServletRequest request, @RequestParam(value = "link") int notifyId){
        return notificationService.redirectNotify(request, notifyId);
    }

    @RequestMapping(value = "danh-sanh-cau-hinh", method = RequestMethod.GET)
    public String systemConfiguration(HttpServletRequest request) {
        List<TblSystemConfiguration> listConfig = configurationDAO.findAll();
        if(listConfig == null) {
            listConfig = new ArrayList<TblSystemConfiguration>();
        }

        request.setAttribute("LISTCONFIG", listConfig);
        return "staff/SystemConfiguration";
    }

    @RequestMapping(value = "cau-hinh", method = RequestMethod.POST)
    @ResponseBody
    public String systemConfiguration(HttpServletRequest request, @RequestParam(value = "key") String key, @RequestParam(value = "value") String value) {
        if(key != null) {
            TblSystemConfiguration config = configurationDAO.find(key);
            if (config != null) {
                if (value != null && config.getValue().equals(value)) {
                    return "Giá trị cập nhật không thay đổi!";
                }

                config.setValue(value);
                configurationDAO.merge(config);

                return "Cập nhật thành công!";
            }
        }

        return "Có lổi trong quá trình xử lý!";
    }
}
