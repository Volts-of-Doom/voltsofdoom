### Notes on running this version:

#### Resources

Resources are expected in <user.home>/AppData/Roaming/voltsofdoom, as follows:

- /mods and /img directories from the Examples section
- /resources/adventures and from the Examples section.

IntelliJ config currently does not allow code to run without re-running from the command line.

#### Building


To build on Linux, I needed to install the LWJGL native libraries: 

`sudo apt-get install -y liblwjgl-java-doc`

To do: Confirm whether LWJGL files need to be in resources/lib. Is there a better location?
