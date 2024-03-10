package top.cmarco.bibleplugin.exceptions;

@FunctionalInterface
public interface ExceptionHandler {
    void handle(final Exception e);
}
