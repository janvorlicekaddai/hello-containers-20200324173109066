package cz.addai.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.springframework.util.StringUtils.isEmpty;

public class RequestClientInfo {
    public enum OsType {
        ANDROID, IOS;

        @JsonCreator
        public static OsType fromString(String value){
            if(!isEmpty(value)){
                for(OsType val : OsType.values()){
                    if(val.name().toLowerCase().equals(value.toLowerCase())){
                        return val;
                    }
                }
            }
            throw new IllegalArgumentException();
        }
    }

    // App version
    @NotNull
    @NotBlank
    @Length(min = 1, max = 50)
    @ApiModelProperty(example="1.0")
    private String appVersion;

    // Device name (like "lge LG-H340n")
    @NotNull
    @NotBlank
    @Length(min = 2, max = 50)
    @ApiModelProperty(example="lge LG-H340n")
    private String device;

    // Unique device ID
    @NotNull
    @NotBlank
    @Length(min = 10, max = 60)
    @ApiModelProperty(example="99B2F1CA29744138225CAAF9ABE7B87052C23DAC")
    private String deviceId;

    // Device OS type
    @NotNull
    @ApiModelProperty(example="Android")
    private OsType osType;

    // Device OS version
    @NotNull
    @NotBlank
    @Length(min = 1, max = 50)
    @ApiModelProperty(example="6.0")
    private String osVersion;

    // Latitude
    @ApiModelProperty(example="50.0582581")
    private Double latitude;

    // Longitude
    @ApiModelProperty(example="14.3862938")
    private Double longitude;


    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public OsType getOsType() {
        return osType;
    }

    public void setOsType(OsType osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

}
