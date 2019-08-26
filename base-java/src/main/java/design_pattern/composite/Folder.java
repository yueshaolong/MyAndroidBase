package design_pattern.composite;

import java.util.ArrayList;
import java.util.List;

public class Folder implements File {

    private List<File> fileList = new ArrayList<>();

    @Override
    public void addFile(File file) {
        fileList.add(file);
        System.out.println("向文件夹中加入一个文件");
    }

    @Override
    public void removeFile(File file) {
        fileList.remove(file);
        System.out.println("从文件夹中删除一个文件");
    }

    @Override
    public File getFile(int i) {
        System.out.println("从文件夹中获取一个文件");
        return fileList.get(i);
    }

    @Override
    public void operation() {
        for (File file : fileList){
            file.operation();
        }
    }
}
