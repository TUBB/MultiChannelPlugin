package io.github.tubb.multichannel

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * multiChannel task
 * Created by tubingbing on 18/3/26.
 */

class AppChannelTask extends DefaultTask {
    /**
     * Bundle output dir
     */
    String outputDir
    /**
     * Source bundle path list
     */
    def srcBundlePathList

    @TaskAction
    void copyBundlesToCustomDir() {
        if (outputDir != null && !"".equals(outputDir)) {
            File parentFile = new File(outputDir)
            parentFile.mkdirs()
            println("========begin copy=========")
            srcBundlePathList.each { path ->
                File srcFile = new File(path)
                File targetFile = new File(parentFile, srcFile.getName())
                targetFile.withOutputStream { os->
                    srcFile.withInputStream { ins->
                        os << ins
                    }
                }
                println(srcFile.getAbsolutePath() + "==>" + targetFile.getAbsolutePath())
            }
            println("========end copy=========")
        } else {
            println("========outputDir is invalid========")
        }
    }

    static String taskName() {
        return "multiChannel"
    }

    static String taskShortName() {
        return "mC"
    }
}
