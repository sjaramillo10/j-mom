# J-mom Project Guidelines

## Project Overview
J-mom is a JSON pretty print utility built with the Kobweb framework (a Kotlin web framework). The application provides a web interface where users can:
- Paste or type JSON data in a text area
- Validate the JSON to check if it's valid
- See formatted (pretty-printed) JSON with configurable indentation (planned feature)
- Use advanced editor features like syntax highlighting, line numbers, and collapsible sections (planned feature)

## Project Structure
- `/site/` - Main project directory containing the Kobweb application
  - `/src/jsMain/` - Main source code
    - `/kotlin/dev/sjaramillo/jmom/` - Application code
      - `/components/` - Reusable UI components
      - `/pages/` - Page definitions (including Index.kt with the main UI)
      - `/utils/` - Utility functions (including JsonValidator.kt)
  - `/src/jsTest/` - Test code
    - `/kotlin/dev/sjaramillo/jmom/utils/` - Tests for utility functions

## Implementation Status
The project is being implemented in phases. The progress can be found in the [requirements.md](../requirements.md) file.

## Testing Guidelines
- When implementing new features or fixing bugs, always run the relevant tests to ensure functionality works as expected
- Tests can be run using Gradle tasks:
  - `./gradlew jsNodeTest` - Run all JS tests in a NodeJS environment (recommended for unit tests)
  - `./gradlew jsBrowserTest` - Run all JS tests in a browser environment (requires browser installation)
  - `./gradlew jsTest` - Run all JS tests (both NodeJS and browser tests)
  - `./gradlew allTests` - Run all tests and create an aggregated report
- Add new tests for any new functionality or edge cases

## Code Style Guidelines
- Follow Kotlin coding conventions
- Use descriptive variable and function names
- Add appropriate documentation comments for public functions
- Keep functions focused on a single responsibility
- Use Compose UI patterns consistent with the existing codebase

## Build and Run Instructions
- The project uses Kobweb for development and building
- To run the development server: `cd site && kobweb run`
- To build for production: `kobweb export --layout static`
- The project is automatically deployed to GitHub Pages when changes are pushed to the main branch

## Implementation Notes
- When implementing new features, ensure they work in both light and dark color modes
- The UI should be responsive and work well on different screen sizes
- Error messages should be clear, fun and helpful to the user
- Update the README.md file after implementing a feature to make sure it does not get stale.
