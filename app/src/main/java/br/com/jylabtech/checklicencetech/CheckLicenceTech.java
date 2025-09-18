package br.com.jylabtech.checklicencetech;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.nio.charset.StandardCharsets;


public class CheckLicenceTech {

    private static final String PREFS_NAME = "licence_prefs_encrypted";
    private static final String KEY_JSON = "licence_entity";

    private final Context context;


    private final LicenceProvider licenceProvider;

    public CheckLicenceTech(Context context) {
        this.context = context;
        this.licenceProvider = new LicenceProvider(context);
    }

    /**
     * Valida a licença chamando a API e salva localmente se sucesso.
     */
    public boolean validateLicence(String licenceKey, String licenceUrl) {
        boolean aux = false;
        try {
            boolean licenceDatabase = validateLicenceDatabase(licenceKey);
            if(licenceDatabase) {
                return licenceDatabase;
            }

            String apiUrl = sanitizeUrl(licenceUrl);
            // Chama o provider que já pega deviceId, serialNumber e uuid internamente
            CheckLicenceEntity licence = licenceProvider.checkLicence(apiUrl, licenceKey);
            if (licence != null) {
                boolean isValidLicence = validateLicenceServer(licence);
                if(isValidLicence) {
                    saveLicenceEncrypted(licence);
                    aux = true;
                }
            }
            return aux;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aux;
    }

    public boolean validateExistsLicence() {
        boolean aux = false;
        Date now = new Date();

        try {
            CheckLicenceEntity licenceDatabase = getLicenceEncrypted();
            if(licenceDatabase!= null) {
                if(licenceDatabase.getExpiredAt().after(now)) {
                    aux = true;
                }
                if(licenceDatabase.isExpired() || licenceDatabase.isInactive()) {
                    aux = false;
                }
                if(licenceDatabase.getSavedLocalDatabaseAt() != null) {
                    long diffInMillis = now.getTime() - licenceDatabase.getSavedLocalDatabaseAt().getTime();
                    long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
                    if (diffInDays > 7) {
                        deleteLicenceDatabase();
                        aux = false;
                    }
                }
            }
            return aux;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean validateLicenceServer(CheckLicenceEntity entity) {
        boolean aux = false;
        Date now = new Date();
        if(entity.getExpiredAt().after(now)) {
            aux = true;
        }

        if(entity.isExpired() || entity.isInactive()) {
            aux = false;
        }

        return aux;
    }

    private boolean validateLicenceDatabase(String licenceKey) {
        boolean aux = false;
        Date now = new Date();

        try {
            CheckLicenceEntity licenceDatabase = getLicenceEncrypted();
            if(licenceDatabase!= null) {
                if(licenceDatabase.getExpiredAt().after(now)) {
                    aux = true;
                }
                if(licenceDatabase.isExpired() || licenceDatabase.isInactive()) {
                    aux = false;
                }
                if(licenceDatabase.getSavedLocalDatabaseAt() != null) {
                    long diffInMillis = now.getTime() - licenceDatabase.getSavedLocalDatabaseAt().getTime();
                    long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
                    if (diffInDays > 7) {
                        deleteLicenceDatabase();
                        aux = false;
                    }
                }

                if(!Objects.equals(licenceDatabase.getLicenceEncrypt(), licenceKey)) {
                    aux = false;
                }
            }
            return aux;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Salva a entidade de licença localmente de forma criptografada.
     */
    private boolean saveLicenceEncrypted(CheckLicenceEntity entity) {
        try {
            entity.setSavedLocalDatabaseAt(new Date());

            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            SharedPreferences prefs = EncryptedSharedPreferences.create(
                    context,
                    PREFS_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            prefs.edit().putString(KEY_JSON, entity.toJson()).apply();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Recupera a licença salva localmente de forma criptografada.
     */
    private CheckLicenceEntity getLicenceEncrypted() {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            SharedPreferences prefs = EncryptedSharedPreferences.create(
                    context,
                    PREFS_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            String savedJson = prefs.getString(KEY_JSON, null);
            if (savedJson != null) {
                return CheckLicenceEntity.fromJson(savedJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void deleteLicenceDatabase() {
        try {
            // Cria ou recupera a chave mestre de forma segura
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            // Acessa o SharedPreferences criptografado
            SharedPreferences prefs = EncryptedSharedPreferences.create(
                    context,
                    PREFS_NAME,
                    masterKey,                  // chave mestre
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            // Remove a licença salva
            prefs.edit().remove(KEY_JSON).apply();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String sanitizeUrl(String data) {
        if (data.contains("http")) {
            return data;
        } else {
            byte[] decodedBytes = android.util.Base64.decode(data, android.util.Base64.DEFAULT);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        }
    }
}
