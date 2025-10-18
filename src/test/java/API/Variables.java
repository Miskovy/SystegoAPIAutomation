package API;

public class Variables {
    final String BASE_URL = "https://bcknd.systego.net";
    final String ADMIN_AUTH_END = "/api/admin/auth/login";
    final String ADM_END =  "/api/admin/admin";

    public String getBASE_URL() {
        return BASE_URL;
    }

    public String getADMIN_AUTH_END() {
        return ADMIN_AUTH_END;
    }

    public String get_ADMIN_END() {
        return ADM_END;
    }
    public String getUPDATE_ADMIN_END(String id){
        return ADM_END +"/"+id;
    }
    public String get_byID_ADMIN_END(String id){
        return ADM_END +"/"+id;
    }
}
