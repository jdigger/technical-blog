import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

class JBakePlugin implements Plugin<Project> {


    public static final String JBAKE = "jbake"
    Project project
    JBakeExtension extension

    void apply(Project project) {
        this.project = project
        project.apply(plugin: 'base')

        project.repositories {
            jcenter()
        }

        Configuration configuration = project.configurations.maybeCreate(JBAKE)
        extension = project.extensions.create(JBAKE, JBakeExtension)

        addDependenciesAfterEvaluate()

        project.task('jbake', type: JBakeTask, group: 'Documentation', description: 'Bake a jbake project') {
            classpath = configuration
            conventionMapping.input = { project.file("$project.projectDir/$project.jbake.srcDirName") }
            conventionMapping.output = { project.file("$project.buildDir/$project.jbake.destDirName") }
            conventionMapping.clearCache = { project.jbake.clearCache }
            conventionMapping.configuration = { project.jbake.configuration }
        }

    }

    def addDependenciesAfterEvaluate() {
        project.afterEvaluate {
            addDependencies()
        }
    }

    def addDependencies() {
        project.dependencies {
            jbake("org.jbake:jbake-core:${extension.version}")

            if (new Version(extension.version) > new Version("2.3.0")) {
                jbake("org.asciidoctor:asciidoctorj:${extension.asciidoctorjVersion}")
            }
            else {
                jbake("org.asciidoctor:asciidoctor-java-integration:${extension.asciidoctorJavaIntegrationVersion}")
            }

            jbake("org.freemarker:freemarker:${extension.freemarkerVersion}")
            jbake("org.pegdown:pegdown:${extension.pegdownVersion}")
        }
    }

}
