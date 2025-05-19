# Yeison project requirements

The idea of this project is to use the Kobweb framework to create a JSON pretty print utility with these features:
- Has a big Text widget at the center where users can paste or type their JSON data
- A button below the Text that reads "Validate", when the button is clicked it will validate if the provided JSON is valid:
  - If it is invalid, an error message will be shown below the button
  - If it is valid, a message saying that the JSON is valid will show and the given JSON will be auto-formatted

## Implementation plan

### Phase 1: Create the UI
- [x] Replace the current UI in Index.kt with the layout for our JSON validator
