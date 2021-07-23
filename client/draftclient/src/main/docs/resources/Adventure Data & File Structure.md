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
<li><code>data.json</code></li>
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
<p>Each Adventure should be in the form of a <code>.zip</code> file, with the file structure (below “<strong>List of Adventures</strong>”) detailed above enclosed. The <code>data.json</code> file should be in the top level of the <code>.zip</code> file.</p>
<h1 id="the-structure-of-an-adventure">The Structure of an Adventure</h1>
<h2 id="data.json">data.json</h2>
<p>The <code>data.json</code> file contains basic, core data about the Adventure – i.e. metadata.</p>
<h4 id="registryname">registryName</h4>
<p><em><strong>Short String</strong></em> The internal name to be given to the Adventure. This will be used to store and access data for the Adventure within the <em>Volts of Doom</em> code.</p>
<h4 id="displayname">displayName</h4>
<p><em><strong>Short String</strong></em> The name of the Adventure to be shown to the player .</p>
<h4 id="description">description</h4>
<p><em><strong>Long String</strong></em> A basic description of the Adventure.</p>
<h4 id="levels">levels</h4>
<p><em><strong>Array of Short Strings</strong></em> The <em>registryName</em>s of the Levels to be added to the Adventure. This is used to locate the correct folder for each Level. See the <a href="https://github.com/Volts-of-Doom/voltsofdoom-coresystem/blob/feature-stackedit/Adventure%20Data%20&amp;%20File%20Structure.md#registryname-1">naming conventions of Levels</a>.</p>
<h2 id="sheets">Sheets</h2>
<p>A Sheet lays down a template which can be repeatedly used throughout an Adventure. This means that, if you wanted to modify every creature of a given type in each Level of an Adventure in the same way, instead of copying and pasting the same data tag into every Level’s <code>entities.json</code>, which can be error-prone, you can create a Sheet for that type of creature, and specify to use the sheet’s data in the Entity’s key.</p>
<p>If a Sheet tag is specified, it will override any other tags.</p>
<p>Use of Sheets is not limited to Entities. Check the relevant section of this documentation to see when a Sheet can be used.</p>
<h4 id="domain">domain</h4>
<p><em>Short String</em> The first half of a <em>Volts of Doom <strong>Resource Location</strong></em>. Specifies which mod or Adventure the <strong>identifier</strong> is from.</p>
<h4 id="identifier">identifier</h4>
<p><em>Short String</em> The second half of a <em>Volts of Doom <strong>Resource Location</strong></em>. Specifies the name of am Object from within the given <strong>domain</strong> which the <strong>key</strong> string should be bound to. The location of where to access this item from is inferred from where this Sheet is called from, i.e. if this Sheet is called from the <code>map.json</code> file, it is safe to assume that the <strong>identifier</strong> should reference a Tile.</p>
<h4 id="data">data</h4>
<p><em>JSON Object</em> Varies. Configures the Object that the <strong>identifier</strong> references.</p>
<h1 id="the-structure-of-a-level">The Structure of a Level</h1>
<h2 id="level.json">level.json</h2>
<p>Provides basic information for the Level, much like <a href="https://github.com/Volts-of-Doom/voltsofdoom-coresystem/blob/feature-stackedit/Adventure%20Data%20&amp;%20File%20Structure.md#the-structure-of-an-adventure"><code>data.json</code></a> does for an Adventure.</p>
<h4 id="registryname-1">registryName</h4>
<p><em><strong>Short String</strong></em> The internal name for the Level. This should match the name of the root folder of the Level, i.e this string should match the name of this Level’s folder in the <strong>List of Levels</strong>. If it doesn’t, problems will arise.</p>
<h4 id="displayname-1">displayName</h4>
<p><em><strong>Short String</strong></em> The name of the Level to be displayed to the player.</p>
<h2 id="map.json">map.json</h2>
<p>Provides a map of the Tiles in a Level.</p>
<h4 id="key">key</h4>
<p><em><strong>Array of JSON Objects, each of which contains the following values</strong></em><br>
A Sheet can replace the <em>domain</em>, <em>identifier</em>, and <em>data</em> tags.</p>
<ul>
<li><strong>key</strong>: <em>Short String</em> An identifier unique within this array, used to specify where to place a Tile of this type in the <strong>map</strong> array.</li>
<li><strong>domain</strong>: <em>Short String</em> The first half of a <em>Volts of Doom <strong>Resource Location</strong></em>. Specifies which mod or Adventure the <strong>identifier</strong> is from.</li>
<li><strong>identifier</strong>: <em>Short String</em> The second half of a <em>Volts of Doom <strong>Resource Location</strong></em>. Specifies the name of a Tile from within the given <strong>domain</strong> which the <strong>key</strong> string should be bound to.</li>
<li><strong>(Optional) data</strong>: <em>JSON Object</em> The contents of this JSON Object vary from Tile to Tile. These values are used to customise the properties of the Tile specified by the <strong>domain</strong> and <strong>identifier</strong>.</li>
</ul>
<h4 id="map">map</h4>
<p><em>Array of Nested-Arrays of Strings</em> The actual layout of the Level. Each nested-array signifies another Layer in the Level, with the Tiles being added from the <em>top-left</em> of the screen (first array, first item), to the <em>bottom-right</em> (last array, last item). Each item in each array should be a string equal to a key found in the <strong>key</strong> section of this file.</p>
<h2 id="entities.json">entities.json</h2>
<p>Provides the locations of every Entity in the Level.</p>
<h4 id="key-1">key</h4>
<p>See <a href="https://github.com/Volts-of-Doom/voltsofdoom-coresystem/blob/feature-stackedit/Adventure%20Data%20&amp;%20File%20Structure.md#key"><code>map.json#key</code></a></p>
<h4 id="map-1">map</h4>
<p><em><strong>Array of JSON Objects, each of which contains the following values</strong></em><br>
A Sheet can replace the <em>domain</em>, <em>identifier</em>, and <em>data</em> tags.</p>
<ul>
<li><strong>key</strong>: <em>Short String</em> An identifier unique within this array, used to specify which Entity from the <strong>key</strong> is being referenced.</li>
<li><strong>x</strong>: <em>Integer</em> The X coordinate that the entity should be placed at. Counted from the left, to the right.</li>
<li><strong>y</strong>: <em>Integer</em> The Y coordinate that the entity should be placed at. Counted from the bottom, up.</li>
<li><strong>(Optional) data</strong>: <em>JSON Object</em> The contents of this JSON Object vary from Entity to Entity . These values are used to customise the properties of the Entity specified by the <strong>domain</strong> and <strong>identifier</strong> in the <strong>key</strong>. <em><strong>This data tag will override both the data set in the key, AND any Sheet applied!</strong></em></li>
</ul>
<h2 id="puzzles">Puzzles</h2>
<p>The <code>/puzzles</code> folder contains the puzzles which can be found in the Level.</p>
<h3 id="t.b.c.">T.B.C.</h3>
<h2 id="behaviours">Behaviours</h2>
<p>The <code>/behaviors</code> folder contains additional behaviours which will be applied to a Level. This might include extra challenges, such as an entire Level flooding over time.</p>
<h3 id="t.b.c.-1">T.B.C.</h3>

