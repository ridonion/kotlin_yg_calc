package com.example.ygo_calc

import C_HistoryPage
import android.content.DialogInterface.OnShowListener
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ygo_calc.pages.C_CalcPage
import com.example.ygo_calc.pages.C_DicePage

// ページ管理用enum
enum class PageType(@StringRes val title: Int) {
    Calc(title = R.string.page_calc),
    History(title = R.string.page_duel_history),
    Dice(title = R.string.page_dice),
}

// ヘッダー要素
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun C_AppHeader(
    pageType: PageType,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(pageType.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

//　フッター要素
@Composable
fun C_AppFooter(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        )
        {
            IconButton(onClick = { navController.navigate(PageType.Calc.name) }) {
                Icon(imageVector = Icons.Default.Calculate, contentDescription = stringResource(R.string.page_calc))
            }
            IconButton(onClick = { navController.navigate(PageType.History.name) }) {
                Icon(imageVector = Icons.Default.History, contentDescription = stringResource(R.string.page_duel_history))
            }
            IconButton(onClick = { navController.navigate(PageType.Dice.name) }) {
                Icon(imageVector = Icons.Default.Casino, contentDescription = stringResource(R.string.page_dice))
            }
        }
    }
}

@Composable
fun ManagePages(
    navController: NavHostController = rememberNavController()
) {
    // 戻るボタン対応
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentPage = PageType.valueOf(
        backStackEntry?.destination?.route ?: PageType.Calc.name
    )
    val context = LocalContext.current

    Scaffold(
        topBar = {
            C_AppHeader(
                pageType = currentPage,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            C_AppFooter(
                navController = navController
            )
        }
    ) {
        innerPadding ->
        // val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = PageType.Calc.name,
            modifier = Modifier.padding(innerPadding)
        )
        {
            composable(route = PageType.Calc.name) {
                // 電卓
                C_CalcPage()
            }
            composable(route = PageType.History.name) {
                // 履歴
                C_HistoryPage(context = context)
            }
            composable(route = PageType.Dice.name) {
                // ダイス
                C_DicePage()
            }
        }
    }
}
