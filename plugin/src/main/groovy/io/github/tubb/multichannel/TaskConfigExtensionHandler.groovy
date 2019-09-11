package io.github.tubb.multichannel

import org.gradle.api.Project;

/**
 * taskConfig dsl handler
 * Created by tubingbing on 18/3/27.
 */

class TaskConfigExtensionHandler {
    static void applyTaskConfig(Project project, def taskConfigExtension) {
        boolean disableDebugTask = taskConfigExtension.disableDebugTask
        boolean disableLintTask = taskConfigExtension.disableLintTask
        boolean disableTestTask = taskConfigExtension.disableTestTask
        def targetTasks = project.tasks.findAll{ task ->
            def taskName = task.name.toLowerCase()
            if (disableDebugTask && taskName.contains('debug'))
                return true
            if (disableLintTask && taskName.contains('lint'))
                return true
            if (disableTestTask && taskName.contains("test"))
                return true
            return false
        }
        targetTasks.each {
            println "disable task ${it.name} by AppChannelPlugin"
            it.setEnabled false
        }
    }
}
