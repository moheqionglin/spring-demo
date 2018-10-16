import java.util.HashMap;
import java.util.Map;

/**
 * @author wanli.zhou
 * @description
 * @time 10/10/2018 10:00 AM
 */
public class Message {
    private Map<String, String> header = new HashMap<>();
    private String body;

    public Message(Map<String, String> header, String body) {
        this.header = header;
        this.body = body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    @Override
    public String toString() {
        return "Message{" +
                "header=" + header +
                ", body='" + body + '\'' +
                '}';
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}