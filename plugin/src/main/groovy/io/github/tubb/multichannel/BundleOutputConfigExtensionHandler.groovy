package io.github.tubb.multichannel

import org.gradle.api.GradleException
import org.gradle.api.Project;

/**
 * apkOutputConfig dsl handler
 * Created by tubingbing on 18/3/27.
 */

class BundleOutputConfigExtensionHandler {

    static void renameBundleFile(Project project, def appChannelExtension) {
        def bundleOutputConfigExtension = appChannelExtension.bundleOutputConfig
        if (bundleOutputConfigExtension == null) return
        String outputDir = bundleOutputConfigExtension.outputDir
        Closure renameBundleFile = bundleOutputConfigExtension.renameBundleFile
        def variants
        if (project.plugins.hasPlugin('com.android.application')) {
            variants = project.android.applicationVariants
        } else if (project.plugins.hasPlugin('com.android.library')) {
            variants = project.android.libraryVariants
        } else {
            throw new GradleException('Android Application or Library plugin required')
        }
        def srcBundlePathList = []
        variants.all { variant ->
            String flavorName = variant.flavorName
            if (project.plugins.hasPlugin('com.android.library')
                    && (flavorName == null || ''.equals(flavorName))) {
                // filter library sync
                return
            }
            variant.outputs.all { output ->
                if (renameBundleFile != null) {
                    output.outputFileName = renameBundleFile(project, variant)
                }
                if (!'debug'.equals(variant.buildType.name)) {
                    srcBundlePathList.add(output.outputFile.getAbsolutePath())
                }
            }
        }
        AppChannelTask appChannelTask = project.tasks.findByName(AppChannelTask.taskName())
        appChannelTask.outputDir = outputDir
        appChannelTask.srcBundlePathList = srcBundlePathList
    }
}
