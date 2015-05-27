package intefaces;

import components.GraphicInterface;
import interpreter.Interpreter;

import java.io.File;
import java.io.IOException;

/**
 * Created by Дмитрий on 24.05.15.
 */
public class PostScriptInterface {
    public static void main(String[] args) throws IOException {
        Interpreter interpreter = Interpreter.instance;
        interpreter.setCustomOperators(GraphicInterface.getCustomsOperators());
//        interpreter.run(new File("graphicsEngine/basics/drawButton.ps"));
//        interpreter.run(new File("graphicsEngine/basics/drawWindows.ps"));
//        interpreter.run(new File("graphicsEngine/basics/drawButtons.ps"));
//        interpreter.run(new File("graphicsEngine/basics/demo.ps"));
        interpreter.run(new File("graphicsEngine/basics/calculator.ps"));
//        interpreter.run(new File("graphicsEngine/basics/demo2.ps"));
//        interpreter.run(new File("graphicsEngine/basics/simpleExample.ps"));
//        interpreter.run(new File("graphicsEngine/basics/windowsWithElements.ps"));
//        interpreter.run(new File("graphicsEngine/basics/onlyButtonGS.sps"));
//        interpreter.run(new File("graphicsEngine/basics/textField.ps"));
//        interpreter.run(new File("graphicsEngine/basics/libs/listLib.ps"));
//        interpreter.run(new File("graphicsEngine/basics/drawing.ps"));
//        interpreter.run(new File("graphicsEngine/basics/UnpressedButton1.ps"));
//        interpreter.run(new File("graphicsEngine/basics/UnpressedCheckbox.ps"));
    }
}
