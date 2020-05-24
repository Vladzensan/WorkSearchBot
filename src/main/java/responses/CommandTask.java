package responses;

public interface CommandTask {
    Response execute(String query, long chatId);
}
