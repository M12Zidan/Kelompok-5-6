package com.app.kelompok_5_6.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.kelompok_5_6.navigation.Screen

data class  BottomNavItem(
    val route: String,
    val icon: @Composable () -> Unit
)

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // Daftar item menu navigasi bawah
    val itemsMenu = listOf(
        BottomNavItem(Screen.Home.route) {
            Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
        },
        BottomNavItem(Screen.CreateNote.route) {
            Icon(imageVector = Icons.Default.Create, contentDescription = "Create Note")
        },
        BottomNavItem(Screen.Profile.route) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Profile")
        }
    )

    // Tampilan background BottomBar
    Surface(
        tonalElevation = 4.dp,
        color = Color.White,
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
    ) {
        // Susun item navigasi secara horizontal
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tampilkan setiap item menu
            itemsMenu.forEach { item ->
                BottomBarItem(item, currentRoute, navController)
            }
        }
    }
}

@Composable
fun BottomBarItem(
    item: BottomNavItem,
    currentRoute: String?,
    navController: NavHostController
) {
    // Cek apakah item ini yang sedang aktif
    val isSelected = currentRoute == item.route

    // Atur animasi saat ukuran berubah (ikon & teks)
    val floatAnimSpec = tween<Float>(700, easing = FastOutSlowInEasing)

    val iconSize by animateFloatAsState(
        targetValue = if (isSelected) 28f else 24f,
        animationSpec = floatAnimSpec,
        label = "iconSize"
    )

    val textSize by animateFloatAsState(
        targetValue = if (isSelected) 12f else 10f,
        animationSpec = floatAnimSpec,
        label = "textSize"
    )

    // Warna ikon saat aktif dan tidak aktif
    val iconColor = if (isSelected) Color(0xFF27548A) else Color.Gray

    // Tampilan visual untuk satu item navigasi
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(72.dp)
            .clickable {
                // Navigasi hanya dijalankan jika belum berada di halaman tersebut
                if (!isSelected) {
                    navController.navigate(item.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
    ) {
        // Tampilkan ikon dengan warna sesuai status aktif/tidak
        CompositionLocalProvider(LocalContentColor provides iconColor) {
            Box(modifier = Modifier.size(iconSize.dp)) {
                item.icon()
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Tampilkan teks route sebagai label
        Text(
            text = item.route.replaceFirstChar { it.uppercase() },
            color = iconColor,
            fontSize = textSize.sp,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

// Preview untuk menampilkan BottomBar di tampilan pratinjau Android Studio
@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    BottomBar(navController = rememberNavController())
}