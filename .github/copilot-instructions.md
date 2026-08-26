# Movie Night — Copilot Instructions

## Scope

These instructions define how the Presentation Layer should be implemented and refactored.

The target architecture is MVVM + UDF.

Some existing code may contain legacy patterns. Do not copy legacy patterns into new or refactored code.

## 1. Understand Before Modify

Before changing code:

1. Inspect the complete relevant feature.
2. Inspect its Fragment, ViewModel, UiState, UiEvent, UiEffect, Adapter, InteractionListener, and XML.
3. Understand the current data flow.
4. Identify legacy patterns and architectural violations.
5. Determine the minimum files that need to change.

Do not start editing after inspecting only one file.

For non-trivial tasks, explain the plan before implementation.

Do not modify unrelated code.

## 2. MVVM + UDF

The Presentation flow is:

UI
→ UiEvent
→ ViewModel
→ UiState / UiEffect
→ UI

The ViewModel owns the screen state.

User actions must enter the ViewModel through UiEvent.

The UI observes UiState and renders it.

One-time UI actions are emitted through UiEffect.

Do not introduce bidirectional data flow.

## 3. UiState

UiState represents persistent screen state.

Examples:

- Loading
- Success/content
- Empty
- Error
- Selected state
- Search query
- Filters

UiState should be immutable.

The ViewModel owns UiState.

Do not use UiState for one-time actions such as navigation or Snackbar.

## 4. UiEvent

UiEvent represents user intent coming from the UI.

Examples:

- ButtonClicked
- MovieClicked
- SearchQueryChanged
- RetryClicked
- FilterSelected

Prefer a single ViewModel entry point:

```kotlin
fun onEvent(event: UiEvent)