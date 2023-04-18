package project.mainframe.api.openAI.exceptions;

/**
 * The OpenAIException.
 */
public class OpenAIException extends Exception {

    /**
     * Constructor.
     * 
     * @param msg The message
     */
    public OpenAIException(String msg) {
        super(msg);
    }

    /**
     * Constructor.
     * 
     * @param msg The message
     * @param cause The cause
     */
    public OpenAIException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
