package com.mozhimen.study_lint_lib.issues

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

/**
 * @ClassName KotlinTodoDetector
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/10 18:21
 * @Version 1.0
 */
/**
 * Detect usage of kotlin TODO usage to avoid of NotImplementedError.
 * <p>
 * `TODO("Not yet implemented")`
 * </p>
 */
@Suppress("UnstableApiUsage")
class KotlinTodoDetector : Detector(), Detector.UastScanner {

    override fun getApplicableMethodNames(): List<String> {
        return listOf("TODO")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        println("KotlinTodoDetector >>> matched TODO in [${method.parent.containingFile}]")
        if (context.evaluator.isMemberInClass(method, "kotlin.StandardKt__StandardKt")) {
            val deleteFix = fix().name("Delete this TODO method")
                .replace().all().with("").build()
            context.report(
                ISSUE,
                context.getLocation(node),
                "You must fix `TODO()` first.", deleteFix)
        }
    }

    companion object {
        val ISSUE = Issue.create(
            "KotlinTodo",
            "Detecting `TODO()` method from kotlin/Standard.kt.",
            """
                You have unimplemented method or undo work marked by `TODO()`,
                please implement it or remove dangerous TODO.
                """,
            category = Category.CORRECTNESS,
            priority = 9,
            severity = Severity.ERROR,
            implementation = Implementation(KotlinTodoDetector::class.java, Scope.JAVA_FILE_SCOPE),
        )
    }
}