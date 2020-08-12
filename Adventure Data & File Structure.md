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
<p><em><strong>Array of Short Strings</strong></em> The <em>registryName</em>s of the Levels to be added to the Adventure. This is used to locate the correct folder for each Level. See the naming conventions of Levels.</p>
<h2 id="sheets">Sheets</h2>
<p>A Sheet lays down a template which can be repeatedly used throughout an Adventure. This means that, if you wanted to modify every creature of a given type in each Level of an Adventure in the same way, instead of copying and pasting the same data tag into every Level’s <code>entities.json</code>, which can be error-prone, you can create a Sheet for that type of creature, and specify to use the sheet’s data in the Entity’s key.</p>
<h4 id="domain">domain</h4>
<p>(See <code>entities.json#domain</code>)</p>
<h4 id="identifier">identifier</h4>
<p>(See <code>entities.json#identifier</code>)</p>
<h4 id="data">data</h4>
<p>(See <code>entities.json#data</code>)</p>
<h1 id="the-structure-of-a-level">The Structure of a Level</h1>

