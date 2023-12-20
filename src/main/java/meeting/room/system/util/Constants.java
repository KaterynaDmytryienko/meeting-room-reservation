package meeting.room.system.util;


import meeting.room.system.enums.Roles;

public final class Constants {

    /**
     * Default user role.
     */
    public static final Roles DEFAULT_ROLE = Roles.USER;

    /**
     * Username login form parameter.
     */
    public static final String USERNAME_PARAM = "username";

    private Constants() {
        throw new AssertionError();
    }
}
