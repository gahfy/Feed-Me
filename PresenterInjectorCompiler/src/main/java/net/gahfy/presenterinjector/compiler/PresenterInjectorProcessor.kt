package net.gahfy.presenterinjector.compiler

import net.gahfy.presenterinjector.annotations.BasePresenter
import net.gahfy.presenterinjector.annotations.BaseView
import net.gahfy.presenterinjector.annotations.FinalPresenter
import net.gahfy.presenterinjector.annotations.PresenterComponent
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

private const val BASE_PRESENTER_CLASS_NAME = "net.gahfy.presenterinjector.annotations.BasePresenter"
private const val BASE_VIEW_CLASS_NAME = "net.gahfy.presenterinjector.annotations.BaseView"
private const val FINAL_PRESENTER_CLASS_NAME = "net.gahfy.presenterinjector.annotations.FinalPresenter"
private const val PRESENTER_COMPONENT_CLASS_NAME = "net.gahfy.presenterinjector.annotations.PresenterComponent"

/**
 * The annotation processor which will generate code to inject dependencies in Presenters based on
 * classes annotated with @BasePresenter, @BaseView, @FinalPresenter and @PresenterComponent
 */
@SupportedAnnotationTypes(BASE_PRESENTER_CLASS_NAME, BASE_VIEW_CLASS_NAME, FINAL_PRESENTER_CLASS_NAME, PRESENTER_COMPONENT_CLASS_NAME)
class PresenterInjectorProcessor : AbstractProcessor() {
    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        // Getting the annotations
        // Base presenter
        val allBasePresenterElements = roundEnv?.getElementsAnnotatedWith(BasePresenter::class.java)
        val basePresenterElement = if (allBasePresenterElements?.count() ?: 0 > 0) allBasePresenterElements?.first() else null

        // Base view
        val allBaseViewElements = roundEnv?.getElementsAnnotatedWith(BaseView::class.java)
        val baseViewElement = if (allBaseViewElements?.count() ?: 0 > 0) allBaseViewElements?.first() else null

        // Final presenters
        val allFinalPresenterElements = roundEnv?.getElementsAnnotatedWith(FinalPresenter::class.java)

        // Presenter component
        val allPresenterComponentElements = roundEnv?.getElementsAnnotatedWith(PresenterComponent::class.java)
        val presenterComponentElement = if (allPresenterComponentElements?.count() ?: 0 > 0) allPresenterComponentElements?.first() else null

        if (basePresenterElement != null && presenterComponentElement != null && baseViewElement != null && allFinalPresenterElements != null && allFinalPresenterElements.count() > 0) {
            // Getting string values from the annotated elements
            // Base presenter
            val basePresenterPackageName = processingEnv.elementUtils.getPackageOf(basePresenterElement).toString()
            val basePresenterPackagePath = basePresenterPackageName.replace('.', File.separatorChar)
            val basePresenterClassName = basePresenterElement.simpleName.toString()

            // Base view
            val baseViewClassName = processingEnv.elementUtils.getPackageOf(baseViewElement).toString() + "." + baseViewElement.simpleName.toString()

            // Final presenters
            val finalPresenterClassNames: MutableList<String> = mutableListOf()
            allFinalPresenterElements.forEach { finalPresenterElement -> finalPresenterClassNames.add(processingEnv.elementUtils.getPackageOf(finalPresenterElement).toString() + "." + finalPresenterElement.simpleName.toString()) }

            // Presenter component
            val presenterComponentClassName = processingEnv.elementUtils.getPackageOf(presenterComponentElement).toString() + "." + presenterComponentElement.simpleName.toString()

            val sourceCode = getInjectionHelperSource(basePresenterPackageName, basePresenterClassName, baseViewClassName, presenterComponentClassName, finalPresenterClassNames)

            // Getting the folder in which files will be saved and create it if needed
            val kotlinGeneratedPath = processingEnv.options["kapt.kotlin.generated"]?.replace("kaptKotlin", "kapt")
            val kaptKotlinGenerated = File(kotlinGeneratedPath)
            val folder = File(kaptKotlinGenerated, basePresenterPackagePath).apply {
                if (!exists()) {
                    mkdirs()
                }
            }

            // Writing source code to the file
            val helperClassFile = File(folder, "${basePresenterClassName}_InjectionHelper.kt")
            helperClassFile.writeText(sourceCode)
        }
        return false
    }

    /**
     * Returns the source code of the injection helper class.
     * @param basePresenterPackageName the package of the base presenter
     * @param basePresenterClassName the name of the class of the base presenter
     * @param baseViewClassName the name of the class of the base view
     * @param presenterComponentClassName the name of the class of the presenter component
     * @param finalPresenterClassNames the list of the name of the class of the base presenters
     */
    private fun getInjectionHelperSource(basePresenterPackageName: String, basePresenterClassName: String, baseViewClassName: String, presenterComponentClassName: String, finalPresenterClassNames: List<String>): String {
        var classCastCheck = "    "
        finalPresenterClassNames.forEach { currentClassName -> classCastCheck = "${classCastCheck}is $currentClassName -> injector.inject(basePresenter)\n   " }
        return """package $basePresenterPackageName

fun makeInjection(injector:$presenterComponentClassName, basePresenter:$basePresenterClassName<$baseViewClassName>){
    when(basePresenter){
        $classCastCheck
    }
}"""
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }
}