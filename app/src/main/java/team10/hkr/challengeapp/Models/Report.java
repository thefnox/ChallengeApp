package team10.hkr.challengeapp.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Martin on 01/05/2017.
 */

public class Report {

    private String UUID;
    private String reportType;
    private String reason;
    private String creationDate;

    public Report(JSONObject jsonObject) throws JSONException{

        this.UUID = jsonObject.has("_id") ? jsonObject.getString("_id") : "";
        this.reportType = jsonObject.has("reportType") ? jsonObject.getString("reportType") : "";
        this.reason = jsonObject.has("reason") ? jsonObject.getString("reason") : "";
        this.creationDate = jsonObject.has("creationDate") ? jsonObject.getString("creationDate") : "";

    }

    public String getUUID() {
        return UUID;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
