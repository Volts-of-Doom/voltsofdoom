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
<div class="mermaid"><svg xmlns="http://www.w3.org/2000/svg" id="mermaid-svg-lSUSNJfAidYSLUAk" width="100%" style="max-width: 344.8359375px;" viewBox="0 0 344.8359375 811.9187469482422"><g transform="translate(-12, -12)"><g class="output"><g class="clusters"></g><g class="edgePaths"><g class="edgePath" style="opacity: 1;"><path class="path" d="M180.1796875,109.82499847412107L179.6796875,134.3249969482422L179.6796875,159.3249969482422" marker-end="url(#arrowhead19103)" style="fill:none"></path><defs><marker id="arrowhead19103" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1; stroke-dasharray: 1, 0;"></path></marker></defs></g><g class="edgePath" style="opacity: 1;"><path class="path" d="M179.6796875,205.3249969482422L179.6796875,230.3249969482422L179.6796875,255.3249969482422" marker-end="url(#arrowhead19104)" style="fill:none"></path><defs><marker id="arrowhead19104" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1; stroke-dasharray: 1, 0;"></path></marker></defs></g><g class="edgePath" style="opacity: 1;"><path class="path" d="M179.6796875,301.3249969482422L179.6796875,326.3249969482422L179.6796875,351.3249969482422" marker-end="url(#arrowhead19105)" style="fill:none"></path><defs><marker id="arrowhead19105" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1; stroke-dasharray: 1, 0;"></path></marker></defs></g><g class="edgePath" style="opacity: 1;"><path class="path" d="M122.54654947916666,397.3249969482422L60.4453125,422.3249969482422L60.4453125,465.9031219482422" marker-end="url(#arrowhead19106)" style="fill:none"></path><defs><marker id="arrowhead19106" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1; stroke-dasharray: 1, 0;"></path></marker></defs></g><g class="edgePath" style="opacity: 1;"><path class="path" d="M179.6796875,397.3249969482422L179.6796875,422.3249969482422L179.6796875,465.9031219482422" marker-end="url(#arrowhead19107)" style="fill:none"></path><defs><marker id="arrowhead19107" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1; stroke-dasharray: 1, 0;"></path></marker></defs></g><g class="edgePath" style="opacity: 1;"><path class="path" d="M240.81087239583334,397.3249969482422L307.2578125,422.3249969482422L307.2578125,447.3249969482422" marker-end="url(#arrowhead19108)" style="fill:none"></path><defs><marker id="arrowhead19108" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1; stroke-dasharray: 1, 0;"></path></marker></defs></g><g class="edgePath" style="opacity: 1;"><path class="path" d="M148.60188886118283,511.9031219482422L89.71875,555.4812469482422L89.71875,580.4812469482422" marker-end="url(#arrowhead19109)" style="fill:none"></path><defs><marker id="arrowhead19109" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1; stroke-dasharray: 1, 0;"></path></marker></defs></g><g class="edgePath" style="opacity: 1;"><path class="path" d="M210.75748613881717,511.9031219482422L269.640625,555.4812469482422L269.640625,580.4812469482422" marker-end="url(#arrowhead19110)" style="fill:none"></path><defs><marker id="arrowhead19110" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1; stroke-dasharray: 1, 0;"></path></marker></defs></g><g class="edgePath" style="opacity: 1;"><path class="path" d="M269.640625,626.4812469482422L269.640625,651.4812469482422L269.640625,685.9968719482422" marker-end="url(#arrowhead19111)" style="fill:none"></path><defs><marker id="arrowhead19111" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1; stroke-dasharray: 1, 0;"></path></marker></defs></g><g class="edgePath" style="opacity: 1;"><path class="path" d="M89.71875,626.4812469482422L89.71875,651.4812469482422L89.71875,676.4812469482422" marker-end="url(#arrowhead19112)" style="fill:none"></path><defs><marker id="arrowhead19112" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1; stroke-dasharray: 1, 0;"></path></marker></defs></g></g><g class="edgeLabels"><g class="edgeLabel" transform="" style="opacity: 1;"><g transform="translate(0,0)" class="label"><foreignObject width="0" height="0"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel"></span></div></foreignObject></g></g><g class="edgeLabel" transform="" style="opacity: 1;"><g transform="translate(0,0)" class="label"><foreignObject width="0" height="0"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel"></span></div></foreignObject></g></g><g class="edgeLabel" transform="" style="opacity: 1;"><g transform="translate(0,0)" class="label"><foreignObject width="0" height="0"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel"></span></div></foreignObject></g></g><g class="edgeLabel" transform="" style="opacity: 1;"><g transform="translate(0,0)" class="label"><foreignObject width="0" height="0"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel"></span></div></foreignObject></g></g><g class="edgeLabel" transform="" style="opacity: 1;"><g transform="translate(0,0)" class="label"><foreignObject width="0" height="0"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel"></span></div></foreignObject></g></g><g class="edgeLabel" transform="" style="opacity: 1;"><g transform="translate(0,0)" class="label"><foreignObject width="0" height="0"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel"></span></div></foreignObject></g></g><g class="edgeLabel" transform="" style="opacity: 1;"><g transform="translate(0,0)" class="label"><foreignObject width="0" height="0"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel"></span></div></foreignObject></g></g><g class="edgeLabel" transform="" style="opacity: 1;"><g transform="translate(0,0)" class="label"><foreignObject width="0" height="0"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel"></span></div></foreignObject></g></g><g class="edgeLabel" transform="" style="opacity: 1;"><g transform="translate(0,0)" class="label"><foreignObject width="0" height="0"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel"></span></div></foreignObject></g></g><g class="edgeLabel" transform="" style="opacity: 1;"><g transform="translate(0,0)" class="label"><foreignObject width="0" height="0"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel"></span></div></foreignObject></g></g></g><g class="nodes"><g class="node" id="root" transform="translate(179.6796875,64.6624984741211)" style="opacity: 1;"><polygon points="44.6625,0 89.325,-44.6625 44.6625,-89.325 0,-44.6625" rx="5" ry="5" transform="translate(-44.6625,44.6625)"></polygon><g class="label" transform="translate(0,0)"><g transform="translate(-16.625,-13)"><foreignObject width="33.25" height="26"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">Root</div></foreignObject></g></g></g><g class="node" id="adventures" transform="translate(179.6796875,182.3249969482422)" style="opacity: 1;"><rect rx="5" ry="5" x="-52.1640625" y="-23" width="104.328125" height="46"></rect><g class="label" transform="translate(0,0)"><g transform="translate(-42.1640625,-13)"><foreignObject width="84.328125" height="26"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">/adventures</div></foreignObject></g></g></g><g class="node" id="data" transform="translate(179.6796875,278.3249969482422)" style="opacity: 1;"><rect rx="5" ry="5" x="-28.5546875" y="-23" width="57.109375" height="46"></rect><g class="label" transform="translate(0,0)"><g transform="translate(-18.5546875,-13)"><foreignObject width="37.109375" height="26"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">/data</div></foreignObject></g></g></g><g class="node" id="adventure_list" transform="translate(179.6796875,374.3249969482422)" style="opacity: 1;"><rect rx="0" ry="0" x="-72.578125" y="-23" width="145.15625" height="46"></rect><g class="label" transform="translate(0,0)"><g transform="translate(-62.578125,-13)"><foreignObject width="125.15625" height="26"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">List of adventures</div></foreignObject></g></g></g><g class="node" id="levels" transform="translate(60.4453125,488.9031219482422)" style="opacity: 1;"><rect rx="5" ry="5" x="-33.234375" y="-23" width="66.46875" height="46"></rect><g class="label" transform="translate(0,0)"><g transform="translate(-23.234375,-13)"><foreignObject width="46.46875" height="26"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">/levels</div></foreignObject></g></g></g><g class="node" id="sheets" transform="translate(179.6796875,488.9031219482422)" style="opacity: 1;"><rect rx="5" ry="5" x="-36" y="-23" width="72" height="46"></rect><g class="label" transform="translate(0,0)"><g transform="translate(-26,-13)"><foreignObject width="52" height="26"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">/sheets</div></foreignObject></g></g></g><g class="node" id="adventure_data" transform="translate(307.2578125,488.9031219482422)" style="opacity: 1;"><circle x="-41.578125" y="-23" r="41.578125"></circle><g class="label" transform="translate(0,0)"><g transform="translate(-31.578125,-13)"><foreignObject width="63.15625" height="26"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">data.json</div></foreignObject></g></g></g><g class="node" id="sheets-entities" transform="translate(89.71875,603.4812469482422)" style="opacity: 1;"><rect rx="5" ry="5" x="-39.4296875" y="-23" width="78.859375" height="46"></rect><g class="label" transform="translate(0,0)"><g transform="translate(-29.4296875,-13)"><foreignObject width="58.859375" height="26"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">/entities</div></foreignObject></g></g></g><g class="node" id="sheets-tiles" transform="translate(269.640625,603.4812469482422)" style="opacity: 1;"><rect rx="5" ry="5" x="-28.0390625" y="-23" width="56.078125" height="46"></rect><g class="label" transform="translate(0,0)"><g transform="translate(-18.0390625,-13)"><foreignObject width="36.078125" height="26"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">/tiles</div></foreignObject></g></g></g><g class="node" id="tile_sheet" transform="translate(269.640625,746.1999969482422)" style="opacity: 1;"><circle x="-60.203125" y="-23" r="60.203125"></circle><g class="label" transform="translate(0,0)"><g transform="translate(-50.203125,-13)"><foreignObject width="100.40625" height="26"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">tile_sheet.json</div></foreignObject></g></g></g><g class="node" id="entity_sheet" transform="translate(89.71875,746.1999969482422)" style="opacity: 1;"><circle x="-69.71875" y="-23" r="69.71875"></circle><g class="label" transform="translate(0,0)"><g transform="translate(-59.71875,-13)"><foreignObject width="119.4375" height="26"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">entity_sheet.json</div></foreignObject></g></g></g></g></g></g></svg></div>