package bg.magna.machines.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "machines")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "serial_number", unique = true, nullable = false)
    private String serialNumber;

    @Column(nullable = false)
    private String name;

    @Column(name = "image_url", nullable = false)
    private String imageURL;

    @Column(nullable = false)
    private int year;

    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @Column(name = "description_en", nullable = false)
    private String descriptionEn;

    @Column(name = "description_bg", nullable = false)
    private String descriptionBg;

    @Column(name = "working_width")
    private double workingWidth;

    @Column
    private int weight;

    @Column(name = "required_power")
    private int requiredPower;

    @Column(name = "more_info_en")
    private String moreInfoEn;

    @Column(name = "more_info_bg")
    private String moreInfoBg;

    public Machine() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionBg() {
        return descriptionBg;
    }

    public void setDescriptionBg(String descriptionBg) {
        this.descriptionBg = descriptionBg;
    }

    public double getWorkingWidth() {
        return workingWidth;
    }

    public void setWorkingWidth(double workingWidth) {
        this.workingWidth = workingWidth;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getRequiredPower() {
        return requiredPower;
    }

    public void setRequiredPower(int requiredPower) {
        this.requiredPower = requiredPower;
    }

    public String getMoreInfoEn() {
        return moreInfoEn;
    }

    public void setMoreInfoEn(String moreInfo) {
        this.moreInfoEn = moreInfo;
    }

    public String getMoreInfoBg() {
        return moreInfoBg;
    }

    public void setMoreInfoBg(String moreInfoBg) {
        this.moreInfoBg = moreInfoBg;
    }
}
