package demo;

/**
 * @Description TODO
 * @Author LHL
 * @Date 15:47
 */
public class MyClassLoader extends ClassLoader {

    private ClassCache classCache;

    public MyClassLoader() {
        this.classCache = ClassCache.create("./","class");
    }

    public MyClassLoader(ClassCache classCache) {
        this.classCache = classCache;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        classCache.load();
        Class aClass = classCache.getClass(name);
        if (null != aClass) {
            System.out.println("get class cache " + name);
            return aClass;
        }
        byte[] classBytes = classCache.getClassBytes(name);

        if (null == classBytes) {
            throw new ClassNotFoundException("class not found : "+name);
        }

        Class<?> resultClass = defineClass(name, classBytes, 0, classBytes.length);
        classCache.putClass(name,resultClass);
        return resultClass;
    }

}