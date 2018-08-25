package provider.androidbuffer.com.samplerentapplication.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by incred-dev on 12/8/18.
 */

public class FacilitiesModel implements Serializable {

    @SerializedName("facility_id")
    private String facilityId;

    @SerializedName("name")
    private String name;

    @SerializedName("options")
    private List<OptionsModel> options;

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OptionsModel> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsModel> options) {
        this.options = options;
    }
}
