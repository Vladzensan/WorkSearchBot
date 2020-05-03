package responses;

public enum Command {
    START("/start"), HELP("/help"), LOGIN("/login"), UNDEFINED("undefined");


    private String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
