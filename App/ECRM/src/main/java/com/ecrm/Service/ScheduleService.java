package com.ecrm.Service;

import com.ecrm.DAO.Impl.ScheduleDAOImpl;
import com.ecrm.DTO.ScheduleDTO;
import com.ecrm.Entity.TblScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 7/22/2015.
 */

@Service
public class ScheduleService {

    @Autowired
    private ScheduleDAOImpl scheduleDAO;

    public boolean checkSchedule(int classId) {
        try {
            List<TblScheduleEntity> result = scheduleDAO.getScheduleNoFinishOfRoom(classId);
            if (result.size() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ScheduleDTO> getAllSchedule(String username) {
        List<ScheduleDTO> result = new ArrayList<ScheduleDTO>();
        List<TblScheduleEntity> listSchedule = scheduleDAO.getAllSchedulesOfUser(username);
        for (TblScheduleEntity scheduleEntity : listSchedule) {
            ScheduleDTO dto = new ScheduleDTO();
            dto.setClassId(scheduleEntity.getClassroomId());
            dto.setClassName(scheduleEntity.getTblClassroomByClassroomId().getName());
            dto.setTimeFrom(scheduleEntity.getTblScheduleConfigByScheduleConfigId().getTimeFrom().getTime() + "");
            dto.setTimeTo(scheduleEntity.getTblScheduleConfigByScheduleConfigId().getTimeTo().getTime() + "");
            dto.setDate(scheduleEntity.getDate() + "");
            result.add(dto);
        }
        return result;
    }
}
