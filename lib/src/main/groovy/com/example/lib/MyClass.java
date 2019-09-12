package com.example.lib;

import org.gradle.api.Plugin;
import org.gradle.api.Project;


public class MyClass implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("apply my plugin");
        project.getTasks().create("mytask", MyTask.class);
    }
}
