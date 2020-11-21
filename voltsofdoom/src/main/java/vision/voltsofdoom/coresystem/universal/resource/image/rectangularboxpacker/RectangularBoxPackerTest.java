package vision.voltsofdoom.coresystem.universal.resource.image.rectangularboxpacker;

import java.util.ArrayList;

public class RectangularBoxPackerTest {
  public static void main(String[] args) {

    ArrayList<Box> boxes = new ArrayList<>();

    boxes.add(new Box("one", 7, 2));
    boxes.add(new Box("two", 3, 3));
    boxes.add(new Box("three", 2, 2));

    new RectangularBoxPacker(boxes).pack();

  }
}
