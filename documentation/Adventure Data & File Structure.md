---


---

<h1 id="adventure-data--file-structure">Adventure Data &amp; File Structure</h1>
<h2 id="overview">Overview</h2>
<p>The Root is located in your system equivalent of <code>%AppData%\Roaming\voltsofdoom</code>, a.k.a. <code>${user.home}\Roaming\voltsofdoom</code></p>
<ul>
<li><strong>Root</strong>
<ul>
<li><strong>/adventures</strong>
<ul>
<li><strong>/data</strong>
<ul>
<li><strong>List of Adventures</strong>
<ul>
<li><code>adventure.json</code></li>
<li><strong>/levels</strong>
<ul>
<li><strong>List of Levels</strong>
<ul>
<li><code>entities.json</code></li>
<li><code>map.json</code></li>
<li><code>level.json</code></li>
<li><strong>/behaviors</strong>
<ul>
<li><code>List of Behaviors</code></li>
</ul>
</li>
<li><strong>/puzzles</strong>
<ul>
<li><code>List of Puzzles</code></li>
</ul>
</li>
</ul>
</li>
</ul>
</li>
<li><strong>/sheets</strong>
<ul>
<li><strong>/tiles</strong>
<ul>
<li><code>Tile Sheets</code></li>
</ul>
</li>
<li><strong>/entities</strong>
<ul>
<li><code>Entity Sheets</code></li>
</ul>
</li>
</ul>
</li>
</ul>
</li>
</ul>
</li>
</ul>
</li>
</ul>
</li>
</ul>
<h2 id="list-of-adventures">List of Adventures</h2>
<p>Each <code>Adventure</code> should be in the form of a <code>.zip</code> file, with the file structure (below “<strong>List of Adventures</strong>”) detailed above enclosed. The <code>adventure.json</code> file should be in the top level of the <code>.zip</code> file. The name of the <code>zip</code> file (disregarding file extension), should be: <code>&lt;registryName&gt;_&lt;version&gt;</code>, replacing the variable names with the values defined for them in the <code>adventure.json</code> file.</p>
<h1 id="the-structure-of-an-adventure">The Structure of an Adventure</h1>
<h3 id="notes-on-consistent-objects">Notes on Consistent Objects:</h3>
<p>In every case where these tags are found, follow the given syntax:</p>
<h4 id="identifier">identifier</h4>
<p><code>"identifier": {domain: &lt;domainName&gt;, entry: &lt;entry&gt;}</code>, where <code>domainName</code> and <code>entryName</code> are both <em>Short Strings</em></p>
<h4 id="data">data</h4>
<p><code>"data": {"tags":[{"key":&lt;key1&gt;,"value":&lt;value1&gt;}]}</code>, continuing the <code>tags</code> list in the same style for as long as necessary.</p>
<h2 id="adventure.json">adventure.json</h2>
<p>The <code>adventure.json</code> file contains basic, core data about the <code>Adventure</code> – i.e. metadata.</p>
<h4 id="registryname">registryName</h4>
<p><em><strong>Short String</strong></em> The internal name to be given to the <code>Adventure</code>. This will be used to store and access data for the <code>Adventure</code> within the <em>Volts of Doom</em> code.</p>
<h4 id="displayname">displayName</h4>
<p><em><strong>Short String</strong></em> The name of the <code>Adventure</code> to be shown to the player .</p>
<h4 id="description">description</h4>
<p><em><strong>Long String</strong></em> A basic description of the <code>Adventure</code>.</p>
<h4 id="levels">levels</h4>
<p><em><strong>Array of Short Strings</strong></em> The <em>registryName</em>s of the <code>Levels</code> to be added to the <code>Adventure</code>. This is used to locate the correct folder for each <code>Level</code>. See the <a href="https://github.com/Volts-of-Doom/voltsofdoom-coresystem/blob/feature-stackedit/Adventure%20Data%20&amp;%20File%20Structure.md#registryname-1">naming conventions of Levels</a>.</p>
<h2 id="sheets">Sheets</h2>
<p>A <code>Sheet</code> lays down a template which can be repeatedly used throughout an <code>Adventure</code>. This means that, if you wanted to modify every creature of a given type in each <code>Level</code> of an <code>Adventure</code> in the same way, instead of copying and pasting the same data tag into every <code>Level</code>'s <code>entities.json</code>, which can be error-prone, you can create a <code>Sheet</code> for that type of creature, and specify to use the <code>Sheet</code>'s data in the <code>Entity</code>'s key.</p>
<p>Other tags can override a <code>Sheet</code>.</p>
<p>Use of <code>Sheets</code> is not limited to <code>Entities</code>. Check the relevant section of this documentation to see when a <code>Sheet</code> can be used.</p>
<h4 id="identifier-1"><a href="https://github.com/Volts-of-Doom/voltsofdoom/blob/stackedit/documentation/Adventure%20Data%20%26%20File%20Structure.md#notes-on-consistent-objects">identifier</a></h4>
<p>Specifies the name of an Object from within the given <strong>domain</strong> which the <strong>key</strong> string should be bound to. The location of where to access this item from is inferred from where this <code>Sheet</code> is called from, i.e. if this <code>Sheet</code> is called from the <code>map.json</code> file, it is safe to assume that the <strong>identifier</strong> should reference a <code>Tile</code>.</p>
<h4 id="data-1"><a href="https://github.com/Volts-of-Doom/voltsofdoom/blob/stackedit/documentation/Adventure%20Data%20%26%20File%20Structure.md#data">data</a></h4>
<p>Configures the Object that the <strong>identifier</strong> references.</p>
<h1 id="the-structure-of-a-level">The Structure of a Level</h1>
<h3 id="note-on-naming-levels">Note on Naming Levels</h3>
<p>The root folder of the <code>Level</code> should match the <code>registryName</code> in the <code>level.json</code> file.</p>
<h2 id="level.json">level.json</h2>
<p>Provides basic information for the <code>Level</code>, much like <a href="https://github.com/Volts-of-Doom/voltsofdoom-coresystem/blob/feature-stackedit/Adventure%20Data%20&amp;%20File%20Structure.md#the-structure-of-an-adventure"><code>adventure.json</code></a> does for an <code>Adventure</code>.</p>
<h4 id="identifer"><a href="https://github.com/Volts-of-Doom/voltsofdoom/blob/stackedit/documentation/Adventure%20Data%20%26%20File%20Structure.md#notes-on-consistent-objects">identifer</a></h4>
<p>The internal identifier for this <code>Level</code>. The <strong>domain</strong> should match the <code>registryName</code> of the containing <code>Adventure</code>, whilst the <code>entry</code> is the <code>registryName</code> for this <code>Level</code>. (Defined here)</p>
<h4 id="displayname-1">displayName</h4>
<p><em>Short String</em> The name of the <code>Level</code> to be displayed to the player.</p>
<h4 id="description-1">description</h4>
<p><em>Long String</em> A basic description of the <code>Level</code></p>
<h2 id="map.json">map.json</h2>
<p>Provides a map of the <code>Tiles</code> in a <code>Level</code>.</p>
<h4 id="key">key</h4>
<p><em><strong>Array of JSON Objects, each of which contains the following values</strong></em><br>
A <code>Sheet</code> can replace the <em>domain</em>, <em>identifier</em>, and <em>data</em> tags.</p>
<ul>
<li><strong>key</strong>: <em>Short String or Character</em> An identifier unique within this array, used to specify where to place a <code>Tile</code> of this type in the <strong>map</strong> array.</li>
<li><a href="https://github.com/Volts-of-Doom/voltsofdoom/blob/stackedit/documentation/Adventure%20Data%20%26%20File%20Structure.md#notes-on-consistent-objects"><strong>identifier</strong></a> Identifier a <code>Tile</code> from within the given <strong>domain</strong> which the <strong>key</strong> should be bound to.</li>
<li><strong>(Optional) <a href="https://github.com/Volts-of-Doom/voltsofdoom/blob/stackedit/documentation/Adventure%20Data%20%26%20File%20Structure.md#data">data</a></strong>: <em>JSON Object</em> The contents of this JSON Object vary from <code>Tile</code> to <code>Tile</code>. These values are used to customise the properties of the <code>Tile</code> specified by the <strong>identifier</strong>.</li>
</ul>
<h4 id="map">map</h4>
<p><em>Array of Nested-Arrays of Strings</em> The actual layout of the <code>Level</code>. Each nested-array signifies another layer in the <code>Level</code>, with the <code>Tiles</code> being added from the <em>bottom-left</em> of the screen (first array, first item), to the <em>top-right</em> (last array, last item). Each item in each array should be a <em>String or Character</em> equal to a key found in the <strong>key</strong> section of this file.</p>
<h2 id="entities.json">entities.json</h2>
<p>Provides the locations of every <code>Entity</code> in the Level.</p>
<h4 id="key-1">key</h4>
<p>See <a href="https://github.com/Volts-of-Doom/voltsofdoom/blob/stackedit/documentation/Adventure%20Data%20%26%20File%20Structure.md#key"><code>map.json#key</code></a>, replacing references to <code>Tiles</code> with <code>Entities</code>.</p>
<h4 id="map-1">map</h4>
<p><em><strong>Array of JSON Objects, each of which contains the following values</strong></em><br>
A Sheet can replace the <em>domain</em>, <em>identifier</em>, and <em>data</em> tags.</p>
<ul>
<li><strong>key</strong>: <em>Short String</em> An identifier unique within this array, used to specify which <code>Entity</code> from the <strong>key</strong> is being referenced.</li>
<li><strong>coordinate</strong>: <em>Json Object</em> Follows the format: <code>"coordinate":{"x":&lt;x&gt;, "y":&lt;y&gt;}</code>, where <code>x</code> and <code>y</code> are integers. <code>Coordinates</code> are measured from the bottom-left corner, in proper mathematical fashion.</li>
<li><strong>(Optional) <a href="https://github.com/Volts-of-Doom/voltsofdoom/blob/stackedit/documentation/Adventure%20Data%20%26%20File%20Structure.md#data">data</a></strong>: Customises the properties of the <code>Entity</code> specified by the <strong>identifier</strong> in the <strong>key</strong>. <em><strong>This data tag will override both the data set in the key, AND any Sheet applied!</strong></em></li>
</ul>
<h2 id="puzzles">Puzzles</h2>
<p>The <code>/puzzles</code> folder contains the puzzles which can be found in the <code>Level</code>.</p>
<h3 id="t.b.c.">T.B.C.</h3>
<h2 id="behaviours">Behaviours</h2>
<p>The <code>/behaviors</code> folder contains additional behaviours which will be applied to a <code>Level</code>. This might include extra challenges, such as an entire <code>Level</code> flooding over time.</p>
<h3 id="t.b.c.-1">T.B.C.</h3>

