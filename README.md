# J-mom: JSON Validator

## Project Overview
J-mom is a JSON pretty print utility built with the [Kobweb](https://github.com/varabyte/kobweb) framework (a Kotlin web framework). The application provides a web interface where users can:
- Paste or type JSON data in a text area
- Validate the JSON to check if it's valid
- See formatted (pretty-printed) JSON with configurable indentation (planned feature)
- Use advanced editor features like syntax highlighting, line numbers, and collapsible sections (planned feature)

This project is being vibe coded with a lot of patience and JetBrains Junie.

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
The project is being implemented in phases. The progress can be found in the [requirements.md](requirements.md) file.

## Testing Guidelines
- When implementing new features or fixing bugs, always run the relevant tests to ensure functionality works as expected
- Tests can be run using Gradle tasks:
  - `./gradlew jsNodeTest` - Run all JS tests in a NodeJS environment (recommended for unit tests)
  - `./gradlew jsBrowserTest` - Run all JS tests in a browser environment (requires browser installation)
  - `./gradlew jsTest` - Run all JS tests (both NodeJS and browser tests)
  - `./gradlew allTests` - Run all tests and create an aggregated report
- Add new tests for any new functionality or edge cases

## Getting Started

First, run the development server by typing the following command in a terminal under the `site` folder:

```bash
$ cd site
$ kobweb run
```

Open [http://localhost:8080](http://localhost:8080) with your browser to see the result.

You can use any editor you want for the project, but we recommend using **IntelliJ IDEA Community Edition** downloaded
using the [Toolbox App](https://www.jetbrains.com/toolbox-app/).

Press `Q` in the terminal to gracefully stop the server.


### Live Reload

While Kobweb is running, feel free to modify the code! When you make any changes, Kobweb will notice this
automatically, and the site will indicate the status of the build and automatically reload when ready.
