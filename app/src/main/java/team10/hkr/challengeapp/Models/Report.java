package team10.hkr.challengeapp.Models;

import java.util.Date;

/**
 * Created by Martin on 01/05/2017.
 */

public class Report {

    private String UUID;
    private String reportType;
    private String reason;
    private Date creationDate;

    public Report(String UUID, String reportType, String reason, Date creationDate) {

        this.UUID = UUID;
        this.reportType = reportType;
        this.reason = reason;
        this.creationDate = creationDate;

    }
}
