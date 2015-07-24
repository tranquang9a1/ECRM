package com.ecrm.Service;

import com.ecrm.DAO.Impl.NotificationDAOImp;
import com.ecrm.DAO.Impl.UserNotificationDAOImpl;
import com.ecrm.Entity.TblNotificationEntity;
import com.ecrm.Entity.TblRoleEntity;
import com.ecrm.Entity.TblUserEntity;
import com.ecrm.Entity.TblUserNotificationEntity;
import com.ecrm.Utils.Enumerable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by user on 7/24/2015.
 */

@Service
public class NotificationService {
    @Autowired
    private UserNotificationDAOImpl userNotificationDAO;
    @Autowired
    private NotificationDAOImp notificationDAO;

    public String getListNotifies(HttpServletRequest request, String page, boolean isLittle, String backLink) {
        HttpSession session = request.getSession();
        TblUserEntity user = (TblUserEntity) session.getAttribute("USER");

        int numberUnreadNotify = userNotificationDAO.getNumberUnreadNotifyOfUser(user.getUsername());
        request.setAttribute("NUMBEROFNOTIFY", numberUnreadNotify);

        int size = 5;
        int pageNumber = 0;
        try {
            pageNumber = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            request.setAttribute("MESSAGE", "Đường dẫn không tồn tại trong hệ thống!");
            return "Error";
        }

        if(isLittle) {
            pageNumber++;

            List<TblUserNotificationEntity> listNotify = userNotificationDAO.getNotificationByUser(user.getUsername(), pageNumber, size);
            request.setAttribute("LISTNOTIFY", listNotify);
        } else {
            pageNumber = (pageNumber == 0 ? 1 : pageNumber);

            List<TblUserNotificationEntity> listUnread = userNotificationDAO.getUnreadNotifyOfUser(user.getUsername());
            request.setAttribute("UNREADNOTIFYS", listUnread);

            List<TblUserNotificationEntity> listRead = userNotificationDAO.getReadNotifyOfUser(user.getUsername(), pageNumber, size);
            request.setAttribute("READNOTIFYS", listRead);

            if(!"".equals(backLink)) {
                request.setAttribute("BACKLINK", backLink);
            }
        }

        return "ListNotifies";
    }

    public String redirectNotify(HttpServletRequest request, int notifyId) {
        HttpSession session = request.getSession();
        TblUserEntity user = (TblUserEntity) session.getAttribute("USER");
        TblRoleEntity role = user.getTblRoleByRoleId();

        TblUserNotificationEntity userNotification = userNotificationDAO.find(notifyId);
        if(userNotification != null) {
            if(!userNotification.isStatus()) {
                userNotification.setStatus(true);
                userNotificationDAO.merge(userNotification);
            }
            return "redirect:" + userNotification.getTblNotificationById().getRedirectLink();
        }

        if("Teacher".equals(role.getName())) {
            return "redirect:/giang-vien";
        } else if("Staff".equals(role.getName())) {
            return "redirect:/thong-bao";
        } else {
            return "redirect:/admin/account";
        }
    }

    public void changeNotificationStatus(int roomId) {
        List<TblNotificationEntity> notifies = notificationDAO.getActiveNotifyOfRoom(roomId, Enumerable.MessageType.NEWREPORT.getValue());
        for (TblNotificationEntity notify: notifies) {
            if(!notify.isStatus()) {
                notify.setStatus(true);
                notificationDAO.merge(notify);
            }

            List<TblUserNotificationEntity> listUserNotifies = notify.getTblUserNotificationById();
            for (TblUserNotificationEntity userNotify: listUserNotifies) {
                if(!userNotify.isStatus()) {
                    userNotify.setStatus(true);
                    userNotificationDAO.merge(userNotify);
                }
            }
        }
    }
}
