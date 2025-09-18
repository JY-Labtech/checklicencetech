package br.com.jylabtech.checklicencetech;

import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CheckLicenceEntity {
    private String id;
    private String uid;
    private String deviceUid;
    private String deviceSerialNumber;
    private String userUid;
    private String productId;
    private String productName;
    private String productDescription;
    private String integrationLibId;
    private String integrationLibName;
    private String checkoutSessionId;
    private String licenceKey;
    private String licenceEncrypt;
    private Date expiredAt;
    private boolean isExpired;
    private boolean isInactive;
    private boolean isTrial;
    private String subscriptionId;
    private String checkoutType;
    private Date createdAt;
    private Date updatedAt;
    private Date savedLocalDatabaseAt;
    private String databaseVersion;

    // Construtor padrão
    public CheckLicenceEntity() {}

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getDeviceUid() { return deviceUid; }
    public void setDeviceUid(String deviceUid) { this.deviceUid = deviceUid; }

    public String getDeviceSerialNumber() { return deviceSerialNumber; }
    public void setDeviceSerialNumber(String deviceSerialNumber) { this.deviceSerialNumber = deviceSerialNumber; }

    public String getUserUid() { return userUid; }
    public void setUserUid(String userUid) { this.userUid = userUid; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }

    public String getIntegrationLibId() { return integrationLibId; }
    public void setIntegrationLibId(String integrationLibId) { this.integrationLibId = integrationLibId; }

    public String getIntegrationLibName() { return integrationLibName; }
    public void setIntegrationLibName(String integrationLibName) { this.integrationLibName = integrationLibName; }

    public String getCheckoutSessionId() { return checkoutSessionId; }
    public void setCheckoutSessionId(String checkoutSessionId) { this.checkoutSessionId = checkoutSessionId; }

    public String getLicenceKey() { return licenceKey; }
    public void setLicenceKey(String licenceKey) { this.licenceKey = licenceKey; }

    public String getLicenceEncrypt() { return licenceEncrypt; }
    public void setLicenceEncrypt(String licenceEncrypt) { this.licenceEncrypt = licenceEncrypt; }

    public Date getExpiredAt() { return expiredAt; }
    public void setExpiredAt(Date expiredAt) { this.expiredAt = expiredAt; }

    public boolean isExpired() { return isExpired; }
    public void setExpired(boolean expired) { isExpired = expired; }

    public boolean isInactive() { return isInactive; }
    public void setInactive(boolean inactive) { isInactive = inactive; }

    public boolean isTrial() { return isTrial; }
    public void setTrial(boolean trial) { isTrial = trial; }

    public String getSubscriptionId() { return subscriptionId; }
    public void setSubscriptionId(String subscriptionId) { this.subscriptionId = subscriptionId; }

    public String getCheckoutType() { return checkoutType; }
    public void setCheckoutType(String checkoutType) { this.checkoutType = checkoutType; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public Date getSavedLocalDatabaseAt() { return savedLocalDatabaseAt; }
    public void setSavedLocalDatabaseAt(Date savedLocalDatabaseAt) { this.savedLocalDatabaseAt = savedLocalDatabaseAt; }

    public String getDatabaseVersion() { return databaseVersion; }
    public void setDatabaseVersion(String databaseVersion) { this.databaseVersion = databaseVersion; }

    // Construtor a partir de JSON
    public static CheckLicenceEntity fromJson(String jsonStr) {
        try {
            JSONObject json = new JSONObject(jsonStr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

            CheckLicenceEntity entity = new CheckLicenceEntity();
            entity.setId(json.optString("id"));
            entity.setUid(json.optString("uid"));
            entity.setDeviceUid(json.optString("deviceUid"));
            entity.setDeviceSerialNumber(json.optString("deviceSerialNumber"));
            entity.setUserUid(json.optString("userUid"));
            entity.setProductId(json.optString("productId"));
            entity.setProductName(json.optString("productName"));
            entity.setProductDescription(json.optString("productDescription"));
            entity.setIntegrationLibId(json.optString("integrationLibId"));
            entity.setIntegrationLibName(json.optString("integrationLibName"));
            entity.setCheckoutSessionId(json.optString("checkoutSessionId"));
            entity.setLicenceKey(json.optString("licenceKey"));
            entity.setLicenceEncrypt(json.optString("licenceEncrypt"));
            entity.setExpired(json.optBoolean("isExpired"));
            entity.setInactive(json.optBoolean("isInactive"));
            entity.setTrial(json.optBoolean("isTrial"));
            entity.setSubscriptionId(json.optString("subscriptionId"));
            entity.setCheckoutType(json.optString("checkoutType"));
            entity.setDatabaseVersion(json.optString("databaseVersion"));

            String expiredAtStr = json.optString("expiredAt");
            if (!expiredAtStr.isEmpty()) entity.setExpiredAt(sdf.parse(expiredAtStr));

            String createdAtStr = json.optString("createdAt");
            if (!createdAtStr.isEmpty()) entity.setCreatedAt(sdf.parse(createdAtStr));

            String updatedAtStr = json.optString("updatedAt");
            if (!updatedAtStr.isEmpty()) entity.setUpdatedAt(sdf.parse(updatedAtStr));

            String savedLocalStr = json.optString("savedLocalDatabaseAt");
            if (!savedLocalStr.isEmpty()) entity.setSavedLocalDatabaseAt(sdf.parse(savedLocalStr));

            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Converte de volta para JSON
    public String toJson() {
        try {
            JSONObject json = new JSONObject();
            json.put("id", id);
            json.put("uid", uid);
            json.put("deviceUid", deviceUid);
            json.put("deviceSerialNumber", deviceSerialNumber);
            json.put("userUid", userUid);
            json.put("productId", productId);
            json.put("productName", productName);
            json.put("productDescription", productDescription);
            json.put("integrationLibId", integrationLibId);
            json.put("integrationLibName", integrationLibName);
            json.put("checkoutSessionId", checkoutSessionId);
            json.put("licenceKey", licenceKey);
            json.put("licenceEncrypt", licenceEncrypt);
            json.put("isExpired", isExpired);
            json.put("isInactive", isInactive);
            json.put("isTrial", isTrial);
            json.put("subscriptionId", subscriptionId);
            json.put("checkoutType", checkoutType);
            json.put("databaseVersion", databaseVersion);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            if (expiredAt != null) json.put("expiredAt", sdf.format(expiredAt));
            if (createdAt != null) json.put("createdAt", sdf.format(createdAt));
            if (updatedAt != null) json.put("updatedAt", sdf.format(updatedAt));
            if (savedLocalDatabaseAt != null) json.put("savedLocalDatabaseAt", sdf.format(savedLocalDatabaseAt));

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


