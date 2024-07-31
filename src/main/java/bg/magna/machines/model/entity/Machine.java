package bg.magna.machines.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    @Column(name = "createdOn", nullable = false)
    private LocalDateTime createdOn;

    public Machine() {
        this.createdOn = LocalDateTime.now();
    }

    public Machine(String serialNumber, String name, String imageURL, int year, String brandName, String descriptionEn, String descriptionBg, double workingWidth, int weight, int requiredPower, String moreInfoEn, String moreInfoBg) {
        super();
        this.serialNumber = serialNumber;
        this.name = name;
        this.imageURL = imageURL;
        this.year = year;
        this.brandName = brandName;
        this.descriptionEn = descriptionEn;
        this.descriptionBg = descriptionBg;
        this.workingWidth = workingWidth;
        this.weight = weight;
        this.requiredPower = requiredPower;
        this.moreInfoEn = moreInfoEn;
        this.moreInfoBg = moreInfoBg;
    }

    public Machine(String id, String serialNumber, String name, String imageURL, int year, String brandName, String descriptionEn, String descriptionBg, double workingWidth, int weight, int requiredPower, String moreInfoEn, String moreInfoBg) {
        super();
        this.id = id;
        this.serialNumber = serialNumber;
        this.name = name;
        this.imageURL = imageURL;
        this.year = year;
        this.brandName = brandName;
        this.descriptionEn = descriptionEn;
        this.descriptionBg = descriptionBg;
        this.workingWidth = workingWidth;
        this.weight = weight;
        this.requiredPower = requiredPower;
        this.moreInfoEn = moreInfoEn;
        this.moreInfoBg = moreInfoBg;
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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
