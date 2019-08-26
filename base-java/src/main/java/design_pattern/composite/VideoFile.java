package design_pattern.composite;

public class VideoFile implements File {
    @Override
    public void addFile(File file) {
        System.out.println("视频文件不能添加文件");
    }

    @Override
    public void removeFile(File file) {
        System.out.println("视频文件不能移除文件");
    }

    @Override
    public File getFile(int i) {
        System.out.println("视频文件不能获取文件");
        return null;
    }

    @Override
    public void operation() {
        System.out.println("向视频文件中写入一些视频");
    }
}
