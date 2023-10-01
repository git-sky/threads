package cn.com.sky.thread_patterns.active_object.demo1;

public interface ActiveObject {

    /**
     * 构建字符串
     *
     * @param count
     * @param fillChar
     * @return
     */
    Result<String> makeString(int count, char fillChar);

    /**
     * 展现字符串
     *
     * @param string
     */
    void display(String string);

}