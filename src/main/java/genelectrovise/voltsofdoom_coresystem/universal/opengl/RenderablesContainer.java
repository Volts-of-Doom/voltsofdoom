package genelectrovise.voltsofdoom_coresystem.universal.opengl;

import java.util.HashMap;

/**
 * Contains a load of {@link RenderableObj}s
 * @author adam_
 *
 */
public class RenderablesContainer {
	public HashMap<String, RenderableObj> renderObjs = new HashMap<String, RenderableObj>();
	
	public void addRenderObj(String key, RenderableObj obj) {
		renderObjs.put(key, obj);
	}
}
