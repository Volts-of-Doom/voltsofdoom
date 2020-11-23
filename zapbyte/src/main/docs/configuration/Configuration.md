# Configuration

The *ZapByte* configuration system drives preferences for many things within both the loader itself, and the applications it manages.

## ConfigurationFile

Represents a `Map` of configuration options which is both loaded from, and persisted as a JSON file. 

Contains a `java.util.Map<String, String>` of arguments, which is read and accessed by the methods `getArguments()`, to return the `Map`, or `putArgument(String key, String value)`, which is equivalent to the `put(K, V)` method present in the `java.util.Map` class.

## ConfigHandler

`implements vision.voltsofdoom.api.zapbyte.config.IConfigHandler`

Handles the master configuration settings for *ZapByte*. An instance of this class is created when *ZapByte* is loaded, as one of the first objects to be created.

Tries to read `config/config.json`. If found, it uses *GSON* to create a new `ConfigurationFile` from the contained data. If the configuration file is not present, then it will create a new one, write a default string to it (see below), then load the file as if it was always present.

```json
// The default arguments for the master ConfigurationFile.
// The arguments are built from: "vmargs" + defaultArgs
// Here, 'defaultArgs' is a String[]

{
    "vmargs":"argument"
}
```

