package design_pattern.composite;

public interface File {
    void addFile(File file);
    void removeFile(File file);
    File getFile(int i);
    void operation();
}
