import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

import java.lang.reflect.Constructor

class JBakeTask extends DefaultTask {
    @InputDirectory
    File input

    @OutputDirectory
    File output

    @Input
    Map<String, Object> configuration = [:]

    boolean clearCache

    Configuration classpath
    private static ClassLoader cl

    JBakeProxy jbake

    @TaskAction
    void bake() {
        createJbake()
        jbake.prepare()
        mergeConfiguration()
        jbake.jbake()
    }

    private void createJbake() {
        if (!jbake) {
            jbake = new JBakeProxyImpl(delegate: loadOvenDynamic(), input: getInput(), output: getOutput(), clearCache: getClearCache())
        }
    }

    private def mergeConfiguration() {
        //config = new CompositeConfiguration([createMapConfiguration(), jbake.getConfig()])
        def delegate = loadClass('org.apache.commons.configuration.CompositeConfiguration')
        Constructor constructor = delegate.getConstructor(Collection)
        def config = constructor.newInstance([createMapConfiguration(), jbake.getConfig()])
        jbake.setConfig(config)

    }

    private def createMapConfiguration() {
        def delegate = loadClass('org.apache.commons.configuration.MapConfiguration')
        Constructor constructor = delegate.getConstructor(Map)
        constructor.newInstance(getConfiguration())
    }

    private def loadOvenDynamic() {
        setupClassLoader()
        loadClass("org.jbake.app.Oven")
    }

    private static Class loadClass(String className) {
        cl.loadClass(className)
    }

    private def setupClassLoader() {
        if (classpath?.files) {
            def urls = classpath.files.collect { it.toURI().toURL() }
            cl = new URLClassLoader(urls as URL[], Thread.currentThread().contextClassLoader)
            Thread.currentThread().contextClassLoader = cl
        }
        else {
            cl = Thread.currentThread().contextClassLoader
        }
    }

}
