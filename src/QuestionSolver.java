import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.Loader;
import org.rev317.min.api.methods.Game;
import org.rev317.min.api.methods.Menu;

import java.awt.*;
import java.util.ArrayList;

public class QuestionSolver implements Strategy
{
    final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");

    @Override
    public boolean activate() {
        return Game.getOpenBackDialogId() == 368;
    }

    @Override
    public void execute() {
        String message = Loader.getClient().getInterfaceCache()[372].getMessage();

        System.out.println(message);

        if (runnable != null)
            runnable.run();

        if (message.toLowerCase().contains("name")) {
            Menu.sendAction(679, -1, -1, 373);
            Time.sleep(1000);

            Keyboard.getInstance().sendKeys("Ikov");
        } else if(message.toLowerCase().contains("owner")){
            Menu.sendAction(679, -1, -1, 373);
            Time.sleep(1000);

            Keyboard.getInstance().sendKeys("David");
        } else {
            ArrayList<Character> operators = new ArrayList<>();
            operators.add('+');
            operators.add('-');
            operators.add('*');
            operators.add('/');

            char operator = '+';
            int answer = 0;
            int firstOperand;
            int secondOperand;

            for (int i = 0; i < message.length(); i++) {
                if (operators.contains(message.charAt(i))) {
                    operator = message.charAt(i);
                    break;
                }
            }

            String[] intsToOperate = message.replaceAll("[^0-9]+", " ").replaceFirst(" ", "").split("\\s+");

            firstOperand = Integer.parseInt(intsToOperate[0]);
            secondOperand = Integer.parseInt(intsToOperate[1]);

            switch (operator) {
                case '+':
                    answer = firstOperand + secondOperand;
                    break;
                case '-':
                    answer = firstOperand - secondOperand;
                    break;
                case '*':
                    answer = firstOperand * secondOperand;
                    break;
                case '/':
                    answer = firstOperand / secondOperand;
                    break;
            }

            Menu.sendAction(679, -1, -1, 373);
            Time.sleep(1000);

            Keyboard.getInstance().sendKeys(Integer.toString(answer));
        }

        Time.sleep(1000);
    }
}