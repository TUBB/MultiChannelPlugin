package io.github.tubb.multichannel

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * Multi channel plugin: io.github.tubb.multichannel
 * Created by tubingbing on 18/3/26.
 */

class AppChannelPlugin implements Plugin<Project> {
    Project project
    ChannelFlavorConfigExtensionHandler channelFlavorConfigExtensionHandler = new ChannelFlavorConfigExtensionHandler()
    TaskConfigExtensionHandler taskConfigExtensionHandler = new TaskConfigExtensionHandler()
    BundleOutputConfigExtensionHandler bundleOutputConfigExtensionHandler = new BundleOutputConfigExtensionHandler()

    @Override
    void apply(Project project) {
        this.project = project
        project.extensions.create('appChannel', AppChannelExtension)
        project.appChannel.extensions.create('taskConfig', TaskConfigExtension)
        project.appChannel.extensions.create('channelFlavorConfig', ChannelFlavorConfigExtension)
        project.appChannel.extensions.create('bundleOutputConfig', BundleOutputConfigExtension)
        AppChannelTask multiChannelTask = project.tasks.create(AppChannelTask.taskName(), AppChannelTask.class)
        Task buildTask = project.tasks.getByName('build');
        multiChannelTask.setGroup('MultiChannel')
        multiChannelTask.dependsOn(buildTask)
        multiChannelTask.setDescription('Build with multi channels.')
        // export createChannel() method to Project
        project.ext {
            createChannel = this.&createChannel
        }
        project.afterEvaluate {
            if (hasMultiChannelTask()) {
                def appChannelExtension = project['appChannel']
                if (appChannelExtension == null) {
                    throw new GradleException('Please config the appChannel dsl')
                }
                def taskConfigExtension = appChannelExtension.taskConfig
                if (taskConfigExtension != null) {
                    taskConfigExtensionHandler.applyTaskConfig(project, taskConfigExtension)
                }
                bundleOutputConfigExtensionHandler.renameBundleFile(project, appChannelExtension)
            }
        }
    }

    void createChannel() {
        if (hasMultiChannelTask())
            channelFlavorConfigExtensionHandler.createChannel(project)
    }

    boolean hasMultiChannelTask() {
        def taskNames = project.gradle.startParameter.taskNames
        List<String> targetTaskNames = [AppChannelTask.taskName(),
                                        AppChannelTask.taskShortName(),
                                        String.format(":%s:%s", project.name, AppChannelTask.taskName()),
                                        String.format(":%s:%s", project.name, AppChannelTask.taskShortName())]
        def hasMultiChannelTask = false
        taskNames.each { name ->
            if (targetTaskNames.contains(name)) {
                hasMultiChannelTask = true
            }
        }
        return hasMultiChannelTask
    }
}