package com.kampalahomes.core.model.navigation

sealed class AppRoute(val route: String) {
    data object Splash : AppRoute("splash")
    data object RoleSelection : AppRoute("role_selection")
    data object Auth : AppRoute("auth")
    data object Registration : AppRoute("registration")
    data object PreferenceSetup : AppRoute("preference_setup")
    data object LocationAccess : AppRoute("location_access")

    data object Home : AppRoute("home")
    data object Map : AppRoute("map")
    data object Saved : AppRoute("saved")
    data object Profile : AppRoute("profile")

    data object PropertyDetails : AppRoute("property_details")
    data object AffordabilityCalculator : AppRoute("affordability_calculator")
    data object SignInPromptSheet : AppRoute("signin_prompt_sheet")
    data object EnquirySentConfirmation : AppRoute("enquiry_sent_confirmation")

    data object Notifications : AppRoute("notifications")
    data object AdvancedFilters : AppRoute("advanced_filters")
    data object SearchResults : AppRoute("search_results")
    data object SavedSearches : AppRoute("saved_searches")
    data object HelpSupport : AppRoute("help_support")
    data object PrivacyPolicy : AppRoute("privacy_policy")
    data object ForgotPassword : AppRoute("forgot_password")
}

val allRoutes: List<AppRoute> = listOf(
    AppRoute.Splash,
    AppRoute.RoleSelection,
    AppRoute.Auth,
    AppRoute.Registration,
    AppRoute.PreferenceSetup,
    AppRoute.LocationAccess,
    AppRoute.Home,
    AppRoute.Map,
    AppRoute.Saved,
    AppRoute.Profile,
    AppRoute.PropertyDetails,
    AppRoute.AffordabilityCalculator,
    AppRoute.SignInPromptSheet,
    AppRoute.EnquirySentConfirmation,
    AppRoute.Notifications,
    AppRoute.AdvancedFilters,
    AppRoute.SearchResults,
    AppRoute.SavedSearches,
    AppRoute.HelpSupport,
    AppRoute.PrivacyPolicy,
    AppRoute.ForgotPassword
)
