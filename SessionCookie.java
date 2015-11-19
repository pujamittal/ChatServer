import java.util.Random;

public class SessionCookie {

    private long id;
    private long cookiesLastActivity;
    public static int timeoutLength = 300; // The value stored in this variable represents seconds.

    /**
     * Your class must contain this constructor. However, you are free to make additional constructors if you wish.
     * @param id
     */
    public SessionCookie(long id) {
        this.id = id;
        this.cookiesLastActivity = System.currentTimeMillis();
    }

    /**
     * This method determines if the user login session has ended and if the cookie has timed out.
     * A cookie has timed out if, at the time this method is invoked, the elapsed time (in seconds) since the last user
     * action is more than the value of timeoutLength.
     * @return
     */
    public boolean hasTimedOut() {
        long cookiesElapsedTime = System.currentTimeMillis() - cookiesLastActivity;
        if (cookiesElapsedTime >= timeoutLength * 1000) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method will update the cookie's time of last activity by setting it to the current time.
     */
    public void updateTimeOfActivity() {
        this.cookiesLastActivity = System.currentTimeMillis();
    }

    /**
     * This method returns the ID of the cookie.
     * @return
     */
    public long getID() {
        return id;
    }
    /**
     *
     * public static void main(String[] args) {
     * Random idGenerator = new Random();
     * long cookieID = idGenerator.nextInt(9999);
     * String cookieIDs = String.format("%04d", cookieID);
     * long cookieIDprepended = Long.parseLong(cookieIDs);
     * System.out.println(cookieIDprepended);
     *
     * String cookieIDs = String.format("%03d", cookieID);
     * long cookieIDprepended = Long.parseLong(cookieIDs);
     *
     * Random idGenerator = new Random();
     * long cookieID = idGenerator.nextInt(9999);
     */
}
