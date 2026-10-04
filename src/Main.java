import drawing.Circle;
import drawing.RasterRenderer;
import drawing.Renderer;
import drawing.Shape;
import drawing.Square;
import drawing.VectorRenderer;

public class Main {
    private static int passed;

    public static void main(String[] args) {
        if (args.length != 1 || !"--demo".equals(args[0])) {
            System.out.println("Usage: java -cp out Main --demo");
            return;
        }

        Renderer vector = new VectorRenderer();
        Renderer raster = new RasterRenderer();

        check("T1", new Circle("circle-1", 2, vector), "VECTOR circle radius=2", "Circle + VectorRenderer");
        check("T2", new Circle("circle-2", 2, raster), "RASTER circle radius=2", "Circle + RasterRenderer");
        check("T3", new Square("square-1", 3, vector), "VECTOR square side=3", "Square + VectorRenderer");
        check("T4", new Square("square-2", 3, raster), "RASTER square side=3", "Square + RasterRenderer");

        Circle original = new Circle("circle-switch", 2, vector);
        Circle afterSwitch = original;
        String idBefore = original.getId();
        int radiusBefore = original.getRadius();
        String before = original.execute();
        afterSwitch.setImplementation(raster);
        String after = afterSwitch.execute();
        boolean sameObject = original == afterSwitch;
        boolean stateUnchanged = idBefore.equals(afterSwitch.getId())
                && radiusBefore == afterSwitch.getRadius();
        boolean switchPassed = sameObject && stateUnchanged
                && "VECTOR circle radius=2".equals(before)
                && "RASTER circle radius=2".equals(after);
        printSwitch(switchPassed, sameObject, stateUnchanged, before, after);

        System.out.println("SUMMARY: " + passed + "/5 PASS");
    }

    private static void check(String id, Shape shape, String expected, String classes) {
        String actual = shape.execute();
        boolean success = expected.equals(actual);
        if (success) {
            passed++;
        }
        System.out.println(id + " " + (success ? "PASS" : "FAIL") + " | " + classes + " | result=" + actual);
        if (!success) {
            System.out.println("  expected=" + expected);
        }
    }

    private static void printSwitch(boolean success, boolean sameObject, boolean stateUnchanged,
                                    String before, String after) {
        if (success) {
            passed++;
        }
        System.out.println("T5 " + (success ? "PASS" : "FAIL")
                + " | Circle + VectorRenderer -> RasterRenderer"
                + " | sameObject=" + sameObject + " | stateUnchanged=" + stateUnchanged);
        System.out.println("  before=" + before + " | after=" + after);
        if (!success) {
            System.out.println("  expected=VECTOR circle radius=2 -> RASTER circle radius=2; sameObject=true; stateUnchanged=true");
        }
    }
}
