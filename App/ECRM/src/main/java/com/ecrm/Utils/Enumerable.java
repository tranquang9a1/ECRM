package com.ecrm.Utils;

/**
 * Created by ChiDNMSE60717 on 6/13/2015.
 */
public class Enumerable {
    public enum ReportStatus {
        NEW(1), GOING(2), FINISH(3), REMOVE(4);

        private int value;
        private ReportStatus(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum DamagedLevel {
        HIGH("1"), MEDIUM("2"), LOW("3"), UNKNOWN("4");

        private String value;
        private DamagedLevel(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum NotifyType {
        ADMIN(""), STAFFNOTIFYREPORT("NewReport"), STAFFSYSTEMNOTIFY("SystemNotify"), TEACHERCHANGEROOM("ChangeRoom");

        private String value;
        private NotifyType(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum MessageType {
        NEWREPORT(1), CHANGEROOM(2), SYSTEM(3);

        private int value;
        private MessageType(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
