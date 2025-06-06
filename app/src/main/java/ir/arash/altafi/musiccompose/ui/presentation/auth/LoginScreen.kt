package ir.arash.altafi.musiccompose.ui.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ir.arash.altafi.musiccompose.R
import ir.arash.altafi.musiccompose.ui.base.ApiState
import ir.arash.altafi.musiccompose.ui.component.LoadingComponent
import ir.arash.altafi.musiccompose.ui.component.NetworkConnectivityListener
import ir.arash.altafi.musiccompose.ui.navigation.Route
import ir.arash.altafi.musiccompose.ui.theme.CustomFont
import ir.arash.altafi.musiccompose.utils.ValidationChecker

@Composable
fun LoginScreen(navController: NavController) {
    var passwordVisible by remember { mutableStateOf(false) }

    val authViewModel: AuthViewModel = hiltViewModel()

    var isConnected by remember { mutableStateOf(true) }

    NetworkConnectivityListener(onConnectionChanged = { connected ->
        isConnected = connected
    })

    val context = LocalContext.current

    val focusRequester = remember { FocusRequester() }
    var email by remember { mutableStateOf("arashaltafi1377@gmail.com") }
    var password by remember { mutableStateOf("123456") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    when (val state = authViewModel.apiState.collectAsState().value) {
        is ApiState.Loading -> {
            LoadingComponent()
        }

        is ApiState.Success<*> -> {
            navController.navigate(Route.Home) {
                popUpTo(navController.graph.id) { inclusive = true }
                launchSingleTop = true
            }
        }

        is ApiState.Error -> Toast.makeText(
            context,
            state.message,
            Toast.LENGTH_SHORT
        ).show()

        is ApiState.Unauthorized -> {
            navController.navigate(Route.Login) {
                popUpTo(navController.graph.id) { inclusive = true }
                launchSingleTop = true
            }
        }

        else -> Unit
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (!isConnected) {
                    Icon(
                        imageVector = Icons.Filled.Error,
                        contentDescription = "No internet connection",
                        tint = Color.Red
                    )
                }
                Text(
                    text = context.getString(R.string.app_name),
                    fontSize = 28.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    fontFamily = CustomFont,
                    letterSpacing = 2.sp,
                    textDecoration = TextDecoration.None
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .width(280.dp)
                        .focusRequester(focusRequester),
                    textStyle = TextStyle(
                        textAlign = TextAlign.Start,
                        fontFamily = CustomFont,
                    ),
                    value = email,
                    onValueChange = { newValue ->
                        email = newValue
                    },
                    label = {
                        Text(
                            text = context.getString(R.string.email),
                            fontSize = 12.sp,
                            fontFamily = CustomFont,
                            color = Color.White
                        )
                    },
                    placeholder = {
                        Text(
                            text = context.getString(R.string.email_placeholder),
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
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.email),
                            contentDescription = context.getString(R.string.email),
                            tint = Color.White
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Email,
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
                        .width(280.dp),
                    textStyle = TextStyle(
                        textAlign = TextAlign.Start,
                        fontFamily = CustomFont,
                    ),
                    value = password,
                    onValueChange = { newValue ->
                        password = newValue
                    },
                    label = {
                        Text(
                            text = context.getString(R.string.password),
                            fontSize = 12.sp,
                            fontFamily = CustomFont,
                            color = Color.White
                        )
                    },
                    placeholder = {
                        Text(
                            text = context.getString(R.string.password_placeholder),
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth(),
                            style = TextStyle(
                                textAlign = TextAlign.Start
                            ),
                            fontFamily = CustomFont,
                        )
                    },
                    singleLine = true,
                    enabled = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (passwordVisible)
                            painterResource(R.drawable.visibility)
                        else
                            painterResource(R.drawable.visibility_off)
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible }
                        ) {
                            Icon(
                                painter = icon,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                tint = Color.White
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                        autoCorrect = false,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (ValidationChecker.isValidEmail(email).not()) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.email_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (ValidationChecker.isValidPassword(password).not()) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.password_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                authViewModel.onEvent(AuthIntent.Login(email, password))
                            }
                        }
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.width(280.dp)
                ) {
                    Text(
                        text = "قبلا ثبت نام نکرده اید؟",
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontFamily = CustomFont,
                        letterSpacing = 2.sp,
                        textDecoration = TextDecoration.None
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        onClick = {
                            navController.navigate(Route.Register)
                        }
                    ) {
                        Text(
                            text = context.getString(R.string.register),
                            fontSize = 14.sp,
                            color = Color.Blue,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal,
                            fontFamily = CustomFont,
                            letterSpacing = 2.sp,
                            textDecoration = TextDecoration.Underline,
                        )
                    }
                }


                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (ValidationChecker.isValidEmail(email).not()) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.email_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (ValidationChecker.isValidPassword(password).not()) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.password_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            authViewModel.onEvent(AuthIntent.Login(email, password))
                        }
                    },
                    modifier = Modifier
                        .width(280.dp)
                        .imePadding(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(
                        text = context.getString(R.string.login),
                        color = Color.Black,
                        fontFamily = CustomFont,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}