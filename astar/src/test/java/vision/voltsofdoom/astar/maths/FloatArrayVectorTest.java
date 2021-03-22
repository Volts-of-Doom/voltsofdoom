package vision.voltsofdoom.astar.maths;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FloatArrayVectorTest {

  @Test
  void whenVectorsSameLength_thenReturnOperable() throws NoSuchMethodException, SecurityException {
    FloatArrayVector mockVec1 = Mockito.mock(FloatArrayVector.class);
    Mockito.when(mockVec1.getValues()).thenReturn(new float[] {1.0f, 2.0f, 3.0f});

    FloatArrayVector mockVec2 = Mockito.mock(FloatArrayVector.class);
    Mockito.when(mockVec2.getValues()).thenReturn(new float[] {4.0f, 5.0f, 6.0f});

    assertEquals(true, FloatArrayVector.areOperable(mockVec1, mockVec2));
  }

  @Test
  void whenVectorsNotSameLength_thenReturnNotOperable() throws NoSuchMethodException, SecurityException {
    FloatArrayVector mockVec1 = Mockito.mock(FloatArrayVector.class);
    Mockito.when(mockVec1.getValues()).thenReturn(new float[] {1.0f, 2.0f, 3.0f});

    FloatArrayVector mockVec2 = Mockito.mock(FloatArrayVector.class);
    Mockito.when(mockVec2.getValues()).thenReturn(new float[] {4.0f, 5.0f});

    assertEquals(false, FloatArrayVector.areOperable(mockVec1, mockVec2));
  }

  @Test
  void whenHasAnArray_thenProducesCorrectToString() {
    FloatArrayVector mockVec = Mockito.mock(FloatArrayVector.class);
    Mockito.when(mockVec.getValues()).thenReturn(new float[] {1.0f, 2.0f});
    Mockito.when(mockVec.toString()).thenCallRealMethod();

    assertEquals("[1.0, 2.0]", mockVec.toString());
  }

  @Test
  void subtractingTwoVectorsProducesCorrectValue() {
    FloatArrayVector mockVec1 = Mockito.mock(FloatArrayVector.class);
    Mockito.when(mockVec1.getValues()).thenReturn(new float[] {1.0f, 2.0f, 3.0f});

    FloatArrayVector mockVec2 = Mockito.mock(FloatArrayVector.class);
    Mockito.when(mockVec2.getValues()).thenReturn(new float[] {4.0f, 5.0f, 6.0f});
    Mockito.when(mockVec2.subtract(mockVec1)).thenCallRealMethod();

    FloatArrayVector vecOut = Mockito.spy(mockVec2.subtract(mockVec1));
    Mockito.when(vecOut.getValues()).thenCallRealMethod();

    assertEquals(new float[] {3.0f, 3.0f, 3.0f}, vecOut);
  }
}
