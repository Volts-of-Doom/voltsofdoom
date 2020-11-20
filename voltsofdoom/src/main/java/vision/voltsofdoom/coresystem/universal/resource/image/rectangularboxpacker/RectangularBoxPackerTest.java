package vision.voltsofdoom.coresystem.universal.resource.image.rectangularboxpacker;

import java.util.ArrayList;

public class RectangularBoxPackerTest {
  public static void main(String[] args) {

    ArrayList<Box> boxes = new ArrayList<>();

    boxes.add(new Box("one", 5, 3));
    boxes.add(new Box("two", 4, 2));
    boxes.add(new Box("three", 3, 1));
    boxes.add(new Box("four", 2, 1));
    boxes.add(new Box("five", 1, 1));

    new RectangularBoxPacker(boxes).pack();

  }
}
