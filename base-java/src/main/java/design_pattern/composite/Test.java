package design_pattern.composite;

public class Test {
    public static void main(String[] args) {
        File folder = new Folder();

        File textFile = new TextFile();
        folder.addFile(textFile);
        //new 子文件夹
        File subFolder = new Folder();
        File videoFile = new VideoFile();
        subFolder.addFile(videoFile);
        folder.addFile(subFolder);

        folder.operation();
    }
}
