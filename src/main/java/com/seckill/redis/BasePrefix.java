package com.seckill.redis;

public abstract class BasePrefix implements KeyPrefix {
    // 为保证每一个模块的前缀不重复,可以将模块做成一个类,用类名来做为前缀,可以保证一定不重复
    int expireSeconds;
    String prefix;

    public BasePrefix(String prefix) {
        this(0,prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() { // 如果值为0,则表示永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        return getClass().getSimpleName()+":"+prefix;
    }
}
