package com.ecrm.Service;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DTO.ClassDTO;
import com.ecrm.Entity.TblClassroomEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by QuangTV on 7/22/2015.
 */

@Service
public class ClassroomService {

    @Autowired
    private ClassroomDAOImpl classroomDAO;

    public ClassDTO getClassroom(int classId) {
        TblClassroomEntity entity = classroomDAO.getClassroomById(classId);
        ClassDTO result = new ClassDTO(entity.getId(), entity.getName(), entity.getDamagedLevel());
        return result;
    }
}
