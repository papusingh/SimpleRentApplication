package provider.androidbuffer.com.samplerentapplication.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by incred-dev on 12/8/18.
 */

public class ExclusionsModel implements Serializable {

    @SerializedName("facility_id")
    private String facilityId;

    @SerializedName("options_id")
    private String optionsId;

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(String optionsId) {
        this.optionsId = optionsId;
    }
}
