package com.simform.jetcomposedemo.models


data class LessonModel(
    val title: String,
    val description: String,
    val topics: List<String>,
    val activity: ActivityType
)

public fun getLessons(): List<LessonModel> {
    return listOf(
        LessonModel(
            title = "Lesson 1",
            description = "Demo Login Screen UI",
            topics = listOf(
                "Modifiers",
                "Composable",
                "remember variables",
                "Text Styles",
                "Clickable Text",
                "Annotated String",
                "Focus Listeners",
                "Keyboard Actions and Options",
                "Validation",
                "Snack-bar",
                "Etc."
            ),
            activity = ActivityType.LOGIN_ACTIVITY
        ),

        LessonModel(
            title = "Lesson 2",
            description = "Demo of Constraint Layout in Jetpack COmpose",
            topics = listOf(
                "Resizing",
                "ConstraintSet",
                "Constrain",
                "Chains",
                "Etc."
            ),
            activity = ActivityType.CONSTRAINT_LAYOUT_ACTIVITY
        )
    );
}

enum class ActivityType {
    LOGIN_ACTIVITY,
    CONSTRAINT_LAYOUT_ACTIVITY
}

