# J-mom project requirements

The idea of this project is to use the Kobweb framework to create a JSON pretty print utility with these features:
- Has a big Text widget at the center where users can paste or type their JSON data
- A button below the Text that reads "Validate" when the button is clicked, it will validate if the provided JSON is valid:
  - If it is invalid, an error message will be shown below the button
  - If it is valid, a message saying that the JSON is valid will show and the given JSON will be auto-formatted

## Implementation plan

### Phase 1: Create the UI
- [x] Replace the current UI in Index.kt with the layout for our JSON validator

### Phase 2: Add JSON validation
- [x] Configure the kotlinx.serialization library in the project using the version catalog and add it to the site module.
  We do not need to configure the kotlinx.serialization plugin, only the library as we won't have classes annotated with @Serializable.
- [x] Create a validateJson(json: String): Result<Unit> method that uses kotlinx.serialization to validate if a given JSON is valid.
  We return Unit when the validation succeeds, and an Exception with a custom message otherwise.
- [x] Add unit tests to confirm that the validateJson() method works as expected
- [x] Use the validateJson() method to validate if the JSON provided in the text field is valid or not, and show a message accordingly

### Phase 3: Pretty print JSON
- [ ] In the case that the provided JSON is valid, reformat the provided text so that it is pretty printed with a
  hardcoded indentation of 2 spaces
- [ ] Make indentation configuration by providing a selector above the Text input field where the user can select
  between 2, 4, 6 and 8 spaces of indentation.

### Phase 4: JSON editor improvements
- [ ] Add syntax highlighting to the JSON text field when the JSON has been validated. Turn off syntax highlighting as soon
  as the text is updated.
- [ ] Add line numbers for easier JSON reading, use a grayed out text for this to avoid confusing it with the JSON contents.
- [ ] Add functionality to collapse and expand JSON sections. We need gutter icons that will allow us to perform the
  collapse/expand action.
