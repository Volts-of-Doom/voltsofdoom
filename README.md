
Notes on running this version:

Resources are expected in <user.home>/AppData/Roaming/voltsofdoom, as follows:

- /mods and /img directories from the Examples section
- /resources/adventure and /resources/levelmap from the Examples section.

IntelliJ config currently does not allow code to run without re-running from the command line.


To build on Linux, I needed to install the LWJGL native libraries: 

<code>sudo apt-get install -y liblwjgl-java-doc</code>

To do: Confirm whether LWJGL files need to be in resources/lib. Is ther a better location?
