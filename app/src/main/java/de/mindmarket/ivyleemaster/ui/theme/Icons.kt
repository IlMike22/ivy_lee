package de.mindmarket.ivyleemaster.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import de.mindmarket.ivyleemaster.R

val ArrowBack: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.arrow_back)

val EyesOpen: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.password_visibility)

val EyesClosed: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.password_visibility_off)

val IvyLogo: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ivy_logo)

val Settings: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.settings)

val Lock: ImageVector
@Composable
get() = ImageVector.vectorResource(id = R.drawable.lock)

val Add: ImageVector
@Composable
get() = ImageVector.vectorResource(id = R.drawable.add_task)



