package com.example.mywishlistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mywishlistapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
) {

    val snackMessage = remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    if (id != 0L) {

        val wish = viewModel.getWishById(id).collectAsState(
            initial = Wish(0L, "", "")
        )
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description

    } else {

        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""

    }

    Scaffold(
        topBar = {
            AppBarView(
                title =
                if (id != 0L)
                    stringResource(id = R.string.update_wish)
                else
                    stringResource(id = R.string.add_wish)
            ) { navController.navigateUp() }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(
                label = "Title",
                value =
                if (LocalInspectionMode.current)
                    "Nvidia RTX 4090"
                else
                    viewModel.wishTitleState,
                onValueChanged = {
                    viewModel.onWishTitleChanged(it)
                })
            Spacer(modifier = Modifier.height(8.dp))
            WishTextField(
                label = "Description",
                if (LocalInspectionMode.current)
                    "A powerful GPU from Nvidia"
                else
                    viewModel.wishDescriptionState,
                onValueChanged = {
                    viewModel.onWishDescriptionChanged(it)
                })
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(48.dp)
                    .padding(start = 16.dp, end = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(
                        id = R.color.app_bar_color
                    )
                ),
                onClick = {
                    if (viewModel.wishTitleState.isNotBlank()
                    ) {
                        if (id != 0L) {
                            viewModel.updateWish(
                                Wish(
                                    id = id,
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )
                            snackMessage.value = "Wish has been updated"
                        } else {
                            viewModel.addWish(
                                Wish(
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )
                            snackMessage.value = "Wish has been created"

                        }
                    } else
                        snackMessage.value = "Enter field wish title to create a wish"
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            snackMessage.value,
                            duration = SnackbarDuration.Short
                        )
                        navController.navigateUp()
                    }

                }) {
                Text(
                    text =
                    if (id != 0L)
                        stringResource(id = R.string.update_wish)
                    else
                        stringResource(id = R.string.add_wish),
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            // using predefined Color
            textColor = Color.Black,
            // using our own colors in Res.Values.Color
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.black),
        )


    )
}

/*
@Preview(
    name = "Unfolded Foldable",
    device = "spec:id=reference_foldable,shape=Normal,width=673,height=841,unit=dp,dpi=420",
    showSystemUi = true
)
@Preview(
    name = "Tablet",
    device = "spec:id=reference_tablet,shape=Normal,width=1280,height=800,unit=dp,dpi=240",
    showSystemUi = true
)
@Preview(
    name = "Desktop",
    device = "spec:id=reference_desktop,shape=Normal,width=1920,height=1080,unit=dp,dpi=160",
    showSystemUi = true
)
*/
@Preview(
    name = "Phone",
    device = "spec:id=reference_phone,shape=Normal,width=411,height=891,unit=dp,dpi=420",
    showSystemUi = true
)
@Preview(
    name = "Phone - Landscape",
    device = "spec: width=411dp, height=891dp, orientation=landscape, dpi=420",
    showSystemUi = true
)
@Composable
fun AddEditDetailViewPrev() {
    val navController = rememberNavController()
    AddEditDetailView(0L, WishViewModel(), navController)
}

@Preview(showBackground = true)
@Composable
fun WishTestFieldPrev() {
    WishTextField(label = "Title", value = "Text here", onValueChanged = {})
}