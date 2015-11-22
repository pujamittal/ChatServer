public class CircularBuffer {

    String[] buffer;
    String messageFormatted;
    private int numAvailable;
    private int totalMessagesAllowed;
    private int messageCounter;

    /**
     * This constructor accepts the maximum number of messages that this buffer can hold. Furthermore, because this is
     * a fixed-size buffer, this size should never change.
     *
     * @param size
     */
    public CircularBuffer(int size) {
        this.buffer = new String[size];
        this.messageFormatted = null;
        this.numAvailable = 0;
        this.totalMessagesAllowed = 0;
        this.messageCounter = 0;
    }

    /**
     * This method prepends a 4 digit number to the message to form a new string. The numbering starts from 0000 to
     * 9999 and wraps around.
     * This method adds the new message to the tail of the buffer. If the buffer becomes full, the oldest message is
     * overwritten with the new message.
     *
     * @param message
     */
    public void put(String message) {

        messageFormatted = String.format("%04d) %s", totalMessagesAllowed, message);

        buffer[messageCounter] = messageFormatted;
        messageCounter++;
        totalMessagesAllowed++;

        if (numAvailable < buffer.length) {
            numAvailable++;
        }
        if (messageCounter == buffer.length) {
            messageCounter = 0;
        }
        if (totalMessagesAllowed == 10000) {
            this.totalMessagesAllowed = 0;
        }
    }

    /**
     * This method returns the numMessages most recent messages posted to the chatroom.
     *
     * @param numMessages
     * @return
     */
    public String[] getNewest(int numMessages) {
        String[] latestMessages = new String[numMessages];
        String[] noMessages = {};
        int tempIndex = Math.min(numAvailable, numMessages);
        if (numMessages < 0) {
            return null;
        }
        if (numMessages == 0) {
            return noMessages;
        }
        if (tempIndex < 0) {
            tempIndex += numAvailable;
        }
        for (int i = 0; i < numMessages; i++) {
            latestMessages[i] = buffer[tempIndex];
            tempIndex++;
            if (tempIndex == buffer.length) {
                tempIndex = 0;
            }
        }
        return latestMessages;
    }
}