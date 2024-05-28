package my.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
 
@XmlRootElement
public class SampleObject {
    @JsonProperty("SampleObject")
    private int id;
    private String title;
    private String description;
    private String status;
    private String createDate;
    private String updateDate;

    public SampleObject() {

    }

    public SampleObject(int id, String title, String description, String status, String createDate, String updateDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public int GetID() {
        return this.id;
    }

    public String GetTitle() {
        return this.title;
    }

    public String GetDescription() {
        return this.description;
    }

    public String GetStatus() {
        return this.status;
    }

    public String GetCreateDate() {
        return this.createDate;
    }

    public String GetUpdateDate() {
        return this.updateDate;
    }
}
