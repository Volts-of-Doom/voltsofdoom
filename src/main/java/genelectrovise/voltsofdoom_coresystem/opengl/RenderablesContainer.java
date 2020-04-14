package genelectrovise.voltsofdoom_coresystem.opengl;

import java.util.HashMap;

public class RenderablesContainer {
	public static RenderablesContainer instance = new RenderablesContainer();
	public HashMap<String, RenderableObj> renderObjs = new HashMap<String, RenderableObj>();

	public void addRenderObj(String key, RenderableObj obj) {
		renderObjs.put(key, obj);
	}
}
