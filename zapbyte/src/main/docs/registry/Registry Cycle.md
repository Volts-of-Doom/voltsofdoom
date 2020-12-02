# Registry Cycle

The `Registry` is loaded through calling the following `ZapBits` in `ZapByte#collectZapbits()`

```java
    addZapBit(DefaultZapBits.CREATE_REFLECTORIES_20);
    addZapBit(DefaultZapBits.SCAN_FOR_MODS_30);
    addZapBit(DefaultZapBits.CREATE_BANDWAGON_40);
	addZapBit(DefaultZapBits.CREATE_REGISTRY_CREATE_REGISTRY_TYPES_50);
	addZapBit(DefaultZapBits.CREATE_REGISTRY_CREATE_AND_SUBMIT_TYPE_REGISTRIES_54);
	addZapBit(DefaultZapBits.CREATE_REGISTRY_POPULATE_TYPE_REGISTRIES_58);
	addZapBit(VODZapBits.CREATE_REGISTRY_GENERATE_ADVENTURES_62);
	addZapBit(DefaultZapBits.CREATE_REGISTRY_POLL_REGISTRY_TYPES_68);
```

## Relevant ZapBits

#### CREATE_REFLECTORIES_20

*Priority: 20*

Directly calls `Reflectories#generate()`, which undergoes the following steps:



Calls `JarMapper#find()` to get a list of all `Files` in the */mods* directory.



For each found `File`:

Create a new `Reflectory.Builder` using `Reflectory.Builder#defaultBuilder()`.

Apply a new `VODClassLoader` and the name of the parent `File` to the `Reflectory.Builder`.

Build the `Reflectory` and put it into the *reflectories* (`Map<String, Reflectory>`).

Applies the same process for each additional `Class` that has been force added through `addAdditionalClass(String, Class)`.

Repeat one final time to make a `Reflectory` for `ZapByte.class`.



#### SCAN_FOR_MODS_30

*Priority: 30*

Calls `Mods#generate(Collection<Reflectory>)`, passing in `Reflectories#values()`

Uses the `org.reflections` library to get every Type annotated with the  `Mod.class` annotation, and puts them in a `Set<Class<?>>`

Gets the `Mod` annotation from each found class, and puts it into a `LinkedHashMap<String, Mod>`, where the associated `String` is `Mod#modid()`



#### 	CREATE_BANDWAGON_40

*Priority: 40*

This is what this has all been warming up to so far! The `BandWagon` is essential: it's the `ZapByte` event system, and is essential to the rest of the loading cycle!



The `BandWagon` collects the subscribers for itself, using the `Collection` of `Reflectory`'s that was created during **CREATE_REFLECTORIES_20**.

Gets all of the methods annotated with `@Stowaway` in every `Reflectory`, and places them in a `Set<Method>`.

Adds all of these `Method`'s to the master `HashSet<Method>`.



Finds every Type annotated with `@Stowaway`, then gets every `Method` in it, adding each `Method` to the master `HashSet<Method>`.



Iterates through each `Method` in the `HashSet`, and tests each one for validity (`BandWagon#validateMethod(Method)`).

Every `Method` which is found to be valid gets `stowawayMethod(Method)` called for it, which finally subscribes the `Method` to the `BandWagon`.



**<u>This is where the loading cycle gets a bit confusing, because it begins to be run by the `BandWagon`.</u>**



#### 	CREATE_REGISTRY_CREATE_REGISTRY_TYPES_50

*Priority: 50*

Instructs listener methods to create `RegistryType`'s, like so.

```java
  public static RegistryType<ClassThatExtendsRegistryEntry> TYPE;
      
  @Stowaway
  private static void generateTypes(RegistryEvent.CreateRegistryTypesEvent event) {
      ResourceLocation location = new ResourceLocation("your_modid", "class_extending_registry_entry");
      TYPE = event.createRegistryType(location, ClassThatExtendsRegistryEntry.class);
  }
```



In the case of `coresystem.universal.registry.RegistryTypes`, this also sets the `zapbyte.loading.registry.RegistryTypes` *prioritisedTypes*, which defines types which should be loaded first when creating the `Registry` later on.



#### CREATE_REGISTRY_CREATE_AND_SUBMIT_TYPE_REGISTRIES_54

*Priority: 54*

Instructs listener methods to create their `TypeRegistry`'s, and submit them to the `CollectedRegistries`, like so.

```java
  @Stowaway
  private static void createAndSubmitTypeRegistries(RegistryEvent.CreateAndSubmitRegistriesEvent event) {
    event.submit(new TypeRegistry<TYPE_NAME>(new ResourceLocation("your_modid", "type_name"), RegistryTypes.TYPE_NAME));
  }
```

*Note: You should create the `TypeRegistry` globally, and only populate it in the event!*



Creating `TypeRegistry`'s during this event is advisable, because it ensures that the given `RegistryType` is not null, assuming that the `RegistryType` was created properly.

Submitting during the event makes the loader aware of your `TypeRegistry`, so that its contents will be applied later.



#### CREATE_REGISTRY_POPULATE_TYPE_REGISTRIES_58

*Priority: 58*

```java
  @Stowaway
  private static void populateTypeRegistriesEventListener(RegistryEvent.PopulateTypeRegistriesEvent event) {
    RegistryMessenger<TypeName> type = 
        TYPE_NAME.register(new ResourceLocation("your_modid", "type_entry"), () -> new TypeName());
  }
```

*Note: You should declare the contents globally, then create and add them in the event!*



Creating the contents of your `TypeRegistry`'s here makes sure that the `TypeRegistry` that you are trying to use is not null!



Note that `TYPE_NAME` is a `TypeRegistry<TypeName>`: this matches with the type of the object you want to create! (`"RegistryMessenger<TypeName> type"`)



#### CREATE_REGISTRY_POLL_REGISTRY_TYPES_68

Plays the `PollRegistryTypeEventsEvent`.

Creates the `Registry`!

The only caller in *ZapByte* is `@Stowaway TypeRegistry#pollRegistryTypeEvents(PollRegistryTypeEventsEvent event)`.



For each submitted `TypeRegistry`, if the `Registry` contains a `FinalisedTypeRegistry` of the right type, dump the `TypeRegistry`'s contents into the `FinalisedTypeRegistry`, otherwise create a new `FinalisedTypeRegistry`, then dump.



Handles `IRegistry`'s whose types are listed in `RegistryTypes#prioritisedTypes` first.