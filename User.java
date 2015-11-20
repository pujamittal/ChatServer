public class User {

    private String username;
    private String password;
    private SessionCookie cookie;

    /**
     * Your class must have a constructor that accepts the username, password, and cookie of the user.
     * Note: It is valid for the user to not have a session cookie
     * (i.e. the cookie has a null value, this means the user is not connected).
     * @param username
     * @param password
     * @param cookie
     */
    public User(String username, String password, SessionCookie cookie) {
        this.username = username;
        this.password = password;
        this.cookie = cookie;
    }

    /**
     * This method returns the username of the user.
     * @return
     */
    public String getName() {
        return username;
    }

    /**
     * This method returns the password of the user.
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method returns true if the given password matches the user's password exactly. Otherwise, false is returned.
     * @param password
     * @return
     */
    public boolean checkPassword(String password) {
        if (this.password.equals(password)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This getter method returns the user's session cookie.
     * @return
     */
    public SessionCookie getCookie() {
        return cookie;
    }

    /**
     * This setter method updates the user's session cookie with the specified parameter.
     * @param cookie
     */
    public void setCookie(SessionCookie cookie) {
        this.cookie = cookie;
    }
}
