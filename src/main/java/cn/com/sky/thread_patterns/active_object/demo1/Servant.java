package cn.com.sky.thread_patterns.active_object.demo1;

class Servant implements ActiveObject {

    @Override
    public Result<String> makeString(int count, char fillChar) {
        char[] chars = new char[count];
//        Arrays.fill(chars, fillChar);
        for (int i = 0; i < count; i++) {
            chars[i] = fillChar;
            ThreadUtil.sleep(100);
        }

        return new RealResult<>(new String(chars));
    }

    @Override
    public void display(String string) {
        System.out.println("Display string: " + string);
        ThreadUtil.sleep(10);
    }

}