package com.jd.a6i.task;

import android.content.Context;

public abstract class BackgroundTaskBase {
    public final static String KEY_TASK_CLASS = "taskClass";
    public final static String KEY_PRIORITY = "priority";

    protected Context context;
    protected BackgroundTaskPriority priority = BackgroundTaskPriority.NORMAL_PRIORITY;

    public BackgroundTaskBase(Context applicationContext) {
        this.context = applicationContext;
    }

    public BackgroundTaskPriority getPriority() {
        return this.priority;
    }

    public abstract void execute();
}
