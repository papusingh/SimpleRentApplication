package provider.androidbuffer.com.samplerentapplication.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by incred-dev on 12/8/18.
 */

public class MainModel implements Serializable {

    @SerializedName("facilities")
    private List<FacilitiesModel> facilities;

    @SerializedName("exclusions")
    private List<List<ExclusionsModel>> exclusions;

    public List<FacilitiesModel> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<FacilitiesModel> facilities) {
        this.facilities = facilities;
    }

    public List<List<ExclusionsModel>> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<List<ExclusionsModel>> exclusions) {
        this.exclusions = exclusions;
    }
}
