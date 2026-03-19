# Kampala Homes Android (Foundation)

This directory contains the Phase 0 foundation scaffold for the client app.

## Included

1. Android + Compose project scaffold.
2. Canonical route map from implementation plan.
3. App state shell for guest/auth and onboarding flags.
4. NavHost wired with route placeholders.
5. Theme tokens aligned to approved palette (no color changes).

## Next

1. Replace placeholders with real P0 screens in this order:
   - splash -> role_selection -> auth -> registration -> preference_setup -> location_access -> home
2. Add guest route guards for save/profile/contact actions.
3. Implement bottom nav shell routes (home/map/saved/profile).
