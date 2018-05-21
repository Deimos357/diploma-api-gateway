package ua.nure.tanasiuk.commons;

public final class Constants {
    public static final class Url {
        public static final String[] URL_WITHOUT_AUTH = {
            "/auth/v1/login/**",
            "/core/v1/login/**",
            "/core/v1/registration/**"
        };


        public static final String CORE_LOGIN_URL_PREFIX = "/core/v1/login";

        private Url() {
            throw new AssertionError();
        }
    }

    public static final class Parameters {

        public static final String LOGGED_IN_USER = "requestInitiatorId";

        private Parameters() {
            throw new AssertionError();
        }
    }

    public static final class PreFiltersOrder {
        public static final int CORE_SECURITY_FILTER = 2;
        public static final int LOGIN_REDIRECT_FILTER = 7;
    }

    public static final class PostFiltersOrder {
        public static final int BAD_REQUEST_RESPONSE_FILTER = 1;
        public static final int SUCCESS_RESPONSE_FILTER = 2;
        public static final int INTERNAL_SERVER_ERROR_FILTER = 3;
    }

    public static final class ErrorCodes {
        public static final String UNAUTHORIZED_ERROR_CODE = "400010";
        public static final String INTERNAL_SERVER_ERROR_CODE = "400009";
    }

    private Constants() {
        throw new AssertionError();
    }
}
