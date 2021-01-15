package demo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 缓存class类 增加读取速度 浪费内存。。。
 * @Author LHL
 * @Date 17:21:56
 */
public class ClassCache {

    private Map<String,Class> classes = new ConcurrentHashMap<>();

    private FileAnalysisToByte fileAnalysisToByte;

    private ClassCache(FileAnalysisToByte fileAnalysisToByte){
        this.fileAnalysisToByte = fileAnalysisToByte;
    }

    synchronized void load(){
        fileAnalysisToByte.load();
    }

    Class getClass(String className){
        return classes.get(className);
    }

    void putClass(String className,Class clazz){
        classes.put(className,clazz);
    }

    synchronized byte[] getClassBytes(String className){
        return fileAnalysisToByte.getBytes(className);
    }

    //文件路径和加载的文件类型
    static ClassCache create(String filePath,String fileType){
        return new ClassCache(new FileAnalysisToByte(filePath,fileType));
    }

}
