package demo;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description 读取文件 转换成二进制
 * @Author LHL
 * @Date 17:29
 */
public class FileAnalysisToByte {

    private String filePath;

    private String fileType;

    private Map<String,byte[]> cache = new ConcurrentHashMap<>();

    private List<File> classFiles = new CopyOnWriteArrayList<>();
    private FileConvert fileConvert;

    public FileAnalysisToByte(String filePath, String fileType,FileConvert fileConvert) {
        setFilePath(filePath);
        setFileType(fileType);
        this.fileConvert = fileConvert;
    }
    //文件路径和加载的文件类型
    public FileAnalysisToByte(String filePath, String fileType) {
        setFilePath(filePath);
        setFileType(fileType);
        this.fileConvert = new DefaultFileConvert();
    }

    private boolean isEmpty(String param){
        return param == null || "".equals(param);
    }

    public void load() {
        if (!cache.isEmpty()) return;
        //检查文件目录
        loadFile(filePath);
        convert();
    }

    private void loadFile(String filePath) {    //path为目录
        File file = new File(filePath);
        File[] files = file.listFiles();
        if (null == files) return;
        //遍历该目录所有文件和文件夹对象
        for (File value : files) {
            if (value.isDirectory()) {
                loadFile(value.toString());  //递归操作，逐一遍历各文件夹内的文件
                continue;
            }
            if (!value.isDirectory() && value.getName().endsWith(fileType))
                classFiles.add(value);
        }
    }

    private void setFilePath(String filePath) {
        if (isEmpty(filePath))
            throw new RuntimeException("filePath must not null");
        this.filePath = filePath;
    }

    private void setFileType(String fileType){
        if (isEmpty(fileType))
            throw new RuntimeException("fileType must not null");
        this.fileType = "."+fileType;
    }

    private void convert(){
        classFiles.parallelStream().forEach(this::addCache);
        System.out.println(classFiles.toString());
    }

    private void addCache(File file){
        cache.put(file.getName().replace(fileType,""),fileConvert.convertTo(file));
    }

    public byte[] getBytes(String fileName) {
        return cache.get(fileName);
    }

    interface FileConvert{
        byte[] convertTo(File file);
    }

    class DefaultFileConvert implements FileConvert{

        @Override
        public byte[] convertTo(File file) {
            byte[] bytes = FileUtil.readDeal(file);
            if (!file.getName().endsWith(".xlass"))
                return bytes;
            return readXlass(bytes);

        }

        private byte[] readXlass(byte[] bytes) {
            byte[] copyBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                byte a = (byte) 255;
                copyBytes[i] = (byte) (a - bytes[i]);
            }
            return copyBytes;
        }
    }

}
