package ir.arash.altafi.musiccompose.ui.presentation.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.plus
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import ir.arash.altafi.musiccompose.utils.ext.uriToBase64
import ir.arash.altafi.musiccompose.R
import ir.arash.altafi.musiccompose.ui.component.CoilImage
import ir.arash.altafi.musiccompose.ui.navigation.Route
import ir.arash.altafi.musiccompose.ui.theme.CustomFont
import ir.arash.altafi.musiccompose.utils.ValidationChecker

@Composable
fun ProfileScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val profileViewModel: ProfileViewModel = hiltViewModel()

    var name by remember { mutableStateOf("") }
    var family by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

//    LaunchedEffect(liveProfile) {
//        name = liveProfile?.name ?: ""
//        family = liveProfile?.family ?: ""
//    }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    var isEditMode by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    // open keyboard automatic in editMode
    LaunchedEffect(isEditMode) {
        if (isEditMode) {
            focusRequester.requestFocus()
            keyboardController?.show()
        } else {
            keyboardController?.hide()
        }
    }

    LaunchedEffect(selectedImageUri) {
        if (selectedImageUri != null) {
            val base64 = context.uriToBase64(selectedImageUri!!)
            profileViewModel.onEvent(ProfileIntent.Avatar(base64))
            isEditMode = false
        }
    }

    // handle callback gallery
    val singleImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 10.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    color = colorResource(R.color.transparent2_black),
                    shape = RoundedCornerShape(bottomEnd = 300.dp, bottomStart = 300.dp)
                ),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
            ) {
                CoilImage(
                    url = R.drawable.ic_user, //if (isEditMode) R.drawable.ic_user else liveProfile?.avatar,
                    modifier = Modifier
                        .zIndex(1f)
                        .size(120.dp)
                        .clip(CircleShape)
                        .shadow(8.dp)
                        .border(
                            1.dp,
                            Color.White,
                            CircleShape
                        )
                        .clickable(
                            onClick = {
                                if (isEditMode) {
                                    singleImagePickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                } else {
//                                    if (liveProfile?.name != null && liveProfile?.avatar != null) {
//                                        navController.navigate(
//                                            Route.ImageScreen(
//                                                title = liveProfile!!.name + " " + liveProfile!!.family,
//                                                imageUrl = liveProfile!!.avatar!!
//                                            )
//                                        )
//                                    }
                                }
                            }
                        )
                )
                IconButton(
                    onClick = {
                        isEditMode = !isEditMode
                    },
                    modifier = Modifier
                        .zIndex(2f)
                        .align(Alignment.TopEnd)
                        .offset(1.dp, 1.dp)
                        .background(Color.White, CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                        .size(32.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = if (isEditMode) Icons.Filled.Close else Icons.Filled.Edit,
                        contentDescription = "Edit",
                        tint = Color.Black
                    )
                }
            }
        }

        if (isEditMode) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .focusRequester(focusRequester),
                textStyle = TextStyle(
                    textAlign = TextAlign.Start,
                    fontFamily = CustomFont,
                ),
                value = name,
                onValueChange = { newValue ->
                    if (newValue.length <= 20) name = newValue
                },
                label = {
                    Text(
                        text = context.getString(R.string.name),
                        fontSize = 12.sp,
                        fontFamily = CustomFont,
                        color = Color.White
                    )
                },
                placeholder = {
                    Text(
                        text = context.getString(R.string.name),
                        color = Color.White,
                        style = TextStyle(
                            textAlign = TextAlign.End
                        ),
                        fontFamily = CustomFont,
                    )
                },
                singleLine = true,
                enabled = true,
                visualTransformation = VisualTransformation.None,
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.round_person_24),
                        contentDescription = context.getString(R.string.name),
                        tint = Color.White
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                textStyle = TextStyle(
                    textAlign = TextAlign.Start,
                    fontFamily = CustomFont,
                ),
                value = family,
                onValueChange = { newValue ->
                    if (newValue.length <= 30) family = newValue
                },
                label = {
                    Text(
                        text = context.getString(R.string.family),
                        fontSize = 12.sp,
                        fontFamily = CustomFont,
                        color = Color.White
                    )
                },
                placeholder = {
                    Text(
                        text = context.getString(R.string.family),
                        color = Color.White,
                        style = TextStyle(
                            textAlign = TextAlign.End
                        ),
                        fontFamily = CustomFont,
                    )
                },
                singleLine = true,
                enabled = true,
                visualTransformation = VisualTransformation.None,
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.round_person_24),
                        contentDescription = context.getString(R.string.family),
                        tint = Color.White
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (ValidationChecker.isValidName(name).not()) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.name_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (ValidationChecker.isValidFamily(name).not()) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.family_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        profileViewModel.onEvent(ProfileIntent.Info(name, family, password))
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        isEditMode = false

                        Toast.makeText(
                            context,
                            context.getString(R.string.profile_updated),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(
                    text = context.getString(R.string.update),
                    color = Color.Black,
                    fontFamily = CustomFont,
                    fontSize = 16.sp
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
//                Text(
//                    modifier = Modifier
//                        .width(LocalConfiguration.current.screenWidthDp.dp * 0.5f),
//                    text = liveProfile?.name + " " + liveProfile?.family,
//                    fontFamily = CustomFont,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    textAlign = TextAlign.Center,
//                    fontSize = 18.sp,
//                    color = Color.White
//                )
//
//                Text(
//                    modifier = Modifier
//                        .width(LocalConfiguration.current.screenWidthDp.dp * 0.5f),
//                    text = liveProfile?.phone ?: "",
//                    fontFamily = CustomFont,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    textAlign = TextAlign.Center,
//                    fontSize = 16.sp,
//                    color = Color.White
//                )
            }
        }
    }

}