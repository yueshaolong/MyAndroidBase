package com.example.lib;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

class MyTask extends DefaultTask {

    @TaskAction
    void action() {
        System.out.println("my task run");
    }
}
