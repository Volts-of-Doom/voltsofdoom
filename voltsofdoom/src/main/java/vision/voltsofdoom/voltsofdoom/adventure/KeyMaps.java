package vision.voltsofdoom.voltsofdoom.adventure;

import java.util.List;

public class KeyMaps {

  public static KeyNode getNodeByKey(List<KeyNode> keyList, String target) {
    for (KeyNode keyNode : keyList) {
      if (keyNode.getKey().contentEquals(target)) {
        return keyNode;
      }
    }

    throw new NullPointerException("No KeyNode by name of '" + target + "' in listed KeyNodes.");
  }
}
