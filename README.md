# Assignment 3: Bridge Pattern

Student: Arnur Kozhabergen  
Group: se-2529  
Topic: A - Drawing  
Repository: https://github.com/arnur-kozhabergen/sdp.asmnt3  
Base commit (VectorRenderer and RasterRenderer, T1-T5): `f68abb985a57a5e0be449c3846f21904c680f6a8`

This Java console application keeps the shape hierarchy separate from the renderer hierarchy. A shape stores a `Renderer` reference and delegates its drawing operation through that interface. The same shape can use a different renderer at runtime.

## Role map

| Role | Class | Source |
| --- | --- | --- |
| Abstraction | `Shape` | `src/drawing/Shape.java` |
| A1, refined abstraction | `Circle` | `src/drawing/Circle.java` |
| A2, refined abstraction | `Square` | `src/drawing/Square.java` |
| Implementor | `Renderer` | `src/drawing/Renderer.java` |
| I1 | `VectorRenderer` | `src/drawing/VectorRenderer.java` |
| I2 | `RasterRenderer` | `src/drawing/RasterRenderer.java` |
| I3 | `AsciiRenderer` | `src/drawing/AsciiRenderer.java` |
| Client and demo | `Main` | `src/Main.java` |

The interface-typed bridge field, constructor injection, `execute()` declaration, and `setImplementation(...)` are in `Shape`. `Circle.execute()` and `Square.execute()` delegate to the current renderer. The T5 check and reference comparison using `==` are in `Main`.

## Build and run

Requirements: JDK 17 or newer. From the repository root, run:

```text
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
java -cp out Main --demo
```

The demo does not require input. Without `--demo`, the program prints usage and stops.

## Expected results

| Check | Classes or action | Expected result |
| --- | --- | --- |
| T1 | Circle + VectorRenderer | `VECTOR circle radius=2` |
| T2 | Circle + RasterRenderer | `RASTER circle radius=2` |
| T3 | Square + VectorRenderer | `VECTOR square side=3` |
| T4 | Square + RasterRenderer | `RASTER square side=3` |
| T5 | Same Circle, VectorRenderer then RasterRenderer | Same reference and unchanged ID/radius; `VECTOR circle radius=2` then `RASTER circle radius=2` |
| T6 | Circle + AsciiRenderer | `ASCII (circle radius=2)` |
| T7 | Square + AsciiRenderer | `ASCII [square side=3]` |

`Main` compares actual results with these expected values and computes each PASS/FAIL label and the final summary. A failed result also prints the expected value.

## Extension evidence

The base commit above contains working T1-T5 before I3 was added. The next source commit adds `AsciiRenderer` and updates `Main` for T6-T7. `extension.diff` shows that only these two paths changed under `src/`. `sources.txt` was also updated to include the new class.
