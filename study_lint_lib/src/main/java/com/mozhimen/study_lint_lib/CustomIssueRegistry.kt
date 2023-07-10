package com.mozhimen.study_lint_lib

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.mozhimen.study_lint_lib.issues.KotlinTodoDetector

/**
 * @ClassName CustomIssueRegistry
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/10 18:19
 * @Version 1.0
 */

@Suppress("UnstableApiUsage")
class CustomIssueRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(
            KotlinTodoDetector.ISSUE
        )

    override val minApi: Int
        get() = 8 // works with Studio 4.1 or later; see com.android.tools.lint.detector.api.Api / ApiKt

    override val api: Int
        get() = CURRENT_API

}