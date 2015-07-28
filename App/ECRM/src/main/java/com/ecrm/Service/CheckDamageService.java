package com.ecrm.Service;

import com.ecrm.DAO.Impl.ReportDAOImpl;
import com.ecrm.DAO.Impl.ReportDetailDAOImpl;
import com.ecrm.Entity.*;
import com.ecrm.Utils.Enumerable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by QuangTV on 7/22/2015.
 */

@Service
public class CheckDamageService {

    @Autowired
    private ReportDetailDAOImpl reportDetailDAO;

    public int checkDamagedLevel(List<TblEquipmentEntity> damagedEquipment, TblClassroomEntity tblClassroomEntity) {
        int damagedLevel = 0;
        TblRoomTypeEntity2 roomTypeEntity = tblClassroomEntity.getTblRoomType2ByRoomTypeId2();
        int chair = roomTypeEntity.getSlots();
        String[] row = roomTypeEntity.getHorizontalRows().split("-");
        int table = 0;
        for (int i = 0; i < row.length; i++) {
            table += Integer.parseInt(row[i]);
        }

        if (!damagedEquipment.isEmpty()) {
            for (TblEquipmentEntity tblEquipmentEntity : damagedEquipment) {
                List<TblReportDetailEntity> equipments = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                for (TblReportDetailEntity equipment : equipments) {
                    String categoryName = tblEquipmentEntity.getTblEquipmentCategoryByCategoryId().getName();
                    if (!categoryName.equals("Bàn") ||
                            !categoryName.equals("Ghế")) {
                        Collection<TblEquipmentQuantityEntity> tblEquipmentQuantityEntities = equipment.getTblEquipmentByEquipmentId().getTblEquipmentCategoryByCategoryId().getTblEquipmentQuantityById();
                        for (TblEquipmentQuantityEntity tblEquipmentQuantityEntity : tblEquipmentQuantityEntities) {
                            if (roomTypeEntity.getId() == tblEquipmentQuantityEntity.getRoomTypeId()) {

                                if (equipment.getTblEquipmentByEquipmentId().getCategoryId() == tblEquipmentQuantityEntity.getEquipmentCategoryId()) {
                                    int priority = tblEquipmentQuantityEntity.getPriority();
                                    if (priority == 3) {
                                        if (equipment.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
                                            damagedLevel += 20;
                                        } else if (equipment.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
                                            damagedLevel += 30;
                                        } else if (equipment.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
                                            damagedLevel += 50;
                                        } else {
                                            damagedLevel += 50;
                                        }
                                    }
                                    if (priority == 2) {
                                        if (equipment.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
                                            damagedLevel += 10;
                                        } else if (equipment.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
                                            damagedLevel += 20;
                                        } else if (equipment.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
                                            damagedLevel += 30;
                                        } else {
                                            damagedLevel += 30;
                                        }
                                    }
                                    if (priority == 1) {
                                        if (equipment.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
                                            damagedLevel += 5;
                                        } else if (equipment.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
                                            damagedLevel += 10;
                                        } else if (equipment.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
                                            damagedLevel += 15;
                                        } else {
                                            damagedLevel += 15;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (categoryName.equals("Bàn")) {
                        if ((equipments.size() / table) / 100 >= 50) {
                            damagedLevel += 50;
                        } else {
                            for (TblReportDetailEntity ban : equipments) {
                                if (ban.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
                                    damagedLevel += 2;
                                } else if (ban.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
                                    damagedLevel += 3;
                                } else if (ban.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
                                    damagedLevel += 5;
                                } else {
                                    damagedLevel += 5;
                                }
                            }
                        }
                    }
                    if (categoryName.equals("Ghế")) {
                        if ((equipments.size() / chair) / 100 >= 50) {
                            damagedLevel += 50;
                        } else {
                            for (TblReportDetailEntity ghe : equipments) {
                                if (ghe.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
                                    damagedLevel += 1;
                                } else if (ghe.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
                                    damagedLevel += 2;
                                } else if (ghe.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
                                    damagedLevel += 3;
                                } else {
                                    damagedLevel += 3;
                                }
                            }
                        }
                    }
                }

            }
        }

        if (damagedLevel > 100) {
            damagedLevel = 100;
        }
        return damagedLevel;
    }
}
