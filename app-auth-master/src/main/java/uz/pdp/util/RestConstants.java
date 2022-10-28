package uz.pdp.util;

import uz.pdp.controller.AuthController;

public interface RestConstants {
    String AUTHENTICATION_HEADER = "Authorization";

    String[] OPEN_PAGES = {
            "/*",
            AuthController.AUTH_CONTROLLER_BASE_PATH + "/**"
    };

    String SERVICE_BASE_PATH = "/api/auth/v1/";

    String INITIAL_FILTERING_FUNCTION = "" +
            "DROP INDEX IF EXISTS uk_role_id_and_permission;\n" +
            "CREATE UNIQUE INDEX uk_role_id_and_permission ON role_permissions(role_id, permissions);" +
            "DROP INDEX IF EXISTS uk_role_type_without_other_role_type;" +
            "CREATE UNIQUE INDEX uk_role_type_without_other_role_type ON role (role_type) WHERE role_type <> 'OTHER';" +
            "DROP FUNCTION IF EXISTS get_query_result;" +
            " create function get_query_result(sql_query text) " +
            "    returns TABLE(id character varying, name character varying, phone_number character varying, order_count integer, enabled boolean) " +
            "    language plpgsql " +
            "as " +
            "$$ " +
            "BEGIN " +
            "    RETURN QUERY " +
            "        EXECUTE sql_query; " +
            "END " +
            "$$;";
}
