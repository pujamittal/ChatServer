public class CircularBuffer {

    String[] buffer;
    private int bufferTail;
    private int bufferHead;
    private int indexCounter;
    private int messageCounter;
    private String prependedCounter; //ask

    /**
     * This constructor accepts the maximum number of messages that this buffer can hold. Furthermore, because this is
     * a fixed-size buffer, this size should never change.
     *
     * @param size
     */
    public CircularBuffer(int size) {
        this.buffer = new String[size];
        this.bufferHead = 0;
        this.bufferTail = 0;
        this.indexCounter = 0;
        this.messageCounter = 0;
        this.prependedCounter = null;
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
        if (bufferHead != (bufferTail - 1)) {
            if (messageCounter < 10) {
                String prependedCounter = "000) " + messageCounter;
                buffer[bufferHead++] = prependedCounter + message;
            }
            if (messageCounter < 100 && messageCounter > 9) {
                String prependedCounter = "00) " + messageCounter;
                buffer[bufferHead++] = prependedCounter + message;

            }
            if (messageCounter < 1000 && messageCounter > 99) {
                String prependedCounter = "0) " + messageCounter;
                buffer[bufferHead++] = prependedCounter + message;

            }
            if (messageCounter < 10000 && messageCounter > 999 ) {
                String prependedCounter = ") " + messageCounter;
                buffer[bufferHead++] = prependedCounter + message;
            }
        }
        messageCounter++;
        bufferHead = bufferHead % buffer.length;
    }

    /**
     * This method returns the numMessages most recent messages posted to the chatroom.
     *
     * @param numMessages
     * @return
     */
    public String[] getNewest(int numMessages) {
        String[] latestMessages = new String[numMessages];
        String[] noMessages = new String[0];
        if (numMessages > messageCounter) {
            System.out.println("test null");
            return null;
        }
        if (numMessages == 0) {
            return noMessages;
        }
        if (messageCounter > buffer.length) {
            indexCounter = messageCounter % buffer.length - 1;
            for (int i = 0; i < numMessages; i++) {
                latestMessages[i] = buffer[indexCounter];
                indexCounter--;
                if (indexCounter < 0) {
                    indexCounter = buffer.length - 1;
                }
            }
            return latestMessages;
        } else {
            indexCounter = messageCounter - 1;
            for (int i = 0; i < numMessages; i++) {
                latestMessages[i] = buffer[indexCounter];
                if(indexCounter > 0) {
                    indexCounter--;
                }
            }
            return latestMessages;
        }
    }
}