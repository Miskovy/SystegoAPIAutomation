package API;

public class Variables {
    final String BASE_URL = "https://bcknd.systego.net";
    final String ADMIN_AUTH_END = "/api/admin/auth/login";
    final String POST_ADM_END =  "/api/admin/admin";

    public String getBASE_URL() {
        return BASE_URL;
    }

    public String getADMIN_AUTH_END() {
        return ADMIN_AUTH_END;
    }

    public String getPOST_ADM_END() {
        return POST_ADM_END;
    }
}
