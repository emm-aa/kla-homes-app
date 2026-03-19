# Kampala Homes Android App

A modern real estate rental application for finding and browsing properties in Kampala, Uganda.

## Features

### Property Discovery
- Browse rental properties with detailed information (price, bedrooms, bathrooms, size, amenities)
- Map-based property exploration with location coordinates
- Advanced filtering by budget, property type, location, and amenities
- Search and save searches for quick access

### User Experience
- Role-based onboarding (tenant/landlord)
- Personalized preference setup (budget range, preferred areas, property types)
- Location-based property recommendations
- Save favorite properties for later viewing
- Affordability calculator for budget planning

### Core Navigation
- **Home** - Browse featured and recommended properties
- **Map** - View properties on an interactive map
- **Saved** - Access saved properties and searches
- **Profile** - Manage account and preferences

### Property Features
- High-quality property images
- Neighborhood information
- Landlord contact details
- Direct enquiry system
- Help & support access

## Architecture

Modular Android application built with:
- **Jetpack Compose** for modern UI
- **Multi-module architecture** for scalability
  - Core modules: data, model, UI components
  - Feature modules: auth, home, map, property, saved, profile, support, onboarding
- **Type-safe navigation** with sealed route classes
- **Guest and authenticated user flows**

## Current Implementation

### Phase 1 Complete
All critical user onboarding and home screens are fully implemented and functional:

**Startup & Authentication Flow:**
- Splash screen with auto-navigation
- Role selection (tenant/landlord)
- Email/password authentication with validation
- User registration with password confirmation
- Guest mode support

**Onboarding Experience:**
- Preference setup with budget range slider (200K-5M UGX)
- Property type selection (Apartment, Studio, House, Room)
- Preferred area selection (Kololo, Nakasero, Ntinda, Bugolobi, Makindye, Kisaasi)
- Location permission request screen

**Home Screen:**
- Search bar with advanced filter access
- Property type filter chips
- Featured properties section (Kololo)
- Browse by neighborhood grid
- Nearby listings with property cards
- Bottom navigation (Home, Map, Saved, Profile)

**Recent Fixes:**
- Bottom navigation correctly hidden during onboarding
- Route-based navigation state management
- Location permission screen crash resolved
- Proper ViewModel scoping across feature modules

### Architecture Implementation
- Multi-module feature architecture (auth, onboarding, home, map, property, saved, profile, support)
- Jetpack Compose UI with Material 3 design system
- Hilt dependency injection
- Type-safe navigation with sealed route classes
- Shared app state management for auth/guest flows
- Clean separation of startup, onboarding, and main app flows
