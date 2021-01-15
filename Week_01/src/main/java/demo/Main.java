package demo;

/**
 * @Author LHL
 * @Date 14:24:39
 */
public class Main {

    //当时写这段代码的时候没有考虑过 类加载器应该用loadClass函数，而不是findClass，
    //如果使用loadClass函数的话，这段缓存有些冗余，因为这个findClass函数只有loadClass查找不到的时候才会进行调用，
    //当hello被加载后 再次调用loadClass不会再执行findClass 所以缓存白加了
    public static void main(String[] args) {
        try {
            //使用无参构造 路径为./ 文件类型为 class
//            MyClassLoader myClassLoader = new MyClassLoader();
            MyClassLoader myClassLoader = new MyClassLoader(ClassCache.create("Week_01\\src\\main\\java","xlass"));
            Class<?> hello1 = myClassLoader.findClass("Hello");
            hello1.getMethod("hello").invoke(hello1.newInstance());
            //验证是否使用缓存
            Class<?> hello2 = myClassLoader.findClass("Hello");
            hello2.getMethod("hello").invoke(hello2.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
